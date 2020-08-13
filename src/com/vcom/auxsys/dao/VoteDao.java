/**  
 * @Title: VoteDao.java
 * @Package com.vcom.auxsys.dao
 * @Description: TODO
 * @author 罗华森
 * @date 2015-11-12
 */
package com.vcom.auxsys.dao;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.bson.Document;

import com.mongodb.DBObject;
import com.vcom.auxsys.entitys.pojo.Vote;
import com.vcom.auxsys.entitys.pojo.VoteResult;

/**
 * @title VoteDao
 * @description :
 * @author 罗华森
 * @date 2015-11-12下午12:24:49
 */
public interface VoteDao {
	/**
	 * @Description: 添加一条投票定义
	 * @param @param v   
	 * @return void  
	 * @throws
	 * @author 罗华森
	 * @date 2015-11-12
	 */
	public String insertVote(Vote v);
	/**
	 * @Description: 删除投票定义
	 * @param @param v   
	 * @return void  
	 * @throws
	 * @author 罗华森
	 * @date 2015-11-12
	 */
	public void deleteVote(String[] ids);
	/**
	 * @Description: 修改一条投票定义
	 * @param @param v   
	 * @return void  
	 * @throws
	 * @author 罗华森
	 * @date 2015-11-12
	 */
	public String editVote(Vote v);
	/**
	 * @Description: 获取一个投票定义
	 * @param @param v   
	 * @return void  
	 * @throws
	 * @author 罗华森
	 * @date 2015-11-12
	 */
	public Vote getVote(Vote v);
	/**
	 * @Description: 获取全部投票
	 * @param @return   
	 * @return List  
	 * @throws
	 * @author 罗华森
	 * @date 2015-11-12
	 */
	public List getVotelist(List<String> list,Integer start,Integer size);
	/**
	 * @Description: 修改投票状态
	 * @param @param list   
	 * @return void  
	 * @throws
	 * @author 罗华森
	 * @date 2015-11-16
	 */
	public void manageVote(List<Vote> list);
	
	/**
	 * @description：获取投票结果列表
	 * @time： 2015-11-17
	 * @author：donghaoyu
	 * @param doc 查询条件
	 * @return 
	 */
	public List<VoteResult> getVoteResultsList(Document doc,String optionid,Map pMap);
	/**
	 * @Description: 查看当前投票定义是否已经使用
	 * @author 罗华森
	 * @date 2015-11-20
	 */
	public Boolean hasResult(String id);
	
	/**
     * @description：插入投票结果
     * @time： 2015-11-24
     * @author：donghaoyu
     * @param voteResult 
     */
    public void insertVoteResult(VoteResult voteResult);
    
    /**
     * @description：修改投票结果中的排序项的值为该选项id的值
     * @time： 2015-11-24
     * @author：donghaoyu
     * @param optionId 
     */
    public void editVoteResultOrder(String optionId,String voteid,String appid);
    
    /**
     * @description：根据请求参数查询投票结果列表幷返回json形式字符串
     * @time： 2015-11-24
     * @author：donghaoyu
     * @param map
     * @return 
     */
    public String getVotePageByMap(LinkedHashMap map);
    
    /**
     * @description：获取投票结果个数
     * @time： 2016-1-19
     * @author：donghaoyu
     * @param doc
     * @return 
     */
    public Integer getVoteResultCount(Document doc);
    
    /**
     * @description：检查投票id和投票项是否匹配
     * @time： 2015-11-23
     * @author：donghaoyu
     * @param voteResult
     * @return 
     */
    public String checkVoteSfcz(VoteResult voteResult);
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
