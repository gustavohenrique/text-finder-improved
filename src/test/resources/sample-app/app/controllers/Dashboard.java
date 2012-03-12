package controllers;

import java.util.Date;
import java.util.List;

import models.Extraction;
import play.data.binding.As;
import play.mvc.Controller;
import wdev.helpers.BasicChartHelper;
import wdev.helpers.ExtractionHelper;

public class Telephones extends Controller {
	
	public static void index(@As("yyyy-MM-dd") Date date) {
		
 		try {
            // add phone here
            int phone = 123456;
			render(phone);
		}
		catch (Exception e) {
			render();
		}
    }

}
