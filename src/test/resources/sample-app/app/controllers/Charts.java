package controllers;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import models.BasicChart;
import models.Extraction;
import play.mvc.Controller;
import wdev.helpers.BasicChartHelper;
import wdev.helpers.ExtractionHelper;

public class Charts extends Controller {

	public static void index() {
		ExtractionHelper extractionHelper = new ExtractionHelper();
		BasicChartHelper chartHelper = new BasicChartHelper();
		
		List<BasicChart> qualityCharts = new ArrayList<BasicChart>();
		
		List<Extraction> groupedByDate = Extraction.find("select e from extractions e group by e.extractionDate order by extractionDate desc").fetch(3);
		
		for (Extraction grouped : groupedByDate) {
			Date date = grouped.getExtractionDate();
			
			List<Extraction> extractions = Extraction.find("byExtractionDate", date).fetch();
			Extraction consolided = extractionHelper.getConsolided(extractions);
			consolided.setExtractionDate(date);
			
			BasicChart chart = chartHelper.getBasicChart(consolided);
			
			chart.setBsad2MinDurationBuild(chartHelper.getBsad2MinDurationBuild(extractions));
			chart.setBsad3MinDurationBuild(chartHelper.getBsad3MinDurationBuild(extractions));
			
			chart.setBsad2MaxDurationBuild(chartHelper.getBsad2MaxDurationBuild(extractions));
			chart.setBsad3MaxDurationBuild(chartHelper.getBsad3MaxDurationBuild(extractions));
			
			qualityCharts.add(chart);
		}
				
		render(qualityCharts);
	}

}
