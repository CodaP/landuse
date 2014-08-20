package query;
/*
 * This file is part of the OWL API.
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

/*
 * Modified extensively by Alec Anderson for use
 */

import java.util.ArrayList;
import java.util.Set;

import org.apache.commons.lang3.text.WordUtils;
import org.semanticweb.owlapi.expression.ParserException;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.util.ShortFormProvider;


class DLQueryPrinter {

    private final DLQueryEngine dlQueryEngine;
    private final ShortFormProvider shortFormProvider;

    private static final String EXTRA = "<http://www.semanticweb.org/ontologies/2011/9/ResidentialLandCodes.owl#";
    
    /**
     * @param engine
     *        the engine
     * @param shortFormProvider
     *        the short form provider
     */
    public DLQueryPrinter(DLQueryEngine engine,
            ShortFormProvider shortFormProvider) {
        this.shortFormProvider = shortFormProvider;
        dlQueryEngine = engine;
    }

    /**
     * @param classExpression
     *        the class expression to use for interrogation
     * @return
     * 		returns the query result, "" if no result for given area
     */
    public ArrayList<String> askQuery(String classExpression, OWLOntology ontology, String area) {
    	ArrayList<String> results = new ArrayList<String>();
        if (classExpression.length() == 0) {
        	return results;
        } else {
            try {
                OWLClass cls = dlQueryEngine.getClass(getClassExpression(classExpression));
                if(cls != null) {
                	//remove the extra bits from the class name
                	String clsName = getClassName(cls);
                	
                	for(int j=0; j < cls.getAnnotations(ontology).size(); j++) {
                		
                		String lines [] = getComments(cls, ontology);
               			boolean found = false;
                       	for(String line : lines) {
                       		//check comments for area and see if it's there
                       		if(line.contains(area)) {
                       			results.add(line + ": " + clsName);
                       			return results;
                       		}
                       	}
                       	// check equivalent classes
                       	if(!found) {
                       		//System.out.println(area + " not found");
                       		Set<OWLClass> equivalentClasses = dlQueryEngine
                                       .getEquivalentClasses(classExpression);
                       		
                       		for(OWLClass equivalent : equivalentClasses) {
                       			String equivalentLines[] = getComments(equivalent, ontology);
                       			for(String line : equivalentLines) {
                       				if(line.contains(area)) {
                       					String equivalentName = getClassName(equivalent);
                       					results.add(line + ": " + equivalentName + ", synonym of " + clsName);
                       					return results;
                       				}
                       			}
                       		}
                       		
                       		//check subclasses next
                       		/*
                       		 * 
                       		 * Don't return anything here yet. fix this
                       		 * 
                       		 */
                       		if(!found) {
                       			Set<OWLClass> subClasses = dlQueryEngine
                                           .getSubClasses(classExpression, true);
                           		for(OWLClass subClass : subClasses) {
                           			String subLines[] = getComments(subClass, ontology);
                           			if(subLines != null) {
                           				for(String line : subLines) {
                               				if(line.contains(area)) {
                               					String subName = getClassName(subClass);
                               					results.add(line + ": " + subName + ", subclass of " + clsName);
                                       			found = true;
                               				}
                               			}
                           			}
                           			
                           		}
                           		//we don't return results right away because subclasses is the one area where we
                           		//can more than one result
                           		if(found) {
                           			return results;
                           		} else {
                           			//go up the ontology and check superclass last
                           			Set<OWLClass> superClassesSet = dlQueryEngine.getSuperClasses(classExpression, true);
                       				ArrayList<OWLClass> superClasses = new ArrayList<OWLClass>(superClassesSet);
                   					for(int i = 0; i < superClasses.size(); i++) {
                   						OWLClass superClass = superClasses.get(i);
                   						//adds the super classes of the super class to ensure we find a class that contains the area
                   						for(OWLClassExpression superSuperExpression : superClass.getSuperClasses(ontology)) {
                   							String superSuperClassName = getClassExpression(superSuperExpression.toString().trim());
                   							OWLClass superSuperClass = dlQueryEngine.getClass(superSuperClassName);
                   							if(!superClasses.contains(superSuperClass)) {
                   								superClasses.add(superSuperClass);
                   							}
                   						}
                   						String superLines[] = getComments(superClass, ontology);
                   						if(superLines != null) {
                   							for(String line : superLines) {
                       							if(line.contains(area)) {
                       								String superName = getClassName(superClass);
                      								results.add(line + ": " 
                       										+superName + ", superclass of " + clsName);
                      								return results;
                       							}
                       						}
                  						}
                   					}
                           		}
                       			
                       			
                       			
                       			
                       		}
                       		
                    
                       	}
                    }
                }
               
            } catch (ParserException e) {
                System.out.println(e.getMessage());
            }
        }
        return results;
    }

    
    private static String[] getComments(OWLClass cls, OWLOntology ontology) {
    	String lines[] = null;
    	for(OWLAnnotation annot : cls.getAnnotations(ontology)) {
    		String comment = annot.getValue().toString();
    		//remove quotes
    		comment = comment.replace("\"", "");
    		lines = comment.split("\\r?\\n");
    	}
    	return lines;
    }
    
    private static String getClassName(OWLClass cls) {
    	String clsName = cls.toString().replace(EXTRA, "");
    	clsName = clsName.replace(">", "");
    	clsName = clsName.replace("_", " ");
    	return clsName;
    }
    
    private static String getClassExpression(String classExpression) {
    	String expression = classExpression.replace(EXTRA, "");
    	expression = expression.replace(">", "");
    	expression = WordUtils.capitalizeFully(expression,'_',' ');
    	return expression;
    }
    
    
}