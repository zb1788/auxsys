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
		<title>投票定义列表</title>
		<link rel="stylesheet" type="text/css" href="<%=path%>/vcomframe/css/common.css" />
		<link rel="stylesheet" id='skin' type="text/css" href="<%=path%>/vcomframe/list/message/skin/qq/ymPrompt.css" />
		<script type="text/javascript" src="<%=path%>/vcomframe/js/common.js"></script>
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
<form name="form1" action="<%=path%>/admin/vote/list.action" method="post">
<table width="100%">
<tr align="center">
<td>
<table class="TableForm" width="100%" cellpadding="3" cellspacing="0" align="center">
<caption>模块查询</caption>
<colgroup><col width="15%"></col><col width="55%"></col><col width="30%"></col></colgroup>
<tr>
   <td class="labelTd">投票标题</td>
   <td><input type="text" name="vote.title" maxlength="25" value="<s:property value="vote.title" />" id="vname" /></td>
   <td align="center">  
       <input type="button" class="button" value="[检 索]" onclick="return search();">
	   <input type="button" class="button"  value="[重 置]" onclick="resetI()">
   </td>
</tr>  
</table>
</td>
 </tr>
 <tr>
	<td>
	   <table class="Table" width="100%" cellpadding="3" cellspacing="0" align="center">
	   <caption>投票列表</caption>
	   <tr>
	      <TD class=freeBar colSpan=9>
						<STYLE>
						.toAdd { background-image:url(<%=path%>/vcomframe/images/add.gif) !important}
						.toDel { background-image:url(<%=path%>/vcomframe/images/delete.gif) !important}
						.toEdit { background-image:url(<%=path%>/vcomframe/images/txt.gif) !important}
						.toUse { background-image:url(<%=path%>/vcomframe/images/drop-yes.gif) !important}
						.toStop { background-image:url(<%=path%>/vcomframe/images/drop-no.gif) !important}
						.toManage { background-image:url(<%=path%>/vcomframe/images/preview.gif) !important}
						</STYLE>
						<DIV class="x-toolbar x-small-editor">
						<TABLE cellSpacing=0>
						<TBODY><TR>
						
						<TD>
						<TABLE style="WIDTH: auto" class="x-btn-wrap x-btn x-btn-text-icon" onmousemove="this.className='x-btn-wrap x-btn x-btn-text-icon x-btn-over x-btn-focus'" onmouseout="this.className='x-btn-wrap x-btn x-btn-text-icon'" border=0 cellSpacing=0 cellPadding=0>
						<TBODY><TR>
						<TD class=x-btn-left><I>&nbsp;</I></TD>
						<TD class=x-btn-center><EM unselectable="on"><span class="x-btn-text toAdd" onclick="add();">添加</span></EM></TD>
						<TD class=x-btn-right><I>&nbsp;</I></TD>
						</TR></TBODY></TABLE>
						</TD>
						
						<TD><SPAN class=ytb-sep></SPAN></TD>
						
						<TD>
						<TABLE style="WIDTH: auto" class="x-btn-wrap x-btn x-btn-text-icon" onmousemove="this.className='x-btn-wrap x-btn x-btn-text-icon x-btn-over x-btn-focus'" onmouseout="this.className='x-btn-wrap x-btn x-btn-text-icon'" border=0 cellSpacing=0 cellPadding=0>
						<TBODY><TR>
						<TD class=x-btn-left><I>&nbsp;</I></TD>
						<TD class=x-btn-center><EM unselectable="on"><span class="x-btn-text toEdit" onclick="edit();">修改</span></EM></TD>
						<TD class=x-btn-right><I>&nbsp;</I></TD>
						</TR></TBODY></TABLE>
						</TD>
						
						<TD><SPAN class=ytb-sep></SPAN></TD>
						
						<TD>
						<TABLE style="WIDTH: auto" class="x-btn-wrap x-btn x-btn-text-icon" onmousemove="this.className='x-btn-wrap x-btn x-btn-text-icon x-btn-over x-btn-focus'" onmouseout="this.className='x-btn-wrap x-btn x-btn-text-icon'" border=0 cellSpacing=0 cellPadding=0>
						<TBODY><TR>
						<TD class=x-btn-left><I>&nbsp;</I></TD>
						<TD class=x-btn-center><EM unselectable="on"><span class="x-btn-text toDel" onclick="remove();">删除</span></EM></TD>
						<TD class=x-btn-right><I>&nbsp;</I></TD>
						</TR></TBODY></TABLE></TD>
						
						<TD><SPAN class=ytb-sep></SPAN></TD>
												
						<TD>
						<TABLE style="WIDTH: auto" class="x-btn-wrap x-btn x-btn-text-icon" onmousemove="this.className='x-btn-wrap x-btn x-btn-text-icon x-btn-over x-btn-focus'" onmouseout="this.className='x-btn-wrap x-btn x-btn-text-icon'" border=0 cellSpacing=0 cellPadding=0>
						<TBODY><TR>
						<TD class=x-btn-left><I>&nbsp;</I></TD>
						<TD class=x-btn-center><EM unselectable="on"><span class="x-btn-text toManage" onclick="resultList();">结果</span></EM></TD>
						<TD class=x-btn-right><I>&nbsp;</I></TD>
						</TR></TBODY></TABLE></TD>
						
						<TD><SPAN class=ytb-sep></SPAN></TD>
						
						<TD>
						<TABLE style="WIDTH: auto" class="x-btn-wrap x-btn x-btn-text-icon" onmousemove="this.className='x-btn-wrap x-btn x-btn-text-icon x-btn-over x-btn-focus'" onmouseout="this.className='x-btn-wrap x-btn x-btn-text-icon'" border=0 cellSpacing=0 cellPadding=0>
						<TBODY><TR>
						<TD class=x-btn-left><I>&nbsp;</I></TD>
						<TD class=x-btn-center><EM unselectable="on"><span class="x-btn-text toUse" onclick="manage(1);">启用</span></EM></TD>
						<TD class=x-btn-right><I>&nbsp;</I></TD>
						</TR></TBODY></TABLE></TD>
						
						<TD><SPAN class=ytb-sep></SPAN></TD>	
						
						<TD>
						<TABLE style="WIDTH: auto" class="x-btn-wrap x-btn x-btn-text-icon" onmousemove="this.className='x-btn-wrap x-btn x-btn-text-icon x-btn-over x-btn-focus'" onmouseout="this.className='x-btn-wrap x-btn x-btn-text-icon'" border=0 cellSpacing=0 cellPadding=0>
						<TBODY><TR>
						<TD class=x-btn-left><I>&nbsp;</I></TD>
						<TD class=x-btn-center><EM unselectable="on"><span class="x-btn-text toStop" onclick="manage(0);">停用</span></EM></TD>
						<TD class=x-btn-right><I>&nbsp;</I></TD>
						</TR></TBODY></TABLE></TD>
						
						<TD><SPAN class=ytb-sep></SPAN></TD>
						</TR></TBODY></TABLE>
						</DIV>
					</TD>	
	   </tr>	   
	   <tr>
	        <td class="head" width="5%"><input type="checkbox" id="all" name="all" class="checkbox"
										onclick="checkall(this.checked,this.form)" /></td>
		    <!-- <td class="head" width="5%">序号</td>  -->
		    <td class="head" width="26%">编号</td>
		    <td class="head" width="26%">标题</td>
		    <td class="head" width="18%">创建时间</td>
		    <td class="head" width="9%">状态</td>
		    <td class="head" width="16%">备注</td>
	   </tr>
		 <s:iterator value="voteList" id='vote' status='st'>
		  <tr onmouseover="changecolor(this,1);" onmouseout="changecolor(this,2);" <s:if test="!#st.odd">class="custom"</s:if>>
		    <td class="changecol"><input name="checkboxId" id="checkboxId" type="checkbox" class="checkbox" value="<s:property value="#vote.id" />"></td>
		     <!-- <td class="changecol"><s:property value="#st.index+1" /></td> -->
		    <td class="changecol"><s:property value='#vote.id'/></td>
		    <td class="changecol"><s:property value='#vote.title'/>&nbsp;</td>
		    <td class="changecol"><s:property value='#vote.createtime'/>&nbsp;</td>	    
		    <td class="changecol"><s:if test="#vote.status==0">停用</s:if><s:else>启用</s:else></td>
		    <td class="changecol"><s:property value='#vote.remark'/>&nbsp;</td>
		 </tr>
         </s:iterator>	         
        <tr><td colspan="8" align="right" class="head"><s:property value="pageBar" escape="false" /></td>
	   </table>
	</td>
</tr>
</table>
</form>
<script type="text/javascript">
   var parameter="dialogWidth:650px;dialogHeight:380px;help:no;scroll:auto;status:no";
   function add(){   
	   var url="<%=path%>/admin/vote/toAdd.action?";
	   var handle=window.showModalDialog(url,window,parameter);
	   if(handle){
		   document.getElementById("vname").value="";
		form1.submit();
		}
   }
   function remove(){
	   if(getcheckedNum()==0){
		   ymPrompt.alert("请选择要删除的记录",null,null,'提示',null);
		   return false;
	   }
	   ymPrompt.confirmInfo("删除投票定义时会清空已产生的投票记录，是否继续",null,null,null,function(tp){
		   if("ok"!=tp){
			   return false;
		   }
		   var url='<%=path%>/admin/vote/delete.action?'+"ids="+getcheckedValue();
		   var handle=window.showModalDialog(url,window,parameter);
		   if(handle){
				document.getElementById("vname").value="";
				form1.submit();
			}
		});	  
   }
   function edit(){
	   var num=getcheckedNum();
	   if(num==0){
		   ymPrompt.alert("请选择要修改的记录",null,null,'提示',null);
		   return false;
	   }else if(num>1){
		   ymPrompt.alert("每次只能修改一条记录",null,null,'提示',null);
		   return false;
	   }
	   var url='<%=path%>/admin/vote/toEdit.action?'+"vote.id="+getcheckedValue()+'&time='+new Date().getTime();
	   var handle=window.showModalDialog(url,window,parameter);
	   if(handle){
		document.getElementById("vname").value="";
		form1.submit();
	   }
   }
   function manage(flag){
	   if(getcheckedNum("checkboxId")==0){
		   ymPrompt.alert("请选择要修改的记录",null,null,'提示',null);
		   return false;
	   }
	   var url='<%=path%>/admin/vote/manage.action?'+"ids="+getcheckedValue()+"&flag="+flag;
	   var handle=window.showModalDialog(url,window,parameter);
	   if(handle){window.location.reload();}
   }
   function editAll(obj){
	   var ck=obj.checked;
	   var checkbox=document.getElementsByName("checkboxId");
	   for(var i=0;i<checkbox.length;i++){
		   checkbox[i].checked=ck;
	   }
   }
   function resultList(){
	   var num=getcheckedNum();
	   if(num==0){
		   ymPrompt.alert("请选择要查看的记录",null,null,'提示',null);
		   return false;
	   }else if(num>1){
		   ymPrompt.alert("每次只能查看一条记录",null,null,'提示',null);
		   return false;
	   }
	   var url0='<%=basePath%>/admin/vote/Vote_Main.jsp?'+"voteid="+getcheckedValue();
	   location.replace(url0);
	   return false;
   }
   function getcheckedNum(){
	   var checkbox=document.getElementsByName("checkboxId");
	   var num=0;
	   for(var i=0;i<checkbox.length;i++){
		   if(checkbox[i].checked)num++;
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
		document.getElementById("vname").value="";
	}
</script>
</body>
</html>
