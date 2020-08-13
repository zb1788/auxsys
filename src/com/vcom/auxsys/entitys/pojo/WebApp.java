package com.vcom.auxsys.entitys.pojo;

import org.bson.Document;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.vcom.auxsys.util.MongoDBAdepter;
import com.vcom.auxsys.util.updateDBbyVO;

/**
 * @description： 应用实体类
 */
public class WebApp implements MongoDBAdepter,updateDBbyVO{
    
    private String id; //应用或目录id
    private String name; //应用或目录名称
    private String remark;//应用或目录备注
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    /**
     * @description：将webapp转化成dbobject
     * @time： 2015-11-18
     * @return 
     */
    public Document formatDBObject() {
    	Document doc=new Document();
        if(!(null==id||"".equals(id.trim())))
        	doc.put("id", id.trim());
        if(!(null==name||"".equals(name.trim())))
        	doc.put("name", name.trim());
        if(!(null==remark||"".equals(remark.trim())))
        	doc.put("remark", remark.trim());
        return doc;
    }
    /**
     * @description：将dbobject转化成webapp对象
     * @time： 2015-11-18
     * @param dbObject 
     */
    public void formatVObject(Document dbObject) {
    	if(null!=dbObject.get("id"))
    		this.setId(String.valueOf(dbObject.get("id")));
    	if(null!=dbObject.get("name"))
    		this.setName(String.valueOf(dbObject.get("name")));
    	if(null!=dbObject.get("remark"))
    		this.setRemark(String.valueOf(dbObject.get("remark"))); 
    }
	/**
	 * @return the remark
	 */
	public String getRemark() {
		return remark;
	}
	/**
	 * @param remark the remark to set
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}
	/* (non-Javadoc)
	 * @see com.vcom.auxsys.util.updateDBbyVO#updateDB(com.mongodb.DBObject)
	 */
	public void updateDB(Document db) {
		// TODO Auto-generated method stub
		if(!(null==id||"".equals(id.trim())))
        	db.put("id", this.getId());
		if(!(null==name||"".equals(name.trim())))
			db.put("name", name);
		if(!(null==remark||"".equals(remark.trim())))
			db.put("remark", remark);
	}
}
