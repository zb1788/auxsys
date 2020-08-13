<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
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
		<title>过滤词列表</title>
		<link rel="stylesheet" type="text/css" href="<%=path%>/vcomframe/css/common.css" />
		<link rel="stylesheet" id='skin' type="text/css" href="<%=path%>/vcomframe/list/message/skin/qq/ymPrompt.css" />
		<script type="text/javascript" src="<%=path%>/vcomframe/js/common.js"></script>
		<script type="text/javascript" src="<%=path%>/vcomframe/list/DatePicker/WdatePicker.js"></script>
        <script type="text/javascript" src="<%=path%>/vcomframe/list/message/ymPrompt.js"></script>
        <script type="text/javascript" src="<%=path%>/js/jquery.js"></script>
        <script type="text/javascript" src="<%=path%>/js/jquery.cookie.js"></script>
        <script type="text/javascript" src="../js/selectparam.js"></script>
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
<form name="form1" action="<%=path%>/admin/filter/list.action" method="post">
<table width="100%">
<tr align="center">
<td>
<table class="TableForm" width="100%" cellpadding="3" cellspacing="0" align="center">
<caption>过滤词查询</caption>
<colgroup><col width="15%"></col><col width="55%"></col><col width="30%"></col></colgroup>
<tr>
   <td class="labelTd">过滤词内容</td>
   <td><input type="text" name="filterKey.content" maxlength="25" value="<s:property value="filterKey.content" />" id="fname" /></td>
   <td align="center">  
       <input type="button" class="button" value="[检 索]" onclick="return search();">
	   <input type="button" class="button" value="[重 置]" onclick="return resetI();">
   </td>
</tr>  
</table>
</td>
 </tr>
 
 <tr align="center">
	<td>
	   <table class="Table" width="100%" cellpadding="3" cellspacing="0" align="center">
	   <caption>过滤词列表</caption>
	   <tr>
	      <TD class=freeBar colSpan=9>
						<STYLE>
						.toAdd { background-image:url(<%=path%>/vcomframe/images/add.gif) !important;}
						.toDel { background-image:url(<%=path%>/vcomframe/images/delete.gif) !important;}
						.toEdit{ background-image:url(<%=path%>/vcomframe/images/txt.gif) !important;}
						.rePwd { background-image:url(<%=path%>/vcomframe/images/plugin.gif) !important;}/*重置*/
						.use { background-image:url(<%=path%>/vcomframe/images/drop-yes.gif) !important;}/*启用*/
						.toManage { background-image:url(<%=path%>/vcomframe/images/channelpreview.gif) !important;}/*管理,结果*/
						</STYLE>
						
						<DIV class="x-toolbar x-small-editor">
						<TABLE cellSpacing=0>
						<TBODY>
						<TR>
						<TD>
						<TABLE style="WIDTH: auto" class="x-btn-wrap x-btn x-btn-text-icon" onmousemove="this.className='x-btn-wrap x-btn x-btn-text-icon x-btn-over x-btn-focus'" onmouseout="this.className='x-btn-wrap x-btn x-btn-text-icon'" border=0 cellSpacing=0 cellPadding=0>
						<TBODY>
						<TR>
						<TD class=x-btn-left><I>&nbsp;</I></TD>
						<TD class=x-btn-center><EM unselectable="on"><span class="x-btn-text toAdd" onclick="return add();">添加</span></EM></TD>
						<TD class=x-btn-right><I>&nbsp;</I></TD></TR></TBODY></TABLE></TD>
						<TD><SPAN class=ytb-sep></SPAN></TD>
						<TD>
						<TABLE style="WIDTH: auto" class="x-btn-wrap x-btn x-btn-text-icon" onmousemove="this.className='x-btn-wrap x-btn x-btn-text-icon x-btn-over x-btn-focus'" onmouseout="this.className='x-btn-wrap x-btn x-btn-text-icon'" border=0 cellSpacing=0 cellPadding=0>
						<TBODY>
						<TR>
						<TD class=x-btn-left><I>&nbsp;</I></TD>
						<TD class=x-btn-center><EM unselectable="on"><span class="x-btn-text toEdit" onclick="return edit();">修改</span></EM></TD>
						<TD class=x-btn-right><I>&nbsp;</I></TD></TR></TBODY></TABLE></TD>
						<TD><SPAN class=ytb-sep></SPAN></TD>
						<TD>
						<TABLE style="WIDTH: auto" class="x-btn-wrap x-btn x-btn-text-icon" onmousemove="this.className='x-btn-wrap x-btn x-btn-text-icon x-btn-over x-btn-focus'" onmouseout="this.className='x-btn-wrap x-btn x-btn-text-icon'" border=0 cellSpacing=0 cellPadding=0>
						<TBODY>
						<TR>
						<TD class=x-btn-left><I>&nbsp;</I></TD>
						<TD class=x-btn-center><EM unselectable="on"><span class="x-btn-text toDel" onclick="return remove();">删除</span></EM></TD>
						<TD class=x-btn-right><I>&nbsp;</I></TD></TR></TBODY></TABLE></TD>
						<TD><SPAN class=ytb-sep></SPAN></TD></TR></TBODY></TABLE></DIV>
					</TD>	
	   </tr>	
	   <tr>
	        <td class="head" width="5%"><input type="checkbox" id="all" name="all" class="checkbox"
										onclick="checkall(this.checked,this.form)" /></td>
		    <td class="head" width="5%">序号</td>
		    <td class="head" width="50%">内容</td>
		    <td class="head" width="40%">创建时间</td>
	   </tr>
		 <s:iterator value="flist" id='f' status='st'>
		  <tr onmouseover="changecolor(this,1);" onmouseout="changecolor(this,2);" <s:if test="!#st.odd">class="custom"</s:if>>
			<td class="changecol"><input name="checkboxId" id="checkboxId" type="checkbox" class="checkbox" value="<s:property value="#f.id" />"></td>
		    <td class="changecol"><s:property value="#st.index+1" /></td>
		    <td class="changecol"><s:property value='#f.content'/></td>
		    <td class="changecol"><s:property value='#f.createtime'/></td>
		 </tr>
         </s:iterator>
        <tr><td colspan="8" align="right" class="head"><s:property value="pageBar" escape="false" /></td>
	   </table>
	</td>
</tr>
</table>
</form>
<script type="text/javascript">
var parameter="dialogWidth:650px;dialogHeight:205px;help:no;scroll:auto;status:no";
function add(){   	
	var url="<%=path%>/admin/filter/toAdd.action?";
	var handle=window.showModalDialog(url,window,parameter);
	if(handle){
		document.getElementById("fname").value="";
		form1.submit();
	}
}
function remove(){
	if(getcheckedNum()==0){
		ymPrompt.alert("请选择要删除的数据",null,null,'提示',null);
		return;
	}
	ymPrompt.confirmInfo("确定要删除该记录吗？",null,null,null,function(tp){
		if("ok"!=tp){return false;}
		var url='<%=path%>/admin/filter/remove.action?'+"ids="+getcheckedValue();
		var handle=window.showModalDialog(url,window,parameter);
		if(handle){
			document.getElementById("fname").value="";
			form1.submit();
		}
	});
	return false;
}
function edit(){
	var num=getcheckedNum();
	if(num==0){
	  ymPrompt.alert("请选择要修改的记录",null,null,'提示',null);
	  return;
	}else if(num>1){
	  ymPrompt.alert("每次只能修改一条记录",null,null,'提示',null);
	  return;
	}
	var url='<%=path%>/admin/filter/toEdit.action?'+"filterKey.id="+getcheckedValue()+'&time='+new Date().getTime();
	var handle=window.showModalDialog(url,window,parameter);
	if(handle){
		document.getElementById("fname").value="";
		form1.submit();
		//location.href='<%=path%>/admin/filter/list.action?';
	}
}
function editAll(obj){
	var ck=obj.checked;
	var checkbox=document.getElementsByName("checkboxId");
	for(var i=0;i<checkbox.length;i++){
		checkbox[i].checked=ck;
	}
}
function getcheckedNum(){
	var checkbox=document.getElementsByName("checkboxId");
	var num=0;
	for(var i=0;i<checkbox.length;i++){
		if(checkbox[i].checked)
			num++;
	}
	return num;
}
function getcheckedValue(){
	var checkbox=document.getElementsByName("checkboxId");
	var value="";
	for(var i=0;i<checkbox.length;i++){
		if(checkbox[i].checked){
			if(value=="")value=checkbox[i].value;
			else value+=","+checkbox[i].value;
		}
	}
	return value;
}
function search(){
	form1.submit();
}
function resetI(){
	fname=document.getElementById("fname");	
	fname.value="";
}
</script>
</body>
</html>
