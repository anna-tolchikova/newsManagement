<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>


<c:set var="isFromViewNews" value="true" scope="session" />

	<table class="viewnewstable">
		<tbody>
		<col width="15%">
		<col width="*">

		<tr>
			<td class="innertitle"><bean:message key="layout.form.title" /></td>
			<td><c:out value="${testNewsForm.title}" /></td>
		</tr>
		<tr>

			<td class="innertitle"><bean:message key="layout.form.postdate" /></td>
			<td><c:out value="${testNewsForm.displayPostDate}" /></td>
		</tr>
		<tr>
			<td class="innertitle"><bean:message key="layout.form.brief" /></td>
			<td class="textfield"><c:out value="${testNewsForm.brief}" /></td>
		</tr>
		<tr>
			<td class="innertitle"><bean:message key="layout.form.content" /></td>
			<td class="textfield"><c:out value="${testNewsForm.content}" /></td>
		</tr>
		</tbody>
	</table>
	<div class="createbutton">
		<html:link action="/editNews" paramId="id" paramName="testNewsForm"
			paramProperty="id">
			<html:button property="button">
				<bean:message key="layout.button.edit" />
			</html:button>
		</html:link>
		<c:set var="confirmationMessage"><bean:message key="errors.news.confirm.delete" /></c:set>
		<html:link action="/deleteNews" paramId="id" paramName="testNewsForm"
			paramProperty="id" onclick="return confirmDeleteNews('${confirmationMessage}');">
			<html:button property="button">
				<bean:message key="layout.button.delete" />
			</html:button>
		</html:link>
	</div>
