package com.vcom.auxsys.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

@Document(collection = "auxsys_vote")
public class Vote {
    @Id
    private String id;

    @Field("title")
    private String title;//查找的时候字段名是根据bean的字段名来查（这里就是name而不是title）

    @Field("status")
    private String status;

    @Field("createtime")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",locale = "zh",timezone = "GMT+8")
    private String createtime;

    @Field("options")
    private List<Option> options; //对应MongoDB内容是"options":[{"optionid":1,"optionname":"aaa","optionorder":0}]

    //类型
    //1:无限制 ;
    //2点赞<!-- 每项只能投一次（可取消） 没有每天 -->;
    //3:每天可以给任意项投几票<!-- 比如可以每天给任何一个人投 1/5票-->;
    //4:每天可以给最多几项投几票<!-- 比如可以每天给最多几个人投 1/5票-->
    //5:每天投几票<!-- 每天投10票（可以全投给一个人，也可以投给其他人，只能投10次）-->
    @Field("type")
    private String type;

    @Field("filtertype")
    private String filtertype;//1用户过滤2ip过滤

    private String filterdate;//是否合适起始时间1设置，0不设置

    private String starttime;//投票开始时间

    private String endtime;//投票结束时间

    private String votecountlimit;//用户投票次数

    private String votetermlimit;//可以投机项

    //如果数组里不是对象(对应数据库内容是"author":["mike","jam"])
    //private String [] auther;


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFiltertype() {
        return filtertype;
    }

    public void setFiltertype(String filtertype) {
        this.filtertype = filtertype;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }

    public List<Option> getOptions() {
        return options;
    }

    public void setOptions(List<Option> options) {
        this.options = options;
    }


    @Override
    public String toString() {
        return "Vote{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", status='" + status + '\'' +
                ", createtime='" + createtime + '\'' +
                ", options=" + options +
                ", type='" + type + '\'' +
                ", filtertype='" + filtertype + '\'' +
                ", filterdate='" + filterdate + '\'' +
                ", starttime='" + starttime + '\'' +
                ", endtime='" + endtime + '\'' +
                ", votecountlimit='" + votecountlimit + '\'' +
                ", votetermlimit='" + votetermlimit + '\'' +
                '}';
    }
}
