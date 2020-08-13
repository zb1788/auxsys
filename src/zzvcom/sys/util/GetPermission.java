package zzvcom.sys.util;

import java.util.List;

import zzvcom.sys.dao.AuxsysModuleDao;
import zzvcom.sys.pojo.AuxsysModule;
import zzvcom.sys.pojo.AuxsysRole;
import com.googlecode.jsonplugin.JSONException;
import com.googlecode.jsonplugin.JSONUtil;
/**
 * 获取用户权限信息
 * @author liuzhiqiang
 *
 */
public class GetPermission {
	/**
	 * 返回登录的权限树
	 * @param user
	 * @param modelService
	 * @return
	 */
	public static String getLoginUserPermission(int udepth){
		List menulist=new java.util.ArrayList();
		//查询功能列表
		AuxsysModuleDao auxsysModuleDao = new AuxsysModuleDao();
		List modellist = auxsysModuleDao.getAllModelList();
		for(int i=0;i<modellist.size();i++){
		    AuxsysModule module = (AuxsysModule)modellist.get(i);
		    int mdepth = 0;
		    try{
		    	mdepth=Integer.parseInt(module.getDepth());
		    }catch(Exception e){}
		    //当菜单级别为0或未设置,或者用户级别数小于菜单级别数时,显示菜单
			if(mdepth==0 || udepth<=mdepth){
			    TreeForm tree=new TreeForm();
	            tree.setId("o_"+module.getId());
	            tree.setText(module.getName());
	            if(module.getLink()!=null){
	                tree.setHref(module.getLink());
	            }
	            menulist.add(tree);
			}
		}
		try {
			String value=JSONUtil.serialize(menulist,null,null,false,false);
			return value;
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 返回模块对应的操作列表
	 * @return
	 */
	public static String getModelOperations(){
		return null;
	}
	/**
	 * 获取用户权限和操作权限
	 * @param per
	 * @param type
	 * @return
	 */
	public static String getPer(String per,boolean pertype,boolean type){
		per=per+",";
		if(pertype&&type){
			return per.replaceAll("mc?_", "").replaceAll("o_\\d+,?", "").replaceAll(",*$", "").replaceAll("^,", "");
		}else if(!pertype&&type){
			return per.replaceAll("m_", "").replaceAll("mc_\\d+,?", "").replaceAll("o_\\d+,?", "").replaceAll(",*$", "").replaceAll("^,", "");
		}else
			return per.replaceAll("o_", "").replaceAll("mc?_\\d+,?", "").replaceAll(",*$", "").replaceAll("^,", "");
	}
}
