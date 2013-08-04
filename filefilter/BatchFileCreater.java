package ca.softwarebyslim.filefilter;

import java.io.File;

public class BatchFileCreater {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		BatchFileCreater bfc = new BatchFileCreater();
		bfc.run();
	}
	
	public void run() {
		
		String autoRunCommand = "start javaw -jar JarFile.jar";
		String batFileName = "runFileFilter.bat";
		String windowsStartUpFolder = "%appdata%\\Microsoft\\Windows\\Start Menu\\Programs\\Startup";
		
		File batFile = new File(windowsStartUpFolder + batFileName);
		
	}

	private String GetExecutionPath(){
	    String absolutePath = getClass().getProtectionDomain().getCodeSource().getLocation().getPath();
	    //absolutePath = absolutePath.substring(0, absolutePath.lastIndexOf("/"));
	    return absolutePath;
	}
}
