package com.epam.news.presentation.form;

import java.util.ArrayList;
import java.util.List;

import org.apache.struts.action.ActionForm;

import com.epam.news.entity.News;


public final class NewsForm extends ActionForm{

	private static final long serialVersionUID = 1L;

	private List<News> newsList = new ArrayList<News>();
	private Integer[] listNewsId;

	public NewsForm() {
		super();
	}

	public List<News> getNewsList() {
		return newsList;
	}

	public void setNewsList(List<News> newsList) {
		this.newsList = newsList;
	}

	public Integer[] getListNewsId() {
		return listNewsId;
	}

	public void setListNewsId(Integer[] listNewsId) {
		this.listNewsId = listNewsId;
	}

}
