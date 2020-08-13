/**  
 * @Title: votoService.java
 * @Package com.vcom.auxsys.service
 * @Description: TODO
 * @author 罗华森
 * @date 2015-11-12
 */
package com.vcom.auxsys.service;

import java.util.List;

import com.vcom.auxsys.entitys.pojo.Vote;

/**
 * @title votoService
 * @description :
 * @author 罗华森
 * @date 2015-11-12下午1:48:30
 */
public interface VoteService {
	/**
	 * @Description:添加一条记录
	 * @author 罗华森
	 * @date 2015-11-19
	 */
	public String insertVoto(Vote v);
	/**
	 * @Description: 删除一条记录
	 * @author 罗华森
	 * @date 2015-11-19
	 */
	public void deleteVoto(String[] list);
	/**
	 * @Description: 修改一条记录
	 * @author 罗华森
	 * @date 2015-11-19
	 */
	public String editVoto(Vote v);
	/**
	 * @Description: 获取列表
	 * @author 罗华森
	 * @date 2015-11-19
	 */
	public List<Vote> getVoteList(List<String> list,Integer start,Integer size);
	/**
	 * @Description: 获取一条记录
	 * @author 罗华森
	 * @date 2015-11-19
	 */
	public Vote getVote(Vote v);
	/**
	 * @Description: 管理记录
	 * @author 罗华森
	 * @date 2015-11-19
	 */
	public void manageVote(List<Vote> list);
	/**
	 * @Description: 当前投票定义是否有记录
	 * @author 罗华森
	 * @date 2015-11-20
	 */
	public Boolean hasResult(String id);
	/**
	 * @Description: 模糊查询
	 * @author 罗华森
	 * @date 2015-12-1
	 */
	public List<Vote> queryVote(Vote v);
	/**
     * @Description: 获取记录个数
     * @author 罗华森
     * @date 2015-12-11
     */
    public int getCount();
}
