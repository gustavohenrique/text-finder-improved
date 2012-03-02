package wdev.helpers;

import java.util.Calendar;
import java.util.List;

import models.Extraction;

public class ExtractionHelper {
	
	private int totalApprovedReleases = 0;
    private int totalRejectedReleases = 0;
    private int totalValuedReleases = 0;
    private int totalCreatedReleases = 0;

    private int totalValuedBuilds = 0;
    private int totalRejectedBuilds = 0;
    private int totalApprovedBuilds = 0;

    private int totalJavadocs = 0;
    private int totalComments = 0;
    private int totalSources = 0;
    private int totalLines = 0;

    private int totalCobol = 0;
    private int totalWebservice = 0;
    private int totalEjb = 0;

    private int totalJobs = 0;
    private int totalEnabledJobs = 0;

    private int totalComponents = 0;
    private int totalUniqueComponents = 0;
	
	
	public Extraction getConsolided(List<Extraction> extractions) {
		for (Extraction extraction : extractions) {
			calculateSumOf(extraction);
		}
		
		Extraction consolided = new Extraction();
		consolided.setTotalApprovedReleases(totalApprovedReleases);
		consolided.setTotalRejectedReleases(totalRejectedReleases);
		consolided.setTotalValuedReleases(totalValuedReleases);
		consolided.setTotalCreatedReleases(totalCreatedReleases);
		
		consolided.setTotalValuedBuilds(totalValuedBuilds);
		consolided.setTotalRejectedBuilds(totalRejectedBuilds);
		consolided.setTotalApprovedBuilds(totalApprovedBuilds);
		
		consolided.setTotalJavadocs(totalJavadocs);
		consolided.setTotalComments(totalComments);
		consolided.setTotalSources(totalSources);
		consolided.setTotalLines(totalLines);
		
		consolided.setTotalCobol(totalCobol);
		consolided.setTotalWebservice(totalWebservice);
		consolided.setTotalEjb(totalEjb);
		
		consolided.setTotalUniqueComponents(totalUniqueComponents);
		consolided.setTotalComponents(totalComponents);
		
		consolided.setTotalEnabledJobs(totalEnabledJobs);
		consolided.setTotalJobs(totalJobs);
		
		consolided.setExtractionDate(Calendar.getInstance().getTime());
		
		return consolided;
	}

	private void calculateSumOf(Extraction extraction) {
		totalApprovedReleases += extraction.getTotalApprovedReleases();
		totalRejectedReleases += extraction.getTotalRejectedReleases();
		totalValuedReleases += extraction.getTotalValuedReleases();
		totalCreatedReleases += extraction.getTotalCreatedReleases();
		
		totalValuedBuilds += extraction.getTotalValuedBuilds();
		totalRejectedBuilds += extraction.getTotalRejectedBuilds();
		totalApprovedBuilds += extraction.getTotalApprovedBuilds();
		
		totalJavadocs += extraction.getTotalJavadocs();
		totalComments += extraction.getTotalComments();
		totalSources += extraction.getTotalSources();
		totalLines += extraction.getTotalLines();
		
		totalCobol += extraction.getTotalCobol();
		totalWebservice += extraction.getTotalWebservice();
		totalEjb += extraction.getTotalEjb();
		
		totalUniqueComponents += extraction.getTotalUniqueComponents();
		totalComponents = extraction.getTotalComponents();
		
		totalJobs = extraction.getTotalJobs();
		totalEnabledJobs = extraction.getTotalEnabledJobs();
	}
}
