package com.epam.news.dao;

import java.util.ArrayList;
import java.util.List;

import com.epam.news.entity.News;
import com.epam.news.exception.DaoException;

public interface INewsDao{

	List<News> getList() throws DaoException;
	
	News save(News news) throws DaoException;
	
	int remove(Integer id) throws DaoException;
	
	News fetchById(Integer id) throws DaoException;
	
	void update(News news) throws DaoException;
	
	int deleteList(List<Integer> idList) throws DaoException;
	
}
