package com.epam.news.connectionpool.impl;

import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;


public class ConnectionWrapper {


    private static Logger log = Logger.getLogger(ConnectionWrapper.class);

    private Connection connection;

    ConnectionWrapper(Connection connection) {
        this.connection = connection;
    }
    
    public Statement createStatement()  throws SQLException {
    	return connection.createStatement();
    }


    /**
     * @param sql an SQL statement that may contain one or more '?' IN
     *            parameter placeholders
     * @return a new default <code>PreparedStatement</code> object containing the
     * pre-compiled SQL statement
     * @throws SQLException             if
     *                                  1. this method is called on a null connection
     *                                  2. result statement is null
     *                                  3. a database access error occurs
     *                                  4. this method is called on a closed connection
     * @throws IllegalArgumentException if sql statement is null or empty
     */

    public PreparedStatement prepareStatement(String sql) throws SQLException {
        if (sql == null || sql.isEmpty()) {
            throw new IllegalArgumentException("Parameter \"sql\" cannot be null or empty.");
        }
        if (connection != null) {
            PreparedStatement statement = connection.prepareStatement(sql);
            if (statement != null) {
                return statement;
            }
        }
        throw new SQLException("Connection or statement is null.");
    }


    public void closeStatement(PreparedStatement preparedStatement) {
        if (preparedStatement != null) {
            try {
            	preparedStatement.close();
            } catch (SQLException e) {
                log.error("Error during closing statement. " + e);
            }
        } else {
            log.error("Prepared statement for closing is null. ");
        }
    }
    
    public void closeStatement(Statement statement) {
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                log.error("Error during closing statement. " + e);
            }
        } else {
            log.error("Prepared statement for closing is null. ");
        }
    }


    // package-private - чтобы закрывать соединение мог только пул
    void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                log.error(" wrong connection" + e);
            }
        }
        else {
        	log.error("connenction object is null");
        }
        log.info("Connection closed");
    }


// другие необходимые делегированные методы интерфейса Connection


    public void setAutoCommit(boolean autoCommit)
            throws SQLException {
        if (connection != null) {
            connection.setAutoCommit(autoCommit);
        }
    }

    public void commit()
            throws SQLException {
        if (connection != null) {
            connection.commit();
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ConnectionWrapper)) return false;

        ConnectionWrapper that = (ConnectionWrapper) o;

        if (!connection.equals(that.connection)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return connection.hashCode();
    }
	
}
