<%@ page language="java" import="java.util.*,zzvcom.sys.service.PowerService" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		
	    <title>voteOption编辑页面</title>
	    
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<link rel="stylesheet" type="text/css" href="<%=path%>/vcomframe/css/common.css" />
		<link rel="stylesheet" id='skin' type="text/css" href="<%=path%>/vcomframe/list/message/skin/qq/ymPrompt.css" />
		<script type="text/javascript" src="<%=path%>/js/commonlib.js"></script>
		<script type="text/javascript" src="<%=path%>/vcomframe/js/common.js"></script>
		<script type="text/javascript" src="<%=path%>/vcomframe/list/message/ymPrompt.js"></script>
		<script type="text/javascript" src="<%=path%>/vcomframe/list/check/formInvalidCheck.js"></script>
		<script type="text/javascript" src="<%=path%>/admin/js/limit.js"></script>
		<script type="text/javascript" src="<%=path%>/admin/limit.js"></script>
		<base target="_self">
	</head>
	<body scroll="auto">
		<form name="form1" onsubmit="return checkinput();" method="post" action="" >
			<input type="hidden" id="voteoption.id" name="voteoption.id" value="<s:property value="voteoption.id"/>" />
			<input type="hidden" id="voteoption.voteId" name="voteoption.voteId" value="<s:property value="voteoption.voteId"/>" />
			<table width="100%">
				<tr>
					<td>
						<table align="center" width="100%" class="TableForm"
							cellpadding="3" cellspacing="0">
							<caption>
								<%= ("0".equals(request.getParameter("operFlag"))?"添加选项":"修改选项") %>
							</caption>
							<colgroup>
								<col width="15%"></col>
								<col width="35%"></col>
								<col width="15%"></col>
								<col width="35%"></col>
							</colgroup>
							<tr>
								<td class="labelTd">
									标题&nbsp;<span class="star">*</span>
								</td>
								<td>
									<input type="text" name="voteoption.title"  id="voteoption.title" maxlength="50" value='<s:property value="voteoption.title"/>' />
								</td>
								<td class="labelTd">
									排序号&nbsp;<span class="star">*</span>
								</td>
								<td><input type="text" name="voteoption.orderNumber" id="voteoption.orderNumber" maxlength="3" value='<s:property value="voteoption.orderNumber"/>' />
								</td>
							</tr>
							<tr>
								<td class="labelTd">
									图片
								</td>
								<td colspan="3"  >
								<input type="text" name="voteoption.picture" id="voteoption.picture" maxlength="500" value="<s:property value='voteoption.picture'/>"  style="width:85%" />
								&nbsp;&nbsp;<input type="button" class="button" value="上传" onclick="upload('pic','voteoption.picture');" />
								</td>
							</tr>
							<tr>
								<td class="labelTd">
									链接地址
								</td>
								<td colspan="3"  >
									<input checktype="0" type="text" id="voteoption.linkurl" name="voteoption.linkurl" maxlength="500"
										value="<s:property value='voteoption.linkurl'/>" />
								</td>
							</tr>
							<tr>
								<td class="labelTd">
									描述
								</td>
								<td colspan="3"  >
									<input type="text" name="voteoption.description" id="voteoption.description" maxlength="120" value="<s:property value='voteoption.description'/>" />
								</td>
							</tr>
							<tr>
								<td class="labelTd">
									备选属性1
								</td>
								<td colspan="3"  >
									<input checktype="0" type="text" name="voteoption.A1" id="voteoption.A1" maxlength="500" value="<s:property value="voteoption.A1"/>" />
								</td>
							</tr>
							<tr>
								<td class="labelTd">
									备选属性2
								</td>
								<td colspan="3"  >
									<input checktype="0" type="text" name="voteoption.A2" id="voteoption.A2" maxlength="500" value="<s:property value="voteoption.A2"/>" />
								</td>
							</tr>
							<tr>
								<td class="labelTd">
									备选属性3
								</td>
								<td colspan="3"  >
									<input checktype="0" type="text" name="voteoption.A3" id="voteoption.A3" maxlength="500" value="<s:property value="voteoption.A3"/>" />
								</td>
							</tr>
							<tr>
								<td colspan="4" align="center">
									<input type="submit" class="button" value="[保 存]" >
									&nbsp;
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
var operFlag=<%=request.getParameter("operFlag")%>;
var voteid='<s:property value="voteid"/>';
function checkinput(){
    if(!CheckNotNull(document.getElementById("voteoption.title"),"标题")){return false};
    if(!CheckNotNull(document.getElementById("voteoption.orderNumber"),"排序号")){return false};
    if(!CheckNum(document.getElementById("voteoption.orderNumber"),"排序号",null,null)){return false;}
    var actionname="";
    if(1==operFlag)
       actionname="<%=path%>/admin/vote/updateVoteOption.action";
    else
       actionname="<%=path%>/admin/vote/saveVoteOption.action";
    window.form1.action=actionname;
	return true;
}
function upload(fileType, id) {
	/*pic,file*/
    var backvalue=window.showModalDialog('<%=path%>/admin/upload/upload.jsp?fileType='+fileType,'upload','dialogWidth:410px;DialogHeight=240px;status:no;scroll:no');
    if(backvalue!=null&&backvalue!=''){
    	document.getElementById(id).value=backvalue;
    }
}
try{
/*
autoselect(document.getElementById("voteoption.voteType"),'<s:property value="voteoption.voteType" />');
if(document.getElementById("voteoption.limitValue").value==""){
	document.getElementById("voteoption.limitValue").value="0";
}
*/
}catch(e){alert("errorMessage:"+getErrorMessage(e));}
</script>
</html>