package main.database.driver;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

public abstract class DBDriverFactory {

	private static final List<String> MS_SQL = new ArrayList<String>() {{
		add("1");
		add("MSSQL");
	}};

	public static DBDriverFactory getDriver(String name) {
		if (MS_SQL.contains(name)) {
			return new MsSqlDriver();
		}
		throw new IllegalArgumentException("This driver is not supported!");
	}

	public abstract Connection getConnection(String host, String databaseName, String userName, String password);

}
