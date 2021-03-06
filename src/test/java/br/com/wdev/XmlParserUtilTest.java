package br.com.wdev;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import junit.framework.TestCase;

import org.junit.Test;

import br.com.wdev.Finder;
import br.com.wdev.Report;
import br.com.wdev.XmlParserUtil;

public class XmlParserUtilTest extends TestCase {

	@Test
	public void testGenerateXmlFileFromFinderObject() throws Exception {
		List<Finder> finders = new ArrayList<Finder>();
		finders.add(getFinder());
		
		File output = new File(getFile(Constants.OUTPUT_XML_FILENAME));
		
		XmlParserUtil xmlParserUtil = new XmlParserUtil();
		xmlParserUtil.toXml(finders, output);

		String xml = getStringFrom(output);
		
		String expected =   "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
		                    "<root>" +
				            "  <br.com.wdev.Finder>" +
                    		"    <includes>" +
                    		"      <string>**/*.java</string>" +
                    		"    </includes>" +
                    		"    <excludes>" +
                    		"      <string>**/application.conf</string>" +
                    		"    </excludes>" +
                    		"    <words>" +
                    	    "      <string>phone</string>" +
                    	    "      <string>cel</string>" +
                    	    "    </words>" +
                    		"    <regexp>telefone|tel[; ]|celular|cel[; ]|fone[; ]</regexp>" +
                    		"    <checkOnlyConsoleOutput>false</checkOnlyConsoleOutput>" +
                    		"    <caseSensitive>false</caseSensitive>" +
                    		"    <buildResult>" +
                    		"      <name>SUCCESS</name>" +
                    		"      <ordinal>0</ordinal>" +
                    		"      <color>BLUE</color>" +
                    		"    </buildResult>" +
                    		"    <buildNumber>10</buildNumber>" +
                    		"    <reports class=\"tree-map\">" +
                    		"      <no-comparator/>" +
                    		"      <entry>" +
                    		"        <string>java</string>" +
                    		"        <root>" +         
                    		"          <br.com.wdev.Report>" +
                    		"            <fileName>file1</fileName>" +
                    		"            <lines>" +
                    		"              <string>line1</string>" +
                    		"              <string>line2</string>" +
                    		"              <string>line3</string>" +
                    		"            </lines>" +
                    		"          </br.com.wdev.Report>" +
                    		"          <br.com.wdev.Report>" +
                    		"            <fileName>file2</fileName>" +
                    		"            <lines>" +
                    		"              <string>line4</string>" +
                    		"              <string>line5</string>" +
                    		"            </lines>" +
                    		"          </br.com.wdev.Report>" +
                    		"        </root>" +
                    		"      </entry>" +
                    		"    </reports>" +
                    		"  </br.com.wdev.Finder>" +
                    		"</root>";
		
		assertTrue(output.exists());
		
		assertEquals(expected, xml);
	}

	@Test
    public void testConvertFromXmlToObject() throws Exception {
        File xml = new File(getFile(Constants.OUTPUT_XML_FILENAME));
        
        XmlParserUtil xmlParserUtil = new XmlParserUtil();
        List<Finder> finders = xmlParserUtil.fromXml(xml);
        
        assertEquals(1, finders.size());
        
        Finder finder = finders.get(0);
        assertEquals("telefone|tel[; ]|celular|cel[; ]|fone[; ]", finder.regexp);
	}
	
	
    private String getFile(String filename) {
        String tempDir = System.getProperty("java.io.tmpdir") + System.getProperty("file.separator");
        return tempDir + filename;
    }

    private String getStringFrom(File output) throws Exception {
        FileInputStream xmlCreated = new FileInputStream(output);
		InputStreamReader streamReader = new InputStreamReader(xmlCreated);  
		BufferedReader reader = new BufferedReader(streamReader);
		String result = getLinesFrom(reader);
        return result;
    }

    private Finder getFinder() {
        List<String> lines1 = new ArrayList<String>();
		lines1.add("line1");
		lines1.add("line2");
		lines1.add("line3");
		
		List<String> lines2 = new ArrayList<String>();
		lines2.add("line4");
		lines2.add("line5");
		
		List<Report> reportList = new ArrayList<Report>();
		reportList.add(new Report("file1", lines1));
		reportList.add(new Report("file2", lines2));
		
		Map<String, List<Report>>reports = new TreeMap<String, List<Report>>();
		reports.put("java", reportList);
		
		String[] includes = {"**/*.java"};
		String[] excludes = {"**/application.conf"};
		String[] words = {"phone", "cel"};
		
		Finder finder = new Finder(null);
		finder.includes = includes;
		finder.excludes = excludes;
		finder.words = words;
		finder.regexp = "telefone|tel[; ]|celular|cel[; ]|fone[; ]";
		finder.reports = reports;
		finder.buildNumber = 10;
        return finder;
    }

	private String getLinesFrom(BufferedReader reader) throws Exception {
		String line, content = "";
		while((line = reader.readLine()) != null) {  
			content = content + line; 
		}
		return content;
	}
}
