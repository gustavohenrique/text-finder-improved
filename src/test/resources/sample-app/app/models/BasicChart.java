package models;

import java.util.Date;

public class BasicChart {

	private Date extractionDate;
	
	private Double javadocs;
	
	private Double comments;
	
	private Double sources;
	
	private int rejectedReleases;
	
	private int valuedReleases;
	
	private int createdReleases;
	
	private int cobol;
	
	private int ejb;
	
	private int webservice;
	
	private Long bsad2MinDurationBuild;
	private Long bsad3MinDurationBuild;
	
	private Long bsad2MaxDurationBuild;
	private Long bsad3MaxDurationBuild;
	
	
	public Double getJavadocs() {
		return javadocs;
	}
	public void setJavadocs(Double javadocs) {
		this.javadocs = javadocs;
	}
	public Double getComments() {
		return comments;
	}
	public void setComments(Double comments) {
		this.comments = comments;
	}
	public Double getSources() {
		return sources;
	}
	public void setSources(Double sources) {
		this.sources = sources;
	}
	public Date getExtractionDate() {
		return extractionDate;
	}
	public void setExtractionDate(Date extractionDate) {
		this.extractionDate = extractionDate;
	}
	public int getRejectedReleases() {
		return rejectedReleases;
	}
	public void setRejectedReleases(int totalRejectedReleases) {
		this.rejectedReleases = totalRejectedReleases;
	}
	public int getValuedReleases() {
		return valuedReleases;
	}
	public void setValuedReleases(int totalValuedReleases) {
		this.valuedReleases = totalValuedReleases;
	}
	public int getCreatedReleases() {
		return createdReleases;
	}
	public void setCreatedReleases(int totalCreatedReleases) {
		this.createdReleases = totalCreatedReleases;
	}
	public int getCobol() {
		return cobol;
	}
	public void setCobol(int cobol) {
		this.cobol = cobol;
	}
	public int getEjb() {
		return ejb;
	}
	public void setEjb(int ejb) {
		this.ejb = ejb;
	}
	public int getWebservice() {
		return webservice;
	}
	public void setWebservice(int webservice) {
		this.webservice = webservice;
	}
	public Long getBsad2MinDurationBuild() {
		return bsad2MinDurationBuild;
	}
	public void setBsad2MinDurationBuild(Long bsad2MinDurationBuild) {
		this.bsad2MinDurationBuild = bsad2MinDurationBuild;
	}
	public Long getBsad3MinDurationBuild() {
		return bsad3MinDurationBuild;
	}
	public void setBsad3MinDurationBuild(Long bsad3MinDurationBuild) {
		this.bsad3MinDurationBuild = bsad3MinDurationBuild;
	}
	public Long getBsad2MaxDurationBuild() {
		return bsad2MaxDurationBuild;
	}
	public void setBsad2MaxDurationBuild(Long bsad2MaxDurationBuild) {
		this.bsad2MaxDurationBuild = bsad2MaxDurationBuild;
	}
	public Long getBsad3MaxDurationBuild() {
		return bsad3MaxDurationBuild;
	}
	public void setBsad3MaxDurationBuild(Long bsad3MaxDurationBuild) {
		this.bsad3MaxDurationBuild = bsad3MaxDurationBuild;
	}

	
}
