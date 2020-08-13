<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@page import="com.vcom.auxsys.util.CommonUtil"%>
<%@page import="com.vcom.auxsys.dao.WebAppDao"%>
<%@page import="com.vcom.auxsys.entitys.pojo.Comments"%>
<%@page import="com.vcom.auxsys.dao.CommentsDao"%>
<%@page import="com.vcom.auxsys.util.SpringContextUtil"%>
<%@page import="org.apache.commons.lang.StringUtils"%>

<%
//CROS IE10以上 跨域支持
response.setHeader("Access-Control-Allow-Origin","*");
response.setHeader("Access-Control-Allow-Methods","*");
response.setContentType("application/json");
response.setCharacterEncoding("utf-8");

	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"+ request.getServerName() + ":" + request.getServerPort()	+ path + "/";
	String param = request.getParameter("param");
  if (param == null) {  param = request.getParameter("data");}
	String jsoncallback = request.getParameter("jsoncallback");
	String message = "";
	//1、如果参数为空则给出提示
	if (param == null || param.trim().length() <= 0) {
	  message = "参数为空，请检查！";
	} else {
	  //2、判断参数格式是否是正确的json格式
    param = CommonUtil.decode4utf8(param);
    LinkedHashMap map = new LinkedHashMap();
    try{
        map = CommonUtil.getMap4Json(param);
    }catch(Exception e){
        message ="参数格式有问题，请用正确的json格式，请检查！";
    }
    //3、判断必输参数是否输入
    if("".equals(message)){
			if(StringUtils.isBlank(String.valueOf(map.get("moduleid")))){
			    message += "moduleid，";
			}
			if(StringUtils.isBlank(String.valueOf(map.get("infoid")))){
			    message += "infoid，";
			}
			if(StringUtils.isBlank(String.valueOf(map.get("start")))){
			    message += "start，";
			}
			if(StringUtils.isBlank(String.valueOf(map.get("limit")))){
			    message += "limit，";
			}
			if(!"".equals(message)){
			    message += "为空，请检查！";
			}
			if(!StringUtils.isBlank(String.valueOf(map.get("start")))){
			    boolean fnumber = CommonUtil.fnumber(String.valueOf(map.get("start")));
          if(!fnumber){
              message+="start必须录入数字，请检查。";
          } 
			}
			if(!StringUtils.isBlank(String.valueOf(map.get("limit")))){
			    boolean fnumber = CommonUtil.fnumber(String.valueOf(map.get("limit")));
          if(!fnumber){
              message+="limit必须录入数字，请检查。";
          }else{
              if(Integer.valueOf(String.valueOf(map.get("limit")))>50){
                  message+="limit不能大于50，请检查。";
              }
          } 
			}
			if ("".equals(message)){
			    //4、判断应用是否存在
			    WebAppDao webAppDao =(WebAppDao) SpringContextUtil.getBean("webAppDao");
			    Comments comments = new Comments();
			    comments.setAppid(String.valueOf(map.get("moduleid")));
			    message = webAppDao.checkAppSfcz(comments);
	        if("".equals(message)){
	          //5、根据请求参数获取评论列表
	          CommentsDao commentsDao = (CommentsDao)SpringContextUtil.getBean("commentsDao");
	          int start = Integer.valueOf(String.valueOf(map.get("start")));
						int limit = Integer.valueOf(String.valueOf(map.get("limit")));
				    if(limit>50) limit =50;
				    if(limit<=1) limit =1;
						String text = commentsDao.getCommentsPageByMap(map, start, limit);
						text=text.replace("\"commets\": ", "\"root\": " );
				        text = "{"+text+",\"message\":'请求成功！',\"status\":1}";
						if (jsoncallback != null) {
							out.println(jsoncallback+"("+text+")");
						} else {
							out.println("("+text+")");
						}
	        }
    	}
		}
		map.clear();
		map = null;
	}
	if(!"".equals(message)){
      if (jsoncallback != null) {
          out.println(jsoncallback+"({totalRow:0,status:0,message:'"+message+"',root[]})");
      } else {
          out.println("({totalRow:0,status:0,message:'"+message+"',root[]})");
      }
  }
%>

