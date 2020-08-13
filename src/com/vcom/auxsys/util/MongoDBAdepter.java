/**  
 * @Title: MongoDBAdepter.java
 * @Package com.vcom.auxsys.entitys.pojo
 * @Description: TODO
 * @author 罗华森
 * @date 2015-11-13
 */
package com.vcom.auxsys.util;

import org.bson.Document;

import com.mongodb.DBObject;

/**
 * @title MongoDBAdepter 实体对象 适配mongoDB数据接口 
 * @description :
 * @author 罗华森
 * @date 2015-11-13上午11:30:48
 */
public interface MongoDBAdepter {
	//将当前对象转化为com.mongodb.DBObject
	public Document formatDBObject();
	//将DB对象转化为实体对象
	public void formatVObject(Document db);
	//更新数据库对象
}
