package com.epam.news.presentation.action;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.MappingDispatchAction;

import com.epam.news.presentation.form.NewsForm;
import com.epam.news.presentation.form.TestNewsForm;
import com.epam.news.dao.INewsDao;
import com.epam.news.entity.News;

public class NewsAction extends MappingDispatchAction {

	private static final Logger LOG = Logger.getLogger(NewsAction.class);

	private final static String SUCCESS_PATH = "success";
	private final static String ERROR_PATH = "error";

	private INewsDao newsDao;

	public INewsDao getNewsDao() {
		return newsDao;
	}

	public void setNewsDao(INewsDao newsDao) {
		this.newsDao = newsDao;
	}

	public NewsAction() {
		super();
	}

	public ActionForward viewList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		LOG.info("VIEW LIST ACTION");
		ArrayList<News> newsList = null;
		newsList = newsDao.getList();
		NewsForm newsForm = (NewsForm) form;
		newsForm.setNewsList(newsList);
		newsForm.setListNewsId(null);
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

		TestNewsForm newsForm = (TestNewsForm) form;
		int id = newsForm.getId();
		if (id != 0) {
			LOG.info("EDIT ACTION");
			News news = new News();
			news = newsDao.fetchById(id);
			System.out.println(news);
			fillTestNewsForm(newsForm, news);
		} else {
			LOG.error("EDIT ACTION NO ID");
			return mapping.findForward(ERROR_PATH);
		}

		return mapping.findForward(SUCCESS_PATH);
	}

	public ActionForward saveNews(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		if (isCancelled(request)) {
			LOG.info("EDIT CANCEL ACTION");
			TestNewsForm newsForm = (TestNewsForm) form;
			int idViewedNews = newsForm.getIdViewedNews();
			if (idViewedNews != 0) {
				News news = null;
				news = newsDao.fetchById(idViewedNews);
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
				newsDao.save(news);
				fillTestNewsForm(testNewsForm, news);
			} else {
				LOG.info("SAVE (EDIT) ACTION");
				fillNews(news, testNewsForm);
				newsDao.update(news);
				fillTestNewsForm(testNewsForm, news);
			}
			return mapping.findForward("viewnews");
		}
	}

	public ActionForward deleteNews(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		TestNewsForm newsForm = (TestNewsForm) form;
		int id = newsForm.getId();
		if (id != 0) {
			LOG.info("DELETE NEWS ACTION");
			if (!newsDao.remove(id)) {
				return mapping.findForward(ERROR_PATH);
			}
		} else {
			LOG.error("DELETE ACTION: NO ID");
			return mapping.findForward(ERROR_PATH);
		}

		return mapping.findForward(SUCCESS_PATH);
	}

	public ActionForward deleteList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		LOG.info("DELETE NEWS LIST ACTION");
		NewsForm newsForm = (NewsForm) form;
		if (newsForm.getListNewsId() != null) {
			List<Integer> idList = Arrays.asList(newsForm.getListNewsId());
			if (idList.size() != 0) {
				newsDao.deleteList(idList);
			}
		}
		return mapping.findForward(SUCCESS_PATH);
	}

	public ActionForward viewNews(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TestNewsForm newsForm = (TestNewsForm) form;
		int id = newsForm.getId();
		News news = null;

		if (id == 0) {

			// int idViewedNews = newsForm.getIdViewedNews();
			// if (idViewedNews != 0) {
			// news = newsDao.fetchById(idViewedNews);
			// if (news != null) {
			// fillTestNewsForm(newsForm, news);
			// newsForm.setIdViewedNews(idViewedNews);
			// }
			// }
			LOG.error("VIEW ACTION: NO ID IN FORM!!!");
			return mapping.findForward(ERROR_PATH);
		} else {
			LOG.info("VIEW ACTION: VIEW ACTION NEWS ID = " + id);
			news = newsDao.fetchById(id);
			if (news != null) {
				fillTestNewsForm(newsForm, news);
				newsForm.setIdViewedNews(id);
			}
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
			news = newsDao.fetchById(idViewedNews);
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
