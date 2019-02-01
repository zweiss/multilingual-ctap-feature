package com.ctapweb.feature.util;

/**
 * 
 * @author zweiss
 *
 */
public abstract class DependencyLabelCategories {
	protected String[] subordinationgConjunctions;
	protected String[] conjuncts;
	protected String[] arguments;
	protected String[] relativeClauses;

	public String[] getSubordinatingConjunctions() {
		return subordinationgConjunctions;
	}

	public boolean isSubordinatingConjunction(String depLabel) {
		return arrayContainsTag(depLabel, this.subordinationgConjunctions);
	}

	public boolean isConjunct(String depLabel) {
		return arrayContainsTag(depLabel, this.conjuncts);
	}

	public boolean isArgument(String depLabel) {
		return arrayContainsTag(depLabel, this.arguments);
	}

	public boolean isRelativeClause(String depLabel) {
		return arrayContainsTag(depLabel, this.relativeClauses);
	}

	private boolean arrayContainsTag(String givenLabel, String[] labelArray) {
		for (String currentLabel : labelArray) {
			if (givenLabel.equals(currentLabel)) {
				return true;
			}
		}
		return false;
	}
}