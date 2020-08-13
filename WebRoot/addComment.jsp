<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@page import="java.text.SimpleDateFormat" %>
<%@page import="com.vcom.auxsys.util.CommonUtil"%>
<%@page import="com.vcom.auxsys.entitys.pojo.Comments"%>
<%@page import="com.vcom.auxsys.dao.CommentsDao"%>
<%@page import="com.vcom.auxsys.dao.WebAppDao"%>
<%@page import="com.vcom.auxsys.util.SensitivewordFilter"%>
<%@page import="com.vcom.auxsys.util.SpringContextUtil"%>
<%
	//CROS IE10以上 跨域支持
	response.setHeader("Access-Control-Allow-Origin","*");
	response.setHeader("Access-Control-Allow-Methods","*");
	response.setContentType("application/json");
	response.setCharacterEncoding("utf-8");
	
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
    request.setCharacterEncoding("utf-8"); // 设置post参数的编码方式
    String param = request.getParameter("param");
    if (param == null) {  param = request.getParameter("data");}
    param = CommonUtil.decode4utf8(param);
    String jsoncallback = request.getParameter("jsoncallback");
    String message = "";
    String resultContent="";
    String resultId="";
    //1、判断参数是否为空
    if (param == null || param.trim().length() <= 0) {
        message = "参数为空，请检查！";
    } else {
        //2、判断参数格式是否是json格式
        StringBuffer str = new StringBuffer();
        LinkedHashMap map = new LinkedHashMap();
        try{
            map = CommonUtil.getMap4Json(param);
        }catch(Exception e){
            message = "参数格式有问题，请用正确的json格式，异常信息："+e+"请检查！";
        }
        if("".equals(message)){ 
          	//3、判断参数中是否包含特殊字符
            str.append(map.get("moduleid"));
            str.append(map.get("code"));
            str.append(map.get("codename"));
            str.append(map.get("infoid"));
            str.append(map.get("infotitle"));
            str.append(map.get("content"));
            str.append(map.get("userid"));
            str.append(map.get("username"));
            str.append(map.get("area"));
            str.append(map.get("point"));
            str.append(map.get("remark"));
            str.append(map.get("bk2"));
            boolean cztszf = false;
            cztszf = CommonUtil.sfcztszf(str.toString());
            if(cztszf){
            		message = "所提交的数据中存在特殊字符，请检查！";
            }else{
                Comments comments = new Comments();
                comments.setAppid(String.valueOf(map.get("moduleid")));
	  	          if(map.get("code")==null || "".equals(String.valueOf(map.get("code")))){
	  	              comments.setDircode("-1001");
	  	              comments.setDirname("默认目录01");
	  	          }else{
	  	              comments.setDircode(String.valueOf(map.get("code")));
	  	              if(map.containsKey("codename")){
	  	                if("".equals(String.valueOf(map.get("codename")))){
		  	                  comments.setDirname("默认目录01");
		  	              }else{
		  	                  comments.setDirname(String.valueOf(map.get("codename")));
		  	              }
	  	              }else{
	  	                comments.setDirname("默认目录01"); 
	  	              }
	  	          }
	  	          comments.setInfoid(String.valueOf(map.get("infoid")));
	  	          comments.setInfotitle(String.valueOf(map.get("infotitle")));
	  	          comments.setContent(String.valueOf(map.get("content")));
	  	          comments.setUserid(String.valueOf(map.get("userid")));
	  	          comments.setUsername(String.valueOf(map.get("username")));
	  	          comments.setArea(String.valueOf(map.get("area")));
	  	        	if(map.containsKey("point")){
	  	            comments.setPoint(String.valueOf(map.get("point")));
	  	          }else{
	  	            comments.setPoint("");
	  	          }
	  	        	if(map.containsKey("remark")){
	  	            comments.setRemark1(String.valueOf(map.get("remark")));
	  	          }else{
	  	            comments.setRemark1("");
	  	          }
	  	          comments.setRemark2(String.valueOf(map.get("bk2")));
	  	       		comments.setIp(request.getRemoteAddr());
	  	          SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
	  	          String date = df.format(new Date());// new Date()为获取当前系统时间
	  	          comments.setCreatetime(date);
	  	        	//3、判断提交的评论信息是否完整
	  	          message = comments.checkCommentsNeeds(comments);
		  	        if("".equals(message)){
		  	       		//4、校验评论内容是否包含过滤词，若包含则状态置为未审，不包含则置为已审(改为替换为*)
			            SensitivewordFilter sensitivewordFilter = new SensitivewordFilter();
			            boolean sfcz_content = sensitivewordFilter.isContaintSensitiveWord(comments.getContent(),1);
			  	      	boolean sfcz_title = sensitivewordFilter.isContaintSensitiveWord(comments.getInfotitle(),1);
		  	       		/*
			  	      	if(sfcz_content || sfcz_title){
			                comments.setStatus("0");
			                message = "所提交的数据中存在敏感词，需要等待管理员审核！";
			            }else{
			                comments.setStatus("1");
			            }
			  	      	*/
			  	      	comments.setStatus("1");
			  	      	if(sfcz_content){
			  	      		comments.setContent(sensitivewordFilter.replaceSensitiveWord(comments.getContent(),1,"*"));
			  	      	}
			  	      	if(sfcz_title){
			  	      		comments.setInfotitle(sensitivewordFilter.replaceSensitiveWord(comments.getInfotitle(),1,"*"));
			  	      	}
			  	      	//当包含过滤词时，不入库。
			  	        if("".equals(message)){
				            //5、判断该条评论对应的应用id是否存在
				            WebAppDao webAppDao =(WebAppDao) SpringContextUtil.getBean("webAppDao");
				        		message = webAppDao.checkAppSfcz(comments);
				            if("".equals(message)){
				              	//6、插入评论信息,返回成功提示
				                CommentsDao commentsDao = (CommentsDao)SpringContextUtil.getBean("commentsDao");
				                commentsDao.insertComments(comments);
				                resultId=comments.getId();
				            }
				            resultContent=CommonUtil.getJsonHtml(comments.getContent());
			  	        }
		  	       }
        		}
    		}
        map.clear();
 				map = null;	
    }
    
  	if (jsoncallback != null) {
	  	  if("".equals(message)){
	  	    	message = "请求成功！";
	  	  		out.println(jsoncallback + "({\"status\": \"1\", \"message\": \"" + message + "\",\"content\":\""+resultContent+"\",\"id\":\""+resultId+"\"})");
	  	  }else{
	  	    	out.println(jsoncallback + "({\"status\": \"0\", \"message\": \"" + message + "\",\"content\":\""+resultContent+"\",\"id\":\"\"})");
	  	  }
    } else {
        if("".equals(message)){
  	    		message = "请求成功！";
  	    		out.println("{\"status\": \"1\", \"message\": \"" + message + "\",\"content\":\""+resultContent+"\",\"id\":\""+resultId+"\"}");
	  	  }else{
	  	    	out.println("{\"status\": \"0\", \"message\": \"" + message + "\",\"content\":\""+resultContent+"\",\"id\":\"\"}");
	  	  }
    }
%>

