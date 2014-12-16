package com.epam.news.presentation.action;

import java.sql.Date;
import java.util.List;
import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.MappingDispatchAction;
import org.springframework.beans.factory.annotation.Autowired;

import com.epam.news.presentation.form.NewsForm;
import com.epam.news.presentation.form.TestNewsForm;
import com.epam.news.service.INewsService;
import com.epam.news.aspects.Monitor;
import com.epam.news.entity.News;
import com.epam.news.exception.ServiceException;

@Monitor
public class NewsAction extends MappingDispatchAction {

	private static final Logger LOG = Logger.getLogger(NewsAction.class);

	private final static String SUCCESS_PATH = "success";
	private final static String ERROR404_PATH = "error404";

	private INewsService newsService;

	public INewsService getNewsService() {
		return newsService;
	}

	public void setNewsService(INewsService newsService) {
		this.newsService = newsService;
	}

	public NewsAction() {
		super();
	}

	public ActionForward viewList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		LOG.info("VIEW LIST ACTION");
		try {
			List<News> newsList = null;
			newsList = newsService.getAllNews();
			NewsForm newsForm = (NewsForm) form;
			newsForm.setNewsList(newsList);
			newsForm.setListNewsId(null);
		} catch (ServiceException e) {
			LOG.error(e);
			return mapping.findForward(ERROR404_PATH);
		}
		return mapping.findForward(SUCCESS_PATH);
	}

	public ActionForward addNews(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		LOG.info("ADD ACTION");
		TestNewsForm newsForm = (TestNewsForm) form;
		News news = new News();
		fillTestNewsForm(newsForm, news);
		Date curDate = new Date(System.currentTimeMillis());
		newsForm.setPostDate(curDate);
		return mapping.findForward(SUCCESS_PATH);

	}

	public ActionForward editNews(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		LOG.info("EDIT ACTION");
		try {
			TestNewsForm newsForm = (TestNewsForm) form;
			int id = newsForm.getId();
			News news = new News();
			news = newsService.findNewsByIdForGetAction(id);
			fillTestNewsForm(newsForm, news);
		} catch (ServiceException e) {
			LOG.error(e);
			return mapping.findForward(ERROR404_PATH);
		}
		return mapping.findForward(SUCCESS_PATH);
	}

	public ActionForward saveNews(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		try {
			if (isCancelled(request)) {
				LOG.info("EDIT CANCEL ACTION");
				TestNewsForm newsForm = (TestNewsForm) form;
				int idViewedNews = newsForm.getIdViewedNews();
				if (idViewedNews != 0) {
					News news = null;
					news = newsService.findNewsById(idViewedNews);
					if (news != null) {
						fillTestNewsForm(newsForm, news);
						return mapping.findForward("viewnews");
					} else {
						return mapping.findForward("viewlist");
					}
				} else {
					return mapping.findForward("viewlist");
				}
			} else {
				TestNewsForm testNewsForm = (TestNewsForm) form;
				int id = testNewsForm.getId();
				News news = new News();
				if (id == 0) {
					LOG.info("SAVE (ADD NEW) ACTION");
					fillNews(news, testNewsForm);
					newsService.saveNews(news);
					fillTestNewsForm(testNewsForm, news);
				} else {
					LOG.info("SAVE (EDIT) ACTION");
					fillNews(news, testNewsForm);
					newsService.updateNews(news);
					fillTestNewsForm(testNewsForm, news);
				}
				return mapping.findForward("viewnews");
			}
		} catch (ServiceException e) {
			LOG.error(e);
			return mapping.findForward(ERROR404_PATH);
		}
	}

	public ActionForward deleteNews(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		LOG.info("DELETE NEWS ACTION");
		try {
			TestNewsForm newsForm = (TestNewsForm) form;
			int id = newsForm.getId();
			newsService.deleteNewsById(id);
		} catch (ServiceException e) {
			LOG.error(e);
			return mapping.findForward(ERROR404_PATH);
		}
		return mapping.findForward(SUCCESS_PATH);
	}

	public ActionForward deleteList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		LOG.info("DELETE NEWS LIST ACTION");
		try {
			NewsForm newsForm = (NewsForm) form;
			if (newsForm.getListNewsId() != null) {
				List<Integer> idList = Arrays.asList(newsForm.getListNewsId());
				newsService.deleteNewsList(idList);
			}
		} catch (ServiceException e) {
			LOG.error(e);
			return mapping.findForward(ERROR404_PATH);
		}
		return mapping.findForward(SUCCESS_PATH);
	}

	public ActionForward viewNews(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		LOG.info("VIEW ACTION");
		try {
			TestNewsForm newsForm = (TestNewsForm) form;
			int id = newsForm.getId();
			News news = newsService.findNewsByIdForGetAction(id);
			fillTestNewsForm(newsForm, news);
			newsForm.setIdViewedNews(id);
		} catch (ServiceException e) {
			LOG.error(e);
			return mapping.findForward(ERROR404_PATH);
		}
		return mapping.findForward(SUCCESS_PATH);
	}

	public ActionForward editCancel(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		LOG.info("EDIT CANCEL ACTION");
		TestNewsForm newsForm = (TestNewsForm) form;
		int idViewedNews = newsForm.getIdViewedNews();
		if (idViewedNews != 0) {
			News news = null;
			news = newsService.findNewsById(idViewedNews);
			if (news != null) {
				fillTestNewsForm(newsForm, news);
				return mapping.findForward("viewnews");
			} else {
				return mapping.findForward("viewlist");
			}
		} else {
			return mapping.findForward("viewlist");
		}
	}

	private void fillNews(News news, TestNewsForm newsForm) {
		news.setId(newsForm.getId());
		news.setTitle(newsForm.getTitle());
		news.setBrief(newsForm.getBrief());
		news.setPostDate(newsForm.getPostDate());
		news.setContent(newsForm.getContent());
	}

	private void fillTestNewsForm(TestNewsForm newsForm, News news) {
		newsForm.setId(news.getId());
		newsForm.setTitle(news.getTitle());
		newsForm.setPostDate(news.getPostDate());
		newsForm.setBrief(news.getBrief());
		newsForm.setContent(news.getContent());
	}

}
