package com.epam.news.presentation.form;

import java.sql.Date;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.Globals;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.validator.ValidatorForm;

import com.epam.news.helperbean.DateConverterBean;
import com.epam.news.presentation.action.NewsAction;

public final class TestNewsForm extends ValidatorForm {

	private static final Logger LOG = Logger.getLogger(TestNewsForm.class);
	private static final long serialVersionUID = -3823581748283691103L;

	private int id;
	private String title;
	private String brief;
	private String content;
	private Date postDate;
	private Locale locale;
	private int idViewedNews;
	private boolean isFromViewNews;

	public TestNewsForm() {
		super();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getBrief() {
		return brief;
	}

	public void setBrief(String brief) {
		this.brief = brief;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Date getPostDate() {
		return postDate;
	}

	public void setPostDate(Date postDate) {
		this.postDate = postDate;
	}

	public Locale getLocale() {
		return locale;
	}

	public void setLocale(Locale locale) {
		this.locale = locale;
	}

	public String getDisplayPostDate() {
		System.out.println("get display post date");
		return DateConverterBean.convertDateToStringByLocale(postDate, locale);
	}

	public void setDisplayPostDate(String postDate) {
		this.postDate = DateConverterBean.convertStringToDateByLocale(postDate,
				locale);
	}

	public int getIdViewedNews() {
		return idViewedNews;
	}

	public void setIdViewedNews(int idViewedNews) {
		this.idViewedNews = idViewedNews;
	}

	public boolean isFromViewNews() {
		return isFromViewNews;
	}

	public void setFromViewNews(boolean isFromViewNews) {
		this.isFromViewNews = isFromViewNews;
	}

	@Override
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		LOG.info("\nRESET FORM\n");
		HttpSession session = request.getSession();
		if (session != null) {
			this.locale = (Locale) session.getAttribute(Globals.LOCALE_KEY);
		} else {
			this.locale = Locale.getDefault();
		}

		String isFromViewNews = (String) request.getSession().getAttribute(
				"isFromViewNews");
		if (isFromViewNews != null && "true".equals(isFromViewNews)) {
			LOG.info("TESTNEWSFORM: request.getAttribute = idViewedNews"
					+ this.idViewedNews);
		} else {
			this.idViewedNews = 0;
		}
	}

	@Override
	public ActionErrors validate(ActionMapping arg0, HttpServletRequest arg1) {
		return super.validate(arg0, arg1);
	}

}
