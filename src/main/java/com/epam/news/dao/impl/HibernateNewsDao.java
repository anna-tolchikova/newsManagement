package com.epam.news.dao.impl;

import java.util.ArrayList;
import java.util.List;

import com.epam.news.dao.INewsDao;
import com.epam.news.entity.News;
import com.epam.news.exception.DaoException;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class HibernateNewsDao implements INewsDao{

	private SessionFactory sessionFactory;

//	public HibernateNewsDAO() {
//	}

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	@Override
	public ArrayList<News> getList() throws DaoException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public News save(News news) throws DaoException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean remove(Integer id) throws DaoException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public News fetchById(Integer id) throws DaoException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void update(News news) throws DaoException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean deleteList(List<Integer> idList) throws DaoException {
		// TODO Auto-generated method stub
		return false;
	}

}
