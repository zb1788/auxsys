package com.vcom.auxsys.repository;

import com.vcom.auxsys.pojo.Vote;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface VoteRepository extends MongoRepository<Vote,String> {
    Vote findByTitle(String title);

    Vote findById(String id);

}
