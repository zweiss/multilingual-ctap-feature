package com.ctapweb.feature.util;

/**
 * Definition of word categories for English
 * @author zweiss
 *
 */
public class EnglishWordCategories extends WordCategories {
	public EnglishWordCategories() {
		lexical = new String[]{
				"JJ","JJR","JJS","RB","RBR","RBS","WRB","VB","VBD",
				"VBG","VBN","VBP","VBZ","NN","NNS","NNP","NNPS"
		};
		adjective = new String[]{"JJ", "JJR", "JJS"};
		noun = new String[]{ "NN", "NNS", "NNP", "NNPS" };
		adverb = new String[]{"RB", "RBR", "RBS", "WRB"};
		verb = new String[]{"VB", "VBD", "VBG", "VBN", "VBP", "VBZ"};
		finVerb = new String[]{"VBD", "VBP", "VBZ"};
		lexicalVerb = new String[]{"VB", "VBD", "VBG", "VBN", "VBP", "VBZ"};
		functional = new String[] {
				"CC","IN","PDT","DT","WDT","PRP","PRP$","WP","WP$","CD",
				"EX","FW","LS","MD","POS","RP","SYM","TO","UH"
		}; 
		pronouns = new String[] {"PRP", "PRP$", "WP", "WP$"};
	}

	// TODO check if this works
	public boolean isRelativePronoun(String form, String tag) {
		boolean tagMatches = tag.equals("WP") || tag.equals("WDT") || tag.equals("IN");
		boolean formMatches = form.equalsIgnoreCase("that") || form.equalsIgnoreCase("who"); 
		return tagMatches && formMatches;
	}

	// TODO refine, currently also includes subordinating conjunctions
	public boolean isPreposition(String tag) {
		return tag.equals("IN");
	}
}

