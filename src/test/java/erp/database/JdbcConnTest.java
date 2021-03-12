package erp.database;

import java.sql.Connection;

import org.junit.Assert;
import org.junit.Test;


public class JdbcConnTest {

	@Test
	public void testGetConnection() {
		System.out.printf("%s()%n", "testGetConnection");
		Connection con = JdbcConn.getConnection();
		System.out.println("con > " + con); // 출력이 된다면 DB접속 성공
		Assert.assertNotNull(con);
	}

}
