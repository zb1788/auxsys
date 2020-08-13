<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%><%@page import="com.vcom.auxsys.util.CommonUtil,com.vcom.auxsys.dao.VoteDao,com.vcom.auxsys.dao.WebAppDao,com.vcom.auxsys.util.SpringContextUtil"%><%@page import="com.vcom.auxsys.entitys.pojo.Comments"%><%@page import="org.apache.commons.lang.StringUtils"%><%
//CROS IE10以上 跨域支持
response.setHeader("Access-Control-Allow-Origin","*");
response.setHeader("Access-Control-Allow-Methods","*");
response.setContentType("application/json");
response.setCharacterEncoding("utf-8");

    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"+ request.getServerName() + ":" + request.getServerPort()+ path + "/";

    String appid = request.getParameter("appid");
    String voteid = request.getParameter("voteid");
    String infoids = request.getParameter("infoids");

	String jsoncallback = request.getParameter("jsoncallback");
		
    String message = "";
    //1、判断参数是否为空
    if (appid == null || appid.trim().length() <= 0 || voteid == null || voteid.trim().length() <= 0 ||infoids == null || infoids.trim().length() <= 0 ) {
        message="参数为空，请检查！";
    }else {
        if("".equals(message)){
          	//3、判断参数必输项是否输入
          	LinkedHashMap<String,String> map=new LinkedHashMap<String,String>();
          	map.put("moduleid", appid);
          	map.put("appraiseid", voteid);
          	String[] infoarr = infoids.split(",");
          	if(infoarr==null){
          		message="infoids参数为错误，请检查！";
          	}
          	
          	StringBuffer tsb=new StringBuffer();
          	
          	for(String infoid : infoarr){
              	map.put("infoid", infoid);
      		    //4、判断该条评论对应的应用id是否存在
              	WebAppDao webAppDao =(WebAppDao) SpringContextUtil.getBean("webAppDao");
              	Comments comments = new Comments();
              	comments.setAppid(String.valueOf(map.get("moduleid")));
              	message = webAppDao.checkAppSfcz(comments);
              	//5、返回查询结果
                VoteDao voteDao = (VoteDao)SpringContextUtil.getBean("voteDao");
                String text = voteDao.getVotePageByMap(map);
                if(tsb.length()>0){
                	tsb.append(",");
                }
                tsb.append("{\"infoid\":\""+infoid+"\",\"options\":"+text+"}");
            }
          	if (jsoncallback != null) {
              	out.println(jsoncallback+"(["+tsb.toString()+"])");
            } else {
              	out.println("["+tsb.toString()+"]");
            }
            map.clear();
            map = null;
        }
    }
    if(!"".equals(message)){
	    if (jsoncallback != null) {
	        out.println(jsoncallback+"({\"root\":[],\"message\":'"+message+"',\"status\":0})");
	    } else {
	        out.println("{\"root\":[],\"message\":'"+message+"',\"status\":0}");
	    }
    }
%>