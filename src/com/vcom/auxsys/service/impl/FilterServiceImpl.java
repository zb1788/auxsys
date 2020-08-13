/**  
 * @Title: FilterServiceImpl.java
 * @Package com.vcom.auxsys.service.impl
 * @Description: TODO
 * @author 罗华森
 * @date 2015-11-18
 */
package com.vcom.auxsys.service.impl;

import java.util.List;

import com.vcom.auxsys.dao.FilterDao;
import com.vcom.auxsys.entitys.pojo.FilterKey;
import com.vcom.auxsys.service.FilterService;

/**
 * @title FilterServiceImpl
 * @description :
 * @author 罗华森
 * @date 2015-11-18下午6:54:48
 */
public class FilterServiceImpl implements FilterService {
	private FilterDao filterDao;

	/* (non-Javadoc)
	 * @see com.vcom.auxsys.service.FilterService#add(com.vcom.auxsys.entitys.pojo.Filter)
	 */
	
	public String add(FilterKey f) {
		// TODO Auto-generated method stub
		return filterDao.add(f);
	}

	/* (non-Javadoc)
	 * @see com.vcom.auxsys.service.FilterService#delete(java.util.List)
	 */
	
	public void delete(String[] list) {
		// TODO Auto-generated method stub
		filterDao.delete(list);
	}

	/* (non-Javadoc)
	 * @see com.vcom.auxsys.service.FilterService#edit(com.vcom.auxsys.entitys.pojo.Filter)
	 */
	
	public String edit(FilterKey f) {
		// TODO Auto-generated method stub
		return filterDao.edit(f);
	}

	/* (non-Javadoc)
	 * @see com.vcom.auxsys.service.FilterService#get(java.lang.String)
	 */
	
	public FilterKey get(FilterKey f) {
		// TODO Auto-generated method stub
		return filterDao.get(f);
	}

	/* (non-Javadoc)
	 * @see com.vcom.auxsys.service.FilterService#list(java.util.List)
	 */
	
	public List<FilterKey> list(List<String> list,Integer start,Integer size) {
		// TODO Auto-generated method stub
		return filterDao.list(list,start,size);
	}

	/**
	 * @return the filterDao
	 */
	public FilterDao getFilterDao() {
		return filterDao;
	}

	/**
	 * @param filterDao the filterDao to set
	 */
	public void setFilterDao(FilterDao filterDao) {
		this.filterDao = filterDao;
	}
	public List<FilterKey> queryFilter(FilterKey f){
		return this.filterDao.queryFilter(f);
	}

	/* (non-Javadoc)
	 * @see com.vcom.auxsys.service.FilterService#getCount()
	 */
	public int getCount() {
		// TODO Auto-generated method stub
		return this.filterDao.getCount();
	}

}
