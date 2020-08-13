/**  
 * @Title: DBUtil.java
 * @Package com.vcom.auxsys.util
 * @Description: TODO
 * @author 罗华森
 * @date 2015-11-13
 */
package com.vcom.auxsys.util;

import java.util.ArrayList;
import java.util.List;

import org.bson.BSONObject;
import org.bson.Document;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

/**
 * @title DBUtil
 * @description :
 * @author 罗华森
 * @date 2015-11-13下午12:10:43
 */
public class DBUtil {
    public List<Document>  parseDBList(List list){
    	List<MongoDBAdepter> inList = null;
    	try{
    		inList=(List<MongoDBAdepter>)list;
    	}catch(Exception e){
    		//out.println("参数类型错误！\r\n"+e.printStackTrace());
    	}
    	if(inList==null){
    		return null;
    	}
    	List<Document> dbList=new ArrayList();
    	for(MongoDBAdepter m : inList){ 
    		dbList.add(m.formatDBObject());
    	} 	
    	return dbList;
    }
    
    /**
     * 循环转化列表DB对象为实体对象列表方法
     * 
     * @param dlist
     * @param user
     * @return
     */
    public List formatVOList(List<Document> dlist,Class user){
    	List<MongoDBAdepter> vlist=new ArrayList<MongoDBAdepter>();
    	MongoDBAdepter m;
    	if(dlist==null){
    		return null;
    	}
    	for(Document db:dlist){
    		try {
				m=(MongoDBAdepter) user.newInstance();
				m.formatVObject(db);
	    		vlist.add(m);
			} catch (InstantiationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}  		
    	}
    	return vlist;
    }
}
