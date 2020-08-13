package com.vcom.auxsys.dao;

import com.vcom.auxsys.pojo.VoteResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

@Repository
public class VoteResultDao {

    @Autowired
    MongoTemplate mongoTemplate;

    //根据投票结果表的infoid查找结果内容
    public VoteResult findById(String appid,String area,String infoid, String voteid){
        Query query = new Query();
        query.addCriteria(Criteria.where("appid").is(appid).and("infoid").is(infoid).and("voteid").is(voteid).and("area").is(area));
        return mongoTemplate.findOne(query,VoteResult.class);
    }

    //保存投票结果内容
    public void save(VoteResult voteResult){
        mongoTemplate.save(voteResult);
    }
}
