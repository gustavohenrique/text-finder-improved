package wdev.helpers;

import java.util.List;

import models.Extraction;

import org.junit.Before;
import org.junit.Test;

import play.test.Fixtures;
import play.test.UnitTest;

public class ExtractionHelperTest extends UnitTest {
	
	@Before
	public void setUp() {
		Fixtures.deleteAllModels();
        Fixtures.loadModels("data.yml");
	}

	@Test
	public void testCalculatePercentualOfExtractionData() {
		List<Extraction> extractions = Extraction.findAll(); 
		
		Extraction extraction = new ExtractionHelper().getConsolided(extractions);
		
		assertEquals(new Double(28.0), extraction.getCommentsPercentual());
		assertEquals(new Double(25.33), extraction.getJavadocsPercentual());
		assertEquals(new Double(46.67), extraction.getSourcePercentual());

		assertEquals(new Double(25.0), extraction.getRejectedReleasesPercentual());
		assertEquals(1, extraction.getTotalApprovedReleases());
		
		assertEquals(new Double(0.29), extraction.getTotalBsraComponentsReusedPercentual());
		assertEquals(new Double(62.5), extraction.getTotalComponentsReusedPercentual());

		assertEquals(5, extraction.getTotalCobol());
		assertEquals(2, extraction.getTotalEjb());
		assertEquals(1, extraction.getTotalWebservice());
		
		assertEquals(4, extraction.getTotalCreatedReleases());
		assertEquals(1, extraction.getTotalRejectedReleases());
		assertEquals(4, extraction.getTotalValuedReleases());

		assertEquals(3, extraction.getTotalApprovedBuilds());
		assertEquals(1, extraction.getTotalRejectedBuilds());
		assertEquals(2, extraction.getTotalValuedBuilds());
		
		assertEquals(75000, extraction.getTotalLines());
		assertEquals(19000, extraction.getTotalJavadocs());
		assertEquals(35000, extraction.getTotalSources());

		assertEquals(21000, extraction.getTotalComments());
		assertEquals(1700, extraction.getTotalComponents());
		assertEquals(8, extraction.getTotalComponentsReused());
		assertEquals(5, extraction.getTotalUniqueComponents());
		
		assertEquals(1, extraction.getTotalEnabledJobs());
		assertEquals(2, extraction.getTotalJobs());
	}
}
