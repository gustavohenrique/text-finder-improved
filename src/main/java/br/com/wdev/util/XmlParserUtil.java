package br.com.wdev.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.Serializable;

import br.com.wdev.helpers.Finder;

import com.thoughtworks.xstream.XStream;

public class XmlParserUtil implements Serializable {

    private static final long serialVersionUID = -203503679000431913L;

    public File toXml(Finder finder, File file) {
		XStream xstream = new XStream();//Items.XSTREAM;
		xstream.alias("text-finder-improved", Finder.class);
		String xml = xstream.toXML(finder);
		
		return saveFile(file, xml);
	}
    /*
    public void toXml(Finder finder, File file) {
        
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.newDocument();
            
            Element root = document.createElement("text-finder-improved");
            document.appendChild(root);
            
            Element includes = document.createElement("includes");
            for (String includeText : finder.includes) {
                Element include = document.createElement("string");
                include.setNodeValue(includeText);
                includes.appendChild(include);    
            }
            root.appendChild(includes);
            
            Element excludes = document.createElement("excludes");
            for (String excludeText : finder.excludes) {
                Element exclude = document.createElement("string");
                exclude.setNodeValue(excludeText);
                excludes.appendChild(exclude);    
            }
            root.appendChild(excludes);
            
            FileOutputStream out = new FileOutputStream(file);
            XMLSerializer serializer = new XMLSerializer(out, new OutputFormat(document, "UTF-8", true));
            
            serializer.serialize(document);
        }
        catch (Exception e) {
        }
        
    }
*/
	private File saveFile(File file, String xml) {
		try {
			PrintWriter writer = new PrintWriter(file);
			writer.println("<?xml version=\"1.0\" encoding=\"UTF-8\">");           
			writer.println(xml);
			writer.flush();
			writer.close();
		} catch (FileNotFoundException e) {
			//
		}
		return file;
	}

}
