package com.ctapweb.feature.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import edu.stanford.nlp.ling.Label;
import edu.stanford.nlp.ling.LabeledWord;
import edu.stanford.nlp.ling.Word;
import edu.stanford.nlp.trees.LabeledScoredTreeNode;
import edu.stanford.nlp.trees.Tree;

/** reads the Tint syntactic parser result (in its READABLE format) and transforms it into a tree structure (edu.stanford.nlp.trees.LabeledScoredTreeNode)
 * Example of the Tint string that is taken as input:
 * 
 * Sentence #1 (4 tokens):
 * Lo hanno dichiarato colpevole
 * 
 * Tokens:
 * [Text=Lo CharacterOffsetBegin=0 CharacterOffsetEnd=2 PartOfSpeech=PC Lemma=lo]
 * [Text=hanno CharacterOffsetBegin=3 CharacterOffsetEnd=8 PartOfSpeech=VA Lemma=avere]
 * [Text=dichiarato CharacterOffsetBegin=9 CharacterOffsetEnd=19 PartOfSpeech=V Lemma=dichiarare]
 * [Text=colpevole CharacterOffsetBegin=20 CharacterOffsetEnd=29 PartOfSpeech=A Lemma=colpevole]
 * 
 * Dependency Parse (enhanced plus plus dependencies):
 * root(ROOT-0, dichiarato-3)
 * dobj(dichiarato-3, Lo-1)
 * aux(dichiarato-3, hanno-2)
 * amod(dichiarato-3, colpevole-4)
 * 
 * Example of a tree printed out with the function treeToStringSyntRelWord:
 * (ROOT ((root dichiarato) (dobj Lo) (aux hanno) (amod colpevole)))
 * 
 * In CTAP this class is used to calculate the depth of the syntactic tree (MeanParseTreeDepth).
 * 
 */
public class TintReadableStringTransformer {
	private HashMap <Integer, LabeledScoredTreeNode> indexToOneElementTreeHashMap;
	private HashMap <Integer, String> indexToStringHashMap;
	private HashMap <Integer, Integer> childToParentIndexHashMap;
	private HashMap <Integer, String> indexToSyntrelHashMap;
	
	/** reads the Tint syntactic parser result (in its READABLE format) and transforms it into a tree structure (edu.stanford.nlp.trees.LabeledScoredTreeNode)
	 * Example of the Tint string that is taken as input:
	 * 
	 * Sentence #1 (4 tokens):
	 * Lo hanno dichiarato colpevole
	 * 
	 * Tokens:
	 * [Text=Lo CharacterOffsetBegin=0 CharacterOffsetEnd=2 PartOfSpeech=PC Lemma=lo]
	 * [Text=hanno CharacterOffsetBegin=3 CharacterOffsetEnd=8 PartOfSpeech=VA Lemma=avere]
	 * [Text=dichiarato CharacterOffsetBegin=9 CharacterOffsetEnd=19 PartOfSpeech=V Lemma=dichiarare]
	 * [Text=colpevole CharacterOffsetBegin=20 CharacterOffsetEnd=29 PartOfSpeech=A Lemma=colpevole]
	 * 
	 * Dependency Parse (enhanced plus plus dependencies):
	 * root(ROOT-0, dichiarato-3)
	 * dobj(dichiarato-3, Lo-1)
	 * aux(dichiarato-3, hanno-2)
	 * amod(dichiarato-3, colpevole-4)
	 * 
	 * The function  works with the Dependency Parse
	 * 
	 * @param String annotation: the Tint syntactic parser result in READABLE format
	 */
	public LabeledScoredTreeNode textToTree(String annotation){
    	LabeledScoredTreeNode labeledScoredTreeNode = new LabeledScoredTreeNode();
    	//Take only the syntactic analysis out of the Tint READABLE output
    	String substring = annotation.substring(annotation.lastIndexOf("Dependency Parse (enhanced plus plus dependencies):") + 52);
		
		indexToStringHashMap = new HashMap <Integer, String> (); // Will contain word indexes (as keys) and the corresponding word strings (as values)
		childToParentIndexHashMap = new HashMap <Integer, Integer> (); // Will contain child syntactic element indexes (as keys) and their parents indexes (as values)
		indexToOneElementTreeHashMap = new HashMap <Integer, LabeledScoredTreeNode> (); // Will contain parent indexes (as keys) and trees with the same index on the top (values). Those trees will be enriched with child (and grandchild ...) elements in the function createTreeFromHashes
		indexToSyntrelHashMap = new HashMap <Integer, String> (); // Will contain word indexes (as keys) and the corresponding syntactic relations to their superiors (as values)
		
		// Pattern that finds the 5 elements of the Tint READABLE format string: det(distinzioni-3, le-2)
		Pattern pattern = Pattern.compile("([^\\(]+)\\(([^\\-]+)\\-([0-9]+), ([^\\-]+)\\-([0-9]+)\\)\n");
        Matcher matcher = pattern.matcher(substring);
        // check all occurences
        while (matcher.find()) {
        	String syntRel = matcher.group(1);
            String parent = matcher.group(2);
            Integer parentIndex = Integer.parseInt(matcher.group(3));
            String child = matcher.group(4);
            Integer childIndex = Integer.parseInt(matcher.group(5));
            
            indexToStringHashMap.putIfAbsent(parentIndex, parent);
            indexToStringHashMap.putIfAbsent(childIndex, child);
            indexToSyntrelHashMap.putIfAbsent(childIndex, syntRel);
            childToParentIndexHashMap.putIfAbsent(childIndex, parentIndex);
            
            //Create one element trees only for parents, not for children
            Label indexWord = new Word(parentIndex.toString());
            Label stringWord = new Word(parent);
            Label labelParent = new LabeledWord(indexWord, stringWord);
            LabeledScoredTreeNode oneElementTreeParent = new LabeledScoredTreeNode(labelParent);
            indexToOneElementTreeHashMap.put(parentIndex, oneElementTreeParent);
            
        }
         
        Set<Integer> allChildren = childToParentIndexHashMap.keySet();
                  
        createTreeFromHashes(allChildren);
        
        LabeledScoredTreeNode bigTree = indexToOneElementTreeHashMap.get(new Integer(0));
        
    	return bigTree;
    }
	
	/** creates a tree out of HashMaps containing information about the syntactic parsing result of a sentence by Tint.
	 * @param Set<Integer> allChildren : a set of indices of all the elements that have superior elements i the syntactic structure of the tree
	 */
	private void createTreeFromHashes (Set<Integer> allChildren ){
    	// loop through all the elements that are under other elements in the tree
    	for (Integer childIndex: allChildren){           
            //Get the tree depending from this element
            LabeledScoredTreeNode childsTree = indexToOneElementTreeHashMap.get(childIndex);
            
            if (childsTree == null){           	
            	Label indexWord = new Word(childIndex.toString());
            	String child = indexToStringHashMap.get(childIndex);
                Label stringWord = new Word(child);
                Label labelChild = new LabeledWord(indexWord, stringWord);
                childsTree = new LabeledScoredTreeNode(labelChild);
            }
            
            //Get the index of the parent of this element in the syntactic tree structure
            Integer parentIndex = childToParentIndexHashMap.get(childIndex);
            
            if (parentIndex == null){
            	parentIndex = new Integer(0);
            }
            
            //Get the tree going down (depending) on this element
            LabeledScoredTreeNode parentsTree = indexToOneElementTreeHashMap.get(parentIndex);
            Tree[] alreadyChildren = parentsTree.children();
            ArrayList<Tree> listAlreadyChildren = new ArrayList<Tree>();
            //Put all the elements that already depend on this one into a list
            for (Tree tr: alreadyChildren){
            	listAlreadyChildren.add(tr);
            }
            //Append the new child to the list
            listAlreadyChildren.add(childsTree);
            //Attach the children to the parent's tree
            parentsTree.setChildren(listAlreadyChildren);
            //Insert the tree into the HashMap containing word indexes and the corresponding  dependent trees
            indexToOneElementTreeHashMap.put(parentIndex, parentsTree); 
        }
        return;
    }
	
	/** transforms a UD parse tree into a String by replacing each words index by its syntactic relation to its superior and its word, united in parenthesis
	 * Ex: (det il)
	 * @param the tree to transform into a string
	 */
	public String treeToStringSyntRelWord (LabeledScoredTreeNode tree){
    	String replacement;
    	Integer elementIndex;
    	String word;
    	String syntRel;
    	
    	String treeString = tree.flatten().toString();
    	Pattern pattern = Pattern.compile("([0-9]+)");
    	StringBuffer sb = new StringBuffer(treeString.length());
    	
        Matcher matcher = pattern.matcher(treeString);
        // replaces word indexes by their syntactic relations to their superiors and their word strings
        while (matcher.find()) {
        	elementIndex = Integer.parseInt(matcher.group(1));
        	word = indexToStringHashMap.get(elementIndex);
            syntRel = indexToSyntrelHashMap.get(elementIndex);
            
            if (syntRel != null){
            	replacement = "(" + syntRel + " " + word + ")";
            }else{
            	replacement = word;
            }
            matcher.appendReplacement(sb, Matcher.quoteReplacement(replacement));
        }
        matcher.appendTail(sb);
        
        return sb.toString();
        
    }
	
	/** transforms a UD parse tree into a String by replacing each words index by its syntactic relation to its superior
	 * Ex: det
	 * @param the tree to transform into a string
	 */
	public String treeToStringSyntRel (LabeledScoredTreeNode tree){
    	String replacement;
    	Integer elementIndex;
    	String word;
    	String syntRel;
    	
    	String treeString = tree.flatten().toString();
    	Pattern pattern = Pattern.compile("([0-9]+)");
    	StringBuffer sb = new StringBuffer(treeString.length());
    	
        Matcher matcher = pattern.matcher(treeString);
        // replaces word indexes by their syntactic relations to their superiors
        while (matcher.find()) {
        	elementIndex = Integer.parseInt(matcher.group(1));
            syntRel = indexToSyntrelHashMap.get(elementIndex);
            
            if (syntRel != null){
            	replacement = syntRel;
            }else{
            	replacement = Integer.toString(elementIndex);
            }
            matcher.appendReplacement(sb, Matcher.quoteReplacement(replacement));
        }
        matcher.appendTail(sb);
        
        return sb.toString();   
    }
}
