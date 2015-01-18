package main.database.driver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MsSqlDriver extends DBDriverFactory {

	private static final Logger LOG = LoggerFactory.getLogger(MsSqlDriver.class);
	private static final String URL = "jdbc:sqlserver://$:1433;databaseName=#;";

	@Override
	public Connection getConnection(String host, String databaseName, String userName, String password) {
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			return DriverManager.getConnection(URL.replace("$", host).replace("#", databaseName), userName, password);
		} catch (ClassNotFoundException e) {
			LOG.error("Error connecting database. Driver problems.", e);
		} catch (SQLException e) {
			LOG.error("Error connecting database. Check host, database name, username and password.", e);
		}

		return null;
	}
}
