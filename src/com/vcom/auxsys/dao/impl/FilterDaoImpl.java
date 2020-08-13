/**  
 * @Title: FilterDaoImpl.java
 * @Package com.vcom.auxsys.dao.impl
 * @Description: TODO
 * @author 罗华森
 * @date 2015-11-18
 */
package com.vcom.auxsys.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.bson.Document;
import org.bson.types.ObjectId;

import zzvcom.sys.util.MongoManager;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.vcom.auxsys.dao.FilterDao;
import com.vcom.auxsys.entitys.pojo.FilterKey;

/**
 * @title FilterDaoImpl
 * @description :
 * @author 罗华森
 * @date 2015-11-18下午6:13:27
 */
public class FilterDaoImpl implements FilterDao {
    private MongoManager mongoManager;
	private MongoCollection<Document> collection;

	 public MongoManager getMongoManager() {
	        return mongoManager;
    }
    public void setMongoManager(MongoManager mongoManager) {
        this.mongoManager = mongoManager;
    }
	/* (non-Javadoc)
	 * @see com.vcom.auxsys.dao.FilterDao#add(com.vcom.auxsys.entitys.pojo.Filter)
	 */
	
	public String add(FilterKey f) {
		// TODO Auto-generated method stub
		String result="success";
		collection = this.getCollection();
		Document doc=new Document();
		doc.put("content", f.getContent());
		MongoCursor<Document> mongoCursor  = collection.find(doc).iterator();//查询操作
		if(mongoCursor.hasNext()){
			result="添加失败，关键词已存在！！！";
		}else{
			doc = f.formatDBObject();		
			collection.insertOne(doc);			
		}	
		return result;
	}
	/* (non-Javadoc)
	 * @see com.vcom.auxsys.dao.FilterDao#delete(java.util.List)
	 */
	
	public void delete(String[] list) {
		// TODO Auto-generated method stub
		collection = this.getCollection();
		Document doc=new Document();//待插入的数据
		for(String ids:list){		
            doc.put("_id", new ObjectId(ids));
			collection.deleteOne(doc);	    
   		    doc.clear();
		}
	}

	/* (non-Javadoc)
	 * @see com.vcom.auxsys.dao.FilterDao#edit(com.vcom.auxsys.entitys.pojo.Filter)
	 */
	
	public String edit(FilterKey f) {
		// TODO Auto-generated method stub
		String result="success";
		collection = this.getCollection();
		Document queryObj= new Document();//待修改的数据匹配记录
		//检查师傅重复
		queryObj.put("content",f.getContent());
		MongoCursor<Document> mongoCursor = collection.find(queryObj).iterator();//查询操作
		while(mongoCursor.hasNext()){
			Document db=mongoCursor.next();
			String id=String.valueOf(db.get("_id"));
			if(id!=null&&!id.equals(f.getId())){
				result="修改失败，过滤词名称已存在！！！";
				return result;
			}
		}
		//更新目标对象
		queryObj= new Document();//待修改的数据匹配记录
		queryObj.put("_id", new ObjectId(f.getId()));
		
		Document oriObj=collection.find(queryObj).first();
		oriObj.put("content",f.getContent());		
		collection.replaceOne(queryObj, oriObj);
		return result;
	}

	/* (non-Javadoc)
	 * @see com.vcom.auxsys.dao.FilterDao#get(java.lang.String)
	 */
	
	public FilterKey get(FilterKey f) {
		// TODO Auto-generated method stub
		collection = this.getCollection();
		Document doc=f.formatDBObject();//查询对象
		MongoCursor<Document> mongoCursor =this.collection.find(doc).iterator();
		FilterKey res=null;
		if(mongoCursor.hasNext()){
			res=new FilterKey();
			doc=mongoCursor.next();
			res.formatVObject(doc);
		}
		return res;
	}
	
	public List<FilterKey> queryFilter(FilterKey f) {
		// TODO Auto-generated method stub
		List<FilterKey> list=new ArrayList<FilterKey>();
		collection = this.getCollection();
		Document doc=new Document();//查询对象
		if(null!=f){
			if(!"".equals(f.getContent())){
                Pattern pattern = Pattern.compile("^.*" + f.getContent()+ ".*$", // as SQL:  like " '%" + personName + "%' "  
                Pattern.CASE_INSENSITIVE); //忽略大小写
                doc.put("content", pattern);
            }
			MongoCursor<Document> mongoCursor =this.collection.find(doc).iterator();
			FilterKey res=null;
			while(mongoCursor.hasNext()){
				res=new FilterKey();
				doc=mongoCursor.next();
				res.formatVObject(doc);
				list.add(res);
			}
		}		
		return list;
	}

	/* (non-Javadoc)
	 * @see com.vcom.auxsys.dao.FilterDao#list(java.util.List)
	 */
	
	public List list(List<String> list,Integer start,Integer size) {
		// TODO Auto-generated method stub
      ArrayList<FilterKey> flist=new ArrayList<FilterKey>();		
        collection = this.getCollection();
        if(null!=list){
        	Document doc=new Document();//待插入的数据
        	Document vd;
        	for(String id:list){
        		doc.put("_id", new ObjectId(id));
        		vd=collection.find(doc).first();
        		FilterKey v=new FilterKey();
        		v.formatVObject(vd);
        		flist.add(v);
        		doc.clear();       		
        	}
        }else{
        	Document order=new Document();
        	order.put("createtime", -1);
        	FindIterable<Document> iterable = collection.find().sort(order);//查询操作
        	
            if(start!=null && size!=null){
            	iterable.skip(start).limit(size);
            }
            MongoCursor<Document> mongoCursor = iterable.iterator();
        	
            while(mongoCursor.hasNext()){//遍历输出
            	Document dbObject = mongoCursor.next();
                FilterKey v=new FilterKey();
                v.formatVObject(dbObject);
                flist.add(v);
            }
        }      
        return flist;
	}

	/**
	 * @return the collection
	 */
	public MongoCollection<Document> getCollection() {
		MongoDatabase auxsysDb =MongoManager.getDb();
		collection = auxsysDb.getCollection("auxsys_filter");
		return collection;
	}

	/**
	 * @param collection the collection to set
	 */
	public void setCollection(MongoCollection<Document> collection) {
		this.collection = collection;
	}
	/**
	 * @Description: 获取数量
	 * @author 罗华森
	 * @date 2015-12-11
	 */
	public int getCount(){
        int count = 0;
		MongoCursor<Document> mongoCursor = this.getCollection().find().iterator();//查询操作
		while(mongoCursor.hasNext()){
			mongoCursor.next();
			count++;
		}	

        return count;
    }
}
