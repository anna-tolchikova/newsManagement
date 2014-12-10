package com.epam.news.connectionpool;


import com.epam.news.connectionpool.impl.ConnectionPool;
import com.epam.news.connectionpool.impl.ConnectionWrapper;


public interface IConnectionPool {

	void init();

	void destroy();
	
	ConnectionWrapper getConnection();

	void releaseConnection(ConnectionWrapper connection);
}
