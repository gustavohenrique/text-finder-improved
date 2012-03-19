package br.com.wdev;

import hudson.model.Result;

import java.io.File;
import java.util.List;
import java.util.Map;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;

public class FinderTest extends TestCase {

    private Finder finder;
	
	@Before
	public void setUp() throws Exception {
		File workspace = new File("src/test/resources/sample-app");
		this.finder = new Finder(workspace);
	}
	
	@Test
	public void testReport() {
	    String[] includes = {"**/*.java", "**/*.xml", "**/*.properties", "messages"};
	    String[] words = {"phone"};
	    
	    finder.includes = includes;
        finder.words = words;
        finder.findText();

        Map<String, List<Report>> reports = finder.reports;
        assertTrue(reports.containsKey("java"));
        assertTrue(reports.containsKey("xml"));
        assertTrue(reports.containsKey("properties"));
        assertTrue(reports.containsKey("other"));
	}
	
	@Test
	public void testFindPhoneInJavaFiles() {
		String[] includes = {"**/*.java"};
		String[] words = {"phone"};
		
		finder.includes = includes;
		finder.words = words;
		finder.setBuildResult("success");
		finder.findText();

		List<Report> reports = finder.reports.get("java");

		assertEquals(2, reports.size());
		assertEquals(Result.SUCCESS, finder.buildResult);
		assertEquals("app/controllers/Dashboard.java", reports.get(0).getFileName());
		assertEquals("app/models/Customer.java", reports.get(1).getFileName());
	}
	
	@Test
    public void testFindPhoneInAllFiles() {
        String[] includes = {"**/*"};
        String[] words = {"phone"};
        
        finder.includes = includes;
        finder.words = words;
        finder.findText();
        
        List<Report> javaReports = finder.reports.get("java");
        assertEquals(2, javaReports.size());
        assertEquals("app/controllers/Dashboard.java", javaReports.get(0).getFileName());
        assertEquals("app/models/Customer.java", javaReports.get(1).getFileName());
        
        List<Report> htmlReports = finder.reports.get("html");
        assertEquals(2, htmlReports.size());
        assertEquals("app/views/errors/phone.html", htmlReports.get(0).getFileName());
        assertEquals("app/views/main.html", htmlReports.get(1).getFileName());
        
        List<Report> otherReports = finder.reports.get("other");
        assertEquals(1, otherReports.size());
        assertEquals("conf/messages", otherReports.get(0).getFileName());
    }
	
	@Test
    public void testFindPhoneCaseSensitiveInAllFiles() {
        String[] includes = {"**/*"};
        String[] words = {"Phone"};
        
        finder.includes = includes;
        finder.words = words;
        finder.caseSensitive = true;
        
        finder.findText();
        
        List<Report> reports = finder.reports.get("html");
        
        assertEquals(2, reports.size());
        assertEquals("app/views/errors/phone.html", reports.get(0).getFileName());
        assertEquals("app/views/main.html", reports.get(1).getFileName());
    }
	
	@Test
    public void testTryToBlankString() {
        String[] includes = {"**/*"};
        String[] words = {""};
        
        finder.includes = includes;
        finder.words = words;
        
        Map<String, List<Report>> reports = finder.findText().reports;
        assertEquals(0, reports.size());
        
        String[] spaces = {" ", " "};
        finder.words = spaces;
        
        reports = finder.findText().reports;
        assertEquals(0, reports.size());
    }
	
	@Test
	public void testTryToFindSimpleStringButNotFoundFile() {
		String[] includes = {"**/*.swp"};
		String[] words = {"consolided"};
		
		finder.includes = includes;
		finder.words = words;
		
		List<Report> reports = finder.findText().reports.get("swp");
		assertEquals(0, reports.size());
	}
	
	@Test
    public void testFindPhoneUsingRegexInHtmlFiles() {
        String[] includes = {"**/*.html"};
        String regexp = "Phone";
        
        finder.includes = includes;
        finder.regexp = regexp;
        finder.findText();
        
        List<Report> reports = finder.reports.get("html");
        
        assertEquals(2, reports.size());
        assertEquals("app/views/errors/phone.html", reports.get(0).getFileName());
        assertEquals("app/views/main.html", reports.get(1).getFileName());
    }
	
	@Test
    public void testFindPhoneUsingRegexInAllFiles() {
        String[] includes = {"**/*"};
        String regexp = "phone|cel";
        
        finder.includes = includes;
        finder.regexp = regexp;
        finder.findText();
        
        List<Report> javaReports = finder.reports.get("java");
        assertEquals(2, javaReports.size());
        assertEquals("app/controllers/Dashboard.java", javaReports.get(0).getFileName());
        assertEquals("app/models/Customer.java", javaReports.get(1).getFileName());
        
        List<Report> htmlReports = finder.reports.get("html");
        assertEquals(1, htmlReports.size());
        assertEquals("app/views/main.html", htmlReports.get(0).getFileName());
        
        List<Report> otherReports = finder.reports.get("other");
        assertEquals(1, otherReports.size());
        assertEquals("conf/messages", otherReports.get(0).getFileName());
    }
	
}
