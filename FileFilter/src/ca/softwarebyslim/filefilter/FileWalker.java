package ca.softwarebyslim.filefilter;

import java.io.File;
import java.util.ArrayList;

public class FileWalker {
	static Boolean DEBUG_MODE = true;
	static ArrayList<String[]> dirFileList = new ArrayList<String[]>();
	
	static String originalPath;
	static Boolean originalPathSet = false;
	
    public static ArrayList<String[]> walk(String path) {
    	
    	if(!originalPathSet) {
    		originalPath = path;
    		originalPathSet = true;
    	}
    	
    	File root = new File(path);
        File[] list = root.listFiles();

        for (File f : list) {
            if (f.isDirectory()) {
                walk(f.getAbsolutePath());
                System.out.println("originalPath: " + originalPath + "\nf.getAbsoluteFile: " + f.getAbsoluteFile().toString() + "\nDifferenceOfStrings (originalPath, f.getAbsolutFile) :" + DifferenceOfStrings.difference(originalPath, f.getAbsoluteFile().toString()));
                System.out.println("Dir:" + f.getAbsoluteFile() + " SubDirs: " + root.toString());
                
                dirFileList.add(new String[] {f.getAbsoluteFile().toString(), "Dir", DifferenceOfStrings.difference(originalPath, f.getAbsoluteFile().toString())});
            }
            else {
                System.out.println("File:" + f.getAbsoluteFile());
                dirFileList.add(new String[] {f.getAbsoluteFile().toString(), "File", DifferenceOfStrings.difference(originalPath, f.getAbsoluteFile().toString())});
            }
            if(DEBUG_MODE){
            	System.out.println("FileWalker.dirFileList.size(): " + dirFileList.size());
            }
        }
        
        return dirFileList;
        
    }

    /*
    public static void main(String[] args) {
        FileWalker fw = new FileWalker();
        fw.walk(args[0]);
    }
    */
}