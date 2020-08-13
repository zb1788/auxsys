<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%><%@page import="java.text.SimpleDateFormat,com.vcom.auxsys.util.CommonUtil,com.vcom.auxsys.dao.CommentsDao,com.vcom.auxsys.util.SpringContextUtil"%><%
	//CROS IE10以上 跨域支持
	response.setHeader("Access-Control-Allow-Origin","*");
	response.setHeader("Access-Control-Allow-Methods","*");
	response.setContentType("application/json");
	response.setCharacterEncoding("utf-8");
	
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
    request.setCharacterEncoding("utf-8"); // 设置post参数的编码方式
    String commentid = request.getParameter("commentid");

    String jsoncallback = request.getParameter("jsoncallback");
    String message = "";
    //1、判断参数是否为空
    if (commentid == null || commentid.trim().length() <= 0) {
        message = "参数为空，请检查！";
    } else {
    	CommentsDao dao =(CommentsDao) SpringContextUtil.getBean("commentsDao");
    	dao.delComments(commentid);
    }
    
  	if (jsoncallback != null) {
	  	  if("".equals(message)){
	  	    	message = "请求成功！";
	  	  		out.println(jsoncallback + "({\"status\": \"1\", \"message\": \"" + message + "\"})");
	  	  }else{
	  	    	out.println(jsoncallback + "({\"status\": \"0\", \"message\": \"" + message + "\"})");
	  	  }
    } else {
        if("".equals(message)){
  	    		message = "请求成功！";
  	    		out.println("{\"status\": \"1\", \"message\": \"" + message + "\"}");
	  	  }else{
	  	    	out.println("{\"status\": \"0\", \"message\": \"" + message + "\"}");
	  	  }
    }
%>

