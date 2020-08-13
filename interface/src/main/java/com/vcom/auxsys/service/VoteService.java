package com.vcom.auxsys.service;

import com.vcom.auxsys.dao.VoteDao;
import com.vcom.auxsys.pojo.Vote;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VoteService {

    @Autowired
    private VoteDao voteDao;

    public Vote findById(String voteid){
        return voteDao.findById(voteid);
    }
}
