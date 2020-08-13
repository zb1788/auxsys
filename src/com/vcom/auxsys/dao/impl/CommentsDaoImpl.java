package com.vcom.auxsys.dao.impl;

import java.awt.geom.Area;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.bson.Document;
import org.bson.types.ObjectId;

import zzvcom.sys.util.MongoManager;

import com.mongodb.BasicDBObject;
import com.mongodb.Block;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.vcom.auxsys.dao.CommentsDao;
import com.vcom.auxsys.entitys.pojo.Comments;
import com.vcom.auxsys.util.CommonUtil;
import com.vcom.auxsys.util.Globals;

/**
 * @description： 评论实现类
 * @time： 2015-11-11
 * 
 */
public class CommentsDaoImpl implements CommentsDao {
    
    private MongoManager mongoManager;
    private  MongoCollection<Document> commentsCollection;

    public MongoManager getMongoManager() {
        return mongoManager;
    }
    public void setMongoManager(MongoManager mongoManager) {
        this.mongoManager = mongoManager;
    }
    /**
     * @description： 保存评论
     * @time： 2015-11-11
     * 
     * @param comments 评论对象
     */
    public void saveComments(Comments comments) {
        //查询
    	Document queryObj=new Document();
        queryObj.put("_id", new ObjectId(comments.getId()));
        
        Document upDbObject = new Document();
        upDbObject = comments.formatDBObject();
        this.getCommentsCollection().replaceOne(queryObj, upDbObject); 
    }

    /**
     * @description：根据评论id获取详细评论信息
     * @time： 2015-11-11
     * 
     * @param commentsid 评论id
     * @return 
     */
    public Comments getComments(String commentsid) {
        Document doc = new Document();
        final Comments comments = new Comments();
        doc.put("_id", new ObjectId(commentsid));
        FindIterable<Document> iterable = commentsCollection.find(doc);//查询操作
        
        //DBCursor dbCursor = this.getCommentsCollection().find(doc);//查询操作
       
//        while(dbCursor.hasNext()){//遍历输出
//            DBObject dbObject = dbCursor.next();
//            comments.formatVObject(dbObject);
//        }
        iterable.forEach(new Block<Document>() {
            @Override
            public void apply(final Document document) {
                System.out.println(document);
                comments.formatVObject(document);

            }
        });       
        
        return comments;
    }

    /**
     * @description：批量删除评论
     * 2019-02-18 支持无,的单个删除
     * @time： 2015-11-11
     * 
     * @param commentsids  评论ids
     */
    public void delComments(String commentsids) {
    	if(commentsids!=null && commentsids.indexOf(",")>1) {
	        String[] ids = commentsids.split(",");
	        Document doc=new Document();
	        Document in = new Document();
	    	ArrayList<ObjectId> idList = new ArrayList<ObjectId>();
	        for(int i = 0;i<ids.length;i++){ 
	        	idList.add(new ObjectId(ids[i].trim()));   
	        }
	        in.put("$in", idList);
	        doc.put("_id", in);
	        this.getCommentsCollection().deleteMany(doc);
    	}else if(commentsids!=null){
        	Document doc=new Document();
        	doc.put("_id", new ObjectId(commentsids.trim()));
	        this.getCommentsCollection().deleteMany(doc);
    	}
    }
    
    /**
     * @description:插入评论
     * id回写入comments对象
     * @time： 2015-11-23
     * 
     * @param comments 
     */
    public void insertComments(Comments comments){
    	Document documents = comments.formatDBObject();
        this.getCommentsCollection().insertOne(documents);
        ObjectId id = (ObjectId)documents.get( "_id" );
        comments.setId(id.toString());
    }

    /**
     * @description 批量审核评论
     * @time： 2015-11-11
     * 
     * @param commentsids 评论ids
     */
    public void checkComments(String commentsids) {
        String[] ids = commentsids.split(",");
        for(String id:ids){ 
        	Document queryObj=new Document();
            queryObj.put("_id", new ObjectId(id.trim()));
            
            Comments comments = this.getComments(id.trim());
            comments.setStatus(String.valueOf(Globals.STATUS_YS));
            Document upDbObject = new Document();
            upDbObject = comments.formatDBObject();
            this.getCommentsCollection().replaceOne(queryObj, upDbObject);     
        }
    }

    /**
     * @description：获取评论列表
     * @time： 2015-11-11
     * 
     * @param doc 查询参数
     * @param pMap 分页map
     * @return 返回评论列表
     */
    public List<Comments> getCommentsList(Document doc,Map pMap) {
        final List<Comments> commentsList = new ArrayList<Comments>();
        FindIterable<Document> iterable = null;
        Document orderBy = new Document();//排列对象
        orderBy.put("createtime", -1);//加入排列字段名称（1-升序 -1降序）
        iterable = this.getCommentsCollection().find(doc).sort(orderBy);//查询操作
        if(pMap.containsKey("start")&&pMap.containsKey("end")){
            Integer start = Integer.valueOf(String.valueOf(pMap.get("start")));
            Integer end = Integer.valueOf(String.valueOf(pMap.get("end")));
            iterable.skip(start).limit(end);
        }
        iterable.forEach(new Block<Document>() { //遍历输出
            @Override
            public void apply(final Document document) {
            	
                  Comments comments = new Comments();
                  comments.formatVObject(document);
                  commentsList.add(comments);

            }
        }); 
       
        return commentsList;
    }
    
    /**
     * @description：根据查询条件获取评论总条数
     * @time： 2015-11-19
     * 
     * @param doc
     * @return 
     */
    public int getCommentsCount(Document doc){
        FindIterable<Document> iterable = null;
        if(doc != null){
        	iterable = this.getCommentsCollection().find(doc);//查询操作
        }else{
        	iterable =  this.getCommentsCollection().find();//查询操作
        }
        int count = 0;
		MongoCursor<Document> mongoCursor = iterable.iterator();//查询操作
		while(mongoCursor.hasNext()){
			mongoCursor.next();
			count++;
		}	
        return count;
    }
    
    /**
     * @description：根据请求参数查询评论列表幷返回json形式字符串
     * @time： 2015-11-23
     * 
     * @param map 
     * @param start
     * @param limit
     * @return 
     */
    public String getCommentsPageByMap(LinkedHashMap map, int start, int limit) {
        String text = null;
        Document doc = new Document();
        String appid = String.valueOf(map.get("moduleid"));
        doc.put("appid", appid);
        String infoid = String.valueOf(map.get("infoid"));
        doc.put("infoid", infoid);
        //若包含目录编码则加入区域查询条件
        if(map.containsKey("code")){
            String dircode = String.valueOf(map.get("code"));
            doc.put("dircode", dircode);
        }
        //若包含区域则加入区域查询条件
        if(map.containsKey("area")){
            String area = String.valueOf(map.get("area"));
            doc.put("area", area);
        }
        
        doc.put("status", "1");
        Map pMap = new HashMap();
        if((start-1)*limit<0)
        	start=0;
        else
        	start = (start-1)*limit;
        pMap.put("start", start);
        pMap.put("end", limit);
        List<Comments> commentsList = this.getCommentsList(doc, pMap);
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for(int i=0;i<commentsList.size();i++){
            Comments comments = commentsList.get(i);
            if(i>0){
                sb.append(",");
            }
            sb.append("{\"id\":\"" + comments.getId()+"\",\"appid\":\""+comments.getAppid()+"\",\"dircode\":\""+CommonUtil.getJsonHtml(comments.getDircode())+"\",\"dirname\":\""+CommonUtil.getJsonHtml(comments.getDirname())+"\"");
            sb.append(",\"infoid\":\""+comments.getInfoid()+"\",\"infotitle\":\""+CommonUtil.getJsonHtml(comments.getInfotitle())+"\",\"userid\":\""+comments.getUserid()+"\",\"username\":\""+CommonUtil.getJsonHtml(comments.getUsername())+"\",\"area\":\""+CommonUtil.getJsonHtml(comments.getArea())+"\"");
            sb.append(",\"ip\":\""+comments.getIp()+"\",\"content\":\""+CommonUtil.getJsonHtml(comments.getContent())+"\",\"postdate\":\""+comments.getCreatetime()+"\",\"status\":\""+comments.getStatus()+"\",\"point\":\""+comments.getPoint()+"\",\"remark1\":\""+CommonUtil.getJsonHtml(comments.getRemark1())+"\",\"remark2\":\""+CommonUtil.getJsonHtml(comments.getRemark2())+"\"");
            sb.append("}");
        }
        sb.append("]");
        int totalrow = this.getCommentsCount(doc);
        text = "\"totalRow\":\""+totalrow+"\",\"commets\": " + sb.toString();
        return text;
    }
    
    /**
     * @description：更新评论打分
     * @time： 2016-3-3
     * @author：donghaoyu
     * @param commentId 评论id
     * @return 
     */
    public String updateCommentPoint(String commentId){
        String text = null;
        Document queryObj = new Document();
        try{
            queryObj.put("_id", new ObjectId(commentId));
        }catch (Exception e) {
            text = "{\"totalPoint\":\""+0+"\",\"message\":'请求失败，请求的评论数据不存在，请检查！',\"status\":0}";
        }
        FindIterable<Document> iterable = this.getCommentsCollection().find(queryObj);
       
        if(!iterable.iterator().hasNext()){
            text = "{\"totalPoint\":\""+0+"\",\"message\":'请求失败，请求的评论数据不存在，请检查！',\"status\":0}";
        }else{
        	Document updateObj = new Document();
            updateObj =  iterable.iterator().next();
            String point = "0";
            if(updateObj.get("point")!=null){
                point = String.valueOf(Integer.valueOf(updateObj.get("point").toString())+1);
            }else{
                point = "1";
            }
            updateObj.put("point", point);
            this.getCommentsCollection().replaceOne(queryObj, updateObj);
            text = "{\"totalPoint\":\""+point+"\",\"message\":'请求成功！',\"status\":1}";
        }
        return text;
    }


    public  MongoCollection<Document> getCommentsCollection() {
		MongoDatabase auxsysDb = MongoManager.getDb();
        commentsCollection =  auxsysDb.getCollection("auxsys_comments");
        return commentsCollection;
    }

    public void setCommentsCollection(MongoCollection<Document> commentsCollection) {
        this.commentsCollection = commentsCollection;
    }

}
