package yaelet.utils.ColorCalculator;

import java.util.TreeMap;
import java.io.File;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.myjavatools.lib.foundation.Pair;


public class LoadColorsXML implements IManageColors {

	
    SAXParserFactory factory;
    SAXParser saxParser;

    private TreeMap<Integer, String> parseXML(File XMLFile)
    {
    	factory = SAXParserFactory.newInstance();
    	final TreeMap<Integer, String> resultTree = new TreeMap<Integer, String>();
    	
    	try
    	{
    		saxParser = factory.newSAXParser();
        		
    		DefaultHandler handler = new DefaultHandler() {
    			 
    		     boolean m_eProductName = false;
    		     boolean m_eProductNumber = false;
    		     Product m_CurrentProduct;
    		  
    		     public void startElement(String uri, String localName,
    		    	        String qName, Attributes attributes)
    		    	        throws SAXException {
    		    	 
    		    	        System.out.println("Start Element :" + qName);
    		    	 
    		    	        if (qName.equalsIgnoreCase("PRODUCT")) {
    		    	           HandleProduct();
    		    	        }
    		    	        
    		    	        if (qName.equalsIgnoreCase("NAME")) {
    		    	        	m_eProductName = true;
     		    	        }
    		    	        
    		    	        if (qName.equalsIgnoreCase("NUMBER")) {
    		    	        	m_eProductNumber = true;
     		    	        }
    		     }
    		     
    		     private void HandleProduct()
    		     {
    		    	 // set the previous product
    		    	 if (m_CurrentProduct != null)
    		    	 {
    		    		 Pair<Integer, String> pair = m_CurrentProduct.flushAsPair();
    		    		 resultTree.put(pair.getKey(), pair.getValue());
    		    	 }
    		    	 // create a new one
    		    	 m_CurrentProduct = new Product();
    		     }
    		     
    		     
    		     public void characters(char ch[], int start, int length)
    		     throws SAXException {

    		    	 if (m_eProductName) {
    		    		 m_CurrentProduct.setName(new String(ch));
    		    		 m_eProductName = false;
    		    	 }
    		    	 
    		    	 if (m_eProductNumber) {
    		    		 m_CurrentProduct.setNumber(new Integer(new String(ch)));
    		    		 m_eProductNumber = false;
    		    	 }
    		     }
    		     
    		};
    		
    		saxParser.parse("c:\\file.xml", handler);
    	}
    	catch (Exception ex)
    	{
    		System.out.format("An exception occured: %d", ex.getMessage());
    	}
    	
    	
    	return resultTree;
    	
    }
    
    private final class Product
    {
    	private String name;
    	private int number;
    	
    	public String getName()
    	{
    		return name;
    	}
    	
    	public void setName(String value)
    	{
    		name = value;
    	}
    	
    	public int getNumber()
    	{
    		return number;
    	}
    	
    	public void setNumber(int value)
    	{
    		number = value;
    	}
    	
    	public Pair<Integer, String> flushAsPair()
    	{
    		return new Pair<Integer, String>(number, name);
    	}
    }
	
	@Override
	public TreeMap<Integer, String> getColorsTreeMap() {
		File XMLFile = new File("C:\\Users\\Eyal\\Workspace\\MakupMatch\\XMLs\\AvonColors.xml"); 
		return parseXML(XMLFile);
	}

	@Override
	public int getItemColor() {
		// TODO Auto-generated method stub
		return 0;
	}

}
