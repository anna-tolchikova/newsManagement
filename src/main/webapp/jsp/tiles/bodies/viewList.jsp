<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>


<c:set var="isFromViewNews" value="false" scope="session" />

<logic:notEmpty name="newsForm" property="newsList">
	<c:set var="confirmationMessage"><bean:message key="errors.news.confirm.listdelete" /></c:set>
	<html:form action="/deleteList" onsubmit="return confirmDeleteNewsList('${confirmationMessage}');">
		<c:forEach items="${newsForm.newsList}" var="news">
			<div class="newsMessage">
				<div>
					<div class="newsheader">
						<div class="newsdate">
							<c:set var="datePattern">
								<bean:message key="layout.date.pattern"/>
							</c:set>

							<fmt:formatDate value="${news.postDate}"
								pattern="${datePattern}" />
						</div>
						<div class="newstitle">
							<bean:message key="layout.lable.news" />
							<c:out value="${news.title}" />
						</div>
					</div>
					<div class="newsbrief">
						<c:out value="${news.brief}" />
					</div>
					<div class="controlbar">
						<html:link action="/viewNews" paramId="id" paramName="news" paramProperty="id">
							<bean:message key="layout.button.view" />
						</html:link>
						<html:link action="/editNews" paramId="id" paramName="news" paramProperty="id">
							<bean:message key="layout.button.edit" />
						</html:link>
						<html:multibox name="newsForm" property="listNewsId" value="${news.id}" />
					</div>
				</div>
			</div>
			<br>
		</c:forEach>

		
		<div id="deletebutton">
			<html:submit styleClass="control">
				<bean:message key="layout.button.delete" />
			</html:submit>
		</div>
	</html:form>
</logic:notEmpty>
