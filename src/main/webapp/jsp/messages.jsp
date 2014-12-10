<%@page contentType="text/javascript" pageEncoding="UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>

var msgTitleRequired 			= "<bean:message key="errors.news.title.required" />";
var msgDateRequired 			= "<bean:message key="errors.news.date.required" />";
var msgBriefRequired 			= "<bean:message key="errors.news.brief.required" />";
var msgContentRequired 			= "<bean:message key="errors.news.content.required" />";
var msgDateFormat 				= "<bean:message key="errors.news.date.date" />"; 
var msgTitleIsBiger 			= "<bean:message key="errors.news.title.maxlength" />"; 
var msgBriefIsBiger 			= "<bean:message key="errors.news.brief.maxlength" />"; 
var msgContentIsBiger 			= "<bean:message key="errors.news.content.maxlength" />";
var msgRemoveListConfirmMessage = "<bean:message key="errors.news.confirm.listdelete"/>";
var msgRemoveConfirmMessage 	= "<bean:message key="errors.news.confirm.delete"/>";
var msgNothingNewsForDelete 	= "<bean:message key="errors.news.nothing.news.for.delete"/>";
var msgDatePattern 				= "<bean:message key="layout.date.pattern"/>";
