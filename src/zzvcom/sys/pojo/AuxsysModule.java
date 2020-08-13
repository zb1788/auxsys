package zzvcom.sys.pojo;

import org.bson.Document;
import org.bson.types.ObjectId;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

public class AuxsysModule {
	
	private String id;//标识
    private String identify;//权限标识
    private String name;//名称
    private String link;//链接
    private String depth;//级别
    private String remark;//备注
    private String sort;//序号
    
    public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getIdentify() {
        return identify;
    }
    public void setIdentify(String identify) {
        this.identify = identify;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
    	if(name!=null){
            this.name = name.trim();
    	}
    }
    public String getLink() {
        return link;
    }
    public void setLink(String link) {
    	if(link!=null){
            this.link = link;
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
		if(this.getIdentify()!=null && this.getIdentify().trim().length()>0 ){
			doc.put("identify", this.getIdentify());
		}
		if(this.getDepth()!=null && this.getDepth().trim().length()>0){
			doc.put("depth", this.getDepth());
		}
		if(this.getLink()!=null && this.getLink().trim().length()>0){
			doc.put("link", this.getLink());
		}
		if(this.getName()!=null && this.getName().trim().length()>0){
			doc.put("name", this.getName());
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
		if(db.get("identify")!=null){
			this.setIdentify(db.get("identify").toString());
		}
		if(db.get("depth")!=null){
			this.setDepth(db.get("depth").toString());
		}
		if(db.get("link")!=null){
			this.setLink(db.get("link").toString());
		}
		if(db.get("name")!=null){
			this.setName(db.get("name").toString());
		}
		if(db.get("remark")!=null){
			this.setRemark(db.get("remark").toString());
		}
		if(db.get("sort")!=null){
			this.setSort(db.get("sort").toString());
		}
	}
    
}
