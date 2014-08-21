/**
 * 
 */
package query;

import static org.junit.Assert.*;

import java.io.File;

import query.QueryManager;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * @author cphillips
 *
 */
public class QueryManagerTest {
	
	private QueryManager m;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {	
		m = new QueryManager(new File("WebContent/WisLandUseCodes.owl"));
		assertNotNull(m);
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}
	
	@Test
	public void testInitQP(){
		assertNotNull(m.dlQueryPrinter);
	}

	@Test
	public void test() {
		assertNotNull(m.runQuery("Vacant_Lot"));
	}
	
	@Test
	public void testNoClass(){
		m.runQuery("NoClass");
	}

}
