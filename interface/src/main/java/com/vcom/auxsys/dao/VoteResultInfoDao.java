package com.vcom.auxsys.dao;

import com.vcom.auxsys.pojo.VoteResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

@Repository
public class VoteResultInfoDao {

    @Autowired
    MongoTemplate mongoTemplate;

    //查询是否有点赞
    public VoteResultInfo findByAuthorAndId(Query query){
        return mongoTemplate.findOne(query, VoteResultInfo.class);
    }


    public long getTotal(Query query){
        return mongoTemplate.count(query, VoteResultInfo.class);
    }


    //保存结果详情
    public void save(VoteResultInfo voteResultInfo){
        mongoTemplate.save(voteResultInfo);
    }

    public void delete(Query query){
        mongoTemplate.remove(query,VoteResultInfo.class);
    }

}
