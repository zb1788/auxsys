<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<% boolean goback ="true".equals(request.getParameter("goback")); %>
<%@ taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>执行结果</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0"> 
  </head>
  <body>
    <script type="text/javascript">
    <% boolean rvalue ="true".equals(request.getParameter("returnValue")); %>
    var smessage = '<s:property value="smessage" />';
    if(typeof(smessage)!="undefined" && smessage!=null && smessage.length>0 && smessage!="null"){
	    alert(smessage);
	    if(!<%= goback %>){
    		window.returnValue=false;
    	}
	}else{
    	alert("操作成功!");
    	window.returnValue=true;
    }
    if(<%= goback %>){
    	history.back();
    }else{
    	if(window.opener!=undefined){
	    	window.opener.document.getElementsByName("form1")[0].submit();
    	}
    	window.close();
    }
    </script>
  </body>
</html>
