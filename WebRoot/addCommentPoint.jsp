<%@page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.vcom.auxsys.util.CommonUtil"%>
<%@page import="com.vcom.auxsys.dao.CommentsDao"%>
<%@page import="com.vcom.auxsys.util.SpringContextUtil"%>
<%
//CROS IE10以上 跨域支持
response.setHeader("Access-Control-Allow-Origin","*");
response.setHeader("Access-Control-Allow-Methods","*");
response.setContentType("application/json");
response.setCharacterEncoding("utf-8");

String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
String commentid = request.getParameter("commentid"); //应用id*
commentid = CommonUtil.decode4utf8(commentid);
String text = null;
String jsoncallback = request.getParameter("jsoncallback");
if(commentid!=null && commentid!=""){//判断评论id是否为空
    CommentsDao commentsDao = (CommentsDao)SpringContextUtil.getBean("commentsDao");
    text = commentsDao.updateCommentPoint(commentid);
}else{
    text = "{\"totalPoint\":\""+0+"\",\"message\":'请求失败，评论id为空，请检查！',\"status\":0}";
}
if (jsoncallback != null) {
    out.println(jsoncallback + "("+text+")");
}else{
	out.println(text);
}
%>