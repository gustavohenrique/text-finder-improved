package wdev.helpers;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.List;
import java.util.Locale;

import models.BasicChart;
import models.Extraction;
import wdev.enums.CategoryEnum;

public class BasicChartHelper {

	
	public BasicChart getBasicChart(Extraction extraction) {
		BasicChart chart = new BasicChart();
		chart.setExtractionDate(extraction.getExtractionDate());
		
		chart = calculateQualityPercentual(extraction, chart);
		chart = calculateReleasesData(extraction, chart);
		chart = calculateComponentsReuses(extraction, chart);
		
		return chart;
	}
	
	public Long getBsad2MinDurationBuild(List<Extraction> extractions) {
		Long minDuration = Long.MAX_VALUE;
		for (Extraction extraction : extractions) {
			Long duration = extraction.getBsad2MinDurationBuild();
			
			if (duration != null && isBsad2(extraction) && duration < minDuration) {
				minDuration = duration;
			}
		}
		return convertToMinutes(minDuration);

	}

	public Long getBsad3MinDurationBuild(List<Extraction> extractions) {
		Long minDuration = Long.MAX_VALUE;
		for (Extraction extraction : extractions) {
			Long duration = extraction.getBsad3MinDurationBuild();
			
			if (duration != null && isBsad3(extraction) && duration < minDuration) {
				minDuration = duration;
			}
		}
		return convertToMinutes(minDuration);
	}
	
	public Long getBsad2MaxDurationBuild(List<Extraction> extractions) {
		Long maxDuration = Long.MIN_VALUE;
		for (Extraction extraction : extractions) {
			Long duration = extraction.getBsad2MaxDurationBuild();
			
			if (duration != null && isBsad2(extraction) && duration > maxDuration) {
				maxDuration = duration;
			}
		}
		return convertToMinutes(maxDuration);
	}

	public Long getBsad3MaxDurationBuild(List<Extraction> extractions) {
		Long maxDuration = Long.MIN_VALUE;
		for (Extraction extraction : extractions) {
			Long duration = extraction.getBsad3MaxDurationBuild();
			
			if (duration != null && isBsad3(extraction) && duration > maxDuration) {
				maxDuration = duration;
			}
		}
		return convertToMinutes(maxDuration);
	}
	

	private Long convertToMinutes(Long miliseconds) {
		long duration = ((miliseconds / 1000) / 60);
		if (duration == 0) {
			return 1l;
		}
		return duration;
	}
	
	private boolean isBsad2(Extraction extraction) {
		return CategoryEnum.BSAD2.toString().equals(extraction.getJob().getCategory());
	}
	
	private boolean isBsad3(Extraction extraction) {
		return CategoryEnum.BSAD3.toString().equals(extraction.getJob().getCategory());
	}
	
	private BasicChart calculateComponentsReuses(Extraction extraction, BasicChart chart) {
		chart.setCobol(extraction.getTotalCobol());
		chart.setEjb(extraction.getTotalEjb());
		chart.setWebservice(extraction.getTotalWebservice());
		return chart;
	}

	private BasicChart calculateReleasesData(Extraction extraction, BasicChart chart) {
		chart.setRejectedReleases(extraction.getTotalRejectedReleases());
		chart.setValuedReleases(extraction.getTotalValuedReleases());
		chart.setCreatedReleases(extraction.getTotalCreatedReleases());
		
		return chart;
	}

	private BasicChart calculateQualityPercentual(Extraction extraction, BasicChart chart) {
		double totalLines = extraction.getTotalLines() * 1.0;
		
		if (totalLines > 0) {
			Double sources = calculatePercentual(extraction.getTotalSources(), totalLines);
			chart.setSources(sources);
			
			Double javadocs = calculatePercentual(extraction.getTotalJavadocs(), totalLines);
			chart.setJavadocs(javadocs);
			
			Double comments = calculatePercentual(extraction.getTotalComments(), totalLines);
			chart.setComments(comments);
		}
		
		return chart;
	}

	private double calculatePercentual(int value, double total) {
		double result = (value / total) * 100.0;
		Locale locale = new Locale("en_US");
        DecimalFormatSymbols symbols = new DecimalFormatSymbols(locale);
        symbols.setDecimalSeparator('.');
        
		DecimalFormat formater = new DecimalFormat("#.##", symbols);
		
		return Double.valueOf(formater.format(result));
	}
	
}
