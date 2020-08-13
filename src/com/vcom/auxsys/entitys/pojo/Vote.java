/**  
 * @Title: Vote.java
 * @Package com.vcom.auxsys.entitys.pojo
 * @date 2015-11-12
 */
package com.vcom.auxsys.entitys.pojo;

import java.util.*;

import org.bson.BSONObject;
import org.bson.Document;
import org.bson.types.ObjectId;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.vcom.auxsys.util.DBUtil;
import com.vcom.auxsys.util.MongoDBAdepter;

/**
 * @title Vote
 * @description :投票定义实体类
 * @date 2015-11-12下午12:05:53
 */
public class Vote implements MongoDBAdepter{
	private String id; //id
	private String title;//名称
	private String createtime;//
	private String status;//
	private String remark;//备注
	//类型
	//1:无限制 ;
	//2点赞<!-- 每项只能投一次（可取消） 没有每天 -->;
	//3:每天可以给任意项投几票<!-- 比如可以每天给任何一个人投 1/5票-->;
	//4:每天可以给最多几项投几票<!-- 比如可以每天给最多几个人投 1/5票-->
	//5:每天投几票<!-- 每天投10票（可以全投给一个人，也可以投给其他人，只能投10次）-->
	private String type;
	private String filterdate;//是否合适起始时间1设置，0不设置
	private String starttime;//投票开始时间
	private String endtime;//投票结束时间
	private String filtertype;//1用户过滤2ip过滤
	private String votecountlimit;//用户投票次数
	private String votetermlimit;//可以投机项
	

	private List<VoteOption> options;//
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
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}
	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
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
	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}
	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
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
	/**
	 * @return the opptions
	 */
	public List<VoteOption> getOptions() {
		return options;
	}
	/**
	 * @param opptions the opptions to set
	 */
	public void setOptions(List<VoteOption> opptions) {
		this.options = opptions;
	}
	
    public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getStarttime() {
		return starttime;
	}
	public void setStarttime(String starttime) {
		this.starttime = starttime;
	}
	public String getEndtime() {
		return endtime;
	}
	public void setEndtime(String endtime) {
		this.endtime = endtime;
	}
	public String getFiltertype() {
		return filtertype;
	}
	public void setFiltertype(String filtertype) {
		this.filtertype = filtertype;
	}
	public String getVotecountlimit() {
		return votecountlimit;
	}
	public void setVotecountlimit(String votecountlimit) {
		this.votecountlimit = votecountlimit;
	}
	public String getVotetermlimit() {
		return votetermlimit;
	}
	public void setVotetermlimit(String votetermlimit) {
		this.votetermlimit = votetermlimit;
	}	

	public String getFilterdate() {
		return filterdate;
	}
	public void setFilterdate(String filterdate) {
		this.filterdate = filterdate;
	}
	
	public Document formatDBObject() {
		// TODO Auto-generated method stub	
		DBUtil dbu=new DBUtil();
		Document doc=new Document();//待插入的数据
		if(!(null==id||"".equals(id.trim())))
			doc.put("_id", new ObjectId(id));
		if(!(null==title||"".equals(title.trim())))
		    doc.put("title", this.getTitle());
		if(!(null==createtime||"".equals(createtime.trim())))
		    doc.put("createtime", this.getCreatetime());
		if(!(null==status||"".equals(status.trim())))
		    doc.put("status", this.getStatus());
		if(!(null==remark||"".equals(remark.trim())))
		    doc.put("remark", this.getRemark());
		if(!(null==options))
		    doc.put("options",dbu.parseDBList(options) );
		if(!(null==type||"".equals(type.trim())))
		    doc.put("type", this.getType());
		if(!(null==filterdate||"".equals(filterdate.trim()))){
			doc.put("filterdate",this.getFilterdate());
		}
		if(!(null==starttime||"".equals(starttime.trim())))
		    doc.put("starttime", this.getStarttime());
		if(!(null==endtime||"".equals(endtime.trim())))
		    doc.put("endtime", this.getEndtime());
		if(!(null==filtertype||"".equals(filtertype.trim())))
		    doc.put("filtertype", this.getFiltertype());
		if(!(null==votecountlimit||"".equals(votecountlimit.trim())))
		    doc.put("votecountlimit", this.getVotecountlimit());
		if(!(null==votetermlimit||"".equals(votetermlimit.trim())))
		    doc.put("votetermlimit", this.getVotetermlimit());
		
		
		return doc;
	}
	/* (non-Javadoc)
	 * @see com.vcom.auxsys.util.MongoDBAdepter#formatVObject()
	 */
	
	public void formatVObject(Document db) {
		// TODO Auto-generated method stub
		
		this.setId(String.valueOf(db.get("_id")));
		if(null!=db.get("createtime"))
			this.setCreatetime(String.valueOf(db.get("createtime")));
		if(null!=db.get("title"))
		this.setTitle(String.valueOf(db.get("title")));
		if(null!=db.get("remark"))
		this.setRemark(String.valueOf(db.get("remark")));
		if(null!=db.get("status"))
		this.setStatus(String.valueOf(db.get("status")));
		if(null!=db.get("options")){
			List<Document> mlist=(List<Document>)db.get("options");
			List<VoteOption> options=new ArrayList<VoteOption>();
			if(null!=mlist){
				DBUtil dbu=new DBUtil();
				options=(List<VoteOption>)dbu.formatVOList(mlist, VoteOption.class);
				this.setOptions(options);
			}
		}
		if(null!=db.get("type"))
		this.setType(String.valueOf(db.get("type")));
		if(null!=db.get("filterdate"))
		this.setFilterdate(String.valueOf(db.get("filterdate")));
		if(null!=db.get("starttime"))
		this.setStarttime(String.valueOf(db.get("starttime")));
		if(null!=db.get("endtime"))
		this.setEndtime(String.valueOf(db.get("endtime")));
		if(null!=db.get("filtertype"))
		this.setFiltertype(String.valueOf(db.get("filtertype")));
		if(null!=db.get("votecountlimit"))
		this.setVotecountlimit(String.valueOf(db.get("votecountlimit")));
		if(null!=db.get("votetermlimit"))
		this.setVotetermlimit(String.valueOf(db.get("votetermlimit")));
		
	}
	
	public void updateDB(Document db) {
		// TODO Auto-generated method stub
		DBUtil dbu=new DBUtil();
		if(!(null==title||"".equals(title.trim())))
			db.put("title", title);
		if(!(null==createtime||"".equals(createtime.trim())))
		    db.put("createtime", createtime);
		if(!(null==remark||"".equals(remark.trim())));
		    db.put("remark", remark);
		if(!(null==status||"".equals(status.trim())));
		    db.put("status", status);
        if(!(null==options))
            db.put("options",dbu.parseDBList(options) );
		if(!(null==type||"".equals(type.trim())));
	    db.put("type", type);
		if(!(null==filterdate||"".equals(filterdate.trim())));
	    db.put("filterdate", filterdate);
		if(!(null==starttime||"".equals(starttime.trim())));
	    db.put("starttime", starttime);
		if(!(null==endtime||"".equals(endtime.trim())));
	    db.put("endtime", endtime);
		if(!(null==filtertype||"".equals(filtertype.trim())));
	    db.put("filtertype", filtertype);
		if(!(null==votecountlimit||"".equals(votecountlimit.trim())));
	    db.put("votecountlimit", votecountlimit);
		if(!(null==votetermlimit||"".equals(votetermlimit.trim())));
	    db.put("votetermlimit", votetermlimit);
	}

	
	
}
