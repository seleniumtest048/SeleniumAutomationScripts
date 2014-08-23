package com.projectname.base;

import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import jxl.Sheet;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.UnhandledAlertException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.projectname.scripts.TestReport;
import com.projectname.utils.TestConstants;

public class Keywords extends FunctionLibrary {

	 TestConstants tc;	
	/**
	 * Retrieving objects form control sheet
	 * @param i
	 * @param colom
	 * @param tdshetnum
	 * @param csheet
	 * @param fileName 
	 * @param keyword2 
	 * @param stepDescription 
	 * @throws Exception 
	 */
	 public String controlScript(String browser,int row, int colom, String tdshetnum, Sheet csheet, String testcaseid, String stepDescription, String keyword2, String fileName) throws Exception{
		 controlshet=csheet;
		 steps = new ArrayList<TestReport>();
		 Robot r=new Robot();
		 String result = "Pass";
		 int webtableCounter = 0;
		 for (int k = 1; k < controlshet.getRows(); k++) {
			 tr=new TestReport();
			 TestConstants tc;
			 String testLinkID=controlshet.getCell(0, k).getContents();
			 String desc=controlshet.getCell(1, k).getContents();
			 String keyword=controlshet.getCell(2, k).getContents().toUpperCase();
			 String keywordtype=controlshet.getCell(3,k).getContents();
			 String objectProp=controlshet.getCell(4,k).getContents();
			 String object=OR.getProperty(objectProp);
			 String data=null;
			 Object testdata=null;
			 String webObject=null;
			
			 try{
				 switch(keyword){
				 case "ACCEPTALERT":
					 log.info("Accepting Alert");
					 try{
					 driver.switchTo().alert().accept();
					 }catch(Exception e){
						 
					 }
					 result="Pass";
					 break;
				 case "CHECKBOX": case "RADIOBUTTON": 
					 testdata= testData(colom,row,tdshetnum);
					 data=(String) testdata;
					 log.info("Clicking on "+data+" Radiobutton");
					 String buttonvalue=OR.getProperty(data);
					 tc=actionElement(keyword,keywordtype,buttonvalue,data);
					 tc.result=result;
					 colom++;
					 break;
				 case "COMPARETWOFILES":
					 log.info("Compareing two files-----");
					 testdata= testData(colom,row,tdshetnum);
					 data=(String) testdata;
					 result=compareTwoFiles(keyword,keywordtype,object,data);
					 colom++;
					 break;
				 case "CLICK":
					 log.info("Clicking on Button " + object);
					 tc=actionElement(keyword,keywordtype,object,data);
					 result="Pass";
					 break;
				 case "CLICKENTER": 
					 log.info("Clicking on Enter Key");
					 tc=actionElement(keyword,keywordtype,object,data);
					 tc.result=result;
					 break;
				 case "CLICKESC": 
					 log.info("Clicking on Escape Button");
					 Thread.sleep(3000);
					 tc=actionElement(keyword,keywordtype,object,data);
					 tc.result=result;
					 break;
				 case "CLICKTAB":
					 log.info("Clicking on TAB Button");
					 tc=actionElement(keyword,keywordtype,object,data);
					 tc.result=result;
					 break;
				 case "CLOSEBROWSER":
					 log.info("Closing Browser");
					 closeBrowser();
					 result="Pass";
					 break; 
				 case "CLOSEWINDOW":
					 log.info("Closing Child Window");
					 closeWindow();
					 result="Pass";
					 break;
				 case "COMPARETEXT":
					 log.info("Compairing Text");
					 result=  compairText(keyword,keywordtype,object,objectProp);
					 break;
				 case "DESC":
					 result="desc";
					 break;
				 case "DEFAULTCONTENT":
					 log.info("Switch to default content");
					 driver.switchTo().defaultContent();
					 result="Pass";
					 break;  
				 case "GETALLLINKS": 
					 log.info("Getting All Links from Webpage");
					 getLinks();
					 result="Pass";
					 break; 
				 case "GETATTRIBUTEVALUE":
					 log.info("Getting the value form WebPage-----");
					 result=getAttributeValue(keyword,keywordtype,object,objectProp);
					 break; 
				 case "GETATTRIBUTEALT":
					 log.info("Getting the value form WebPage-----");
					 result=getAttributeAlt(keyword,keywordtype,object,objectProp);
					 break;
				 case "GETELEMENTTEXT": 
					 log.info("Getting Text From Webpage");
					 tc=actionElement(keyword,keywordtype,object,data);
					 tc.result=result;
					 break; 
				 case "GETPAGETITLE":
					 log.info("Getting Page Title");
					 driver.getTitle();
					 result="Pass";
					 break;  
				 case "GOBACK":
					 log.info("Clicking on Back Button");
					 driver.navigate().back();
					 result="Pass";
					 break; 
				 case "GOFORWARD":
					 driver.navigate().forward();
					 result="Pass";
					 break; 
				 case "INPUT":
					 log.info("Entering Data into "+object);
					 testdata= testData(colom,row,tdshetnum);
					 data=(String) testdata;
					 tc=actionElement(keyword,keywordtype,object,data);
					 result="Pass";
					 colom++;
					 break;  
				 case "ISDISPLAYED":
					 result= isDisplayed(keyword,keywordtype,object,objectProp);
					 break;
				 case "ISSELECTED":
					 result= isSelected(keyword,keywordtype,object,objectProp);
					 break;
				 case "ISENABLED":
					 result= isEnabled(keyword,keywordtype,object,objectProp);
					 break; 
				 case "KEYDOWN": 
					 log.info("Clicking on Down Button");
					 Thread.sleep(3000);
					 tc=actionElement(keyword,keywordtype,object,data);
					 tc.result=result;
					 break;
				 case "MOUSEOVER":
					 log.info("Mouse Over to "+object);
					 tc=actionElement(keyword,keywordtype,object,data);
					 tc.result=result;
					 break;  
				 case "OPENNEWTAB":
					 log.info("Open New Tab");
					try{
					 r.keyPress(KeyEvent.VK_CONTROL); 
					 r.keyPress(KeyEvent.VK_T);
					 }catch (Exception e) {
						 driver.findElement(By.tagName("body")).sendKeys(Keys.CONTROL +"t");
					}
					 result="Pass";
					 break;	  
				 case "OPENURL":
					 log.info("Navigating to ---"+object);
					 driver.navigate().to(object);
					 result="Pass";
					 break;	  
				 case "PASTE": 
					 log.info("Paste Text into Textbox");
					 tc=actionElement(keyword,keywordtype,object,data);
					 tc.result=result;
					 break;
				 case "REFRESH": 
					 log.info("Refreshing Page........");
					 driver.navigate().refresh();
					 result="Pass";
					 break; 
				 case "SCROLL":
					 log.info("Scroll down");
					 tc=actionElement(keyword,keywordtype,object,data);
					 tc.result=result;
					 break;  
				 case "SELECT":
					 testdata= testData(colom,row,tdshetnum);
					 data=(String) testdata;
					 log.info("Selecting DropDown Value "+data);
					 tc=actionElement(keyword,keywordtype,object,data);
					 tc.result=result;
					 colom++;
					 break;
				 case "WEBSELECT":
					 testdata= testData(colom,row,tdshetnum);
					 data=(String) testdata;
					 webObject=object+webtableCounter;
					 System.out.println("Webtable counter value-====>>"+webObject);
					 tc=actionElement(keyword,keywordtype,webObject,data);
					 tc.result=result;
					 colom++;
					 break;
				 case "SELECTFRAME":
					 log.info("Switch to frame------");
					 tc=actionElement(keyword,keywordtype,object,data);
					 tc.result=result;
					 break;	  
				 case "SWITCHTONEWWINDOW":
					 log.info("Switch to New Window");
					 windowhandle();
					 result="Pass";
					 break;	  
				 case "SWITCHTOPARENTWINDOW":
					 log.info("Switching to Parent Window");
					 Mainwindow();
					 result="Pass";
					 break;  
				 case"VERIFYALERT":
					 log.info("Verifying Alert-----");
					 result=alertverify(objectProp);
					 break;
				 case "VERIFYTEXT":
					 log.info("Verifying Text");
					 result= verifyText(keyword,keywordtype,object,objectProp);
					 break;
				 case "VERIFYPAGETITLE":
					 log.info("Verifying Page Title");
					 result= verifyPageTitle(keyword,keywordtype,object,objectProp);
					 break;	 
				 case "WAIT":
					 log.info("Loading Page");
					 Thread.sleep(10000);
					 result="Pass";
					 break;
				 case "WEBTABLE":
					 webtableCounter= webTable(object);
					 break;
				 case "WEBINPUT":
					 log.info("Entering Data into "+object);
					 testdata= testData(colom,row,tdshetnum);
					 data=(String) testdata;
					 webObject=object+webtableCounter;
					 System.out.println("Webtable counter value-====>>"+webObject);
					 tc=actionElement(keyword,keywordtype,webObject,data);
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
					 deleteBlackList(object,data);
					 colom++;
					 break;	 
				 case "DELETEWHITELIST":
					 testdata= testData(colom,row,tdshetnum);
					 data=(String) testdata;
					 deleteBlackList(object,data);
					 colom++;
					 break;	
				 case "EDITERRORMSG":
					 testdata= testData(colom,row,tdshetnum);
					 data=(String) testdata;
					 colom++;
					 int colomValue=editErroMeassages(object,data,colom,row,tdshetnum);							 
					 colom=colomValue;
					 break;	
				 case "VERIFYERRORMSG":
					 testdata= testData(colom,row,tdshetnum);
					 data=(String) testdata;
					 colom++;
					 String colomValue1=verifyingErroMeassages(object,data,colom,row,tdshetnum);	
					 result=colomValue1;
					 break;	
				 case "AUTHENTICATION":
					 log.info("Executing VBScript");
					 authentication();
					 break;
				 default:
					 break;
				 }
				 reportSteps(result,desc,keyword,fileName+testLinkID + ".png",object,testcaseid,testLinkID);
				 
			 }catch(UnhandledAlertException e){
				 driver.switchTo().alert().accept();
			 }catch(Exception e){	
				 result="Fail";
				 log.info("",e);
				 failcount++;
				 report(result,desc,keyword,fileName,object,testcaseid);
				 e.printStackTrace();
				 tr.result=result;
				 tr.desc=desc;
				 tr.keyword=keyword;
				 tr.fileName=fileName;
				 tr.object=object;
				 tr.testcaseid=testcaseid;
				 steps.add(tr);
				 break;
			 }
		 }
		 reportEmailMain(browser,result,fileName,testcaseid,failcount);
		 for (int i = 0; i < steps.size(); i++) {
			 String re=steps.get(i).result;
			 String ds=steps.get(i).desc;
			 String testLinkID=steps.get(i).testLinkID;
			 System.out.println("test Link ID for Email Report=====>>>"+testLinkID);
			 switch (re) {
			 case "Fail":
				 EmailReportUtil.addTestCaseSteps(ds,re,testLinkID);
				 break;
			 case "Pass": case "res":
				 EmailReportUtil.addTestCaseSteps(ds,re,testLinkID);
				 break;
			 default:
				 break;
			 }
		 }
		 return result;
	 }
	 public TestConstants actionElement(String keyword, String keywordtype,String object,String data) throws Exception  {
		 tc=new TestConstants();
		 try{
			 switch (keyword.toUpperCase()) {
			 case "CLICK":
				 tc.welement=welement(keywordtype, object);
				 tc. welement.click();
				 break;
			 case "CHECKBOX": case "RADIOBUTTON":
				 tc.welement=welement(keywordtype, object);
				 tc.welement.click();
				 break;	 
			 case "CLICKESC": 
				 log.info("Clicking on Escape Button");
				 tc.welement=welement(keywordtype, object);
				 tc.welement.sendKeys(Keys.ESCAPE);
				 break;
			 case "CLICKENTER": 
				 tc.welement=welement(keywordtype, object);
				 tc.welement.sendKeys(Keys.ENTER);
				 break;
			 case "CLICKTAB":
				 tc.welement=welement(keywordtype, object);
				 tc.welement.sendKeys(Keys.TAB);
				 break;
			 case "COMPARETEXT":
				 tc.welement=welement(keywordtype, object);
				 break; 
			 case "COMPARETWOFILES":
				 tc.welement=welement(keywordtype, object);
				 break;	 
			 case "GETATTRIBUTEVALUE":
				 tc.welement=welement(keywordtype, object);
				 break;
			 case "GETATTRIBUTEALT":
				 tc.welement=welement(keywordtype, object);
				 break;
			 case "GETELEMENTTEXT":
				 tc.welement=welement(keywordtype, object);
				 String pageText=tc.welement.getText();
				 log.info("Get Element Text-----------"+pageText);
//				 pastevalue=  getText(tc.welement);
				 break; 
			 case "ISDISPLAYED":
				 tc.welement=welement(keywordtype, object);
				 break;
			 case "ISSELECTED":
				 tc.welement=welement(keywordtype, object);
				 break;
			 case "ISENABLED":
				 tc.welement=welement(keywordtype, object);
				 break;	  
			 case "INPUT":
				 tc.welement=welement(keywordtype, object);
				 tc.welement.clear();
				 tc. welement.sendKeys(data);
				 break;
			 case "KEYDOWN": 
				 tc.welement=welement(keywordtype, object);
				 tc.welement.sendKeys(Keys.DOWN);
				 break;		 
			 case "MOUSEOVER":
				 tc.welement=welement(keywordtype, object);
				 mouseover(tc.welement);
				 break;	
			 case "PASTE": 
				 tc.welement=welement(keywordtype, object);
				 tc.welement.sendKeys(pastevalue);
				 break;	
			 case "SCROLL":
				 tc.welement=welement(keywordtype, object);
				 scrollBar(tc.welement);
				 break;	
			 case "SELECT":
				 tc.welement=welement(keywordtype, object);
				 new Select(tc.welement).selectByVisibleText(data);
				 break;
			 case "WEBSELECT":
				 tc.welement=welement(keywordtype, object);
				 new Select(tc.welement).selectByVisibleText(data);
				 break;
			 case "SELECTFRAME":
				 tc.welement=welement(keywordtype, object);
				 frame(tc.welement);
				 break;
			 case "VERIFYTEXT":
				 tc.welement=welement(keywordtype, object);
				 break;
			 case "WAIT":
				 Thread.sleep(Long.parseLong(data));
				 break;
			 case "WEBINPUT":
				 tc.welement=welement(keywordtype, object);
				 tc.welement.clear();
				 tc. welement.sendKeys(data);
				 break;
			 default:
				 break;
			 }
			 tc.result="Pass";
		 }catch(Exception e){
			 tc.result="Fail";
		 }
		 return tc;
	 }
	 public WebElement welement(String keywordtype, String object) throws Exception{
		 WebElement welement =null;
		 driverWait=new WebDriverWait(driver, 10);
		try{	
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
			case "NAME":
				driverWait.until(ExpectedConditions.visibilityOfElementLocated(By.name(object)));
				welement=driver.findElement(By.name((object)));
				return welement ;	
			case "CSS":
				driverWait.until(ExpectedConditions.visibilityOfElementLocated(By.name(object)));
				welement=driver.findElement(By.cssSelector((object)));
				return welement ;
			default:
				break;
			}
		}catch(Exception e){
			switch (keywordtype.toUpperCase()) {
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
			case "NAME":
				driverWait.until(ExpectedConditions.visibilityOfElementLocated(By.name(object)));
				welement=driver.findElement(By.name((object)));
				return welement ;
			case "CSS":
				driverWait.until(ExpectedConditions.visibilityOfElementLocated(By.name(object)));
				welement=driver.findElement(By.cssSelector((object)));
				return welement ;
			default:
				break;
			
			}
			e.getMessage();
		}
		return welement;
	 }

}
