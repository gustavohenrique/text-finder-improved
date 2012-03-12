package br.com.wdev;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.thoughtworks.xstream.io.xml.XmlFriendlyReplacer;

public class XmlParserUtil implements Serializable {

    private static final long serialVersionUID = -203503679000431913L;
    
    private XStream xstream;

    public String toXml(List<Finder> finders) {
        xstream = getXstream();
        return xstream.toXML(finders);
    }

    public File toXml(List<Finder> finders, File file) {
		String xml = toXml(finders);
		return saveFile(file, xml);
	}
   
	public File saveFile(File file, String xml) {
		try {
			PrintWriter writer = new PrintWriter(file);
			writer.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
			writer.println(xml);
			writer.flush();
			writer.close();
		} catch (FileNotFoundException e) {
			//
		}
		return file;
	}

    public List<Finder> fromXml(File filename) {
        try {
            xstream = getXstream();
//            FileInputStream i = new FileInputStream(filename);
            return (List<Finder>) xstream.fromXML(getReader(filename));
        }
        catch (Exception e) { 
            return new ArrayList<Finder>();
        }
    }
    
    private XStream getXstream() {
        if (xstream == null) {
            xstream = new XStream(new DomDriver("UTF-8", new XmlFriendlyReplacer("__", "_")));//Items.XSTREAM;
            xstream.setClassLoader(Finder.class.getClassLoader());
            xstream.alias("root", List.class);
            xstream.alias("br.com.wdev.Finder", Finder.class);
            xstream.alias("br.com.wdev.Report", Report.class);
        }
        return xstream;
    }
    
    private Reader getReader(File filename) throws Exception {
        FileInputStream xmlCreated = new FileInputStream(filename);
        InputStreamReader streamReader = new InputStreamReader(xmlCreated);  
        return new BufferedReader(streamReader);
    }

}
