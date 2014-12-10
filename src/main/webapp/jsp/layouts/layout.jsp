<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE HTML>
<html:html>
<head>
<html:base/>
<meta charset="utf-8"/>
<link rel="stylesheet" type="text/css" href="../../css/style.css"/>
<script type="text/javascript" src="../messages.jsp"></script>


<c:set var="pageTitle"><tiles:getAsString name="title"/></c:set>
<title><bean:message key="${pageTitle}"/></title>

</head>
<body class="layout">
	<tiles:insert attribute="header" />
	<div class="clear"></div>
	<div class="wrapper">
		
		<div class="content">
			<div class="titlelist">
				<html:link action="/viewList">
					<bean:message key="layout.title.welcome" />
				</html:link>
				<bean:message key="layout.title.newslist" />
			</div>
			<tiles:insert attribute="body" />
		</div>
		
		<tiles:insert attribute="menu" />
	</div>
	<div class="clear"></div>
	<tiles:insert attribute="footer" />
</body>
</html:html>