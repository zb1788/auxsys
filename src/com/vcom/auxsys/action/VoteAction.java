/**  
 * @Title: VoteAction.java
 * @Package com.vcom.auxsys.action
 * @Description: TODO
 * 
 * @date 2015-11-12
 */
package com.vcom.auxsys.action;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.bson.Document;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.vcom.auxsys.dao.VoteDao;
import com.vcom.auxsys.entitys.pojo.Vote;
import com.vcom.auxsys.entitys.pojo.VoteOption;
import com.vcom.auxsys.entitys.pojo.VoteResult;
import com.vcom.auxsys.service.VoteService;
import com.vcom.auxsys.util.PageUtil;
/**
 * @title VoteAction
 * @description :
 * 
 * @date 2015-11-12下午2:13:54
 */
public class VoteAction extends BaseAction {
	private VoteService voteService;
	private VoteDao voteDao;
	private Vote vote;
	private VoteResult voteResult;
	private String ids;
	private List<Vote> voteList;
	private String webappcode;//编码（目录编码 or 应用编码）
    private String channelCode;//栏目编号
    private String voteid;//投票id
    private String optionid;//排序项
	private List<VoteResult> voteResultsList; //投票结果列表
	private List<VoteOption> voteOptionsList; //投票项列表
    private String smessage;//消息提示
    private String flag;//状态
	/**
	 * @return the voteService
	 */
	public VoteService getVoteService() {
		return voteService;
	}

	/**
	 * @param voteService the voteService to set
	 */
	public void setVoteService(VoteService voteService) {
		this.voteService = voteService;
	}
	/**
	 * @Description: 进入添加界面
	 * @param @return   
	 * @return String  
	 * @throws
	 * 
	 * @date 2015-11-14
	 */
	public String toAdd(){
		this.setVote(null);
		return "SUCCESS";
	}
	/**
	 * @Description: 获取投票信息列表
	 * @param @return   
	 * @return String  
	 * @throws
	 * 
	 * @date 2015-11-12
	 */
	public String list(){	
		 //加入分页
        PageUtil page = new PageUtil();
        int count =voteService.getCount();
        page.setPageSize(pageSize);// 每页多少行
        page.setCurPage(pageIndex);//当前页
        page.setTotalRow(count);//总页数
        pageBar = page.getToolsMenu();
        if(null==vote||"".equals(vote.getTitle())){
        	setVoteList(voteService.getVoteList(null,page.getStart(),pageSize));
        }else{
        	this.voteList=this.voteService.queryVote(vote);
        }
    	return "SUCCESS";
	}
	public String search(){
		this.voteList=this.voteService.queryVote(vote);
		 //加入分页
        PageUtil page = new PageUtil();
        int count = this.voteList.size();
        page.setPageSize(pageSize);// 每页多少行
        page.setCurPage(pageIndex);//当前页
        page.setTotalRow(count);//总页数
        pageBar = page.getToolsMenu();
    	return "SUCCESS";
	}
    /**
     * @Description: 添加一条记录
     * @param @return   
     * @return String  
     * @throws
     * 
     * @date 2015-11-14
     */
    public String add(){
    	if(null==vote)return "FALSE";
    	SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
    	Date d=new Date();
    	vote.setCreatetime(df.format(d));
    	smessage=voteService.insertVoto(vote);
    	if("success".equals(smessage))
    		smessage=null;
        return "SUCCESS";
    }
    /**
     * @Description: 删除
     * @param @return   
     * @return String  
     * @throws
     * 
     * @date 2015-11-14
     */
    public String delete(){
    	if(null==ids||"".equals(ids))
    		return "FALSE";
    	String[] idstr=ids.split(",");
    	voteService.deleteVoto(idstr);
    	return "SUCCESS";
    }
    /**
     * @Description: 进入修改界面
     * @param @return   
     * @return String  
     * @throws
     * 
     * @date 2015-11-14
     */
    public String toEdit(){
    	if(null==vote.getId()||"".equals(vote.getId()))
    		return "FALSE";
    	vote=voteService.getVote(vote);
    	boolean hasResults=voteService.hasResult(vote.getId());
    	if(hasResults){
    		request.setAttribute("hasResults", "true");
    	}else{
    		request.setAttribute("hasResults", "false");
    	}
    	
    	return "SUCCESS";
    }
    /**
     * @Description: 修改
     * @param @return   
     * @return String  
     * @throws
     * 
     * @date 2015-11-14
     */
    public String edit(){
    	smessage=this.voteService.editVoto(vote);
    	if("success".equals(smessage))
    		smessage=null;
    	return "SUCCESS";
    }
    /**
     * @Description: 管理投票定义
     * 
     * @date 2015-11-19
     */
    public String manage(){
    	String[] idstr=ids.split(",");
    	List<String> list=Arrays.asList(idstr);
    	setVoteList(voteService.getVoteList(list,null,null));
    	for(Vote v:voteList){
    		v.setStatus(flag);
    	}
    	this.voteService.manageVote(voteList);
    	return "SUCCESS";
    }
    /**
     * @Description: TODO
     *  查看投票结果个数
     *  返回json
     * 
     * @date 2016-1-20
     */
    public void getVoteResultCount(){
    	
    }
    /**
     * @description：根据目录编码或应用编码获取投票结果列表，返回成功列表页面
     * @time： 2015-11-17
     * @author：donghaoyu
     * @return 
     */
    public String getAllVoteLists(){
    	Document doc = new Document();
        doc.put("appid", this.getWebappcode());
        if(!this.getChannelCode().equals("null")){
            try {
                doc.put("dircode", java.net.URLDecoder.decode(this.getChannelCode(), "utf-8"));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        if(voteid!=null && !"".equals(voteid)){
            doc.put("voteid", this.getVoteid());
        }
        //加入查询条件
        if(voteResult!=null){
            if(!"".equals(voteResult.getInfoid())){
                Pattern pattern = Pattern.compile("^.*" + voteResult.getInfoid()+ ".*$", // as SQL:  like " '%" + personName + "%' "  
                Pattern.CASE_INSENSITIVE); //忽略大小写
                doc.put("infoid", pattern);
            }
            if(!"".equals(voteResult.getInfotitle())){
                Pattern pattern = Pattern.compile("^.*" + voteResult.getInfotitle()+ ".*$", // as SQL:  like " '%" + personName + "%' "  
                Pattern.CASE_INSENSITIVE); //忽略大小写
                doc.put("infotitle", pattern);
            }
            if(!"".equals(voteResult.getArea())){
                Pattern pattern = Pattern.compile("^.*" + voteResult.getArea()+ ".*$", // as SQL:  like " '%" + personName + "%' "  
                Pattern.CASE_INSENSITIVE); //忽略大小写
                doc.put("area", pattern);
            }
        }
        //如果排序项不为空则加入排序项
        if(optionid!=null && !"".equals(optionid)){
            voteDao.editVoteResultOrder(optionid,this.getVoteid(),this.getWebappcode());
        }
        //加入分页
        PageUtil page = new PageUtil();
        int count = voteDao.getVoteResultCount(doc);
        page.setPageSize(pageSize);// 每页多少行
        page.setCurPage(pageIndex);//当前页
        page.setTotalRow(count);//总页数
        pageBar = page.getToolsMenu();
        Map pMap = new HashMap();
        pMap.put("start", page.getStart());
        pMap.put("end", pageSize);
        voteResultsList = voteDao.getVoteResultsList(doc,optionid,pMap);
        this.setVoteResultsList(voteResultsList);
        Vote vote = new Vote();
        vote.setId(voteid);
        vote = voteDao.getVote(vote);
        this.setVoteOptionsList(vote.getOptions());
        return "success";
    }
    
	/**
	 * @return the vote
	 */
	public Vote getVote() {
		return vote;
	}

	/**
	 * @param vote the vote to set
	 */
	public void setVote(Vote vote) {
		this.vote = vote;
	}

	/**
	 * @return the voteList
	 */
	public List<Vote> getVoteList() {
		return voteList;
	}

	/**
	 * @param voteList the voteList to set
	 */
	public void setVoteList(List<Vote> voteList) {
		this.voteList = voteList;
	}

	/**
	 * @return the ids
	 */
	public String getIds() {
		return ids;
	}

	/**
	 * @param ids the ids to set
	 */
	public void setIds(String ids) {
		this.ids = ids;
	}

    public List<VoteResult> getVoteResultsList() {
        return voteResultsList;
    }

    public void setVoteResultsList(List<VoteResult> voteResultsList) {
        this.voteResultsList = voteResultsList;
    }

    public String getWebappcode() {
        if(webappcode==null || "".equals(webappcode)){
            webappcode = this.request.getParameter("webappCode");
        }
        return webappcode;
    }

    public void setWebappcode(String webappcode) {
        this.webappcode = webappcode;
    }

    public VoteDao getVoteDao() {
        return voteDao;
    }

    public void setVoteDao(VoteDao voteDao) {
        this.voteDao = voteDao;
    }

    public VoteResult getVoteResult() {
        return voteResult;
    }

    public void setVoteResult(VoteResult voteResult) {
        this.voteResult = voteResult;
    }

    public String getOptionid() {
        if(optionid==null || "".equals(optionid)){
            optionid = this.request.getParameter("optionid");
        }
        return optionid;
    }

    public void setOptionid(String optionid) {
        this.optionid = optionid;
    }
	/**
	 * @return the message
	 */
	public String getSmessage() {
		return smessage;
	}

	/**
	 * @param message the message to set
	 */
	public void setSmessage(String smessage) {
		this.smessage = smessage;
	}

    public String getVoteid() {
        if(voteid==null || "".equals(voteid)){
            voteid = this.request.getParameter("voteid");
        }
        return voteid;
    }

    public void setVoteid(String voteid) {
        this.voteid = voteid;
    }

	/**
	 * @return the flag
	 */
	public String getFlag() {
		return flag;
	}

	/**
	 * @param flag the flag to set
	 */
	public void setFlag(String flag) {
		this.flag = flag;
	}
	
    public List<VoteOption> getVoteOptionsList() {
        return voteOptionsList;
    }

    public void setVoteOptionsList(List<VoteOption> voteOptionsList) {
        this.voteOptionsList = voteOptionsList;
    }

    public String getChannelCode() {
        if(channelCode==null || "".equals(channelCode)){
            channelCode = this.request.getParameter("channelCode");
        }
        return channelCode;
    }

    public void setChannelCode(String channelCode) {
        this.channelCode = channelCode;
    }
}