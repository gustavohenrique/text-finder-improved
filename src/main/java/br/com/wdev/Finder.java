package br.com.wdev;

import hudson.model.Result;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.tools.ant.DirectoryScanner;


public class Finder implements Serializable {
	
    private static final long serialVersionUID = 1227802849153892243L;

    private File workspace;

	public String[] includes;
	
	public String[] excludes;
	
	public String[] words;
	
	public String regexp;
	
	public boolean checkOnlyConsoleOutput;
	
	public boolean caseSensitive;
	
	public Result buildResult;
	
	public int buildNumber;
	
	public List<Report> reports;

	
	public Finder(File workspace) {
		this.workspace = workspace;
        this.buildResult = Result.SUCCESS;
        this.checkOnlyConsoleOutput = false;
        
        this.reports = new ArrayList<Report>();
	}
	
	public Finder findText() {
	    boolean hasWords = isNotBlank(words);
	    boolean hasRegexp = isValid(regexp);
	    
	    if ( hasWords || hasRegexp ) {
        	for (String includedFile : getFoundFiles()) {
        	    Report report = hasWords ? find(includedFile, "words") : find(includedFile, "regex");
        		if (report != null) reports.add(report);
            }
	    }
		
		return this;
	}
	
	private boolean isValid(String regexp) {
        try {
            Pattern.compile(regexp);
            return true;
        }
        catch (Exception e) {
            return false;
        }
    }

    private String[] getFoundFiles() {
	    try {
            DirectoryScanner ds = getDirectoryScanner(workspace, includes, excludes);
            ds.scan();
            return ds.getIncludedFiles();
        }
        catch (Exception e) {
            return new String[] {};
        }
	}
	
	private boolean isNotBlank(String[] words) {
        if (words != null) {
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
    
    private Report find(String includedFile, String method) {
        try {
            File file = new File(workspace, includedFile);
            BufferedReader reader = new BufferedReader(new FileReader(file));
            
            Report report = "regex".equals(method) ? findRegexInFile(reader, includedFile) : findWordsInFile(reader, includedFile); 
            
            IOUtils.closeQuietly(reader);
            return report;
        }
        catch (Exception e) {
            return null;
        }
    }
    
    private Report findRegexInFile(BufferedReader reader, String includedFile) throws IOException {
        List<String> lines = new ArrayList<String>();
        String line;
        while ((line = reader.readLine()) != null) {
            Pattern pattern = Pattern.compile(regexp);
            Matcher matcher = pattern.matcher(line);
            
            if (matcher.find()) {
                lines.add(line);
                if (checkOnlyConsoleOutput) break;
            }
        }
        
        if (lines.size() > 0) {
            return new Report(includedFile, lines);
        }
        return null;
    }

	private Report findWordsInFile(BufferedReader reader, String includedFile) throws IOException {
	    List<String> lines = new ArrayList<String>();
	    String line;
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
        	return new Report(includedFile, lines);
        }
            
        return null;
    }

    public void setBuildResult(String result) {
    	if ("success".equals(result)) {
        	this.buildResult = Result.SUCCESS; 
        }
        else {
        	this.buildResult = Result.UNSTABLE;
        }
    }
	
}
