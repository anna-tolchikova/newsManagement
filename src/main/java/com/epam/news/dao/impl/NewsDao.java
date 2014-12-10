package com.epam.news.dao.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

//import org.apache.log4j.Logger;


import com.epam.news.connectionpool.impl.ConnectionWrapper;
import com.epam.news.dao.INewsDao;
import com.epam.news.dao.abstractdao.AbstractDao;
import com.epam.news.entity.News;
import com.epam.news.exception.DaoException;

public final class NewsDao extends AbstractDao implements INewsDao {

	// private static final Logger LOG = Logger.getLogger(NewsDao.class);

	private static final String PROPERTY_ID = "ID";
	private static final String PROPERTY_TITLE = "TITLE";
	private static final String PROPERTY_BRIEF = "BRIEF";
	private static final String PROPERTY_DATE = "POST_DATE";
	private static final String PROPERTY_CONTENT = "CONTENT";

	private static final String SELECT_ALL_NEWS = "SELECT * FROM NEWS ORDER BY POST_DATE DESC, ID DESC";
	private static final String SELECT_NEWS_BY_ID = "SELECT * FROM NEWS WHERE ID =";
	private static final String UPDATE_NEWS = "UPDATE NEWS SET TITLE=?, BRIEF=?, POST_DATE=?, CONTENT=? where ID=?";
	private static final String CREATE_NEWS = "INSERT INTO NEWS "
			+ "(TITLE, BRIEF, POST_DATE, CONTENT ) VALUES ( ?, ?, ?, ?)";
	private static final String SELECT_CURRENT_NEWS_ID = "SELECT NEWS_SEQ.CURRVAL FROM dual";
	private static final String DELETE_NEWS = "DELETE FROM NEWS WHERE ID=";
	private static final String DELETE_NEWS_LIST = "DELETE FROM NEWS WHERE ID IN (?)";

	public ArrayList<News> getList() throws DaoException {
		ConnectionWrapper connector = getConnection();
		ArrayList<News> listNews = new ArrayList<News>();
		News news = null;
		Statement statement = null;
		ResultSet resultSet = null;
		try {
			statement = connector.createStatement();
			resultSet = statement.executeQuery(SELECT_ALL_NEWS);
			while (resultSet.next()) {
				news = new News();
				news.setId(resultSet.getInt(PROPERTY_ID));
				news.setTitle(resultSet.getString(PROPERTY_TITLE));
				news.setContent(resultSet.getString(PROPERTY_CONTENT));
				news.setPostDate(resultSet.getDate(PROPERTY_DATE));
				news.setBrief(resultSet.getString(PROPERTY_BRIEF));
				listNews.add(news);
			}
		} catch (SQLException e) {
			throw new DaoException("Error during getting list of news:", e);
		} finally {
			closeTransaction(connector, statement);
		}
		return listNews;
	}

	public News save(News news) throws DaoException {
		ConnectionWrapper connector = getConnection();
		PreparedStatement preparedStatement = null;
		Statement currvalStatement = null;
		ResultSet currvalResultSet = null;
		try {

			connector.setAutoCommit(false);
			preparedStatement = connector.prepareStatement(CREATE_NEWS);
			preparedStatement.setString(1, news.getTitle());
			preparedStatement.setString(2, news.getBrief());
			preparedStatement.setDate(3, news.getPostDate());
			preparedStatement.setString(4, news.getContent());
			preparedStatement.executeUpdate();
			currvalStatement = connector.createStatement();
			currvalResultSet = currvalStatement.executeQuery(SELECT_CURRENT_NEWS_ID);
			int id = 0;
			if (currvalResultSet.next()) {
				id = currvalResultSet.getInt(1);
			}
			connector.commit();
			connector.setAutoCommit(true);
			news.setId(id);

		} catch (SQLException e) {
			throw new DaoException("Error during saving new news:", e);
		} finally {
			try {
				currvalStatement.close();
			} catch (SQLException e) {
				throw new DaoException("Error during saving new news:", e);
			}
			closeTransaction(connector, preparedStatement);
		}
		return news;
	}

	public boolean remove(Integer id) throws DaoException {
		ConnectionWrapper connector = getConnection();
		Statement statement = null;
		boolean isDeleted = false;
		try {
			statement = connector.createStatement();
			String sql = DELETE_NEWS + id;
			if (statement.executeUpdate(sql) != 0) {
				isDeleted = true;
			}
		} catch (SQLException e) {
			throw new DaoException("Error during removing news:", e);
		} finally {
			closeTransaction(connector, statement);
		}

		return isDeleted;
	}

	public News fetchById(Integer id) throws DaoException {
		ConnectionWrapper connector = getConnection();
		News news = null;
		Statement statement = null;
		ResultSet resultSet = null;
		try {
			statement = connector.createStatement();
			String sql = SELECT_NEWS_BY_ID + id;
			resultSet = statement.executeQuery(sql);
			if (resultSet.next()) {
				news = new News();
				news.setId(resultSet.getInt(PROPERTY_ID));
				news.setTitle(resultSet.getString(PROPERTY_TITLE));
				news.setContent(resultSet.getString(PROPERTY_CONTENT));
				news.setPostDate(resultSet.getDate(PROPERTY_DATE));
				news.setBrief(resultSet.getString(PROPERTY_BRIEF));
			}
		} catch (SQLException e) {
			throw new DaoException("Error during fetching news by id:", e);
		} finally {
			closeTransaction(connector, statement);
		}
		return news;
	}

	public void update(News news) throws DaoException {
		ConnectionWrapper connector = getConnection();
		PreparedStatement preparedStatement = null;
		try {
			preparedStatement = connector.prepareStatement(UPDATE_NEWS);
			preparedStatement.setString(1, news.getTitle());
			preparedStatement.setString(2, news.getBrief());
			preparedStatement.setDate(3, news.getPostDate());
			preparedStatement.setString(4, news.getContent());
			preparedStatement.setInt(5, news.getId());
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			throw new DaoException("Error during updating news:", e);
		} finally {
			closeTransaction(connector, preparedStatement);
		}
	}

	public boolean deleteList(List<Integer> idList) throws DaoException {
		ConnectionWrapper connector = getConnection();
		PreparedStatement preparedStatement = null;
		boolean isListDeleted = false;
		int listSize;
		listSize = idList.size();
		try {
			preparedStatement = connector.prepareStatement(prepareSql(listSize));
			int pos = 0;
			for (int id : idList) {
				preparedStatement.setInt(++pos, id);
			}
			if (preparedStatement.executeUpdate() == listSize) {
				isListDeleted = true;
			}
		} catch (SQLException e) {
			throw new DaoException("Error during removing news list:", e);
		} finally {
			closeTransaction(connector, preparedStatement);
		}
		return isListDeleted;
	}

	private String prepareSql(int batchSize) {
		StringBuilder sql = new StringBuilder();
		for (int i = 0; i < batchSize - 1 ; i++) {
			sql.append("?,");
		}
		sql.append('?');
		return DELETE_NEWS_LIST.replace("?", sql);
	}

}
