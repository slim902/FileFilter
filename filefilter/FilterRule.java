package ca.softwarebyslim.filefilter;

import java.util.HashMap;

/**
 * @author slim90
 * FilterRule object will hold an individual rule
 * 	- a file type array that will have an entry for each file type this rule applies to
 *  - a destination path that will be the landing place for the specified file types
 */
public class FilterRule {
	
	static boolean DEBUG_MODE = false;
 
	private String ruleFileType;
	private String ruleDestinationPath;
	private String[] ruleAcceptedFileTypes;
	public static final HashMap<String, String[]> fileTypeMap;   // Maps our 'types' to actual associated file extensions
	
	static {
	// HashMap where keys are the 'fileTypes' and values are String arrays of that fileTypes accepted file extensions	
	fileTypeMap = new HashMap<String, String[]>();
	
	fileTypeMap.put("Music", new String[] {"mp3"});
	fileTypeMap.put("Pictures", new String[]{"jpeg", "png"});
	fileTypeMap.put("Video", new String[]{"mp4", "avi"});
	fileTypeMap.put("Office", new String[]{"doc", "pdf", "ppt", "txt"});
	fileTypeMap.put("Folders", new String[]{"folder"});
	}

	/*
	 * Constructor take two Strings: a 'type' - the type of files the rule applies to
	 * and a 'destinationPath' - where the filtered files will be moved to 
	 */
	public FilterRule(String fileType, String destinationPath) {
		ruleFileType = fileType;
		ruleDestinationPath = destinationPath;
		ruleAcceptedFileTypes = FilterRule.fileTypeMap.get(ruleFileType);
		
		if(DEBUG_MODE) {
			getRulePrintout();
		}
		
	}
	
	public String getRuleFileType() {
		return ruleFileType;
	}

	public String getRuleDestinationPath() {
		return ruleDestinationPath;
	}
	
	public String[] getRuleAcceptedFileTypes() {
		return ruleAcceptedFileTypes;
	}
	
	public void getRulePrintout() {
		System.out.println("Rule Creation Info: ");
		System.out.println("Rule File Type: " + ruleFileType);
		System.out.println("Rule Destination Path: " + ruleDestinationPath);
	}
	
	public String acceptedFileTypesToString(String[] typesArray) {
		String typeString = "";
		for(int i=0; i<typesArray.length; i++) {
			if(i<typesArray.length -1){
				typeString += typesArray[i] + " ";
			}else {
				typeString += typesArray[i];
			}
		}
		return typeString;
	}
}
