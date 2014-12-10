<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<html>
<head>
<link rel="stylesheet" type="text/css" href="css/style.css">
<title><bean:message key="layout.title.404" /></title>
</head>
<body>
	<div class="error">
		<bean:message key="layout.title.404" />
	</div>
	
</body>
<html:link action="/viewList">
	<bean:message key="error.redirect" />
</html:link>
</html>
