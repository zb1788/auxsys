<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		
	    <title>vote编辑页面</title>
	    
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<link rel="stylesheet" type="text/css" href="<%=path%>/vcomframe/css/common.css" />
		<link rel="stylesheet" id='skin' type="text/css" href="<%=path%>/vcomframe/list/message/skin/qq/ymPrompt.css" />
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
<form name="form1" onsubmit="return checkinput();" method="post" action="<%=path%>/admin/vote/add.action?" >
<input type="hidden" id="vote.id" name="vote.id" >
<table width="100%">
<tr>
   <td>
	  <table align="center" width="100%" class="TableForm" cellpadding="3" cellspacing="0">
		<caption>添加投票</caption>
		<colgroup><col width="15%"></col><col width="35%"></col><col width="15%"></col></colgroup>
		<tr>
			<td class="labelTd">标题&nbsp;<span class="star">*</span></td>
			<td><input type="text" name="vote.title"  id="vote.title" maxlength="50" />
			</td>
			<td class="labelTd">状态</td>
			<td><select name="vote.status" id="vote.status"><option value="0">启用</option><option value="1">停用</option></select>
			</td>
		</tr>
		<tr>
			<td class="labelTd">备注</td>
			<td colspan="3"  >
				<input type="text" name="vote.remark" id="vote.remark" maxlength="120"  />
			</td>
		</tr>
		<tr><td colspan="5" style="padding:0px">
		   <table  class="Table" id="vote.options">
		     <caption>选项列表<span class="headpan"><a onclick="addOption()">增加投票项</a></span></caption>
		     <tr >
		        <td class="head" style="padding-top:0px;padding-bottom:0px;" width="10%">序号</td>
		        <td class="head" style="padding-top:0px;padding-bottom:0px;" width="10%">ID(*)</td>
		        <td class="head" style="padding-top:0px;padding-bottom:0px;" width="30%">标题(*)</td>
		        <td class="head" style="padding-top:0px;padding-bottom:0px;" width="40%">备注</td>
		        <td class="head" style="padding-top:0px;padding-bottom:0px;" width="10%">管理</td>
	         </tr>
	         <tr name="option">
		        <td><input type="text" name="vote.opptions[0].optionOrder" value="1"></td>
		        <td><input type="text" name="vote.opptions[0].optionId"></td>
		        <td><input type="text" name="vote.opptions[0].optionName"></td>
		        <td><input type="text" name="vote.opptions[0].optionRemark"></td>
		        <td><a onclick="deleteOption(this)">删除</a></td>
	         </tr>
		   </table>	
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
var table=document.getElementById("vote.options");
function addOption(){//添加选项
	var order=getMaxOrder();//生成序号  最大值加一
	
	var row = document.createElement("tr");  //创建一个行
    row.setAttribute("name", "option");//设置行属性
    
    var cell = document.createElement("td");  //创建一个单元格
    var cellValue='<input type="text" name="vote.opptions["'+order+'"].optionOrder" value="'+order+'" disabled="true">';
    cell.innerHTML=cellValue;  //设置单元格属性
    row.appendChild(cell);  //将单元格添加到行里
    
    cell = document.createElement("td");  //创建一个单元格
    cellValue='<input type="text" name="vote.opptions["'+order+'"].optionId">';
    cell.innerHTML=cellValue;  //设置单元格属性
    row.appendChild(cell);  //将单元格添加到行里
    
    cell = document.createElement("td");  //创建一个单元格
    cellValue='<input type="text" name="vote.opptions["'+order+'"].optionName">';
    cell.innerHTML=cellValue;  //设置单元格属性
    row.appendChild(cell);  //将单元格添加到行里
    
    cell = document.createElement("td");  //创建一个单元格
    cellValue='<input type="text" name="vote.opptions["'+order+'"].optionRemark">';
    cell.innerHTML=cellValue;  //设置单元格属性
    row.appendChild(cell);  //将单元格添加到行里
    
    cell = document.createElement("td");  //创建一个单元格
    cellValue='<a onclick="deleteOption(this)">删除</a>';
    cell.innerHTML=cellValue;  //设置单元格属性
    row.appendChild(cell);  //将单元格添加到行里
    
    table.appendChild(row);
}
function deleteOption(obj){//删除选项
	var rows=document.getElementsByName("option");
	if(rows.length==1)return;
	var row=obj.parentNode.parentNode;
	table.deleteRow(row.rowIndex);
}
function getMaxOrder(){
	var options=document.getElementsByName("option");
	var big=0;
	for(var i=0;i<options.length;i++){
		var order=options[i].cells[0].firstChild.value;
		if(order>big)big=order;
	}
	return ++big;
}
function checkinput(){
	var url="<%=path%>/admin/vote/add.action?";
	var vote="{";	
	if(document.getElementById("vote.title").value==""){//获取标题
		return;
	}else{
		vote+="vote.title:"+document.getElementById("vote.title").value+",";
	}
	
	vote+="vote.status:"+document.getElementById("vote.status").value+",";//获取状态	
	
	if(document.getElementById("vote.remark").value!=""){//获取备注
		vote+="vote.remark:"+document.getElementById("vote.remark").value+",";
	}
	
	var options=document.getElementsByName("option");
	for(var i=0;i<options.length;i++){
		var id=document.getElementById("vote.options["+i+"].optionId");
		var name=document.getElementById("vote.options["+i+"].optionIame");
	}
}
//
//关闭当前窗口，刷新父窗口
function colse(){
	window.opener.location.reload(); 
	window.close();
}
</script>
</html>