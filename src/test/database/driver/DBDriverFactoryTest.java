package test.database.driver;

import main.database.driver.DBDriverFactory;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

public class DBDriverFactoryTest {

	@Test(expected= IllegalArgumentException.class)
	public void testGetDriverNull() {
		DBDriverFactory.getDriver(null);
	}

	@Test
	public void testGetDriverMsSql() {
		DBDriverFactory driver = DBDriverFactory.getDriver("MSSQL");

		assertNotNull(driver);
	}

	@Test
	public void testGetDriverMsSqlAsNumber() {
		DBDriverFactory driver = DBDriverFactory.getDriver("1");

		assertNotNull(driver);
	}



}
