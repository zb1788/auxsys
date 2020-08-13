/**  
 * @Title: FilterDao.java
 * @Package com.vcom.auxsys.dao.impl
 * @Description: TODO
 * @author 罗华森
 * @date 2015-11-18
 */
package com.vcom.auxsys.dao;

import java.util.List;

import com.vcom.auxsys.entitys.pojo.FilterKey;

/**
 * @title FilterDao
 * @description :过滤词持久化对象
 * @author 罗华森
 * @date 2015-11-18下午6:06:05
 */
public interface FilterDao {
	/**
	 * @Description: 添加一个过滤词
	 * @param @param f   
	 * @return void  
	 * @throws
	 * @author 罗华森
	 * @date 2015-11-18
	 */
	public String add(FilterKey f);
	/**
	 * @Description: 删除一个过滤词
	 * @param @param list   
	 * @return void  
	 * @throws
	 * @author 罗华森
	 * @date 2015-11-18
	 */
	public void delete(String[] list);
	/**
	 * @Description: 修改一个过滤词
	 * @param @param f   
	 * @return void  
	 * @throws
	 * @author 罗华森
	 * @date 2015-11-18
	 */
	public String edit(FilterKey f);
	/**
	 * @Description: 获取一个过滤词
	 * @param @param id   
	 * @return void  
	 * @throws
	 * @author 罗华森
	 * @date 2015-11-18
	 */
	public FilterKey get(FilterKey f);
	/**
	 * @Description: 获取过滤词列表
	 * @param @param list
	 * @param @return   
	 * @return List  
	 * @throws
	 * @author 罗华森
	 * @date 2015-11-18
	 */
	public List list(List<String> list,Integer start,Integer size);
	/**
	 * @Description: 模糊查询
	 * @author 罗华森
	 * @date 2015-12-1
	 */
	public List<FilterKey> queryFilter(FilterKey f);
	/**
     * @Description: 获取记录个数
     * @author 罗华森
     * @date 2015-12-11
     */
    public int getCount();
}
