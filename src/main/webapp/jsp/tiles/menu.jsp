<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<div class="menu">
	<div class="menuTitle">
		<bean:message key="layout.title.welcome" />
	</div>
	<div id="menuInner">
		<ul>
			<li><html:link action="/viewList">
					<bean:message key="layout.button.list" />
				</html:link></li>
			<li><html:link action="/addNews">
					<bean:message key="layout.button.add" />
				</html:link></li>
		</ul>
	</div>
</div>