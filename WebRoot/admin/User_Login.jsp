<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%
	String path = request.getContextPath();
	String message=(String)request.getAttribute("message");
	if(message==null) message="";
	response.setHeader("Pragma", "No-Cache");
	response.setHeader("Cache-Control", "No-Cache");
	response.setDateHeader("Expires", 0);
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<!-- saved from url=(0035)http://192.168.151.240/login.action -->
<HTML>
	<HEAD>
		<TITLE>登录</TITLE>
		<META http-equiv=Content-Type content="text/html; charset=UTF-8">
		<link rel="stylesheet" type="text/css" href="<%=path%>/vcomframe/css/common.css" />
		<script type="text/javascript" src="<%=path%>/vcomframe/js/common.js"></script>
		<script type="text/javascript" src="<%=path%>/vcomframe/list/check/formInvalidCheck.js"></script>
		<script type="text/javascript"  src="<%=path%>/vcomframe/list/message/ymPrompt.js"></script>
		<script type="text/javascript" src="<%=path%>/js/version.js"></script>
		<link rel="stylesheet" id='skin' type="text/css" href="<%=path%>/vcomframe/list/message/skin/qq/ymPrompt.css" />
		<STYLE type=text/css>
BODY {
	MARGIN-TOP: 80px;
	BACKGROUND-COLOR: #b9cae8
}

TD {
	FONT-SIZE: 12px;
	COLOR: #ffffff
}

INPUT {
	BORDER-RIGHT: #8aacec 1px solid;
	BORDER-TOP: #8aacec 1px solid;
	FONT-SIZE: 9pt;
	BORDER-LEFT: #8aacec 1px solid;
	COLOR: #0c2a5c;
	BORDER-BOTTOM: #8aacec 1px solid;
	FONT-FAMILY: Arial, Helvetica, sans-serif;
	HEIGHT: 20px;
	BACKGROUND-COLOR: #ffffff
}

BODY {
	COLOR: #000000
}

TD {
	COLOR: #000000
}

TH {
	COLOR: #000000
}
</STYLE>

		<META content="MSHTML 6.00.6000.16735" name=GENERATOR>
	</HEAD>
	<BODY>
		<form name="login" method="post" action="<%=path%>/admin/login.action"
			onSubmit="return checkinput();">
			<TABLE height="100%" cellSpacing=0 cellPadding=0 width="100%"
				align=center border=0>
				<TBODY>
					<TR>
						<TD vAlign=top>
							<TABLE height=327 cellSpacing=0 cellPadding=0 width=675
								align=center background="<s:property value="projecticon"/>"
								border=0>
								<TBODY>
									<TR>
										<TD vAlign=bottom height=51>
											&nbsp;
										</TD>
									</TR>
									<TR>
										<TD>
											<TABLE cellSpacing=0 cellPadding=0 width="100%" border=0>
												<TBODY>
													<TR>
														<TD width="51%">
															&nbsp;
														</TD>
														<TD width="49%">
															
															<TABLE cellSpacing=0 cellPadding=0 width="100%" border=0>
																<TBODY>
																	<TR>
																		<TD align=middle width=60 height=29>
																			用户名：
																		</TD>
																		<TD vAlign=center>
																		    <s:textfield name="auxsysRole.username" id="username" size="25" maxlength="50" onkeydown="if(event.keyCode==13) event.keyCode=9;"/>
																		</TD>
																	</TR>
																	<TR>
																		<TD align=middle height=29>
																			密&nbsp; 码：
																		</TD>
																		<TD vAlign=center>
																			<INPUT id=password type=password size=25 maxLength="16" name=auxsysRole.password>
																		</TD>
																	</TR>
																	<TR>
																		<TD align=middle height=29>
																			验证码：
																		</TD>
																		<TD vAlign=center>
																			<INPUT size="13" name="vcode" id="vcode" maxLength="4" >
																			<IMG height=20 alt="" 
																				src="<%=path%>/validateCodeServlet.frame"
																				width=56 align=absMiddle>
																		</TD>
																	</TR>
																	<TR>
																		<TD align=middle height=40>
																		<TD>
																			<TABLE height=32 cellSpacing=0 cellPadding=0
																				width="100%" border=0>
																				<TBODY>
																					<TR>
																						<TD align=middle width="27%">
																							<INPUT type=submit value=登&nbsp;&nbsp;录>
																						</TD>
																						<TD width="73%">
																							<INPUT type="reset" value=重&nbsp;&nbsp;置>
																						</TD>
																					</TR>
																				</TBODY>
																			</TABLE>
																		<TD></TD>
																	</TR>
																</TBODY>
															</TABLE>
														</TD>
													</TR>
												</TBODY>
											</TABLE>
										</TD>
									</TR>
								</TBODY>
							</TABLE>
						</TD>
					</TR>
				</TBODY>
			</TABLE>
		</FORM>
		<script>
			setTimeout(function(){document.all.username.focus()},0);
			function checkinput(){
			
			    if(CheckNotNull(document.getElementById("username"),"用户名")&&CheckNotNull(document.getElementById("password"),"密码")&&CheckNotNull(document.getElementById("vcode"),"验证码"))
			       return true;
			     return false;
			    
			}
			
			function resetform(){
			   alert();
			   document.getElementById("username").value="";
			   document.getElementsById("password").value="";
			   document.getElementsById("vcode").value="";
			  
 
			}
			
			function loadjs(){
			   //设置焦点
			   if(null==document.getElementById("username").value||""==document.getElementById("username").value)
			       document.getElementById("username").focus();
			   else if(null==document.getElementById("password").value||""==document.getElementById("password").value)
			       document.getElementById("password").focus();
			   else
			     document.getElementById("vcode").focus();
 
			}
			
			//判断
			var msg='<%=message%>';
			if(msg!='login'){
			   ymPrompt.alert(msg,300,200,'提示',null);
			}

			 
		</script>
	</BODY>
</HTML>
