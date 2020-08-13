<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%><%@page import="com.vcom.auxsys.util.CommonUtil,com.vcom.auxsys.dao.WebAppDao,com.vcom.auxsys.entitys.pojo.Comments,com.vcom.auxsys.dao.CommentsDao,com.vcom.auxsys.util.SpringContextUtil,org.apache.commons.lang.StringUtils"%><%
//CORS IE10以上 跨域支持
response.setHeader("Access-Control-Allow-Origin","*");
response.setHeader("Access-Control-Allow-Methods","*");
response.setContentType("application/json");
response.setCharacterEncoding("utf-8");

	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"+ request.getServerName() + ":" + request.getServerPort()	+ path + "/";
	String param = request.getParameter("param");
	

    String appid = request.getParameter("appid");
    String infoids = request.getParameter("infoids");
    String code = request.getParameter("code");

	String jsoncallback = request.getParameter("jsoncallback");
	String message = "";
	if (appid == null || appid.trim().length() <= 0 || infoids == null || infoids.trim().length() <= 0 ) {
	        message="参数为空，请检查！";
	}else{
      	//3、判断参数必输项是否输入
      	LinkedHashMap<String,String> map=new LinkedHashMap<String,String>();
      	map.put("moduleid", appid);
      	if(code!=null){
      		map.put("code", code);
      	}
      	String[] infoarr = infoids.split(",");
      	if(infoarr==null){
      		message="infoids参数为错误，请检查！";
      	}
		//检查应用id
		if ("".equals(message)){
		    //4、判断应用是否存在
		    WebAppDao webAppDao =(WebAppDao) SpringContextUtil.getBean("webAppDao");
		    Comments comments = new Comments();
		    comments.setAppid(String.valueOf(appid));
		    message = webAppDao.checkAppSfcz(comments);
   		}
		//获取结果
        if("".equals(message)){
          	
          	StringBuffer tsb=new StringBuffer();
			int start = 1;
			int limit = 50;
			//5、根据请求参数获取评论列表
			CommentsDao commentsDao = (CommentsDao)SpringContextUtil.getBean("commentsDao");

          	for(String infoid : infoarr){
              	map.put("infoid", infoid);
				//默认前50条
			    //if(limit<=1) limit =1;
				String text = commentsDao.getCommentsPageByMap(map, start, limit);
				text="{\"infoid\":\""+infoid+"\","+text+"}";
				if(tsb.length()>0){
					tsb.append(",");
				}
				tsb.append(text);
          	}
			if (jsoncallback != null) {
				out.println(jsoncallback+"("+tsb.toString()+")");
			} else {
				out.println("["+tsb.toString()+"]");
			}
	    }
		map.clear();
		map = null;
	}
	if(!"".equals(message)){
      if (jsoncallback != null) {
          out.println(jsoncallback+"({totalRow:0,status:0,message:'"+message+"',root[]})");
      } else {
          out.println("{totalRow:0,status:0,message:'"+message+"',root[]}");
      }
	}
%>