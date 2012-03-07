package br.com.wdev;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.Serializable;


import com.thoughtworks.xstream.XStream;

public class XmlParserUtil implements Serializable {

    private static final long serialVersionUID = -203503679000431913L;

    public String toXml(Finder finder) {
        XStream xstream = new XStream();//Items.XSTREAM;
        xstream.alias("text-finder-improved", Finder.class);
        return xstream.toXML(finder);
    }
    
    public File toXml(Finder finder, File file) {
		String xml = toXml(finder);
		return saveFile(file, xml);
	}
   
	public File saveFile(File file, String xml) {
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
