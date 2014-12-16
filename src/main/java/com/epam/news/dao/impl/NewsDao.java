package com.epam.news.dao.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

//import org.apache.log4j.Logger;





import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

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
			+ "(TITLE, BRIEF, POST_DATE, CONTENT, ID ) VALUES ( ?, ?, ?, ?, ?)";
	private static final String SELECT_NEXT_NEWS_ID = "SELECT NEWS_SEQ.NEXTVAL FROM dual";
	private static final String DELETE_NEWS = "DELETE FROM NEWS WHERE ID=?";
//	private static final String DELETE_NEWS_LIST = "DELETE FROM NEWS WHERE ID IN (?)";

	public List<News> getList() throws DaoException {
		ConnectionWrapper connector = getConnection();
		List<News> listNews = new ArrayList<News>();
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
		} catch (Exception e) {
			throw new DaoException("Error during getting list of news:", e);
		} finally {
			closeTransaction(connector, statement);
		}
		return listNews;
	}

	public News save(News news) throws DaoException {
		ConnectionWrapper connector = getConnection();
		PreparedStatement preparedStatement = null;
		Statement nextvalStatement = null;
		ResultSet nextvalResultSet = null;
		try {

			connector.setAutoCommit(false);
			nextvalStatement = connector.createStatement();
			nextvalResultSet = nextvalStatement.executeQuery(SELECT_NEXT_NEWS_ID);
			int id = 0;
			if (nextvalResultSet.next()) {
				id = nextvalResultSet.getInt(1);
			}
			preparedStatement = connector.prepareStatement(CREATE_NEWS);
			preparedStatement.setString(1, news.getTitle());
			preparedStatement.setString(2, news.getBrief());
			preparedStatement.setDate(3, news.getPostDate());
			preparedStatement.setString(4, news.getContent());
			preparedStatement.setInt(5, id);
			preparedStatement.executeUpdate();
			connector.commit();
			connector.setAutoCommit(true);
			news.setId(id);

		} catch (Exception e) {
			throw new DaoException("Error during saving new news:", e);
		} finally {
			try {
				nextvalStatement.close();
			} catch (Exception e) {
				throw new DaoException("Error during saving new news:", e);
			}
			closeTransaction(connector, preparedStatement);
		}
		return news;
	}

	public int remove(Integer id) throws DaoException {
		ConnectionWrapper connector = getConnection();
		PreparedStatement preparedStatement = null;
		int affectedRowsCount = 0;
		try {
			preparedStatement = connector.prepareStatement(DELETE_NEWS);
			preparedStatement.setInt(1, id);
			affectedRowsCount = preparedStatement.executeUpdate();
			if (affectedRowsCount > 1) {
				throw new Exception ("Error. More than 1 news was deleted. Check your SQL statement.");
			}
		} catch (Exception e) {
			throw new DaoException("Error during removing news:", e);
		} finally {
			closeTransaction(connector, preparedStatement);
		}

		return affectedRowsCount;
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
		} catch (Exception e) {
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
		} catch (Exception e) {
			throw new DaoException("Error during updating news:", e);
		} finally {
			closeTransaction(connector, preparedStatement);
		}
	}

	public int deleteList(List<Integer> idList) throws DaoException {
		ConnectionWrapper connector = getConnection();
		PreparedStatement preparedStatement = null;
		int affectedRowsCount = 0;
		try {
			preparedStatement = connector.prepareStatement(DELETE_NEWS);
			for (int id : idList) {
				preparedStatement.setInt(1, id);
				affectedRowsCount += preparedStatement.executeUpdate();
			}
			
		} catch (Exception e) {
			throw new DaoException("Error during removing news list:", e);
		} finally {
			closeTransaction(connector, preparedStatement);
		}
		return affectedRowsCount;
	}

}
