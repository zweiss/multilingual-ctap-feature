package com.ctapweb.feature.util;

/**
 * Definition of word categories for Italian
 * @author nokinina
 *
 */
public class ItalianWordCategories extends WordCategories {
	public ItalianWordCategories() {
		// I made the choice of not including modal verbs into the lexical parts of speech. In reality they can be both lexical and functional, depending on the use.
		lexical = new String[]{ 
				"As","Ap","An","SA","SP","Ss","Sp","Sn", "SWs","SWp","SWn", "NOs", "NOp", "NOn","B","Vip","Vip3","Vii","Vii3","Vis","Vis3","Vif","Vif3","Vcp","Vcp3","Vci","Vci3","Vdp","Vdp3","Vg","Vp","Vf","Vm"
		};
		
		adjective = new String[]{"As","Ap","An","APs","APp","APn"};
		article = new String[]{"RD", "RI"};
		noun = new String[]{"SP","Ss","Sp","Sn","SWs","SWp","SWn"}; 
		adverb = new String[]{"B","BN"};
		verb = new String[]{
				"Vip","Vip3","Vii","Vii3","Vis","Vis3","Vif","Vif3","Vcp","Vcp3","Vci","Vci3","Vdp","Vdp3","Vg","Vp","Vf","Vm","VMip","VMip3","VMii","VMii3","VMis","VMis3","VMif","VMif3","VMcp","VMcp3","VMci","VMci3","VMdp","VMdp3","VMg","VMp","VMf","VMm", "VAip","VA","VAip3", "VAii", "VAii3", "VAis", "Vis3", "VAif", "VAif3", "VAcp", "VAcp3", "VAci", "VAci3", "VAdp", "VAdp3", "VAg", "VAp", "VAf", "VAm" 
		};
		
		auxiliary = new String[]{"VAip","VAip3", "VAii", "VAii3", "VAis", "Vis3", "VAif", "VAif3", "VAcp", "VAcp3", "VAci", "VAci3", "VAdp", "VAdp3", "VAg", "VAp", "VAf", "VAm"};
		
		finVerb = new String[]{
				"Vip","Vip3","Vii","Vii3","Vis","Vis3","Vif","Vif3","Vcp","Vcp3","Vci","Vci3","Vdp","Vdp3","VAip","VAip3","VAii","VAii3","VAis","VAis3","VAif","VAif3","VAcp","VAcp3","VAci","VAci3","VAdp","VAdp3","VMip","VMip3","VMii","VMii3","VMis","VMis3","VMif","VMif3","VMcp","VMcp3","VMci","VMci3","VMdp","VMdp3"
		};
		lexicalVerb = new String[]{
				"Vip","Vip3","Vii","Vii3","Vis","Vis3","Vif","Vif3","Vcp","Vcp3","Vci","Vci3","Vdp","Vdp3","Vg","Vp","Vf","Vm"
		};
		functional = new String[] { "BN","CC","CS","DD","DE","DI","DQ","DR","E","EA","RD","RI","I","N","PC","PD","PE","PI","PP","PQ","PR","T","APs","APp","APn","VAip","VAip3", "VAii", "VAii3", "VAis", "Vis3", "VAif", "VAif3", "VAcp", "VAcp3", "VAci", "VAci3", "VAdp", "VAdp3", "VAg", "VAp", "VAf", "VAm", "VMip", "VMip3", "VMii", "VMii3", "VMis", "VMis3", "VMif", "VMif3", "VMcp", "VMcp3", "VMci", "VMci3", "VMdp", "VMdp3", "VMg", "VMp", "VMf", "VMm"
		}; 
		pronouns = new String[] {
				"PC","PD","PE","PI","PP","PQ","PR"};
	}

	@Override
	public boolean isVerb(String tag) {
		return tag.startsWith("V");
	}
	
	@Override
	public boolean isNoun(String tag) {
		return tag.startsWith("S");
	}
	
	@Override
	public boolean isFiniteVerb(String tag) {
		finVerb = new String[]{
				"Vip","Vip3","Vii","Vii3","Vis","Vis3","Vif","Vif3","Vcp","Vcp3","Vci","Vci3","Vdp","Vdp3"
		};
		//return finVerb.toString().matches(".*\\b" + tag + "\\b.*");
		
		for(String s : finVerb)
		    if(s.equals(tag)) return true;
		return false;
	}
	
	@Override
	public boolean isRelativePronoun(String form, String tag) {
		return tag.startsWith("PR");
	}
	@Override
	public boolean isPreposition(String tag) {
		return tag.startsWith("E");
	}

}
