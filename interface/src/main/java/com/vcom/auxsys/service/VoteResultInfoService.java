package com.vcom.auxsys.service;

import com.vcom.auxsys.dao.VoteResultInfoDao;
import com.vcom.auxsys.pojo.Option;
import com.vcom.auxsys.pojo.VoteResult;
import com.vcom.auxsys.pojo.VoteResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

@Service
public class VoteResultInfoService {


    @Autowired
    private VoteResultInfoDao voteResultInfoDao;



    /**
     * 查询是否有点赞
     * @param id
     * @param author
     * @return
     */
    public VoteResultInfo findByIdAndAuthor(String id,String selectid, String author){
        Query query = new Query();
        query.addCriteria(Criteria.where("resultid").is(id).and("voteoptionid").is(selectid).and("author").is(author));
        return voteResultInfoDao.findByAuthorAndId(query);
    }



    /**
     * 通用查询条件
     * @return
     */
    private Query whereLists(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String pattern_name = sdf.format(new Date());
        Pattern pattern= Pattern.compile("^"+pattern_name+".*$", Pattern.CASE_INSENSITIVE);
        Query query = new Query();
        query.addCriteria(Criteria.where("createtime").regex(pattern));
        return query;
    }


    /**
     * 查询当天给每一项投票的次数
     * @param id
     * @param author
     * @param voteoptionid
     * @return
     */
    public long findByIdAndAuthorAndVoteoptionid(String id, String author, String voteoptionid){
        Query query = this.whereLists();

        query.addCriteria(Criteria.where("resultid").is(id).and("author").is(author).and("voteoptionid").is(voteoptionid));
        return voteResultInfoDao.getTotal(query);
    }


    /**
     * 查询当天是否有机会投票（投了几项，每项投了几票）
     * @param voteResult
     * @param author
     * @param selectid
     * @return
     */
    public Boolean hasVote(VoteResult voteResult, String author, String selectid){
        long maxVoteCountLimit = Long.parseLong(voteResult.getVotecountlimit());
        long maxVoteTermLimit = Long.parseLong(voteResult.getVotetermlimit());
        Query query = this.whereLists();
        query.addCriteria(Criteria.where("resultid").is(voteResult.getId()).and("author").is(author).and("voteoptionid").is(selectid));
        long nowVoteCountLimit = voteResultInfoDao.getTotal(query);
        if(nowVoteCountLimit >= maxVoteCountLimit){
            return false;
        }else{
            long nowVoteTermNum = 0;
            String optionids = "";
            List<Option> options = voteResult.getVoteOptions();
            for (Option option : options){
                if(Long.parseLong(option.getResults())> 0){
                    optionids += option.getOptionid() + ",";
                    nowVoteTermNum ++;
                }
            }
            if(nowVoteTermNum < maxVoteTermLimit){
                return true;
            }else{
                if(nowVoteTermNum == maxVoteTermLimit){
                    if(optionids.indexOf(selectid+",") != -1){
                        return true;
                    }else{
                        return false;
                    }
                }else{
                    return false;
                }
            }
        }
    }



    /**
     * 查询当天是否有机会投票
     * @param id
     * @param author
     * @param maxVoteCountLimit
     * @return
     * 总共投了几票，不管投给哪一项，只限制总数
     */
    public Boolean hasVoteChance(String id, String author, long maxVoteCountLimit){
        Query query = this.whereLists();
        query.addCriteria(Criteria.where("resultid").is(id).and("author").is(author));
        long nowVoteCountLimit = voteResultInfoDao.getTotal(query);
        return nowVoteCountLimit < maxVoteCountLimit;
    }




    /**
     * 保存结果详情
     * @param voteResultInfo
     */
    public void save(VoteResultInfo voteResultInfo){
        voteResultInfoDao.save(voteResultInfo);
    }


    /**
     * 删除结果详情
     * @param id
     */
    public void delete(String id){
        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(id));
        voteResultInfoDao.delete(query);
    }
}
