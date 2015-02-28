package com.epam.news.dao.impl;

import java.util.List;

import com.epam.news.dao.INewsDao;
import com.epam.news.entity.News;
import com.epam.news.exception.DaoException;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public final class HibernateNewsDaoImpl implements INewsDao {

	private final static Logger LOG = Logger.getLogger(HibernateNewsDaoImpl.class);

	private static final String HQL_SELECT_ALL_NEWS = "FROM News ORDER BY postDate DESC, id DESC";
	private static final String HQL_SELECT_NEWS_BY_ID = "FROM News WHERE id = :news_id";
	private final static String HQL_DELETE_BY_ID = "DELETE News "
			+ "WHERE id = :news_id";
	private final static String HQL_DELETE_BY_ID_LIST = "DELETE News "
			+ "WHERE id IN :news_id_list";
	
	private SessionFactory sessionFactory;

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}
	
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<News> getList() throws DaoException {
		List<News> personsList = null;
		try {
			Session session = this.sessionFactory.getCurrentSession();
			personsList = session.createQuery(HQL_SELECT_ALL_NEWS).list();
		} catch (Exception e) {
			throw new DaoException("Error during getting list of news:" + e);
		}
		return personsList;
	}

	@Override
	public News save(News news) throws DaoException {
		try {
			Session session = this.sessionFactory.getCurrentSession();
			session.save(news);
		} catch (Exception e) {
			throw new DaoException("Error during saving new news:" + e);
		}
		return news;
	}

	@Override
	public int remove(Integer id) throws DaoException {
		int affectedRowsCount = 0;
		try {
			Session session = this.sessionFactory.getCurrentSession();
			Query query = session.createQuery(HQL_DELETE_BY_ID);
			query.setParameter("news_id", id);
			affectedRowsCount = query.executeUpdate();
		} catch (Exception e) {
			throw new DaoException("Error during removing news:" + e);
		}
		return affectedRowsCount;
	}

	@Override
	public News fetchById(Integer id) throws DaoException {
		News news = null;
		try {
			Session session = this.sessionFactory.getCurrentSession();
			Query query = session.createQuery(HQL_SELECT_NEWS_BY_ID);
			query.setParameter("news_id", id);
			news  = (News)query.uniqueResult();
		} catch (Exception e) {
			throw new DaoException("Error during fetching news by id:" + e);
		}
		return news;
	}

	@Override
	public void update(News news) throws DaoException {
		try {
			Session session = this.sessionFactory.getCurrentSession();
			session.update(news);
		} catch (Exception e) {
			throw new DaoException("Error during updating news:" + e);
		}

	}

	@Override
	public int deleteList(List<Integer> idList) throws DaoException {
		int affectedRowsCount = 0;
		try {
			Session session = this.sessionFactory.getCurrentSession();
			Query query = session.createQuery(HQL_DELETE_BY_ID_LIST)
					.setParameterList("news_id_list", idList);
			affectedRowsCount = query.executeUpdate();
		} catch (Exception e) {
			throw new DaoException("Error during removing news list:" + e);
		}
		return affectedRowsCount;
	}

	// @AfterThrowing(pointcut = "execution(public * *(..))", throwing = "ex")
	// public void onThrowing(Exception ex) throws DaoException {
	// LOG.error("SQL Exception in hibernate news dao class");
	// throw new DaoException("SQL Exception in hibernate news dao class."
	// + ex);
	// }

}
