package zzvcom.sys.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;
import zzvcom.frame.beans.VcomFrameSettingImpl;
import zzvcom.sys.pojo.AuxsysRole;
import zzvcom.sys.util.GetPermission;
import zzvcom.sys.util.MD5;

public class LoginAction extends BaseAction{
	public static final Logger log = Logger.getLogger(LoginAction.class);
	VcomFrameSettingImpl frameindexpageimpl = new VcomFrameSettingImpl();
    Map<String, String> map = frameindexpageimpl.getFrameSetting();
    private String projecttitle;
    private String projecticon;
    private String vcode;
	public String getProjecttitle() {
		return map.get("project-name");
	}
	public String getProjecticon() {
		return map.get("index-icon");
	}
	public AuxsysRole getRole(){
	    return auxsysRole;
	}
	public String index(){
		request.setAttribute("message", "login");
		return SUCCESS;
	}
	public String quit(){
		request.getSession().removeAttribute("user");
		request.getSession().removeAttribute("userInfo");
		request.getSession().removeAttribute("listOperate");
		request.getSession().removeAttribute("menuTree");
		request.setAttribute("message", "login");
		return SUCCESS;
	}
	public String login(){
		String message = "";
		String vcode = request.getParameter("vcode");
		if (request.getSession().getAttribute("validateCode") == null) {
			message = "校验码无效！";
			request.setAttribute("message", message);
			return "error";
		}
		String validateCode = request.getSession().getAttribute("validateCode")
				.toString();
		if (!vcode.equals(validateCode)) {
			message = "验证码错误,请重新输入！";
			request.setAttribute("message", message);
			return "error";
		} else {
			auxsysRole.setPassword(auxsysRole.getPassword());
			List<AuxsysRole> list=null;
			try{
				list = this.getRoleService().getLoginRole(auxsysRole);
			}catch(Exception e){
				e.printStackTrace();
			}
			
			if (list == null)
				list = new ArrayList<AuxsysRole>();
			if (list.size() > 0) {
				auxsysRole = list.get(0);
				request.getSession().setAttribute("userName", auxsysRole.getUsername());
				int idepth=0;
				if(auxsysRole.getUsername().equals("admin")){
					String menuTree=GetPermission.getLoginUserPermission(1);
					request.getSession().setAttribute("menuTree", menuTree);
					request.getSession().setAttribute("userInfo", auxsysRole);
					return SUCCESS;
				}else{
					try{
						idepth=Integer.parseInt(auxsysRole.getDepth());
					}catch(Exception e){}
					if(0==idepth){
						message = "当前用户没有权限，请联系超级管理员！";
						request.setAttribute("message", message);
						return "error";
					}else{
						String menuTree=GetPermission.getLoginUserPermission(idepth);
						request.getSession().setAttribute("menuTree", menuTree);
						request.getSession().setAttribute("userInfo", auxsysRole);
						request.getSession().setAttribute("userName", auxsysRole.getUsername());
						return SUCCESS;
					}
				}
				
			} else {
				message = "密码或用户名错误,请重新输入！";
				request.setAttribute("message", message);
				return "error";
			}
		}
	}
    public String getVcode() {
        return vcode;
    }
    public void setVcode(String vcode) {
        this.vcode = vcode;
    }

}
