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
		<title></title>
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
	   <table class="Table" width="70%" cellpadding="3" cellspacing="0" align="center">
	   <caption>投票列表<span class="headpan"><a onclick="add()">添加</a>&nbsp;&nbsp;<a >删除</a>&nbsp;&nbsp;<a onclick="">修改</a>&nbsp;&nbsp;<a onclick="">管理</a></span></caption>
	   <tr>
		    <td class="head" width="10%">序号</td>
		    <td class="head" width="20%">标题</td>
		    <td class="head" width="20%">创建时间</td>
		    <td class="head" width="10%">状态</td>
		    <td class="head" width="40%">备注</td>
	   </tr>
		 <s:iterator value="voteList" id='vote' status='st'>
		  <tr>
		    <td class="head">
                <input name="checkboxId" type="checkbox" class="checkbox" value="<s:property value="#vote.id" />"><s:property value="#st.index+1" />
		    </td>
		    <td class="head"><s:property value='#vote.title'/></td>
		    <td class="head"><s:property value='#vote.createtime'/></td>	    
		    <td class="head"><s:if test="vote.status=='0'">停用</s:if><s:else>启用</s:else></td>
		    <td class="head"><s:property value='#vote.remark'/></td>
		 </tr>   
         </s:iterator>	
         <td colspan="8" align="right" class="head"><s:property value="pageBar" escape="false" /></td>
	   </table>
	</td>
</tr>
</table>
<script type="text/javascript">
   function add(){
	   var parameter="dialogWidth:650px;dialogHeight:380px;help:no;scroll:auto;status:no";
	   var url="<%=path%>/admin/vote/vote_addOrEdit.jsp?"+"operFlag=1&id=111";
		   window.showModalDialog(url,null,parameter);
   }
   function remove(){
	   
   }
   function edit(){
	   
   }
   function manage(){
	   
   }
</script>
</body>
</html>
