package com.epam.news.presentation.action;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.Globals;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.MappingDispatchAction;

import com.epam.news.presentation.form.LocaleForm;

public class LocaleAction extends MappingDispatchAction {

	public ActionForward changeLanguage(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		LocaleForm localeForm = (LocaleForm) form;
		ActionForward actionForward = null;
		String newLocaleStr = localeForm.getLocale();

		Locale newLocale = new Locale(newLocaleStr);
		request.getSession().setAttribute(Globals.LOCALE_KEY, newLocale);

		String currentPage = (String) request.getHeader("referer");
		if (currentPage != null) {
			actionForward = new ActionForward(currentPage, true);
		}
		else {
			actionForward = mapping.findForward("viewlist");
		}
		return actionForward;
	}

}
