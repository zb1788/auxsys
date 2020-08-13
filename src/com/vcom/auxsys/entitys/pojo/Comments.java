package com.vcom.auxsys.entitys.pojo;

import org.apache.commons.lang.StringUtils;
import org.bson.Document;
import org.bson.types.ObjectId;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.sun.org.apache.bcel.internal.generic.NEW;
import com.vcom.auxsys.util.CommonUtil;
import com.vcom.auxsys.util.MongoDBAdepter;

/**
 * @description： 评论实体类
 * @time： 2015-11-11
 * @author： donghaoyu
 */
public class Comments implements MongoDBAdepter{
    private String id;
    private String appid; //应用id
    private String dircode; //目录code
    private String dirname; //目录名称
    private String infoid; //信息id
    private String infotitle; //信息标题
    private String userid; //用户id
    private String username; //用户名称
    private String area; //地区
    private String ip; //IP
    private String content; //内容
    private String createtime; //评论时间
    private String status; //状态
    private String point; //打分
    private String remark1; //备注1
    private String remark2; //备注2
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
    public String getInfotitle() {
        return infotitle;
    }
    public void setInfotitle(String infotitle) {
        this.infotitle = infotitle;
    }
    public String getUserid() {
        return userid;
    }
    public void setUserid(String userid) {
        this.userid = userid;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getArea() {
        return area;
    }
    public void setArea(String area) {
        this.area = area;
    }
    public String getIp() {
        return ip;
    }
    public void setIp(String ip) {
        this.ip = ip;
    }
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public String getCreatetime() {
        return createtime;
    }
    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public String getPoint() {
        return point;
    }
    public void setPoint(String point) {
        this.point = point;
    }
    public String getRemark1() {
        return remark1;
    }
    public void setRemark1(String remark1) {
        this.remark1 = remark1;
    }
    public String getRemark2() {
        return remark2;
    }
    public void setRemark2(String remark2) {
        this.remark2 = remark2;
    }
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    /**
     * @description：将comments转化成dbobject
     * @time： 2015-11-18
     * @return Document
     */
    public Document formatDBObject() {
    	Document doc=new Document();
        if(this.getId()!=null && !"".equals(this.getId()) ){
            doc.put("_id", new ObjectId(this.getId()));
        }
        doc.put("appid", this.getAppid());
        doc.put("dircode", this.getDircode());
        doc.put("dirname", this.getDirname());
        doc.put("infoid", this.getInfoid());
        doc.put("infotitle", this.getInfotitle());
        doc.put("userid", this.getUserid());
        doc.put("username", this.getUsername());
        doc.put("ip",this.getIp() );
        doc.put("area",this.getArea() );
        doc.put("content", this.getContent());
        doc.put("createtime", this.getCreatetime());
        doc.put("status", this.getStatus());
        doc.put("point", !StringUtils.isBlank(this.getPoint())?this.getPoint():0);
        doc.put("remark1", this.getRemark1());
        doc.put("remark2", this.getRemark2());
        return doc;
    }
    /**
     * @description：将dbobject转化成comments对象
     * @time： 2015-11-18
     * @param dbObject 
     */
    public void formatVObject(Document dbObject) {
        this.setId(String.valueOf(dbObject.get("_id")));
        this.setAppid(String.valueOf(dbObject.get("appid")));
        this.setDircode(String.valueOf(dbObject.get("dircode")));
        this.setDirname(String.valueOf(dbObject.get("dirname")));
        this.setInfoid(String.valueOf(dbObject.get("infoid")));
        this.setInfotitle(String.valueOf(dbObject.get("infotitle")));
        this.setUserid(String.valueOf(dbObject.get("userid")));
        this.setUsername(String.valueOf(dbObject.get("username")));
        this.setArea(String.valueOf(dbObject.get("area")));
        this.setIp(String.valueOf(dbObject.get("ip")));
        this.setContent(String.valueOf(dbObject.get("content")));
        this.setCreatetime(String.valueOf(dbObject.get("createtime")));
        this.setStatus(String.valueOf(dbObject.get("status")));
        this.setPoint(String.valueOf(dbObject.get("point")));
        this.setRemark1(String.valueOf(dbObject.get("remark1")));
        this.setRemark2(String.valueOf(dbObject.get("remark2")));
    }
    
    public boolean isEmpty(Object object){
        boolean sfwk = false;
        if("null".equals(object)){
            sfwk = true;
        }else if("".equals(object)){
            sfwk = true;
        }else if(object == null){
            sfwk = true;
        }
        return sfwk;
    }
    
    
    /**
     * @description：查询评论信息必输项是否录入
     * @time： 2015-11-23
     * @param comments 待校验评论对象
     * @return 
     */
    public String checkCommentsNeeds(Comments comments){
        String message = "";
        if(StringUtils.isBlank(comments.getAppid())){
            message+="应用id,";
        }
        if(StringUtils.isBlank(comments.getInfoid())){
            message+="信息id,";
        }
        if(StringUtils.isBlank(comments.getInfotitle())){
            message+="信息标题,";
        }
        if(StringUtils.isBlank(comments.getUsername())){
            message+="用户名,";
        }
        if(StringUtils.isBlank(comments.getContent())){
            message+="评论内容,";
        }
        if(message!=""){
            message+="为空，请检查。";
        }
        //校验打分是否为
        if(!"null".equals(comments.getPoint()) && !StringUtils.isBlank(comments.getPoint())){
            boolean fnumber = CommonUtil.fnumber(comments.getPoint());
            if(!fnumber){
                message+="评论打分必须录入数字，请检查。";
            } 
        }
        return message;
    }
}
