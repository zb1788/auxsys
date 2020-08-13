package com.vcom.auxsys.dao;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.bson.Document;

import com.vcom.auxsys.entitys.pojo.Comments;

/**
 * @description： 评论接口
 * @time： 2015-11-11
 * @author： donghaoyu
 */
public interface CommentsDao {
    
    /**
     * @description：获取评论列表
     * @time： 2015-11-11
     * @param doc 查询参数
     * @param pMap 分页map
     * @return 返回评论列表
     */
    public List<Comments> getCommentsList(Document doc,Map pMap);
    
    /**
     * @description： 保存评论
     * @time： 2015-11-11
     * @param comments 评论对象
     */
    public void saveComments(Comments comments);
    
    /**
     * @description：根据评论id获取详细评论信息
     * @time： 2015-11-11
     * @param commentsid 评论id
     * @return 
     */
    public Comments getComments(String commentsid);
    
    /**
     * @description:插入评论
     * @time： 2015-11-23
     * @param comments 
     */
    public void insertComments(Comments comments);
    
    /**
     * @description：批量删除评论
     * @time： 2015-11-11
     * @param commentsids  评论ids
     */
    public void delComments(String commentsids);
    
    /**
     * @description 批量审核评论
     * @time： 2015-11-11
     * @param commentsids 评论ids
     */
    public void checkComments(String commentsids);
    
    /**
     * @description：根据查询条件获取评论总条数
     * @time： 2015-11-19
     * @param doc
     * @return 
     */
    public int getCommentsCount(Document doc);
    
    /**
     * @description：根据请求参数查询评论列表幷返回json形式字符串
     * @time： 2015-11-23
     * @param map 
     * @param start
     * @param limit
     * @return 
     */
    public String getCommentsPageByMap(LinkedHashMap map, int start, int limit);
    
    /**
     * @description：更新评论打分
     * @time： 2016-3-3
     * @author：donghaoyu
     * @param commentId 评论id
     * @return 
     */
    public String updateCommentPoint(String commentId);
    
}
