package erp.dao;

import static org.junit.Assert.fail;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;

import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import erp.daoImpl.EmployeeDetailDaoImpl;
import erp.dto.EmployeeDetail;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class EmployeeDetailDaoTest {
	private EmployeeDetailDao dao = EmployeeDetailDaoImpl.getInstance();

//	@BeforeClass
//	public static void setUpBeforeClass() throws Exception {
//	}
//
//	@Before
//	public void setUp() throws Exception {
//	}
//
//	@After
//	public void tearDown() throws Exception {
//	}

//	@Test
//	public void test04SelectEmployeeDatailByNo() {
//		fail("Not yet implemented");
//	}

	@Test
	public void test01InsertEmployeeDetail() {
		System.out.printf("%s()%n", "test01InsertEmployeeDetail");
		EmployeeDetail empDetail = new EmployeeDetail(1003, true, new Date(), "1234", getImage("noimage.jpg"));
		int res = dao.insertEmployeeDetail(empDetail);
		Assert.assertEquals(1, res);
	}

	private byte[] getImage(String imgName) {
		//빈 바이트 배열을 생성해준다.
		byte[] pic = null;
		//파일생성	//	/image/imgName
		File file = new File(System.getProperty("user.dir") + File.separator + "image", imgName);
		try(InputStream is = new FileInputStream(file)){
			pic = new byte[is.available()]; //file로부터 읽은 이미지의 바이트 길이로 배열 생성
			is.read(pic);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return pic;
	}

//	@Test
//	public void test02UpdateEmployeeDetail() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void test03DeleteEmployeeDetail() {
//		fail("Not yet implemented");
//	}

}
