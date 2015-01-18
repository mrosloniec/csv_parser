package main.database.builder;

import java.util.ArrayList;
import java.util.List;

public class SqlStatementBuilder {

	private String table;
	private List<String> columns;
	private List<String> values;
	private String whereColumn;
	private String whereOperand;
	private List<String> whereArguments;

	public SqlStatementBuilder() {
		whereArguments = new ArrayList<String>();
		columns = new ArrayList<String>();
		values = new ArrayList<String>();
	}

	public SqlStatementBuilder withColumn(String column) {
		columns.add(column);
		return this;
	}

	public SqlStatementBuilder withTable(String table) {
		this.table = table;
		return this;
	}

	public SqlStatementBuilder withWhereColumn(String whereColumn) {
		this.whereColumn = whereColumn;
		return this;
	}

	public SqlStatementBuilder withWhereArgument(String whereArgument) {
		whereArguments.add(whereArgument);
		return this;
	}

	public SqlStatementBuilder withWhereOperand(String whereOperand) {
		this.whereOperand = whereOperand;
		return this;
	}

	public String select() {
		StringBuilder sql = new StringBuilder();
		addSelect(sql);
		addFrom(sql);
		addWhereClause(sql);
		return sql.toString();
	}

	public String insert() {
		StringBuilder sql = new StringBuilder();
		addInsert(sql);
		addColumns(sql);
		addValues(sql);
		return sql.toString();
	}

	private void addInsert(StringBuilder sql) {
		sql.append("INSERT INTO ");
		sql.append(table);
	}

	private void addColumns(StringBuilder sql) {
		if (columns.isEmpty()) {
			return;
		}
		sql.append("(");
		int index = 0;
		for (String column : columns) {
			sql.append(column);
			if (columns.size() - 1 != index++) {
				sql.append(",");
			}
		}
		sql.append(")");
	}

	private void addValues(StringBuilder sql) {
		if (values.isEmpty()) {
			return;
		}
		sql.append(" VALUES(");
		int index = 0;
		for (String value : values) {
			sql.append("'");
			sql.append(value.replaceAll("'","''"));
			sql.append("'");
			if (values.size() - 1 != index++) {
				sql.append(",");
			}
		}
		sql.append(")");
	}

	private void addSelect(StringBuilder sql) {
		sql.append("SELECT ");
		int index = 0;
		for (String column : columns) {
			sql.append(column);
			if (columns.size() - 1 != index++) {
				sql.append(",");
			}
		}
	}

	private void addFrom(StringBuilder sql) {
		sql.append(" FROM ");
		sql.append(table);
	}

	private void addWhereClause(StringBuilder sql) {
		if (whereColumn == null || whereOperand == null || whereArguments.isEmpty()) {
			return;
		}
		int index = 0;
		sql.append(" WHERE ");
		for (String whereArgument : whereArguments) {
			sql.append(whereColumn);
			sql.append(" = ");
			sql.append(whereArgument);
			if (whereArguments.size() - 1 != index++) {
				sql.append(" ");
				sql.append(whereOperand);
				sql.append(" ");
			}
		}
	}

	public List<String> getColumns() {
		return columns;
	}

	public SqlStatementBuilder withValue(String value) {
		if (value != null) {
			values.add(value);
		}
		return this;
	}
}
