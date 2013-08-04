package ca.softwarebyslim.filefilter;

import java.util.ArrayList;

/**
 * 
 * @author slim902
 *
 * FilterRuleManager will hold all rules and should have methods so the snycFolder can access rules
 */

public class FilterRuleManager {
	public ArrayList<FilterRule> ruleManager;
	public int numberOfRules;
	
	static boolean DEBUG_MODE = false;
	
	
	public FilterRuleManager(){
		ruleManager = new ArrayList<FilterRule>();
		numberOfRules = 0;
	}
	
	
	public void addRule(FilterRule rule) {
		//System.out.println("MGR CHECK: " + ruleManager.size());
		boolean newRule = true;
		for(FilterRule rules : ruleManager) {
			if(rules.getRuleDestinationPath() == rule.getRuleDestinationPath()){
				System.out.println("Rule Already Exists For This Destination Path. Please Enter A Different Path.");
				newRule = false;
			}
		}
		if(newRule == true){
			ruleManager.add(rule);
			numberOfRules++;
		}
		
		if(DEBUG_MODE) {
			if(newRule == true){ 
				System.out.println("Rule Added!");
			} else {
				System.out.println("Rule Already Exists!");
			}
			
		}
	}
	
	
	public void deleteRule(FilterRule rule) {
		ruleManager.remove(rule);
		
		if(DEBUG_MODE){
			System.out.println("Rule Deleted!");
		}
	}
	
	
	public void listRules() {
		for(FilterRule rule : ruleManager) {
			System.out.println("---------------------");
			System.out.println("Rule Type: " + rule.getRuleFileType());
			System.out.println("Rule Destination Path: " + rule.getRuleDestinationPath());
			System.out.println("Acctepted File Extensions: ");
			String[] exts = rule.acceptedFileTypesToString(rule.getRuleAcceptedFileTypes()).split(" ");
			for(String ext : exts) {
				System.out.println("\t" + ext);
			}
			System.out.println("---------------------");
		}
	}
	
	
	public static void main(String[] args) {
		FilterRuleManager mgr = new FilterRuleManager();
		
		if(DEBUG_MODE) {
			//mgr.addRule(new FilterRule("Music",System.getProperty("user.home") + "/Desktop" +"\\FileFilter SyncFolder"));
			//mgr.addRule(new FilterRule("Office", System.getProperty("user.home") + "/Desktop" +"\\FileFilter SyncFolder"));
			mgr.listRules();
		}
	}
	

	public String[][] getRules(){
		String[][] list = new String[numberOfRules][2];
		//ArrayList<FilterRule> list = new ArrayList<FilterRule>();
		int listLength = list.length;
		int count = 0;
		
		if(DEBUG_MODE) {
			System.out.println("List Length Before: " + listLength);
		}
		
		for(FilterRule rules : ruleManager) {
			String[] listRule = {rules.getRuleDestinationPath(), rules.acceptedFileTypesToString(rules.getRuleAcceptedFileTypes())};
			if(DEBUG_MODE) {
				for(String s : listRule) {
					System.out.println("Rule: " + s);
				}
			}
			list[count][0] = listRule[0];  // Rule Destination Path
			list[count][1] = listRule[1];  // Rule Accepted FileTypes
			count++;
			if(DEBUG_MODE) {
				System.out.println("List Length After: " + listLength);
			}
			
		}
		return list;
	}
	
	/**
	 * 
	 * @param fileExtension - (String) want to match this fileExtension to an existing rule in our FilterRuleManager
	 * @return String[] in which each entry is a destination path for the give fileExtension
	 */
	public ArrayList<String> findDestinationPathOfFileType(String fileExtension) {
		ArrayList<String> destinationPaths = new ArrayList<String>();
		
		if(DEBUG_MODE)  {
			listRules();
		}
		for(FilterRule rule : ruleManager) {
			if(DEBUG_MODE)  {
				System.out.println("findDestinationPathOfFileType first loop rule: " + rule);
			}
			
			for(String acceptedType : rule.getRuleAcceptedFileTypes()){
				if(DEBUG_MODE)  {
					System.out.println("findDestinationPathOfFileType second loop acceptedType: " + acceptedType);
					System.out.println("findDestinationPathOfFileType ext to match: " + fileExtension);
					acceptedType = acceptedType.trim();
					fileExtension = fileExtension.trim();
					System.out.println((acceptedType.equalsIgnoreCase(fileExtension)));
					
				}
				if(acceptedType.equalsIgnoreCase(fileExtension)) {
					destinationPaths.add(rule.getRuleDestinationPath());
					if(DEBUG_MODE)  {
						System.out.println("Found Destination path: " + rule.getRuleDestinationPath());
					}
				}
			}
		}
		return destinationPaths;
	}
	
}
