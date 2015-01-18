package main.service;

import main.database.driver.DBDriverFactory;
import main.database.builder.SqlStatementBuilder;
import main.reader.CSVReader;
import main.reader.ConsoleReader;
import main.model.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CsvService {

	private static final Logger LOG = LoggerFactory.getLogger(CsvService.class);
	private static final ConsoleReader consoleReader = new ConsoleReader();

	public void init() {
		Connection conn = null;
		try {
			DBDriverFactory driver = selectDatabaseType();
			conn = getConnection(driver);
			List<Data> dataList = readFile();
			insertData(conn, dataList);
		} catch (IOException e) {
			LOG.error("Exception reading file. Please check file name.", e);
		} finally {
			closeConnection(conn);
		}
	}

	private void insertData(Connection conn, List<Data> dataList) {
		validateData(conn, dataList);
		putData(conn, dataList);
	}

	private void putData(Connection conn, List<Data> dataList) {
		for (Data data : dataList) {
			SqlStatementBuilder sqlStatementBuilder = createInsertQuery(data);
			SqlService<Data> sqlService = new SqlService<Data>(conn, sqlStatementBuilder);
			sqlService.executeInsert();
		}
	}

	private SqlStatementBuilder createInsertQuery(Data data) {
		return new SqlStatementBuilder()
				.withTable("DATA")
				.withColumn("ID")
				.withColumn("ARTICLEID")
				.withColumn("ATTRIBUTE")
				.withColumn("VALUE")
				.withColumn("LANGUAGE")
				.withColumn("TYPE")
				.withValue(data.getId())
				.withValue(data.getArticleid())
				.withValue(data.getAttribute())
				.withValue(data.getValue())
				.withValue(data.getLanguage())
				.withValue(data.getType());
	}

	private void validateData(Connection conn, List<Data> dataList) {
		SqlStatementBuilder sqlStatementBuilder = createSelectQuery(dataList);
		SqlService<Data> sqlService = new SqlService<Data>(conn, sqlStatementBuilder);
		sqlService.executeSelect();
		List<Data> data = sqlService.getData(Data.class);
		if (!data.isEmpty()) {
			throw new IllegalStateException("Some objects already exists in database!");
		}
	}

	private SqlStatementBuilder createSelectQuery(List<Data> dataList) {
		SqlStatementBuilder sqlStatementBuilder = new SqlStatementBuilder()
				.withTable("DATA")
				.withColumn("ID")
				.withWhereColumn("ID")
				.withWhereOperand("OR");
		withWhereArgument(dataList, sqlStatementBuilder);
		return sqlStatementBuilder;
	}

	private void withWhereArgument(List<Data> dataList, SqlStatementBuilder sqlStatementBuilder) {
		List<String> idList = getIds(dataList);
		for (String id : idList) {
			sqlStatementBuilder.withWhereArgument(id);
		}
	}

	private List<String> getIds(List<Data> dataList) {
		List<String> idList = new ArrayList<String>();
		for (Data data : dataList) {
			idList.add(data.getId());
		}
		return idList;
	}

	private List<Data> readFile() throws IOException {
		String fileName = consoleReader.readLine("Enter file name with extension to parse: ");
		validateField(fileName, "fileName");
		return new CSVReader().read(fileName);
	}

	private Connection getConnection(DBDriverFactory driver) throws IOException {
		String databaseHost = consoleReader.readLine("Database host: ");
		String databaseName = consoleReader.readLine("Database name: ");
		String databaseUser = consoleReader.readLine("Database username: ");
		char[] databasePassword = consoleReader.readPassword("Database password: ");

		validateInputData(databaseHost, databaseName, databaseUser, databasePassword);
		return driver.getConnection(databaseHost, databaseName, databaseUser, String.valueOf(databasePassword));
	}

	private DBDriverFactory selectDatabaseType() throws IOException {
		System.out.println("What database to use?");
		System.out.println("1. MSSQL");
		String databaseType = consoleReader.readLine("");
		return DBDriverFactory.getDriver(databaseType);
	}

	private void validateInputData(String databaseHost, String databaseName, String databaseUser, char[] databasePassword) {
		validateField(databaseHost, "databaseHost");
		validateField(databaseName, "databaseName");
		validateField(databaseUser, "databaseUser");
		validateField(databasePassword, "databasePassword");
	}

	private void validateField(String field, String fieldName) {
		if (field == null || "".equals(field)) {
			throw new IllegalArgumentException(fieldName + " cannot be null or empty!");
		}
	}

	private void validateField(char[] field, String fieldName) {
		if (field == null || field.length == 0) {
			throw new IllegalArgumentException(fieldName + " cannot be null or empty!");
		}
	}

	private void closeConnection(Connection conn) {
		try {
			if (conn != null) {
				conn.close();
			}
		} catch (SQLException e) {
			LOG.warn("Cannot close connection.", e);
		}
	}

}
