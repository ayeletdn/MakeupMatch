package yaelet.utils.ColorCalculator;

import java.lang.String;
import java.util.TreeMap;
import java.io.File;
import java.io.FilenameFilter;

public class LoadMakeupColors {


	private static File[] getFiles(String Path)
	{
		File FilesDir = new File(Path);
		
		FilenameFilter filterGetImages = new FilenameFilter() {
		    public boolean accept(File dir, String name) {
		        return name.endsWith(".jpg");
		    }
		};

		File[] FilesList = FilesDir.listFiles(filterGetImages);
		
		
		return FilesList;
		
	}
	
	public static TreeMap<Integer, String> getAvonColors()
	{
		TreeMap<Integer, String> returnValue = new TreeMap<Integer, String>();
		
		for (File ImageFile : getFiles("C:\\Users\\Eyal\\Workspace\\MyColorFinder\\Samples")) {
			returnValue.put(Calculator.getTopColor(ImageFile), ImageFile.getName());
		}
		
		return returnValue;
	}
}
