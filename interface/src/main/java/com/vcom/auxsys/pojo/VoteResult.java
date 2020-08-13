package com.vcom.auxsys.pojo;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

/**
 * @description： 投票结果
 */
@Document(collection = "auxsys_result")
public class VoteResult {
    @Id
    private String id;
    private String appid; //应用id
    private String dircode;//目录code
    private String dirname;//目录名称
    private String infoid;//信息id
    private String infotitle;//信息标题
    private String area;//地区
    private String updatetime;//更新时间
    private String voteid;//投票id
    @Field("options")
    private List<Option> voteOptions;//投票基础项
    private Integer order;

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



    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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

    public String getVoteid() {
        return voteid;
    }

    public void setVoteid(String voteid) {
        this.voteid = voteid;
    }

    public List<Option> getVoteOptions() {
        return voteOptions;
    }

    public void setVoteOptions(List<Option> voteOptions) {
        this.voteOptions = voteOptions;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFilterdate() {
        return filterdate;
    }

    public void setFilterdate(String filterdate) {
        this.filterdate = filterdate;
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
}
