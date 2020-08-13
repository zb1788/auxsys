<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
response.setHeader("Pragma", "No-Cache");
response.setHeader("Cache-Control", "No-Cache");
response.setDateHeader("Expires", 0);
String operFlag=request.getParameter("operFlag");
String caption=("0".equals(operFlag)?"添加应用":"修改应用");
String actionName=("0".equals(operFlag)?"add.action?":"edit.action?");
String action=path+"/admin/webApp/"+actionName;
%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>应用编辑页面</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<link rel="stylesheet" type="text/css" href="<%=path%>/vcomframe/css/common.css" />
		<link rel="stylesheet" id='skin' type="text/css" href="<%=path%>/vcomframe/list/message/skin/qq/ymPrompt.css" />
		<script type="text/javascript" src="<%=path%>/vcomframe/list/message/ymPrompt.js"></script>
		<script type="text/javascript" src="<%=path%>/js/jquery.js"></script>
		<script type="text/javascript" src="<%=path%>/admin/limit.js"></script>
		<base target="_self">
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
<style>
.head{border-width:0px}
</style>
<form name="form1" onsubmit="return checkinput();" method="post" action="<%=action %>">
<input type="hidden" id="webApp.id" name="webApp.id" value='<s:property value="webApp.id" />'/>
<table width="100%">
<tr>
   <td>
	  <table align="center" width="100%" class="TableForm" cellpadding="3" cellspacing="0">
		<caption><%=caption%></caption>
		<colgroup><col width="20%"></col><col width="40%"></col><col width="40%"></col></colgroup>
		<tr>
			<td class="labelTd">标题&nbsp;<span class="star">*</span></td>
			<td>
			    <input type="text" checktype="1" name="webApp.name"  id="webApp.name" onkeydown="" maxlength="30" value='<s:property value="webApp.name"/>'/>
			</td>
		</tr>
		<tr>
			<td class="labelTd">备注</td>
			<td colspan="3"  >
				<input type="text" checktype="1" name="webApp.remark" id="webApp.remark" maxlength="120" value='<s:property value="webApp.remark"/>'/>
			</td>
		</tr>
		<tr>
		   <td colspan="4" align="center">
			<input type="submit" class="button" value="[保 存]" >&nbsp;
			<input class="button" id="resetNote" type="button" value="[关 闭]" onclick="window.close();">
			</td>
		</tr>
	  </table>
	</td>
</tr>
</table>
</form>
</body>
<script type="text/javascript">
function checkinput(){
	var name=document.getElementById("webApp.name").value;
	name=name.replace(/\s+/g,"");
	if(null==name||""==name){
		ymPrompt.alert("应用名不能为空",null,null,'警告',null);
		return false;
	}
}
</script>
</html>