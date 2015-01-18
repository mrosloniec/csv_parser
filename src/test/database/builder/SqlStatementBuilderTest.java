package test.database.builder;

import main.database.builder.SqlStatementBuilder;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class SqlStatementBuilderTest {

	@Test
	public void testSelectWithColumnNull() {
		SqlStatementBuilder sqlStatementBuilder = new SqlStatementBuilder();
		sqlStatementBuilder.withColumn(null);

		String expected = sqlStatementBuilder.select();

		assertEquals("SELECT null FROM null", expected);
	}

	@Test
	public void testSelectWithColumnManyColumns() {
		SqlStatementBuilder sqlStatementBuilder = new SqlStatementBuilder();
		sqlStatementBuilder.withColumn("Column1");
		sqlStatementBuilder.withColumn("Column2");
		sqlStatementBuilder.withColumn("Column3");
		sqlStatementBuilder.withColumn("Column4");

		String expected = sqlStatementBuilder.select();

		assertEquals("SELECT Column1,Column2,Column3,Column4 FROM null", expected);
	}

	@Test
	public void testSelectWithWhereOperandNull() {
		SqlStatementBuilder sqlStatementBuilder = new SqlStatementBuilder();
		sqlStatementBuilder.withWhereOperand(null);

		String expected = sqlStatementBuilder.select();

		assertEquals("SELECT  FROM null", expected);
	}

	@Test
	public void testSelectWithWhereOperandNotNull() {
		SqlStatementBuilder sqlStatementBuilder = new SqlStatementBuilder();
		sqlStatementBuilder.withWhereOperand("AND");

		String expected = sqlStatementBuilder.select();

		assertEquals("SELECT  FROM null", expected);
	}

	@Test
	public void testSelectWithWhereStatementComplete() {
		SqlStatementBuilder sqlStatementBuilder = new SqlStatementBuilder();
		sqlStatementBuilder.withWhereArgument("10");
		sqlStatementBuilder.withWhereColumn("ID");
		sqlStatementBuilder.withWhereOperand("AND");

		String expected = sqlStatementBuilder.select();

		assertEquals("SELECT  FROM null WHERE ID = 10", expected);
	}

	@Test
	public void testSelectWithWhereManyArgumentsComplete() {
		SqlStatementBuilder sqlStatementBuilder = new SqlStatementBuilder();
		sqlStatementBuilder.withWhereArgument("1");
		sqlStatementBuilder.withWhereArgument("2");
		sqlStatementBuilder.withWhereArgument("5");
		sqlStatementBuilder.withWhereArgument("50");
		sqlStatementBuilder.withWhereArgument("60");
		sqlStatementBuilder.withWhereColumn("ID");
		sqlStatementBuilder.withWhereOperand("AND");

		String expected = sqlStatementBuilder.select();

		assertEquals("SELECT  FROM null WHERE ID = 1 AND ID = 2 AND ID = 5 AND ID = 50 AND ID = 60", expected);
	}

	@Test
	public void testProperSelect() {
		SqlStatementBuilder sqlStatementBuilder = new SqlStatementBuilder();
		sqlStatementBuilder.withTable("DATA");
		sqlStatementBuilder.withColumn("ID");
		sqlStatementBuilder.withColumn("ARGUMENTID");
		sqlStatementBuilder.withColumn("LANGUAGE");
		sqlStatementBuilder.withWhereArgument("1");
		sqlStatementBuilder.withWhereArgument("2");
		sqlStatementBuilder.withWhereArgument("5");
		sqlStatementBuilder.withWhereArgument("50");
		sqlStatementBuilder.withWhereArgument("60");
		sqlStatementBuilder.withWhereColumn("ID");
		sqlStatementBuilder.withWhereOperand("AND");

		String expected = sqlStatementBuilder.select();

		assertEquals("SELECT ID,ARGUMENTID,LANGUAGE FROM DATA WHERE ID = 1 AND ID = 2 AND ID = 5 AND ID = 50 AND ID = 60", expected);
	}

	@Test
	public void testInsertWithValueNull() {
		SqlStatementBuilder sqlStatementBuilder = new SqlStatementBuilder();
		sqlStatementBuilder.withValue(null);

		String expected = sqlStatementBuilder.insert();

		assertEquals("INSERT INTO null", expected);
	}

	@Test
	public void testInsertWithManyValues() {
		SqlStatementBuilder sqlStatementBuilder = new SqlStatementBuilder();
		sqlStatementBuilder.withValue("100");
		sqlStatementBuilder.withValue("200");
		sqlStatementBuilder.withValue("300");
		sqlStatementBuilder.withValue("''300");

		String expected = sqlStatementBuilder.insert();

		assertEquals("INSERT INTO null VALUES('100','200','300','''''300')", expected);
	}

	@Test
	public void testInsertProperValue() {
		SqlStatementBuilder sqlStatementBuilder = new SqlStatementBuilder();
		sqlStatementBuilder.withTable("DATA");
		sqlStatementBuilder.withColumn("ID");
		sqlStatementBuilder.withColumn("ARGUMENTID");
		sqlStatementBuilder.withColumn("LANGUAGE");
		sqlStatementBuilder.withValue("100");
		sqlStatementBuilder.withValue("200");
		sqlStatementBuilder.withValue("5");

		String expected = sqlStatementBuilder.insert();

		assertEquals("INSERT INTO DATA(ID,ARGUMENTID,LANGUAGE) VALUES('100','200','5')", expected);
	}

}
