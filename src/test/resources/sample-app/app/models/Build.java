package models;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import play.db.jpa.Model;

@Entity(name="builds")
public class Build extends Model implements Serializable {

	private String dirName;
	
	private String dirPath;
	
	@Column(nullable=true)
	private Long duration;
	
	@Column(nullable=true)
	private int javadocs;
	
	@Column(nullable=true)
	private int commentLines;
    
	@Column(nullable=true)
	private int totalSources;
	
	@Column(nullable=true)
    private String seloQualidade;

    private boolean isRelease;
	
    private boolean isSuccess;
	
	@ManyToOne(cascade=CascadeType.ALL)
	private Job job;
	
	@Column(nullable=false)
	@Temporal(TemporalType.DATE)
	private Date date;

	
	public String getDirName() {
		return dirName;
	}

	public void setDirName(String dirName) {
		this.dirName = dirName;
	}

	public String getDirPath() {
		return dirPath;
	}

	public void setDirPath(String dirPath) {
		this.dirPath = dirPath;
	}

	public Long getDuration() {
		return duration;
	}

	public void setDuration(Long duration) {
		this.duration = duration;
	}

	public int getJavadocs() {
		return javadocs;
	}

	public void setJavadocs(int javadocs) {
		this.javadocs = javadocs;
	}

	public int getCommentLines() {
		return commentLines;
	}

	public void setCommentLines(int commentLines) {
		this.commentLines = commentLines;
	}

	public int getTotalSources() {
		return totalSources;
	}

	public void setTotalSources(int totalSources) {
		this.totalSources = totalSources;
	}

	public String getSeloQualidade() {
		return seloQualidade;
	}

	public void setSeloQualidade(String seloQualidade) {
		this.seloQualidade = seloQualidade;
	}

	public boolean isRelease() {
		return isRelease;
	}

	public void setRelease(boolean isRelease) {
		this.isRelease = isRelease;
	}

	public boolean isSuccess() {
		return isSuccess;
	}

	public void setSuccess(boolean isSuccess) {
		this.isSuccess = isSuccess;
	}

	public Job getJob() {
		return job;
	}

	public void setJob(Job job) {
		this.job = job;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
	
		
}