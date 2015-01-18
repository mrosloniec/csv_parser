package test.service;

import main.model.Data;
import main.service.SqlService;
import org.junit.Assert;
import org.junit.Test;
import java.util.List;

public class SqlServiceTest {

	@Test
	public void testGetData() throws Exception {
		SqlService<Data> sqlService = new SqlService<Data>(null, null);
		List<Data> data = sqlService.getData(null);

		Assert.assertTrue(data.isEmpty());
	}
}