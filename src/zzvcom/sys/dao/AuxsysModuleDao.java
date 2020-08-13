package zzvcom.sys.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import zzvcom.sys.pojo.AuxsysModule;
import zzvcom.sys.util.MongoManager;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;

/**
 * @description： 资源评价系统功能接口
 * @time： 2015-11-4
 * @author： donghaoyu
 */
public class AuxsysModuleDao {
    private MongoManager mongoManager;
    private  MongoCollection<Document> moduleCollection;
    
    public MongoManager getMongoManager() {
        return mongoManager;
	}
	public void setMongoManager(MongoManager mongoManager) {
	    this.mongoManager = mongoManager;
	}
    /**
     * @description：获取评价数据库auxsys中auxsys_module的collection集合对象
     * @time： 2015-11-4
     * @author：donghaoyu
     * @return 
     */
    public  synchronized MongoCollection<Document> getModuleCollection() {
        MongoDatabase auxsysDb = MongoManager.getDb();
        moduleCollection = auxsysDb.getCollection("auxsys_module");
        return moduleCollection;
    }


    /**
     * @description：根据modulename查询unid外是否有同名模块
     * @param modulename  系统模块名
     * @param unid	排除模块id(null值则不排除)
     * @return 
     */
    public int getModuleCountByName(String modulename,String unid){
        moduleCollection = this.getModuleCollection();
        Document querydoc =new Document();
        //name采用模糊查询
        querydoc.put("name", modulename);
        if(unid!=null){
        	Document in = new Document();
	        List< ObjectId > fParams = new ArrayList< ObjectId >();
	        fParams.add(new ObjectId(unid.trim()));
	        in.put("$nin", fParams);
	        querydoc.put("_id", in);
        }
        MongoCursor<Document> mongoCursor = moduleCollection.find(querydoc).iterator();//查询操作
        int count =0;
        while(mongoCursor.hasNext()){
        	mongoCursor.next();
			count++;
		}	
        return count;
    }
    /**
     * @description：根据module对象设置条件获取功能列表<name为模糊匹配,sort倒序排列>
     * @param:条件Module对象
     * @param:起始位置
     * @param:每页长度
     * @return 
     */
    public List<AuxsysModule> queryByAuxsysModules(AuxsysModule qmodule,Integer start,Integer size){
        List modellist=new ArrayList();
        moduleCollection = this.getModuleCollection();
        Document querydoc = qmodule.formatDBObject();
        //name采用模糊查询
        if(querydoc.get("name")!=null){
        	Pattern pattern = Pattern.compile("^.*" + querydoc.get("name")+ ".*$", // as SQL:  like " '%" + personName + "%' "  
        	Pattern.CASE_INSENSITIVE); //忽略大小写
        	querydoc.put("name", pattern);
        }
        Document orderdoc = new Document();
        orderdoc.put("sort",-1);//sort字段降序排列
        FindIterable<Document> iterable = moduleCollection.find(querydoc).sort(orderdoc);//查询操作
        if(start!=null && size!=null){
        	iterable.skip(start).limit(size);
        }
        MongoCursor<Document> mongoCursor = iterable.iterator();
        while(mongoCursor.hasNext()){//遍历输出
        	Document dbObject = mongoCursor.next();
            AuxsysModule amodule = new AuxsysModule();
            amodule.formatVObject(dbObject);
            modellist.add(amodule);
        }
        return modellist;
    }
    
    /**
     * @description：根据module对象设置条件获取系统模块数量<name为模糊匹配>
     * @param qmodule  系统模块参数对象
     * @return 
     */
    public int getModuleCount(AuxsysModule qmodule){
        moduleCollection = this.getModuleCollection();
        Document querydoc = qmodule.formatDBObject();
        //name采用模糊查询
        if(querydoc.get("name")!=null){
        	Pattern pattern = Pattern.compile("^.*" + querydoc.get("name")+ ".*$", // as SQL:  like " '%" + personName + "%' "  
        	Pattern.CASE_INSENSITIVE); //忽略大小写
        	querydoc.put("name", pattern);
        }
        MongoCursor<Document> mongoCursor = moduleCollection.find(querydoc).iterator();//查询操作
        int count =0;
        while(mongoCursor.hasNext()){
        	mongoCursor.next();
			count++;
		}	
        return count;
    }
    
    /**
     * @description：获取全部功能列表
     * @time： 2015-11-4
     * @author：donghaoyu
     * @return 
     */
    public List<AuxsysModule> getAllModelList(){
        List modellist=new ArrayList();
        moduleCollection = this.getModuleCollection();
        MongoCursor<Document> mongoCursor = moduleCollection.find().iterator();//查询操作
        while(mongoCursor.hasNext()){//遍历输出
        	Document dbObject = mongoCursor.next();
            AuxsysModule amodule = new AuxsysModule();
            amodule.formatVObject(dbObject);
            modellist.add(amodule);
        }
        return modellist;
        
    }

    /**
     * @description：保存系统模块
     * @param role 待保存的系统模块对象
     * @return 
     */
    public void save(AuxsysModule tmodule){
        List rolelist=new ArrayList();
        tmodule.setId(null);
    	moduleCollection=this.getModuleCollection();
    	Document doc = tmodule.formatDBObject();
        moduleCollection.insertOne(doc);//写入
    }
    
    /**
     * 更新对应id的系统模块
     * @param role
     */
    public void update(AuxsysModule tmodule){
    	moduleCollection=this.getModuleCollection();
    	Document queryObj= new Document();//待修改的数据匹配记录
    	queryObj.put("_id", new ObjectId(tmodule.getId().trim()));
    	tmodule.setId(null);
    	Document updateObj= tmodule.formatDBObject();//待更新的数据记录
    	moduleCollection.replaceOne(queryObj, updateObj);
    }
    
    /**
     * @description：根据Id获取对应的模块对象
     * @param id 模块id
     * @return AuxsysModule
     */
    public AuxsysModule getModuleById(String id){
    	if(id==null || id.trim().length()==0){
    		return null;
    	}
    	moduleCollection = this.getModuleCollection();
    	Document doc = new Document();
        doc.put("_id", new ObjectId(id.trim()));
        MongoCursor<Document> mongoCursor = moduleCollection.find(doc).iterator();//查询操作
        if(mongoCursor.hasNext()){//遍历输出
        	Document dbObject = mongoCursor.next();
            AuxsysModule amodule = new AuxsysModule();
            amodule.formatVObject(dbObject);
            return amodule;
        }
        return null;
    }

    /**
     * 根据id串批量删除
     * @param ids
     */
    public void deleteByIds(String ids){
    	String[] idarrs = ids.split(",");
    	Document doc=new Document();
    	Document  in = new Document();
    	ArrayList<ObjectId> idList = new ArrayList<ObjectId>();
        for(int i = 0;i<idarrs.length;i++){ 
        	idList.add(new ObjectId(idarrs[i].trim()));
        }
        in.put("$in", idList);
        doc.put("_id", in);
        this.getModuleCollection().deleteMany(doc);
        
    }
    
    public void setModuleCollection(MongoCollection<Document> moduleCollection) {
        this.moduleCollection = moduleCollection;
    }

    
}
