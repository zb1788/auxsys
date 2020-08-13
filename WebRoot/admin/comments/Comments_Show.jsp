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
		<title>查看评论明细</title>
		<link rel="stylesheet" type="text/css"
			href="<%=path%>/vcomframe/css/common.css" />
		<script type="text/javascript" src="<%=path%>/vcomframe/js/common.js"></script>
		<link rel="stylesheet" id='skin' type="text/css"
			href="<%=path%>/vcomframe/list/message/skin/qq/ymPrompt.css" />
		<script type="text/javascript"
			src="<%=path%>/vcomframe/list/message/ymPrompt.js"></script>
	</head>
	<body scroll="auto">
			<table width="100%">
				<tr>
					<td>
						<table class="Table" width="100%" cellpadding="3" cellspacing="0" align="center">
							<caption>评论信息</caption>
							<colgroup>
								<col width="20%"></col>
								<col width="30%"></col>
								<col width="20%"></col>
								<col width="30%"></col>
							</colgroup>
							<tr>
								<td class="labelTd">应用id</td>
								<td><s:property value="comments.appid" escape="false" />&nbsp;</td>
								<td class="labelTd">目录id</td>
								<td><s:property value="comments.dircode" escape="false" />&nbsp;</td>
							</tr>
							<tr>
								<td class="labelTd">目录名称</td>
								<td><s:property value="comments.dirname" escape="false" />&nbsp;</td>
								<td class="labelTd">信息id</td>
								<td><s:property value="comments.infoid" escape="false" />&nbsp;</td>
							</tr>
							<tr>
								<td class="labelTd">信息标题</td>
								<td><s:property value="comments.infotitle" escape="false" />&nbsp;</td>
								<td class="labelTd">用户id</td>
								<td><s:property value="comments.userid" escape="false" />&nbsp;</td>
							</tr>
							<tr>
								<td class="labelTd">用户名</td>
								<td><s:property value="comments.username" escape="false" />&nbsp;</td>
								<td class="labelTd">地区</td>
								<td><s:property value="comments.area" escape="false" />&nbsp;</td>
							</tr>
							<tr>
								<td class="labelTd">ip</td>
								<td><s:property value="comments.ip" escape="false" />&nbsp;</td>
								<td class="labelTd">内容</td>
								<td><s:property value="comments.content" escape="false" />&nbsp;</td>
							</tr>
							<tr>
								<td class="labelTd">评论时间</td>
								<td><s:property value="comments.createtime" escape="false" />&nbsp;</td>
								<td class="labelTd">评论状态</td>
								<td>
								<s:if test="comments.status==1">
									已审&nbsp;
								</s:if>
								<s:else>
									待审&nbsp;
								</s:else>
								</td>
							</tr>
							<tr>
								<td class="labelTd">打分</td>
								<td><s:property value="comments.point" escape="false" />&nbsp;</td>
								<td class="labelTd">备注1</td>
								<td><s:property value="comments.remark1" escape="false" />&nbsp;</td>
							</tr>
							<tr>
								<td class="labelTd">备注2</td>
								<td><s:property value="comments.remark2" escape="false" />&nbsp;</td>
							</tr>
						</table>
					</td>
				</tr>
				</table>
        <table class="TableForm" width="100%" cellpadding="3" cellspacing="0" align="center">
            <tr>
    			<td height=60 align=center>
    				<input type=button value="关闭" class="button" onclick="self.close();">
    			</td>
    		</tr>
        </table>
	</body>
</html>
