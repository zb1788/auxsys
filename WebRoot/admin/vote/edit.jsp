<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
response.setHeader("Pragma", "No-Cache");
response.setHeader("Cache-Control", "No-Cache");
response.setDateHeader("Expires", 0);
String operFlag=request.getParameter("operFlag");
String caption=("0".equals(operFlag)?"添加投票":"修改投票");
String actionName=("0".equals(operFlag)?"add.action?":"edit.action?");
String action=path+"/admin/vote/"+actionName;
String hasResults=(String)request.getAttribute("hasResults");


%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>vote编辑页面</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<link rel="stylesheet" type="text/css" href="<%=path%>/vcomframe/css/common.css" />
		<link rel="stylesheet" id='skin' type="text/css" href="<%=path%>/vcomframe/list/message/skin/qq/ymPrompt.css" />
		<script type="text/javascript"
			src="<%=path%>/vcomframe/list/DatePicker/WdatePicker.js"></script>
		<script type="text/javascript" src="<%=path%>/vcomframe/list/message/ymPrompt.js"></script>
		<script type="text/javascript" src="<%=path%>/js/jquery.js"></script>
		<script type="text/javascript" src="<%=path%>/admin/limit.js"></script>
		<base target="_self">
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
<style>
.head{border-width:0px}
</style>
<form name="form1" onsubmit="return checkinput();" method="post" action="<%=action%>" >
<input type="hidden" id="vote.id" name="vote.id" value='<s:property value="vote.id" />'/>
<table width="100%">
<tr>
   <td>
	  <table align="center" width="100%" class="TableForm" cellpadding="3" cellspacing="0">
		<caption><%=caption%></caption>
		<colgroup><col width="15%"></col><col width="35%"></col><col width="15%"></col></colgroup>
		<tr>
			<td class="labelTd">标题&nbsp;<span class="star">*</span></td>
			<td><input type="text" name="vote.title"  id="vote.title" maxlength="50" checktype="0" value='<s:property value="vote.title"/>'/></td>			
			<td class="labelTd">状态</td>
			<td>
			<select name="vote.status" id="vote.status" checktype="0">
				<option value="1" ${vote.status =="1"?'selected':''}>启用</option>
				<option value="0" ${vote.status =="0"?'selected':''}>停用</option>
			</select>
			</td>
		</tr>
		<tr>
			<td class="labelTd">规则</td>
			<td colspan="3">
				<!--1无限制  -->
				<!--2点赞  每项只能投一次:（可取消） 没有每天 -->
				<!--3每天可以给任意项投几票 : 比如可以每天给任何一个人投 1/5票-->
				<!--4每天可以给最多几项投几票: 比如可以每天给最多几个人投 1/5票-->
				<!--5每天投几票: 每天投10票（可以全投给一个人，也可以投给其他人，只能投10次）-->
				<s:radio style="width:15px;" name="vote.type" value='vote.type' list="#{'1':'无限制', '2':'点赞','3':'每天可以给任意项投几票','4':'每天可以给最多几项投几票','5':'每天投几票'}" />
				<!-- 登录就用用户过滤，未登录就用ip过滤 -->		
			</td>		
		</tr>
		<tr>
			<td class="labelTd">设置有效期</td>
			<td colspan="3">
				<select name="vote.filterdate" id="dateoption" checktype="0">
					<option value="0" ${vote.filterdate =="0"?'selected':''}>不设置</option>
					<option value="1" ${vote.filterdate =="1"?'selected':''}>设置</option>
				</select>
			</td>			
		</tr>
		<tr style="display:none;" id="voteCountLimit">
			<td class="labelTd">每天每项投几票</td>
			<td colspan="3"  >
				<input type="text" checktype="0" name="vote.votecountlimit" id="votecountlimit" maxlength="20" value='<s:property value="vote.votecountlimit"/>'/>
			</td>			
		</tr>
		<tr style="display:none;" id="voteTermLimit">
			<td class="labelTd">每天可以投几项</td>
			<td colspan="3"  >
				<input type="text" checktype="0" name="vote.votetermlimit" id="votetermlimit" maxlength="20" value='<s:property value="vote.votetermlimit"/>'/>
			</td>			
		</tr>		
        <tr style="display:none;" id="voteDateLimit">
            <td class="labelTd">投票起始时间</td>
            <td colspan=3><input class="Wdate" checktype="0" name="vote.starttime" id="starttime" readonly="readonly"
										value="<s:property value="vote.starttime"  />"
										onfocus="WdatePicker({minDate:'%y-%M-%d'})"
										type="text" style="width: 100px" />
              至
              <input class="Wdate" checktype="0" name="vote.endtime" id="endtime" readonly="readonly"
										value="<s:property value="vote.endtime"  />"
										onfocus="WdatePicker({minDate:'%y-%M-%d'})"
										type="text" style="width: 100px" />
            </td>
        </tr>		
		<tr>
			<td class="labelTd">过滤条件</td>
			<td>
				<select name="vote.filtertype" id="filtertype" checktype="0" '<s:property value="vote.filtertype"/>'>
					<option value="1" ${vote.filtertype =="1"?'selected':''}>用户过滤（需要登录）</option>
					<option value="2" ${vote.filtertype =="2"?'selected':''}>IP过滤（不需要登录）</option>
				</select>
			</td>		
			<td class="labelTd">备注</td>
			<td colspan="2"  >
				<input type="text" name="vote.remark" id="vote.remark" maxlength="120" value='<s:property value="vote.remark"/>'/>
			</td>
		</tr>
		<tr><td colspan="5" style="padding:0px">
		   <table  class="Table" id="vote.options">
		     <caption>选项列表<span class="headpan"><a onclick="addOption()">增加投票项</a></span></caption>
		     <thead </tbody><tr>
		        <td class="head" style="padding-top:0px;padding-bottom:0px;" width="10%">序号</td>
		        <td class="head" style="padding-top:0px;padding-bottom:0px;" width="10%">ID(*)</td>
		        <td class="head" style="padding-top:0px;padding-bottom:0px;" width="30%">标题(*)</td>
		        <td class="head" style="padding-top:0px;padding-bottom:0px;" width="40%">备注</td>
		        <td class="head" style="padding-top:0px;padding-bottom:0px;" width="10%">管理</td>
		     </tr></thead>
	         <tbody style="word-break:break-all">
	         <%if("0".equals(operFlag)){%>
	         <tr>
		        <td><input type="text" checktype="1" name="vote.options[0].optionOrder" value="0"></td>
		        <td><input type="text" checktype="1" name="vote.options[0].optionId" onkeyup="onlyNum(this);" maxlength="4"></td>
		        <td><input type="text" checktype="1" name="vote.options[0].optionName" maxlength="14"></td>
		        <td><input type="text" checktype="1" name="vote.options[0].optionRemark"></td>
		        <td><a onclick="deleteOption(this)">删除</a></td>
	         </tr>
	         <%}else{ %>
	         <s:iterator value="vote.options" id="vp" status='st'>
	         <tr>
		        <td><input type="text" checktype="1" name="vote.options[<s:property value="#st.index" />].optionOrder" value="<s:property value="#vp.optionOrder" />" </td>
		        <td><input type="text" checktype="1" name="vote.options[<s:property value="#st.index" />].optionId" onkeyup="onlyNum(this);" style="ime-mode:Disabled" value="<s:property value="#vp.optionId" />" maxlength="4"></td>
		        <td><input type="text" checktype="1" name="vote.options[<s:property value="#st.index" />].optionName" value="<s:property value="#vp.optionName" />" maxlength="14"></td>
		        <td><input type="text" checktype="1" name="vote.options[<s:property value="#st.index" />].optionRemark" value="<s:property value="#vp.optionRemark"/>" ></td>
		        <td><a onclick="deleteOption(this)" style="width:100%">删除</a></td>
	         </tr>
	         </s:iterator>
	         <%}%>
	         </tbody>
		   </table>	
		   </td>
		</tr>
		<tr>
		   <td colspan="4" align="center">
			<input type="submit" class="button" value="[保 存]" >&nbsp;
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
var isAdd = <%=operFlag%>;
$(function(){
	if(isAdd == 0){
		//添加
		$('input:radio[name="vote.type"]:eq(0)').attr("checked","checked");
	}else{
		//编辑
		var val=$('input:radio[name="vote.type"]:checked').val();
		checkRadio(val);
		var isHasDateLimit = $('#dateoption').val();
		if(isHasDateLimit == 1){
			$("#voteDateLimit").show();
		}
	}

	
})

var hasResults=<%=hasResults%>;
if(hasResults){
	ymPrompt.alert("已经产生投票数据，不允许修改",null,null,'警告',function(){window.close();});	
}


//单选框点击
$('input[name="vote.type"]').click(function(){
	var val = $(this).val();
	checkRadio(val);
})
function checkRadio(val){
	if(val == 3 || val == 5 ){
		//展示每天投票次数输入框
		$("#voteCountLimit").show();
		$("#voteTermLimit").hide();
	}else if(val == 4){
		$("#voteCountLimit").show();
		$("#voteTermLimit").show();
	}else{
		$("#voteCountLimit").hide();
		$("#voteTermLimit").hide()	
	}
}
$('#dateoption').change(function(){
	if($('#dateoption').val() == 1){
		$("#voteDateLimit").show();
	}else{
		$("#voteDateLimit").hide();
	}
})


var table=document.getElementById("vote.options");
function addOption(){//添加选项
	var order=getMaxOrder();//生成序号  最大值加一
	var cell=null,name=null,cellValue=null;
	var row=table.insertRow(table.rows.length);
    
    cell = row.insertCell(0);  //创建一个单元格
    name='vote.options['+order+'].optionOrder';
    cellValue='<input type="text" name="'+name+'" value="'+order+'">';
    cell.innerHTML=cellValue;  //设置单元格属性
    
    cell = row.insertCell(1);  //创建一个单元格
    name='vote.options['+order+'].optionId';
    cellValue='<input type="text" name="'+name+'"onkeyup="onlyNum(this);" maxlength="4">';
    cell.innerHTML=cellValue;  //设置单元格属性
    
    cell = row.insertCell(2);  //创建一个单元格
    name='vote.options['+order+'].optionName';
    cellValue='<input type="text" name="'+name+'" maxlength="14">';
    cell.innerHTML=cellValue;  //设置单元格属性
    
    cell = row.insertCell(3);  //创建一个单元格
    name='vote.options['+order+'].optionRemark';
    cellValue='<input type="text" name="'+name+'">';
    cell.innerHTML=cellValue;  //设置单元格属性
    
    cell = row.insertCell(4);  //创建一个单元格
    cellValue='<a onclick="deleteOption(this)">删除</a>';
    cell.innerHTML=cellValue;  //设置单元格属性
    
//    table.appendChild(row);
}
function deleteOption(obj){//删除选项
	var rows=table.rows;
	if(rows.length==2)
	{
		ymPrompt.alert("投票选项不能为空",null,null,'警告',null);
		return false;
	}
		
	var row=obj.parentNode.parentNode;
	table.deleteRow(row.rowIndex);
}
function getMaxOrder(){
	var options=table.rows;
	var big=0;
	for(var i=1;i<options.length;i++){
		var order=options[i].cells[0].firstChild.value*1;
		if(order>big)big=order;
	}
	return ++big;
}
function checkinput(){
	var name=document.getElementById("vote.title").value;
	name=name.replace(/\s+/g,"");
	if(null==name||""==name){
		ymPrompt.alert("投票标题不能为空",null,null,'警告',null);
		return false;
	}
	var titles=[],ids=[];
	var options=table.rows;
	if(options.length<2){
		ymPrompt.alert("投票选项不能为空",null,null,'警告',null);
		return false;
	}
	for(var i=1;i<options.length;i++){
		var id=options[i].cells[1].firstChild.value;
		id=id.replace(/\s+/g,"");
		if(null==id||""==id){
			ymPrompt.alert("选项编号不能为空",null,null,'警告',null);
			return false;
		}else{
			for(var j=0;j<ids.length;j++)
			{
				if(ids[j]==id)
				{
					ymPrompt.alert("第"+i+"项ID重复",null,null,'警告',null);
					return false;
				}
			}
		}
		ids.push(id);
		var title=options[i].cells[2].firstChild.value;
		title=title.replace(/\s+/g,"");
		if(null==title||""==title){
			ymPrompt.alert("第"+i+"项标题为空",null,null,'警告',null);
			return false;
		}else{
			for(var j=0;j<titles.length;j++){
				if(titles[j]==title){
					ymPrompt.alert("第"+i+"项标题重复",null,null,'警告',null);
					return false;
				}
			}
		}
		titles.push(title);	
	}
	
	//获取规则
	var val=$('input:radio[name="vote.type"]:checked').val();
	var voteCountLimit = $("#votecountlimit").val();
	var voteTermLimit = $("#votetermlimit").val(); 
	var starttime = $("#starttime").val();
	var endtime = $("#endtime").val();
	
	if(val == 3 || val == 5){
		if(voteCountLimit==""||voteCountLimit<=0){
			ymPrompt.alert("投票次数不能为空",null,null,'警告',null);
			return false;
		}
	}else if(val == 4){
		if(voteCountLimit==""||voteCountLimit<=0){
			ymPrompt.alert("投票次数不能为空",null,null,'警告',null);
			return false;
		}
		
		if(voteTermLimit==""||voteTermLimit<=0){
			ymPrompt.alert("最多投几项不能为空",null,null,'警告',null);
			return false;
		}			
	}
	
	//是否选中起始时间
	var isHasDateLimit = $('#dateoption').val();
	if(isHasDateLimit == 1){
		if(starttime == "" || endtime == ""){
			ymPrompt.alert("起始时间不能为空",null,null,'警告',null);
			return false;
		}

		if(starttime > endtime){
			ymPrompt.alert("结束时间不能大于开始时间",null,null,'警告',null);
			return false;
		}
	}
}
//
//关闭当前窗口，刷新父窗口
function colse(){
	window.opener.location.reload(); 
	window.close();
}
//数字检测
function onlyNum(obj)
{
	obj.value=obj.value.replace(/[^a-zA-Z0-9]/g,'');
}
</script>
</html>