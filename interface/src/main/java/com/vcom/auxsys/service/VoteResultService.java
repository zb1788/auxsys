package com.vcom.auxsys.service;

import com.vcom.auxsys.dao.VoteDao;
import com.vcom.auxsys.dao.VoteResultDao;
import com.vcom.auxsys.pojo.*;
import com.vcom.auxsys.util.IpUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Service
public class VoteResultService {

    public static final Logger logger = LoggerFactory.getLogger(VoteResultService.class);

    private static final int FIRSTR = 0;//插入一条数据
    private static final int NOTFIRST = 1;//更新一条数据

    @Autowired
    private VoteResultDao voteResultDao;

    @Autowired
    private VoteDao voteDao;



    @Autowired
    VoteResultInfoService voteResultInfoService;




    /**
     * 添加投票接口
     * @param request
     * @param appid 应用id
     * @param area  区域
     * @param infoid 投票结果唯一值
     * @param infotitle 投票的描述（应用名称+投票名称+投票人名称）
     * @param voteid 投票基础表的id
     * @param author 投票人
     * @param selectid 选择的投票项
     * @return
     */
    public JSONResult addVote(HttpServletRequest request,String appid, String area, String voteid, String infoid, String infotitle,String selectid, String author){

        Vote vote = voteDao.findById(voteid);
        if(vote == null){
            return JSONResult.errorMsg("voteid不存在");
        }

        VoteResult voteResult = voteResultDao.findById(appid,area,infoid,voteid);

        String type = vote.getType();

        logger.debug("type:"+type);


        if(!checkVoteStatus(vote)){
            return JSONResult.errorMsg("投票已经停用");
        }


        if("1".equals(vote.getFiltertype())){
            //用户过滤
            if(author.equals("")){
                return JSONResult.errorMsg("用户信息不存在");
            }
        }else{
            //IP过滤
            author = IpUtil.getIpAddr(request);
        }

        if(!this.checkExpire(vote)){
            return JSONResult.errorMsg("投票已经过期");
        }

        if(!this.checkOptionExist(vote,selectid)){
            return JSONResult.errorMsg("selecid不存在");
        }

        if(voteResult == null){
            //保存结果
            voteResult = new VoteResult();
            this.saveVoteResult(voteResult,vote,appid,area,infoid,infotitle,selectid,author,FIRSTR);
            return JSONResult.ok("成功");
        }else{
            //判断逻辑
            if("1".equals(type)){
                this.saveVoteResult(voteResult,vote,appid,area,infoid,infotitle,selectid,author,NOTFIRST);
                return JSONResult.ok("成功");
            }else if("2".equals(type)){
                return this.thumbsUp(vote,voteResult,author,selectid);
            }else if("3".equals(type)){
                return this.addVoteByVoteCountLimit(vote,voteResult,appid,area,infoid,infotitle,author,selectid);
            }else if("4".equals(type)){
                return this.addVoteByVoteCountLimitAndVoteTermLimit(vote,voteResult,appid,area,infoid,infotitle,author,selectid);
            }else if("5".equals(type)){
                return this.addVoteByMaxCount(vote,voteResult,appid,area,infoid,infotitle,author,selectid);
            }else{
                return JSONResult.ok("未知投票类型");
            }
        }

    }


    /**
     * 检查selectid是否存在
     * @param vote
     * @param selectid
     * @return
     */
    private Boolean checkOptionExist(Vote vote, String selectid){
        List<Option> options = vote.getOptions();
        Boolean hasOptionId = false;
        for( Option option : options){
            if(option.getOptionid().equals(selectid)){
                hasOptionId = true;
                break;
            }
        }
        return hasOptionId;
    }


    /**
     * 验证投票是否停用
     * @param vote
     * @return
     */
    private Boolean checkVoteStatus(Vote vote){
        return "1".equals(vote.getStatus());
    }



    /**
     * 验证投票是否过期
     * @param vote
     * @return
     */
    private Boolean checkExpire(Vote vote){
        if(vote.getFilterdate().equals("1")){
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

            long  starttime = 0;
            long endtime = 0;
            try {
                starttime = sdf.parse(vote.getStarttime()).getTime();
                endtime = sdf.parse(vote.getEndtime()).getTime();
            } catch (ParseException e) {
                e.printStackTrace();
            }

            String localtime = sdf.format(new Date());

            long nowtime = 0;
            try {
                nowtime = sdf.parse(localtime).getTime();
            } catch (ParseException e) {
                e.printStackTrace();
            }

            if(nowtime < starttime || nowtime > endtime){
                return false;
            }else{
                return true;
            }
        }else{
            return true;
        }



    }




    /**
     * 更新投票结果
     * @param voteResult
     * @param vote
     * @param appid
     * @param infoid
     * @param selectid
     * @param type
     * @return
     * 从投票基础项内重新拉取基础数据
     */
    private VoteResult setVoteResult(VoteResult voteResult,Vote vote, String appid, String area,String infoid, String infotitle,String selectid,Integer type){

        voteResult.setType(vote.getType());
        voteResult.setFilterdate(vote.getFilterdate());
        voteResult.setStarttime(vote.getStarttime());
        voteResult.setEndtime(vote.getEndtime());
        voteResult.setFiltertype(vote.getFiltertype());
        voteResult.setVotecountlimit(vote.getVotecountlimit());
        voteResult.setVotetermlimit(vote.getVotetermlimit());

        if(type == 0){
            voteResult.setVoteOptions(vote.getOptions());
        }


        voteResult.setVoteid(vote.getId());
        voteResult.setAppid(appid);
        voteResult.setArea(area);
        voteResult.setInfoid(infoid);
        voteResult.setInfotitle(infotitle);
        voteResult.setOrder(0);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        voteResult.setUpdatetime(sdf.format(new Date()));

        //设置当前投票选项
        List<Option> options = voteResult.getVoteOptions();
        for (Option option : options){
            if(option.getOptionid().equals(selectid)){
                if(type == 0){
                    option.setResults("1");
                }else{
                    long total = Long.parseLong(option.getResults())+1;
                    option.setResults(String.valueOf(total));
                }
            }else{
                if(type == 0){
                    option.setResults("0");
                }
            }
        }
        //重新设置结果
        voteResult.setVoteOptions(options);

        return voteResult;
    }



    /**
     * 保存投票结果详情到数据库
     * @param voteResult
     * @param selectid
     * @param author
     */
    private void saveVoteResultInfo(VoteResult voteResult,String selectid,String author){
        VoteResultInfo voteResultInfo = new VoteResultInfo();
        voteResultInfo.setResultid(voteResult.getId());
        voteResultInfo.setVoteoptionid(selectid);
        voteResultInfo.setAuthor(author);
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        voteResultInfo.setCreatetime(sdf1.format(new Date()));

        voteResultInfoService.save(voteResultInfo);
    }



    //保存/更新投票结果到数据库
    private void saveVoteResult(VoteResult voteResult,Vote vote,String appid,String area,String infoid,String infotitle,String selectid,String author,int type){

        //1更新投票总数
        voteResultDao.save(this.setVoteResult(voteResult,vote,appid,area,infoid,infotitle,selectid,type));

        //2保存投票结果详情
        this.saveVoteResultInfo(voteResult,selectid,author);
    }




    /**
     * 投票类型2
     * @param vote
     * @param voteResult
     * @param author
     * @param selectid
     * @return
     * 点赞逻辑
     * 可以给每一项投一票，再次投票相当于取消投票（即点赞和取消点赞）
     */
    private JSONResult thumbsUp(Vote vote,VoteResult voteResult,String author,String selectid){
        //1.voteResult表次数不用更新
        //2.增加点赞voteResultInfo加一条数据，取消点赞voteResultInfo删除一条数据
        VoteResultInfo voteResultInfo = voteResultInfoService.findByIdAndAuthor(voteResult.getId(),selectid,author);
        if(voteResultInfo == null){
            //点赞
            this.saveVoteResultInfo(voteResult,selectid,author);
            return JSONResult.ok("成功");
        }else{
            //取消点赞
            voteResultInfoService.delete(voteResultInfo.getId());
            return JSONResult.ok("取消点赞成功");
        }
    }


    /**
     * 投票类型3
     * @param vote 投票基础信息
     * @param voteResult 投票结果
     * @param appid 应用id
     * @param infoid 投票结果唯一值
     * @param author 投票人员
     * @param selectid 选择的投票项
     * @return
     * 每天可以给任意项投几票
     * 比如可以每天给任何一个人投 1/5票（不限制最多投几项）
     */
    private  JSONResult addVoteByVoteCountLimit(Vote vote,VoteResult voteResult,String appid,String area, String infoid,String infotitle, String author, String selectid){
            long voteCountLimit = Long.parseLong(vote.getVotecountlimit());
            long nowVoteCountNum = voteResultInfoService.findByIdAndAuthorAndVoteoptionid(voteResult.getId(),author,selectid);
            logger.debug("nowVoteCountNum:" + nowVoteCountNum);
            logger.debug("voteCountLimit:" + voteCountLimit);
            if(nowVoteCountNum < voteCountLimit){
                //可以继续点赞

                this.saveVoteResult(voteResult,vote,appid,area,infoid,infotitle,selectid,author,NOTFIRST);
                return JSONResult.ok("投票成功");
            }else{
                //今天不能再点赞
                return JSONResult.ok("此项投票次数用完了");
            }
    }


    /**
     * 投票类型4
     * @param vote 投票基础信息
     * @param voteResult 投票结果
     * @param appid 应用id
     * @param infoid 投票结果唯一值
     * @param author 投票人员
     * @param selectid 选择的投票项
     * @return
     * 每天可以给最多几项投几票
     * 比如可以每天给最多几个人投 1/5票
     */
    private JSONResult addVoteByVoteCountLimitAndVoteTermLimit(Vote vote,VoteResult voteResult,String appid,String area,String infoid,String infotitle,String author,String selectid){

        long voteCountLimit = Long.parseLong(vote.getVotecountlimit());
        long voteTermLimit = Long.parseLong(vote.getVotetermlimit());

        Boolean hasVote = voteResultInfoService.hasVote(voteResult,author,selectid);
        return this.checkHasChance(hasVote,voteResult,vote,appid,area,infoid,infotitle,selectid,author,NOTFIRST);
    }


    /**
     * 投票类型5
     * @param vote 投票基础信息
     * @param voteResult 投票结果
     * @param appid 应用id
     * @param infoid 投票结果唯一值
     * @param author 投票人员
     * @param selectid 选择的投票项
     * @return
     * 每天投几票
     * 每天投10票（可以全投给一个人，也可以投给其他人，只能投10次）
     */
    private JSONResult addVoteByMaxCount(Vote vote,VoteResult voteResult,String appid,String area,String infoid,String infotitle,String author,String selectid){
            long voteCountLimit = Long.parseLong(vote.getVotecountlimit());
            Boolean hasVote = voteResultInfoService.hasVoteChance(voteResult.getId(),author,voteCountLimit);
            return this.checkHasChance(hasVote,voteResult,vote,appid,area,infoid,infotitle,selectid,author,NOTFIRST);
    }


    /**
     * 检查是否有机会投票
     * @param hasVote
     * @param voteResult
     * @param vote
     * @param appid
     * @param area
     * @param infoid
     * @param infotitle
     * @param selectid
     * @param author
     * @param mode
     * @return
     */
    private JSONResult checkHasChance(Boolean hasVote,VoteResult voteResult,Vote vote,String appid,String area,String infoid,String infotitle,String selectid,String author,int mode){
        if(hasVote){
            this.saveVoteResult(voteResult,vote,appid,area,infoid,infotitle,selectid,author,mode);
            return JSONResult.ok("投票成功");
        }else{
            return JSONResult.errorMsg("此项投票次数用完了");
        }
    }


    //查询投票结果
    public HashMap<String, Object> getVoteResult(String appid, String area, String infoid, String voteid) {
        HashMap<String,Object> resultMap = new HashMap<>();
        VoteResult voteResult = voteResultDao.findById(appid,area,infoid,voteid);
        if(voteResult != null){
            resultMap.put("appid", voteResult.getAppid());
            resultMap.put("infoid", voteResult.getInfoid());
            resultMap.put("voteid",voteResult.getVoteid());
            resultMap.put("options",voteResult.getVoteOptions());
        }

        return resultMap;


    }


    /**
     * 批量获取投票结果
     * @param appid
     * @param area
     * @param infoids
     * @param voteid
     * @return
     */
    public JSONResult getMulitiVoteResult(String appid, String area, String infoids, String voteid) {
        String[] infoidArr = infoids.split(",");

        ArrayList<Object>  resultList = new ArrayList<>();
        for (String infoid : infoidArr){
            resultList.add(this.getVoteResult(appid,area,infoid,voteid));
        }
        return JSONResult.ok(resultList);
    }
}
