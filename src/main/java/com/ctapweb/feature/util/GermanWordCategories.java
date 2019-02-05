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
		noun = new String[]{"NN","NE"}; 
		adverb = new String[]{"ADV"};
		verb = new String[]{
				"VVFIN", "VVIMP", "VVINF","VVIZU",
				"VMFIN","VMIMP","VMINF","VMIZU","VMPP",
				"VAFIN","VAIMP","VAINF","VAIZU","VAPP"
		};
		finVerb = new String[]{
				"VVFIN", "VVIMP",
				"VMFIN","VMIMP",
				"VAFIN","VAIMP"
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

	@Override
	public boolean isVerb(String tag) {
		return tag.startsWith("V");
	}
	
	@Override
	public boolean isNoun(String tag) {
		return tag.startsWith("N");
	}
	
	@Override
	public boolean isFiniteVerb(String tag) {
		return tag.endsWith("FIN")||tag.endsWith("IMP");
	}
	@Override
	public boolean isRelativePronoun(String form, String tag) {
		return tag.startsWith("PREL");
	}
	@Override
	public boolean isPreposition(String tag) {
		return tag.startsWith("AP");
	}

}
