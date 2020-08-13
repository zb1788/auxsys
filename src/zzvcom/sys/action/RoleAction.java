package zzvcom.sys.action;

import java.util.List;

import org.apache.log4j.Logger;

import com.vcom.auxsys.util.PageUtil;

import zzvcom.sys.pojo.AuxsysRole;
import zzvcom.sys.service.RoleService;

public class RoleAction extends BaseAction{
	public static final Logger log = Logger.getLogger(RoleAction.class);
	//用户属性参数
	private AuxsysRole roleobj;
	//id串
	private String ids;
	//结果列表
	private List<AuxsysRole> datalist;
	//service对象
	private RoleService roleservice;
	//失败消息
	private String smessage;
	//修改密码-新密码
	private String password;
	/**
	 * 用户列表
	 * @return
	 */
	public String list(){
        PageUtil page = new PageUtil();
		page.setCurPage(this.getPageIndex());
		if(roleobj==null){
			roleobj=new AuxsysRole();
		}
		roleobj.setId(null);
		page.setTotalRow(roleservice.getRoleCount(roleobj));
		this.setPageBar(page.getToolsMenu());
		datalist=roleservice.queryByAuxsysRoles(roleobj, page.getStart(), page.getPageSize());
		return SUCCESS;
	}
	/**
	 * 保存新用户信息
	 * @return
	 */
	public String save(){
		int ri=roleservice.addRole(roleobj);
		if(ri==1){
			return SUCCESS;
		}
		if(ri==-1){
			smessage="用户名冲突";
		}
		return ERROR;
	}
	/**
	 * 进入编辑用户信息界面
	 * @return
	 */
	public String edit(){
		roleobj=roleservice.getRoleById(roleobj.getId());
		if(roleobj==null){
			return ERROR;
		}
		return SUCCESS;
	}
	/**
	 * 保存用户修改
	 * @return
	 */
	public String update(){
		roleservice.update(roleobj);
		return SUCCESS;
	}
	/**
	 * 批量删除
	 * @return
	 */
	public String delete(){
		roleservice.deleteRoleByIds(ids);
		return SUCCESS;
	}
	/**
	 * 个人修改密码
	 * @return
	 */
	public String editPwd(){
		if(password==null || password.trim().length()==0){
			smessage="新密码不能为空!";
			return ERROR;
		}
		int ri = roleservice.editPwd(roleobj,password); 
		if(ri==1){
			return SUCCESS;
		}else if(ri==-1){
			smessage="原密码错误!";
		}else if(ri==-2){
			smessage="用户数量错误!";
		}
		return ERROR;
	}
	/**
	 * 批量重置密码
	 * @return
	 */
	public String resetPwd(){
		roleservice.resetPwd(ids);
		return SUCCESS;
	}
	public void setIds(String ids) {
		this.ids = ids;
	}

	public List<AuxsysRole> getDatalist() {
		return datalist;
	}

	public void setDatalist(List<AuxsysRole> datalist) {
		this.datalist = datalist;
	}

	public AuxsysRole getRoleobj() {
		return roleobj;
	}

	public void setRoleobj(AuxsysRole roleobj) {
		this.roleobj = roleobj;
	}

	public RoleService getRoleService() {
		return roleservice;
	}

	public void setRoleService(RoleService roleservice) {
		this.roleservice = roleservice;
	}

	public String getSmessage() {
		return smessage;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
}
