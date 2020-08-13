package zzvcom.sys.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.bson.Document;
import org.bson.types.ObjectId;

import zzvcom.sys.pojo.AuxsysRole;
import zzvcom.sys.util.MongoManager;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;

/**
 * @description： 系统用户数据处理方法
 * @time： 2015-11-15
 */
public class AuxsysRoleDao {
    private MongoManager mongoManager;
    private   MongoCollection<Document> roleCollection;
    
	public MongoManager getMongoManager() {
	    return mongoManager;
	}
	public void setMongoManager(MongoManager mongoManager) {
		this.mongoManager = mongoManager;
	}
    /**
     * @description：获取评价数据库auxsys中auxsys_role的collection集合对象
     * @time： 2015-11-4
     * @author：donghaoyu
     * @return 
     */
    public  synchronized  MongoCollection<Document> getRoleCollection() {
        MongoDatabase auxsysDb = MongoManager.getDb();
        roleCollection = auxsysDb.getCollection("auxsys_role");
        return roleCollection;
    }

    /**
     * @description：根据username完全匹配用户
     * @param role  用户角色参数对象
     * @param start 分页起点
     * @param size  分页数据长度
     * @return 
     */
    public List getRoleList(AuxsysRole auxsysRole,Integer start,Integer size){
        List rolelist=new ArrayList();
        roleCollection = this.getRoleCollection();
        Document doc = auxsysRole.formatDBObject();
        //username采用模糊查询
        if(doc.get("username")!=null){
        	Pattern pattern = Pattern.compile("^.*" + doc.get("username")+ ".*$", // as SQL:  like " '%" + personName + "%' "  
        	Pattern.CASE_INSENSITIVE); //忽略大小写
        	doc.put("username", pattern);
        }
        Document orderdoc = new Document();
        orderdoc.put("sort",1);//sort字段升序排列
        FindIterable<Document> iterable = roleCollection.find(doc).sort(orderdoc);//查询操作
    	
        if(start!=null && size!=null){
        	iterable.skip(start).limit(size);
        }
        MongoCursor<Document> mongoCursor = iterable.iterator();
        
        while(mongoCursor.hasNext()){//遍历输出
        	Document dbObject = mongoCursor.next();
            AuxsysRole trole = new AuxsysRole();
            trole.formatVObject(dbObject);
            rolelist.add(trole);
        }
        return rolelist;
    }
    /**
     * @description：根据条件获取用户分页列表(start,size为null则不分页)<username为模糊匹配,sort倒序排列>
     * @param role  用户角色参数对象
     * @param start 分页起点
     * @param size  分页数据长度
     * @return 
     */
    public int getRoleCountByUserName(String username){
        List rolelist=new ArrayList();
        AuxsysRole auxsysRole = new AuxsysRole();
        auxsysRole.setUsername(username);
        roleCollection = this.getRoleCollection();
        Document doc = auxsysRole.formatDBObject();
        MongoCursor<Document> mongoCursor = roleCollection.find(doc).iterator();//查询操作
        
        int count = 0;
        while(mongoCursor.hasNext()){//遍历输出
           mongoCursor.next();
           count++;
        }        
        return count;
    }

    /**
     * @description：根据条件获取用户总数<username为模糊匹配>
     * @param role  系统用户参数对象
     * @return 
     */
    public int getRoleCount(AuxsysRole auxsysRole){
        roleCollection = this.getRoleCollection();
        Document doc = auxsysRole.formatDBObject();
        //username采用模糊查询
        if(doc.get("username")!=null){
        	Pattern pattern = Pattern.compile("^.*" + doc.get("username")+ ".*$", //as SQL:  like " '%" + personName + "%' "
        	Pattern.CASE_INSENSITIVE); //忽略大小写
        	doc.put("username", pattern);
        }
        MongoCursor<Document> mongoCursor = roleCollection.find(doc).iterator();//查询操作
        
        int count = 0;
        while(mongoCursor.hasNext()){//遍历输出
        	mongoCursor.next();
           count++;
        }        
        return count;    }

    /**
     * @description：保存系统用户
     * @param role 系统用户对象
     * @return 
     */
    public void save(AuxsysRole role){
        List rolelist=new ArrayList();
        role.setId(null);
        roleCollection = this.getRoleCollection();
        Document doc = role.formatDBObject();
        roleCollection.insertOne(doc);//写入
    }
    
    /**
     * 更新对应id的系统用户
     * @param role
     */
    public void update(AuxsysRole role){
    	roleCollection=this.getRoleCollection();
    	Document queryObj= new Document();//待修改的数据匹配记录
    	queryObj.put("_id", new ObjectId(role.getId().trim()));
    	role.setId(null);
    	Document updateObj= role.formatDBObject();//待更新的数据记录
    	roleCollection.replaceOne(queryObj, updateObj);
    }
    
    /**
     * @description：根据用户Id获取对应的用户
     * @time： 2015-11-5
     * @author：donghaoyu
     * @param role 系统用户对象
     * @return 
     */
    public AuxsysRole getRoleById(String id){
    	if(id==null || id.trim().length()==0){
    		return null;
    	}
        roleCollection = this.getRoleCollection();
        Document doc = new Document();
        doc.put("_id", new ObjectId(id.trim()));
        MongoCursor<Document> mongoCursor = roleCollection.find(doc).iterator();//查询操作
        if(mongoCursor.hasNext()){//遍历输出
        	Document dbObject = mongoCursor.next();
            AuxsysRole auxsysRole = new AuxsysRole();
            auxsysRole.formatVObject(dbObject);
            return auxsysRole;
        }
        return null;
    }
    
    /**
     * @description：获取根据用户名和密码获取对应的用户
     * @time： 2015-11-5
     * @author：donghaoyu
     * @param role 系统用户对象
     * @return 
     */
    public List getLoginRole(AuxsysRole role){
        List rolelist=new ArrayList();
        roleCollection = this.getRoleCollection();
        Document doc = new Document();
        doc.put("username", role.getUsername());
        doc.put("password", role.getPassword());
        MongoCursor<Document> mongoCursor = roleCollection.find(doc).iterator();//查询操作
        while(mongoCursor.hasNext()){//遍历输出
        	Document dbObject = mongoCursor.next();
            AuxsysRole auxsysRole = new AuxsysRole();
            auxsysRole.formatVObject(dbObject);
            rolelist.add(auxsysRole);
        }
        return rolelist;
    }
    
    /**
     * 根据id串批量删除
     * @param ids
     */
    public void deleteByIds(String ids){
    	String[] idarrs = ids.split(",");
        Document doc=new Document();
        Document in = new Document();
    	ArrayList<ObjectId> idList = new ArrayList<ObjectId>();
        for(int i = 0;i<idarrs.length;i++){ 
        	idList.add(new ObjectId(idarrs[i].trim()));
        }
        in.put("$in", idList);
        doc.put("_id", in);
        this.getRoleCollection().deleteMany(doc);
    }
    
    /**
     * 根据id串批量重置密码
     * @param ids
     */
    public void restPwd(String ids){
    	String[] idarrs = ids.split(",");
    	for(int i=0;i<idarrs.length;i++){
    		Document qdoc=new Document();
	        qdoc.put("_id", new ObjectId(idarrs[i].trim()));
	        MongoCursor<Document> mongoCursor = roleCollection.find(qdoc).iterator();//查询操作
	        while(mongoCursor.hasNext()){//重置对应用户的密码
	        	Document dbObject = mongoCursor.next();
	            dbObject.put("password","123456");
		        this.getRoleCollection().replaceOne(qdoc,dbObject);
	        }
    	}
    }
    
    public void setRoleCollection(MongoCollection<Document> roleCollection) {
        this.roleCollection = roleCollection;
    }


}
