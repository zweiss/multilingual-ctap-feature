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
		lexicalVerb = new String[]{"VB", "VBD", "VBG", "VBN", "VBP", "VBZ"};
		functional = new String[] {
				"CC","IN","PDT","DT","WDT","PRP","PRP$","WP","WP$","CD",
				"EX","FW","LS","MD","POS","RP","SYM","TO","UH"
		}; 
		pronouns = new String[] {"PRP", "PRP$", "WP", "WP$"};
	}
}

