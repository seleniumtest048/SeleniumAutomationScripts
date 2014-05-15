package com.projectname.base;

import java.util.ArrayList;
import jxl.Sheet;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.UnhandledAlertException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;

import com.projectname.scripts.TestReport;

public class Keywords extends FunctionLibrary {

	
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
	 public String controlScript(int row, int colom, String tdshetnum, Sheet csheet, String testcaseid, String stepDescription, String keyword2, String fileName) throws Exception{
		 String result="res";
		 controlshet=csheet;
		 steps = new ArrayList<TestReport>();
		 for (int k = 1; k < controlshet.getRows(); k++) {
			 tr=new TestReport();
			 String desc=controlshet.getCell(1, k).getContents();
			 String keyword=controlshet.getCell(2, k).getContents();
			 String keywordtype=controlshet.getCell(3,k).getContents();
			 String objectProp=controlshet.getCell(4,k).getContents();
			 String object=OR.getProperty(objectProp);
			 String data=null;
			 Object testdata=null;
			 //Validations
			 String expData=V.getProperty(objectProp);
			 //comparision Object
			 String compairobject=V.getProperty(objectProp);
			
			  try{
				  switch(keyword){
				  case "radiobutton": case "Radiobutton": case "radioButton": case "button": case "Button":
					  testdata= testData(colom,row,tdshetnum);
					  data=(String) testdata;
					  log.info("Clicking on "+data+" Radiobutton");
					  String buttonvalue=OR.getProperty(data);
					  actionElement(keyword,keywordtype,buttonvalue,data);
					  colom++;
					  break;
				  case "links": 
					  testdata= testData(colom,row,tdshetnum);
					  data=(String) testdata;
					  log.info("Clicking on "+data);
					  actionElement(keyword,keywordtype,data,data);
					  colom++;
					  break;
				  case "click": case "Click":
					  log.info("Clicking on Button " + object);
					  actionElement(keyword,keywordtype,object,data);
					  break;
				  case "input": case "Input":
					  log.info("Entering Data into "+object);
					  testdata= testData(colom,row,tdshetnum);
					  data=(String) testdata;
					  actionElement(keyword,keywordtype,object,data);
					  colom++;
					  break;
				  case "select": case "Select":
					  testdata= testData(colom,row,tdshetnum);
					  data=(String) testdata;
					  log.info("Selecting DropDown Value "+data);
					  actionElement(keyword,keywordtype,object,data);
					  colom++;
					  break;
				  case "wait": case"Wait":
					  log.info("Loading Page");
					Thread.sleep(10000);
					  break;
				  case "url": case"URL":
					 uRL();
					  break;
				  case "verifytext": case "verifyText":
					  log.info("Verifying Text");
					result= verifyText(keyword,keywordtype,object,objectProp);
					  break;
				  case "compairText": case "CompairText":
					  log.info("Compairing Text");
					  result=  compairText(keyword,keywordtype,object,objectProp);
					  break;
				  case "newwindow": case "newWindow": case "Newwindow":
					  log.info("Switch to New Window");
					  windowhandle();
				  break;
				  case "keyDown": 
					  log.info("Clicking on Down Button");
					  Thread.sleep(3000);
					  actionElement(keyword,keywordtype,object,data);
					  break;
				  case "Esc": 
					  log.info("Clicking on Escape Button");
					  Thread.sleep(3000);
					  actionElement(keyword,keywordtype,object,data);
					  break;
				  case "enterKey": 
					  log.info("Clicking on Enter Key");
					  
					  actionElement(keyword,keywordtype,object,data);
					  break;
				  case "tab": case "Tab":
					  log.info("Clicking on TAB Button");
					  actionElement(keyword,keywordtype,object,data);
					  break;	
				  case "Closewindow": case "closeWindow": case "CloseWindow":
					  log.info("Closing Child Window");
					  closeWindow();
				  break;
				  case "parentwindow": case "parentWindow": case "Parentwindow":
					  log.info("Switching to Parent Window");
					  Mainwindow();
					  break;
				  case "frame": case "Frame":
					  log.info("");
					  actionElement(keyword,keywordtype,object,data);
					  break;
				  case"alertverify": case"Alertverify":
					  log.info("Switching to Frame");
					  testdata= testData(colom,row,tdshetnum);
					  data= (String) testdata;
					  alertverify(data);
					  colom++;
					  break;
				  case "close": case "closeBrowser":
					  log.info("Closing Browser");
					  closeBrowser();
					  break;
				  case "alert": case "Alert":
					  log.info("Accepting Alert");
					  driver.switchTo().alert().accept();
					  break;
				  case "getLinks": 
					  log.info("Getting All Links from Webpage");
					  getLinks();
					  break;
				  case "getText": 
					  log.info("Getting Text From Webpage");
					  actionElement(keyword,keywordtype,object,data);
					  break;
				  case "pasteText": 
					  log.info("Paste Text into Textbox");
					  actionElement(keyword,keywordtype,object,data);
					  break;
				  case "getTitle":
					  log.info("Getting Page Title");
					  String title=driver.getTitle();
					  break;
				  case "mouseover": case "MouseOver": case "mouseOver":  case "mwindow": case "mWindow": case "MWindow":
					  log.info("Mouse Over to "+object);
					  actionElement(keyword,keywordtype,object,data);
					  break;
				  case "authentication":
					  log.info("Executing VBScript");
					  authentication();
				  break;
				  case "scrollBar":
					  log.info("Scroll down");
					  actionElement(keyword,keywordtype,object,data);
				  break;
				  default:
					  break;
				  }
				  reportSteps(result,desc,keyword,fileName,object,testcaseid);
				  result="res";
				  
			  }catch(UnhandledAlertException e){
				  driver.switchTo().alert().accept();
				  
			  }catch(Exception e)
			  {	
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
		 reportEmailMain(result,fileName,testcaseid,failcount);
		 for (int i = 0; i < steps.size(); i++) {
			 String re=steps.get(i).result;
				String ds=steps.get(i).desc;
				String kw=steps.get(i).keyword;
				String fn=steps.get(i).fileName;
				String ob=steps.get(i).object;
				String tcid=steps.get(i).testcaseid;
				switch (re) {
				case "Fail":
					 		EmailReportUtil.addTestCaseSteps(ds,re);
					break;
				case "Pass": case "res":
					 		EmailReportUtil.addTestCaseSteps(ds,re);
					break;
				default:
					break;
				}
		 }
		 return result;
	 }
	 public static WebElement actionElement(String keyword, String keywordtype,String object,String data) throws Exception  {
		 WebElement welement=null;
		
			 switch (keyword) {
			 case "click": case "Click":
			 	welement=welement(keywordtype, object);
			 	welement.click();
				break;
			 case "input": case "Input":
			  	welement=welement(keywordtype, object);
			  	welement.clear();
			  	welement.sendKeys(data);
				break;
			 case "select": case "Select":
				 welement=welement(keywordtype, object);
				 new Select(welement).selectByVisibleText(data);
				 break;
			 case "verifytext": case "verifyText":
				 welement=welement(keywordtype, object);
				 break;
			 case "compairText": case "CompairText":
				 welement=welement(keywordtype, object);
				 break;
			 case "wait": case"Wait":
				 Thread.sleep(Long.parseLong(data));
				 break;
			 case "radiobutton": case "Radiobutton": case "radioButton":case "Button":case "button":
				 System.out.println("object is--"+ object);
				 welement=welement(keywordtype, object);
				 welement.click();
				  break;
			 case "links": 
				 welement=welement(keywordtype, data);
				 welement.click();
				 break;
			 case "mouseover": case "MouseOver": case "mouseOver":  case "mwindow": case "mWindow": case "MWindow":
				 welement=welement(keywordtype, object);
				 mouseover(welement);
				 break;
			 case "frame": case "Frame":
				 welement=welement(keywordtype, object);
				 frame(welement);
				 break;	
			 case "getText":
				 welement=welement(keywordtype, object);
				 pastevalue=  getText(welement);
				 break;
			 case "pasteText": 
				 welement=welement(keywordtype, object);
				 welement.sendKeys(pastevalue);
				 break;
			  case "keyDown": 
				  welement=welement(keywordtype, object);
				  welement.sendKeys(Keys.DOWN);
				  break;
			  case "Esc": 
				  log.info("Clicking on Escape Button");
				  welement=welement(keywordtype, object);
				  welement.sendKeys(Keys.ESCAPE);
				  break;
			  case "enterKey": 
				  welement=welement(keywordtype, object);
				  welement.sendKeys(Keys.ENTER);
				  break;
			  case "tab": case "Tab":
				  welement=welement(keywordtype, object);
				  welement.sendKeys(Keys.TAB);
				  break;
			  case "scrollBar":
				  log.info("Scroll down");
				  welement=welement(keywordtype, object);
				  scrollBar(welement);
			  break;
			  default:
				  break;
			  }
		
			return welement;
	 }
	 
	 public static WebElement welement(String keywordtype, String object) throws Exception{
			WebElement welement =null;
		try{	
			switch (keywordtype) {
			case "id": case "Id":
				driverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id(object)));
				welement=driver.findElement(By.id(object));
				return welement ;
			case "xpath": case "Xpath":
				driverWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(object)));
				welement=driver.findElement(By.xpath(object));
				return welement ;
			case "linktext": case "linkText": case "LinkText":
				driverWait.until(ExpectedConditions.visibilityOfElementLocated(By.linkText(object)));
				welement=driver.findElement(By.linkText(object));
				return welement ;
			case "plink": case "PLink":
				driverWait.until(ExpectedConditions.visibilityOfElementLocated(By.partialLinkText(object)));
				welement=driver.findElement(By.partialLinkText(object));
				return welement ;
			case "classname": case "className": case "ClassName":
				driverWait.until(ExpectedConditions.visibilityOfElementLocated(By.className(object)));
				welement=driver.findElement(By.className((object)));
				return welement ;
			default:
				break;
			}
		}catch(Exception e){
			switch (keywordtype) {
			case "id": case "Id":
				Thread.sleep(900);
				welement=driver.findElement(By.id(object));
				return welement ;
			case "xpath": case "Xpath":
				Thread.sleep(900);
				welement=driver.findElement(By.xpath(object));
				return welement ;
			case "linktext": case "linkText": case "LinkText":
				Thread.sleep(900);
				welement=driver.findElement(By.linkText(object));
				return welement ;
			case "plink": case "PLink":
				Thread.sleep(900);
				welement=driver.findElement(By.partialLinkText(object));
				return welement ;
			case "classname": case "className": case "ClassName":
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
