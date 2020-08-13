package zzvcom.sys.pojo;

import org.bson.Document;
import org.bson.types.ObjectId;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.vcom.auxsys.util.MongoDBAdepter;

/**
 * @description： 资源评价用户类
 * @time： 2015-11-5
 * @author： donghaoyu
 */
public class AuxsysRole implements MongoDBAdepter{
    public String id;
    public String username;
    public String password;
    public String depth;
    public String remark;
    public String sort;
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
    	if(username!=null){
    		this.username = username.trim();
    	}
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
    	if(password!=null){
    		this.password = password;
    	}
    }
    public String getDepth() {
        return depth;
    }
    public void setDepth(String depth) {
        this.depth = depth;
    }
    public String getRemark() {
        return remark;
    }
    public void setRemark(String remark) {
        this.remark = remark;
    }
    public String getSort() {
        return sort;
    }
    public void setSort(String sort) {
        this.sort = sort;
    }
    /**
     * 转化为数据库对象
     */
	public Document formatDBObject() {
		Document doc = new Document();
		if(this.getId()!=null && this.getId().trim().length()>0 ){
			doc.put("_id",  new ObjectId(this.getId().trim()));
		}
		if(this.getDepth()!=null && this.getDepth().trim().length()>0){
			doc.put("depth", this.getDepth());
		}
		if(this.getUsername()!=null && this.getUsername().trim().length()>0){
			doc.put("username", this.getUsername());
		}
		if(this.getPassword()!=null && this.getPassword().trim().length()>0){
			doc.put("password", this.getPassword());
		}
		if(this.getRemark()!=null && this.getRemark().trim().length()>0){
			doc.put("remark", this.getRemark());
		}
		if(this.getSort()!=null && this.getSort().trim().length()>0){
			try{
				doc.put("sort",Integer.parseInt(this.getSort()));
			}catch(Exception e){}
		}
		return doc;
	}
	/**
	 * 从数据库对象转化
	 */
	public void formatVObject(Document db) {
		if(db.get("_id")!=null){
			this.setId(db.get("_id").toString());
		}
		if(db.get("depth")!=null){
			this.setDepth(db.get("depth").toString());
		}
		if(db.get("username")!=null){
        this.setUsername(db.get("username").toString());
		}
        if(db.get("password")!=null){
        	this.setPassword(db.get("password").toString());
        }
        if(db.get("remark")!=null){
        	this.setRemark(db.get("remark").toString());
        }
        if(db.get("sort")!=null){
        	this.setSort(db.get("sort").toString());
        }
	}
}
