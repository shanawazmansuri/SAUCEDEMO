package com.utilities;

import com.basepage.BasePage;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class ScreenShot extends BasePage {

	public static void captureScreenshot(WebDriver driver, String scrPath,
			String scrPrefix) {
		String path = System.getProperty("user.dir");
		String sourcePath = path + scrPath;
		String prefix = scrPrefix;

		SimpleDateFormat date = new SimpleDateFormat("yyyyMMdd-HHmmss");
		final String timeStamp = date.format(Calendar.getInstance().getTime());

		File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

		try {
			FileUtils.copyFile(src, new File(sourcePath + prefix + timeStamp
					+ ".png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	 public static String screenShotExtentReport(WebDriver driver,String screenShotName) throws IOException
	    {
	        TakesScreenshot ts = (TakesScreenshot)driver;
	        File source = ts.getScreenshotAs(OutputType.FILE);
	        String dest = System.getProperty("user.dir") +"\\ScreenShots\\"+screenShotName+".png";
	        File destination = new File(dest);
	        FileUtils.copyFile(source, destination);        
	                     
	        return dest;
	    }

}