/**
 * 
 */
package query;

import static org.junit.Assert.*;
import java.io.File;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.semanticweb.owlapi.model.OWLOntology;

/**
 * @author cphillips
 *
 */
public class DLQueryPrinterTest {
	
	
	private DLQueryPrinter dlQueryPrinter;
	private OWLOntology	ontology;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		QueryManager m = new QueryManager(new File("WebContent/WisLandUseCodes.owl"));		
		assertNotNull(m);
		dlQueryPrinter = m.dlQueryPrinter;
		ontology = m.ontology;
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testFoundItRightAway() {
		assertEquals(1,dlQueryPrinter.askQuery("Vacant_Lot", ontology, "Bay Lakes").size());
		assertEquals(1,dlQueryPrinter.askQuery("Residential", ontology, "Madison").size());
		assertEquals(1,dlQueryPrinter.askQuery("Residential", ontology, "NCWRPC").size());
	}
	
	@Test
	public void testNeedToLookSubclass(){
		assertEquals(1,dlQueryPrinter.askQuery("Vacant", ontology, "Eau Claire").size());
		
		//assertEquals(4,dlQueryPrinter.askQuery("Residential", ontology, "SEWRPC").size());
	}
	
	@Test
	public void testNoSuchClass(){
		assertEquals(0,dlQueryPrinter.askQuery("NoClass", ontology, "Eau Claire").size());
	}
	
	@Test
	public void testNeedToLookSuperclass1(){
		assertEquals(1,dlQueryPrinter.askQuery("Vacant_Lot", ontology, "ECWRPC").size());
	}
	
	@Test
	public void testNeedToLookSuperclass2(){
		assertEquals(1,dlQueryPrinter.askQuery("Vacant_Lot", ontology, "NCWRPC").size());
	}
	
	@Test
	public void testNeedToLookSuperclass3(){
		assertEquals(1,dlQueryPrinter.askQuery("County_Garages", ontology, "NCWRPC").size());
	}
	
	@Test
	public void testLowerClassName(){
		assertEquals(1,dlQueryPrinter.askQuery("vacant_lot", ontology, "Bay Lakes").size());
	}
	
	@Test
	public void testDeepLookSubclass(){
		assertTrue(1 < dlQueryPrinter.askQuery("Natural_Areas", ontology, "SEWRPC").size());
	}
	
	@Test
	public void testNeedToLookSuperclass4(){
		assertEquals(1, dlQueryPrinter.askQuery("County_Garages", ontology, "Bay Lakes").size());
	}
	
	@Test
	public void testEquivalent(){
		assertEquals(1, dlQueryPrinter.askQuery("Dorms", ontology, "ECWRPC").size());
	}

}
