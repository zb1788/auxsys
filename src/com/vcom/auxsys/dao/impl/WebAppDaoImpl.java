package com.vcom.auxsys.dao.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;

import org.bson.Document;

import zzvcom.sys.util.MongoManager;

import com.mongodb.AggregationOutput;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.client.AggregateIterable;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.vcom.auxsys.dao.WebAppDao;
import com.vcom.auxsys.entitys.pojo.Comments;
import com.vcom.auxsys.entitys.pojo.WebApp;

public class WebAppDaoImpl implements WebAppDao {
    
	private MongoManager mongoManager;//数据库连接池
    private  MongoCollection<Document> appCollection;//
    private  MongoCollection<Document> voteResultCollection;
    private  MongoCollection<Document> commentsCollection;
    
    
    public MongoManager getMongoManager() {
        return mongoManager;
    }

    public void setMongoManager(MongoManager mongoManager) {
        this.mongoManager = mongoManager;
    }
    
    /**
     * @Description: 获取当前最大id
     * @author 罗华森
     * @date 2015-11-19
     */
    public String getMaxId(){
    	String max="0";
    	MongoCursor<Document> mongoCursor = this.getAppCollection().find().sort(new Document("id",-1)).limit(1).iterator();//查询操作
        //Document doc = new Document();
       
        if(mongoCursor.hasNext())
        {
        	Object obj = mongoCursor.next().get("id");
        	if(obj!=null)
        	{
        		max = String.valueOf(obj);
        		max = String.valueOf(Integer.parseInt(max)+1);
        	}
        }

        return max;
    }
    /**
     * @description：获取应用列表
     * @time： 2015-11-12
     * @author：donghaoyu
     * @return 
     */
    public List<WebApp> getWebAppList(Integer start,Integer size) {
        List<WebApp> appList=new ArrayList<WebApp>();
        Document order=new Document();
    	order.put("id", 1);
    	FindIterable<Document> iterable = this.getAppCollection().find().sort(order);//查询操作
    	
        if(start!=null && size!=null){
        	iterable.skip(start).limit(size);
        }
        MongoCursor<Document> mongoCursor = iterable.iterator();
        while(mongoCursor.hasNext()){//遍历输出
            Document doc = mongoCursor.next();
            WebApp app = new WebApp();
            app.formatVObject(doc);
            appList.add(app);
        }
        return appList;
    }

    /**
     * @description：根据应用id获取应用对象
     * @time： 2015-11-12
     * @author：donghaoyu
     * @param appId 应用id
     * @return 
     */
    public WebApp getWebAppById(String appId) {
        List<WebApp> appList=new ArrayList<WebApp>();
        Document doc = new Document();
        doc.put("id", appId);
        MongoCursor<Document> mongoCursor  = this.getAppCollection().find(doc).iterator();//查询操作
        WebApp app = null;
        while(mongoCursor.hasNext()){//遍历输出
        	Document dbObject = mongoCursor.next();
            app = new WebApp();
            app.formatVObject(dbObject);
        }
        return app;
    }
    /* (non-Javadoc)
     * @see com.vcom.auxsys.dao.WebAppDao#getWebAppByName(java.lang.String)
     */
    public List<WebApp> queryWebApp(WebApp wa){
    	List<WebApp> wlist=new ArrayList<WebApp>();
    	Document doc = new Document();
    	if(null!=wa){
    		if(!"".equals(wa.getName())){
                Pattern pattern = Pattern.compile("^.*" + wa.getName()+ ".*$", // as SQL:  like " '%" + personName + "%' "  
                Pattern.CASE_INSENSITIVE); //忽略大小写
                doc.put("name", pattern);
            }
    		MongoCursor<Document> mongoCursor = this.getAppCollection().find(doc).iterator();//查询操作
            WebApp app = null;
            while(mongoCursor.hasNext()){//遍历输出
                doc = mongoCursor.next();
                app=new WebApp();
                app.formatVObject(doc);
                wlist.add(app);
            }
    	}        
        return wlist;
    }
    /**
     * @description：检查提价评论项是否完整
     * @time： 2015-11-23
     * @author：donghaoyu
     * @param comments
     * @return 
     */
    public String checkAppSfcz(Comments comments){
        String message = "";
        //应用id是否匹配
        WebApp webApp = this.getWebAppById(comments.getAppid());
        if(webApp==null){
            message+="提交的应用id在评论系统中没有匹配，请添加已有的应用id或在评论系统中建立相关应用";
        }
        return message;
    }
    
    /**
     * @description：根据应用id从投票结果集合中聚合目录列表
     * @time： 2015-11-12
     * @author：donghaoyu
     * @param appId 应用id
     * @return 
     */
    public List<WebApp> getDirListByVote(String appId,String voteid){
        List<WebApp> dirList=new ArrayList<WebApp>();
        Document doc = new Document();
        doc.put("appid", appId);
        doc.put("voteid", voteid);
        Document groupFields = new Document(); 
        groupFields.put("_id", "$dircode");
        groupFields.put("dircode", new Document("$push","$dircode"));
        groupFields.put("dirname", new Document("$push","$dirname")); 
        Document group = new Document("$group",groupFields );
        Document match = new Document("$match",doc);
        List<Document> dbObjects = new ArrayList<Document>();
        dbObjects.add(match);
        dbObjects.add(group);
        AggregateIterable<Document> output = this.getVoteResultCollection().aggregate(dbObjects); //聚合查询
        MongoCursor<Document> list_iterator = output.iterator();
        //MongoCursor<Document> list_iterator = list_iterable.iterator();
        while(list_iterator.hasNext()){//遍历输出
        	Document dbObject = list_iterator.next();
            List dircode = (List) dbObject.get("dircode");
            List dirname = (List) dbObject.get("dirname");
            WebApp app = new WebApp();
            app.setId(String.valueOf(dircode.get(0)));
            app.setName(String.valueOf(dirname.get(dirname.size()-1)));
            //如果目录编码为空则默认设置为-10010
            /*if(dircode.get(0)!=null && !"".equals(dircode.get(0))){
                app.setId(String.valueOf(dircode.get(0)));
            }else{
                app.setId("-10010");
            }
            //如果目录名称为空且  目录编码不为空则设置为目录编码，否则默认为默认目录01
            if(dirname.get(0)!=null && !"".equals(dirname.get(0))){
                app.setName(String.valueOf(dirname.get(0)));
            }else{
                if(dircode.get(0)!=null && !"".equals(dircode.get(0))){
                    app.setName(String.valueOf(dircode.get(0))); 
                }else{
                    app.setName("默认目录01"); 
                }
            }*/
            
            dirList.add(app);
        }
        //List dblisList = this.getVoteResultCollection().distinct("dircode", doc);//查询操作
        return dirList;
    }
    
    /**
     * @description：根据应用id从评价集合中聚合目录列表
     * @time： 2015-11-12
     * @author：donghaoyu
     * @param appId 应用id
     * @return  
     */
    public List<WebApp> getDirListByComments(String appId){
        List<WebApp> dirList=new ArrayList<WebApp>();
        Document doc = new Document();
        doc.put("appid", appId);
        Document sortDbObject = new Document();
        sortDbObject.put("createtime", -1);
        Document groupFields = new Document(); 
        groupFields.put("_id", "$dircode");
        groupFields.put("dircode", new Document("$push","$dircode"));
        groupFields.put("dirname", new Document("$push","$dirname")); 
        Document group = new Document("$group",groupFields );
        Document match = new Document("$match",doc);
        Document sort = new Document("$sort",sortDbObject);
        List<Document> dbObjects = new ArrayList<Document>();
        dbObjects.add(match);
        dbObjects.add(sort);
        dbObjects.add(group);
        AggregateIterable<Document> output = this.getCommentsCollection().aggregate(dbObjects); //聚合查询
        MongoCursor<Document> list_iterator = output.iterator();

        while(list_iterator.hasNext()){//遍历输出
        	Document dbObject = list_iterator.next();
            List dircode = (List) dbObject.get("dircode");
            List dirname = (List) dbObject.get("dirname");
            WebApp app = new WebApp();
            app.setId(String.valueOf(dircode.get(0)));
            app.setName(String.valueOf(dirname.get(0)));
          //如果目录编码为空则默认设置为-10010
            /*if(dircode.get(0)!=null && !"".equals(dircode.get(0))){
                app.setId(String.valueOf(dircode.get(0)));
            }else{
                app.setId("-10010");
            }
            //如果目录名称为空且  目录编码不为空则设置为目录编码，否则默认为默认目录01
            if(dirname.get(0)!=null && !"".equals(dirname.get(0))){
                app.setName(String.valueOf(dirname.get(0)));
            }else{
                if(dircode.get(0)!=null && !"".equals(dircode.get(0))){
                    app.setName(String.valueOf(dircode.get(0))); 
                }else{
                    app.setName("默认目录01"); 
                }
            }*/
            dirList.add(app);
        }
        return dirList;
    }

    public MongoCollection<Document> getCommentsCollection() {
        MongoDatabase auxsysDb = MongoManager.getDb();
        commentsCollection = auxsysDb.getCollection("auxsys_comments");
        return commentsCollection;
    }

    public void setCommentsCollection(MongoCollection<Document> commentsCollection) {
        this.commentsCollection = commentsCollection;
    }

    public  MongoCollection<Document> getAppCollection() {
		MongoDatabase auxsysDb = MongoManager.getDb();
        appCollection = auxsysDb.getCollection("auxsys_app");
        return appCollection;
    }

    public void setAppCollection(MongoCollection<Document> appCollection) {
        this.appCollection = appCollection;
    }

	/* (non-Javadoc)
	 * @see com.vcom.auxsys.dao.WebAppDao#add(com.vcom.auxsys.entitys.pojo.WebApp)
	 */
	
	public String add(WebApp wa) {
		// TODO Auto-generated method stub
		this.appCollection=this.getAppCollection();
		String result="success";
		Document doc=new Document();
		doc.put("name", wa.getName());
		MongoCursor<Document> mongoCursor = appCollection.find(doc).iterator();//查询操作
		if(mongoCursor.hasNext()){
			result="添加失败，应用模块名称已存在！！！";
		}else{
			wa.setId(getMaxId());
			doc = wa.formatDBObject();		
			appCollection.insertOne(doc);			
		}	
		return result;
	}

	/* (non-Javadoc)
	 * @see com.vcom.auxsys.dao.WebAppDao#remove(java.lang.String[])
	 */
	
	public void remove(String[] ids) {
		// TODO Auto-generated method stub
		this.appCollection=this.getAppCollection();
		Document doc=new Document();
		for(String id:ids){			
			doc.put("id", id);
			this.appCollection.deleteOne(doc);
		}
	}

	/* (non-Javadoc)
	 * @see com.vcom.auxsys.dao.WebAppDao#edit(com.vcom.auxsys.entitys.pojo.WebApp)
	 */
	
	public String edit(WebApp wa) {
		// TODO Auto-generated method stub
		String result="success";
		this.appCollection=this.getAppCollection();
		Document doc=new Document();
		doc.put("name", wa.getName());
		MongoCursor<Document> mongoCursor = appCollection.find(doc).iterator();//查询操作
		if(mongoCursor.hasNext()){
			Document db=mongoCursor.next();
			String id=String.valueOf(db.get("id"));
			if(id!=null&&!id.equals(wa.getId())){
				result="添加失败，应用模块名称已存在！！！";
				return result;
			}
		}	
		doc=new Document();
		doc.put("id",wa.getId());
		Document rdoc=this.appCollection.find(doc).first();
		wa.updateDB(rdoc);
		this.appCollection.replaceOne(doc, rdoc);	
		return result;
	}
    public MongoCollection<Document> getVoteResultCollection() {
    	
		MongoDatabase auxsysDb = MongoManager.getDb();
        voteResultCollection = auxsysDb.getCollection("auxsys_result");
        return voteResultCollection;
    }
    public void setVoteResultCollection(MongoCollection<Document> voteResultCollection) {
        this.voteResultCollection = voteResultCollection;
    }
    /**
     * @Description: 获取资源数量
     * @author 罗华森
     * @date 2015-12-11
     */
    public int getCount(){
    	int count = 0;
		MongoCursor<Document> mongoCursor = this.getAppCollection().find().iterator();//查询操作
		while(mongoCursor.hasNext()){
			mongoCursor.next();
			count++;
		}	

        return count;
    }
}
