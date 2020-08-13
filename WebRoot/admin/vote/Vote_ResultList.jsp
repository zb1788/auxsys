<%@page language="java" import="java.util.*" pageEncoding="UTF-8"%>
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
<title>投票结果列表</title>
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
<script type="text/javascript">
$(function(){
	$("select option[value='"+$("#optionid").val()+"']").attr("selected", "selected");  
})
</script>
</head>
<body scroll="auto">
<form id="form" name="form" method="post" action="getVoteResults.action">
<input type="hidden" id="webappcode" name ="webappcode" value="<s:property value="webappcode"  />"/>
<input type="hidden" id="channelCode"  name="channelCode" value="<s:property value="channelCode" />"/>
<input type="hidden" id="voteid"  name="voteid" value="<s:property value="voteid" />"/>
  <table width="100%">
    <TR>
      <td><table align="center" width="100%" class="TableForm" cellpadding="3" cellspacing="0">
          <caption> 查询  </caption>
          <tr>
            <td class="labelTd">信息标题:</td>
            <td><input type="text" name="voteResult.infotitle" id="infotitle" value="<s:property value="voteResult.infotitle" />" maxlength="25" style="width: 120px" /> </td>
            <td class="labelTd">信息id:</td>
            <td><input type="text" name="voteResult.infoid" id="infoid" value="<s:property value="voteResult.infoid" />"  maxlength="25"style="width: 120px" /> </td>
          </tr>
          <tr>
            <td class="labelTd">地区:</td>
            <td><input type="text" name="voteResult.area" id="area" value="<s:property value="voteResult.area" />"  maxlength="25" style="width: 120px" /> </td>
          	<td class="labelTd">排序项（默认降序）:</td>
          	<td>
          		<input type="hidden" name="optionid" id="optionid" value="<s:property value="optionid" />"/>
              <select id="v_optionid" name="v_optionid" style="width: 70px">
		              <option value="">全部</option>
		            		<s:if test="voteOptionsList!=null">
		            		<s:iterator value="voteOptionsList" id="options">
		            			<option value="<s:property value="#options.optionId" />"><s:property value="#options.optionName" /></option>
		            		</s:iterator>
		            		</s:if>
              </select>
          	</td>
          	
          </tr>
          <tr>
            <td colspan=4 align=center>
            	<input type="button" class="button" value="[查询]" onClick="search();" />
              &nbsp;
              <input type="button" class="button" value="[重置]" onClick="resetForm();" />
            </td>
          </tr>
        </table></td>
    </tr>
    <tr>
      <td><table class="Table" width="100%" cellpadding="3" cellspacing="0" align="center">
          <caption>
          	投票结果列表
          </caption>
          <tr>
            <td class="head">&nbsp;</td>
            <td class="head td_author">&nbsp;信息id</td>
            <td class="head td_author">&nbsp;信息标题</td>
            <td class="head td_state">&nbsp;地区</td>
            <td class="head td_state">&nbsp;更新时间</td>
            <s:if test="voteOptionsList!=null">
            	<s:iterator value="voteOptionsList" id="voteoption" status="st">
            			<td class="head td_type">
            				&nbsp;<s:property value="#voteoption.optionName" />
            			</td>
            	</s:iterator>
            </s:if>
          </tr>
          <s:if test="null!=voteResultsList">
            <s:iterator value="voteResultsList" id="voteResult" status="st">
              <tr onmouseover="changecolor(this,1);"onmouseout="changecolor(this,2);"
              <s:if test="!#st.odd">class="custom"</s:if>>
             		<td class="changecol">&nbsp;<s:property value="#st.index+1" /></td>
                <td class="changecol td_author">&nbsp;<s:property value="infoid" />
                </td>
                <td class="changecol td_author">&nbsp;<s:property value="infotitle" />
                </td>
                <td class="changecol td_mtime">&nbsp;<s:property value="area" />
                </td>
                <td class="changecol td_pubtime">&nbsp;<s:property value="updatetime" />
                </td>
                <s:if test="voteOptions!=null">
	            		<s:iterator value="voteOptions" id="options">
	            			<td class="changecol">
	            				&nbsp;<s:property value="#options.results" />
	            			</td>
	            		</s:iterator>
            		</s:if>
              </tr>
            </s:iterator>
          </s:if>
          <tr>
            <td colspan="10" align="right" class="head">&nbsp;<s:property value="pageBar" escape="false" />
            </td>
          </tr>
        </table></td>
    </tr>
  </table>
</form>
</body>
<script type="text/javascript">
	 function search(){
		 $("#optionid").val($("#v_optionid").val());
		 form.submit();
	 }
   function resetForm(){
	   $("#infotitle").val("");
	   $("#infoid").val("");
	   $("#area").val("");
	   $("select option[value='']").attr("selected", "selected");
   }
</script>
</html>
