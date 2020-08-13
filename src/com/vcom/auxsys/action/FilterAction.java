/**  
 * @Title: FilterAction.java
 * @Package com.vcom.auxsys.action
 * @Description: TODO
 * @author 罗华森
 * @date 2015-11-18
 */
package com.vcom.auxsys.action;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.vcom.auxsys.entitys.pojo.FilterKey;
import com.vcom.auxsys.service.FilterService;
import com.vcom.auxsys.util.PageUtil;

/**
 * @title FilterAction
 * @description :
 * @author 罗华森
 * @date 2015-11-18下午6:59:23
 */
public class FilterAction extends BaseAction{
	private FilterService filterService;//过滤词业务对象
	private FilterKey filterKey;//过滤词
	private List<FilterKey> flist;//过滤词列表
	private String ids;//过滤词id串
	private String smessage;
    /**
     * @Description: 获取过滤词列表
     * @author 罗华森
     * @date 2015-11-19
     */
    public String list(){	
    	 //加入分页
        PageUtil page = new PageUtil();
        int count = this.filterService.getCount();
        page.setPageSize(pageSize);// 每页多少行
        page.setCurPage(pageIndex);//当前页
        page.setTotalRow(count);//总页数
        pageBar = page.getToolsMenu();
        
        if(null==filterKey||"".equals(filterKey.getContent())){
    		this.flist=this.filterService.list(null,page.getStart(),pageSize);
    	}else{
    		this.flist=this.filterService.queryFilter(filterKey);	
    	}   
    	return "SUCCESS";
    }
    /**
     * @Description: 查询
     * @author 罗华森
     * @date 2015-12-1
     */
    public String search(){
    	
		 //加入分页
        PageUtil page = new PageUtil();
        int count = this.flist.size();
        page.setPageSize(pageSize);// 每页多少行
        page.setCurPage(pageIndex);//当前页
        page.setTotalRow(count);//总页数
        pageBar = page.getToolsMenu();
		return "SUCCESS";
    }
    /**
     * @Description: 请求过滤词添加界面
     * @author 罗华森
     * @date 2015-11-19
     */
    public String toAdd(){
    	filterKey=null;
        return "SUCCESS";
    }
    /**
     * @Description: 添加一条过滤词
     * @author 罗华森
     * @date 2015-11-19
     */
    public String add(){
    	SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
    	Date d=new Date();
    	this.filterKey.setCreatetime(df.format(d));
    	smessage=this.filterService.add(filterKey);
    	if("success".equals(smessage))smessage=null;
        return "SUCCESS";
    }
    /**
     * @Description: 删除过滤词
     * @author 罗华森
     * @date 2015-11-19
     */
    public String remove(){
    	if(null==ids||"".equals(ids))
    		return "FALSE";
    	String[] idstr=ids.split(",");    	
    	this.filterService.delete(idstr);
    	return "SUCCESS";
    }
    /**
     * @Description: 请求过滤词修改页面
     * @author 罗华森
     * @date 2015-11-19
     */
    public String toEdit(){
    	if(null==filterKey.getId()||"".endsWith(filterKey.getId()))return "FALSE";
    	this.filterKey=this.filterService.get(filterKey);
    	return "SUCCESS";
    }
    /**
     * @Description: 更新一条过滤词
     * @author 罗华森
     * @date 2015-11-19
     */
    public String edit(){
    	smessage=this.filterService.edit(filterKey);
    	if("success".equals(smessage))smessage=null;
    	return "SUCCESS";
    }
	/**
	 * @return the filterService
	 */
	public FilterService getFilterService() {
		return filterService;
	}

	/**
	 * @param filterService the filterService to set
	 */
	public void setFilterService(FilterService filterService) {
		this.filterService = filterService;
	}

	/**
	 * @return the filter
	 */
	public FilterKey getFilterKey() {
		return filterKey;
	}

	/**
	 * @param filter the filter to set
	 */
	public void setFilterKey(FilterKey filterKey) {
		this.filterKey = filterKey;
	}

	/**
	 * @return the flist
	 */
	public List<FilterKey> getFlist() {
		return flist;
	}

	/**
	 * @param flist the flist to set
	 */
	public void setFlist(List<FilterKey> flist) {
		this.flist = flist;
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
	 * @return the smessage
	 */
	public String getSmessage() {
		return smessage;
	}
	/**
	 * @param smessage the smessage to set
	 */
	public void setSmessage(String smessage) {
		this.smessage = smessage;
	}
}
