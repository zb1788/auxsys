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
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<meta http-equiv="content-type" content="text/html; charset=UTF-8">
		<title>评论管理</title>
		<link rel="stylesheet" id="ext-all-css" type="text/css"
			href="<%=path%>/vcomframe/ext/resources/css/ext-all.css" />
		<link rel="stylesheet" type="text/css"
			href="<%=path%>/vcomframe/css/common.css" />
		<script type="text/javascript" src="<%=path%>/vcomframe/js/common.js"></script>
		<script type="text/javascript" id="ext-base-js"
			src="<%=path%>/vcomframe/ext/adapter/ext/ext-base.js"></script>
		<script type="text/javascript" id="ext-all-js"
			src="<%=path%>/vcomframe/ext/ext-all.js"></script>
		<script type="text/javascript">
	    Ext.BLANK_IMAGE_URL = 'vcomframe/images/s.gif';
			Ext.onReady(function(){
					var west=new Ext.Panel({
			        region:"west",
			        width:200,
			        minSize:0,
			        maxSize:200,
			        split:true,        
			        collapsible: true,
			        title:"应用列表",
			        autoScroll:true,
			        items:new Ext.BoxComponent({
							el: 'east',
							height:'100%'
						}),
			        collapsed:false
			    });
				_center=new Ext.Panel({
						region:'center',
			            title: null,
			            collapsible: true,
			            split:true,
			            width: 225,
			            minSize: 175,
			            maxSize: 300,
			            layout:'fit',
			            margins:'0 0 0 0',
			            items:new Ext.BoxComponent({
							el: 'center',
							height:'100%'
						})    
				});
				var viewport = new Ext.Viewport({
					layout:"border",
					items:[_center,west]
				});
			});
		</script>
	</head>

	<body>
		<div id="center">
			<iframe topmargin="0" leftmargin="0" id="centerframe" scrolling="no"
				marginheight="0" marginwidth="0" style="width: 100%; height: 100%"
				src="<%=path%>/admin/common/Tips.jsp" frameborder="0"></iframe>
		</div>
		<div id="east">
			<iframe topmargin="0" leftmargin="0" id="moduletree" scrolling="auto"
				marginheight="0" marginwidth="0" style="width: 100%; height: 100%"
				src="<%=path%>/admin/tree/webapptree.jsp?appType=comments"
				frameborder="0"></iframe>
		</div>
	</body>
</html>