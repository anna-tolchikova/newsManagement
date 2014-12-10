package com.epam.news.dao;

import java.util.ArrayList;
import java.util.List;

import com.epam.news.entity.News;
import com.epam.news.exception.DaoException;

public interface INewsDao{

	ArrayList<News> getList() throws DaoException;
	
	News save(News news) throws DaoException;
	
	boolean remove(Integer id) throws DaoException;
	
	News fetchById(Integer id) throws DaoException;
	
	void update(News news) throws DaoException;
	
	boolean deleteList(List<Integer> idList) throws DaoException;
	
}
