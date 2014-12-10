<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<script type="text/javascript" src="../../js/validator-js.js"></script>
<html:errors/>
<html:form action="/saveNews" styleClass="message"
	onsubmit="return validateTestNewsForm(this); ">
	<html:hidden name="testNewsForm" property="id" />
	<table class="viewnewstable">
		<tbody>
		<col width="15%">
		<col width="*">
		<tr>
			<td class="innertitle"><bean:message key="layout.form.title" /></td>
			<td><html:text name="testNewsForm" property="title"
					maxlength="100" value="${testNewsForm.title}" /></td>
		</tr>
		<tr>

			<td class="innertitle"><bean:message key="layout.form.postdate" /></td>
			<td><html:text name="testNewsForm" property="displayPostDate"
					value="${testNewsForm.displayPostDate}" /></td>
		</tr>
		<tr>
			<td class="innertitle"><bean:message key="layout.form.brief" /></td>
			<td class="textfield"><html:textarea rows="10"
					name="testNewsForm" property="brief" value="${testNewsForm.brief}" /></td>
		</tr>
		<tr>
			<td class="innertitle"><bean:message key="layout.form.content" /></td>
			<td class="textfield"><html:textarea rows="20"
					name="testNewsForm" property="content"
					value="${testNewsForm.content}" /></td>
		</tr>
		</tbody>
	</table>
	<div class="createbutton">
		<html:submit onclick="">
			<bean:message key="layout.button.save" />
		</html:submit>
		<html:cancel >
			<bean:message key="layout.button.cancel" />
		</html:cancel>
		
	</div>
</html:form>