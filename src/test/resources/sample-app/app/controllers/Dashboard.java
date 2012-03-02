package controllers;

import java.util.Date;
import java.util.List;

import models.Extraction;
import play.data.binding.As;
import play.mvc.Controller;
import wdev.helpers.BasicChartHelper;
import wdev.helpers.ExtractionHelper;

public class Dashboard extends Controller {
	
	public static void index(@As("yyyy-MM-dd") Date date) {
		
		try {
			List<Extraction> allExtractions = getAllExtractionsGroupedByDate();
			
			Date currentDate = getFromFirstExtractionIfIsNull(date, allExtractions);
			
			List<Extraction> extractionsByDate = Extraction.find("byExtractionDate", currentDate).fetch();
	
			Extraction consolided = new ExtractionHelper().getConsolided(extractionsByDate);
			
			BasicChartHelper chartHelper = new BasicChartHelper();
			consolided.setAverageBuildTimeBsad2(chartHelper.getBsad2MinDurationBuild(extractionsByDate) + chartHelper.getBsad2MaxDurationBuild(extractionsByDate) / 2);
			consolided.setAverageBuildTimeBsad3(chartHelper.getBsad3MinDurationBuild(extractionsByDate) + chartHelper.getBsad3MaxDurationBuild(extractionsByDate) / 2);

			render(currentDate, allExtractions, consolided);
		}
		catch (Exception e) {
			render();
		}
    }

	private static Date getFromFirstExtractionIfIsNull(Date date, List<Extraction> allExtractions) {
	    Date extractionDate = allExtractions.get(0).getExtractionDate();
		if (date != null) {
			extractionDate = date;
		}
		return extractionDate;
	}

	private static List<Extraction> getAllExtractionsGroupedByDate() {
		return Extraction.find("select e from extractions e group by e.extractionDate order by extractionDate desc").fetch();
	}
	
}
