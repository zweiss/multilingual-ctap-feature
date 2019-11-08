package com.ctapweb.feature.test.util;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;


/**
 * Helper class for modifying XML descriptors for the use of UIMA tests.
 * @author Nadezda Okinina
 */
public class DescriptorModifier {
	/*
	 * Reads an existing annotator descriptor (XML file) and makes some modifications in it:
	 * - sets the LanguageCode parameter to "IT"
	 * - deletes the typeSystemDescription
	 * 
	 * Parameters:
	 * - filePath: the path to the existing annotator descriptor (XML file)
	 * - outputFilePath: the path to the file that will be created and will contain the modified annotator descriptor
	 * - languageCode: language code : IT, DE or EN
	 * 
	 * Returns a file object containing the new XML annotator descriptor
	 */	
	public static File readXMLAnnotatorDescriptorAddLanguage (String filePath, String outputFilePath, String languageCode) throws Exception {
		//File fToReturn = new File(filePath);
		File fXmlFile = new File(filePath);
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document doc = dBuilder.parse(fXmlFile);
		
		try {
			doc.getDocumentElement().normalize();
			//Get the already existing node "analysisEngineMetaData"
			NodeList nList = doc.getElementsByTagName("analysisEngineMetaData");
			
			//Create a new node "configurationParameterSettings" with the indication of the Italian language in the LanguageCode parameter
			Element newConfParamSettingsNode = doc.createElement("configurationParameterSettings");
	        Element nameValuePairNode = doc.createElement("nameValuePair");
	        newConfParamSettingsNode.appendChild(nameValuePairNode);	        
	        Element nameNode = doc.createElement("name");
	        nameValuePairNode.appendChild(nameNode);
	        Text nametextNode = doc.createTextNode("LanguageCode");
	        nameNode.appendChild(nametextNode);	        
	        Element valueNode = doc.createElement("value");
	        nameValuePairNode.appendChild(valueNode);
	        Element stringNode = doc.createElement("string");
	        valueNode.appendChild(stringNode);
	        Text langTextNode = doc.createTextNode("IT");
	        stringNode.appendChild(langTextNode);
	        
	        // Loop through all possible "analysisEngineMetaData" nodes: in reality only one
	        int temp;
	        int t;
			for (temp = 0; temp < nList.getLength(); temp++) {
				Node nNode = nList.item(temp);						
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
					Element eElement = (Element) nNode;					
					NodeList childrenList = eElement.getChildNodes();
					//Loop through all the children of "analysisEngineMetaData"
					for (t = 0; t < childrenList.getLength(); t++) {
						Node childNode = childrenList.item(t);						
						if (childNode.getNodeName().equals("configurationParameterSettings") || childNode.getNodeName().equals("typeSystemDescription") ){
							eElement.removeChild(childNode);
						}
						eElement.appendChild(newConfParamSettingsNode);
					}
				}
			}

		    } catch (Exception e) {
			e.printStackTrace();
		    }
		//Write the new XML to a new annotator descriptor in the META-INF folder only for the use of the test function
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		File fToReturn = new File(outputFilePath);
		FileWriter writer = new FileWriter(fToReturn);
		StreamResult result = new StreamResult(writer);
		DOMSource source = new DOMSource(doc);
		transformer.transform(source, result);
		//Return the file object
		return fToReturn;
	}
	
	public static File readXMLAnnotatorDescriptorAddLanguageAddParamsFromHashModifyImports(String filePath, String outputFilePath, HashMap <String, ArrayList <String>> parametersValuesHashMap, ArrayList <String> locationsArrayList) throws Exception {
		//System.out.println("readXMLAnnotatorDescriptorAddLanguageAddParamsFromHashModifyImports");
		//System.out.println("HASH MAP DUMP: " + parametersValuesHashMap.toString());
		//System.out.println("locationsArrayList DUMP: ");
		//System.out.println(locationsArrayList.toString());
		//File fToReturn = new File(filePath);
		File fXmlFile = new File(filePath);
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document doc = dBuilder.parse(fXmlFile);
		
		try {
			doc.getDocumentElement().normalize();
			//Get the already existing node "analysisEngineMetaData"
			NodeList nList = doc.getElementsByTagName("analysisEngineMetaData");		
			//Node nNode = nList.item(0);
			//Create a new node "imports" with the full path to the file location
			Element importsNode = doc.createElement("imports");
			Element importNode;
			for (String location: locationsArrayList){
	        	importNode = doc.createElement("import");
	        	importNode.setAttribute("location", location);
	        	importsNode.appendChild(importNode);       
			}
			
	        int temp;
	        String type;
			String valueValue;
			Element nameValuePairNode2;
			Element nameNode2;
			Text nametextNode2;
			Element valueNode2;	        
	        Element stringNode2;
	        Text langTextNode2;
	        Node nNode;
	        for (temp = 0; temp < nList.getLength(); temp++) {
				nNode = nList.item(temp);
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
					Element eElement = (Element) nNode;					
					NodeList childrenList = eElement.getChildNodes();

					//Loop through all the children of "analysisEngineMetaData"
					for (int t = 0; t < childrenList.getLength(); t++) {
						Node childNode = childrenList.item(t);

						if (childNode.getNodeName().equals("configurationParameterSettings") ){
							//Loop through the parameters HashMap
							for(String valueName: parametersValuesHashMap.keySet()){
								//Get the name and value of the parameter
								ArrayList <String> typeValueArray = parametersValuesHashMap.get(valueName);
								type = typeValueArray.get(0);
								valueValue = typeValueArray.get(1);
								//Create the nameValuePair XML element with the name and value parameters inside
						        nameValuePairNode2 = doc.createElement("nameValuePair");	        	        
						        nameNode2 = doc.createElement("name");	        
						        nametextNode2 = doc.createTextNode(valueName);
						        nameNode2.appendChild(nametextNode2);	        
						        valueNode2 = doc.createElement("value");	        
						        stringNode2 = doc.createElement(type);
						        langTextNode2 = doc.createTextNode(valueValue);
						        stringNode2.appendChild(langTextNode2);
						        valueNode2.appendChild(stringNode2);
						        nameValuePairNode2.appendChild(nameNode2);
						        nameValuePairNode2.appendChild(valueNode2);
						        //Add the nameValuePair XML element to the configurationParameterSettings XML element
						        childNode.appendChild(nameValuePairNode2);
							}
						}else if (childNode.getNodeName().equals("typeSystemDescription") ){
							NodeList childrenTSDList = childNode.getChildNodes();
							//Loop through all the children of "typeSystemDescription"
							for (int i = 0; i < childrenTSDList.getLength(); i++) {
								Node childTSDNode = childrenTSDList.item(i);
								if (childTSDNode.getNodeName().equals("imports") ){
									childNode.removeChild(childTSDNode);
									
								}	
							}
							//Add the imports XML element to the typeSystemDescription XML element
							childNode.appendChild(importsNode);
						}
						
						
					}
				}
	        }

		    } catch (Exception e) {
			e.printStackTrace();
		    }
		//Write the new XML to a new annotator descriptor in the META-INF folder only for the use of the test function
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		File fToReturn = new File(outputFilePath);
		FileWriter writer = new FileWriter(fToReturn);
		StreamResult result = new StreamResult(writer);
		DOMSource source = new DOMSource(doc);
		transformer.transform(source, result);
		//Return the file object
		return fToReturn;
	}
	
	/*
	 * Reads an existing annotator descriptor (XML file) and makes some modifications in it:
	 * - sets the LanguageCode parameter to "IT"
	 * - deletes the typeSystemDescription
	 * 
	 * Parameters:
	 * - filePath: the path to the existing annotator descriptor (XML file)
	 * - outputFilePath: the path to the file that will be created and will contain the modified annotator descriptor
	 * - languageCode: language code : IT, DE or EN
	 * - unit: the measure unit: token, syllable or letter
	 * 
	 * Returns a file object containing the new XML annotator descriptor
	 */	
	public static File readXMLAnnotatorDescriptorAddLanguageAddUnitAddaeID (String filePath, String outputFilePath, String languageCode, String valueName, String valueValue, String aeID) throws Exception {
		//System.out.println("in readXMLAnnotatorDescriptorAddLanguageAddUnitAddaeID");
		//File fToReturn = new File(filePath);
		File fXmlFile = new File(filePath);
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document doc = dBuilder.parse(fXmlFile);
		
		try {
			doc.getDocumentElement().normalize();
			//Get the already existing node "analysisEngineMetaData"
			NodeList nList = doc.getElementsByTagName("analysisEngineMetaData");
			
			//Create a new node "configurationParameterSettings" with the indication of the Italian language in the LanguageCode parameter
			Element newConfParamSettingsNode = doc.createElement("configurationParameterSettings");

			//Set the language to Italian
	        Element nameValuePairNode = doc.createElement("nameValuePair");
	        	        
	        Element nameNode = doc.createElement("name");
	        nameValuePairNode.appendChild(nameNode);
	        Text nametextNode = doc.createTextNode("LanguageCode");
	        nameNode.appendChild(nametextNode);	        
	        Element valueNode = doc.createElement("value");
	        nameValuePairNode.appendChild(valueNode);
	        Element stringNode = doc.createElement("string");
	        valueNode.appendChild(stringNode);
	        Text langTextNode = doc.createTextNode(languageCode);
	        stringNode.appendChild(langTextNode);
	        
	        //Set the unit (token, syllable or letter)
	        Element nameValuePairNode2 = doc.createElement("nameValuePair");	        	        
	        Element nameNode2 = doc.createElement("name");	        
	        Text nametextNode2 = doc.createTextNode(valueName);
	        nameNode2.appendChild(nametextNode2);	        
	        Element valueNode2 = doc.createElement("value");	        
	        Element stringNode2 = doc.createElement("string");
	        Text langTextNode2 = doc.createTextNode(valueValue);
	        stringNode2.appendChild(langTextNode2);
	        valueNode2.appendChild(stringNode2);
	        nameValuePairNode2.appendChild(nameNode2);
	        nameValuePairNode2.appendChild(valueNode2);
	        
	        //Set the aeID 
	        Element nameValuePairNode3 = doc.createElement("nameValuePair");
	        Element nameNode3 = doc.createElement("name");
	        Text nametextNode3 = doc.createTextNode("aeID");
	        nameNode3.appendChild(nametextNode3);
	        Element valueNode3 = doc.createElement("value");
	        Element stringNode3 = doc.createElement("integer");
	        Text langTextNode3 = doc.createTextNode(aeID);
	        stringNode3.appendChild(langTextNode3);
	        valueNode3.appendChild(stringNode3);
	        nameValuePairNode3.appendChild(nameNode3);	        
	        nameValuePairNode3.appendChild(valueNode3);
	        
	        newConfParamSettingsNode.appendChild(nameValuePairNode);
	        newConfParamSettingsNode.appendChild(nameValuePairNode2);
	        newConfParamSettingsNode.appendChild(nameValuePairNode3);
	        
	        // Loop through all possible "analysisEngineMetaData" nodes: in reality only one
	        int temp;
	        int t;
			for (temp = 0; temp < nList.getLength(); temp++) {
				Node nNode = nList.item(temp);						
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
					Element eElement = (Element) nNode;					
					NodeList childrenList = eElement.getChildNodes();
					//Loop through all the children of "analysisEngineMetaData"
					for (t = 0; t < childrenList.getLength(); t++) {
						Node childNode = childrenList.item(t);						
						if (childNode.getNodeName().equals("configurationParameterSettings") || childNode.getNodeName().equals("typeSystemDescription") ){
							eElement.removeChild(childNode);
						}
						eElement.appendChild(newConfParamSettingsNode);
					}
				}
			}

		    } catch (Exception e) {
			e.printStackTrace();
		    }
		//Write the new XML to a new annotator descriptor in the META-INF folder only for the use of the test function
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		File fToReturn = new File(outputFilePath);
		FileWriter writer = new FileWriter(fToReturn);
		StreamResult result = new StreamResult(writer);
		DOMSource source = new DOMSource(doc);
		transformer.transform(source, result);
		//Return the file object
		return fToReturn;
	}
	
	/*
	 * Reads an existing annotator descriptor (XML file) and makes some modifications in it:
	 * - sets the LanguageCode parameter to "IT"
	 * - deletes the typeSystemDescription
	 * 
	 * Parameters:
	 * - filePath: the path to the existing annotator descriptor (XML file)
	 * - outputFilePath: the path to the file that will be created and will contain the modified annotator descriptor
	 * - languageCode: language code : IT, DE or EN
	 * 
	 * Returns a file object containing the new XML annotator descriptor
	 */	
	public static File readXMLAnnotatorDescriptorAddLanguageAddaeID (String filePath, String outputFilePath, String languageCode, String aeID) throws Exception {
		System.out.println("in readXMLAnnotatorDescriptorAddLanguageAddUnitAddaeID");
		//File fToReturn = new File(filePath);
		File fXmlFile = new File(filePath);
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document doc = dBuilder.parse(fXmlFile);
		
		try {
			doc.getDocumentElement().normalize();
			//Get the already existing node "analysisEngineMetaData"
			NodeList nList = doc.getElementsByTagName("analysisEngineMetaData");
			
			//Create a new node "configurationParameterSettings" with the indication of the Italian language in the LanguageCode parameter
			Element newConfParamSettingsNode = doc.createElement("configurationParameterSettings");

			//Set the language to Italian
	        Element nameValuePairNode = doc.createElement("nameValuePair");
	        	        
	        Element nameNode = doc.createElement("name");
	        nameValuePairNode.appendChild(nameNode);
	        Text nametextNode = doc.createTextNode("LanguageCode");
	        nameNode.appendChild(nametextNode);	        
	        Element valueNode = doc.createElement("value");
	        nameValuePairNode.appendChild(valueNode);
	        Element stringNode = doc.createElement("string");
	        valueNode.appendChild(stringNode);
	        Text langTextNode = doc.createTextNode(languageCode);
	        stringNode.appendChild(langTextNode);
	        	        
	        //Set the aeID 
	        Element nameValuePairNode3 = doc.createElement("nameValuePair");
	        Element nameNode3 = doc.createElement("name");
	        Text nametextNode3 = doc.createTextNode("aeID");
	        nameNode3.appendChild(nametextNode3);
	        Element valueNode3 = doc.createElement("value");
	        Element stringNode3 = doc.createElement("integer");
	        Text langTextNode3 = doc.createTextNode(aeID);
	        stringNode3.appendChild(langTextNode3);
	        valueNode3.appendChild(stringNode3);
	        nameValuePairNode3.appendChild(nameNode3);	        
	        nameValuePairNode3.appendChild(valueNode3);
	        
	        newConfParamSettingsNode.appendChild(nameValuePairNode);
	        newConfParamSettingsNode.appendChild(nameValuePairNode3);
	        
	        // Loop through all possible "analysisEngineMetaData" nodes: in reality only one
	        int temp;
	        int t;
			for (temp = 0; temp < nList.getLength(); temp++) {
				Node nNode = nList.item(temp);						
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
					Element eElement = (Element) nNode;					
					NodeList childrenList = eElement.getChildNodes();
					//Loop through all the children of "analysisEngineMetaData"
					for (t = 0; t < childrenList.getLength(); t++) {
						Node childNode = childrenList.item(t);						
						if (childNode.getNodeName().equals("configurationParameterSettings") || childNode.getNodeName().equals("typeSystemDescription") ){
							eElement.removeChild(childNode);
						}
						eElement.appendChild(newConfParamSettingsNode);
					}
				}
			}

		    } catch (Exception e) {
			e.printStackTrace();
		    }
		//Write the new XML to a new annotator descriptor in the META-INF folder only for the use of the test function
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		File fToReturn = new File(outputFilePath);
		FileWriter writer = new FileWriter(fToReturn);
		StreamResult result = new StreamResult(writer);
		DOMSource source = new DOMSource(doc);
		transformer.transform(source, result);
		//Return the file object
		return fToReturn;
	}
	
	/*
	 * Reads an existing type system descriptor (XML file) and modifies its imports section
	 * by providing the new location of the imported file.
	 * Writes the new type system descriptor to the specified location.
	 * Parameters:
	 * - filePath: the path to the existing type system descriptor (XML file)
	 * - outputFilePath: the path to the file that will be created and will contain the modified type system descriptor
	 * - location: the location of the file  that has to be imported in the imports section of the newly created type system descriptor
	 */
	public static void readXMLTypeDescriptorModifyImports (String filePath, String outputFilePath, ArrayList<String> locationsArrayList) throws Exception {
		//File fToReturn = new File(filePath);
		File fXmlFile = new File(filePath);
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document doc = dBuilder.parse(fXmlFile);
		
		try {
			doc.getDocumentElement().normalize();
			//Get the already existing node "typeSystemDescription"
			NodeList nList = doc.getElementsByTagName("typeSystemDescription");
			
			//Create a new node "imports" with the full path to the file location
			Element importsNode = doc.createElement("imports");
			Element importNode;
			for (String location: locationsArrayList){
	        	importNode = doc.createElement("import");
	        	importNode.setAttribute("location", location);
	        	importsNode.appendChild(importNode);       
			}
			
	        // Loop through all possible "typeSystemDescription" nodes: in reality only one
	        int temp;
	        int t;
			for (temp = 0; temp < nList.getLength(); temp++) {
				Node nNode = nList.item(temp);						
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
					Element eElement = (Element) nNode;					
					NodeList childrenList = eElement.getChildNodes();
					//Loop through all the children of "analysisEngineMetaData"
					for (t = 0; t < childrenList.getLength(); t++) {
						Node childNode = childrenList.item(t);						
						if (childNode.getNodeName().equals("imports") ){
							eElement.removeChild(childNode);
						}
						eElement.appendChild(importsNode);
					}
				}
			}

		    } catch (Exception e) {
			e.printStackTrace();
		    }
		//Write the new XML to a new annotator descriptor in the META-INF folder only for the use of the test function
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		File fToReturn = new File(outputFilePath);
		FileWriter writer = new FileWriter(fToReturn);
		StreamResult result = new StreamResult(writer);
		DOMSource source = new DOMSource(doc);
		transformer.transform(source, result);
		
	}
}
