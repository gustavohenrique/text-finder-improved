package br.com.wdev.helpers;

import hudson.model.Result;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;

import br.com.wdev.model.Report;

public class FinderTest extends TestCase {

	private File workspace;
	
	@Before
	public void setUp() throws Exception {
		workspace = new File("src/test/resources/sample-app");
	}
	
	@Test
	public void testFindSimpleStringInJavaFiles() throws FileNotFoundException {
		String[] includes = {"**/*.java"};
		String regexp = "consolided";
		
		Finder finder = new Finder(workspace, includes, null, regexp);
		finder.setBuildResult("success");
		
		finder.findText();
		assertEquals(Result.SUCCESS, finder.buildResult);
		
		List<Report> reports = finder.reports;
		assertEquals(3, reports.size());
		
		assertEquals("Charts.java", reports.get(0).getFileName());
		assertEquals("Dashboard.java", reports.get(1).getFileName());
		assertEquals("ExtractionHelper.java", reports.get(2).getFileName());
	}
	
	@Test
	public void testFindTestInApplicationConf() throws FileNotFoundException {
		String[] includes = {"**/application.conf"};
		String regexp = "test";
		
		Finder finder = new Finder(workspace, includes, null, regexp);
		finder.setBuildResult("unstable");
		finder.checkOnlyConsoleOutput = false;
		finder.findText();
		
		assertEquals(Result.UNSTABLE, finder.buildResult);
		
		List<Report> reports = finder.reports;
		assertEquals(1, reports.size());
		
		Report report = reports.get(0);
		assertEquals(5, report.getLines().size());
		assertEquals("application.conf", report.getFileName());

		List<String> lines = report.getLines();
		assertEquals("%test.db.url=jdbc:h2:file:/tmp/sample-app.db;DB_CLOSE_DELAY=-1;MVCC=TRUE", lines.get(0));
		assertEquals("%test.db.driver=org.h2.Driver", lines.get(1));
		assertEquals("%test.db.user=sa", lines.get(2));
		assertEquals("%test.db.pass=", lines.get(3));
		assertEquals("%test.jpa.dialect=org.hibernate.dialect.H2Dialect", lines.get(4));
		
	}
	
	@Test
	public void testFindRegexUsingOrInOneFile() throws FileNotFoundException {
		String[] includes = {"**/application.conf"};
		String regexp = "ddl=update|test.jpa";
		
		Finder finder = new Finder(workspace, includes, null, regexp);
		finder.setBuildResult("unstable");
		finder.checkOnlyConsoleOutput = false;
		finder.findText();
		
		assertEquals(Result.UNSTABLE, finder.buildResult);
		
		Report report = finder.reports.get(0);
		
		List<String> lines = report.getLines();
		assertEquals("%prod.jpa.ddl=update", lines.get(0));
		assertEquals("%test.jpa.dialect=org.hibernate.dialect.H2Dialect", lines.get(1));
	}
	
	@Test
	public void testFindRegexUsingOrInTwoFiles() throws FileNotFoundException {
		String[] includes = {"**/application.conf", "**/messages"};
		String regexp = ".*(H2Dialect|app).*";
		
		Finder finder = new Finder(workspace, includes, null, regexp);
		finder.setBuildResult("unstable");
		finder.checkOnlyConsoleOutput = false;
		finder.findText();
		
		assertEquals(2, finder.reports.size());
		
		List<String> applicationConfLines = finder.reports.get(0).getLines();
		assertEquals("application.name=dashboard", applicationConfLines.get(0));
		assertEquals("application.mode=dev", applicationConfLines.get(1));
		assertEquals("%prod.application.mode=prod", applicationConfLines.get(2));
		assertEquals("application.secret=dNTNhN5fQaqIYm8wggyujJx0vQVjtVSxrs45iJjvKQPOQlLBR7aWs8ZwnIpktjB6", applicationConfLines.get(3));
		
		List<String> messagesLines = finder.reports.get(1).getLines();
		assertEquals("title=Simple app sample for test with H2Dialect", messagesLines.get(0));
	}
	
	@Test
	public void testFindTwoWordsInTwoFilesFromLeftToRight() throws FileNotFoundException {
		String[] includes = {"**/application.conf", "**/messages"};
		String regexp = ".*(app).*(H2Dialect).*";
		
		Finder finder = new Finder(workspace, includes, null, regexp);
		finder.setBuildResult("unstable");
		finder.checkOnlyConsoleOutput = false;
		finder.findText();
		
		assertEquals(1, finder.reports.size());
		
		List<String> messagesLines = finder.reports.get(0).getLines();
		assertEquals("title=Simple app sample for test with H2Dialect", messagesLines.get(0));
	}
	
	@Test
	public void testFindRegexUsingAndInTwoFilesInAnyOrder() throws FileNotFoundException {
		String[] includes = {"**/application.conf", "**/messages"};
		String regexp = "(.*(H2Dialect).*(app).*)|(.*(app).*(H2Dialect).*)";
		
		Finder finder = new Finder(workspace, includes, null, regexp);
		finder.setBuildResult("unstable");
		finder.checkOnlyConsoleOutput = false;
		finder.findText();
		
		assertEquals(1, finder.reports.size());
		
		List<String> messagesLines = finder.reports.get(0).getLines();
		assertEquals("title=Simple app sample for test with H2Dialect", messagesLines.get(0));
	}
	
	@Test
	public void testFindRegexUsingExcludes() throws FileNotFoundException {
		String[] includes = {"**/messages", "**/application.conf"};
		String[] excludes = {"**/application.conf"};
		String regexp = ".*(H2Dialect|app).*";
		
		Finder finder = new Finder(workspace, includes, excludes, regexp);
		finder.setBuildResult("unstable");
		finder.checkOnlyConsoleOutput = false;
		finder.findText();
		
		assertEquals(1, finder.reports.size());
		
		List<String> messagesLines = finder.reports.get(0).getLines();
		assertEquals("title=Simple app sample for test with H2Dialect", messagesLines.get(0));
	}
	
	@Test
	public void testTryToFindSimpleStringButNotFoundFile() throws FileNotFoundException {
		String[] includes = {"**/*.swp"};
		String regexp = "consolided";
		
		Finder finder = new Finder(workspace, includes, null, regexp);
		
		List<Report> reports = finder.findText().reports;
		assertEquals(0, reports.size());
	}
	
	
}
