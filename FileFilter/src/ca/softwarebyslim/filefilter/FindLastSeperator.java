package ca.softwarebyslim.filefilter;

public class FindLastSeperator {
	
	String rawPath;
	static int lastUnixPos;
	static int lastWindowsPos;
	static int indexOfLastSeperator;
	
	private static int getLastSeperatorIndex(String rawPath) {
		lastUnixPos = rawPath.lastIndexOf('/');
		lastWindowsPos = rawPath.lastIndexOf('\\');
		
		return indexOfLastSeperator = Math.max(lastUnixPos, lastWindowsPos);	
	}

	public static String getParentPath(String rawPath){
		return rawPath.substring(0, getLastSeperatorIndex(rawPath));
	}
	
	public static String getFileName(String rawPath) {
		return rawPath.substring(getLastSeperatorIndex(rawPath) +  1);
	}
}
