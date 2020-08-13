<%@page import="com.vcom.auxsys.entitys.pojo.Vote"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@page import="com.vcom.auxsys.util.CommonUtil"%>
<%@page import="java.text.SimpleDateFormat" %>
<%@page import="com.vcom.auxsys.entitys.pojo.VoteResult"%>
<%@page import="com.vcom.auxsys.dao.VoteDao"%>
<%@page import="com.vcom.auxsys.dao.WebAppDao"%>
<%@page import="com.vcom.auxsys.util.SpringContextUtil"%>
<%@page import="com.vcom.auxsys.entitys.pojo.Comments"%>
<%@page import="com.vcom.auxsys.entitys.pojo.VoteOption"%>

<%
	//CROS IE10以上 跨域支持
	response.setHeader("Access-Control-Allow-Origin","*");
	response.setHeader("Access-Control-Allow-Methods","*");
	response.setContentType("application/json");
	response.setCharacterEncoding("utf-8");
	
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
    request.setCharacterEncoding("utf-8"); // 设置post参数的编码方式
	String voteid = request.getParameter("voteid");
	voteid = CommonUtil.decode4utf8(voteid);
    String jsoncallback = request.getParameter("jsoncallback");
    String message = "";
	String result = "";
    //1、判断参数是否为空
    if (voteid == null || voteid.trim().length() <= 0) {
        message = "参数为空，请检查！";
    } else {
        //2、判断参数格式是否是json格式
        StringBuffer str = new StringBuffer();
        LinkedHashMap map = new LinkedHashMap();
        if("".equals(message)){
        	VoteDao voteDao =(VoteDao) SpringContextUtil.getBean("voteDao");
        	Vote avote= new Vote();
        	avote.setId(voteid);
        	try{
        	avote=voteDao.getVote(avote);
        	}catch(Exception e){
        		message="查询错误，请检查参数";
        	}
        	if(avote!=null){
	        	net.sf.json.JSONObject jsonobj= net.sf.json.JSONObject.fromObject(avote);
				result = jsonobj.toString();
        	}else{
        		message="数据不存在!";
        	}
        }
    }
    if (jsoncallback != null) {
	  	  if("".equals(message)){
	  	    	message = "执行成功！";
	  	  		out.println(jsoncallback + "({\"status\": \"1\", \"info\":"+result+",\"message\": \"" + message + "\"})");
	  	  }else{
	  	    	out.println(jsoncallback + "({\"status\": \"0\", \"info\":null, \"message\": \"" + message + "\"})");
	  	  }
    } else {
        if("".equals(message)){
  	    		message = "执行成功！";
  	    		out.println("{\"status\": \"1\", \"info\":"+result+", \"message\": \"" + message + "\"}");
	  	  }else{
	  	    	out.println("{\"status\": \"0\",  \"info\":null,\"message\": \"" + message + "\"}");
	  	  }
    }
%>

