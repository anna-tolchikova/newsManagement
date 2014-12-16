<%@ page isErrorPage="true" language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<html>
<head>
<link rel="stylesheet" type="text/css" href="css/style.css">
<title><bean:message key="layout.title.exception" /></title>
</head>
<body>
	<div class="error">
		<bean:message key="layout.title.exception" />
	</div>
	
</body>
<html:link action="/viewList">
	<bean:message key="error.redirect" />
</html:link>
<p>
<c:if test="${not empty pageContext.errorData}">
        </br>
        Request from <c:out value="${pageContext.errorData.requestURI}"/> is failed
        <br/>
        Servlet name or type: <c:out value="${pageContext.errorData.servletName}"/>
        <br/>
        Status code: <c:out value="${pageContext.errorData.statusCode}"/>
        <br/>
        Exception: <c:out value="${pageContext.errorData.throwable}"/>
        <br/>
        Exception cause: <c:out value="${pageContext.errorData.throwable.cause}"/>
        <br/>
        Exception trace:
        <br/>
        <c:forEach var="traceElem" items="${pageContext.exception.stackTrace}">
            <c:out value="${traceElem}"/><br/>
        </c:forEach>
    </c:if>
</p>
</html>
