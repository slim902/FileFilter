package ca.softwarebyslim.filefilter;

public class PathBreakdown{
	
	/* take an ArrayList<String> {pathname, isdir} and breakdown all files
	 * 
	 * 
	 */
	static Boolean DEBUG_MODE = false;
	String path;
	String subDirs;
	String fileName;
	String isDirString;
	boolean isDir;

	
	public PathBreakdown(String[] fileProperties){
		path = fileProperties[0];
		isDirString = fileProperties[1];
		subDirs = FindLastSeperator.getParentPath(fileProperties[2]);
		isDir = false;
		fileName = "";
	}

	public String[] breakdownPath() {
		String rawPath = path;
		String[] brokendownPath;
		
		if(isDirString.equalsIgnoreCase("dir")){
			isDir = true;
		}
		else{
			isDir = false;
		}
		path = rawPath;
		
		System.out.println("PathBreakdown: " + path);
		int lastUnixPos = rawPath.lastIndexOf('/');
		int lastWindowsPos = rawPath.lastIndexOf('\\');
		int indexOfLastSeparator = Math.max(lastUnixPos, lastWindowsPos);
		
		if(DEBUG_MODE) {
			System.out.println("Pathbreakdown.breakdownPath fileName: " + rawPath.substring(indexOfLastSeparator + 1)); 
		}
		
		fileName = rawPath.substring(indexOfLastSeparator + 1);
		
		
		brokendownPath = new String[] {path, fileName, isDirString, subDirs};
		
		return brokendownPath;
	
		}
}
