package com.vcom.auxsys.pojo;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "auxsys_resultinfo")
public class VoteResultInfo {
    @Id
    private String id;

    private String resultid;


    private String voteoptionid;

    private String createtime;

    private String author;//用户凭证(帐号orIP)

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }



    public String getVoteoptionid() {
        return voteoptionid;
    }

    public void setVoteoptionid(String voteoptionid) {
        this.voteoptionid = voteoptionid;
    }

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }

    public String getResultid() {
        return resultid;
    }

    public void setResultid(String resultid) {
        this.resultid = resultid;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}
