package br.com.wdev;

import hudson.model.Result;

import java.io.File;
import java.util.List;

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
	public void testFindPhoneInJavaFiles() {
		String[] includes = {"**/*.java"};
		String[] words = {"phone"};
		
		finder.includes = includes;
		finder.words = words;
		finder.setBuildResult("success");
		finder.findText();

		List<Report> reports = finder.reports;

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
        
        List<Report> reports = finder.reports;
        
        assertEquals(5, reports.size());
        assertEquals("app/controllers/Dashboard.java", reports.get(0).getFileName());
        assertEquals("app/models/Customer.java", reports.get(1).getFileName());
        assertEquals("app/views/errors/phone.html", reports.get(2).getFileName());
        assertEquals("app/views/main.html", reports.get(3).getFileName());
        assertEquals("conf/messages", reports.get(4).getFileName());
    }
	
	@Test
    public void testFindPhoneCaseSensitiveInAllFiles() {
        String[] includes = {"**/*"};
        String[] words = {"Phone"};
        
        finder.includes = includes;
        finder.words = words;
        finder.caseSensitive = true;
        
        finder.findText();
        
        List<Report> reports = finder.reports;
        
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
        
        List<Report> reports = finder.findText().reports;
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
		
		List<Report> reports = finder.findText().reports;
		assertEquals(0, reports.size());
	}
	
	@Test
    public void testFindPhoneUsingRegexInHtmlFiles() {
        String[] includes = {"**/*.html"};
        String regexp = "Phone";
        
        finder.includes = includes;
        finder.regexp = regexp;
        finder.findText();
        
        List<Report> reports = finder.reports;
        
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
        
        List<Report> reports = finder.reports;
        
        assertEquals(4, reports.size());
        assertEquals("app/controllers/Dashboard.java", reports.get(0).getFileName());
        assertEquals("app/models/Customer.java", reports.get(1).getFileName());
        assertEquals("app/views/main.html", reports.get(2).getFileName());
        assertEquals("conf/messages", reports.get(3).getFileName());
    }
	
}
