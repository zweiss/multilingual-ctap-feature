package com.ctapweb.feature.util;

/**
 * Interface that allows to define common word categories that are shared between POS based AEs
 * @author zweiss
 *
 */
public abstract class WordCategories {

		protected String[] lexical; 
		protected String[] functional; 
		protected String[] noun; 
		protected String[] adjective; 
		protected String[] adverb; 
		protected String[] verb; 
		protected String[] lexicalVerb; 
		protected String[] pronouns; 
		
		public String[] getLexicalWords() {
			return lexical;
		}
		
		public String[] getNouns() {
			return noun;
		}
		
		public String[] getVerbs() {
			return verb;
		}
		public String[] getLexicalVerbs() {
			return lexicalVerb;
		}
		
		public String[] getAdjectives() {
			return adjective;
		}
		
		public String[] getAdverbs() {
			return adverb;
		}
		
		public String[] getFunctionalWords() {
			return functional;
		}
		
		public String[] getPronouns() {
			return pronouns;
		}
	
}
