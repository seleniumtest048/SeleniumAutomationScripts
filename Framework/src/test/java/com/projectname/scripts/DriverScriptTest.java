package com.projectname.scripts;
import jxl.Sheet;
import jxl.write.Label;

import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.projectname.base.EmailReportUtil;
import com.projectname.base.Keywords;
import com.projectname.base.ReportUtil;
import com.projectname.utils.TestConstants;
import com.projectname.utils.TestUtil;

public class DriverScriptTest extends Keywords {
	
	public static String keyword;
	public static String stepDescription;
	public static String result;
	public static DriverScriptTest dstest;
	static float totalTestCaseCount,runTestCaseCount=0,failedTestCases;
	@BeforeClass
	public void beforeClass() throws Exception{
		initialize();
		log.info("Initialized All Resources Files");		
		setTestClassName(DriverScriptTest.this.getClass().getName());	
		log.info("Creatng Driver Script Object");
		dstest=new DriverScriptTest();
		log.info("Creating Test Suite");
		startTesting();
		log.info("Creating Test Suite For Email Report");
		emailStartTesting() ;		
	}
	@Test
	public void driverScript() throws Exception {
		log.info("Creating Suite In Detailed Test Report");
		ReportUtil.startSuite("Suite");
		log.info("Creating Suite In Mailing Report");
		EmailReportUtil.startSuite("Suite");
		// test data colom and test results starting row
		int colom=3,excelrow=1;
		int sno=1;
		log.info("Creating Excel Sheet For Test Results");
		generateExcel(sno);
		controlshet=controllerwb.getSheet("Suite");
		totalTestCaseCount=controlshet.getRows()-1;
		log.info("Total No.of Test Cases  "+totalTestCaseCount);
		log.info("Launching Browser");
		openBrowser();
		log.info("Loading Controller Work Book");
		for (int i = 1; i < controlshet.getRows(); i++) {
			log.info("Selecting Test Scenario From Controller File");
			String tsrunmode=controlshet.getCell(2,i).getContents();
			if (tsrunmode.equalsIgnoreCase("Y")) {
				runTestCaseCount++;
				log.info("Navigate To Test Scenario Sheet");
				String tcaseid=controlshet.getCell(0,i).getContents();
				Sheet tdsheet1=testdatawb.getSheet(tcaseid);
				//control sheet
				Sheet controlshet=controllerwb.getSheet(tcaseid);
				String fileName=null;
				log.info("Loading Test Data Work Book");
				for (int j = 1; j < tdsheet1.getRows(); j++) {
					String tcaserunmode=tdsheet1.getCell(2,j).getContents();
					if (tcaserunmode.equalsIgnoreCase("y")) {
						String testcaseid=tdsheet1.getCell(0,j).getContents();
						String testdesc=tdsheet1.getCell(1,j).getContents();
						fileName = "Suite1_TC"+(testcaseid)+"_TS"+tcaseid+"_"+keyword+j+".png";
						stepDescription=testdesc;
						keyword=testcaseid;
						log.info("Passing Parameters Driver Script to ContolScript");
						result=controlScript(j, colom, tcaseid,controlshet,testcaseid,stepDescription,keyword,fileName);
						if (failcount>=1 || rptFailCnt>=1) {
							result="Fail";
							log.info("Test Scenario Result --"+ result);
							if (failcount==0) {
								failedTestCases+=rptFailCnt;
							}else{
								failedTestCases+=failcount;
							}
							mainReport(keyword,result,fileName);
							createLabel(excelrow, testcaseid, testdesc, result);
							rptFailCnt=0;
							failcount=0;
						}else{
							result="Pass";
							log.info("Test Scenario Result --"+ result);
							mainReport(keyword,result,fileName);
							createLabel(excelrow, testcaseid, testdesc, result);
						}
						
					}  
					excelrow++;
				}
			}
			controlshet=controllerwb.getSheet("Suite");
		}
		float totalPassCount=runTestCaseCount-failedTestCases;
		System.out.println("Total Run Test Case Count ---"+runTestCaseCount);
		System.out.println("Total Failed Test Case Count--"+failedTestCases);
		System.out.println("Total Passed Test Cases ----"+totalPassCount);
		closeBrowser();
		wwb.write();
		wwb.close();
		float passPercentage=(totalPassCount/runTestCaseCount)*100;
		float failPercentage=(failedTestCases/runTestCaseCount)*100;
		System.out.println(passPercentage);
		System.out.println(failPercentage);
		dstest.generateCsvFile(0,passPercentage,failPercentage);
		generatePieChart();
	}
	@AfterSuite
	public static void endScript() throws Exception{
		ReportUtil.updateEndTime(TestUtil.now("dd.MMMMM.yyyy hh.mm.ss aaa"));
		EmailReportUtil.updateEndTime(TestUtil.now("dd.MMMMM.yyyy hh.mm.ss aaa"));
		//mail();
	}
}