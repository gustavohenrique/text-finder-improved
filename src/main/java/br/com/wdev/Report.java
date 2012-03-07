package br.com.wdev;

import java.io.Serializable;
import java.util.List;

public class Report implements Serializable {

    private static final long serialVersionUID = 9059825035315694714L;

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
