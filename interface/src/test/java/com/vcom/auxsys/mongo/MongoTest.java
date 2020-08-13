package com.vcom.auxsys.mongo;

import com.mongodb.BasicDBObject;
import com.vcom.auxsys.pojo.Option;
import com.vcom.auxsys.pojo.Vote;
import com.vcom.auxsys.pojo.VoteResult;
import com.vcom.auxsys.pojo.VoteResultInfo;
import com.vcom.auxsys.repository.VoteRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@RunWith(SpringRunner.class)
@SpringBootTest
public class MongoTest {

    @Autowired
    private MongoTemplate mongoTemplate;


    @Autowired
    private VoteRepository voteRepository;


    @Test
    public void testdemo(){
        String [] arr = new String[]{"1","2","3","4"};
        for(String a : arr){
            if(a.equals("2")){
                break;
            }
            System.out.println(a);
        }
    }

    @Test
    public void testAddResult(){
        String infoid = "88888888";
        String voteid = "5c946f7be41f7531a0b50cc6";
        String voteoptionid = "1";

        Query query = new Query();
        query.addCriteria(Criteria.where("infoid").is(infoid));
        VoteResult result = mongoTemplate.findOne(query,VoteResult.class);

        if(result == null){
            Vote v = voteRepository.findById(voteid);

            System.out.println(v);

            VoteResult voteResult = new VoteResult();
            voteResult.setType(v.getType());
            voteResult.setFilterdate(v.getFilterdate());
            voteResult.setStarttime(v.getStarttime());
            voteResult.setEndtime(v.getEndtime());
            voteResult.setFiltertype(v.getFiltertype());
            voteResult.setVotecountlimit(v.getVotecountlimit());
            voteResult.setVotetermlimit(v.getVotetermlimit());

            voteResult.setVoteOptions(v.getOptions());

            voteResult.setVoteid(voteid);
            voteResult.setAppid("444");
            voteResult.setInfoid(infoid);

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            voteResult.setUpdatetime(sdf.format(new Date()));


            List<Option> options = voteResult.getVoteOptions();

            for (Option option : options){
                if(option.getOptionid().equals(voteoptionid)){
                    option.setResults("1");
                }else{
                    option.setResults("0");
                }
            }
            //重新设置结果
            voteResult.setVoteOptions(options);

            mongoTemplate.save(voteResult);


            VoteResultInfo voteResultInfo = new VoteResultInfo();
            voteResultInfo.setResultid(voteResult.getId());
            voteResultInfo.setVoteoptionid(voteoptionid);
            voteResultInfo.setAuthor("zb1788");
            SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            voteResultInfo.setCreatetime(sdf1.format(new Date()));

            mongoTemplate.save(voteResultInfo);
        }else{
            List<Option> options = result.getVoteOptions();
            for (Option option : options){
                if(option.getOptionid().equals(voteoptionid)){
                    long total = Long.parseLong(option.getResults())+1;
                    option.setResults(String.valueOf(total));
                }
            }
            //重新设置结果
            result.setVoteOptions(options);
            mongoTemplate.save(result);

            VoteResultInfo voteResultInfo = new VoteResultInfo();
            voteResultInfo.setResultid(result.getId());
            voteResultInfo.setVoteoptionid(voteoptionid);
            voteResultInfo.setAuthor("zb1788");
            SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            voteResultInfo.setCreatetime(sdf1.format(new Date()));

            mongoTemplate.save(voteResultInfo);
        }



    }


    @Test
    public void testabc(){
//        语文共享圈投票项列表

//        Vote v = new Vote();
//        v.setId("5c946f7be41f7531a0b50cc6");
//        v.setStatus("3");

        Vote v = voteRepository.findById("5c946f7be41f7531a0b50cc6");
        VoteResult voteResult = new VoteResult();
        voteResult.setType(voteResult.getType());



//        Vote vv = mongoTemplate.findOne(q, Vote.class);

//        System.out.println(vv);

//        List<Vote> lists = new ArrayList<>();
//        lists.add(v);
//
//        Query query = new Query();
//        query.with(new Sort(new Sort.Order(Sort.Direction.ASC,"status")));
//        List<Vote> lists2 = mongoTemplate.find(query, Vote.class);
//        System.out.println(lists2);


//        Sort sort = new Sort(new Sort.Order(Sort.Direction.ASC,"status"));
//
//        Integer page = 1;
//        Integer rows = 2;
//        PageRequest pageRequest = new PageRequest(page-1,rows,sort);

//        List<Vote> lists1 = voteRepository.findAll(sort);


//        System.out.println(lists1);


//        Page<Vote> p = voteRepository.findAll(pageRequest);
//        List<Vote> lists1 = p.getContent();
//
//        System.out.println(lists1);


//        String pattern_name = "的";
//        Pattern pattern= Pattern.compile("^.*"+pattern_name+".*$", Pattern.CASE_INSENSITIVE);
//
//        Query q = new Query();
//        q.addCriteria(Criteria.where("title").regex(pattern));
//        q.with(new Sort(new Sort.Order(Sort.Direction.ASC,"status")));

//        Query query = new Query(Criteria.where("title").regex(pattern));
//        List<Vote> a = mongoTemplate.find(q, Vote.class);
//        System.out.println(a);

//        Aggregation agg = newAggregation(
//                unwind("$options"),
//                match(Criteria.where("id").is("5c1c9491d5397b35bfce5405").and("options.optionorder").is("0")),
//                project("options")
//        );
//        AggregationResults<Object> results = mongoTemplate.aggregate(agg, "auxsys_vote", Object.class);
//        List<Object> resultList = results.getMappedResults();
//
//        System.out.println(resultList);
//        Query query = new Query(Criteria.where("id").is("5c53f50a6e078129c884df5a"));
//        mongoTemplate.remove(query,Vote.class);

//        mongoTemplate.insert(lists,"auxsys_vote");
//        mongoTemplate.insert(lists,Vote.class);


//        voteRepository.save(v);

//        System.out.println(voteRepository.findAll());
//        System.out.println(voteRepository.count());

//          voteRepository.delete("5c53f6926e0781494cf01709");

//          voteRepository.save(v);

    }



    @Test
    public void testGroup(){
        Criteria ca = Criteria.where("classId").is("140687834519428108").and("subjectCode").is("0002").and("createTime").is("20180329");

        List<AggregationOperation> operations = new ArrayList<>();
        operations.add(Aggregation.match(ca));
        operations.add(Aggregation.group(new String[]{"username", "trueName", "reportId", "classId", "subjectCode", "createTime"}).sum("praiseNum").as("praiseNum"));
        operations.add(Aggregation.sort(Sort.Direction.DESC,"praiseNum"));

        Aggregation aggregation = Aggregation.newAggregation(operations);
        AggregationResults aggreResult = mongoTemplate.aggregate(aggregation,"praiseRecord", BasicDBObject.class);
        List list = aggreResult.getMappedResults();
        System.out.println(list);
    }

}
