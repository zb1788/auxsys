package zzvcom.sys.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;

import zzvcom.sys.pojo.AuxsysModule;
import zzvcom.sys.pojo.AuxsysRole;
import zzvcom.sys.service.RoleService;

import com.opensymphony.xwork2.ActionSupport;

public class BaseAction extends ActionSupport implements ServletRequestAware,
		ServletResponseAware {
	protected HttpServletRequest request;
	protected HttpServletResponse response;
	private static final long serialVersionUID = 6126905657342889076L;
	private String pageBar;// 分页条
	private int pageIndex = 1;// 页码
	private String rightTree;
	private String leftTree;
	private RoleService roleService;
	protected int operFlag = 0;// 操作标帜 0 -新增 1-修改

	//角色部分
	private String backvalue = "success";
	public String logInfo;//日志信息
	public boolean isLog=false;//是否添加日志信息
	public AuxsysRole auxsysRole = new AuxsysRole();
	
	//默认转向方法
	public String execute(){
		return SUCCESS;
	}
	
	public String getRightTree() {
		return rightTree;
	}

	public void setRightTree(String rightTree) {
		this.rightTree = rightTree;
	}

	public String getBackvalue() {
		return backvalue;
	}

	public void setBackvalue(String backvalue) {
		this.backvalue = backvalue;
	}

	public String getLeftTree() {
		return leftTree;
	}

	public void setLeftTree(String leftTree) {
		this.leftTree = leftTree;
	}

	public int getOperFlag() {
		return operFlag;
	}

	public void setOperFlag(int operFlag) {
		this.operFlag = operFlag;
	}

	public String getPageBar() {
		return pageBar;
	}

	public void setPageBar(String pageBar) {
		this.pageBar = pageBar;
	}

	public int getPageIndex() {
		return pageIndex;
	}

	public void setPageIndex(int pageIndex) {
		this.pageIndex = pageIndex;
	}

	public void setServletRequest(HttpServletRequest request) {
		this.request = request;

	}

	public RoleService getRoleService() {
		return roleService;
	}

	public void setRoleService(RoleService rService) {
		this.roleService = rService;
	}

	public void setServletResponse(HttpServletResponse response) {
		this.response = response;

	}

	public String getLogInfo() {
		return logInfo;
	}

	public void setLogInfo(String logInfo) {
		this.logInfo = logInfo;
	}

	public boolean isLog() {
		return isLog;
	}

	public void setLog(boolean isLog) {
		this.isLog = isLog;
	}

    public AuxsysRole getAuxsysRole() {
        return auxsysRole;
    }

    public void setAuxsysRole(AuxsysRole auxsysRole) {
        this.auxsysRole = auxsysRole;
    }

}
