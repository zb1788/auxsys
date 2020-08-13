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
    String param = request.getParameter("param");
    if (param == null) {param = request.getParameter("data");}
    param = CommonUtil.decode4utf8(param);
    String jsoncallback = request.getParameter("jsoncallback");
    String message = "";
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
          	//3、判断是否存在特殊字符
            str.append(map.get("appid"));
            str.append(map.get("dircode"));
            str.append(map.get("dirname"));
            str.append(map.get("infoid"));
            str.append(map.get("infotitle"));
            str.append(map.get("voteid"));
            str.append(map.get("area"));
            str.append(map.get("opts"));
            boolean cztszf = false;
            cztszf = CommonUtil.sfcztszf(str.toString());
            if(cztszf){
                message = "所提交的数据中存在特殊字符，请检查！";
            }else{
                Map<String, Class> classMap = new HashMap<String, Class>();
	    	        classMap.put("voteOptions", VoteOption.class);
	    	        VoteResult voteResult = new VoteResult();
	    	        try{
	    		        voteResult = (VoteResult) CommonUtil.getObject3JsonString(param,VoteResult.class,classMap);
	    	        }catch(Exception e){
	    	            message+="参数格式有问题，请用正确的json格式，请检查！";
	    	        }
	    	        if("".equals(message)){
	    	            if(null==voteResult.getDircode() || "".equals(voteResult.getDircode())){
	    		  	      	  voteResult.setDircode("-1001");
	    		  	      	  voteResult.setDirname("默认目录01");
	    		  	      	}
	    		  	        //4、判断提交的投票结果信息是否完整
	    		  	        voteResult.setOrder(0);
	    		  	        message = voteResult.checkVoteNeeds(voteResult);
	    		  	        if("".equals(message)){
	    		  	        		//5、判断该条投票对应的应用id是否存在
	    			  	          WebAppDao webAppDao =(WebAppDao) SpringContextUtil.getBean("webAppDao");
	    			  	          Comments comments = new Comments();
	    			  	    		  comments.setAppid(voteResult.getAppid());
	    			  	          message = webAppDao.checkAppSfcz(comments);
	    			  	          if("".equals(message)){
	    			  	          		//6、判断提交的投票id及投票项是否匹配 
	    			  	              VoteDao voteDao = (VoteDao)SpringContextUtil.getBean("voteDao");
	    			  	              message = voteDao.checkVoteSfcz(voteResult);
	    			  	              if("".equals(message)){
	    			  	              		//7、插入投票结果信息,然后返回成功消息
	    			  	                	SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
	    			  	                	String date = df.format(new Date());// new Date()为获取当前系统时间
	    			  	                	voteResult.setUpdatetime(date);
	    			  	                  voteDao.insertVoteResult(voteResult);
	    			  	              }
	    			  	          }
	    		  	        }
	    	        }
            }
        }
    }
    if (jsoncallback != null) {
	  	  if("".equals(message)){
	  	    	message = "添加投票结果成功！";
	  	  		out.println(jsoncallback + "({\"status\": \"1\", \"message\": \"" + message + "\"})");
	  	  }else{
	  	    	out.println(jsoncallback + "({\"status\": \"0\", \"message\": \"" + message + "\"})");
	  	  }
    } else {
        if("".equals(message)){
  	    		message = "添加投票结果成功！";
  	    		out.println("{\"status\": \"1\", \"message\": \"" + message + "\"}");
	  	  }else{
	  	    	out.println("{\"status\": \"0\", \"message\": \"" + message + "\"}");
	  	  }
    }
%>

