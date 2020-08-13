<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@page import="java.text.SimpleDateFormat" %>
<%@page import="com.vcom.auxsys.util.CommonUtil"%>
<%@page import="com.vcom.auxsys.entitys.pojo.Comments"%>
<%@page import="com.vcom.auxsys.dao.WebAppDao"%>
<%@page import="com.vcom.auxsys.dao.CommentsDao"%>
<%@page import="com.vcom.auxsys.util.SensitivewordFilter"%>
<%@page import="com.vcom.auxsys.util.SpringContextUtil"%>
<%@page import="org.apache.commons.lang.StringUtils"%>
<%@page import="java.net.URLEncoder"%>

<%

//CROS IE10以上 跨域支持
response.setHeader("Access-Control-Allow-Origin","*");
response.setHeader("Access-Control-Allow-Methods","*");
response.setCharacterEncoding("utf-8");

	request.setCharacterEncoding("utf-8");
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()  + path + "/";
    StringBuffer str = new StringBuffer();
    String moduleid = request.getParameter("moduleid"); //应用id*
    moduleid = CommonUtil.decode4utf8(moduleid);
    str.append(moduleid);
    String code = request.getParameter("code");//目录id
    code =CommonUtil.decode4utf8(code);
    str.append(code);
    String codename = request.getParameter("codename");//目录名称
    codename = CommonUtil.decode4utf8(codename);
    str.append(codename);
    String infoid = request.getParameter("infoid"); //信息id*
    infoid = CommonUtil.decode4utf8(infoid);
    str.append(infoid);
    String infotitle = request.getParameter("infotitle");//信息标题*
    infotitle = CommonUtil.decode4utf8(infotitle);
    str.append(infotitle);
    String content = request.getParameter("content");//评论内容*
    content = CommonUtil.decode4utf8(content);
    str.append(content);
    String userid = request.getParameter("userid");//用户id
    userid = CommonUtil.decode4utf8(userid);
    str.append(userid);
    String username = request.getParameter("username");//用户名称*
    username = CommonUtil.decode4utf8(username);
    str.append(username);
    String area = request.getParameter("area");//用户地区
    area = CommonUtil.decode4utf8(area);
    str.append(area);
    String point = request.getParameter("point");//打分
    point = CommonUtil.decode4utf8(point);
    str.append(point);
    String successurl = request.getParameter("successurl");//成功跳转链接*
    successurl = CommonUtil.decode4utf8(successurl);
    String errorurl = request.getParameter("errorurl");//失败跳转链接*
    errorurl = CommonUtil.decode4utf8(errorurl);
    String remark = request.getParameter("remark"); //备注
    if (!StringUtils.isBlank(remark)) {
        remark = CommonUtil.decode4utf8(remark);
        str.append(remark);
    }
    String bk2 = request.getParameter("bk2"); //备注2
    if (!StringUtils.isBlank(bk2)) {
        bk2 = CommonUtil.decode4utf8(bk2);
        str.append(bk2);
    }
    boolean cztszf = true;
    if(str.toString()!=null){
        cztszf = CommonUtil.sfcztszf(str.toString());
    }
    if(cztszf){
        response.sendRedirect(errorurl + "?message="+URLEncoder.encode("所提交的数据中存在特殊字符，请检查！","utf-8"));
    }else{
	    //组装评论类
	    Comments comments = new Comments();
	    if (moduleid != null && moduleid.trim().length() > 0) {
	        comments.setAppid(moduleid);
	    }else{
	        comments.setAppid("");
	    }
	    if (code != null && code.trim().length() > 0) {
	        comments.setDircode(code);
	        if (codename != null && codename.trim().length() > 0) {
	            comments.setDirname(codename);
	        }else{
	            comments.setDirname("默认目录01");
	        }
	    }else{
	        comments.setDircode("-1001");
	        comments.setDirname("默认目录01");
	    }
	    if (infoid != null && infoid.trim().length() > 0) {
	        comments.setInfoid(infoid);
	    }else{
	        comments.setInfoid("");
	    }
	    if (infotitle != null && infotitle.trim().length() > 0) {
	        comments.setInfotitle(infotitle);
	    }else{
	        comments.setInfotitle("");
	    }
	    if (content != null && content.trim().length() > 0) {
	        comments.setContent(content);
	    }else{
	        comments.setContent("");
	    }
	    if (userid != null && userid.trim().length() > 0) {
	        comments.setUserid(userid);
	    }else{
	        comments.setUserid("");
	    }
	    if (username != null && username.trim().length() > 0) {
	        comments.setUsername(username);
	    }else{
	        comments.setUsername("");
	    }
	    if (area != null && area.trim().length() > 0) {
	        comments.setArea(area);
	    }else{
	        comments.setArea("");
	    }
	    if (point != null && point.trim().length() > 0) {
	        comments.setPoint(point);
	    }else{
	        comments.setPoint("");
	    }
	    comments.setIp(request.getRemoteAddr());
	    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
	    String date = df.format(new Date());// new Date()为获取当前系统时间
	    comments.setCreatetime(date);
	    
	    comments.setRemark1(remark);
	    comments.setRemark2(bk2);
	    //1、判断提交的评论信息是否完整
	    String message = null;
	    message = comments.checkCommentsNeeds(comments);
	    if( message=="" || message==null){
	      //2、校验评论内容是否包含过滤词，若包含则状态置为未审，不包含则置为已审
	      SensitivewordFilter sensitivewordFilter = new SensitivewordFilter();
	      boolean sfcz_content = sensitivewordFilter.isContaintSensitiveWord(comments.getContent(),1);
	      boolean sfcz_title = sensitivewordFilter.isContaintSensitiveWord(comments.getInfotitle(),1);
	      if(sfcz_content || sfcz_title){
	          comments.setStatus("0");
	      }else{
	          comments.setStatus("1");
	      }
	      //3、判断该条评论对应的应用id是否存在
	      WebAppDao webAppDao =(WebAppDao) SpringContextUtil.getBean("webAppDao");
	      message = webAppDao.checkAppSfcz(comments);
	      if( message=="" ||message==null){
	          //4、插入评论信息,然后跳转到成功页面
	          CommentsDao commentsDao = (CommentsDao)SpringContextUtil.getBean("commentsDao");
	          commentsDao.insertComments(comments);
	          response.sendRedirect(successurl);
	      }else{
	          response.sendRedirect(errorurl + "?message="+URLEncoder.encode(message,"utf-8"));
	      }
	    }else{
	        response.sendRedirect(errorurl + "?message="+URLEncoder.encode(message,"utf-8"));
	    }
    } 
%>

