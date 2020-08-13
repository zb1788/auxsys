package com.vcom.auxsys.controller;

import com.vcom.auxsys.pojo.JSONResult;
import com.vcom.auxsys.service.VoteResultService;
import com.vcom.auxsys.util.IpUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * 投票接口类
 */
@RestController
@RequestMapping("vote")
public class VoteResultController {
    public static final Logger logger = LoggerFactory.getLogger(VoteResultController.class);

    @Autowired
    VoteResultService voteResultService;



    @RequestMapping("test")
    public JSONResult test(HttpServletRequest request, @RequestParam("abc") String abc){
        String ip = IpUtil.getIpAddr(request);
        try {
            System.out.println(URLDecoder.decode(URLDecoder.decode(abc,"UTF-8"),"UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        System.out.println(abc);
        HashMap map = new HashMap();
        //map.put("a","x");
        ArrayList list = new ArrayList();
        list.add("a");
        return JSONResult.ok(map);
    }


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
    @RequestMapping("addVote")
    public JSONResult addVote(
            HttpServletRequest request,
            @RequestParam("appid") String appid,
            @RequestParam(name="area",required = false,defaultValue = "") String area,
            @RequestParam("infoid") String infoid,
            @RequestParam("infotitle") String infotitle,
            @RequestParam("voteid") String voteid,
            @RequestParam(name="author",required = false,defaultValue = "") String author,
            @RequestParam("selectid") String selectid){


        return voteResultService.addVote(request,appid,area,voteid,infoid,infotitle,selectid,author);

    }


    /**
     * 获取单个投票结果
     * @param appid 应用id
     * @param area 区域
     * @param infoid 投票结果唯一值
     * @param voteid 投票基础表的id
     * @return
     */
    @RequestMapping("getVoteResult")
    public JSONResult getVoteResult(
            @RequestParam("appid") String appid,
            @RequestParam(name="area",required = false,defaultValue = "") String area,
            @RequestParam("infoid") String infoid,
            @RequestParam("voteid") String voteid){
        return JSONResult.ok(voteResultService.getVoteResult(appid,area,infoid,voteid));
    }


    /**
     * 批量获取投票结果
     * @param appid 应用id
     * @param area 区域
     * @param infoids 投票结果唯一值集合（逗号分割）
     * @param voteid 投票基础表的id
     * @return
     */
    @RequestMapping("getMulitiVoteResult")
    public JSONResult getMulitiVoteResult(
            @RequestParam("appid") String appid,
            @RequestParam(name="area",required = false,defaultValue = "") String area,
            @RequestParam("infoids") String infoids,
            @RequestParam("voteid") String voteid){

        return voteResultService.getMulitiVoteResult(appid,area,infoids,voteid);
    }










}
