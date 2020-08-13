<?xml version="1.0" encoding="utf-8" ?>
<%@ page language="java" import="java.util.*,zzvcom.sys.service.*,com.vcom.auxsys.service.*,com.vcom.auxsys.entitys.pojo.*,zzvcom.sys.service.*,org.springframework.web.context.support.*,org.springframework.web.context.*" pageEncoding="utf-8"%>
<%
String showType = request.getParameter("showType") == null ?"0":request.getParameter("showType");
String webappCode = request.getParameter("webappcode");
String appType = request.getParameter("appType") == null ?"comments":request.getParameter("appType");
String voteid = request.getParameter("voteid") == null ?"":request.getParameter("voteid");

WebApplicationContext wac = WebApplicationContextUtils.getWebApplicationContext(session.getServletContext());
WebAppService webAppService = (WebAppService)wac.getBean("webAppService");
List<WebApp> list = null; //目录列表
if(webappCode !=null){
    if("comments".equals(appType)){
      //根据应用id从评价集合中聚合目录列表
      list = webAppService.getDirListByComments(webappCode);
    }else{
      //根据应用id从投票集合中聚合目录列表
      list = webAppService.getDirListByVote(webappCode,voteid);
    }
} 
%>
<tree>
	 <% for(int i=0;i<list.size();i++){
	     WebApp channel = list.get(i);
	 	if ("0".equals(showType)) { //不显示checkbox
	 %>
	 <tree text="<%=channel.getName() %>" action="javascript:clicknode('<%=webappCode %>','<%=channel.getId() %>','<%=channel.getName() %>','<%=voteid%>')"/>
	 <%
	 	} else if("1".equals(showType)){ //全部显示checkbox
	  %>
	  <tree text="<%=channel.getName() %>___<%=channel.getId() %>"  action="javascript:clicknode('<%=webappCode %>','<%=channel.getId() %>','<%=channel.getName() %>','<%=voteid%>')"/>
	 <%
	 	} else if("2".equals(showType)){ //部分显示checkbox
	  %>	  
	  <tree  text="<%=channel.getName() %>___<%=channel.getId() %>" action="javascript:clicknode('<%=webappCode %>','<%=channel.getId() %>','<%=channel.getName() %>','<%=voteid%>')"/>
	 <% } }%>
</tree>