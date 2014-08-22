package query;
/*
 *
 * The contents of this file are subject to the LGPL License, Version 3.0.
 *
 * Copyright (C) 2011, The University of Manchester
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see http://www.gnu.org/licenses/.
 *
 *
 * Alternatively, the contents of this file may be used under the terms of the Apache License, Version 2.0
 * in which case, the provisions of the Apache License Version 2.0 are applicable instead of those above.
 *
 * Copyright 2011, The University of Manchester
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import java.util.Collections;
import java.util.Set;
import org.semanticweb.owlapi.expression.ParserException;
import org.semanticweb.owlapi.model.AxiomType;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDeclarationAxiom;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.reasoner.Node;
import org.semanticweb.owlapi.reasoner.NodeSet;
import org.semanticweb.owlapi.reasoner.OWLReasoner;
import org.semanticweb.owlapi.util.ShortFormProvider;

class DLQueryEngine {

	final OWLReasoner reasoner;
	final DLQueryParser parser;
	private final OWLOntology rootOntology;
	private final ShortFormProvider shortFormProvider;

	/**
	 * Constructs a DLQueryEngine. This will answer "DL queries" using the
	 * specified reasoner. A short form provider specifies how entities are
	 * rendered.
	 * 
	 * @param reasoner
	 *        The reasoner to be used for answering the queries.
	 * @param shortFormProvider
	 *        A short form provider.
	 */
	public DLQueryEngine(OWLReasoner reasoner,
			ShortFormProvider shortFormProvider) {
		this.reasoner = reasoner;
		rootOntology = reasoner.getRootOntology();
		this.shortFormProvider = shortFormProvider;
		parser = new DLQueryParser(rootOntology, shortFormProvider);
	}

	/**
	 * Gets the superclasses of a class expression parsed from a string.
	 * 
	 * @param classExpressionString
	 *        The string from which the class expression will be parsed.
	 * @param direct
	 *        Specifies whether direct superclasses should be returned or not.
	 * @return The superclasses of the specified class expression If there was a
	 *         problem parsing the class expression.
	 */
	public Set<OWLClass> getSuperClasses(String classExpressionString,
			boolean direct) {
		if (classExpressionString.trim().length() == 0) {
			return Collections.emptySet();
		}
		OWLClassExpression classExpression = parser
				.parseClassExpression(classExpressionString);
		return getSuperClasses(classExpression,direct);
	}

	public Set<OWLClass> getSuperClasses(OWLClassExpression classExpression, boolean direct) {
		NodeSet<OWLClass> superClasses = reasoner.getSuperClasses(
				classExpression, direct);
		return superClasses.getFlattened();
	}

	/**
	 * Gets the equivalent classes of a class expression parsed from a string.
	 * 
	 * @param classExpressionString
	 *        The string from which the class expression will be parsed.
	 * @return The equivalent classes of the specified class expression If there
	 *         was a problem parsing the class expression.
	 */
	public Set<OWLClass> getEquivalentClasses(String classExpressionString) {
		if (classExpressionString.trim().length() == 0) {
			return Collections.emptySet();
		}
		OWLClassExpression classExpression = parser
				.parseClassExpression(classExpressionString);
		return getEquivalentClasses(classExpression);
	}

	public Set<OWLClass> getEquivalentClasses(OWLClassExpression classExpression) {
		Node<OWLClass> equivalentClasses = reasoner
				.getEquivalentClasses(classExpression);
		Set<OWLClass> result;
		if (classExpression.isAnonymous()) {
			result = equivalentClasses.getEntities();
		} else {
			result = equivalentClasses.getEntitiesMinus(classExpression
					.asOWLClass());
		}
		return result;
	}

	/**
	 * Gets the subclasses of a class expression parsed from a string.
	 * 
	 * @param classExpressionString
	 *        The string from which the class expression will be parsed.
	 * @param direct
	 *        Specifies whether direct subclasses should be returned or not.
	 * @return The subclasses of the specified class expression If there was a
	 *         problem parsing the class expression.
	 */
	public Set<OWLClass> getSubClasses(String classExpressionString,
			boolean direct) {
		if (classExpressionString.trim().length() == 0) {
			return Collections.emptySet();
		}
		OWLClassExpression classExpression = parser
				.parseClassExpression(classExpressionString);
		return getSubClasses(classExpression,direct);
	}

	public Set<OWLClass> getSubClasses(OWLClassExpression classExpression, boolean direct) {
		NodeSet<OWLClass> subClasses = reasoner.getSubClasses(classExpression,
				direct);
		//OWLClass o1 = (OWLClass) classExpression;
		//OWLClass o2 = subClasses.getFlattened().iterator().next();
		//System.out.println("Superclass "+o1.toString());
		//System.out.println("Subclass "+o2.toString());
		//System.out.println(reasoner.isEntailed(new OWLSubClassOfAxiomImpl(o2,o1, new ArrayList<OWLAnnotation>())));
		return subClasses.getFlattened();
	}

	/**
	 * Gets the instances of a class expression parsed from a string.
	 * 
	 * @param classExpressionString
	 *        The string from which the class expression will be parsed.
	 * @param direct
	 *        Specifies whether direct instances should be returned or not.
	 * @return The instances of the specified class expression If there was a
	 *         problem parsing the class expression.
	 */
	public Set<OWLNamedIndividual> getInstances(String classExpressionString,
			boolean direct) {
		if (classExpressionString.trim().length() == 0) {
			return Collections.emptySet();
		}
		OWLClassExpression classExpression = parser
				.parseClassExpression(classExpressionString);
		NodeSet<OWLNamedIndividual> individuals = reasoner.getInstances(
				classExpression, direct);
		return individuals.getFlattened();
	}

	/**
	 * @author Alec Anderson
	 * Gets the class of a class expression parsed from a string
	 * 
	 * @param classExpressionString
	 * 			The string from which the class expression will be parsed
	 * @return The instance of the class
	 */
	public OWLClass getClass(String classExpressionString) {
		try{
			OWLClassExpression classExpression = parser
					.parseClassExpression(classExpressionString);
			if(!classExpression.isAnonymous()) {
				return classExpression.asOWLClass();
			} else {
				System.out.println("bad input");
				return null;
			}
		}
		catch(ParserException e){
			for(OWLDeclarationAxiom dax : rootOntology.getAxioms(AxiomType.DECLARATION)){
				OWLClass cls = dax.getEntity().asOWLClass();
				String shortForm = this.shortFormProvider.getShortForm(cls);
				if(shortForm.contains(classExpressionString)){
					System.out.println("Found similar class "+shortForm);
					return cls;
				}
			}
			throw(e);
		}

	}

}