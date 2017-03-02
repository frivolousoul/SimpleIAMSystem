
package pers.zxlin.iam.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Manage the database-related operation, including connect and executing SQL sentence
 * 
 * @author BoJack
 */
public class DbManager {
	private static final Logger logger = Logger.getLogger(DbManager.class.getName());
	Connection connection;
	ResultSet resultSet;
	ResultSetMetaData metaData;

	/**
	 * Constructor of the database manager
	 * 
	 * @param url
	 *            database connection url
	 * @param driverName
	 *            JDBC driver name of database used
	 * @param user
	 *            login of the database connection
	 * @param pwd
	 *            password of the database connection
	 */
	public DbManager(String url, String driverName, String user, String pwd) {
		// connect to the specified database once instantiated
		connect(url, driverName, user, pwd);
	}

	/**
	 * Get the column names of the specified table in the database
	 * 
	 * @param tableName
	 *            the specified table in the database
	 * @return
	 */
	public String[] getColumnNames(String tableName) {
		String[] columnNames = null;
		if (connection == null) {
			logger.severe("There is no database selected to execute the query.");
			return columnNames;
		}

		// excute select sql sentence
		Statement stat = null;
		String sql = "SELECT * FROM " + tableName;

		try {
			stat = connection.createStatement();
			resultSet = stat.executeQuery(sql);
			metaData = resultSet.getMetaData();
			int numberOfColumns = metaData.getColumnCount();
			columnNames = new String[numberOfColumns];

			// get the column names and store them
			for (int column = 0; column < numberOfColumns; column++) {
				columnNames[column] = metaData.getColumnLabel(column + 1);
			}

		} catch (SQLException e) {
			logger.log(Level.SEVERE, e.toString(), e);
		} finally {
			// close the statement
			if (stat != null)
				try {
					stat.close();
				} catch (SQLException e) {
					logger.log(Level.SEVERE, e.toString(), e);
				}
		}
		return columnNames;
	}

	public Connection getConnection() {
		return connection;
	}

	/**
	 * Connect to the database
	 * 
	 * @param url
	 * @param driverName
	 * @param user
	 * @param pwd
	 */
	private void connect(String url, String driverName, String user, String pwd) {
		try {
			logger.info("Opening db connection");
			// load database driver
			Class.forName(driverName);
			// create connection
			connection = DriverManager.getConnection(url, user, pwd);
		} catch (ClassNotFoundException e) {
			logger.log(Level.SEVERE, e.toString(), e);
			logger.severe("Fail to load the database driver");
		} catch (SQLException e) {
			logger.log(Level.SEVERE, e.toString(), e);
			logger.severe("Fail to connect to the database");
		}
	}

	/**
	 * Execute query SQL sentence
	 * 
	 * @param stat
	 * @return list of the contents of the query result
	 */
	public List<List<Object>> executeQuery(PreparedStatement stat) {
		List<List<Object>> rows = new ArrayList<>();

		if (connection == null) {
			logger.severe("There is no database to execute the query.");
			return rows;
		}
		try {
			resultSet = stat.executeQuery();
			metaData = resultSet.getMetaData();

			// get all rows
			rows = new ArrayList<>();
			while (resultSet.next()) {
				List<Object> newRow = new ArrayList<>();
				for (int i = 1; i <= metaData.getColumnCount(); i++) {
					newRow.add(resultSet.getObject(i));
				}
				rows.add(newRow);
			}

		} catch (SQLException e) {
			logger.log(Level.SEVERE, e.toString(), e);
		}
		return rows;
	}

	/**
	 * Execute non-query SQL sentence
	 * 
	 * @param stat
	 */
	public void excuteNonQuery(PreparedStatement stat) {
		try {
			stat.execute();

		} catch (SQLException e) {
			logger.log(Level.SEVERE, e.toString(), e);
		}
	}

	/**
	 * Close the connection to the database
	 * 
	 * @throws SQLException
	 */
	public void close() throws SQLException {
		logger.info("Closing db connection");
		resultSet.close();
		connection.close();
	}
}
