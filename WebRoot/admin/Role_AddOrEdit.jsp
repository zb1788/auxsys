<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	response.setHeader("Pragma", "No-Cache");
	response.setHeader("Cache-Control", "No-Cache");
	response.setDateHeader("Expires", 0);
String operFlag = request.getParameter("operFlag");
%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>管理员编辑</title>
		<link rel="stylesheet" type="text/css" href="<%=path%>/vcomframe/css/common.css" />

		<link rel="stylesheet" id='skin' type="text/css" href="<%=path%>/vcomframe/list/message/skin/qq/ymPrompt.css" />

		<script type="text/javascript" src="<%=path%>/vcomframe/js/common.js"></script>
		<script type="text/javascript" src="<%=path%>/admin/limit.js"></script>

		<script type="text/javascript" src="<%=path%>/vcomframe/list/message/ymPrompt.js"></script>
		<script type="text/javascript" src="<%=path%>/vcomframe/list/check/formInvalidCheck.js"></script>
		<base target="_self">
	</head>
	<body scroll="auto">
		<form name="zzvcom" onSubmit="return checkinput();" method="post" onSubmit="return checkinput();">
		<input type="hidden" name="roleobj.id" value="<s:property value="roleobj.id"/>" id="id" />
		<% if( "0".equals(operFlag) ){ %>
			<input type="hidden" name="roleobj.password" maxlength="12" value='123456' >
		<% }else{ %>
			<input type="hidden" name="roleobj.password" maxlength="12" value='<s:property value="roleobj.password"/>' >
		<% } %>
			<table width="100%">
				<TR>
					<td>
						<table align="center" width="100%" class="TableForm"
							cellpadding="3" cellspacing="0">
							<caption>
								<s:if test="0==operFlag">
								新增管理员
								</s:if><s:else>
								修改管理员
								</s:else>
							</caption>
							<colgroup>
								<col width="15%"></col>
								<col width="40%"></col>
								<col width="15%"></col>
								<col width="30%"></col>
							</colgroup>
							<tr>
								<td class="labelTd" title='账号为登陆标识,创建后不可修改' >
									账号
									<span class="star">*</span>
								</td>
								<td title='账号为登陆标识,创建后不可修改' >
									<input type="text" name="roleobj.username" maxlength="25" value='<s:property value="roleobj.username"/>' id="rolename" <%= ("1".equals(operFlag)?" readOnly='true' style='color:#A6A6A6;border-color:#A6A6A6'":"") %> />
								</td>
								<td class="labelTd">
									排序号
									<span class="star">*</span>
								</td>
								<td>
									<input type="text" name="roleobj.sort" maxlength="50" onchange="this.value=this.value.replace(/\s/g,'')"
										value="<s:property value="roleobj.sort"/>" id="sort" />
								</td>
							</tr>
							<tr>
								<td class="labelTd">
									级别
									<span class="star">*</span>
								</td>
								<td>
									<input type="text" name="roleobj.depth" maxlength="25"
										value='<s:property value="roleobj.depth"/>'
										id="depth" />
								</td>
								<td class="labelTd">
									备注
								</td>
								<td>
									<input type="text" name="roleobj.remark" maxlength="50" onchange="this.value=this.value.replace(/\s/g,'')"
										value="<s:property value="roleobj.remark"/>" id="remark" />
								</td>
							</tr>

							<tr>
								<td colspan="4" align="center">
									<input type="button" class="button" value="[保 存]"
										onclick="checkinput();">
									&nbsp;
									<input class="button" id="resetNote" type="button"
										value="[关 闭]" onclick="window.close();">
								</td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
		</form>
	</body>
	<script type="text/javascript">
            var operFlag=<%=operFlag %>;
            try{
            	if(document.getElementById("depth").value==""){
            		document.getElementById("depth").value=2;
            	}
            }catch(e){}
			function checkinput(){
			    if(!CheckNotNull(document.getElementById("rolename"),"账号")){return false};
			    if(!CheckNotNull(document.getElementById("sort"),"排序号")){return false};
			    if(!CheckNum(document.getElementById("sort"), "排序号", 0, 10)) return false;
			    if(!CheckNotNull(document.getElementById("depth"),"级别")){return false};
			    if(!CheckNum(document.getElementById("depth"), "级别", 0, 10)) return false;
			    
			    var actionname="";
			    if(0==operFlag)
			       actionname="<%=path%>/admin/saveRole.action";
			    else
			       actionname="<%=path%>/admin/updateRole.action";
			       
			    zzvcom.action=actionname;
			    zzvcom.submit();
				return true;
				
				
			}
			
			
			function CheckNum(objField, strText, numMinLen, numMaxLen) {
				  if (objField.value == "") return true;
				  var RE = new RegExp("[^0-9]");
				  if (objField.value.search(RE) != -1) {
				    ymPrompt.alert("“" + strText + "”中只能填写正整数和0！",null,null,'警告',function(){
				    	  objField.focus();
				    })
				    return false;
				  }
				  return CheckStringLength(objField, strText, numMinLen, numMaxLen);
				}
			
			
			
		 <%
			Boolean isSucc=(Boolean)request.getAttribute("isSucc");
			String message=(String)request.getAttribute("message");
			
			if(null==isSucc) {
			    
			}else if(isSucc){
			
				out.println("window.returnValue=true");
				out.println("window.close();");
			}else {
			    out.println("window.returnValue=false");
			    out.println("ymPrompt.errorInfo('"+message+"',null,null,'提示',null)");
			   
				//out.println("alert(\""+message+"\")");
			}
	     %>
</script>
</html>
