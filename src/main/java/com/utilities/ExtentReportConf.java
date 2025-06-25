package com.utilities;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import java.net.MalformedURLException;

public class ExtentReportConf {
	public static ExtentTest test;
	public static ExtentReports report;
	

	public static void reportSetUp(String Classname) throws MalformedURLException {
		report = new ExtentReports(System.getProperty("user.dir") + "/TestReport/" + Classname + ".html", false);
		report.addSystemInfo("Environment", "Pre-Production").addSystemInfo("User", "Shanawaz");
		test = report.startTest(Classname);

	}

	public static void reportTearDown() {
		report.endTest(test);
		report.flush();
		report.close();
	}

	public static void reportPassLog(String log) {
		test.log(LogStatus.PASS, log,"");
	}

	public static void reportPassLogwithSS(String log, String ss) {
		test.log(LogStatus.PASS, log, ExtentReportConf.test.addScreenCapture(ss));

	}

	public static void reportFailLog(String log) {
		test.log(LogStatus.FAIL, log,"");
	}

	public static void reportFailLogwithSS(String log, String ss) {
		test.log(LogStatus.FAIL,log,ExtentReportConf.test.addScreenCapture(ss));

	}
	
	public static void reportInfoLog(String log) {
		test.log(LogStatus.INFO, log,"");
	}
	
	public static void reportSkipLog(String log) {
		test.log(LogStatus.SKIP, log,"");
	}
	
	public static void reportErrorLog(String log) {
		test.log(LogStatus.ERROR, log,"");
	}
	
	public static void reportFatalLog(String log) {
		test.log(LogStatus.FATAL, log,"");
	}
	
	public static void reportWarningLog(String log) {
		test.log(LogStatus.WARNING, log,"");
	}
	
	public static void reportUnknownLog(String log) {
		test.log(LogStatus.UNKNOWN, log,"");
	}
	
}
