package com.vcom.auxsys.action;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.bson.Document;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.vcom.auxsys.dao.CommentsDao;
import com.vcom.auxsys.entitys.pojo.Comments;
import com.vcom.auxsys.util.PageUtil;

/**
 * @description： 
 * @time： 2015-11-11
 * @author： donghaoyu
 */
public class CommentsAction extends BaseAction {
    
    private CommentsDao commentsDao; //评论接口
    private String webappcode;//编码（目录编码 or 应用编码）
    private String channelCode;//栏目编号
    private Comments comments; //评论对象
    private String q_createtime;//创建时间起
    private String s_createtime;//创建时间止
    private List<Comments> commentsList; //评论列表
    
    /**
     * @description：根据目录编码或应用编码获取评论列表，返回成功列表页面
     * @time： 2015-11-17
     * 
     * @return 
     */
    public String getAllCommentsLists(){
        Document doc = new Document();
        /*if(comments!=null){
            doc = comments.formatDBObject();
        }*/
        if(comments!=null){
            if(!"".equals(comments.getInfoid())){
                Pattern pattern = Pattern.compile("^.*" + comments.getInfoid()+ ".*$", // as SQL:  like " '%" + personName + "%' "  
                Pattern.CASE_INSENSITIVE); //忽略大小写
                doc.put("infoid", pattern);
            }
            if(!"".equals(comments.getInfotitle())){
                Pattern pattern = Pattern.compile("^.*" + comments.getInfotitle()+ ".*$", // as SQL:  like " '%" + personName + "%' "  
                Pattern.CASE_INSENSITIVE); //忽略大小写
                doc.put("infotitle", pattern);
            }
            if(!"".equals(comments.getContent())){
                Pattern pattern = Pattern.compile("^.*" + comments.getContent()+ ".*$", // as SQL:  like " '%" + personName + "%' "  
                Pattern.CASE_INSENSITIVE); //忽略大小写
                doc.put("content", pattern);
            }
            if(!"".equals(comments.getStatus().trim())){
                doc.put("status", comments.getStatus().trim());
            }
            
        }
        doc.put("appid", this.getWebappcode());
        if(!this.getChannelCode().equals("null")){
            try {
                doc.put("dircode", java.net.URLDecoder.decode(this.getChannelCode(), "utf-8"));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        //加入分页
        PageUtil page = new PageUtil();
        int count = commentsDao.getCommentsCount(doc);
        page.setPageSize(pageSize);// 每页多少行
        page.setCurPage(pageIndex);//当前页
        page.setTotalRow(count);//总页数
        pageBar = page.getToolsMenu();
        Map pMap = new HashMap();
        pMap.put("start", page.getStart());
        pMap.put("end", pageSize);
        //加入日期段
        Document dateDbObject = new Document();
        if(q_createtime!=null && !"".equals(q_createtime.trim())){
            dateDbObject.put("$gte", q_createtime+" 00:00:00");
        }
        if(s_createtime!= null && !"".equals(s_createtime.trim())){
            dateDbObject.put("$lte", s_createtime+" 23:59:59");
        }
        if(dateDbObject.keySet().size()!=0){
            doc.put("createtime", dateDbObject);
        }
        
        List<Comments> commentsList = commentsDao.getCommentsList(doc,pMap);
        this.setCommentsList(commentsList) ;
        return "success";
    }
    
    /**
     * @description：根据评论id获取详细评论信息
     * @time： 2015-11-17
     * 
     * @param commentsId 评论id
     * @return 
     */
    public String getCommentsById(){
        String commentsId = request.getParameter("commentsId");
        comments = this.getCommentsDao().getComments(commentsId);
        return "success";
    }
    
    /**
     * @description：批量删除评论
     * @time： 2015-11-19
     *  
     * @return 
     */
    public String delCommentsByIds(){
        String commentsId = request.getParameter("commentsIds");
        this.getCommentsDao().delComments(commentsId);
        return "success";
    }
    
    /**
     * @description：批量审核评论
     * @time： 2015-11-19
     *  
     * @return 
     */
    public String checkCommentsByIds(){
        String commentsId = request.getParameter("commentsIds");
        this.getCommentsDao().checkComments(commentsId);
        return "success";
    }
    
    /**
     * @description：根据评论id获取待修改评论对象
     * @time： 2015-11-19
     * 
     * @return 
     */
    public String editCommentsById(){
        String commentsId = request.getParameter("commentsId");
        comments = this.getCommentsDao().getComments(commentsId);
        return "success";
    }
    
    /**
     * @description：保存修改的评论信息
     * @time： 2015-11-19
     *  
     */
    public String saveComments(){
        String commentsId = null;
        if(comments!=null){
            commentsId = comments.getId();
        }
        Comments nComments = new Comments();
        nComments = this.getCommentsDao().getComments(commentsId);
        nComments.setStatus(comments.getStatus());
        nComments.setContent(comments.getContent());
        this.getCommentsDao().saveComments(nComments);
        return "success";
    }
    
    public CommentsDao getCommentsDao() {
        return commentsDao;
    }

    public void setCommentsDao(CommentsDao commentsDao) {
        this.commentsDao = commentsDao;
    }

    public String getWebappcode() {
        if(webappcode==null || "".equals(webappcode)){
            webappcode = this.request.getParameter("webappCode");
        }
        return webappcode;
    }

    public void setWebappcode(String webappcode) {
        this.webappcode = webappcode;
    }

    public Comments getComments() {
        return comments;
    }

    public void setComments(Comments comments) {
        this.comments = comments;
    }

    public List<Comments> getCommentsList() {
        return commentsList;
    }

    public void setCommentsList(List<Comments> commentsList) {
        this.commentsList = commentsList;
    }

    public String getQ_createtime() {
        return q_createtime;
    }

    public void setQ_createtime(String q_createtime) {
        this.q_createtime = q_createtime;
    }

    public String getS_createtime() {
        return s_createtime;
    }

    public void setS_createtime(String s_createtime) {
        this.s_createtime = s_createtime;
    }

    public String getChannelCode() {
        if(channelCode==null || "".equals(channelCode)){
            channelCode = this.request.getParameter("channelCode");
        }
        return channelCode;
    }

    public void setChannelCode(String channelCode) {
        this.channelCode = channelCode;
    }

}
