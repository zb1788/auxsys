<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	response.setHeader("Pragma", "No-Cache");
	response.setHeader("Cache-Control", "No-Cache");
	response.setDateHeader("Expires", 0);
	String operFlag=request.getParameter("operFlag");
%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title></title>
		<link rel="stylesheet" type="text/css"
			href="<%=path%>/vcomframe/css/common.css" />

		<link rel="stylesheet" id='skin' type="text/css"
			href="<%=path%>/vcomframe/list/message/skin/qq/ymPrompt.css" />

		<script type="text/javascript" src="<%=path%>/vcomframe/js/common.js"></script>
		<script type="text/javascript" src="<%=path%>/admin/limit.js"></script>

		<script type="text/javascript"
			src="<%=path%>/vcomframe/list/message/ymPrompt.js"></script>
		<script type="text/javascript"
			src="<%=path%>/vcomframe/list/check/formInvalidCheck.js"></script>
		<base target="_self">
	</head>
	<body scroll="auto">
		<form name="zzvcom" onSubmit="return checkinput();" method="post"
			onSubmit="return checkinput();">
			<input type="hidden" name="moduleobj.id" value="<s:property value="moduleobj.id"/>">
			<table width="100%">
				<TR>
					<td>
						<table align="center" width="100%" class="TableForm" cellpadding="3" cellspacing="0">
							<caption>
								<%if("0".equals(operFlag)){%>新增模块<% }else{ %>修改模块<% } %>
							</caption>
							<colgroup>
								<col width="15%"></col>
								<col width="40%"></col>
								<col width="15%"></col>
								<col width="30%"></col>
							</colgroup>
							<tr>
								<td class="labelTd">
									模块名称
									<span class="star">*</span>
								</td>
								<td>
									<input type="text" name="moduleobj.name" maxlength="25"
										value='<s:property value="moduleobj.name"/>'
										id="name" />
								</td>
								<td class="labelTd">
									级别
									<span class="star">*</span>
								</td>
								<td>
									<input type="text" name="moduleobj.depth" maxlength="50" onchange="this.value=this.value.replace(/\s/g,'')"
										value="<s:property value="moduleobj.depth"/>" id="depth" />
								</td>
							</tr>
						   <tr>
								<td class="labelTd">
									排序号
									<span class="star">*</span>
								</td>
								<td>
									<input type="text" name="moduleobj.sort" maxlength="50" onchange="this.value=this.value.replace(/\s/g,'')"
										value="<s:property value="moduleobj.sort"/>" id="sort" />
								</td>
								<td class="labelTd">
									备注
								</td>
								<td>
									<input type="text" name="moduleobj.remark" maxlength="50" onchange="this.value=this.value.replace(/\s/g,'')"
										value="<s:property value="moduleobj.remark"/>" id="remark" />
								</td>
						   </tr>
							<tr>
								<td class="labelTd">
									链接
									<span class="star">*</span>
								</td>
								<td colspan="3">
									<input checktype="0" type="text" name="moduleobj.link" maxlength="250"
										value="<s:property value="moduleobj.link"/>" id="link" />
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
            var operFlag=<%=operFlag%>;
			function checkinput(){
			    if(!CheckNotNull(document.getElementById("name"),"模块名称")){return false};
			    if(!CheckNotNull(document.getElementById("sort"),"排序号")){return false};
			    if(!CheckNum(document.getElementById("sort"), "排序号", 0, 4)){ return false;}
			    if(!CheckNotNull(document.getElementById("depth"),"级别")){return false};
			    if(!CheckNum(document.getElementById("depth"), "级别", 0, 4)){ return false;}
			    if(!CheckNotNull(document.getElementById("link"),"链接")){return false};
			    
			    var actionname="";
			    if(0==operFlag)
			       actionname="<%=path%>/admin/saveModule.action";
			    else
			       actionname="<%=path%>/admin/updateModule.action";
			       
			    zzvcom.action=actionname;
			    zzvcom.submit();
				return true;	
			}
</script>
</html>
