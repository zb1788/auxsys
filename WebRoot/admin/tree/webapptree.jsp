<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page language="java" import="com.vcom.auxsys.service.*,com.vcom.auxsys.entitys.pojo.*,org.springframework.web.context.support.*,org.springframework.web.context.*" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	// 是否显示栏目，0不显示栏目仅显示站点、1显示所有叶子节点，默认为1
	String showLeaf = request.getParameter("showLeaf") == null ?"1":request.getParameter("showLeaf");
	// 是否显示checkbox、radio，0不显示、1全部显示、2叶子节点显示，默认为0
	String showType = request.getParameter("showType") == null ?"0":request.getParameter("showType");
	// 按钮类型，checkbox复选框、radio单选框，默认为checkbox
	String buttonType = request.getParameter("buttonType") == null ?"checkbox":request.getParameter("buttonType");
	// clicknode的事件类型，显示checkbox、radio时该参数无效
	String eventType = request.getParameter("eventType") == null ?"":request.getParameter("eventType");
	// checkbox、radio默认选中的id值，用英文逗号分隔
	String selectIds = request.getParameter("selectIds") == null ?"":request.getParameter("selectIds");
	// checkbox、radio默认选中的是否不可用，0可用、1不可用，默认为1
	String checkedDisable = request.getParameter("checkedDisable") == null ?"1":request.getParameter("checkedDisable");
	// webappId站点编码，默认为空。若不为空，显示单个站点栏目结构
	String webappCode = request.getParameter("webappCode") == null ?"":request.getParameter("webappCode");
	//应用类型vote-投票 ，comments-评论
	String appType = request.getParameter("appType") == null ?"comments":request.getParameter("appType");
	//投票id
	String voteid = request.getParameter("voteid") == null ?"":request.getParameter("voteid");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
	"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html>
<head>
<title>Webapp Tree</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<script type="text/javascript" src="xloadtree/xtree.js"></script>
<script type="text/javascript" src="xloadtree/xmlextras.js"></script>
<script type="text/javascript" src="xloadtree/xloadtree.js"></script>
<script type="text/javascript" src="<%=path %>/js/jquery.js"></script>
<link type="text/css" rel="stylesheet" href="xloadtree/xtree.css" />
<style type="text/css">
body {
	background:	white;
	color:		black;
	font-size:12px;
	word-break:keep-all
}
<%if ("0".equals(showType)) {%>
.selected {
	background:	#BBBBBB;
}
.selected-inactive{
	background:	#BBBBBB;
}
<%}%>
</style>
<script type="text/javascript" >
var showType = '<%=showType%>';
var appType = '<%=appType%>';
function clicknode(webappCode,channelCode,name,voteid){
	//var eventType = (channelCode!="null"?"channelCode":"webappCode");
	//var webappCode = (channelCode!="null"?channelCode:webappCode);
	if(channelCode!="null"){
		channelCode = encodeURIComponent(encodeURIComponent(channelCode));
	}
	if (showType != '0') return;
	if (appType == 'comments'){
		parent.document.getElementById("centerframe").src="<%=path %>/admin/comments/getAll.action?channelCode="+channelCode+"&webappCode="+webappCode;
	}else if (appType=='vote'){
	    parent.document.getElementById("centerframe").src="<%=path %>/admin/vote/getVoteResults.action?channelCode="+channelCode+"&webappCode="+webappCode+"&voteid="+voteid;
	}else {
		return;
	}
}
</script>
</head>
<body>
<script type="text/javascript">
/// XP Look
webFXTreeConfig.rootIcon		= "xloadtree/images/xp/folder.png";
webFXTreeConfig.openRootIcon	= "xloadtree/images/xp/openfolder.png";
webFXTreeConfig.folderIcon		= "xloadtree/images/xp/folder.png";
webFXTreeConfig.openFolderIcon	= "xloadtree/images/xp/openfolder.png";
webFXTreeConfig.fileIcon		= "xloadtree/images/xp/file.png";
webFXTreeConfig.lMinusIcon		= "xloadtree/images/xp/Lminus.png";
webFXTreeConfig.lPlusIcon		= "xloadtree/images/xp/Lplus.png";
webFXTreeConfig.tMinusIcon		= "xloadtree/images/xp/Tminus.png";
webFXTreeConfig.tPlusIcon		= "xloadtree/images/xp/Tplus.png";
webFXTreeConfig.iIcon			= "xloadtree/images/xp/I.png";
webFXTreeConfig.lIcon			= "xloadtree/images/xp/L.png";
webFXTreeConfig.tIcon			= "xloadtree/images/xp/T.png";
webFXTreeConfig.selectIDs		= '<%=selectIds%>';
webFXTreeConfig.checkedDisable	= eval('<%=checkedDisable%>'=='1');
webFXTreeConfig.buttonType		= '<%=buttonType%>';
var tree = new WebFXLoadTree("所有应用", "webappxml.jsp?appType=<%=appType%>&showType=<%=showType%>&webappCode=<%=webappCode%>&showLeaf=<%=showLeaf%>&voteid=<%=voteid%>");
document.write(tree);
</script>
</body>
</html>
