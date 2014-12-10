package com.epam.news.presentation.form;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public class LocaleForm extends ActionForm{


	private static final long serialVersionUID = -1344578723070771964L;
	private String locale;

	public String getLocale() {
		return locale;
	}

	public void setLocale(String locale) {
		this.locale = locale;
	}

	@Override
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		this.locale=null;
	}
	
	
}
