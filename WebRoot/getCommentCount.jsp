<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@page import="com.vcom.auxsys.util.CommonUtil"%>
<%@page import="com.vcom.auxsys.dao.VoteDao"%>
<%@page import="com.vcom.auxsys.dao.WebAppDao"%>
<%@page import="com.vcom.auxsys.util.SpringContextUtil"%>
<%@page import="com.vcom.auxsys.entitys.pojo.Comments"%>
<%@page import="org.apache.commons.lang.StringUtils"%>

<%
//CROS IE10以上 跨域支持
response.setHeader("Access-Control-Allow-Origin","*");
response.setHeader("Access-Control-Allow-Methods","*");
response.setContentType("application/json");
response.setCharacterEncoding("utf-8");

    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"+ request.getServerName() + ":" + request.getServerPort()+ path + "/";
    String param = request.getParameter("param");
    if (param == null) {param = request.getParameter("data");}
		String jsoncallback = request.getParameter("jsoncallback");
    String message = "";
    //1、判断参数是否为空
    if (param == null || param.trim().length() <= 0) {
        message="参数为空，请检查！";
    }else {
        //2、判断参数格式是否正确
        param = CommonUtil.decode4utf8(param);
        LinkedHashMap map = new LinkedHashMap();
        try{
            map = CommonUtil.getMap4Json(param);
        }catch(Exception e){
            message="参数格式有问题，请用正确的json格式，请检查！";
        }
        if("".equals(message)){
          //3、判断参数必输项是否输入
      		if(StringUtils.isBlank(String.valueOf(map.get("moduleid")))){
      		  message += "moduleid，";
      		}
      		if(StringUtils.isBlank(String.valueOf(map.get("infoid")))){
      		  message += "infoid，";
      		}
      		if(StringUtils.isBlank(String.valueOf(map.get("appraiseid")))){
      		  message += "appraiseid，";
      		}
      		if(!"".equals(message)){
      		  message += "为空，请检查！";
      		}
        	if ("".equals(message)){
      		    //4、判断该条评论对应的应用id是否存在
              WebAppDao webAppDao =(WebAppDao) SpringContextUtil.getBean("webAppDao");
              Comments comments = new Comments();
  		    		comments.setAppid(String.valueOf(map.get("moduleid")));
              message = webAppDao.checkAppSfcz(comments);
              if("".equals(message)){
               	 //5、返回查询结果
                  VoteDao voteDao = (VoteDao)SpringContextUtil.getBean("voteDao");
                  String text = voteDao.getVotePageByMap(map);
                  text = "{\"message\":'请求成功！',\"status\":1,\"root\": " + text + "}";
                  if (jsoncallback != null) {
                  	out.println(jsoncallback+"("+text+")");
                  } else {
                  	out.println(text);
                  }
              }
          }
        }
        map.clear();
        map = null;	 
    }
    if(!"".equals(message)){
	    if (jsoncallback != null) {
	        out.println(jsoncallback+"({\"root\":[],\"message\":'"+message+"',\"status\":0})");
	    } else {
	        out.println("{\"root\":[],\"message\":'"+message+"',\"status\":0}");
	    }
    }
%>

