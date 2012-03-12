package br.com.wdev;

import hudson.model.Result;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.tools.ant.DirectoryScanner;


public class Finder implements Serializable {
	
    private static final long serialVersionUID = 1227802849153892243L;

    private File workspace;

	public String[] includes;
	
	public String[] excludes;
	
	public String[] words;
	
	public boolean checkOnlyConsoleOutput;
	
	public boolean caseSensitive;
	
	public Result buildResult;
	
	public int buildNumber;
	
	public List<Report> reports;

	
	public Finder(File workspace, String[] includes, String[] excludes, String[] words) {
		this.workspace = workspace;
		this.includes = includes;
		this.excludes = excludes;
        this.words = words;
        this.buildResult = Result.SUCCESS;
        this.checkOnlyConsoleOutput = false;
        
        this.reports = new ArrayList<Report>();
	}
	
	public Finder findText() {
	    if (isNotBlank(words)) {
    		try {
    			DirectoryScanner ds = getDirectoryScanner(workspace, includes, excludes);
    			ds.scan();
                
            	for (String includedFile : ds.getIncludedFiles()) {
            		Report report = findPatterInFile(words, new File(workspace, includedFile));
            		if (report != null) reports.add(report);
                }
    
            }
    		catch (Exception e) {
    			log(e.getMessage());
            	buildResult = Result.UNSTABLE;
            }
	    }
		
		return this;
	}
	
	private boolean isNotBlank(String[] words) {
        if (words.length > 0) {
            for (int i=0; i<words.length; i++) {
                if (StringUtils.isNotBlank(words[i])) {
                    return true;
                }
            }
        }
        return false;
    }

    private DirectoryScanner getDirectoryScanner(File workspace, String[] includes, String[] excludes) {
		DirectoryScanner ds = new DirectoryScanner();
		ds.setBasedir(workspace);
		ds.setCaseSensitive(caseSensitive);
		
		if (includes != null) {
			ds.setIncludes(includes);
		}
		
		if (excludes != null) {
			ds.setExcludes(excludes);
		}
		return ds;
	}

	private Report findPatterInFile(String[] words, File file) throws IOException {
		Report report = null;
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String line;
            
        List<String> lines = new ArrayList<String>();
            
        while ((line = reader.readLine()) != null) {
            for (String word : words) {
                
                if (! caseSensitive) {
                    line = StringUtils.lowerCase(line);
                    word = StringUtils.lowerCase(word);
                }
                
                if (line.contains(word)) {
                    line = StringUtils.trim(line);
                    lines.add(line);
                    
                    if (checkOnlyConsoleOutput) break;
                }
            }
        }
        
        if (lines.size() > 0) {
        	report = new Report(file.getName(), lines);
        }
            
        IOUtils.closeQuietly(reader);
		return report;
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
//    	logger.println("Text Finder Improved: " + message);
    	System.out.println("Text Finder Improved: " + message);
    }
  
	
}
