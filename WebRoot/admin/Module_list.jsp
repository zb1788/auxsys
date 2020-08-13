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
		<form name="zzvcom" onsubmit="search();" method="post" action="<%=path%>/admin/getModuleList.action">
		<!-- 权限按钮所用id -->
			<input type="hidden" name="mid"
				value="<%=request.getParameter("mid")%>">
			<table width="100%">
				<TR>
					<td>
						<table align="center" width="100%" class="TableForm"
							cellpadding="3" cellspacing="0">
							<caption>
								模块查询
							</caption>
							<colgroup>
								<col width="15%"></col>
								<col width="55%"></col>
								<col width="30%"></col>
							</colgroup>
							<tr>
								<td class="labelTd">
									模块名称
								</td>
								<td>
									<input type="text" name="moduleobj.name" maxlength="25"
										value="<s:property value="moduleobj.name" />" id="modulename" />
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
								模块列表
							</caption>
							<tr>
								<td class="freeBar" colspan="7">
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
						</TR></TBODY></TABLE></DIV>	
<!-- 按钮 end -->
								</td>
							</tr>
							<!-- 每列所占的比例 -->
							<colgroup>
								<col width="4%"></col>
								<col width="5%"></col>
								<col width="16%"></col>
								<col width="7%"></col>
								<col width="40%"></col>
								<col width="7%"></col>
								<col width="20%"></col>
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
									模块名称
								</td>
								<td class="head">
									级别
								</td>
								<td class="head">
									链接
								</td>
								<td class="head">
									序号
								</td>
								<td class="head">
									备注
								</td>
							</tr>
							<s:if test="null!=datalist">
								<s:iterator value="datalist" id="modulel" status="st">
									<tr onmouseover="changecolor(this,1);"
										onmouseout="changecolor(this,2);"
										<s:if test="!#st.odd">class="custom"</s:if>>
										<td class="changecol">
											<input name="checkboxId" type="checkbox" id="checkboxId"
												class="checkbox" value="<s:property value="id"  />" onclick="uncheck(this,this.form)">
										</td>
										<td class="changecol">
											<s:property value="#st.index+1" />
										</td>
										<td class="changecol">
											<s:property value="name" />
											&nbsp;

										</td>
										<td class="changecol">
											<s:property value="depth" />
											&nbsp;
										</td>
										<td class="changecol">
											<s:property value="link" />
											&nbsp;
										</td>
										<td class="changecol">

											<s:property value="sort" />
											&nbsp;
										</td>
										<td class="changecol">
											<s:property value="remark" />
											&nbsp;
										</td>

									</tr>
								</s:iterator>
							</s:if>


							<tr>
								<td colspan="7" align="right" class="head">
									<s:property value="pageBar" escape="false" />

								</td>
							</tr>
						</table>

					</td>
				</tr>
			</table>
		</form>
	</body>
	<script type="text/javascript">
         function resetfrom(){
			    document.getElementById("modulename").value="";
         }
         function search(){
			    document.forms(0).pageIndex.value=1;//设置为第一页
				zzvcom.action="<%=path%>/admin/getModuleList.action";
				zzvcom.submit();
	    }
	    var actionname="";
	    var window_parameter="dialogWidth:650px;dialogHeight:380px;help:no;scroll:auto;status:no";
			//添加 
			function toAddPage(){
			    //alert(window.dialogArguments);
			    actionname="<%=path%>/admin/toAddModule.action?operFlag=0";
			    
				var handle = showModalDialog(actionname,window,window_parameter);
				
				if(handle){
				    document.forms(0).pageIndex.value=1;//设置为第一页
				    zzvcom.action="<%=path%>/admin/getModuleList.action";
					zzvcom.submit();
				}
			}
			
			//添加
			function toEditPage(){
			    var num=getCheckBoxSelectNumber(zzvcom.checkboxId);
			    //alert(getCheckBoxvalue(contest.checkboxId));
			    if(num>1){
			          ymPrompt.alert('每次只能修改一条记录！',null,null,'提示',null);
					  return false;
				}else if(num==0){
				     ymPrompt.alert('请首先选择要修改的记录！',null,null,'提示',null);
					return false;
				}else{
				   actionname="<%=path%>/admin/toEditModule.action?operFlag=1&moduleobj.id="+getCheckBoxvalue(zzvcom.checkboxId);
				   var handle = showModalDialog(actionname,window,window_parameter);
				   if(handle){
				     zzvcom.action="<%=path%>/admin/getModuleList.action";
					 zzvcom.submit();
				   }
				}
				
				 
			}
			function toDel(){
				var num=getCheckBoxSelectNumber(zzvcom.checkboxId);
			    if(0==num){
			        ymPrompt.alert('请选择要删除的记录！',null,null,'提示',null);
					return false;
				}else {
					ymPrompt.confirmInfo('确定要删除该记录吗？',null,null,null,handler);
					return false;
				}
			
			}//del
			
			function handler(tp){
			   if(tp=='ok'){
			      var idarr = document.getElementsByName("checkboxId")
			      var resultarr = new Array();
			      for(var i=0;i<idarr.length;i++){
			      	var temp=idarr[i];
			      	if(temp.checked){
			      		resultarr.push(temp.value);
			      	}
			      }
			      var handle = showModalDialog("<%=path%>/admin/deleteModule.action?ids="+resultarr.join(","),window,window_parameter);
			      if(handle){
			    	 document.forms(0).pageIndex.value=1;//设置为第一页
				     zzvcom.action="<%=path%>/admin/getModuleList.action";
					 zzvcom.submit();
				   }
			   }else
			     return false;
		}
		<%
			Boolean isSucc=(Boolean)request.getAttribute("isSucc");
			String message=(String)request.getAttribute("message"); 
			if(null==isSucc) {
			    
			}else if(isSucc){
				out.println("parent.moduletree.location=\""+request.getContextPath()+"/admin/getModuleTreeUI.action?mid="+request.getParameter("mid")+"&menutype=\"+parent.menutype");
			}else {
			    out.println("ymPrompt.errorInfo('"+message+"',null,null,'提示',null)");
			}
	     %>		
</script>
</html>
