/**  
 * @Title: updateDBbyVO.java
 * @Description: TODO
 * @author 罗华森
 * @date 2015-11-19
 */
package com.vcom.auxsys.util;

import org.bson.Document;

/**
 * @title updateDBbyVO
 * @description :
 * @author 罗华森
 * @date 2015-11-19下午4:26:30
 */
public interface updateDBbyVO {
	public void updateDB(Document db);//更新数据到DBObject对象
}
