package com.ctapweb.feature.util;

import java.util.ArrayList;
import java.io.Serializable;
import is2.data.SentenceData09;

/**
 * Representation of dependency parses based on Mate Tools' SentenceData09
 * but supporting a series of operations to identify certain dependents.
 * Requires implementation of language specific WordCategory and DependencyLabelCategories.
 * Defaults to German.
 * 
 * @author zweiss
 *
 */
public class DependencyTree implements Serializable {

		public String[] id;
		public String[] forms;
		public String[] plemmas;
		public int[] pheads;
		public String[] plabels;	
		public String[] ppos;
		public String[] pfeats;

//	WordCategories posMapping;
//	DependencyLabelCategories labelMapping;

	public DependencyTree() { }

	public DependencyTree(SentenceData09 dependencyAnnotation) {
		this.id = dependencyAnnotation.id;
		this.forms= dependencyAnnotation.forms;
		this.plemmas = dependencyAnnotation.plemmas;
		this.ppos = dependencyAnnotation.ppos;
		this.pheads = dependencyAnnotation.pheads;
		this.pfeats = dependencyAnnotation.pfeats;
		this.plabels = dependencyAnnotation.plabels;
	}

//	public DependencyTree(SentenceData09 dependencyAnnotation, String lCode) {
//		this.id = dependencyAnnotation.id;
//		this.forms= dependencyAnnotation.forms;
//		this.plemmas = dependencyAnnotation.plemmas;
//		this.ppos = dependencyAnnotation.ppos;
//		this.pheads = dependencyAnnotation.pheads;
//		this.pfeats = dependencyAnnotation.pfeats;
//		this.plabels = dependencyAnnotation.plabels;
//
////		switch (lCode) {
////		case SupportedLanguages.GERMAN:
////			labelMapping = new GermanDependencyLabels();
////			posMapping = new GermanWordCategories();
////			break;
////			// add new languages here
////		default:  // good default? better than null
////			labelMapping = new GermanDependencyLabels();
////			posMapping = new GermanWordCategories();
////			break;
////		}
//	}

	/**
	 * Reads a DependencyTree object from a conllx formated string
	 * @param conllFormatTree tree to be instantiated
	 * @param lCode language code (DE, EN, ...), cf. SupportedLanguages class
	 * @return DependencyTree object
	 */
	public static DependencyTree valueOf(String conllFormatTree) {
		DependencyTree rval = new DependencyTree();
		
		// set language dependent information
//		DependencyLabelCategories labelMapping;
//		WordCategories posMapping;
//		switch (lCode) {
//		case SupportedLanguages.GERMAN:
//			labelMapping = new GermanDependencyLabels();
//			posMapping = new GermanWordCategories();
//			break;
//			// add new languages here
//		default:  // good default? better than null
//			labelMapping = new GermanDependencyLabels();
//			posMapping = new GermanWordCategories();
//			break;
//		}
//		rval.setDependencyLabelCategories(labelMapping);
//		rval.setWordCategories(posMapping);

		// extract the other information from the given dependency parse
		String[] wordArray = conllFormatTree.trim().split(System.getProperty("line.separator"));
		String[] id = new String[wordArray.length];
		String[] forms = new String[wordArray.length];
		String[] plemmas = new String[wordArray.length];
		String[] ppos = new String[wordArray.length];
		String[] pfeats = new String[wordArray.length];
		int[] pheads = new int[wordArray.length];
		String[] plabels = new String[wordArray.length];
		for (int i = 0; i < wordArray.length; i++) {
			String[] wordSplit = wordArray[i].trim().split("\t");
			id[i] = wordSplit[0];
			forms[i] = wordSplit[1];
			plemmas[i] = wordSplit[2];
			ppos[i] = wordSplit[4];
			pfeats[i] = wordSplit[5];
			pheads[i] = Integer.valueOf(wordSplit[8]);
			plabels[i] = wordSplit[9];
		}
		rval.setId(id);
		rval.setForms(forms);
		rval.setLemmas(plemmas);
		rval.setPOS(ppos);
		rval.setFeats(pfeats);
		rval.setHeads(pheads);
		rval.setLabels(plabels);
		return rval;
	}

	/*
	 * getter and setter methods
	 */
	
	public String[] getId() {
		return id;
	}

	public void setId(String[] id) {
		this.id = id;
	}

	public String[] getForms() {
		return forms;
	}

	public void setForms(String[] forms) {
		this.forms = forms;
	}

	public String[] getLemmas() {
		return plemmas;
	}

	public void setLemmas(String[] plemmas) {
		this.plemmas = plemmas;
	}

	public int[] getHeads() {
		return pheads;
	}

	public void setHeads(int[] pheads) {
		this.pheads = pheads;
	}

	public String[] getLabels() {
		return plabels;
	}

	public void setLabels(String[] plabels) {
		this.plabels = plabels;
	}

	public String[] getPOS() {
		return ppos;
	}

	public void setPOS(String[] ppos) {
		this.ppos = ppos;
	}

	public String[] getFeats() {
		return pfeats;
	}

	public void setFeats(String[] pfeats) {
		this.pfeats = pfeats;
	}

//	public WordCategories getWordCategories() {
//		return posMapping;
//	}
//
//	public DependencyLabelCategories getDependencyLabelCategories() {
//		return labelMapping;
//	}

//	public void setWordCategories(WordCategories posMapping) {
//		this.posMapping = posMapping;
//	}
//	public void setDependencyLabelCategories(DependencyLabelCategories labelMapping) {
//		this.labelMapping = labelMapping;
//	}

	/*
	 * dependency tree operation methods
	 */
	
	/**
	 * Get IDs of all dependents of a head
	 * @param headID
	 * @return list of IDs of dependents
	 */
	public  ArrayList<Integer> getDependents(int headID) {
		ArrayList<Integer> dependents = new ArrayList<Integer>();
		for (int wID = 0; wID < this.forms.length; wID++ ) {
			if (this.pheads[wID] == headID) {
				dependents.add(wID);
			}
		}
		return dependents;
	}

	/**
	 * Get all verbal dependents of a head
	 * @param headID
	 * @return list of IDs of verbal dependents
	 */
	public ArrayList<Integer> getVerbalDependents(int headID, WordCategories posMapping) {
		ArrayList<Integer> dependents = new ArrayList<Integer>();
		for (int wID = 0; wID < this.forms.length; wID++ ) {
			if (this.pheads[wID] == headID && posMapping.isVerb(this.ppos[wID])) {
				dependents.add(wID);
			}
		}
		return dependents;
	}

	/**
	 * Find the first verbal child of a head
	 * @param headID
	 * @return the index of the child
	 */
	public int findFirstVerbalChild(int headID, WordCategories posMapping, DependencyLabelCategories labelMapping) {
		for (int curWordID = 0; curWordID < this.pheads.length; curWordID++) {
			if (curWordID != headID && this.pheads[curWordID] == headID && posMapping.isVerb(this.ppos[curWordID])){
				return curWordID;
			} else if (curWordID > headID && this.ppos[curWordID] != null && labelMapping.isSubordinatingConjunction(this.ppos[curWordID])) {
				return -1;
			}
		}
		return -1;
	}

	/**
	 * Find the first verbal children with depth first search
	 * TODO Never used, eliminate?
	 * @param head
	 * @param vcs
	 */
	public void findVerbalChildrenDepthFirst(int head,  ArrayList<Integer> vcs, WordCategories posMapping, DependencyLabelCategories labelMapping) {
		while (this.findFirstVerbalChild(head, posMapping, labelMapping) != -1) {
			int nextChild = findFirstVerbalChild(head, posMapping, labelMapping);
			vcs.add(nextChild);
			head = nextChild;
		}
	}

	/*
	 * printing method
	 */
	
	/**
	 * Returns tree in proper CoNLL X format
	 * I.e. id, form, lemma, cpostag, postag, feats, head, deprel, phead, pdeprel
	 * See http://ilk.uvt.nl/conll/#dataformat
	 * @return tree in proper CoNLL X format
	 */
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < this.forms.length; i++) {
			sb.append(i); 
			sb.append("\t");
			sb.append(this.forms[i]);
			sb.append("\t");
			sb.append(this.plemmas[i]);
			sb.append("\t");
			sb.append(this.ppos[i]);
			sb.append("\t");
			sb.append(this.ppos[i]);
			sb.append("\t");
			sb.append(this.pfeats[i]);
			sb.append("\t");
			sb.append(this.pheads[i]);
			sb.append("\t");
			sb.append(this.plabels[i]);
			sb.append("\t");
			sb.append(this.pheads[i]);
			sb.append("\t");
			sb.append(this.plabels[i]);
			sb.append(System.getProperty("line.separator"));
		}
		return sb.toString();
	}

	



}
