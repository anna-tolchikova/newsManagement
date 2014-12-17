package com.epam.news.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.epam.news.dao.INewsDao;
import com.epam.news.entity.News;
import com.epam.news.exception.DaoException;

@Repository
@Transactional(propagation = Propagation.REQUIRED)
public class JpaNewsDaoImpl implements INewsDao {

	private static final String JPQL_SELECT_ALL_NEWS = "SELECT n FROM News n ORDER BY n.postDate DESC, n.id DESC";
	private final static String JPQL_DELETE_BY_ID = "DELETE FROM News n "
			+ "WHERE n.id = :news_id";
	private final static String JPQL_DELETE_BY_ID_LIST = "DELETE FROM News n "
			+ "WHERE n.id IN :news_id_list";

	private EntityManager entityManager;

	public EntityManager getEntityManager() {
		return entityManager;
	}

	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	@Override
	public List<News> getList() throws DaoException {
		Query query = entityManager.createQuery(JPQL_SELECT_ALL_NEWS);
		List<News> news = (List<News>) query.getResultList();
		return news;
	}

	@Override
	public News save(News news) throws DaoException {
		entityManager.persist(news);
		return news;
	}

	@Override
	public int remove(Integer id) throws DaoException {
		Query query = entityManager.createQuery(JPQL_DELETE_BY_ID);
		int deletedCount = query.setParameter("news_id", id).executeUpdate();
		return deletedCount;
	}

	@Override
	public News fetchById(Integer id) throws DaoException {
		News news  = entityManager.find(News.class, id);
		return news;
	}

	@Override
	public void update(News news) throws DaoException {
		entityManager.merge(news);

	}

	@Override
	public int deleteList(List<Integer> idList) throws DaoException {
		Query query = entityManager.createQuery(JPQL_DELETE_BY_ID_LIST);
		int deletedCount = query.setParameter("news_id_list", idList).executeUpdate();
		return deletedCount;
	}

}
