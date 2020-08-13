package com.vcom.auxsys.dao;

import com.vcom.auxsys.pojo.Vote;
import com.vcom.auxsys.repository.VoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class VoteDao {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private VoteRepository voteRepository;

    public Vote findById(String voteid){
        return voteRepository.findById(voteid);
    }
}
