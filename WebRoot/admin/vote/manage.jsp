<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	response.setHeader("Pragma", "No-Cache");
	response.setHeader("Cache-Control", "No-Cache");
	response.setDateHeader("Expires", 0);
%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>投票管理</title>
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="expires" content="0">
		<link rel="stylesheet" type="text/css" href="<%=path%>/vcomframe/css/common.css" />
		<link rel="stylesheet" id='skin' type="text/css" href="<%=path%>/vcomframe/list/message/skin/qq/ymPrompt.css" />
		<style type="text/css">
		.headpan{
		float:right;
		margin-right:5px;
		color:000;
		font-weight:normal
		}
		</style>
	</head>
<body scroll="auto">
<table width="100%">
 <tr>
	<td>
	   <form name="form1" action="<%=path%>/admin/vote/manage.action?" onsubmit="check()">
	   <table class="Table" width="70%" cellpadding="3" cellspacing="0" align="center">
	   <tr>
		    <td class="head" width="10%">序号</td>
		    <td class="head" width="40%">标题</td>
		    <td class="head" width="30%">创建日期</td>
		    <td class="head" width="20%">状态</td>	
	   </tr>
		 <s:iterator value="voteList" id='v' status='st'>
		  <tr>
		    <td><a name='voteList[<s:property value="#st.index" />].id'><s:property value="#st.index" /></a></td>
		    <td><s:property value='#v.title'/></td>
		    <td><s:property value='#v.createtime'/></td>
		    <s:if test="#v.status==1">
		    <td>&nbsp;&nbsp;<input type="radio" value="1" name='voteList[<s:property value="#st.index" />].status' checked="checked">启用 &nbsp;&nbsp;
		        &nbsp;&nbsp;&nbsp;&nbsp;<input type="radio" value='0'name='voteList[<s:property value="#st.index" />].status'>停用
		    </td></s:if><s:else>
		    <td>&nbsp;&nbsp;<input type="radio" value="1" name='voteList[<s:property value="#st.index" />].status' >启用 &nbsp;&nbsp;
		        &nbsp;&nbsp;&nbsp;&nbsp;<input type="radio" value='0'name='voteList[<s:property value="#st.index" />].status' checked="checked">停用
		    </s:else>
		 </tr>   
         </s:iterator>	
         <td colspan="8" align="right" class="head"><s:property value="pageBar" escape="false" /></td>
         <tr>
		   <td colspan="4" align="center">
			<input type="submit" class="button" value="[保 存]" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			<input class="button" id="resetNote" type="button" value="[关 闭]" onclick="window.close();">
			</td>
		</tr>
	   </table>
	   </form>
	</td>
</tr>
</table>
<script type="text/javascript">
</script>
</body>
</html>
