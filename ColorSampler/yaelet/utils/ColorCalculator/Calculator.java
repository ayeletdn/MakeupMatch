package yaelet.utils.ColorCalculator;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.PixelGrabber;
import java.util.Iterator;
import java.util.TreeMap;
import java.util.Map;
import com.myjavatools.lib.foundation.Maps;
import java.util.Set;
import java.io.File;
import java.lang.Math;

public class Calculator {
 
	Image m_objImage;
	int m_iImageWidth;
	int m_iImageHeight;
	int[] m_iImageColors;

	static float similarPercent = 0.5f;
	static double similar = Math.pow(2,24) * similarPercent / 100;

	/*
	 * byte color (8 bit) is structured as follows:
	 * Bit   07 06 05 04 03 02 01 00
	 * Data   R  R  R  G  G  G  B  B
	 */
	
	static boolean is8BitImage(PixelGrabber pg) {
	    return pg.getPixels() instanceof byte[];
	}
	
	public Calculator(Image image)
	{
		this.m_objImage = image;
	}
	
	/*
	 * @brief get the colors to an integer array
	 */
	public boolean Init() throws InterruptedException
	{
		//Image image = Toolkit.getDefaultToolkit().getImage("inFile.png");

	    PixelGrabber grabber = new PixelGrabber(m_objImage, 0, 0, -1, -1, false);

	    if (grabber.grabPixels()) {
	      m_iImageWidth = grabber.getWidth();
	      m_iImageHeight = grabber.getHeight();
	      if (is8BitImage(grabber)){
	    	  return false;
	      }
	      else {
	    	  m_iImageColors = (int[]) grabber.getPixels();
	    	  return true;
	      }
	    }
	    
	    return false;
	}
	
	
	public static int getTopColor(File file)
	{
		int returnValue = 0;
		
		try {
			Image image = Toolkit.getDefaultToolkit().getImage(file.getPath());
			
			PixelGrabber grabber = new PixelGrabber(image, 0, 0, -1, -1, false);
			if (grabber.grabPixels() && !is8BitImage(grabber))
			{
				int[] imagePixels = (int[]) grabber.getPixels();

				if (null != imagePixels)
				{
					TreeMap<Integer, Integer> imageHistogram = BuildHistogram(imagePixels);

					// reverse the histogram
					Map<Integer, Set<Integer>> revHistogramMap = Maps.revert(imageHistogram);
					TreeMap<Integer, Set<Integer>> revHistogram = new TreeMap<Integer, Set<Integer>>();
					revHistogram.putAll(revHistogramMap); 

					return revHistogram.get(revHistogram.lastKey()).iterator().next();
				}
			}
			
		} catch (Exception e) {
			System.out.println(String.format("Exception thrown with message %s", 
					e.getMessage()));
		}
		
		return returnValue;
	}
	
	/*
	 * @brief Build a histogram from the pixels of an image
	 */
	public static TreeMap<Integer, Integer> BuildHistogram(int[] Image)
	{
		TreeMap<Integer, Integer> Histogram = new TreeMap<Integer, Integer>();

		for (int i : Image) {
			// insert all the colors (RGBA)
			if (!Histogram.containsKey(i))
			{
				Histogram.put(i, 1);
			}
			else
			{
				Histogram.put(i, Histogram.get(i)+1);
			}
		}
		
		return CompressColorMap(Histogram);
		
	}

	/*
	 * @brief print to the console a given histogram
	 */
	public static void printHistogram(TreeMap<Integer, Integer> Histogram)
	{
		Map.Entry<Integer, Integer> currentSet = Histogram.firstEntry();
		// iterate over the histogram from most common color to least common color.
		while (currentSet != null)
		{
				System.out.format("Key = %s, Value = %s%n", 
						Integer.toHexString(currentSet.getKey()), currentSet.getValue());

			
			// get the next key
			currentSet = Histogram.higherEntry(currentSet.getKey());
		}
	}

	/*
	 * @brief print to the console an inverse of a given histogram 
	 * (keys and values switched)
	 */
	public static void printInverseHistogram(TreeMap<Integer, Integer> Histogram)
	{
		Map<Integer, Set<Integer>> revHistogramMap = Maps.revert(Histogram);
		TreeMap<Integer, Set<Integer>> revHistogram = new TreeMap<Integer, Set<Integer>>();
		revHistogram.putAll(revHistogramMap); // a reversed histogram, to find the most common colors.

		Map.Entry<Integer, Set<Integer>> currentSet = revHistogram.lastEntry();
		// iterate over the histogram from most common color to least common color.
		while (currentSet != null)
		{
			if (currentSet.getKey() < 100)
			{
				break;
			}
			
			// iterate over the current count, get all the colors of this count
			Iterator<Integer> itr = currentSet.getValue().iterator();
			while (itr.hasNext())
			{
				System.out.format("Key = %s, Value = %s%n", 
						currentSet.getKey(), Integer.toHexString(itr.next()));

			}
			
			// get the next key
			currentSet = revHistogram.lowerEntry(currentSet.getKey());
		}
	}

	/*
	 * @brief Check if two values are close enough, as defined by the parameter "similar"
	 */
	private static boolean AreColorsClose(int largeColor, int smallColor)
	{
		if (largeColor < smallColor)
		{
			throw new IllegalArgumentException("largeColor cannot be smaller than smallColor");
		}
		
		if (largeColor - smallColor > similar)
		{
			return false;
		}
		else
		{
			return true;
		}
		
	}

	/*
	 * @brief Compress a histogram of colors according to AreColorsClose(..) 
	 * @param A histogram of an image with the key the color and the value the number of times that color appeared.
	 * @return Returns a compressed version of the histogram, according to the "closeness" of the colors.
	 */
	private static TreeMap<Integer, Integer> CompressColorMap(TreeMap<Integer, Integer> Histogram)
	{
		// copy the original
		TreeMap<Integer, Integer> compressed = new TreeMap<Integer, Integer>();
		compressed.putAll(Histogram);

		if (compressed.size() > 1)
		{
			Map.Entry<Integer, Integer> currentColor = compressed.firstEntry();

			while (currentColor != null)
			{
				// stop if no more colors
				Map.Entry<Integer, Integer> nextColor = compressed.higherEntry(currentColor.getKey());
				if (nextColor == null)
				{
					break;
				}

				if (AreColorsClose(nextColor.getKey(), currentColor.getKey()))
				{
					// update the current color
					compressed.put(currentColor.getKey(), currentColor.getValue() + nextColor.getValue());
					// get the new current (with it's new value)
					currentColor = compressed.lowerEntry(nextColor.getKey());
					// remove the obsolete next
					compressed.remove(nextColor.getKey());
				}
				else
				{
					currentColor = compressed.higherEntry(currentColor.getKey());
				}
			}
		}
		return compressed;
	}
		
	
	/*
	 * @brief Returns the array of pixels of the image initialized.
	 */
	public int[] GetImagesArray()
	{
		return m_iImageColors;
	}
	}
