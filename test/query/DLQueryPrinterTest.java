/**
 * 
 */
package query;

import static org.junit.Assert.*;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.semanticweb.owlapi.expression.ParserException;
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
		try{
			assertEquals(0,dlQueryPrinter.askQuery("NoClass", ontology, "Eau Claire").size());
			fail("Did not throw ParserException");
		}
		catch(ParserException e){
		}
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

	@Test
	public void testNeedToLookSuperclass5(){
		assertEquals(1, dlQueryPrinter.askQuery("Electric_Generation_Plants", ontology, "Eau Claire").size());
	}

	@Test
	public void testGetClassExpression(){
		assertEquals("Electric_Generation_Plants",dlQueryPrinter.getClassExpression("electric_generation_plants"));
	}

	@Test
	public void testApproximateClass(){
		assertEquals("Electric_Generation_Plants",dlQueryPrinter.getClassExpression("electric_generation_plants"));
	}

	@Test
	public void testEquivalentSuper(){
		assertEquals(1, dlQueryPrinter.askQuery("College_Dorms", ontology, "ECWRPC").size());
		assertEquals("ECWRPC [942]: Residence Halls, superclass of College Dorms",
				dlQueryPrinter.askQuery("College_Dorms", ontology, "ECWRPC").get(0));
	}

	@Test
	public void testEquivalentSuper2(){
		List<String> results = dlQueryPrinter.askQuery("Single_Unit_In_A_Duplex", ontology, "Madison");
		assertEquals(2, results.size());
		String result1 = "Madison [111]: Single Family, superclass of Single Unit In A Duplex";
		String result2 = "Madison [1110]: One Family Unit, superclass of Single Unit In A Duplex, equivalent class of Single Family";
		assertTrue(results.toString()+" does not contain "+result1, results.contains(result1));
		assertTrue(results.toString()+" does not contain "+result2, results.contains(result2));
	}

	@Test
	public void testEquivalentSuper3(){
		assertEquals(1, dlQueryPrinter.askQuery("Single_Unit_In_A_Duplex", ontology, "Bay Lakes").size());
		assertEquals("Bay Lakes [110]: Single Family, superclass of Single Unit In A Duplex", dlQueryPrinter.askQuery("Single_Unit_In_A_Duplex", ontology, "Bay Lakes").get(0));
	}

}
