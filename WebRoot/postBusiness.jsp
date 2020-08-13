<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
//CROS IE10以上 跨域支持
response.setHeader("Access-Control-Allow-Origin","*");
response.setHeader("Access-Control-Allow-Methods","*");
response.setContentType("application/json");
response.setCharacterEncoding("utf-8");

String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
out.print("{\"succsess\":0,\"codes\":[]}");
%>

