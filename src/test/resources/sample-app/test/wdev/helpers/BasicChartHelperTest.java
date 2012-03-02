package wdev.helpers;

import java.util.ArrayList;
import java.util.List;

import models.BasicChart;
import models.Extraction;
import models.Job;

import org.junit.Before;
import org.junit.Test;

import play.test.Fixtures;
import play.test.UnitTest;
import wdev.enums.CategoryEnum;

public class BasicChartHelperTest extends UnitTest {
	
	@Before
	public void setUp() {
		Fixtures.deleteAllModels();
        Fixtures.loadModels("data.yml");
	}

	@Test
	public void testCalculatePercentualOfExtractionData() {
		Extraction extraction = new Extraction();
		extraction.setTotalLines(552472);
		extraction.setTotalSources(301882);
		extraction.setTotalComments(24668);
		extraction.setTotalJavadocs(225992);
		
		extraction.setTotalRejectedReleases(1);
		extraction.setTotalValuedReleases(17);
		extraction.setTotalCreatedReleases(25);
		
		extraction.setTotalCobol(126);
		extraction.setTotalEjb(88);
		extraction.setTotalWebservice(109);
		
		BasicChart chart = new BasicChartHelper().getBasicChart(extraction);
		
		assertEquals(new Double(54.64), chart.getSources());
		assertEquals(new Double(4.47), chart.getComments());
		assertEquals(new Double(40.91), chart.getJavadocs());
		
		assertEquals(1, chart.getRejectedReleases());
		assertEquals(17, chart.getValuedReleases());
		assertEquals(25, chart.getCreatedReleases());
		
		assertEquals(126, chart.getCobol());
		assertEquals(88, chart.getEjb());
		assertEquals(109, chart.getWebservice());
	}
	
	@Test
	public void testGetMinDurationBuild() {
		Job bsad2Job = new Job("SIAW-AntifurtoComodato");
		bsad2Job.setCategory(CategoryEnum.BSAD2.toString());
		
		Job bsad3Job = new Job("INET-KitDigitalAuto");
		bsad3Job.setCategory(CategoryEnum.BSAD3.toString());
		
		Extraction e1 = new Extraction();
		e1.setBsad2MinDurationBuild(200000l);
		e1.setJob(bsad2Job);
		
		Extraction e2 = new Extraction();
		e2.setBsad3MinDurationBuild(300000l);
		e2.setJob(bsad3Job);
		
		List<Extraction> extractions = new ArrayList<Extraction>();
		extractions.add(e1);
		extractions.add(e2);
		
		BasicChartHelper chartHelper = new BasicChartHelper();
		Long minDurationBsad2 = chartHelper.getBsad2MinDurationBuild(extractions);
		Long minDurationBsad3 = chartHelper.getBsad3MinDurationBuild(extractions);
		
		assertEquals(new Long(3), minDurationBsad2);
		assertEquals(new Long(5), minDurationBsad3);
	}
	
	@Test
	public void testGetMaxDurationBuild() {
		Job bsad2Job = new Job("SIAW-AntifurtoComodato");
		bsad2Job.setCategory(CategoryEnum.BSAD2.toString());
		
		Job bsad3Job = new Job("INET-KitDigitalAuto");
		bsad3Job.setCategory(CategoryEnum.BSAD3.toString());
		
		Extraction e1 = new Extraction();
		e1.setBsad2MaxDurationBuild(200000l);
		e1.setJob(bsad2Job);
		
		Extraction e2 = new Extraction();
		e2.setBsad3MaxDurationBuild(300000l);
		e2.setJob(bsad3Job);
		
		List<Extraction> extractions = new ArrayList<Extraction>();
		extractions.add(e1);
		extractions.add(e2);
		
		BasicChartHelper chartHelper = new BasicChartHelper();
		Long maxDurationBsad2 = chartHelper.getBsad2MaxDurationBuild(extractions);
		Long maxDurationBsad3 = chartHelper.getBsad3MaxDurationBuild(extractions);
		
		assertEquals(new Long(3), maxDurationBsad2);
		assertEquals(new Long(5), maxDurationBsad3);
	}
}
