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
<%
String webappcode = (String)request.getAttribute("webappcode");
%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="t" uri="/WEB-INF/tld/button.tld"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>评论列表</title>
<link rel="stylesheet" type="text/css"
			href="<%=path%>/vcomframe/css/common.css" />
<script type="text/javascript" src="<%=path%>/vcomframe/js/common.js"></script>
<script type="text/javascript"
			src="<%=path%>/vcomframe/list/DatePicker/WdatePicker.js"></script>
<link rel="stylesheet" id='skin' type="text/css"
			href="<%=path%>/vcomframe/list/message/skin/qq/ymPrompt.css" />
<script type="text/javascript"
			src="<%=path%>/vcomframe/list/message/ymPrompt.js"></script>
<script type="text/javascript" src="<%=path%>/js/jquery.js"></script>
<script type="text/javascript" src="<%=path%>/js/jquery.cookie.js"></script>
<script type="text/javascript" src="../js/selectparam.js"></script>
<STYLE>
	.toAdd { background-image:url(<%=path%>/vcomframe/images/add.gif) !important}
	.toDel { background-image:url(<%=path%>/vcomframe/images/delete.gif) !important}
	.toEdit { background-image:url(<%=path%>/vcomframe/images/txt.gif) !important}
	.check { background-image:url(<%=path%>/vcomframe/images/check.gif) !important;}
	.use { background-image:url(<%=path%>/vcomframe/images/drop-yes.gif) !important;}
	.toManage { background-image:url(<%=path%>/vcomframe/images/channelpreview.gif) !important;}
</STYLE>
<script type="text/javascript">
$(function(){
	$("select option[value="+$("#status").val()+"]").attr("selected", "selected");
})
</script>
</head>
<body scroll="auto">
<form id="form" name="form" method="post" action="getAll.action">
<input type="hidden" id="webappcode" name ="webappcode" value="<s:property value="webappcode"  />"/>
<input type="hidden" id="channelCode" name ="channelCode" value="<s:property value="channelCode"  />"/>
  <table width="100%">
    <TR>
      <td><table align="center" width="100%" class="TableForm" cellpadding="3" cellspacing="0">
          <caption>
         		 查询
          </caption>
          <tr>
            <td class="labelTd">信息标题:</td>
            <td><input type="text" name="comments.infotitle" id="infotitle" maxlength="25" value="<s:property value="comments.infotitle"  />" style="width: 120px" /> </td>
            <td class="labelTd">信息id:</td>
            <td><input type="text" name="comments.infoid" id="infoid" maxlength="25" value="<s:property value="comments.infoid"  />" style="width: 120px" /> </td>
          </tr>
          <tr>
            <td class="labelTd">评论状态:</td>
            <td><input type="hidden" name="comments.status" id="status" value="<s:property value="comments.status"  /> "/>
              <select id="c_status" name="c_status" style="width: 70px">
                <option value="">全部</option>
                <option value="0">待审</option>
                <option value="1">已审</option>
              </select>
            </td>
            <td class="labelTd">评论内容:</td>
            <td><input type="text" name="comments.content" id="content" maxlength="25" value="<s:property value="comments.content"  />" style="width: 120px" /> </td>
          </tr>
          <tr>
            <td class="labelTd">评论时间</td>
            <td colspan=3><input class="Wdate" name="q_createtime" id="q_createtime" readonly="readonly"
										value="<s:property value="q_createtime"  />"
										onfocus="WdatePicker({maxDate:'#F{\'2020-10-01\'}'})"
										type="text" style="width: 100px" />
              至
              <input class="Wdate" name="s_createtime" id="s_createtime" readonly="readonly"
										value="<s:property value="s_createtime"  />"
										onfocus="WdatePicker({maxDate:'#F{\'2040-10-01\'}'})"
										type="text" style="width: 100px" />
            </td>
          </tr>
          <tr>
            <td colspan=4 align=center><input type="button" class="button" value="[查询]" onClick="search();" />
              &nbsp;
              <input type="button" class="button" value="[重置]" onClick="resetForm();" />
            </td>
          </tr>
        </table></td>
    </tr>
    <tr>
      <td><table class="Table" width="100%" cellpadding="3" cellspacing="0" align="center">
          <caption>
          	评论列表
          </caption>
          <TD class=freeBar colSpan=9>
						<STYLE>.toState16 {
							BACKGROUND-IMAGE: url(/cms/null)
						}
						.toState14 { background-image:url(/auxsys/vcomframe/images/check.gif) !important;};
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
						<TD class=x-btn-center><EM unselectable="on"><span class="x-btn-text check" onclick="return check();">审核</span></EM></TD>
						<TD class=x-btn-right><I>&nbsp;</I></TD></TR></TBODY></TABLE></TD>
						<TD><SPAN class=ytb-sep></SPAN></TD>
						<TD>
						<TABLE style="WIDTH: auto" class="x-btn-wrap x-btn x-btn-text-icon" onmousemove="this.className='x-btn-wrap x-btn x-btn-text-icon x-btn-over x-btn-focus'" onmouseout="this.className='x-btn-wrap x-btn x-btn-text-icon'" border=0 cellSpacing=0 cellPadding=0>
						<TBODY>
						<TR>
						<TD class=x-btn-left><I>&nbsp;</I></TD>
						<TD class=x-btn-center><EM unselectable="on"><span class="x-btn-text toEdit" onclick="return toEditPage();">修改</span></EM></TD>
						<TD class=x-btn-right><I>&nbsp;</I></TD></TR></TBODY></TABLE></TD>
						<TD><SPAN class=ytb-sep></SPAN></TD>
						<TD>
						<TABLE style="WIDTH: auto" class="x-btn-wrap x-btn x-btn-text-icon" onmousemove="this.className='x-btn-wrap x-btn x-btn-text-icon x-btn-over x-btn-focus'" onmouseout="this.className='x-btn-wrap x-btn x-btn-text-icon'" border=0 cellSpacing=0 cellPadding=0>
						<TBODY>
						<TR>
						<TD class=x-btn-left><I>&nbsp;</I></TD>
						<TD class=x-btn-center><EM unselectable="on"><span class="x-btn-text toDel" onclick="return toDel();">删除</span></EM></TD>
						<TD class=x-btn-right><I>&nbsp;</I></TD></TR></TBODY></TABLE></TD>
						<TD><SPAN class=ytb-sep></SPAN></TD></TR></TBODY></TABLE></DIV>
					</TD>	
          <tr>
            <td class="head" width="3%""><input type="checkbox" id="all" name="all" class="checkbox"
										onclick="checkall(this.checked,this.form)" />
            </td>
            <td class="head td_author" width="12%">信息id</td>
            <td class="head td_author" width="12%">信息标题</td>
            <td class="head td_mtime" width="20%">评论内容</td>
            <td class="head td_pubtime" width="12%">用户名</td>
            <td class="head td_state" width="10%">地区</td>
            <td class="head td_pubtime" width="20%">评论时间</td>
            <td class="head td_state" width="10%">状态</td>
          </tr>
          <s:if test="null!=commentsList">
            <s:iterator value="commentsList" id="comments" status="st">
              <tr onmouseover="changecolor(this,1);"onmouseout="changecolor(this,2);"
              <s:if test="!#st.odd">class="custom"</s:if>>
              <td class="changecol"><input name="checkboxId" type="checkbox" id="checkboxId"
												class="checkbox" value="<s:property value="id"  />
                  " onclick="uncheck(this,this.form)"> </td>
                  <td class="changecol td_author">
                	 &nbsp;<s:property value="infoid" />
                </td>
                <td class="changecol td_author" title="<s:property value="infotitle" />">
                	&nbsp;<a href="javascript: showComments('<s:property value="id"  />')" >
	                	<div style="overflow:hidden;text-overflow:ellipsis;white-space:nowrap;width:90">
		                	<s:property value="infotitle" />
	                	</div>
									</a>
                </td>
                <td class="changecol" title="<s:property value="content" />" >
                	<div style="overflow:hidden;text-overflow:ellipsis;white-space:nowrap;width:250">
	                	&nbsp;<s:property value="content" />
                	</div>
                </td>
                <td class="changecol td_pubtime">&nbsp;<s:property value="username" />
                </td>
                <td class="changecol">&nbsp;<s:property value="area" />
                </td>
                <td class="changecol td_author">&nbsp;<s:property value="createtime" />
                </td>
                <td class="changecol td_mtime">
                	&nbsp;<s:if test="status==1">已审</s:if><s:else>待审</s:else>
                </td>
              </tr>
            </s:iterator>
          </s:if>
          <tr>
            <td colspan="8" align="right" class="head">
            	<s:property value="#request.pageBar" escape="false" />
            </td>
          </tr>
        </table></td>
    </tr>
  </table>
</form>
</body>
<script type="text/javascript">
var actionname="";
//修改评论窗体参数
var window_parameter="dialogWidth:900px;dialogHeight:600px;help:no;scroll:auto;minimize:yes;maximize:yes;";
//关闭窗体参数
var close_parameter="dialogWidth:650px;dialogHeight:380px;help:no;scroll:auto;";
//查询
function search(){
	$("#status").val($("#c_status").val());
	form.action="<%=path%>/admin/comments/getAll.action";
	form.submit();
}
//重置
function resetForm() {
	  $("#s_createtime").val(""); 
		$("#infotitle").val("");
    $("#infoid").val("");
    $("#c_status").val("");
    $("#status").val("");
    $("#content").val("");
    $("#q_createtime").val("");
}
//批量审核
function check() {
  var num=getCheckBoxSelectNumber(form.checkboxId);
  if(0==num){
    ymPrompt.alert('请选择要审核的信息！',null,null,'提示',null);
		return false;
	}else {
		actionname="<%=path%>/admin/comments/check.action?commentsIds="+getCheckBoxvalue(form.checkboxId);
	  var handle =  window.showModalDialog(actionname,null,close_parameter); 
	  if(handle){
      form.action="<%=path%>/admin/comments/getAll.action";
	 		form.submit();
   }
	}
}
	//批量删除
function toDel(){
   var num=getCheckBoxSelectNumber(form.checkboxId);
   if(0==num){
    ymPrompt.alert('请选择要删除的信息！',null,null,'提示',null);
		return false;
	}else {
		ymPrompt.confirmInfo('确定要删除该记录吗？',null,null,null,handler);
		return false;
	}
}
function handler(tp){
 if(tp=='ok'){
	 actionname="<%=path%>/admin/comments/del.action?commentsIds="+getCheckBoxvalue(form.checkboxId);
	 var handle =window.showModalDialog(actionname,null,close_parameter); 
	 if(handle){
      form.action="<%=path%>/admin/comments/getAll.action";
	 		form.submit();
   }
 }else{
   return false;
 }
}
	
//显示详细评论
function showComments(id) {
    var _p="dialogWidth:600px;dialogHeight:400px;help:no;scroll:auto;status:no";
    actionname = 'show.action?commentsId='+id;
    showModalDialog(actionname,window,_p);
}

//编辑评论
function toEditPage(){
  var num=getCheckBoxSelectNumber(form.checkboxId);
  if(num>1){
      ymPrompt.alert('每次只能修改一条信息！',null,null,'提示',null);
		  return false;
	}else if(num==0){
	   ymPrompt.alert('请选择要修改的信息！',null,null,'提示',null);
		 return false;
	}else{
	   actionname="<%=path%>/admin/comments/edit.action?";
	   actionname+="commentsId="+getCheckBoxvalue(form.checkboxId);
	   var handle = showModalDialog(actionname,window,window_parameter);
	   if(handle){
	      form.action="<%=path%>/admin/comments/getAll.action";
		 		form.submit();
	   }
	}
}

</script>
</html>
