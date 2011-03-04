package yaelet.utils.ColorCalculator;
import java.io.File;
import java.lang.String;
import java.util.Iterator;
import java.util.TreeMap;

public class TestCalculator {

	
	
	public static void main(String[] args)
	{
		
		// get the makeup colors from the sample images
		TreeMap<Integer, String> colors = LoadMakeupColors.getAvonColors();
		
		// DEBUG: print the colors Hash table
		Iterator<Integer> color = colors.keySet().iterator();
		while (color.hasNext())
		{
			Integer prodColor = color.next();
			System.out.format("Name: %s, Value: %s%n",colors.get(prodColor), Integer.toHexString(prodColor));
		}
		
		// get the color from a tester file
		String testFile = "C:\\Users\\Eyal\\Workspace\\MyColorFinder\\Samples\\Testers\\Eyal.jpg";
		int testColor = Calculator.getTopColor(new File(testFile));

		//DEBUG: print the color found in the sample
		System.out.format("test color is %s%n", Integer.toHexString(testColor));

		// the closest product color
		System.out.format("The relevant product is:%s%n", colors.higherEntry(testColor).getValue());
		
		
		/*
		Image image = Toolkit.getDefaultToolkit().getImage("C:\\Users\\Eyal\\Pictures\\6AM.jpg");
		//Image image = Toolkit.getDefaultToolkit().getImage("C:\\Users\\Eyal\\Pictures\\com.extjs.ext.2.2.gif");
		Calculator calc = new Calculator(image);
		try {
			if (calc.Init())
			{
				//calc.Calculate();
				Calculator.printInverseHistogram(Calculator.BuildHistogram(calc.GetImagesArray()));
			}

		} catch (Exception e) {
			System.out.println(String.format("Exception thrown with message %s", 
					e.getMessage()));
		}
		*/
	}
}
