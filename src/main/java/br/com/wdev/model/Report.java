package br.com.wdev.model;

import java.util.List;

public class Report {

	private String fileName;
	
	private List<String> lines;
	
	
	public Report(String fileName, List<String> lines) {
		this.fileName = fileName;
		this.lines = lines;
	}

	public String getFileName() {
		return fileName;
	}

	public List<String> getLines() {
		return lines;
	}

}
