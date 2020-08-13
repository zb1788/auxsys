<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" import="java.util.*,zzvcom.sys.service.*,com.vcom.auxsys.service.*,com.vcom.auxsys.entitys.pojo.*,zzvcom.sys.service.*,org.springframework.web.context.support.*,org.springframework.web.context.*" pageEncoding="UTF-8"%>
<%
String showType = request.getParameter("showType") == null ?"0":request.getParameter("showType");
String webappCode = request.getParameter("webappCode") == null ?"":request.getParameter("webappCode");
String showLeaf = request.getParameter("showLeaf") == null ?"1":request.getParameter("showLeaf");
String appType = request.getParameter("appType") == null ?"comments":request.getParameter("appType");
String voteid = request.getParameter("voteid") == null ?"":request.getParameter("voteid");

WebApplicationContext wac = WebApplicationContextUtils.getWebApplicationContext(session.getServletContext());
WebAppService webAppService = (WebAppService)wac.getBean("webAppService");
List<WebApp> list = null;//应用列表
if("".equals(webappCode)) {
	//获取所有应用列表
	list = webAppService.getWebAppList(null,null);
} else {
  //根据应用id获取应用列表
	WebApp webApp = webAppService.getWebAppById(webappCode);
	list = new ArrayList<WebApp>();
	list.add(webApp);
}
%><tree>
	 <% for(int i=0;i<list.size();i++){
	 	WebApp webapp = list.get(i);
	 	if ("0".equals(showType)) { //不显示checkbox
	 %>
	 <tree text="<%=webapp.getName() %>" <% if("1".equals(showLeaf)){ %>src="channelxml.jsp?appType=<%=appType%>&amp;showType=<%=showType%>&amp;webappcode=<%=webapp.getId() %>&amp;voteid=<%=voteid%>"<%} %> action="javascript:clicknode('<%=webapp.getId()%>','null','<%=webapp.getName() %>','<%=voteid%>')"/>
	 <%
	 	} else if("1".equals(showType)){ //全部显示checkbox
	  %>
	  <tree text="<%=webapp.getName() %>___<%=webapp.getId() %>" <% if("1".equals(showLeaf)){ %>src="channelxml.jsp?appType=<%=appType%>&amp;showType=<%=showType%>&amp;webappcode=<%=webapp.getId() %>&amp;voteid=<%=voteid%>"<%} %> action="javascript:clicknode('<%=webapp.getId() %>','null','<%=webapp.getName() %>','<%=voteid%>')"/>
	 <%
	 	} else if("2".equals(showType)){ //部分显示checkbox
	  %>	  
	  <tree text="<%=webapp.getName() %>" <% if("1".equals(showLeaf)){ %>src="channelxml.jsp?appType=<%=appType%>&amp;showType=<%=showType%>&amp;webappcode=<%=webapp.getId() %>&amp;voteid=<%=voteid%>"<%} %> action="javascript:clicknode('<%=webapp.getId() %>','null','<%=webapp.getName() %>','<%=voteid%>')"/>
	 <% } }%>
</tree>