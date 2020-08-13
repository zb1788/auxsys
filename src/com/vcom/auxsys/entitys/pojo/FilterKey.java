/**  
 * @Title: Filter.java
 * @Package com.vcom.auxsys.entitys.pojo
 * @date 2015-11-18
 */
package com.vcom.auxsys.entitys.pojo;

import org.bson.Document;
import org.bson.types.ObjectId;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.vcom.auxsys.util.MongoDBAdepter;

/**
 * @title Filter
 * @description :过滤词
 * @date 2015-11-18下午5:56:30
 */
public class FilterKey implements MongoDBAdepter {
	private String id=null;//id编号
	private String content=null;//过滤词内容
	private String createtime=null;//创建时间

	/* (non-Javadoc)
	 * @see com.vcom.auxsys.util.MongoDBAdepter#formatDBObject()
	 */
	public Document formatDBObject() {
		// TODO Auto-generated method stub
		Document doc=new Document();//待插入的数据
		if(!(null==id||"".equals(id.trim())))
			doc.put("_id", new ObjectId(id.trim()));	
		if(!(null==content||"".equals(content.trim())))
		    doc.put("content", content.trim());
		if(!(null==createtime||"".equals(createtime.trim())))
		    doc.put("createtime",createtime.trim());	
		return doc;
	}

	/* (non-Javadoc)
	 * @see com.vcom.auxsys.util.MongoDBAdepter#formatVObject(com.mongodb.DBObject)
	 */
	public void formatVObject(Document db) {
		// TODO Auto-generated method stub
		this.setId(String.valueOf(db.get("_id")));
		if(null!=db.get("content"))
			this.setContent(String.valueOf(db.get("content")));
		if(null!=db.get("createtime"))
			this.setCreatetime(String.valueOf(db.get("createtime")));
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the content
	 */
	public String getContent() {
		return content;
	}

	/**
	 * @param content the content to set
	 */
	public void setContent(String content) {
		this.content = content;
	}

	/**
	 * @return the createtime
	 */
	public String getCreatetime() {
		return createtime;
	}

	/**
	 * @param createtime the createtime to set
	 */
	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}

}
