package br.com.wdev;

import hudson.model.Result;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;

public class FinderTest extends TestCase {

	private File workspace;
	
	@Before
	public void setUp() throws Exception {
		workspace = new File("src/test/resources/sample-app");
	}
	
	@Test
	public void testFindPhoneInJavaFiles() {
		String[] includes = {"**/*.java"};
		String[] words = {"phone"};
		
		Finder finder = new Finder(workspace, includes, null, words);
		finder.setBuildResult("success");
		finder.findText();

		List<Report> reports = finder.reports;

		assertEquals(2, reports.size());
		assertEquals(Result.SUCCESS, finder.buildResult);
		assertEquals("Dashboard.java", reports.get(0).getFileName());
		assertEquals("Customer.java", reports.get(1).getFileName());
	}
	
	@Test
    public void testFindPhoneInAllFiles() {
        String[] includes = {"**/*"};
        String[] words = {"phone"};
        
        Finder finder = new Finder(workspace, includes, null, words);
        finder.findText();
        
        List<Report> reports = finder.reports;
        
        assertEquals(5, reports.size());
        assertEquals("Dashboard.java", reports.get(0).getFileName());
        assertEquals("Customer.java", reports.get(1).getFileName());
        assertEquals("phone.html", reports.get(2).getFileName());
        assertEquals("main.html", reports.get(3).getFileName());
        assertEquals("messages", reports.get(4).getFileName());
    }
	
	@Test
    public void testFindPhoneCaseSensitiveInAllFiles() {
        String[] includes = {"**/*"};
        String[] words = {"Phone"};
        
        Finder finder = new Finder(workspace, includes, null, words);
        finder.caseSensitive = true;
        
        finder.findText();
        
        List<Report> reports = finder.reports;
        
        assertEquals(2, reports.size());
        assertEquals("phone.html", reports.get(0).getFileName());
        assertEquals("main.html", reports.get(1).getFileName());
    }
	
	@Test
    public void testTryToBlankString() {
        String[] includes = {"**/*"};
        String[] words = {""};
        
        Finder finder = new Finder(workspace, includes, null, words);
        
        List<Report> reports = finder.findText().reports;
        assertEquals(0, reports.size());
        
        String[] spaces = {" ", " "};
        Finder otherFinder = new Finder(workspace, includes, null, spaces);
        
        reports = otherFinder.findText().reports;
        assertEquals(0, reports.size());
    }
	
	@Test
	public void testTryToFindSimpleStringButNotFoundFile() {
		String[] includes = {"**/*.swp"};
		String[] words = {"consolided"};
		
		Finder finder = new Finder(workspace, includes, null, words);
		
		List<Report> reports = finder.findText().reports;
		assertEquals(0, reports.size());
	}
	
}
