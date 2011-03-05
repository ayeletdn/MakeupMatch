package yaelet.utils.ColorCalculator;

import java.util.TreeMap;


/**
 * @author Ayelet Dahan
 *
 */
public interface IManageColors {

	/*
	 * @brief get a map of the colors to use. The key is the color, the value is the name of the color.
	 * @see com.myjavatools.lib.foundation.Maps
	 */
	public TreeMap<Integer, String> getColorsTreeMap();
	
	/*
	 * @brief get the color of the item to find 
	 */
	public int getItemColor();
}
