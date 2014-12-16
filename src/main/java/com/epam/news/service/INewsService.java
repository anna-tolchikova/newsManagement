package com.epam.news.service;

import java.util.List;

import com.epam.news.entity.News;
import com.epam.news.exception.ServiceException;

public interface INewsService {

	List<News> getAllNews() throws ServiceException;

	News saveNews(News news) throws ServiceException;

	void deleteNewsById(Integer id) throws ServiceException;

	News findNewsById(Integer id) throws ServiceException;
	
	News findNewsByIdForGetAction(Integer id) throws ServiceException;

	void updateNews(News news) throws ServiceException;

	void deleteNewsList(List<Integer> idList) throws ServiceException;

}
