<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title></title>
		<link rel="stylesheet" type="text/css"
			href="<%= path%>/vcomframe/css/common.css" />
		<script type="text/javascript" src="<%=path%>/vcomframe/js/common.js"></script>
		<script type="text/javascript"
			src="<%=path%>/vcomframe/list/ajax/jquery.js"></script>
		<script type="text/javascript"
			src="<%=path%>/vcomframe/list/check/formInvalidCheck.js"></script>
		<script type="text/javascript"
			src="<%=path%>/vcomframe/list/message/ymPrompt.js"></script>
		<link rel="stylesheet" id='skin' type="text/css"
			href="<%=path%>/vcomframe/list/message/skin/qq/ymPrompt.css" />
			<base target="_self">
			<script type="text/javascript">
		function init(){
			$("select option[value="+$("#status").val()+"]").attr("selected", "selected");  
		}
		$(function(){
			init();
		})
</script>	
	</head>
	
	<body scroll="auto">
		<form id="form1" name="form1" onsubmit="return saveCheck();" action="<%=path%>/admin/comments/save.action" method="post">
			<table width="100%">
				<TR>
					<td>
						<table align="center" width="100%" class="TableForm"
							cellpadding="3" cellspacing="0">
							<caption>
								评论信息
							</caption>
							<colgroup>
								<col width="20%"></col>
								<col width="80%"></col>
							</colgroup>
							<tr>
								<td class="labelTd">
									状态
								</td>
								<td>
									<input type="hidden" name="comments.status" id="status" value="<s:property value="comments.status"  /> "/>
		              <select id="c_status" name="c_status" style="width: 70px">
		                <option value="0">待审</option>
		                <option value="1">已审</option>
		              </select>
								</td>
							</tr>
							<tr>
								<td class="labelTd">
									内容<span class="star">*</span>
								</td>
								<td>
									<s:textarea id="content" name="comments.content" >
									</s:textarea>
								</td>
							</tr>
							<tr>
								<td colspan="5" align="center">
									<input type="submit" class="button" value="[提 交]">
									&nbsp;
									<input class="button" id="resetNote" type="button"
										value="[返 回]" onclick="_back();">
								</td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
			<input type="hidden" id="cid" name="comments.id" value="<s:property value='comments.id'/>" />
		</form>
	</body>
<script type="text/javascript">
function saveCheck(){
  $("#status").val($("#c_status").val());
  if(!CheckNotNull(document.getElementById("content"),"内容")){return false};
  if(document.getElementById("content").value.length>2000){
  	ymPrompt.alert("备注不能超过2000个字符!",null,null,'警告',null );
  	return false;
  }
}
function _back() {
  window.close();
}
</script>	
</html>