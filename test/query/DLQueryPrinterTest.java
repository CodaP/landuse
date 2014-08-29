/**
 * 
 */
package query;

import static org.junit.Assert.*;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Set;

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
		assertEquals("Bay Lakes [195]: Vacant Lot",
				dlQueryPrinter.askQuery("Vacant_Lot", ontology, "Bay Lakes").iterator().next());
		assertEquals(1,dlQueryPrinter.askQuery("Residential", ontology, "Madison").size());
		assertEquals("Madison [1]: Residential",
				dlQueryPrinter.askQuery("Residential", ontology, "Madison").iterator().next());
		assertEquals(1,dlQueryPrinter.askQuery("Residential", ontology, "NCWRPC").size());
		assertEquals("NCWRPC [5]: Residential",
				dlQueryPrinter.askQuery("Residential", ontology, "NCWRPC").iterator().next());
	}

	@Test
	public void testNeedToLookSubclass(){
		assertEquals(1,dlQueryPrinter.askQuery("Vacant", ontology, "Eau Claire").size());
		assertEquals("Eau Claire [RV]: Vacant Residence, subclass of Vacant",dlQueryPrinter.askQuery("Vacant", ontology, "Eau Claire").iterator().next());
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
		assertEquals("ECWRPC [94]: Vacant, superclass of Vacant Lot",
				dlQueryPrinter.askQuery("Vacant_Lot", ontology, "ECWRPC").iterator().next());

	}

	@Test
	public void testNeedToLookSuperclass2(){
		assertEquals(1,dlQueryPrinter.askQuery("Vacant_Lot", ontology, "NCWRPC").size());
		assertEquals("NCWRPC [5]: Residential, superclass of Vacant Lot",
				dlQueryPrinter.askQuery("Vacant_Lot", ontology, "NCWRPC").iterator().next());
	}

	@Test
	public void testNeedToLookSuperclass3(){
		assertEquals(1,dlQueryPrinter.askQuery("County_Garages", ontology, "NCWRPC").size());
		assertEquals("NCWRPC [9]: Transportation, superclass of County Garages",
				dlQueryPrinter.askQuery("County_Garages", ontology, "NCWRPC").iterator().next());
	}

	@Test
	public void testLowerClassName(){
		assertEquals(1,dlQueryPrinter.askQuery("vacant_lot", ontology, "Bay Lakes").size());
		assertEquals("Bay Lakes [195]: Vacant Lot",
				dlQueryPrinter.askQuery("Vacant_Lot", ontology, "Bay Lakes").iterator().next());
	}

	@Test
	public void testDeepLookSubclass(){
		Set<String> results = dlQueryPrinter.askQuery("Natural_Areas", ontology, "SEWRPC");
		String str1 = "SEWRPC [940]: Woodlands, subclass of Natural Areas";
		String str2 = "SEWRPC [910]: Wetlands, subclass of Natural Areas";
		String str3 = "SEWRPC [950]: Surface Water, subclass of Natural Areas";
		String str4 = "SEWRPC [922]: Unused Land-Rural, subclass of Natural Areas";
		String str5 = "SEWRPC [921]: Unused Land-Urban, subclass of Natural Areas";
		assertEquals(5, dlQueryPrinter.askQuery("Natural_Areas", ontology, "SEWRPC").size());
		assertTrue(results.contains(str1));
		assertTrue(results.contains(str2));
		assertTrue(results.contains(str3));
		assertTrue(results.contains(str4));
		assertTrue(results.contains(str5));

	}

	@Test
	public void testNeedToLookSuperclass4(){
		assertEquals(1, dlQueryPrinter.askQuery("County_Garages", ontology, "Bay Lakes").size());
		assertEquals("Bay Lakes [410]: Motor Vehicle Related, superclass of County Garages",
				dlQueryPrinter.askQuery("County_Garages", ontology, "Bay Lakes").iterator().next());
	}

	@Test
	public void testEquivalent(){
		assertEquals(1, dlQueryPrinter.askQuery("Dorms", ontology, "ECWRPC").size());
		assertEquals("ECWRPC [942]: Residence Halls, synonym of Dorms",
				dlQueryPrinter.askQuery("Dorms", ontology, "ECWRPC").iterator().next());
	}
	
	@Test
	public void testEquivalent2(){
		String str1 = "Madison [111]: Single Family";
		String str2 = "Madison [1110]: One Family Unit, synonym of Single Family";
		assertEquals(2, dlQueryPrinter.askQuery("Single_Family", ontology, "Madison").size());
		assertTrue(dlQueryPrinter.askQuery("Single_Family", ontology, "Madison").contains(str1));
		assertTrue(dlQueryPrinter.askQuery("Single_Family", ontology, "Madison").contains(str2));
	}

	@Test
	public void testNeedToLookSuperclass5(){
		assertEquals(1, dlQueryPrinter.askQuery("Electric_Generation_Plants", ontology, "Eau Claire").size());
		assertEquals("Eau Claire [PU]: Utilities/Communication, superclass of Electric Generation Plants",
				dlQueryPrinter.askQuery("Electric_Generation_Plants", ontology, "Eau Claire").iterator().next());
	}

	@Test
	public void testApproximateClass(){
		assertEquals(1,dlQueryPrinter.askQuery("electric_generation_plants", ontology, "Madison").size());
		assertEquals("Madison [4812]: Electric Generation Plants",
				dlQueryPrinter.askQuery("electric_generation_plants", ontology, "Madison").iterator().next());
	}

	@Test
	public void testEquivalentSuper(){
		assertEquals(1, dlQueryPrinter.askQuery("College_Dorms", ontology, "ECWRPC").size());
		assertEquals("ECWRPC [942]: Residence Halls, superclass of College Dorms",
				dlQueryPrinter.askQuery("College_Dorms", ontology, "ECWRPC").iterator().next());
	}

	@Test
	public void testEquivalentSuper2(){
		Set<String> results = dlQueryPrinter.askQuery("Single_Unit_In_A_Duplex", ontology, "Madison");
		assertEquals(2, results.size());
		String result1 = "Madison [111]: Single Family, superclass of Single Unit In A Duplex, equivalent class of One Family Unit";
		String result2 = "Madison [1110]: One Family Unit, superclass of Single Unit In A Duplex";
		assertTrue(results.toString()+" does not contain "+result1, results.contains(result1));
		assertTrue(results.toString()+" does not contain "+result2, results.contains(result2));
	}

	@Test
	public void testEquivalentSuper3(){
		assertEquals(1, dlQueryPrinter.askQuery("Single_Unit_In_A_Duplex", ontology, "Bay Lakes").size());
		assertEquals("Bay Lakes [110]: Single Family, superclass of Single Unit In A Duplex", dlQueryPrinter.askQuery("Single_Unit_In_A_Duplex", ontology, "Bay Lakes").iterator().next());
	}
	
	@Test
	public void testSlash(){
		assertEquals(1, dlQueryPrinter.askQuery("Piers/Docks", ontology, "Bay Lakes").size());
		assertEquals("Bay Lakes [484]: Piers/Docks", dlQueryPrinter.askQuery("Piers/Docks", ontology, "Bay Lakes").iterator().next());
	}
	
	@Test
	public void testDash(){
		String class_ = "Parcels_in_Agriculture_Areas_Where_Other_Non-Agricultural_Uses_Have_Been_Vacated";
		assertEquals(1,dlQueryPrinter.askQuery(class_, ontology, "Eau Claire").size());
		assertEquals("Eau Claire [AV]: Parcels in Agriculture Areas Where Other Non-Agricultural Uses Have Been Vacated",
				dlQueryPrinter.askQuery(class_, ontology, "Eau Claire").iterator().next());
		
	}

}
