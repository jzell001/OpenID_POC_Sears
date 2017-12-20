package hello;

import java.io.IOException;
import java.io.StringReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class ParseXml {
	
    public static String Parse(String xml, String key) {
		
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;
        InputSource is;
        try {
            builder = factory.newDocumentBuilder();
            is = new InputSource(new StringReader(xml));
            Document doc = builder.parse(is);
            NodeList list = doc.getElementsByTagName(key);
            //System.out.println(list.item(0).getTextContent());
			String value = list.item(0).getTextContent();
			//System.out.println("value: " + value);
			return value;
			
        } catch (ParserConfigurationException e) {
			return null;
        } catch (SAXException e) {
			return null;
        } catch (IOException e) {
			return null;
        }
    }
}