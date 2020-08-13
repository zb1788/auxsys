/**  
 * @Title: voteOption.java
 * @Package com.vcom.auxsys.entitys.pojo
 * @date 2015-11-12
 */
package com.vcom.auxsys.entitys.pojo;

import org.bson.Document;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.vcom.auxsys.util.MongoDBAdepter;

/**
 * @title voteOption
 * @description 投票选项
 * @date 2015-11-12下午12:13:38
 */
public class VoteOption implements MongoDBAdepter{
   private String optionId;//选项编号
   private String optionName;//选项名称
   private String optionRemark;//选项备注
   private String optionOrder;//选项序号
   private String results;//投票数
/**
 * @return the optionId
 */
public String getOptionId() {
	return optionId;
}
/**
 * @param optionId the optionId to set
 */
public void setOptionId(String optionId) {
	this.optionId = optionId;
}
/**
 * @return the optionName
 */
public String getOptionName() {
	return optionName;
}
/**
 * @param optionName the optionName to set
 */
public void setOptionName(String optionName) {
	this.optionName = optionName;
}
/**
 * @return the optionRemark
 */
public String getOptionRemark() {
	return optionRemark;
}
/**
 * @param optionRemark the optionRemark to set
 */
public void setOptionRemark(String optionRemark) {
	this.optionRemark = optionRemark;
}
/**
 * @return the optionOrder
 */
public String getOptionOrder() {
	return optionOrder;
}
/**
 * @param optionOrder the optionOrder to set
 */
public void setOptionOrder(String optionOrder) {
	this.optionOrder = optionOrder;
}
/* (non-Javadoc)
 * @see com.vcom.auxsys.util.MongoDBAdepter#formatDBObject()
 */

public Document formatDBObject() {
	// TODO Auto-generated method stub
	Document doc=new Document();//待插入的数据
	if(!(null==optionId||"".equals(optionId.trim())))
		doc.put("optionid", this.getOptionId());
	if(!(null==optionName||"".equals(optionName.trim())))
		doc.put("optionname", this.getOptionName());
	if(!(null==optionOrder||"".equals(optionOrder.trim())))
		doc.put("optionorder", this.getOptionOrder());
	if(!(null==optionRemark||"".equals(optionRemark.trim())))
		doc.put("optionremark", this.getOptionRemark());
	if(!(null==results||"".equals(results.trim())))
		doc.put("results", this.getResults());
	return doc;
}


/* (non-Javadoc)
 * @see com.vcom.auxsys.util.MongoDBAdepter#formatVObject()
 */

public void formatVObject(Document db) {
	// TODO Auto-generated method stub
	if(null!=db.get("optionid"))
		this.setOptionId(String.valueOf(db.get("optionid")));
	if(null!=db.get("optionname"))
		this.setOptionName(String.valueOf(db.get("optionname")));
	if(null!=db.get("optionorder"))
		this.setOptionOrder(String.valueOf(db.get("optionorder")));
	if(null!=db.get("optionremark"))
		this.setOptionRemark(String.valueOf(db.get("optionremark")));
	if(null!=db.get("results"))
		this.setResults(String.valueOf(db.get("results")));
}
public String getResults() {
    return results;
}
public void setResults(String results) {
    this.results = results;
}


}
