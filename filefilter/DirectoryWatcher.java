package ca.softwarebyslim.filefilter;

import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.io.File;

import static java.nio.file.StandardWatchEventKinds.ENTRY_CREATE;
import static java.nio.file.StandardWatchEventKinds.ENTRY_DELETE;

/**
 * DirectoryWatcher is a class that sets up
 * @author slim902
 *
 */

public class DirectoryWatcher implements Runnable{
	
	static ArrayList<String[]> watchEvents;  // list of watchEvents
	String watchedFolder;					 // folder to watch
	
	static Boolean DEBUG_MODE = false;
	
	/**
	 * Constructs a new DirectoryWatcher, monitoring the folder located at the aFolderToWatch path.
	 * @param aFolderToWatch (String): path to folder to be monitored
	 */
	public DirectoryWatcher(String aFolderToWatch) {
		watchEvents = new ArrayList<String[]>();
		watchedFolder = aFolderToWatch;
	}

	/**
	 * Informs about file creations and deletions in the given directories. This
	 * program works non-recursively.
	 * 
	 * @author <a href="mailto:cher@riedquat.de">Christian Hujer</a>
	 * 
	 * @throws IOException in case of I/O problems.
	 * @throws InterruptedException in case the thread was interrupted during
	 *         watchService.take().
	 */	
	public void watchDirectory() throws IOException, InterruptedException {
		final FileSystem fileSystem = FileSystems.getDefault();
		try(final WatchService watchService = fileSystem.newWatchService()) {
			final Map<WatchKey, Path> keyMap = new HashMap<WatchKey, Path>();
			final Path path = Paths.get(watchedFolder);
			keyMap.put(
					path.register(watchService, ENTRY_CREATE, ENTRY_DELETE),
					path);
			if(DEBUG_MODE){
				System.out.println("Path: " + path + " registered.");
			}
			WatchKey watchKey;
			do {
				watchKey = watchService.take();
				final Path eventDir = keyMap.get(watchKey);
				for (final WatchEvent<?> event : watchKey.pollEvents()) {
					@SuppressWarnings("rawtypes")
					final WatchEvent.Kind kind = event.kind();
					final Path eventPath = (Path) event.context();
					
					if(DEBUG_MODE){
						System.out.println(eventDir + ": " + event.kind() + ": " + event.context());
					}
					if(kind != ENTRY_DELETE){
						// add watchEvent {FilePath, FileExtension, FileName}
						watchEvents.add(new String[] {eventDir + "\\" + eventPath.toFile(), getFileExtension(eventDir + "\\" + eventPath.toFile()), event.context().toString()});
					}
					if(DEBUG_MODE){
						System.out.println("watchEvents size: " + watchEvents.size());
						//System.out.println("LastEvent: " + getLastWatchEvent());
					}
				}
			} while (watchKey.reset());


		}
	}
	
	/**
	 * Start watching our directory in a separate thread.
	 */
	@Override
	public void run() {
		try {
			watchDirectory();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Get the file extension of the file/folder that triggered the watchEvent
	 * 
	 * @param aPath (String): Path of the file/folder that triggered the watchEvent
	 * @return (String): file Extension
	 * 
	 * @author slim902
	 */
	private static final String getFileExtension(final String aPath){
		final File path = new File(aPath);
		final String filename = aPath;
		final String extension;
		
		if(DEBUG_MODE) {
			System.out.println("ISDirectory: " + path.isDirectory() + "\n Path: " + aPath);
		}
		
		if(path.isDirectory()) {
			return "folder";
		}
		else {
			
			if (filename == null) {
	            return null;
			}
			
			// Get the index of the last separator
			int lastUnixPos = filename.lastIndexOf('/');
			int lastWindowsPos = filename.lastIndexOf('\\');
			int indexOfLastSeparator = Math.max(lastUnixPos, lastWindowsPos);

			int extensionPos = filename.lastIndexOf('.');
			int lastSeparator = indexOfLastSeparator;
			
			int indexOfExtension = lastSeparator > extensionPos ? -1 : extensionPos;
			int index = indexOfExtension;
			if (index == -1) {
				extension =  "";
			} else {
				
				extension =  filename.substring(index + 1);
			}			
	        if(DEBUG_MODE) {
	        	System.out.println("File Extension: " + extension);
	        }
	        
	        return extension;
	        
		}
	}
	
	
	/**
	 * Gets the oldest watchEvent from our watchEvent arrayList.
	 * @return (String[]): {FilePath, FileExtension, FileName}
	 */
	public String[] getOldestWatchEvent() {
	    
		String[] oldestWatchEvent = {};
		
		if(watchEvents.isEmpty()) {
			if(DEBUG_MODE){
				System.out.println("No Watch Events");
			}
			return oldestWatchEvent;
		}
		else {
			oldestWatchEvent = watchEvents.get(0);
			watchEvents.remove(oldestWatchEvent);
			if(DEBUG_MODE){
				System.out.println("Watch Event Retrieved");
			}
			
			return oldestWatchEvent;
		}

	}


}