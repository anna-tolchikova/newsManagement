package com.epam.news.service.impl;

import java.util.List;







import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.epam.news.dao.INewsDao;
import com.epam.news.entity.News;
import com.epam.news.exception.DaoException;
import com.epam.news.exception.ServiceException;
import com.epam.news.service.INewsService;

public class NewsServiceImpl implements INewsService {

	private static final Logger LOG = Logger.getLogger(NewsServiceImpl.class);

	private INewsDao newsDao;

	public INewsDao getNewsDao() {
		return newsDao;
	}
	
	public void setNewsDao(INewsDao newsDao) {
		this.newsDao = newsDao;
	}

	@Override
	public List<News> getAllNews() throws ServiceException {
		try {
			return newsDao.getList();
		} catch (DaoException e) {
			throw new ServiceException("Error. Cause: " + e);
		}
	}

	@Override
	public News saveNews(News news) throws ServiceException {
		try {
			if (news != null) {
				return newsDao.save(news);
			} else {
				throw new ServiceException(
						"Error. Parameter 'news' cannot be null.");
			}
		} catch (DaoException e) {
			throw new ServiceException("Error. Cause: " + e);
		}
	}

	@Override
	public void deleteNewsById(Integer id) throws ServiceException {
		try {
			if (id > 0) {
				if (newsDao.remove(id) == 0) {
					throw new ServiceException("Error. News with id = " + id
							+ " cannot be deleted.");
				}
			} else {
				throw new ServiceException(
						"Error. Parameter 'id' cannot be <=0 .");
			}
		} catch (DaoException e) {
			throw new ServiceException("Error. Cause: " + e);
		}

	}

	@Override
	public News findNewsById(Integer id) throws ServiceException {
		try {
			if (id > 0) {
				return newsDao.fetchById(id);
			} else {
				throw new ServiceException(
						"Error. Parameter 'id' cannot be <=0 .");
			}
		} catch (DaoException e) {
			throw new ServiceException("Error. Cause: " + e);
		}
	}

	@Override
	public News findNewsByIdForGetAction(Integer id) throws ServiceException {
		try {
			if (id > 0) {
				News news = null;
				news = newsDao.fetchById(id);
				if (news != null) {
					return news;
				} else {
					throw new ServiceException("Error. News with id = '" + id
							+ "' doesn't exist.");
				}
			} else {
				throw new ServiceException(
						"Error. Parameter 'id' cannot be <=0 .");
			}
		} catch (DaoException e) {
			throw new ServiceException("Error. Cause: " + e);
		}
	}

	@Override
	public void updateNews(News news) throws ServiceException {
		try {
			newsDao.update(news);
		} catch (DaoException e) {
			throw new ServiceException("Error. Cause: " + e);
		}

	}

	@Override
	public void deleteNewsList(List<Integer> idList) throws ServiceException {
		try {
			if (!idList.isEmpty()) {
				int affectedRowsCount = newsDao.deleteList(idList);
				if (affectedRowsCount < idList.size()) {
					throw new ServiceException(
							"Error. Not all news were deleted. ");
				} else if (affectedRowsCount > idList.size()) {
					throw new ServiceException(
							"Error. Some error in your query. There where deleted more news (" + affectedRowsCount + ")");
				}
			} else {
				LOG.debug("News id list for deleting is empty. Nothing was deleted.");
			}
		} catch (DaoException e) {
			throw new ServiceException("Error. Cause: " + e);
		}

	}

}
