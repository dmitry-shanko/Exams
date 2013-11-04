package by.epam.db.pool;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import by.epam.db.exception.NoConnectionException;
import by.epam.db.pool.exception.ConnectionSQLException;
import by.epam.resources.Resources;
import by.epam.xml.XMLEnums;
import by.epam.xml.XMLParser;
import by.epam.xml.exception.NoXMLReaderException;
import by.epam.xml.exception.NotValidXMLException;

import org.apache.log4j.Logger;
/**
 * Class for containing some PooledConnection to RDBMS
 * @author DmitryShanko
 *
 */
public class ConnectionPool
{
	private static final Resources res = Resources.getInstance();
	private static final Logger log = res.getLogger(by.epam.db.pool.ConnectionPool.class);
	private static final int DEFAULT_POOL_SIZE = 5;
	private static ConnectionPool instance;
	/** free connections queue */
	private BlockingQueue<PooledConnection> connectionQueue;
	private String driver = "";
	private String url = "";
	private String user = "";
	private String password = "";
	private int poolSize = DEFAULT_POOL_SIZE;
	private int count = 0;
	private static Lock mainLock = new ReentrantLock();
	private static Lock singletoneLock = new ReentrantLock();
	private Lock lock1 = new ReentrantLock();
	private Lock lock2 = new ReentrantLock();
	/**
	 * Private constructor to create proper ConnectionPool to RDBMS. Must take configuration properties from existing xml file using some XMLParser.
	 * @throws NoConnectionException
	 * @throws ConnectionSQLException
	 */
	private ConnectionPool() throws NoConnectionException, ConnectionSQLException
	{		
		try 
		{
			XMLParser parser = XMLParser.getSAMXMLReader();
			Map<XMLEnums, String> data = parser.parse();
			this.driver = data.get(XMLEnums.DBDRIVER);
			this.url = data.get(XMLEnums.DBURL);
			this.user = data.get(XMLEnums.DBUSER);
			this.password = data.get(XMLEnums.DBPASSWORD);
			try
			{
				this.poolSize = Integer.parseInt(data.get(XMLEnums.DBPOOLSIZE));
			}
			catch (NumberFormatException e)
			{
				log.error("Error in initialization file", e);
			}
		} 
		catch (NoXMLReaderException e) 
		{
			log.error("Error in initialization file", e);
			defaultInitialization();
		} 
		catch (NotValidXMLException e) 
		{
			log.error("Error in initialization file", e);
			defaultInitialization();
		}
		try 
		{
			init(poolSize);
		} 
		catch (ConnectionSQLException e) 
		{
			throw new ConnectionSQLException("ConnectionPool can't be initialized", e);
		}
	}
	/**
	 * Method to get singleton instance of this class
	 * @return Reference to the singleton object of this class
	 * @throws ConnectionSQLException 
	 * @throws NoConnectionException 
	 */
	public static ConnectionPool getInstance() throws NoConnectionException, ConnectionSQLException
	{
		singletoneLock.lock();
		if (instance == null)
		{
			instance = new ConnectionPool();
		}
		singletoneLock.unlock();
		return instance;
	}
	/**
	 * Clears this ConnectionPool.
	 * @throws ConnectionSQLException
	 */
	public void dispose() throws ConnectionSQLException 
	{
		try
		{
			this.clearConnectionQueue();
			log.info("ConnectionPool was disposed.");
		}
		catch (ConnectionSQLException e) 
		{
			throw e;
		}			
	}
	/**
	 * Uses lock2. Takes some connection from the connectionQueue.<p>
	 * Calls init() method to re-initialize ConnectionPool if taken connection isClosed() or all released connections were closed.
	 * @return PooledConnection from existing ConnectionPool
	 * @throws NoConnectionException
	 * @throws ConnectionSQLException
	 */
	public Connection takeConnection () throws NoConnectionException, ConnectionSQLException 
	{
		lock2.lock();
		Connection connection = null;
		try 
		{
			if (count == poolSize)
			{
				init(0);
			}
			connection = connectionQueue.take();
			if (connection != null && connection.isClosed())
			{
				init(connectionQueue.size() + 1);
				connection = this.takeConnection();
			}
			return connection;
		} 
		catch (InterruptedException e) 
		{
			throw new NoConnectionException("InterruptedException in ConnectionPool", e);
		} 
		catch (ConnectionSQLException e) 
		{
			throw new ConnectionSQLException("SQLException in connection.isClosed()", e);
		} 
		catch (SQLException e) 
		{
			throw new ConnectionSQLException("SQLException in connection.isClosed()", e);
		}		
		finally
		{
			lock2.unlock();
		}
	}
	/**
	 * Uses lock1. Releases connection back to this ConnectionPool.<p>
	 * If got connection isClosed(), increments private count++. It will be used in re-initialization method.
	 * @param connection Connection to release.
	 * @throws ConnectionSQLException
	 */
	public void releaseConnection (PooledConnection connection) throws ConnectionSQLException
	{
		try 
		{
			lock1.lock();
			if (connection != null && !connection.isClosed ()) 
			{
				if (!connectionQueue.offer (connection)) 
				{
					lock1.unlock();
					log.error("Connection was not released. Error in syhchronization");
					/*"Connection not added. Possible `leakage` of
					 connections"*/
				}
				else
				{
					lock1.unlock();
				}
			} 
			else 
			{
				if (connection.isClosed())
				{
					count++;
				}
				lock1.unlock();
				log.error("Connection has been alreay closed. Error in syhchronization");
				//"Trying to release closed connection. Possible
				// `leakage` of connections"
			}
		} 
		catch (SQLException e) 
		{
			throw new ConnectionSQLException("SQLException in connection.isClosed()", e);
		}
	}	
	/**
	 * Initializes connectionQueue with proper PooledConnections.<p>
	 * Uses some "magic" count "private int count". This count represents the number of released connections that were closed.<p>
	 * It helps to re-initialize ConnectionPool with the number of Connections that was taken from properties file.<p>
	 * Uses mainLock to provide proper synchronization path.
	 * @param size Size of connectionQueue before calling this method, or number of connections to create.
	 * @throws NoConnectionException
	 * @throws ConnectionSQLException
	 */
	private void init(int size) throws NoConnectionException, ConnectionSQLException
	{
		mainLock.lock();
		if (connectionQueue != null)
		{
			for (PooledConnection pc : connectionQueue)
			{
				Connection con = pc.getConnection();
				try
				{
					if (con != null && !con.isClosed())
					{
						con.close();
					}
				}
				catch (SQLException e)
				{
					throw new ConnectionSQLException("SQLException in connection.close()", e);
				}
			}
			connectionQueue = null;
		}
		try 
		{
			Class.forName (driver);
			connectionQueue = new ArrayBlockingQueue<PooledConnection>(poolSize);
			int prevSize = size;
			size += count;
			// This operation was made for synchronization. Because I didn't want to write many locks, so, if count will be changed HERE, it's number will be saved and
			// will be used in another re-initialize calling.
			count -= size - prevSize;
			for (int i = 0; i < size; i++) 
			{
				Connection connection = DriverManager.getConnection(url, user, password);
				try 
				{
					connectionQueue.offer (new PooledConnection(connection));
				} 
				catch (NoConnectionException e) 
				{
					log.error("Error in creating connection. Driver returned null connection", e);
					throw new NoConnectionException("Error in creating connection. Driver returned null connection", e);
				}
			}
			log.info("Connection pool has been initialized successfully.");
			log.info("Driver=".concat(driver));
			log.info("URL=".concat(url));
			log.info("User=".concat(user));
			log.info("PoolSize=" + poolSize);
			log.info("CurrentSize=" + size);
		} 
		catch (ClassNotFoundException | SQLException e) 
		{
			throw new NoConnectionException("Can't initialize ConnectionPool", e);
		}
		finally
		{
			mainLock.unlock();
		}
	}
	/**
	 * Some default initialization for driver, url, user and password fields.
	 */
	private void defaultInitialization()
	{
		this.driver = "";
		this.url = "";
		this.user = "";
		this.password = "";
	}
	/**
	 * Clears connectionQueue with closing all not released conenctions.
	 * @throws ConnectionSQLException
	 */
	private void clearConnectionQueue () throws ConnectionSQLException
	{
		PooledConnection connection;
		while ((connection = connectionQueue.poll()) != null) 
		{
			try
			{
				if (!connection.getAutoCommit ()) 
				{
					connection.commit ();
					Connection con = connection.getConnection();
					if (con != null && !con.isClosed())
					{
						con.close();	
					}
				}
			}
			catch (SQLException e)
			{
				throw new ConnectionSQLException("SQLException in connection.close()", e);
			}
		}
	}

	@Override
	protected void finalize() throws Throwable 
	{
		super.finalize();
		this.clearConnectionQueue();
		connectionQueue = null;
	}
} 