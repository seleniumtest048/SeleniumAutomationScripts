package com.projectname.scripts;
import jxl.Sheet;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterSuite;

import com.projectname.base.EmailReportUtil;
import com.projectname.base.Keywords;
import com.projectname.base.ReportUtil;
import com.projectname.utils.TestUtil;

public class DriverScript extends Keywords {
	
	public  String keyword;
	public  String stepDescription;
	public  String result;
	public  DriverScript dstest;
	public  WebDriver driver;
	 float totalTestCaseCount,runTestCaseCount=0,failedTestCases;

	public void driverScript(WebDriver driverThread, String threadName) throws Exception {
		driver=driverThread;
		initialize();
		
//		startTesting();
		emailStartTesting();
//		ReportUtil.startSuite("Suite");
		EmailReportUtil.startSuite("Suite");
		controlscript(driver,threadName);
		
	}
	
	public  void controlscript(WebDriver driver,String threadName) throws Exception{
		controlshet=controllerwb.getSheet("Suite");
		totalTestCaseCount=controlshet.getRows()-1;
		System.out.println("am in control script");
		int colom=3,excelrow=1;
		System.out.println("Control Sheet rows--"+controlshet.getRows());
		for (int i = 1; i < controlshet.getRows(); i++) {
			log.info("Selecting Test Scenario From Controller File");
			String tsrunmode=controlshet.getCell(2,i).getContents();
			
			System.out.println("test secenario Runmode---"+tsrunmode);
			
			if (tsrunmode.equalsIgnoreCase("Y")) {
				runTestCaseCount++;
				log.info("Navigate To Test Scenario Sheet");
				String tcaseid=controlshet.getCell(0,i).getContents();
				Sheet tdsheet1=testdatawb.getSheet(tcaseid);
				System.out.println("Test Case ID --"+tdsheet1);
				//control sheet
				Sheet controlshet1=controllerwb.getSheet(tcaseid);
				String fileName=null;
				log.info("Loading Test Data Work Book");
				for (int j = 1; j < tdsheet1.getRows(); j++) {
					String tcaserunmode=tdsheet1.getCell(2,j).getContents();
					if (tcaserunmode.equalsIgnoreCase("y")) {
						String testcaseid=tdsheet1.getCell(0,j).getContents();
						String testdesc=tdsheet1.getCell(1,j).getContents();
						fileName = (testcaseid)+"_";
						stepDescription=testdesc;
						keyword=testcaseid;
						log.info("Passing Parameters Driver Script to ContolScript");
						result=controlScript(driver,threadName,j, colom, tcaseid,controlshet1,testcaseid,stepDescription,keyword,fileName);
						if (failcount>=1 || rptFailCnt>=1) {
							result="Fail";
							log.info("Test Scenario Result --"+ result);
							if (failcount==0) {
								failedTestCases+=rptFailCnt;
							}else{
								failedTestCases+=failcount;
							}
//							mainReport(keyword,result,fileName);
							rptFailCnt=0;
							failcount=0;
						}else{
							result="Pass";
							log.info("Test Scenario Result --"+ result);
//							mainReport(keyword,result,fileName);
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
		
//		wwb.write();
//		wwb.close();
		/*float passPercentage=(totalPassCount/runTestCaseCount)*100;
		float failPercentage=(failedTestCases/runTestCaseCount)*100;
		System.out.println(passPercentage);
		System.out.println(failPercentage);
		dstest.generateCsvFile(0,passPercentage,failPercentage);
		generatePieChart();
		tdsheet=testdatawb.getSheet("Browser");*/
		closeBrowser(driver);
	}
	public static void endScript() throws Exception{
//		ReportUtil.updateEndTime(TestUtil.now("dd.MMMMM.yyyy hh.mm.ss aaa"));
		EmailReportUtil.updateEndTime(TestUtil.now("dd.MMMMM.yyyy hh.mm.ss aaa"));
		//mail();
	}
}