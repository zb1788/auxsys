<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>My JSP 'SessionNotValid.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<script type="text/javascript" src="<%=path%>/vcomframe/list/message/ymPrompt.js"></script>
	<link rel="stylesheet" id='skin' type="text/css" href="<%=path%>/vcomframe/list/message/skin/qq/ymPrompt.css" />

  </head>
  
  <body>
    <script type="text/javascript">
    	if(window.dialogArguments==null){
    		top.location="<%=path%>/admin/index.action";
    	}else{
    		ymPrompt.errorInfo('登录超时,请重新登录！',230,150,'提示',notvalid);
    	}
    	function notvalid(pe){
    		window.returnValue="sessionerr";
    		window.close();
    	}
    </script>
  </body>
</html>
