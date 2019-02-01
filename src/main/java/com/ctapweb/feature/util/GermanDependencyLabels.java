package com.ctapweb.feature.util;

/**
 * 
 * @author zweiss
 *
 */
public class GermanDependencyLabels extends DependencyLabelCategories {
	public GermanDependencyLabels() {
		subordinationgConjunctions = new String[] {"KOUS", "KOUI", "KON"};
		conjuncts = new String[] {"CJ"};
		arguments = new String[] {"SB", "OC", "OP", "OA", "OA2", "OG"};
		relativeClauses = new String[] {"RC"};
	}
}