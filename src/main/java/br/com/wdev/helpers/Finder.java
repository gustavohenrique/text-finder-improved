package br.com.wdev.helpers;

import hudson.model.Result;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import org.apache.commons.io.IOUtils;
import org.apache.tools.ant.DirectoryScanner;

import br.com.wdev.model.Report;

public class Finder {
	
	private File workspace;

	private String[] includes;
	
	private String[] excludes;
	
	private String regexp;
	
	private boolean checkOnlyConsoleOutput;
	
	private boolean sensitive;
	
	private PrintStream logger;
	
	private Result buildResult;
	
	private List<Report> reports;

	
	public Finder(File workspace, String[] includes, String[] excludes, String regexp) {
		this.workspace = workspace;
		this.includes = includes;
		this.excludes = excludes;
        this.regexp = regexp;
        this.buildResult = Result.SUCCESS;
        this.checkOnlyConsoleOutput = false;
        
        this.reports = new ArrayList<Report>();
	}
	
	public Finder findText() {
		try {
			DirectoryScanner ds = getDirectoryScanner(workspace, includes, excludes);
			ds.scan();
            
			Pattern pattern = getPattern(regexp);
        	for (String includedFile : ds.getIncludedFiles()) {
        		Report report = findPatterInFile(pattern, new File(workspace, includedFile));
        		if (report != null) reports.add(report);
            }

        }
		catch (Exception e) {
			log(e.getMessage());
        	buildResult = Result.UNSTABLE;
        }
		
		return this;
	}
	
	private DirectoryScanner getDirectoryScanner(File workspace, String[] includes, String[] excludes) {
		DirectoryScanner ds = new DirectoryScanner();
		ds.setBasedir(workspace);
		ds.setCaseSensitive(sensitive);
		
		if (includes != null) {
			ds.setIncludes(includes);
		}
		
		if (excludes != null) {
			ds.setExcludes(excludes);
		}
		return ds;
	}

	private Report findPatterInFile(Pattern pattern, File file) throws IOException {
		Report report = null;
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String line;
            
        List<String> lines = new ArrayList<String>();
            
        while ((line = reader.readLine()) != null) {
            Matcher matcher = pattern.matcher(line);
            if (matcher.find()) {
                lines.add(line);
                
                if (checkOnlyConsoleOutput) break;
            }
        }
        
        if (lines.size() > 0) {
        	report = new Report(file.getName(), lines);
        }
            
        IOUtils.closeQuietly(reader);
		return report;
    }

    private Pattern getPattern(String regexp) throws Exception {
        try {
        	return Pattern.compile(regexp);
        }
        catch (PatternSyntaxException e) {
            log("Unable to compile regular expression: " + regexp);
            throw new Exception();
        }
    }
    
    public List<Report> getReports() {
		return reports;
	}
    
    public void checkOnlyConsoleOutput(boolean checkOnlyConsoleOutput) {
    	this.checkOnlyConsoleOutput = checkOnlyConsoleOutput;
    }
    
    public void setCaseSensitive(boolean sensitive) {
    	this.sensitive = sensitive;
    }
    
    public void setLogger(PrintStream logger) {
		this.logger = logger;
	}
    
    public void setBuildResult(String result) {
    	if ("success".equals(result)) {
        	this.buildResult = Result.SUCCESS; 
        }
        else {
        	this.buildResult = Result.UNSTABLE;
        }
    }
    
    private void log(String message) {
    	logger.println("Text Finder Improved: " + message);
    }
	
	public Result getBuildResult() {
		return buildResult;
	}

}
