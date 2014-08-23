package com.projectname.base;

import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import jxl.Sheet;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.UnhandledAlertException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.projectname.scripts.DriverScript;
import com.projectname.scripts.TestReport;
import com.projectname.utils.ConstantsThreads;
import com.projectname.utils.TestConstants;
import com.projectname.utils.TestUtil;

public class Keywords extends FunctionLibrary {

	int skipCases;

	/**
	 * Retrieving objects form control sheet
	 * 
	 * @param i
	 * @param colom
	 * @param tdshetnum
	 * @param csheet
	 * @param fileName
	 * @param keyword2
	 * @param stepDescription
	 * @throws Exception
	 */
	public String controlScript(WebDriver driver, String threadName, int row,
			int colom, String tdshetnum, Sheet csheet, String testcaseid,
			String stepDescription, String keyword2, String fName)
			throws Exception {
		String result = "res";
		ConstantsThreads ct = null;
		controlshet = csheet;
		skipCases = 1;
		Robot r = new Robot();
		int webtableCounter = 0;
		for (int k = 1; k < controlshet.getRows(); k++) {
			tr = new TestReport();
			String testLinkID = controlshet.getCell(0, k).getContents();
			String desc = controlshet.getCell(1, k).getContents();
			String keyword = controlshet.getCell(2, k).getContents();
			String keywordtype = controlshet.getCell(3, k).getContents();
			String objectProp = controlshet.getCell(4, k).getContents();
			String object = OR.getProperty(objectProp);
			String data = null;
			Object testdata = null;
			String webObject=null;
			 
			String fileName=fName+testLinkID+".png";
			try {
				switch (keyword.toUpperCase()) {
				case "ACCEPTALERT":
					log.info("Accepting Alert");
					try{
						 driver.switchTo().alert().accept();
						 }catch(Exception e){
							 
						 }
					result = "Pass";
					break;
				case "CHECKBOX":
				case "RADIOBUTTON":
					testdata = testData(colom, row, tdshetnum);
					data = (String) testdata;
					log.info("Clicking on " + data + " Radiobutton");
					String buttonvalue = OR.getProperty(data);
					Thread.sleep(3000);
					ct = actionElement(driver, keyword, keywordtype,
							buttonvalue, data);
					result = ct.result;
					colom++;
					break;
				case "COMPARETWOFILES":
					log.info("Compareing two files-----");
					testdata = testData(colom, row, tdshetnum);
					data = (String) testdata;
					result = compareTwoFiles(driver, keyword, keywordtype,
							object, data);
					 colom++;
					break;
				case "CLICK":
					log.info("Clicking on Button " + object);
					Thread.sleep(3000);
					ct = actionElement(driver, keyword, keywordtype, object,
							data);
					result = ct.result;
					break;
				case "CLICKENTER":
					log.info("Clicking on Enter Key");
					ct = actionElement(driver, keyword, keywordtype, object,
							data);
					result = ct.result;
					break;
				case "CLICKESC":
					log.info("Clicking on Escape Button");
					Thread.sleep(3000);
					ct = actionElement(driver, keyword, keywordtype, object,
							data);
					result = ct.result;
					break;
				case "CLICKTAB":
					log.info("Clicking on TAB Button");
					ct = actionElement(driver, keyword, keywordtype, object,
							data);
					result = ct.result;
					break;
				case "CLOSEBROWSER":
					log.info("Closing Browser");
					closeBrowser(driver);
					result = "Pass";
					break;
				case "CLOSEWINDOW":
					log.info("Closing Child Window");
					closeWindow(driver);
					result = "Pass";
					break;
				case "COMPARETEXT":
					log.info("Compairing Text");
					result = compairText(driver, keyword, keywordtype, object,
							objectProp);
					break;
				case "DEFAULTCONTENT":
					log.info("Switch to default content");
					driver.switchTo().defaultContent();
					result = "Pass";
					break;
				case "GETALLLINKS":
					log.info("Getting All Links from Webpage");
					getLinks(driver);
					result = "Pass";
					break;
				case "GETATTRIBUTEVALUE":
					log.info("Getting the value form WebPage-----");
					result = getAttributeValue(driver, keyword, keywordtype,
							object, objectProp);
					break;
				case "GETATTRIBUTEALT":
					log.info("Getting the value form WebPage-----");
					result = getAttributeAlt(driver, keyword, keywordtype,
							object, objectProp);
					break;
				case "GETELEMENTTEXT":
					log.info("Getting Text From Webpage");
					ct = actionElement(driver, keyword, keywordtype, object,
							data);
					result = ct.result;
					break;
				case "GETPAGETITLE":
					log.info("Getting Page Title");
					driver.getTitle();
					result = "Pass";
					break;
				case "GOBACK":
					log.info("Clicking on Back Button");
					driver.navigate().back();
					result = "Pass";
					break;
				case "GOFORWARD":
					driver.navigate().forward();
					result = "Pass";
					break;
				case "INPUT":
					log.info("Entering Data into " + object);
					testdata = testData(colom, row, tdshetnum);
					data = (String) testdata;
					System.out.println("test data in----------------->>>"+data);
					Thread.sleep(3000);
					ct = actionElement(driver, keyword, keywordtype, object,
							data);
					result = ct.result;
					colom++;
					break;
				case "ISDISPLAYED":
					result = isDisplayed(driver, keyword, keywordtype, object,
							objectProp);
					break;
				case "ISSELECTED":
					result = isSelected(driver, keyword, keywordtype, object,
							objectProp);
					break;
				case "ISENABLED":
					result = isEnabled(driver, keyword, keywordtype, object,
							objectProp);
					break;
				case "KEYDOWN":
					log.info("Clicking on Down Button");
					Thread.sleep(3000);
					ct = actionElement(driver, keyword, keywordtype, object,
							data);
					result = ct.result;
					break;
				case "MOUSEOVER":
					log.info("Mouse Over to " + object);
					ct = actionElement(driver, keyword, keywordtype, object,
							data);
					result = ct.result;
					break;
				case "OPENNEWTAB":
					log.info("Open New Tab");
					r.keyPress(KeyEvent.VK_CONTROL);
					r.keyPress(KeyEvent.VK_T);
					result = "Pass";
					break;
				case "OPENURL":
					log.info("Navigating to ---" + object);
					driver.navigate().to(object);
					result = "Pass";
					break;
				case "PASTE":
					log.info("Paste Text into Textbox");
					ct = actionElement(driver, keyword, keywordtype, object,
							data);
					result = ct.result;
					break;
				case "REFRESH":
					log.info("Refreshing Page........");
					driver.navigate().refresh();
					result = "Pass";
					break;
				case "SCROLL":
					log.info("Scroll down");
					ct = actionElement(driver, keyword, keywordtype, object,
							data);
					result = ct.result;
					break;
				case "SELECT":
					testdata = testData(colom, row, tdshetnum);
					data = (String) testdata;
					log.info("Selecting DropDown Value " + data);
					Thread.sleep(3000);
					ct = actionElement(driver, keyword, keywordtype, object,
							data);
					result = ct.result;
					colom++;
					break;
				case "SELECTFRAME":
					log.info("Switch to frame------");
					ct = actionElement(driver, keyword, keywordtype, object,
							data);
					result = ct.result;
					break;
				case "SWITCHTONEWWINDOW":
					log.info("Switch to New Window");
					windowhandle(driver);
					result = "Pass";
					break;
				case "SWITCHTOPARENTWINDOW":
					log.info("Switching to Parent Window");
					Mainwindow(driver);
					result = "Pass";
					break;
				case "VERIFYALERT":
					alertverify(driver, objectProp);
					break;
				case "VERIFYTEXT":
					log.info("Verifying Text");
					result = verifyText(driver, keyword, keywordtype, object,
							objectProp);
					break;
				case "VERIFYPAGETITLE":
					log.info("Verifying Page Title");
					result = verifyPageTitle(driver, keyword, keywordtype,
							object, objectProp);
					break;
				case "WAIT":
					log.info("Loading Page");
					Thread.sleep(10000);
					result = "Pass";
					break;
				case "AUTHENTICATION":
					log.info("Executing VBScript");
					authentication();
					break;
				 case "WEBSELECT":
					 testdata= testData(colom,row,tdshetnum);
					 data=(String) testdata;
					 webObject=object+webtableCounter;
					 System.out.println("Webtable counter value-====>>"+webObject);
					 ct = actionElement(driver, keyword, keywordtype, object,
								data);
						result = ct.result;
					 colom++;
					 break;
				 case "WEBTABLE":
					 webtableCounter= webTable(object,driver);
					 break;
				 case "WEBINPUT":
					 log.info("Entering Data into "+object);
					 testdata= testData(colom,row,tdshetnum);
					 data=(String) testdata;
					 webObject=object+webtableCounter;
					 System.out.println("Webtable counter value-====>>"+webObject);
					 ct =actionElement(driver,keyword,keywordtype,webObject,data);
					 result="Pass";
					 colom++;
					 break;  
				 case "WHITELISTSAVE":
					 System.out.println("IN White List function");
					  webtableCounter=webtableCounter+1;
					 String whiteListSave="//*[@id='ipAddress_details']/table/tbody/tr["+webtableCounter+"]/td[7]/a/img";
					 driver.findElement(By.xpath(whiteListSave)).click();
					 result="Pass";
					 break;	
				 case "BLACKLISTSAVE":
					 System.out.println("In Block list save function");
					  webtableCounter=webtableCounter+1;
					 String whiteListSave2="//*[@id='ipAddress_details']/table/tbody/tr["+webtableCounter+"]/td[6]/a/img";
					 driver.findElement(By.xpath(whiteListSave2)).click();
					 result="Pass";
					 break;	
				 case "DELETEBLACKLIST":
					 testdata= testData(colom,row,tdshetnum);
					 data=(String) testdata;
					 deleteBlackList(object,data,driver);
					 colom++;
					 break;	 
				 case "DELETEWHITELIST":
					 testdata= testData(colom,row,tdshetnum);
					 data=(String) testdata;
					 deleteBlackList(object,data,driver);
					 colom++;
					 break;	
				 case "EDITERRORMSG":
					 testdata= testData(colom,row,tdshetnum);
					 data=(String) testdata;
					 colom++;
					 int colomValue=editErroMeassages(object,data,colom,row,tdshetnum,driver);							 
					 colom=colomValue;
					 break;	
				 case "VERIFYERRORMSG":
					 testdata= testData(colom,row,tdshetnum);
					 data=(String) testdata;
					 colom++;
					 String colomValue1=verifyingErroMeassages(object,data,colom,row,tdshetnum,driver);	
					 result=colomValue1;
					 break;		 
				default:
					break;

				}
				reportSteps(threadName, driver, result, desc, keyword,
						fileName, object, testcaseid,testLinkID);

			} catch (UnhandledAlertException e) {
				driver.switchTo().alert().accept();

			} catch (Exception e) {
				result = "Fail";
				log.info("", e);
				failcount++;
				report(driver, result, desc, keyword, fileName, object,
						testcaseid);
				e.printStackTrace();
				tr.result = result;
				tr.desc = desc;
				tr.keyword = keyword;
				tr.fileName = fileName;
				tr.object = object;
				tr.testcaseid = testcaseid;
				tr.testLinkID=testLinkID;
				stepsDesc.add(tr);
				if (threadName.equalsIgnoreCase("windowsMF")) {
					ConstantsThreads.stepsWindowsMF.add(tr);
					TestUtil.takeScreenShot(driver,path+com.projectname.utils.TestConstants.TESTSUITE_RESULT_DIR_PATH_EMAIL+threadName+fileName);
					EmailReportUtil.addKeyword(threadName,stepDescription, keyword, result, fileName,testLinkID);
				} else if (threadName.equalsIgnoreCase("windowsIE")) {
					ConstantsThreads.stepsWindowsIE.add(tr);
					TestUtil.takeScreenShot(driver,path+com.projectname.utils.TestConstants.TESTSUITE_RESULT_DIR_PATH_EMAIL+threadName+fileName);
					EmailReportUtil.addKeyword(threadName,stepDescription, keyword, result, fileName,testLinkID);
				} else if (threadName.equalsIgnoreCase("windowsGC")) {
					ConstantsThreads.stepsWindowsGC.add(tr);
					TestUtil.takeScreenShot(driver,path+com.projectname.utils.TestConstants.TESTSUITE_RESULT_DIR_PATH_EMAIL+threadName+fileName);
					EmailReportUtil.addKeyword(threadName,stepDescription, keyword, result, fileName,testLinkID);
				} else if (threadName.equalsIgnoreCase("macMF")) {
					ConstantsThreads.stepsMacMF.add(tr);
					TestUtil.takeScreenShot(driver,path+com.projectname.utils.TestConstants.TESTSUITE_RESULT_DIR_PATH_EMAIL+threadName+fileName);
					EmailReportUtil.addKeyword(threadName,stepDescription, keyword, result, fileName,testLinkID);
				} else if (threadName.equalsIgnoreCase("macSafari")) {
					ConstantsThreads.stepsMacSafari.add(tr);
					TestUtil.takeScreenShot(driver,path+com.projectname.utils.TestConstants.TESTSUITE_RESULT_DIR_PATH_EMAIL+threadName+fileName);
					EmailReportUtil.addKeyword(threadName,stepDescription, keyword, result, fileName,testLinkID);
				} else if (threadName.equalsIgnoreCase("macGC")) {
					ConstantsThreads.stepsMacGC.add(tr);
					TestUtil.takeScreenShot(driver,path+com.projectname.utils.TestConstants.TESTSUITE_RESULT_DIR_PATH_EMAIL+threadName+fileName);
					EmailReportUtil.addKeyword(threadName,stepDescription, keyword, result, fileName,testLinkID);
				}
				break;
			}
		}
		return result;
	}

	public ConstantsThreads actionElement(WebDriver driver, String keyword,
			String keywordtype, String object, String data) throws Exception {

		ConstantsThreads ct = new ConstantsThreads();
		try {
			switch (keyword.toUpperCase()) {
			case "CLICK":
				Thread.sleep(3000);
				ct.welement = welement(driver, keywordtype, object);
				Thread.sleep(3000);
				ct.welement.click();
				 break;
			case "CHECKBOX": case "RADIOBUTTON":
					Thread.sleep(3000);
					ct.welement = welement(driver, keywordtype, object);
					Thread.sleep(3000);
					ct.welement.click();
					break;	 
			 case "CLICKESC": 
				 log.info("Clicking on Escape Button");
				 ct.welement = welement(driver, keywordtype, object);
				 Thread.sleep(3000);
				 ct.welement.sendKeys(Keys.ESCAPE);
				 break;
			 case "CLICKENTER": 
				 ct.welement = welement(driver, keywordtype, object);
				 Thread.sleep(3000);
				 ct.welement.sendKeys(Keys.ENTER);
				 break;
			 case "CLICKTAB":
				 ct.welement = welement(driver, keywordtype, object);
					Thread.sleep(3000);
					ct.welement.sendKeys(Keys.TAB);
				 break;
			 case "COMPARETEXT":
				 Thread.sleep(3000);
				 ct.welement = welement(driver, keywordtype, object);
				 break; 
			 case "COMPARETWOFILES":
				 ct.welement=welement(driver,keywordtype, object);
				 break;	 
			 case "GETATTRIBUTEVALUE":
				 ct.welement=welement(driver,keywordtype, object);
				 break;
			 case "GETATTRIBUTEALT":
				 ct.welement=welement(driver,keywordtype, object);
				 break;
			 case "GETELEMENTTEXT":
				 Thread.sleep(3000);
					ct.welement = welement(driver, keywordtype, object);
					Thread.sleep(3000);
//					pastevalue = getText(ct.welement);
				 break; 
			 case "ISDISPLAYED":
				 ct.welement=welement(driver,keywordtype, object);
				 break;
			 case "ISSELECTED":
				 ct.welement=welement(driver,keywordtype, object);
				 break;
			 case "ISENABLED":
				 ct.welement=welement(driver,keywordtype, object);
				 break;	  
			 case "INPUT":
				 Thread.sleep(3000);
					ct.welement = welement(driver, keywordtype, object);
					ct.welement.clear();
					Thread.sleep(3000);
					ct.welement.sendKeys(data);
				 break;
			 case "KEYDOWN": 
				 ct.welement = welement(driver, keywordtype, object);
					Thread.sleep(3000);
					ct.welement.sendKeys(Keys.DOWN);
				 break;		 
			 case "MOUSEOVER":
				 Thread.sleep(3000);
					ct.welement = welement(driver, keywordtype, object);
					Thread.sleep(3000);
					mouseover(driver, ct.welement);
				 break;	
			 case "PASTE": 
				 ct.welement = welement(driver, keywordtype, object);
					Thread.sleep(3000);
					ct.welement.sendKeys(pastevalue);
				 break;	
			 case "SCROLL":
				 log.info("Scroll down");
					ct.welement = welement(driver, keywordtype, object);
					Thread.sleep(3000);
					scrollBar(driver, ct.welement);
				 break;	
			 case "SELECT":
				 Thread.sleep(3000);
					ct.welement = welement(driver, keywordtype, object);
					Thread.sleep(3000);
					new Select(ct.welement).selectByVisibleText(data);
				 break;
			 case "SELECTFRAME":
				 Thread.sleep(3000);
					ct.welement = welement(driver, keywordtype, object);
					Thread.sleep(3000);
					frame(driver, ct.welement);
				 break;
			 case "VERIFYTEXT":
				 Thread.sleep(3000);
					ct.welement = welement(driver, keywordtype, object);
				 break;
			 case "WAIT":
				 Thread.sleep(Long.parseLong(data));
				 break;
			 default:
				 break;
			}
			ct.result = "Pass";
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("A, in Action Eelements section--");
			if (skipCases == 1) {
				ct.result = "Fail";
				skipCases++;
			} else {
				ct.result = "Skip";
			}

		}
		return ct;
	}

	public WebElement welement(WebDriver driver, String keywordtype,
			String object) throws Exception {
		WebElement welement = null;
		driverWait=new WebDriverWait(driver, 10);
		try {
			switch (keywordtype.toUpperCase()) {
			case "ID":
				driverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id(object)));
				welement=driver.findElement(By.id(object));
				return welement ;
			case "XPATH":
				driverWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(object)));
				welement=driver.findElement(By.xpath(object));
				return welement ;
			case "LINKTEXT":
				driverWait.until(ExpectedConditions.visibilityOfElementLocated(By.linkText(object)));
				welement=driver.findElement(By.linkText(object));
				return welement ;
			case "PLINK":
				driverWait.until(ExpectedConditions.visibilityOfElementLocated(By.partialLinkText(object)));
				welement=driver.findElement(By.partialLinkText(object));
				return welement ;
			case "CLASSNAME":
				driverWait.until(ExpectedConditions.visibilityOfElementLocated(By.className(object)));
				welement=driver.findElement(By.className((object)));
				return welement ;
			default:
				break;
			}
		} catch (Exception e) {
			switch (keywordtype) {
			case "ID":
				Thread.sleep(900);
				welement=driver.findElement(By.id(object));
				return welement ;
			case "XPATH":
				Thread.sleep(900);
				welement=driver.findElement(By.xpath(object));
				return welement ;
			case "LINKTEXT":
				Thread.sleep(900);
				welement=driver.findElement(By.linkText(object));
				return welement ;
			case "PLINK":
				Thread.sleep(900);
				welement=driver.findElement(By.partialLinkText(object));
				return welement ;
			case "CLASSNAME":
				Thread.sleep(900);
				welement=driver.findElement(By.className((object)));
				return welement ;
			default:
				break;

			}
			e.getMessage();
		}
		return welement;
	}

}
