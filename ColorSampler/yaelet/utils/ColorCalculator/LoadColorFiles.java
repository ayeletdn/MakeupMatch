package yaelet.utils.ColorCalculator;

import java.lang.String;
import java.util.TreeMap;
import java.io.File;
import java.io.FilenameFilter;

public class LoadColorFiles implements IManageColors {

	private static final String CompanyColorFilesPath = "C:\\Users\\Eyal\\Workspace\\MyColorFinder\\Samples";
	private static final String TestFilesPath = "C:\\Users\\Eyal\\Workspace\\MyColorFinder\\Samples\\Testers\\Ayelet.jpg";


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
	
	public TreeMap<Integer, String> getColorsTreeMap()
	{
		return getColorsFromDir(CompanyColorFilesPath);
	}
	
	public static TreeMap<Integer, String> getColorsFromDir(String Path)
	{
		TreeMap<Integer, String> returnValue = new TreeMap<Integer, String>();
		
		for (File ImageFile : getFiles(Path)) {
			int key = Calculator.getTopColor(ImageFile);
			if (!returnValue.containsKey(key))
			{
				returnValue.put(key, ImageFile.getName());
			}
			else
			{
				System.out.format("Color %s already exists. Skipping.", Integer.toHexString(key));
			}
		}
		
		return returnValue;
	}
	
	public int getItemColor()
	{
		return getColorsFromFile(TestFilesPath);
	}
	
	public static int getColorsFromFile(String Path)
	{
		File ImageFile = new File(Path);
		
		int key = Calculator.getTopColor(ImageFile);
		return key;
	}
}
