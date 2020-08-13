<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	response.setHeader("Pragma", "No-Cache");
	response.setHeader("Cache-Control", "No-Cache");
	response.setDateHeader("Expires", 0);
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title></title>
		<link rel="stylesheet" type="text/css"
			href="<%=path%>/vcomframe/css/common.css" />
		<script type="text/javascript" src="<%=path%>/vcomframe/js/common.js"></script>

		<link rel="stylesheet" id='skin' type="text/css"
			href="<%=path%>/vcomframe/list/message/skin/qq/ymPrompt.css" />
		<script type="text/javascript"
			src="<%=path%>/vcomframe/list/message/ymPrompt.js"></script>
	</head>
	<body scroll="auto">
		<form name="form1" action="<%=path%>/admin/getRoleList.action"
			method="post">
			<table width="100%">
				<TR>
					<td>
						<table align="center" width="100%" class="TableForm"
							cellpadding="3" cellspacing="0">
							<caption>
								管理员查询
							</caption>
							<colgroup>
								<col width="15%"></col>
								<col width="55%"></col>
								<col width="30%"></col>
							</colgroup>
							<tr>
								<td class="labelTd">
									管理员名称
								</td>
								<td>
									<s:textfield name="roleobj.username" maxlength="50"
										id="username"></s:textfield>
								</td>
								<td align="center">
									<input type="button" class="button" value="[检 索]"
										onclick="search();">
									<input class="button" id="resetNote" type="button"
										value="[重 置]" onclick="resetfrom()">
								</td>
							</tr>
						</table>
					</td>
				</tr>

				<tr>
					<td>
						<table class="Table" width="70%" cellpadding="3" cellspacing="0"
							align="center">
							<!-- 表头 -->
							<caption>
								管理员列表
							</caption>
							<tr>
								<td class="freeBar" colspan="6">
<!-- 按钮 start -->
						<STYLE>
						.toAdd { background-image:url(<%=path%>/vcomframe/images/add.gif) !important;}
						.toDel { background-image:url(<%=path%>/vcomframe/images/delete.gif) !important;}
						.toEdit{ background-image:url(<%=path%>/vcomframe/images/txt.gif) !important;}
						.rePwd { background-image:url(<%=path%>/vcomframe/images/plugin.gif) !important;}/*重置*/
						.use { background-image:url(<%=path%>/vcomframe/images/drop-yes.gif) !important;}/*启用*/
						.toManage { background-image:url(<%=path%>/vcomframe/images/channelpreview.gif) !important;}/*管理,结果*/
						</STYLE>
						
						<DIV class="x-toolbar x-small-editor">
						<TABLE cellSpacing=0><TBODY><TR><TD>
						<TABLE style="WIDTH: auto" class="x-btn-wrap x-btn x-btn-text-icon" onmousemove="this.className='x-btn-wrap x-btn x-btn-text-icon x-btn-over x-btn-focus'" onmouseout="this.className='x-btn-wrap x-btn x-btn-text-icon'" border=0 cellSpacing=0 cellPadding=0>
						<TBODY><TR><TD class=x-btn-left><I>&nbsp;</I></TD>
						<TD class=x-btn-center><EM unselectable="on"><BUTTON class="x-btn-text toAdd" onclick="return toAddPage();">添加</BUTTON></EM></TD>
						<TD class=x-btn-right><I>&nbsp;</I></TD></TR></TBODY></TABLE></TD>
						<TD><SPAN class=ytb-sep></SPAN></TD>
						<TD>
						<TABLE style="WIDTH: auto" class="x-btn-wrap x-btn x-btn-text-icon" onmousemove="this.className='x-btn-wrap x-btn x-btn-text-icon x-btn-over x-btn-focus'" onmouseout="this.className='x-btn-wrap x-btn x-btn-text-icon'" border=0 cellSpacing=0 cellPadding=0>
						<TBODY>
						<TR>
						<TD class=x-btn-left><I>&nbsp;</I></TD>
						<TD class=x-btn-center><EM unselectable="on"><BUTTON class="x-btn-text toEdit" onclick="return toEditPage();">修改</BUTTON></EM></TD>
						<TD class=x-btn-right><I>&nbsp;</I></TD></TR></TBODY></TABLE></TD>
						<TD><SPAN class=ytb-sep></SPAN></TD>
						<TD>
						<TABLE style="WIDTH: auto" class="x-btn-wrap x-btn x-btn-text-icon" onmousemove="this.className='x-btn-wrap x-btn x-btn-text-icon x-btn-over x-btn-focus'" onmouseout="this.className='x-btn-wrap x-btn x-btn-text-icon'" border=0 cellSpacing=0 cellPadding=0>
						<TBODY>
						<TR>
						<TD class=x-btn-left><I>&nbsp;</I></TD>
						<TD class=x-btn-center><EM unselectable="on"><BUTTON class="x-btn-text toDel" onclick="return toDel();">删除</BUTTON></EM></TD>
						<TD class=x-btn-right><I>&nbsp;</I></TD></TR></TBODY></TABLE>
						</TD>
						<TD><SPAN class=ytb-sep></SPAN></TD>
						<TD>
						<TABLE style="WIDTH: auto" class="x-btn-wrap x-btn x-btn-text-icon" onmousemove="this.className='x-btn-wrap x-btn x-btn-text-icon x-btn-over x-btn-focus'" onmouseout="this.className='x-btn-wrap x-btn x-btn-text-icon'" border=0 cellSpacing=0 cellPadding=0>
						<TBODY>
						<TR>
						<TD class=x-btn-left><I>&nbsp;</I></TD>
						<TD class=x-btn-center><EM unselectable="on"><BUTTON class="x-btn-text rePwd" onclick="return rePwd();">重置密码</BUTTON></EM></TD>
						<TD class=x-btn-right><I>&nbsp;</I></TD></TR></TBODY></TABLE>
						</TD>
						<TD><SPAN class=ytb-sep></SPAN></TD>
						</TR></TBODY></TABLE></DIV>	
<!-- 按钮 end -->
								</td>
							</tr>
							<!-- 每列所占的比例 -->
							<colgroup>
								<col width="5%"></col>
								<col width="5%"></col>
								<col width="35%"></col>
								<col width="10%"></col>
								<col width="15%"></col>
								<col width="30%"></col>
							</colgroup>
							<tr>
								<td class="head">
									<input type="checkbox" id="all" name="all" class="checkbox"
										onclick="checkall(this.checked,this.form)" />
								</td>
								<td class="head">
									序号
								</td>
								<td class="head">
									账户名
								</td>
								<td class="head">
									排序号
								</td>
								<td class="head">
									级别
								</td>
								<td class="head">
									备注
								</td>
							</tr>
							<s:iterator value="datalist" id="datalist" status="st">
								<tr onmouseover="changecolor(this,1);"
									onmouseout="changecolor(this,2);"
									<s:if test="!#st.odd">class="custom"</s:if>>
									<td class="changecol">
										<input name="checkboxId" type="checkbox" id="checkboxId"
											onclick="uncheck(this,this.form)" class="checkbox" value="<s:property value='id' />">
									</td>
									<td class="changecol">
										<s:property value="#st.index+1" />
									</td>
									<td class="changecol">
										<s:property value="username" />
									</td>
									<td class="changecol">
										<s:property value="sort" />
									</td>
									<td class="changecol">
										<s:property value="depth" />
									</td>
									<td class="changecol">
										<s:property value="remark" />
										&nbsp;
									</td>
								</tr>
							</s:iterator>
							<tr>
								<td colspan="7" align="right" class="head">
									<s:property value="#request.pageBar" escape="false" />
								</td>
							</tr>
						</table>

					</td>
				</tr>
			</table>
		</form>
	</body>
	<script type="text/javascript">
		//用户检索
		function search(){
			document.forms(0).pageIndex.value=1;//设置为第一页
			form1.submit();
		}
		//重置检索表单
		function resetfrom(){
			form1.username.value="";
		}
		
		 var actionname="";
	     var window_parameter="dialogWidth:650px;dialogHeight:380px;help:no;scroll:auto;status:no";
			//添加 
			function toAddPage(){
			 
			    //alert(window.dialogArguments);
			    actionname="<%=path%>/admin/toAddRole.action";
			    
				var handle = showModalDialog(actionname,window,window_parameter);
				
				if(handle){
				    document.forms(0).pageIndex.value=1;//设置为第一页
				    form1.action="<%=path%>/admin/getRoleList.action";
					form1.submit();
				}
			}
			//修改
			function toEditPage(){
			    var num=getCheckBoxSelectNumber(form1.checkboxId);
			    //alert(getCheckBoxvalue(contest.checkboxId));
			    if(num>1){
			          ymPrompt.alert('每次只能修改一条记录！',null,null,'提示',null);
					  return false;
				}else if(num==0){
				     ymPrompt.alert('请首先选择要修改的记录！',null,null,'提示',null);
					return false;
				}else{
				   actionname="<%=path%>/admin/toEditRole.action?";
			       actionname+="operFlag=1";
				   actionname+="&roleobj.id="+getCheckBoxvalue(form1.checkboxId);
				   var handle = showModalDialog(actionname,window,window_parameter);
				   if(handle){
				   	 //重新查询当前页
				     form1.action="<%=path%>/admin/getRoleList.action";
					 form1.submit();
				   }
				}
			}
			//删除
			function toDel(){
			    var num=getCheckBoxSelectNumber(form1.checkboxId);
			    if(0==num){
			        ymPrompt.alert('请选择要删除的记录！',null,null,'提示',null);
					return false;
				}else {
					ymPrompt.confirmInfo('确定要删除该记录吗？',null,null,null,delHandler);
					return false;
				}
			}//del
			//删除确认回调
			function delHandler(tp){
			   if(tp=='ok'){
			      var idarr = document.getElementsByName("checkboxId")
			      var resultarr = new Array();
			      for(var i=0;i<idarr.length;i++){
			      	var temp=idarr[i];
			      	if(temp.checked){
			      		resultarr.push(temp.value);
			      	}
			      }
			      var handle = showModalDialog("<%=path%>/admin/deleteRoles.action?ids="+resultarr.join(","),window,window_parameter);
			      if(handle){
			    	 document.forms(0).pageIndex.value=1;//设置为第一页
				     form1.action="<%=path%>/admin/getRoleList.action";
					 form1.submit();
				   }
			   }else
			     return false;
		}
		//重置密码
		function rePwd(){
			var num=getCheckBoxSelectNumber(form1.checkboxId);
		    if(0==num){
		        ymPrompt.alert('请选择要重置密码的记录！',null,null,'提示',null);
				return false;
			}else{
				ymPrompt.confirmInfo('确定要重置所选用户的密码吗？',null,null,null,rePwdHandler);
				return false;
			}
		}
		//重置密码确认回调
		function rePwdHandler(tp){
		   if(tp=='ok'){
		      var idarr = document.getElementsByName("checkboxId")
		      var resultarr = new Array();
		      for(var i=0;i<idarr.length;i++){
		      	var temp=idarr[i];
		      	if(temp.checked){
		      		resultarr.push(temp.value);
		      	}
		      }
		      var handle = showModalDialog("<%=path%>/admin/resetPwd.action?ids="+resultarr.join(","),window,window_parameter);
		      if(handle){
			     form1.action="<%=path%>/admin/getRoleList.action";
				 form1.submit();
			   }
		   }else
		     return false;
		}
	</script>
</html>