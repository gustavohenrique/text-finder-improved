package models;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Date;
import java.util.Locale;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import play.db.jpa.Model;

@Entity(name="extractions")
public class Extraction extends Model implements Serializable {

	@OneToOne(cascade=CascadeType.ALL )
	private Job job;
	
	@Column(nullable=false)
	@Temporal(TemporalType.DATE)
	private Date extractionDate;
	
	@Column(nullable=false)
	@Temporal(TemporalType.DATE)
	private Date startDate;
	
	@Column(nullable=false)
	@Temporal(TemporalType.DATE)
	private Date endDate;
	
	private int totalApprovedReleases;
	private int totalRejectedReleases;
	private int totalValuedReleases;
	private int totalCreatedReleases;
	private int totalValuedBuilds;
	private int totalRejectedBuilds;
	private int totalApprovedBuilds;
	private int totalJavadocs;
	private int totalComments;
	private int totalSources;
	private int totalLines;
	private int totalCobol;
	private int totalWebservice;
	private int totalEjb;
	
	private int totalUniqueComponents;
	private int totalComponents;
	
	@Transient
	private Double totalComponentsReusedPercentual;
	
	@Transient
	private Double totalBsraComponentsReusedPercentual;
	
	@Transient
	private int totalComponentsReused;
	
	private int totalEnabledJobs;
	private int totalJobs;
	
	private Long bsad2MinDurationBuild;
	private Long bsad3MinDurationBuild;
	
	private Long bsad2MaxDurationBuild;
	private Long bsad3MaxDurationBuild;
	
	private Double commentsPercentual;
	private Double javadocsPercentual;
	private Double sourcePercentual;
	private Double rejectedReleasesPercentual;
	
	private Long averageBuildTimeBsad2;
	private Long averageBuildTimeBsad3;
	
	
	public int getTotalApprovedReleases() {
		return totalApprovedReleases;
	}
	
	public void setTotalApprovedReleases(int totalApprovedReleases) {
		this.totalApprovedReleases = totalApprovedReleases;
	}
	
	public int getTotalRejectedReleases() {
		return totalRejectedReleases;
	}
	
	public void setTotalRejectedReleases(int totalRejectedReleases) {
		this.totalRejectedReleases = totalRejectedReleases;
	}
	
	public int getTotalValuedReleases() {
		return totalValuedReleases;
	}
	
	public void setTotalValuedReleases(int totalValuedReleases) {
		this.totalValuedReleases = totalValuedReleases;
	}
	
	public int getTotalCreatedReleases() {
		return totalCreatedReleases;
	}
	
	public void setTotalCreatedReleases(int totalCreatedReleases) {
		this.totalCreatedReleases = totalCreatedReleases;
	}
	
	public int getTotalValuedBuilds() {
		return totalValuedBuilds;
	}
	
	public void setTotalValuedBuilds(int totalValuedBuilds) {
		this.totalValuedBuilds = totalValuedBuilds;
	}
	
	public int getTotalRejectedBuilds() {
		return totalRejectedBuilds;
	}
	
	public void setTotalRejectedBuilds(int totalRejectedBuilds) {
		this.totalRejectedBuilds = totalRejectedBuilds;
	}
	
	public int getTotalApprovedBuilds() {
		return totalApprovedBuilds;
	}
	
	public void setTotalApprovedBuilds(int totalApprovedBuilds) {
		this.totalApprovedBuilds = totalApprovedBuilds;
	}
	
	public int getTotalJavadocs() {
		return totalJavadocs;
	}
	
	public void setTotalJavadocs(int totalJavadocs) {
		this.totalJavadocs = totalJavadocs;
	}
	
	public int getTotalComments() {
		return totalComments;
	}
	
	public void setTotalComments(int totalComments) {
		this.totalComments = totalComments;
	}
	
	public int getTotalSources() {
		return totalSources;
	}
	
	public void setTotalSources(int totalSources) {
		this.totalSources = totalSources;
	}
	
	public int getTotalLines() {
		return totalLines;
	}
	
	public void setTotalLines(int totalLines) {
		this.totalLines = totalLines;
	}
	
	public int getTotalCobol() {
		return totalCobol;
	}
	
	public void setTotalCobol(int totalCobol) {
		this.totalCobol = totalCobol;
	}
	
	public int getTotalWebservice() {
		return totalWebservice;
	}
	
	public void setTotalWebservice(int totalWebservice) {
		this.totalWebservice = totalWebservice;
	}
	
	public int getTotalEjb() {
		return totalEjb;
	}
	
	public void setTotalEjb(int totalEjb) {
		this.totalEjb = totalEjb;
	}

	public Date getExtractionDate() {
		return extractionDate;
	}

	public void setExtractionDate(Date date) {
		this.extractionDate = date;
	}

	public Job getJob() {
		return job;
	}

	public void setJob(Job job) {
		this.job = job;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
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

	public int getTotalComponentsReused() {
		return totalEjb + totalWebservice + totalCobol;
	}
	
	public Double getCommentsPercentual() {
		commentsPercentual = calculatePercentual(totalComments, totalLines);
		return commentsPercentual;
	}
	
	public Double getJavadocsPercentual() {
		javadocsPercentual = calculatePercentual(totalJavadocs, totalLines);
		return javadocsPercentual;
	}
	
	public Double getSourcePercentual() {
		sourcePercentual = calculatePercentual(totalSources, totalLines);
		return sourcePercentual;
	}
	
	public Double getRejectedReleasesPercentual() {
		rejectedReleasesPercentual = calculatePercentual(totalRejectedReleases, totalValuedReleases);
		return rejectedReleasesPercentual;
	}
	
	public Long getAverageBuildTimeBsad2() {
		return averageBuildTimeBsad2;
	}

	public void setAverageBuildTimeBsad2(Long averageBuildTimeBsad2) {
		this.averageBuildTimeBsad2 = averageBuildTimeBsad2;
	}

	public Long getAverageBuildTimeBsad3() {
		return averageBuildTimeBsad3;
	}

	public void setAverageBuildTimeBsad3(Long averageBuildTimeBsad3) {
		this.averageBuildTimeBsad3 = averageBuildTimeBsad3;
	}

	public int getTotalEnabledJobs() {
		return totalEnabledJobs;
	}

	public void setTotalEnabledJobs(int totalEnabledJobs) {
		this.totalEnabledJobs = totalEnabledJobs;
	}

	public int getTotalJobs() {
		return totalJobs;
	}

	public void setTotalJobs(int totalJobs) {
		this.totalJobs = totalJobs;
	}

	public int getTotalUniqueComponents() {
		return totalUniqueComponents;
	}
	
	public void setTotalUniqueComponents(int totalUniqueComponents) {
		this.totalUniqueComponents = totalUniqueComponents;
	}

	public int getTotalComponents() {
		return totalComponents;
	}

	public void setTotalComponents(int totalComponents) {
		this.totalComponents = totalComponents;
	}
	
	public Double getTotalComponentsReusedPercentual() {
		return calculatePercentual(totalUniqueComponents, getTotalComponentsReused());
	}
	
	public Double getTotalBsraComponentsReusedPercentual() {
		return calculatePercentual(totalUniqueComponents, totalComponents);
	}
	
	private Double calculatePercentual(int value, double total) {
		Double result = (value / total) * 100.0;
		Locale locale = new Locale("en_US");
        DecimalFormatSymbols symbols = new DecimalFormatSymbols(locale);
        symbols.setDecimalSeparator('.');
        
		DecimalFormat formater = new DecimalFormat("#.##", symbols);
		try {
		    return Double.valueOf(formater.format(result));
		}
		catch (Exception e) {
			return 0.0;
		}
	}
}
