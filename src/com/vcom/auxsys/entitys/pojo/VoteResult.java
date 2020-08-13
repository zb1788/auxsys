package com.vcom.auxsys.entitys.pojo;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.bson.Document;
import org.bson.types.ObjectId;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.vcom.auxsys.util.CommonUtil;
import com.vcom.auxsys.util.DBUtil;
import com.vcom.auxsys.util.MongoDBAdepter;

/**
 * @description： 投票结果
 */
public class VoteResult implements MongoDBAdepter{
    private String id;
    private String appid; //应用id
    private String dircode;//目录code
    private String dirname;//目录名称
    private String infoid;//信息id
    private String infotitle;//信息标题
    private String area;//地区
    private String updatetime;//更新时间
    private String voteid;//投票id
    private List<VoteOption> voteOptions;//投票项
    private Integer order;
    private String opts;
    public String getAppid() {
        return appid;
    }
    public void setAppid(String appid) {
        this.appid = appid;
    }
    public String getDircode() {
        return dircode;
    }
    public void setDircode(String dircode) {
        this.dircode = dircode;
    }
    public String getDirname() {
        return dirname;
    }
    public void setDirname(String dirname) {
        this.dirname = dirname;
    }
    public String getInfoid() {
        return infoid;
    }
    public void setInfoid(String infoid) {
        this.infoid = infoid;
    }
    public String getArea() {
        return area;
    }
    public void setArea(String area) {
        this.area = area;
    }
    public String getUpdatetime() {
        return updatetime;
    }
    public void setUpdatetime(String updatetime) {
        this.updatetime = updatetime;
    }
    public List<VoteOption> getVoteOptions() {
        return voteOptions;
    }
    public void setVoteOptions(List<VoteOption> voteOptions) {
        this.voteOptions = voteOptions;
    }
    public String getVoteid() {
        return voteid;
    }
    public void setVoteid(String voteid) {
        this.voteid = voteid;
    }
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    /**
     * @description：将voteresult转化成dbobject
     * @time： 2015-11-18
     * @return 
     */
    public Document formatDBObject() {
        DBUtil dbu=new DBUtil();
        Document doc=new Document();
        if(this.getId()!=null && !"".equals(this.getId())){
            doc.put("_id", new ObjectId(this.getId()));
        }
        doc.put("appid", this.getAppid());
        doc.put("dircode", this.getDircode());
        doc.put("dirname", this.getDirname());
        doc.put("infoid", this.getInfoid());
        doc.put("infotitle", this.getInfotitle());
        doc.put("updatetime", this.getUpdatetime());
        doc.put("area", this.getArea()==null?"null":this.getArea());
        doc.put("voteid", this.getVoteid());
        doc.put("options",dbu.parseDBList(this.getVoteOptions()) );
        doc.put("order", this.getOrder());
        return doc;
    }
    /**
     * @description：将dbobject转化成voteresult对象
     * @time： 2015-11-18
     * @param dbObject 
     */
    public void formatVObject(Document dbObject) {
        DBUtil dbu=new DBUtil();
        this.setId(String.valueOf(dbObject.get("_id")));
        this.setAppid(String.valueOf(dbObject.get("appid")));
        this.setDircode(String.valueOf(dbObject.get("dircode")));
        this.setDirname(String.valueOf(dbObject.get("dirname")));
        this.setInfoid(String.valueOf(dbObject.get("infoid")));
        this.setInfotitle(String.valueOf(dbObject.get("infotitle")));
        this.setUpdatetime(String.valueOf(dbObject.get("updatetime")));
        this.setArea(String.valueOf(dbObject.get("area")));
        this.setVoteid(String.valueOf(dbObject.get("voteid")));
        List<Document> rlist=(List<Document>)dbObject.get("options");
        List<VoteOption> options=new ArrayList<VoteOption>();
        options=(List<VoteOption>)dbu.formatVOList(rlist, VoteOption.class);
        this.setVoteOptions(options);
        this.setOrder(Integer.valueOf(String.valueOf(dbObject.get("order"))));
    }
    
    /**
     * @description：查询评论信息必输项是否录入
     * @time： 2015-11-23
     * @param comments 待校验评论对象
     * @return 
     */
    public String checkVoteNeeds(VoteResult voteResult){
        String message = "";
        if(StringUtils.isBlank(voteResult.getAppid())){
            message+="应用id,";
        }
        if(StringUtils.isBlank(voteResult.getInfoid())){
            message+="信息id,";
        }
        if(StringUtils.isBlank(voteResult.getInfotitle())){
            message+="信息标题,";
        }
        if(StringUtils.isBlank(voteResult.getVoteid())){
            message+="投票id,";
        }
        if(StringUtils.isBlank(voteResult.getOpts())){
            message+="投票项,";
        }
        /*boolean tpx = false;
        for(int i=0;i<voteResult.getVoteOptions().size();i++){
            VoteOption voteOption = voteResult.getVoteOptions().get(i);
            if(voteOption.getOptionId().isEmpty() || voteOption.getResults().isEmpty()){
                message+="投票项或投票结果";
                tpx = true;
                break;
            }
        }*/
        if(message!=""){
            message+="为空，请检查。";
        }
        /*if(!tpx){
            for(int i=0;i<voteResult.getVoteOptions().size();i++){
                VoteOption voteOption = voteResult.getVoteOptions().get(i);
                if(!CommonUtil.fnumber(voteOption.getResults())){
                    message+="投票结果必须录入数字，请检查。";
                    break;
                }
            }  
        }*/
        return message;
    }
    public Integer getOrder() {
        return order;
    }
    public void setOrder(Integer order) {
        this.order = order;
    }
    public String getInfotitle() {
        return infotitle;
    }
    public void setInfotitle(String infotitle) {
        this.infotitle = infotitle;
    }
    public String getOpts() {
        return opts;
    }
    public void setOpts(String opts) {
        this.opts = opts;
    }
}
