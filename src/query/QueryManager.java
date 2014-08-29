package query;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.expression.ParserException;
import org.semanticweb.owlapi.model.OWLEntity;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.reasoner.OWLReasoner;
import org.semanticweb.owlapi.reasoner.OWLReasonerFactory;
import org.semanticweb.owlapi.reasoner.structural.StructuralReasonerFactory;
import org.semanticweb.owlapi.util.ShortFormProvider;
import org.semanticweb.owlapi.util.SimpleShortFormProvider;

/**
 * 
 * @author Alec Anderson, 2014
 *
 * Simple object that runs a query on the ontology contained in 
 * OWL_FILE
 *
 */

public class QueryManager {
	
	/* static array that contains our areas */
	public final static String[] AREAS = {"Bay Lakes", "Dane County", "Eau Claire", "ECWRPC", "Madison", "NCWRPC", "SEWRPC"};
	
	/* Array that contains boolean that toggles each individual area on or off
	 * boolean in areaToggle controls corresponding location in AREAS array
	 * if areaToggle = {true, false, true, false, true, false, true}, we
	 * would check Bay Lakes, Eau Claire, Madison, SEWRPC
	 */
	private boolean[] areaToggle;
	
	/* Name of file that contains ontology */
	//final public static String OWL_FILE = "ResidentialLandCodes.owl";
	
	DLQueryPrinter dlQueryPrinter;
	OWLOntology ontology;
	
	
	/**
	 * Constructor
	 * set areaToggle to all true so we print out every area by default
	 * Load our file as specified by the constant OWL_FILE
	 * create our ontology and QueryPrinter we use in runQuery
	 */
	public QueryManager(File f) {
		
		//Assume if areaToggle is never set we want all area
		areaToggle = new boolean[] {true, true, true, true, true, true, true};
		
		OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
		
		//File f = new File(fileName);
		if(f.exists()) {
			try {
				ontology = manager.loadOntologyFromOntologyDocument(f);
				
		        // We need a reasoner to do our query answering
		        OWLReasoner reasoner = createReasoner(ontology);
		        if(reasoner == null) {
		        	System.out.println("reasoner is null");
		        }
		        // Entities are named using IRIs. These are usually too long for use
		        // in user interfaces. To solve this
		        // problem, and so a query can be written using short class,
		        // property, individual names we use a short form
		        // provider. In this case, we'll just use a simple short form
		        // provider that generates short froms from IRI
		        // fragments.
		        class MyShortFormProvider extends SimpleShortFormProvider{
		        	
		        	@Override
		        	public String getShortForm(OWLEntity entity){
						if(entity.getIRI().toString() != null){
							String iri = entity.getIRI().toString();
							String[] halves = iri.split("#");
							if(halves.length == 2){
								return halves[1];
							}
						}
						return super.getShortForm(entity);
		        	}
		        }
		        	
		        ShortFormProvider shortFormProvider = new MyShortFormProvider();

		        // Create the DLQueryPrinter helper class. This will manage the
		        // parsing of input and printing of results
		        dlQueryPrinter = new DLQueryPrinter(
		        		new DLQueryEngine(reasoner, shortFormProvider),
		                shortFormProvider);
		        if(dlQueryPrinter == null) {
		        	System.out.println("dlQueryPrinter is null");
		        }
			} catch (OWLOntologyCreationException e) {
				e.printStackTrace();
				
			}
		} else {
			System.out.println("Bad File");
		}
		
		
	}
	
	/**
	 * Sets areaToggle to turn on or off different areas, all areas are on
	 * by default (set in the constructor). True is on, false is off
	 * @param bayLakes turns on Bay Lakes
	 * @param daneCounty turns on Dane County
	 * @param eauClaire turns on Eau Claire
	 * @param ecwrpc turns on ECWRPC
	 * @param madison turns on Madison
	 * @param ncwrpc turns on NCWRPC
	 * @param sewrpc turns on SEWRPC
	 */
	public void setAreas(boolean bayLakes, boolean daneCounty, boolean eauClaire, boolean ecwrpc, boolean madison, boolean ncwrpc, boolean sewrpc) {
		this.areaToggle = new boolean[] {bayLakes, daneCounty, eauClaire, ecwrpc, madison, ncwrpc, sewrpc};
	}
	
	public List<String> runQuery(String query) {
		if(dlQueryPrinter != null) {
			Set<String> results = new HashSet<String>();
			int i = 0;
			try{
				for(boolean toggle : areaToggle) {
					if(toggle) {
						results.addAll(dlQueryPrinter.askQuery(query, ontology, AREAS[i]));
					}
					i++;
				}
				if(results.isEmpty()){
					results.add("No results");
				}
				ArrayList<String> results_list = new ArrayList<String>(results);
				Collections.sort(results_list);
				return results_list;
			}
			catch(ParserException e){
				results.add("No results. No such class: "+DLQueryPrinter.getClassExpression(query));
				return new ArrayList<String>(results);
			}
		} else {
			return null;
		}
		
		
	}
	
	private static OWLReasoner createReasoner(final OWLOntology rootOntology) {
        // We need to create an instance of OWLReasoner. An OWLReasoner provides
        // the basic query functionality that we need, for example the ability
        // obtain the subclasses of a class etc. To do this we use a reasoner
        // factory.
        // Create a reasoner factory.
        OWLReasonerFactory reasonerFactory = new StructuralReasonerFactory();
        return reasonerFactory.createReasoner(rootOntology);
    }
	
}
