package com.epam.news.connectionpool.impl;

import com.epam.news.connectionpool.IConnectionPool;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.util.Date;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Properties;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public final class ConnectionPool implements IConnectionPool {

	private static Logger log = Logger.getLogger(ConnectionPool.class);

	private final Lock lock = new ReentrantLock();
	private final Condition notEmpty = lock.newCondition();

	private int maxPoolSize;
	private int maxClientWaitPeriodMsec;
	private int maxIdleTimeoutMsec; // 3hours
	private int firstDelayPeriodMsec; // 3hours
	private int repeatTaskDelayPeriodMsec; // 3hours
	private String url;
	private String user;
	private String password;
	private String driver;
	private String useUnicode;
	private String encoding;

	private ArrayList<ConnectionWrapper> connections;
	private HashMap<ConnectionWrapper, Long> connectionsLastUsingMap;
	private AtomicInteger currentPoolSize;
	private Timer timer;

	private ConnectionPool() {
	}

	public void init() {

		connections = new ArrayList<ConnectionWrapper>();
		connectionsLastUsingMap = new HashMap<ConnectionWrapper, Long>();
		currentPoolSize = new AtomicInteger();

		for (int i = 0; i < maxPoolSize; ++i) {
			ConnectionWrapper wc = null;
			try {
				Class.forName(driver);
				wc = createConnectionWrapper();
			} catch (ClassNotFoundException e) {
				throw new RuntimeException("DRIVER NOT FOUND" + e);
			} catch (SQLException e) {
				throw new RuntimeException(
						"Error creating ConnectionPool instance." + e);
			}
			
			connections.add(wc);
			connectionsLastUsingMap.put(wc, new Date().getTime()); // при
																	// первичном
																	// создании
																	// время
																	// последнего
																	// использования
																	// инициализируется
																	// временем
																	// создания
			currentPoolSize.incrementAndGet(); // get не нужно, но нужен
												// инкремент
			log.info(i + 1 + ") connection created \n\t poolSize = "
					+ currentPoolSize);
		}

		timer = new Timer();
		timer.scheduleAtFixedRate(new TimerTask() {

			@Override
			public void run() {
				synchronized (connections) {
					log.info("Old connections cleaning started.");
					for (int i = 0; i < connections.size(); i++) {
						ConnectionWrapper wc = connections.get(i);
						Long lastUsing = connectionsLastUsingMap.get(wc);
						log.info(i + 1 + ")connection check \n\t last used "
								+ ((new Date().getTime()) - lastUsing));
						if (lastUsing != null
								&& ((new Date().getTime()) - lastUsing) >= maxIdleTimeoutMsec) {
							connectionsLastUsingMap.remove(wc);
							connections.remove(i);
							wc.closeConnection();
							currentPoolSize.decrementAndGet(); // get не нужно,
																// но нужен
																// декремент
							log.info("...is deleted \n" + "\t poolSize = "
									+ currentPoolSize);
						} else {
							log.info("...is alive \n" + "\t poolSize = "
									+ currentPoolSize);
						}
					}
				}
			}

		}, firstDelayPeriodMsec, repeatTaskDelayPeriodMsec);
	}

	public int getMaxPoolSize() {
		return maxPoolSize;
	}

	public void setMaxPoolSize(int maxPoolSize) {
		this.maxPoolSize = maxPoolSize;
	}

	public int getMaxClientWaitPeriodMsec() {
		return maxClientWaitPeriodMsec;
	}

	public void setMaxClientWaitPeriodMsec(int maxClientWaitPeriodMsec) {
		this.maxClientWaitPeriodMsec = maxClientWaitPeriodMsec;
	}

	public int getMaxIdleTimeoutMsec() {
		return maxIdleTimeoutMsec;
	}

	public void setMaxIdleTimeoutMsec(int maxIdleTimeoutMsec) {
		this.maxIdleTimeoutMsec = maxIdleTimeoutMsec;
	}

	public int getFirstDelayPeriodMsec() {
		return firstDelayPeriodMsec;
	}

	public void setFirstDelayPeriodMsec(int firstDelayPeriodMsec) {
		this.firstDelayPeriodMsec = firstDelayPeriodMsec;
	}

	public int getRepeatTaskDelayPeriodMsec() {
		return repeatTaskDelayPeriodMsec;
	}

	public void setRepeatTaskDelayPeriodMsec(int repeatTaskDelayPeriodMsec) {
		this.repeatTaskDelayPeriodMsec = repeatTaskDelayPeriodMsec;
	}

	public String getDriver() {
		return driver;
	}

	public void setDriver(String driver) {
		this.driver = driver;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUseUnicode() {
		return useUnicode;
	}

	public void setUseUnicode(String useUnicode) {
		this.useUnicode = useUnicode;
	}

	public String getEncoding() {
		return encoding;
	}

	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}

	public Condition getNotEmpty() {
		return notEmpty;
	}
	
	
    private static class SingletonLazyLoader {
        private static ConnectionPool INSTANCE = new ConnectionPool();
    }

    /**
     *
     * @return ConnectionPool single for all clients object
     */
    public static ConnectionPool getInstance() {
        return SingletonLazyLoader.INSTANCE;
    }
	

	private ConnectionWrapper createConnectionWrapper() throws SQLException {

		Connection connection = null;

		Properties prop = new Properties();
		prop.put("url", url);
		prop.put("user", user);
		prop.put("password", password);
		prop.put("useUnicode", useUnicode);
		prop.put("encoding", encoding);

		connection = DriverManager.getConnection(url, prop);
		return new ConnectionWrapper(connection);

	}

	/**
	 * 
	 * @return ConnectionWrapper object - for AbstractDao object. null - in case
	 *         1. timeout waiting for ConnectionPool object or connections list
	 *         locking 2. errors by creating new connection 3. errors by locking
	 *         <code>ConnectionPool</code> instance
	 * 
	 */
	public ConnectionWrapper getConnection() { // return null by timeout
		ConnectionWrapper wc = null;

		try {
			if (lock.tryLock(maxClientWaitPeriodMsec / 2, TimeUnit.MILLISECONDS) == false) {
				log.error("Timeout waiting for pool access.");
			} else {
				try {
					if (connections.size() == 0) { // all alife connections are
													// used

						if (currentPoolSize.get() < maxPoolSize) { // pool is
																	// not full
							wc = createConnectionWrapper();
							currentPoolSize.incrementAndGet();
							connectionsLastUsingMap.put(wc,
									new Date().getTime());
							log.info("New connection was created by getConnection method.");
						} else {

							if (notEmpty.await(maxClientWaitPeriodMsec / 2,
									TimeUnit.MILLISECONDS) == true) {
								wc = connections.remove(0);
								log.info("All connections were used. connection was released and given before timeout waiting");
							} else {
								log.error("All connections are used. timeout waiting for release"); // error
																									// -
																									// empty
																									// pool
							}
						}
					} else {
						wc = connections.remove(0);
						log.info("Connection was given.");
					}
				} catch (SQLException e) {
					log.error("Error creating connection while getting connection."
							+ e);
				} finally {
					lock.unlock();
				}
			}
		} catch (InterruptedException e) {
			log.error(
					"Error locking ConnectionPool instance while getting connection.",
					e);
		}
		return wc;
	}

	/**
	 * 
	 * @param wc
	 *            ConnectionWrapper object for return connection in connection
	 *            pool
	 */
	public void releaseConnection(ConnectionWrapper wc) {
		lock.lock();
		try {
			connections.add(wc);
			connectionsLastUsingMap.put(wc, new Date().getTime());
			log.info("Connection was released");
			notEmpty.signal();
		} finally {
			lock.unlock();
		}
	}

	public void destroy() {
		timer.cancel();
		for (ConnectionWrapper connection : connections) {
			connection.closeConnection();
		}
		log.info("Connection pool was successfully destroyed!");
	}
}
