package br.com.wdev;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

import org.junit.Test;

import br.com.wdev.Finder;
import br.com.wdev.Report;
import br.com.wdev.XmlParserUtil;

public class XmlParserUtilTest extends TestCase {

	@Test
	public void testGenerateXmlFileFromFinderObject() throws Exception {
		Finder finder = getFinder();
		
		File output = new File(getFile("text-finder-improved.xml"));
		
		XmlParserUtil xmlParserUtil = new XmlParserUtil();
		xmlParserUtil.toXml(finder, output);

		String xml = getStringFrom(output);
		
		String expected = "<?xml version=\"1.0\" encoding=\"UTF-8\">" +
				            "<text-finder-improved>" +
                    		"  <includes>" +
                    		"    <string>**/*.java</string>" +
                    		"  </includes>" +
                    		"  <excludes>" +
                    		"    <string>**/application.conf</string>" +
                    		"  </excludes>" +
                    		"  <regexp>phone</regexp>" +
                    		"  <checkOnlyConsoleOutput>false</checkOnlyConsoleOutput>" +
                    		"  <caseSensitive>false</caseSensitive>" +
                    		"  <buildResult>" +
                    		"    <name>SUCCESS</name>" +
                    		"    <ordinal>0</ordinal>" +
                    		"    <color>BLUE</color>" +
                    		"  </buildResult>" +
                    		"  <reports>" +
                    		"    <br.com.wdev.Report>" +
                    		"      <fileName>file1</fileName>" +
                    		"      <lines>" +
                    		"        <string>line1</string>" +
                    		"        <string>line2</string>" +
                    		"        <string>line3</string>" +
                    		"      </lines>" +
                    		"    </br.com.wdev.Report>" +
                    		"    <br.com.wdev.Report>" +
                    		"      <fileName>file2</fileName>" +
                    		"      <lines>" +
                    		"        <string>line4</string>" +
                    		"        <string>line5</string>" +
                    		"      </lines>" +
                    		"    </br.com.wdev.Report>" +
                    		"  </reports>" +
                    		"</text-finder-improved>";
		
		assertTrue(output.exists());
		
		assertEquals(expected, xml);
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
		
		List<Report> reports = new ArrayList<Report>();
		reports.add(new Report("file1", lines1));
		reports.add(new Report("file2", lines2));
		
		String[] includes = {"**/*.java"};
		String[] excludes = {"**/application.conf"};
		Finder finder = new Finder(null, includes, excludes, "phone");
		finder.reports = reports;
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
