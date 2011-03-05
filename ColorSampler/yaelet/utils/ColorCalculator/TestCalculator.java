package yaelet.utils.ColorCalculator;
import java.lang.String;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

public class TestCalculator {

	
	public static void main(String[] args)
	{
		
		IManageColors ColorGetter = new LoadColorFiles();
		
		// get the makeup colors from the sample images
		TreeMap<Integer, String> colors = ColorGetter.getColorsTreeMap();
		
		// DEBUG: print the colors Hash table
		Iterator<Integer> color = colors.keySet().iterator();
		while (color.hasNext())
		{
			Integer prodColor = color.next();
			System.out.format("Name: %s, Value: %s%n",colors.get(prodColor), Integer.toHexString(prodColor));
		}
		
		// get the color from a tester file
		int FindMyColor = ColorGetter.getItemColor();
		//DEBUG: print the color found in the sample
		System.out.format("My color is %s%n", Integer.toHexString(FindMyColor));

		Map.Entry<Integer, String> MyMatch = colors.higherEntry(FindMyColor);
		// the closest product color
		System.out.format("The relevant product is:%s%n", MyMatch.getValue());
		
	}
}
