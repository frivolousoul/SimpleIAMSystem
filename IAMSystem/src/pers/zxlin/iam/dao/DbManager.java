/**
 * 
 */
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

/**
 * Manage the database related operation
 * 
 * @author BoJack
 *
 */
/**
 * @author BoJack
 *
 */
public class DbManager {
	Connection connection;
	ResultSet resultSet;
	ResultSetMetaData metaData;

	/**
	 * Constructor of the Database manager
	 * 
	 * @param url
	 *            database connection url
	 * @param driverName-
	 *            JDBC driver name of database used
	 * @param user
	 *            login of the database connection
	 * @param pwd
	 *            password of the database connection
	 */
	public DbManager(String url, String driverName, String user, String pwd) {
		connect(url, driverName, user, pwd);
		// getTableInfo();
	}

	public String[] getColumnNames(String tableName) {
		String[] columnNames = null;
		if (connection == null) {
			System.err.println("There is no database to execute the query.");
			return columnNames;
		}

		Statement stat = null;
		String sql = "SELECT * FROM " + tableName;

		try {
			stat = connection.createStatement();
			resultSet = stat.executeQuery(sql);
			metaData = resultSet.getMetaData();

			int numberOfColumns = metaData.getColumnCount();
			columnNames = new String[numberOfColumns];

			// get the column names and cache them
			for (int column = 0; column < numberOfColumns; column++) {
				columnNames[column] = metaData.getColumnLabel(column + 1);
			}

		} catch (SQLException e) {
			System.err.println(e);
		} finally {
			if (stat != null)
				try {
					stat.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
		return columnNames;
	}

	public Connection getConnection() {
		return connection;
	}

	/**
	 * Make connection to the database
	 * 
	 * @param url
	 * @param driverName
	 * @param user
	 * @param pwd
	 */
	private void connect(String url, String driverName, String user, String pwd) {
		try {
			Class.forName(driverName);
			System.out.println("Opening db connection");
			connection = DriverManager.getConnection(url, user, pwd);
		} catch (ClassNotFoundException e) {
			System.err.println("Cannot find the database driver classes.");
			System.err.println(e);
		} catch (SQLException e) {
			System.err.println("Cannot connect to this database.");
			System.err.println(e);
		}
	}

	/**
	 * Execute query SQL sentence
	 * 
	 * @param stat
	 * @return List of the contents of the query result
	 */
	public List<List<Object>> executeQuery(PreparedStatement stat) {
		List<List<Object>> rows = new ArrayList<List<Object>>();

		if (connection == null) {
			System.err.println("There is no database to execute the query.");
			return rows;
		}
		try {
			resultSet = stat.executeQuery();
			metaData = resultSet.getMetaData();

			// get all rows
			rows = new ArrayList<List<Object>>();
			while (resultSet.next()) {
				List<Object> newRow = new ArrayList<Object>();
				for (int i = 1; i <= metaData.getColumnCount(); i++) {
					newRow.add(resultSet.getObject(i));
				}
				rows.add(newRow);
			}
			// close(); Need to copy the metaData, bug in jdbc:odbc driver.

		} catch (SQLException e) {
			System.err.println(e);
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
			System.err.println(e);
		}
	}

	/**
	 * Close the connection to the database
	 * 
	 * @throws SQLException
	 */
	public void close() throws SQLException {
		System.out.println("Closing db connection");
		resultSet.close();
		connection.close();
	}
}
