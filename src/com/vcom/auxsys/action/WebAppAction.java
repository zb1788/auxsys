/**  
 * @Title: WebAppAction.java
 * @date 2015-11-19
 */
package com.vcom.auxsys.action;

import java.util.List;

import com.vcom.auxsys.entitys.pojo.WebApp;
import com.vcom.auxsys.service.WebAppService;
import com.vcom.auxsys.util.PageUtil;
/**
 * @title WebAppAction
 * @description :应用或目录action类
 * @date 2015-11-19下午3:40:56
 */
public class WebAppAction extends BaseAction {
	private WebApp webApp;//应用
	private List<WebApp> wlist;//应用列表
	private String ids;//应用id串
	private WebAppService webAppService;//服务层
	private String smessage;//提示信息
	
	/**
	 * @Description: 投票列表
	 * @date 2015-11-30
	 */
	public String list(){
		 //加入分页
        PageUtil page = new PageUtil();
        int count = webAppService.getCount();
        page.setPageSize(pageSize);// 每页多少行
        page.setCurPage(pageIndex);//当前页
        page.setTotalRow(count);//总页数
        pageBar = page.getToolsMenu();
        
        if(null==webApp||"".equals(webApp.getName())){
        	wlist=this.webAppService.getWebAppList(page.getStart(),pageSize);
        }else{
        	this.wlist=this.webAppService.queryWebApp(webApp);	
        }
		return "SUCCESS";
	}
	/**
	 * @Description: 应用搜索
	 * @date 2015-11-30
	 */
	public String search(){
		this.wlist=this.webAppService.queryWebApp(webApp);			
		 //加入分页
        PageUtil page = new PageUtil();
        int count = this.wlist.size();
        page.setPageSize(pageSize);// 每页多少行
        page.setCurPage(pageIndex);//当前页
        page.setTotalRow(count);//总页数
        pageBar = page.getToolsMenu();
		return "SUCCESS";
	}
	/**
	 * @Description:请求应用添加页面
	 * @date 2015-11-19
	 */
	public String toAdd(){
		this.setWebApp(null);
		return "SUCCESS";
	}
	/**
	 * @Description: 添加一条记录
	 * @date 2015-11-19
	 */
	public String add(){
		smessage=this.webAppService.add(webApp);
		if("success".equals(smessage)){
			smessage=null;
		}
		return "SUCCESS";
	}
	/**
	 * @Description: 请求应用修改页面
	 * @date 2015-11-19
	 */
	public String toEdit(){
		if(null==ids||"".endsWith(ids))return "FALSE";
		this.setWebApp(this.getWebAppService().getWebAppById(ids));
		return "SUCCESS";
	}
	/**
	 * @Description: 修改一条记录
	 * @date 2015-11-19
	 */
	public String edit(){
		smessage=this.webAppService.edit(webApp);
		if("success".equals(smessage)){
			smessage=null;
		}
		return "SUCCESS";
	}
	/**
	 * @Description: 删除一条记录
	 * @date 2015-11-19
	 */
	public String remove(){
		if(null==ids||"".equals(ids))
			return "FALSE";
		String [] idl=null;
		if(ids!="")
			idl=ids.split(",");
		this.webAppService.remove(idl);
		return "SUCCESS";
	}
	/**
	 * @return the webApp
	 */
	public WebApp getWebApp() {
		return webApp;
	}
	/**
	 * @param webApp the webApp to set
	 */
	public void setWebApp(WebApp webApp) {
		this.webApp = webApp;
	}
	/**
	 * @return the wlist
	 */
	public List<WebApp> getWlist() {
		return wlist;
	}
	/**
	 * @param wlist the wlist to set
	 */
	public void setWlist(List<WebApp> wlist) {
		this.wlist = wlist;
	}
	/**
	 * @return the ids
	 */
	public String getIds() {
		return ids;
	}
	/**
	 * @param ids the ids to set
	 */
	public void setIds(String ids) {
		this.ids = ids;
	}
	/**
	 * @return the webAppService
	 */
	public WebAppService getWebAppService() {
		return webAppService;
	}
	/**
	 * @param webAppService the webAppService to set
	 */
	public void setWebAppService(WebAppService webAppService) {
		this.webAppService = webAppService;
	}
	/**
	 * @return the message
	 */
	public String getSmessage() {
		return smessage;
	}
	/**
	 * @param message the message to set
	 */
	public void setSmessage(String smessage) {
		this.smessage = smessage;
	}
	
}
