package zzvcom.sys.action;

import java.util.List;

import org.apache.log4j.Logger;

import com.vcom.auxsys.util.PageUtil;

import zzvcom.sys.pojo.AuxsysModule;
import zzvcom.sys.service.ModuleService;

public class ModuleAction extends BaseAction{
	public static final Logger log = Logger.getLogger(ModuleAction.class);
	//模块属性参数
	private AuxsysModule moduleobj;
	//id串
	private String ids;
	//结果列表
	private List<AuxsysModule> datalist;
	//service对象
	private ModuleService moduleservice;
	//失败消息
	private String smessage;
	
	/**
	 * 列表
	 * @return
	 */
	public String list(){
        PageUtil page = new PageUtil();
		page.setCurPage(this.getPageIndex());
		if(moduleobj==null){
			moduleobj=new AuxsysModule();
		}
		moduleobj.setId(null);
		int count = moduleservice.getModuleCount(moduleobj);
		page.setTotalRow(count);
		this.setPageBar(page.getToolsMenu());
		datalist=moduleservice.queryByAuxsysModules(moduleobj, page.getStart(), page.getPageSize());
		return SUCCESS;
	}
	
	/**
	 * 保存新建模块
	 * @return
	 */
	public String save(){
		int ri=moduleservice.add(moduleobj);
		if(ri==1){
			return SUCCESS;
		}
		if(ri==-1){
			smessage="菜单名冲突";
		}
		return ERROR;
	}
	
	/**
	 * 打开修改
	 * @return
	 */
	public String edit(){
		moduleobj=moduleservice.getModuleById(moduleobj.getId());
		if(moduleobj==null){
			return ERROR;
		}
		return SUCCESS;
	}
	
	/**
	 * 保存修改
	 * @return
	 */
	public String update(){
		int ri=moduleservice.update(moduleobj);
		if(ri==1){
			return SUCCESS;
		}
		if(ri==-1){
			smessage="菜单名冲突";
		}
		return ERROR;
	}
	
	/**
	 * 批量删除
	 * @return
	 */
	public String delete(){
		moduleservice.deleteModuleByIds(ids);
		return SUCCESS;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

	public List<AuxsysModule> getDatalist() {
		return datalist;
	}

	public void setDatalist(List<AuxsysModule> datalist) {
		this.datalist = datalist;
	}


	public AuxsysModule getModuleobj() {
		return moduleobj;
	}

	public void setModuleobj(AuxsysModule moduleobj) {
		this.moduleobj = moduleobj;
	}

	public ModuleService getModuleService() {
		return moduleservice;
	}

	public void setModuleService(ModuleService moduleservice) {
		this.moduleservice = moduleservice;
	}

	public String getSmessage() {
		return smessage;
	}
	
}
