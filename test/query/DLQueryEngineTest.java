/**
 * 
 */
package query;

import static org.junit.Assert.*;

import java.io.File;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.semanticweb.owlapi.model.OWLClassExpression;

/**
 * @author cphillips
 *
 */
public class DLQueryEngineTest {
	
	private DLQueryEngine dlQueryEngine;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		QueryManager m = new QueryManager(new File("WebContent/WisLandUseCodes.owl"));
		dlQueryEngine = m.dlQueryPrinter.dlQueryEngine;
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {
		OWLClassExpression cls = dlQueryEngine.parser.parseClassExpression("Single_Unit_In_A_Duplex");
		System.out.println(dlQueryEngine.reasoner.getSuperClasses(cls, true));
		System.out.println(dlQueryEngine.reasoner.getSuperClasses(cls, true));

		
		System.out.println(dlQueryEngine.reasoner.getSuperClasses(cls, false));
		System.out.println(dlQueryEngine.reasoner.getSuperClasses(cls, false).getFlattened());
	}

}
