<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<div class="header">
	<div>
		<h1>
			<bean:message key="layout.lable.title" />
		</h1>
	</div>
	<div class="langbar">
		<html:link action="/changeLanguage.do?locale=en">
			<bean:message key="layout.button.english" />
		</html:link>
		<html:link action="/changeLanguage.do?locale=ru">
			<bean:message key="layout.button.russian" />
		</html:link>
	</div>
</div>












