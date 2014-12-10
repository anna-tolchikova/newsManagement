package com.epam.news.dao.abstractdao;

import java.sql.PreparedStatement;
import java.sql.Statement;

import com.epam.news.connectionpool.impl.ConnectionPool;
import com.epam.news.connectionpool.impl.ConnectionWrapper;

public abstract class AbstractDao {
	private ConnectionPool connectionPool;

	public AbstractDao() {

	}

	public void setConnectionPool(ConnectionPool connectionPool) {
		this.connectionPool = connectionPool;
	}

	public ConnectionWrapper getConnection() {
		return this.connectionPool.getConnection();
	}

	public void closeTransaction(ConnectionWrapper connectionWrapper,
			PreparedStatement preparedStatement) {

		connectionWrapper.closeStatement(preparedStatement);
		this.connectionPool.releaseConnection(connectionWrapper);

	}
	
	public void closeTransaction(ConnectionWrapper connectionWrapper,
			Statement statement) {

		connectionWrapper.closeStatement(statement);
		this.connectionPool.releaseConnection(connectionWrapper);

	}

	public void releaseConnection(ConnectionWrapper connectionWrapper) {

		this.connectionPool.releaseConnection(connectionWrapper);

	}

}
