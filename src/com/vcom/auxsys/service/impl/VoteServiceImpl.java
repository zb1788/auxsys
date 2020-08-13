/**  
 * @Title: voteServiceImpl.java
 * @Package com.vcom.auxsys.service.impl
 * @Description: TODO
 * @author 罗华森
 * @date 2015-11-12
 */
package com.vcom.auxsys.service.impl;

import java.util.List;

import com.vcom.auxsys.dao.VoteDao;
import com.vcom.auxsys.entitys.pojo.Vote;
import com.vcom.auxsys.service.VoteService;

/**
 * @title voteServiceImpl
 * @description :
 * @author 罗华森
 * @date 2015-11-12下午1:57:27
 */
/**
 * @title VoteServiceImpl
 * @description :
 * @author 罗华森
 * @date 2015-12-1上午11:09:27
 */
public class VoteServiceImpl implements VoteService{
	private VoteDao voteDao;

	/* (non-Javadoc)
	 * @see com.vcom.auxsys.service.voteService#insertVoto(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.util.List)
	 */
	
	public String insertVoto(Vote v) {
		// TODO Auto-generated method stub
		return voteDao.insertVote(v);
	}

	/* (non-Javadoc)
	 * @see com.vcom.auxsys.service.voteService#deleteVoto(java.util.List)
	 */
	
	public void deleteVoto(String[] list) {
		// TODO Auto-generated method stub
		voteDao.deleteVote(list);
	}

	/* (non-Javadoc)
	 * @see com.vcom.auxsys.service.voteService#editVoto(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.util.List)
	 */
	
	public String editVoto(Vote v) {
		// TODO Auto-generated method stub
		return this.voteDao.editVote(v);
	}

	/* (non-Javadoc)
	 * @see com.vcom.auxsys.service.voteService#getVoteList()
	 */
	
	public List<Vote> getVoteList(List<String> list,Integer start,Integer size) {
		// TODO Auto-generated method stub
		return voteDao.getVotelist(list,start,size);
	}

	/* (non-Javadoc)
	 * @see com.vcom.auxsys.service.voteService#getVote()
	 */
	
	public Vote getVote(Vote v) {
		// TODO Auto-generated method stub
		return voteDao.getVote(v);
	}

	/**
	 * @return the voteDao
	 */
	public VoteDao getVoteDao() {
		return voteDao;
	}

	/**
	 * @param voteDao the voteDao to set
	 */
	public void setVoteDao(VoteDao voteDao) {
		this.voteDao = voteDao;
	}

	/* (non-Javadoc)
	 * @see com.vcom.auxsys.service.VoteService#manageVote(java.util.List)
	 */
	
	public void manageVote(List<Vote> list) {
		// TODO Auto-generated method stub
		voteDao.manageVote(list);
	}

	/* (non-Javadoc)
	 * @see com.vcom.auxsys.service.VoteService#hasResult(java.lang.String)
	 */
	public Boolean hasResult(String id) {
		// TODO Auto-generated method stub
		return this.voteDao.hasResult(id);
	}
	/* (non-Javadoc)
	 * @see com.vcom.auxsys.service.VoteService#queryVote(com.vcom.auxsys.entitys.pojo.Vote)
	 */
	public List<Vote> queryVote(Vote v){
		return this.voteDao.queryVote(v);
	}
	/**
     * @Description: 获取记录个数
     * @author 罗华森
     * @date 2015-12-11
     */

	/* (non-Javadoc)
	 * @see com.vcom.auxsys.service.VoteService#getCount()
	 */
	public int getCount() {
		// TODO Auto-generated method stub
		return this.voteDao.getCount();
	}

}
