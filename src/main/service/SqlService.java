package main.service;

import main.database.builder.SqlStatementBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SqlService<T> {

	private Connection conn;
	private ResultSet resultSet;
	private SqlStatementBuilder sqlStatementBuilder;
	private static final Logger LOG = LoggerFactory.getLogger(SqlService.class);

	public SqlService(Connection conn, SqlStatementBuilder sqlStatementBuilder) {
		this.conn = conn;
		this.sqlStatementBuilder = sqlStatementBuilder;
	}

	public void executeSelect() {
		try {
			PreparedStatement preparedStatement = conn.prepareStatement(sqlStatementBuilder.select());
			resultSet = preparedStatement.executeQuery();
		} catch (SQLException e) {
			LOG.error("Query execution failed.", e);
		}
	}

	public void executeInsert() {
		try {
			PreparedStatement preparedStatement = conn.prepareStatement(sqlStatementBuilder.insert());
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			LOG.error("Query execution failed", e);
		}
	}

	public List<T> getData(Class<T> classToMap) {
		if (classToMap == null) {
			return Collections.emptyList();
		}
		List<T> list = new ArrayList<T>();
		try {
			while (resultSet.next()) {
				list.add(mapElements(classToMap));
			}
		} catch (SQLException e) {
			LOG.error("Query execution failed.", e);
		}
		return list;
	}

	private T mapElements(Class<T> classToMap) {
		try {
			T instance = classToMap.newInstance();
			for (String column : sqlStatementBuilder.getColumns()) {
				String element = resultSet.getString(column);
				String field = column.substring(0, 1).toUpperCase() + column.substring(1, column.length()).toLowerCase();
				Method method = classToMap.getMethod("set" + field, String.class);
				method.invoke(instance, element);
			}
			return instance;
		} catch (InstantiationException e) {
			LOG.error("Error mapping elements.", e);
		} catch (IllegalAccessException e) {
			LOG.error("Error mapping elements.", e);
		} catch (SQLException e) {
			LOG.error("Error mapping elements.", e);
		} catch (NoSuchMethodException e) {
			LOG.error("Error mapping elements.", e);
		} catch (InvocationTargetException e) {
			LOG.error("Error mapping elements.", e);
		}
		return null;
	}

}
