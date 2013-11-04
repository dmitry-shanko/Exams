package by.epam.db.pool;

import java.sql.Array;
import java.sql.Blob;
import java.sql.CallableStatement;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.NClob;
import java.sql.PreparedStatement;
import java.sql.SQLClientInfoException;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.SQLXML;
import java.sql.Savepoint;
import java.sql.Statement;
import java.sql.Struct;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.Executor;

import by.epam.db.exception.NoConnectionException;
/**
 * Class that is used as composition for any Connection.
 * Overrides method close(). PooledConnection can be closed only by using such code fragment:<p>
 * Connection con = somePooledConnection.getConnection();<p>
 * con.close();
 * @author user
 *
 */
public class PooledConnection implements Connection
{
	private Connection con;
	/**
	 * Creates a cover for Connection.
	 * @param con Connection to be put to this composition.
	 * @throws NoConnectionException
	 */
	public PooledConnection(Connection con) throws NoConnectionException
	{
		if (null == con)
		{
			throw new NoConnectionException();
		}
		else
		{
			this.con = con;
		}
	}
	/**
	 * Getter for contained Connection.
	 * @return Connection that is referenced to this PooledConnection
	 */
	public Connection getConnection()
	{
		return con;
	}

	@Override
	public boolean isWrapperFor(Class<?> iface) throws SQLException 
	{
		return con.isWrapperFor(iface);
	}

	@Override
	public <T> T unwrap(Class<T> iface) throws SQLException 
	{
		return con.unwrap(iface);
	}

	@Override
	public void abort(Executor executor) throws SQLException 
	{
		con.abort(executor);
	}

	@Override
	public void clearWarnings() throws SQLException 
	{
		con.clearWarnings();		
	}
	/**
	 * Empty method. Defends PooledConnection from closing it.
	 */
	@Override
	public void close() throws SQLException 
	{

	}

	@Override
	public void commit() throws SQLException 
	{
		con.commit();
	}

	@Override
	public Array createArrayOf(String typeName, Object[] elements) throws SQLException 
	{
		return con.createArrayOf(typeName, elements);
	}

	@Override
	public Blob createBlob() throws SQLException 
	{
		return con.createBlob();
	}

	@Override
	public Clob createClob() throws SQLException 
	{
		return con.createClob();
	}

	@Override
	public NClob createNClob() throws SQLException 
	{
		return con.createNClob();
	}

	@Override
	public SQLXML createSQLXML() throws SQLException 
	{
		return con.createSQLXML();
	}

	@Override
	public Statement createStatement() throws SQLException 
	{
		return con.createStatement();
	}

	@Override
	public Statement createStatement(int resultSetType, int resultSetConcurrency) throws SQLException 
	{
		return con.createStatement(resultSetType, resultSetConcurrency);
	}

	@Override
	public Statement createStatement(int resultSetType,	int resultSetConcurrency, int resultSetHoldability) throws SQLException 
	{
		return con.createStatement(resultSetType, resultSetConcurrency, resultSetHoldability);
	}

	@Override
	public Struct createStruct(String typeName, Object[] attributes) throws SQLException 
	{
		return con.createStruct(typeName, attributes);
	}

	@Override
	public boolean getAutoCommit() throws SQLException 
	{
		return con.getAutoCommit();
	}

	@Override
	public String getCatalog() throws SQLException 
	{
		return con.getCatalog();
	}

	@Override
	public Properties getClientInfo() throws SQLException 
	{
		return con.getClientInfo();
	}

	@Override
	public String getClientInfo(String name) throws SQLException 
	{
		return con.getClientInfo(name);
	}

	@Override
	public int getHoldability() throws SQLException 
	{
		return con.getHoldability();
	}

	@Override
	public DatabaseMetaData getMetaData() throws SQLException 
	{
		return con.getMetaData();
	}

	@Override
	public int getNetworkTimeout() throws SQLException 
	{
		return con.getNetworkTimeout();
	}

	@Override
	public String getSchema() throws SQLException 
	{
		return con.getSchema();
	}

	@Override
	public int getTransactionIsolation() throws SQLException 
	{
		return con.getTransactionIsolation();
	}

	@Override
	public Map<String, Class<?>> getTypeMap() throws SQLException 
	{
		return con.getTypeMap();
	}

	@Override
	public SQLWarning getWarnings() throws SQLException 
	{
		return con.getWarnings();
	}

	@Override
	public boolean isClosed() throws SQLException 
	{
		return con.isClosed();
	}

	@Override
	public boolean isReadOnly() throws SQLException 
	{
		return con.isReadOnly();
	}

	@Override
	public boolean isValid(int timeout) throws SQLException 
	{
		return con.isValid(timeout);
	}

	@Override
	public String nativeSQL(String sql) throws SQLException 
	{
		return con.nativeSQL(sql);
	}

	@Override
	public CallableStatement prepareCall(String sql) throws SQLException 
	{
		return con.prepareCall(sql);
	}

	@Override
	public CallableStatement prepareCall(String sql, int resultSetType,	int resultSetConcurrency) throws SQLException 
	{
		return con.prepareCall(sql, resultSetType, resultSetConcurrency);
	}

	@Override
	public CallableStatement prepareCall(String sql, int resultSetType,	int resultSetConcurrency, int resultSetHoldability) throws SQLException 
	{
		return con.prepareCall(sql, resultSetType, resultSetConcurrency, resultSetHoldability);
	}

	@Override
	public PreparedStatement prepareStatement(String sql) throws SQLException 
	{
		return con.prepareStatement(sql);
	}

	@Override
	public PreparedStatement prepareStatement(String sql, int autoGeneratedKeys) throws SQLException 
	{
		return con.prepareStatement(sql, autoGeneratedKeys);
	}

	@Override
	public PreparedStatement prepareStatement(String sql, int[] columnIndexes) throws SQLException 
	{
		return con.prepareStatement(sql, columnIndexes);
	}

	@Override
	public PreparedStatement prepareStatement(String sql, String[] columnNames)throws SQLException 
	{
		return con.prepareStatement(sql, columnNames);
	} 

	@Override
	public PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency) throws SQLException 
	{
		return con.prepareStatement(sql, resultSetType, resultSetConcurrency);
	}

	@Override
	public PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency, int resultSetHoldability) throws SQLException 
	{
		return con.prepareStatement(sql, resultSetType, resultSetConcurrency, resultSetHoldability);
	}

	@Override
	public void releaseSavepoint(Savepoint savepoint) throws SQLException 
	{
		con.releaseSavepoint(savepoint);
	}

	@Override
	public void rollback() throws SQLException 
	{
		con.rollback();
	}

	@Override
	public void rollback(Savepoint savepoint) throws SQLException 
	{
		con.rollback(savepoint);
	}

	@Override
	public void setAutoCommit(boolean autoCommit) throws SQLException 
	{
		con.setAutoCommit(autoCommit);
	}

	@Override
	public void setCatalog(String catalog) throws SQLException 
	{
		con.setCatalog(catalog);
	}

	@Override
	public void setClientInfo(Properties properties) throws SQLClientInfoException 
	{
		con.setClientInfo(properties);
	}

	@Override
	public void setClientInfo(String name, String value) throws SQLClientInfoException 
	{
		con.setClientInfo(name, value);
	}

	@Override
	public void setHoldability(int holdability) throws SQLException 
	{
		con.setHoldability(holdability);
	}

	@Override
	public void setNetworkTimeout(Executor executor, int milliseconds) throws SQLException 
	{
		con.setNetworkTimeout(executor, milliseconds);
	}

	@Override
	public void setReadOnly(boolean readOnly) throws SQLException 
	{
		con.setReadOnly(readOnly);
	}

	@Override
	public Savepoint setSavepoint() throws SQLException 
	{
		return con.setSavepoint();
	}

	@Override
	public Savepoint setSavepoint(String name) throws SQLException 
	{
		return con.setSavepoint(name);
	}

	@Override
	public void setSchema(String schema) throws SQLException 
	{
		con.setSchema(schema);
	}

	@Override
	public void setTransactionIsolation(int level) throws SQLException 
	{
		con.setTransactionIsolation(level);
	}

	@Override
	public void setTypeMap(Map<String, Class<?>> map) throws SQLException 
	{
		con.setTypeMap(map);
	}
}
