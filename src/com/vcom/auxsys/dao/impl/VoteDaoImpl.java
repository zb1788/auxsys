/**  
 * @Title: VoteDaoImpl.java
 * @Package com.vcom.auxsys.dao.impl
 * @date 2015-11-12
 */
package com.vcom.auxsys.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.bson.Document;
import org.bson.types.ObjectId;

import zzvcom.sys.util.MongoManager;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.vcom.auxsys.dao.VoteDao;
import com.vcom.auxsys.entitys.pojo.Vote;
import com.vcom.auxsys.entitys.pojo.VoteOption;
import com.vcom.auxsys.entitys.pojo.VoteResult;

/**
 * @title VoteDaoImpl
 * @description :
 * @date 2015-11-12下午12:26:02
 */
public class VoteDaoImpl implements VoteDao{
    private MongoManager mongoManager;
	private  MongoCollection<Document> voteCollection;
	private MongoCollection<Document> voteResultsCollection;

	/* (non-Javadoc)
	 * @see com.vcom.auxsys.dao.VoteDao#insertVote(com.vcom.auxsys.entitys.pojo.Vote)
	 */
	
	public String insertVote(Vote v) {
		// TODO Auto-generated method stub
		voteCollection = this.getVoteCollection();
		String result="success";
		Document doc=new Document();
		doc.put("title", v.getTitle());
		MongoCursor<Document> mongoCursor = voteCollection.find(doc).iterator();//查询操作
		if(mongoCursor.hasNext()){
			result="添加失败，投票名称已存在！！！";
		}else{
			doc = v.formatDBObject();		
			voteCollection.insertOne(doc);			
		}	
		return result;		
	}
	/* (non-Javadoc)
	 * @see com.vcom.auxsys.dao.VoteDao#deleteVote(com.vcom.auxsys.entitys.pojo.Vote)
	 */
	
	public void deleteVote(String[] list) {
		// TODO Auto-generated method stub
		voteCollection = this.getVoteCollection();
		voteResultsCollection=this.getVoteResultsCollection();
		Document doc=new Document();//待插入的数据
		
		for(String ids:list){	
			ObjectId id=new ObjectId(ids);
			Document rdoc=new Document();//待插入的数据
            doc.put("_id", id);
            rdoc.put("voteid", id);
			voteCollection.deleteOne(doc);	//删除相应记录结果
			voteResultsCollection.deleteMany(rdoc);
		}
		
	}

	/* (non-Javadoc)
	 * @see com.vcom.auxsys.dao.VoteDao#editVote(com.vcom.auxsys.entitys.pojo.Vote)
	 */
	
	public String editVote(Vote v) {
		// TODO Auto-generated method stub
		String result="success";
		voteCollection = this.getVoteCollection();
		
		Document doc= new Document();//待修改的数据匹配记录
		doc.put("title", v.getTitle());
		MongoCursor<Document> mongoCursor = voteCollection.find(doc).iterator();//查询操作
		if(mongoCursor.hasNext()){
			Document db=mongoCursor.next();
			String id=String.valueOf(db.get("_id"));
			if(id!=null&&!id.equals(v.getId())){
				result="修改失败，投票定义名称已存在！！！";
				return result;
			}
		}		
		Document queryObj= new Document();//待修改的数据匹配记录
		queryObj.put("_id", new ObjectId(v.getId()));
		Document resObj=voteCollection.find(queryObj).first();
		v.updateDB(resObj);
		voteCollection.replaceOne(queryObj, resObj);		
		return result;
	}

	/* (non-Javadoc)
	 * @see com.vcom.auxsys.dao.VoteDao#getVote(com.vcom.auxsys.entitys.pojo.Vote)
	 */
	
	public Vote getVote(Vote v) {
		// TODO Auto-generated method stub		
		voteCollection = this.getVoteCollection();		
		Document doc=new Document();//待插入的数据			
		doc=v.formatDBObject();
		doc=voteCollection.find(doc).first();
		Vote res=null;
		if(null!=doc){
			res=new Vote();
			res.formatVObject(doc);
		}		
		return res;
	}
	public List<Vote> queryVote(Vote v) {
		// TODO Auto-generated method stub	
		List<Vote> vlist=new ArrayList<Vote>();
		voteCollection = this.getVoteCollection();		
		Document doc=new Document();//待插入的数据			
		if(v!=null){
			if(!"".equals(v.getTitle())){
                Pattern pattern = Pattern.compile("^.*" + v.getTitle()+ ".*$", // as SQL:  like " '%" + personName + "%' "  
                Pattern.CASE_INSENSITIVE); //忽略大小写
                doc.put("title", pattern);
            }
			Vote res=null;
			MongoCursor<Document> mongoCursor=voteCollection.find(doc).iterator();
			while(mongoCursor.hasNext()){	
				doc=mongoCursor.next();
				res=new Vote();
				res.formatVObject(doc);
				vlist.add(res);
			}
		}			
		return vlist;
	}

	/* (non-Javadoc)
	 * @see com.vcom.auxsys.dao.VoteDao#getVotelist()
	 */
	
	public List getVotelist(List<String> list,Integer start,Integer size) {
		// TODO Auto-generated method stub
		ArrayList<Vote> votelist=new ArrayList<Vote>();
		
        voteCollection = this.getVoteCollection();
        if(null!=list){
        	Document doc=new Document();//待插入的数据
        	Document vd;
        	for(String id:list){
        		doc.put("_id", new ObjectId(id));
        		vd=voteCollection.find(doc).first();
        		Vote v=new Vote();
        		v.formatVObject(vd);
        		votelist.add(v);
        		doc.clear();     		
        	}
        }else{
        	Document order=new Document();
        	order.put("createtime", -1);
        	FindIterable<Document> iterable = voteCollection.find().sort(order);//查询操作
        	
            if(start!=null && size!=null){
            	iterable.skip(start).limit(size);
            }
            MongoCursor<Document> mongoCursor = iterable.iterator();
            while(mongoCursor.hasNext()){//遍历输出
                Document dbObject = mongoCursor.next();
                Vote vote = new Vote();
                vote.formatVObject(dbObject);
                votelist.add(vote);
            }
        }      
        return votelist;
	}

	/**
	 * @return the voteCollection
	 */
	public synchronized MongoCollection<Document> getVoteCollection() {
		MongoDatabase auxsysDb =MongoManager.getDb();
		voteCollection = auxsysDb.getCollection("auxsys_vote");
		return voteCollection;
	}

	/**
	 * @param voteCollection the voteCollection to set
	 */
	public void setVoteCollection(MongoCollection<Document> voteCollection) {
		this.voteCollection = voteCollection;
	}
	/* (non-Javadoc)
	 * @see com.vcom.auxsys.dao.VoteDao#manageVote(java.util.List)
	 */
	
	public void manageVote(List<Vote> list) {
		// TODO Auto-generated method stub
		Document doc=new Document();//待插入的数据
		Document res;
		if(list==null||list.isEmpty())return;
		for(Vote v:list){
			doc.put("_id", new ObjectId(v.getId()));
			res=voteCollection.find(doc).first();
			res.put("status",v.getStatus());
			this.voteCollection.replaceOne(doc, res);
		}
	}
	
	/**
     * @description：获取投票结果列表
     * @time： 2015-11-17
     * @author：donghaoyu
     * @param doc 查询条件
     * @return 
     */
    public List<VoteResult> getVoteResultsList(Document doc,String optionid,Map pMap){
        List<VoteResult> voteResultList = new ArrayList<VoteResult>();
        //DBCursor dbCursor = null;
        FindIterable<Document> iterable =null;
        if(optionid != null){
            Document orderBy = new Document();
            orderBy.put("order", -1);
            iterable = this.getVoteResultsCollection().find(doc).sort(orderBy);//查询操作
        }else{
        	iterable = this.getVoteResultsCollection().find(doc);//查询操作
        }
        if(pMap.containsKey("start")&&pMap.containsKey("end")){
            Integer start = Integer.valueOf(String.valueOf(pMap.get("start")));
            Integer end = Integer.valueOf(String.valueOf(pMap.get("end")));
            iterable.skip(start).limit(end);
        }
        MongoCursor<Document> mongoCursor = iterable.iterator();
        while(mongoCursor.hasNext()){//遍历输出
            Document dbObject = mongoCursor.next();
            VoteResult voteResult = new VoteResult();
            voteResult.formatVObject(dbObject);
            voteResultList.add(voteResult);
        }
        return voteResultList;
        
    }
    
    /**
     * @description：修改投票结果中的排序项的值为该选项id的值
     * @time： 2015-11-24
     * @author：donghaoyu
     * @param optionId 
     */
    public void editVoteResultOrder(String optionId,String voteid,String appid){
        Document doc = new Document();
        doc.put("voteid", voteid);
        doc.put("appid", appid);
        MongoCursor<Document> mongoCursor = this.getVoteResultsCollection().find(doc).iterator();//查询该投票下所有的投票项
        while(mongoCursor.hasNext()){//遍历输出
            Document queryDbObject = mongoCursor.next();
            
            VoteResult voteResult = new VoteResult();
            voteResult.formatVObject(queryDbObject);
            if(voteResult.getVoteOptions()!=null){
	            for(int i=0;i<voteResult.getVoteOptions().size();i++){
	                if(optionId.equals(voteResult.getVoteOptions().get(i).getOptionId())){
	                    voteResult.setOrder(Integer.valueOf(voteResult.getVoteOptions().get(i).getResults()));
	                    Document upDbObject = new Document();
	                    upDbObject =voteResult.formatDBObject();
	                    this.getVoteResultsCollection().replaceOne(queryDbObject, upDbObject); 
	                    break;
	                }
	            }
            }
        }
    }
    
    /**
     * @description：插入投票结果
     * @time： 2015-11-24
     * @author：donghaoyu
     * @param voteResult 
     */
    public void insertVoteResult(VoteResult voteResult){
        /*
         * 1、若投票id、地区、信息id、应用id一致则更新投票结果，否则插入投票结果
         */
        Document doc = new Document();
        if(!"".equals(voteResult.getArea()) && !"null".equals(voteResult.getArea()) && voteResult!=null){
            doc.put("area", voteResult.getArea());
        }else{
            doc.put("area", "");
        }
        doc.put("appid", voteResult.getAppid());
        doc.put("infoid", voteResult.getInfoid());
        doc.put("voteid", voteResult.getVoteid());
        MongoCursor<Document> mongoCursor = this.getVoteResultsCollection().find(doc).iterator();//查询操作
        if(!mongoCursor.hasNext()){//插入新的投票结果
            Document documents = new Document();
            //初始化投票结果项
            Vote vote = new Vote();
            vote.setId(voteResult.getVoteid());
            vote = this.getVote(vote);
            voteResult.setVoteOptions(tjOptionResult(vote.getOptions(), voteResult.getOpts()));
            documents = voteResult.formatDBObject();
            this.getVoteResultsCollection().insertOne(documents);
        }else{//更新投票结果
            VoteResult result = new VoteResult();
            while(mongoCursor.hasNext()){//遍历输出
                Document dbObject = mongoCursor.next();
                result.formatVObject(dbObject);
            }
            Document queryObj=new Document();
            queryObj.put("_id", new ObjectId(result.getId()));
            
            voteResult.setVoteOptions(tjOptionResult(result.getVoteOptions(), voteResult.getOpts()));
            Document upDbObject = new Document();
            upDbObject = voteResult.formatDBObject();
            this.getVoteResultsCollection().replaceOne(queryObj, upDbObject); 
        }
    }
    
    /**
     * @description：更新投票结果数
     * @time： 2015-12-24
     * @author：donghaoyu
     * @param oldOptions
     * @param newOptions
     * @return 
     */
    public List<VoteOption> tjOptionResult(List<VoteOption> oldOptions,String newOptions){
        Map<String, String> map = new HashMap<String, String>();
        for(int i=0;i<oldOptions.size();i++){
            String optionId = oldOptions.get(i).getOptionId();
            String result = oldOptions.get(i).getResults();
            if(result==null || "".equals(result)){
                result = "0";
                oldOptions.get(i).setResults("0"); 
            }
            if(newOptions.contains(optionId)){
                Integer res =1+Integer.valueOf(result);
                oldOptions.get(i).setResults(String.valueOf(res)); 
            }
        }
        return oldOptions;
    }
    
    public MongoCollection<Document> getVoteResultsCollection() {
        MongoDatabase auxsysDb = MongoManager.getDb();
        voteResultsCollection = auxsysDb.getCollection("auxsys_result");
        return voteResultsCollection;
    }
    public void setVoteResultsCollection( MongoCollection<Document> voteResultsCollection) {
        this.voteResultsCollection = voteResultsCollection;
    }
    public MongoManager getMongoManager() {
        return mongoManager;
    }
    public void setMongoManager(MongoManager mongoManager) {
        this.mongoManager = mongoManager;
    }

	/* (non-Javadoc)
	 * @see com.vcom.auxsys.dao.VoteDao#hasResult(java.lang.String)
	 */
	public Boolean hasResult(String id) {
		// TODO Auto-generated method stub
		Document rdoc=new Document();
		 rdoc.put("voteid", id);
		voteResultsCollection=this.getVoteResultsCollection();
		MongoCursor<Document> mongoCursor=voteResultsCollection.find(rdoc).iterator();
		return mongoCursor.hasNext();
	}
	
	/**
     * @description：根据请求参数查询投票结果列表幷返回json形式字符串
     * @time： 2015-11-24
     * @author：donghaoyu
     * @param map
     * @return 
     */
    public String getVotePageByMap(LinkedHashMap map) {
        String text = null;
        List<VoteResult> voteResultslist = new ArrayList<VoteResult>();
        Document doc = new Document();
        String appid = String.valueOf(map.get("moduleid"));
        doc.put("appid", appid);
        String infoid = String.valueOf(map.get("infoid"));
        doc.put("infoid", infoid);
        String voteid = String.valueOf(map.get("appraiseid"));
        doc.put("voteid", voteid);
        //若包含目录编码则加入区域查询条件
        if(map.containsKey("code")){
            String dircode = String.valueOf(map.get("code"));
            doc.put("dircode", dircode);
        }
        //若包含区域则加入区域查询条件
        if(map.containsKey("area")){
            String area = String.valueOf(map.get("area"));
            doc.put("area", area);
        }
        Map pMap = new HashMap();
        voteResultslist = this.getVoteResultsList(doc,null,pMap);
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        if(voteResultslist.size()>0){
            VoteResult voteResult = voteResultslist.get(0);
            for(int i=0;i<voteResult.getVoteOptions().size();i++){
                VoteOption voteOption = voteResult.getVoteOptions().get(i);
                if(i==0){
                    sb.append("{\"id\":\"" + voteOption.getOptionId()+"\",\"value\":\""+voteOption.getOptionName()+"\",\"amount\":\""+voteOption.getResults()+"\"");
                    sb.append("}");
                }else{
                    sb.append(",{\"id\":\"" + voteOption.getOptionId()+"\",\"value\":\""+voteOption.getOptionName()+"\",\"amount\":\""+voteOption.getResults()+"\"");
                    sb.append("}");
                }
            }
        }
        sb.append("]");
        return sb.toString();
    }
    
    /**
     * @description：获取投票结果个数
     * @time： 2016-1-19
     * @author：donghaoyu
     * @param doc
     * @return 
     */
    public Integer getVoteResultCount(Document doc){
        int count = 0;
		MongoCursor<Document> mongoCursor = this.getVoteResultsCollection().find(doc).iterator();//查询操作
		while(mongoCursor.hasNext()){
			mongoCursor.next();
			count++;
		}	
        return count;
    }
    
    /**
     * @description：检查投票id和投票项是否匹配
     * @time： 2015-11-23
     * @author：donghaoyu
     * @param voteResult
     * @return 
     */
    public String checkVoteSfcz(VoteResult voteResult){
        String voteMessage ="";
        //判断投票是否存在
        Vote vote = new Vote();
        boolean isvalid = this.isValid(voteResult.getVoteid());
        if(isvalid){
            vote.setId(voteResult.getVoteid());
            vote = this.getVote(vote);
            String voteOptionsIds = "";
            //若存在则判断投票项是否匹配，不匹配则给出提示信息
            if(vote!=null){
                if("0".equals(vote.getStatus())){
                    voteMessage+="所提交的投票已经停用，请使用启用的投票！";
                }else{
                    for(int i=0;i<vote.getOptions().size();i++){
                        VoteOption voteOption = vote.getOptions().get(i);
                        if(i==0){
                            voteOptionsIds += voteOption.getOptionId();
                        }else{
                            voteOptionsIds += ","+voteOption.getOptionId();
                        }
                    }
                    String[] opts = voteResult.getOpts().split(",");
                    boolean sfpp = true;
                    for(int i=0;i<opts.length;i++){
                        if(!voteOptionsIds.contains(opts[i])){
                            sfpp = false;
                            break;
                        }
                    }
                    if(!sfpp){
                        voteMessage+="所提交的投票项与投票不匹配,请检查！";
                    }
                }
            }else{
                voteMessage+="所提交的投票不存在，请查看！";
            }
        }else{
            voteMessage+="所提交的投票id不正确，请查看！";
        }
        return voteMessage.toString();
    }
    
    /**
     * @description：判断投票id是否是16进制
     * @time： 2015-12-14
     * @author：donghaoyu
     * @param hexString
     * @return 
     */
    public boolean isValid(String hexString){
        if(hexString == null)
            throw new IllegalArgumentException();
        int len = hexString.length();
        if(len != 24)
            return false;
        for(int i = 0; i < len; i++)
        {
            char c = hexString.charAt(i);
            if((c < '0' || c > '9') && (c < 'a' || c > 'f') && (c < 'A' || c > 'F'))
                return false;
        }

        return true;
    }
    /**
     * @Description: 获取数量
     * @author 罗华森
     * @date 2015-12-11
     */
    public int getCount(){        
        int count = 0;
		MongoCursor<Document> mongoCursor = this.getVoteCollection().find().iterator();//查询操作
		while(mongoCursor.hasNext()){
			mongoCursor.next();
			count++;
		}	
        return count;
    }
	
}
