package com.ctapweb.feature.util;

/**
 * Definition of word categories for German
 * @author zweiss
 *
 */
public class GermanWordCategories extends WordCategories {
	public GermanWordCategories() {
		lexical = new String[]{ 
				"ADJA","ADJD","ADV","NN","NE","VVFIN","VVIMP","VVINF","VVIZU",
				"VVPP","VMFIN","VMIMP","VMINF","VMIZU","VMPP","FM","XY"
		};
		adjective = new String[]{"ADJA","ADJD"};
		noun = new String[]{"NN","NE"};  // TODO finish implementation of functional words
		adverb = new String[]{"ADV"};
		verb = new String[]{
				"VVFIN", "VVIMP", "VVINF","VVIZU",
				"VMFIN","VMIMP","VMINF","VMIZU","VMPP",
				"VAFIN","VAIMP","VAINF","VAIZU","VAPP"
		};
		lexicalVerb = new String[]{
				"VVFIN", "VVIMP", "VVINF","VVIZU",
				"VMFIN","VMIMP","VMINF","VMIZU","VMPP"
		};
		functional = new String[] { 
				"CARD","ITJ","KOUI","KOUS","KON","KOKOM","PDS","PDAT","PIS","PIAT","PIDAT","PPER",
				"PPOSS","PPOSAT","PRELS","PRELSAT","PRF","PWS","PWAT","PWAV","PAV","PTKZU","PTKNEG",
				"PTKVZ","PTKANT","PTKA","VAFIN","VAIMP","VAINF","VAIZU","VAPP","TRUNC"
		}; 
		pronouns = new String[] {
				"PDS","PDAT","PIS","PIAT","PIDAT","PPER","PPOSS","PPOSAT",
				"PRELS","PRELAT","PRF", "PWS","PWSAT","PWAV","PAV"};
	}
}
