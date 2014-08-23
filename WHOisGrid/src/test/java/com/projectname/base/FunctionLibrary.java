package com.projectname.base;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import jxl.Sheet;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.firefox.internal.ProfilesIni;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.projectname.scripts.DriverScript;
import com.projectname.scripts.TestReport;
import com.projectname.utils.ConstantsThreads;
import com.projectname.utils.TestConstants;
import com.projectname.utils.TestUtil;

public class FunctionLibrary {

	public WebDriverWait driverWait;
	public String currentpath;
	public String path;
	public FileInputStream fi;
	public Workbook testdatawb, controllerwb;
	public Sheet tdsheet, controlshet;
	public Properties OR, V, EMAIL;
	public String proceedOnFail;
	public String testStatus;
	public FileOutputStream fo, foCsv;
	public WritableWorkbook wwb, csvWwb;
	public WritableSheet ws, wws;
	public String className;
	public String startTime;
	public Logger log;
	public String MainWindowHandle;
	public TestReport tr;
	public int failcount;
	public int rptFailCnt = 0;
	public String siteurl;
	String pastevalue = null;
	ArrayList<TestReport> stepsDesc = new ArrayList<TestReport>();;

	File dir;

	// Keywords keywords=new Keywords();
	/***********************************************************************************************************
	 * Description : Initialize all the Paths for Project Resources Files
	 * Created by : Santhosh R Created Date : 10-Oct-2013 Updated by : Santhosh
	 * R LastUpdated :
	 ***********************************************************************************************************/
	public void initialize() throws Exception {
		currentpath = new java.io.File(".").getCanonicalPath();
		path = currentpath.replace("\\", "\\\\");

		// Reading test data file
		fi = new FileInputStream(path + TestConstants.TEST_DATA_DIR_PATH);
		testdatawb = Workbook.getWorkbook(fi);

		fi = new FileInputStream(path + TestConstants.CONTROLLER_DIR_PATH);
		controllerwb = Workbook.getWorkbook(fi);

		// Reading object properties
		OR = new Properties();
		fi = new FileInputStream(path + TestConstants.OBJECT_REPOSTRY_DIR_PATH);
		OR.load(fi);
		// Reading objects from Verification file
		V = new Properties();
		fi = new FileInputStream(path + TestConstants.VALIDATION_DIR_PATH);
		V.load(fi);

		EMAIL = new Properties();
		fi = new FileInputStream(path + TestConstants.EMAIL_DIR_PATH);
		EMAIL.load(fi);

		log = Logger.getLogger(FunctionLibrary.class.getName());
		PropertyConfigurator.configure("log4j.properties");

	}

	/***********************************************************************************************************
	 * Description : Creates Test Suite for Test Report Created by : Santhosh R
	 * Created Date : 10-Oct-2013 Updated by : Santhosh R LastUpdated :
	 ***********************************************************************************************************/

	public void startTesting() throws Exception {

		String currentpath = new java.io.File(".").getCanonicalPath();
		String path = currentpath.replace("\\", "\\\\");
		ReportUtil
				.startTesting(
						path
								+ com.projectname.utils.TestConstants.TESTREPORT_RESULT_DIR_PATH,
						TestUtil.now("dd.MMMMM.yyyy hh.mm.ss aaa"),
						EMAIL.getProperty("Env"), EMAIL.getProperty("Version"));
	}

	/***********************************************************************************************************
	 * Description : Creates Test Suite for Email Report Created by : Santhosh R
	 * Created Date : 10-Oct-2013 Updated by : Santhosh R LastUpdated :
	 ***********************************************************************************************************/
	public void emailStartTesting() throws Exception {

		String currentpath = new java.io.File(".").getCanonicalPath();
		String path = currentpath.replace("\\", "\\\\");
		EmailReportUtil
				.startTesting(
						path
								+ com.projectname.utils.TestConstants.EMAIL_TESTREPORT_RESULT_DIR_PATH,
						TestUtil.now("dd.MMMMM.yyyy hh.mm.ss aaa"),
						EMAIL.getProperty("Env"), EMAIL.getProperty("Version"));
	}

	/***********************************************************************************************************
	 * Description : It sets Class Name to Results file Created by : Santhosh R
	 * Created Date : 10-Oct-2013 Updated by : Santhosh R LastUpdated :
	 ***********************************************************************************************************/
	public String setTestClassName(String className) {
		this.className = className;
		return className;
	}

	/***********************************************************************************************************
	 * Description : It Returns Class Name Created by : Santhosh R Created Date
	 * : 10-Oct-2013 Updated by : Santhosh R LastUpdated :
	 ***********************************************************************************************************/
	public String getTestClassName() {
		return className;
	}

	public void uRL(WebDriver driver) {
		driver.get(siteurl);

	}

	/***********************************************************************************************************
	 * Description : Verify the Alerts Is Available or Not in a Webpage Created
	 * by : Santhosh R Created Date : 10-Oct-2013 Updated by : Santhosh R
	 * LastUpdated :
	 ***********************************************************************************************************/

	public void alertverify(WebDriver driver, String object) {

		String actual = driver.switchTo().alert().getText();
		System.out.println("Verification Objecct=========>>>"+object);
		String expected=V.getProperty(object);
		log.info("Excepted Data----------------->"+expected);
		log.info("Actual Data ------------------->"+actual);
		try {
			Assert.assertEquals(actual, expected);
		} catch (Throwable t) {
			// error
			log.info("", t);
			log.info("Error in text - " + actual);
			log.info("Actual - " + actual);
			log.info("Expected -" + expected);
			log.fatal("test verify");
			log.error("error");
		}
	}

	/***********************************************************************************************************
	 * Description : Validate two String Values Created by : Santhosh R Created
	 * Date : 10-Oct-2013 Updated by : Santhosh R LastUpdated :
	 ***********************************************************************************************************/
	public String verifyText(WebDriver driver, String keyword,
			String keywordtype, String object, String objectProp)
			throws Exception {
		String veryTxt = null;
		Keywords keywords = new Keywords();
		String expData = V.getProperty(objectProp);

		ConstantsThreads ct = keywords.actionElement(driver, keyword,
				keywordtype, object, objectProp);
		WebElement wb = ct.welement;
		log.debug("Executing verifyText");
		String actual;
		try {
			actual = wb.getText().replace("\n", "");
		} catch (Exception e) {
			actual = wb.getText();
		}
		log.info("Excepted Data -------------->>"+expData);
		log.info("Actual Data --------------->>"+actual);
		try {
			Assert.assertEquals(actual, expData);
			veryTxt = "Pass";
		} catch (Throwable t) {
			// error
			log.info("Error in text - " + object);
			log.info("Actual - " + actual);
			log.info("Expected -" + expData);
			log.fatal("test verify");
			log.error("error");
			veryTxt = "Fail";
		}
		return veryTxt;
	}

	/***********************************************************************************************************
	 * Description : Compare two String Values in Runtime Created by : Santhosh
	 * R Created Date : 10-Oct-2013 Updated by : Santhosh R LastUpdated :
	 ***********************************************************************************************************/
	public String compairText(WebDriver driver, String keyword,
			String keywordtype, String object, String objectProp)
			throws Exception {
		String compairTxt = null;
		ConstantsThreads ct = null;
		Keywords keywords = new Keywords();
		WebElement wb1, wb2;
		String text1, text2;
		log.debug("Executing verifyText");
		String object2 = V.getProperty(objectProp);
		ct = keywords.actionElement(driver, keyword, keywordtype, object,
				objectProp);
		wb1 = ct.welement;
		text1 = wb1.getAttribute("value");
		ct = keywords.actionElement(driver, keyword, keywordtype, object2,
				object);
		wb2 = ct.welement;
		text2 = wb2.getAttribute("value");
		try {
			Assert.assertEquals(text1, text2);
			compairTxt = "Pass";
		} catch (Throwable t) {
			// error
			compairTxt = "Fail";
		}
		return compairTxt;
	}

	/***********************************************************************************************************
	 * Description : It closes the Active window and switch to Next Acitive
	 * Window Created by : Santhosh R Created Date : 10-Oct-2013 Updated by :
	 * Santhosh R LastUpdated :
	 ***********************************************************************************************************/
	public void closeWindow(WebDriver driver) {
		driver.close();
		windowhandle(driver);
	}

	/***********************************************************************************************************
	 * Description : Switch to New window to Old window and Old window to New
	 * window Created by : Santhosh R Created Date : 10-Oct-2013 Updated by :
	 * Santhosh R LastUpdated :
	 ***********************************************************************************************************/
	public void windowhandle(WebDriver driver) {
		Object str[] = driver.getWindowHandles().toArray();
		for (int i = 1; i < str.length; i++) {
			driver.switchTo().window((String) str[i]).navigate();
		}
	}

	/***********************************************************************************************************
	 * Description : Switch to Parent Window Created by : Santhosh R Created
	 * Date : 10-Oct-2013 Updated by : Santhosh R LastUpdated :
	 ***********************************************************************************************************/
	public void Mainwindow(WebDriver driver) {
		Object str[] = driver.getWindowHandles().toArray();
		driver.switchTo().window(
				(String) driver.getWindowHandles().toArray()[0]);

	}

	/***********************************************************************************************************
	 * Description : It will return all Anchor tag values Created by : Santhosh
	 * R Created Date : 10-Oct-2013 Updated by : Santhosh R LastUpdated :
	 ***********************************************************************************************************/
	public void getLinks(WebDriver driver) {
		List<WebElement> elements = driver.findElements(By.tagName("a"));
		for (int i = 0; i < elements.size(); i++) {
			System.out.println(elements.get(i).getText());
		}
	}

	/***********************************************************************************************************
	 * Description : It will Generate Main Report Created by : Santhosh R
	 * Created Date : 10-Oct-2013 Updated by : Santhosh R LastUpdated :
	 ***********************************************************************************************************/
	public void mainReport(String keyword, String result, String fileName) {
		startTime = TestUtil.now("dd.MMMMM.yyyy hh.mm.ss aaa");
		if (result.equalsIgnoreCase("Fail")) {
			testStatus = result;
			// TestUtil.takeScreenShot(path+com.projectname.utils.TestConstants.TESTSUITE_RESULT_DIR_PATH+fileName);
			ReportUtil.addTestCase(keyword, startTime,
					TestUtil.now("dd.MMMMM.yyyy hh.mm.ss aaa"), testStatus);
		} else if (result.equalsIgnoreCase("Pass")
				|| result.equalsIgnoreCase("res")) {
			testStatus = "Pass";
			// TestUtil.takeScreenShot(path+com.projectname.utils.TestConstants.TESTSUITE_RESULT_DIR_PATH+fileName);
			ReportUtil.addTestCase(keyword, startTime,
					TestUtil.now("dd.MMMMM.yyyy hh.mm.ss aaa"), testStatus);
		}
	}

	/***********************************************************************************************************
	 * Description : It Creates Failures Report Step by Step Created by :
	 * Santhosh R Created Date : 10-Oct-2013 Updated by : Santhosh R LastUpdated
	 * :
	 ***********************************************************************************************************/

	public void report(WebDriver driver, String result, String stepDescription,
			String keyword, String fileName, String object, String testcaseid)
			throws Exception {
		startTime = TestUtil.now("dd.MMMMM.yyyy hh.mm.ss aaa");
		switch (result) {
		case "Fail":
			testStatus = result;
			TestUtil.takeScreenShot(
					driver,
					path
							+ com.projectname.utils.TestConstants.TESTSUITE_RESULT_DIR_PATH
							+ fileName);
			ReportUtil.addKeyword(stepDescription, keyword, result, fileName);
			break;
		case "Pass":
			testStatus = result;
			// TestUtil.takeScreenShot(path+com.projectname.utils.TestConstants.TESTSUITE_RESULT_DIR_PATH+fileName);
			ReportUtil.addKeyword(stepDescription, keyword, result, fileName);
			break;
		default:
			break;
		}
	}

	/***********************************************************************************************************
	 * Description : It Creates Pass Report Step by Step Created by : Santhosh R
	 * Created Date : 10-Oct-2013 Updated by : Santhosh R LastUpdated :
	 ***********************************************************************************************************/
	public void reportSteps(String threadName, WebDriver driver, String result,
			String desc, String keyword, String fileName, String object,
			String testcaseid,String testLinkID) throws Exception {

		switch (result) {
		case "Fail":
			report(driver, result, desc, keyword, fileName, object, testcaseid);
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
			} else if (threadName.equalsIgnoreCase("windowsIE")) {
				ConstantsThreads.stepsWindowsIE.add(tr);
			} else if (threadName.equalsIgnoreCase("windowsGC")) {
				ConstantsThreads.stepsWindowsGC.add(tr);
			} else if (threadName.equalsIgnoreCase("macMF")) {
				ConstantsThreads.stepsMacMF.add(tr);
			} else if (threadName.equalsIgnoreCase("macSafari")) {
				ConstantsThreads.stepsMacSafari.add(tr);
			} else if (threadName.equalsIgnoreCase("macGC")) {
				ConstantsThreads.stepsMacGC.add(tr);
			}
			rptFailCnt++;
			break;
		case "res":
		case "Pass":
			result = "Pass";
			report(driver, result, desc, keyword, fileName, object, testcaseid);
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
			} else if (threadName.equalsIgnoreCase("windowsIE")) {
				ConstantsThreads.stepsWindowsIE.add(tr);
			} else if (threadName.equalsIgnoreCase("windowsGC")) {
				ConstantsThreads.stepsWindowsGC.add(tr);
			} else if (threadName.equalsIgnoreCase("macMF")) {
				ConstantsThreads.stepsMacMF.add(tr);
			} else if (threadName.equalsIgnoreCase("macSafari")) {
				ConstantsThreads.stepsMacSafari.add(tr);
			} else if (threadName.equalsIgnoreCase("macGC")) {
				ConstantsThreads.stepsMacGC.add(tr);
			}
			break;
		case "Skip":
			report(driver, result, desc, keyword, fileName, object, testcaseid);
			tr.result = "Skip";
			tr.desc = desc;
			tr.keyword = keyword;
			tr.fileName = fileName;
			tr.object = object;
			tr.testcaseid = testcaseid;
			stepsDesc.add(tr);
			if (threadName.equalsIgnoreCase("windowsMF")) {
				ConstantsThreads.stepsWindowsMF.add(tr);
			} else if (threadName.equalsIgnoreCase("windowsIE")) {
				ConstantsThreads.stepsWindowsIE.add(tr);
			} else if (threadName.equalsIgnoreCase("windowsGC")) {
				ConstantsThreads.stepsWindowsGC.add(tr);
			} else if (threadName.equalsIgnoreCase("macMF")) {
				ConstantsThreads.stepsMacMF.add(tr);
			} else if (threadName.equalsIgnoreCase("macSafari")) {
				ConstantsThreads.stepsMacSafari.add(tr);
			} else if (threadName.equalsIgnoreCase("macGC")) {
				ConstantsThreads.stepsMacGC.add(tr);
			}
			rptFailCnt++;
			break;
		default:
			break;
		}
	}

	/***********************************************************************************************************
	 * Description : It Creates Email Report Step by Step Created by : Santhosh
	 * R Created Date : 10-Oct-2013 Updated by : Santhosh R LastUpdated :
	 ***********************************************************************************************************/
	/*
	 * public void reportEmailMain(String result, String fileName,String
	 * keyword,int failcount){ System.out.println("Fail count--"+failcount); if
	 * (failcount>=1 || rptFailCnt>=1){ testStatus="Fail";
	 * EmailReportUtil.addTestCase(keyword, startTime,
	 * TestUtil.now("dd.MMMMM.yyyy hh.mm.ss aaa"), testStatus ); }else
	 * if(result==null){ testStatus=""; EmailReportUtil.addTestCase(keyword,
	 * startTime, TestUtil.now("dd.MMMMM.yyyy hh.mm.ss aaa"), testStatus ); }
	 * else{ testStatus="Pass"; EmailReportUtil.addTestCase(keyword, startTime,
	 * TestUtil.now("dd.MMMMM.yyyy hh.mm.ss aaa"), testStatus ); } }
	 */

	/***********************************************************************************************************
	 * Description : Mouse over to Elements Created by : Santhosh R Created Date
	 * : 10-Oct-2013 Updated by : Santhosh R LastUpdated :
	 ***********************************************************************************************************/
	public void mouseover(WebDriver driver, WebElement we) throws Exception {
		// Thread.sleep(5000);
		Actions builder = new Actions(driver);
		builder.moveToElement(we).perform();
	}

	/***********************************************************************************************************
	 * Description : Switch to Frame Created by : Santhosh R Created Date :
	 * 10-Oct-2013 Updated by : Santhosh R LastUpdated :
	 ***********************************************************************************************************/
	public void frame(WebDriver driver, WebElement elem) {
		driver.switchTo().frame(elem);
	}

	/***********************************************************************************************************
	 * Description : Returns Text from web page Created by : Santhosh R Created
	 * Date : 10-Oct-2013 Updated by : Santhosh R LastUpdated :
	 ***********************************************************************************************************/
	public String getText(WebElement welem) {
		String txtfieldvalue = welem.getAttribute("value");
		// String
		// txtvalue=txtfieldvalue.substring(txtfieldvalue.lastIndexOf("/")+1,
		// txtfieldvalue.length());
		String txtvalue = txtfieldvalue.substring(txtfieldvalue
				.lastIndexOf("/") + 1);
		return txtvalue;
	}

	/***********************************************************************************************************
	 * Description : Read the Test Data from Excel Files Created by : Santhosh R
	 * Created Date : 10-Oct-2013 Updated by : Santhosh R LastUpdated :
	 ***********************************************************************************************************/
	public Object testData(int colom, int row, String tdshetnum) {
		String data = null;
		tdsheet = testdatawb.getSheet(tdshetnum);
		data = tdsheet.getCell(colom, row).getContents();
		return data;
	}

	/***********************************************************************************************************
	 * Description : Creates Excel file under Test Results Folder Created by :
	 * Santhosh R Created Date : 10-Oct-2013 Updated by : Santhosh R LastUpdated
	 * :
	 ***********************************************************************************************************/
	public void generateExcel(int sno) throws Exception {
		Date currentdate = new Date();
		SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd hh");
		String Date = ft.format(currentdate);

		/**
		 * creating a New Directory
		 */
		dir = new File(path + TestConstants.TESTSUITE_RESULT_DIR_PATH + "\\"
				+ Date + "TestSuite");
		if (dir.exists()) {
			// System.out.println("A folder with name 'new folder' is already exist in the path "+path);
		} else {
			dir.mkdir();
		}
		fo = new FileOutputStream(dir.getPath() + "//" + getTestClassName()
				+ "_TestResults.xls");
		wwb = Workbook.createWorkbook(fo);
		generateSheet(sno);

	}

	/***********************************************************************************************************
	 * Description : Creates Excel file under Reports Folder Created by :
	 * Santhosh R Created Date : 10-Oct-2013 Updated by : Santhosh R LastUpdated
	 * :
	 * 
	 * @throws Exception
	 ***********************************************************************************************************/

	public void generateCsvFile(int sno, float pass, float fail)
			throws Exception {

		System.out.println(path);
		FileWriter writer = new FileWriter(path
				+ TestConstants.CSVFILE_CREATION_DIR_PATH);

		writer.append("data");
		writer.append(',');
		writer.append("value");
		writer.append('\n');

		writer.append("Pass" + "  " + Float.toString(pass));
		writer.append(',');
		writer.append(Float.toString(pass));
		writer.append('\n');

		writer.append("Fail" + "  " + Float.toString(fail));
		writer.append(',');
		writer.append(Float.toString(fail));
		writer.append('\n');

		// generate whatever data you want

		writer.flush();
		writer.close();
		System.out.println("file created");

	}

	/***********************************************************************************************************
	 * Description : It Creates New JXL Sheet Created by : Santhosh R Created
	 * Date : 10-Oct-2013 Updated by : Santhosh R LastUpdated :
	 ***********************************************************************************************************/
	public void generateSheet(int sno) throws Exception {
		ws = wwb.createSheet("Sheet1", sno);
		Label testidlabel = new Label(0, 0, "Testcase ID");
		Label testdesclabel = new Label(1, 0, "Test Description");
		Label testresultlabel = new Label(2, 0, "Result");
		ws.addCell(testidlabel);
		ws.addCell(testdesclabel);
		ws.addCell(testresultlabel);

	}

	/***********************************************************************************************************
	 * Description : It will Creates Cells Under Excel Sheet Created by :
	 * Santhosh R Created Date : 10-Oct-2013 Updated by : Santhosh R LastUpdated
	 * :
	 ***********************************************************************************************************/
	public void createLabel(int k, String testcasid, String testdesc,
			String result) throws Exception {
		Label testcaseid = new Label(0, k, testcasid);
		Label testcasedesc = new Label(1, k, testdesc);
		Label testresult = new Label(2, k, result);
		ws.addCell(testcaseid);
		ws.addCell(testcasedesc);
		ws.addCell(testresult);
	}

	/***********************************************************************************************************
	 * Description : It will executes the WBScripts Files Created by : Santhosh
	 * R Created Date : 10-Oct-2013 Updated by : Santhosh R LastUpdated :
	 ***********************************************************************************************************/
	public void authentication() throws Exception {
		Runtime.getRuntime().exec(
				"wscript " + path + TestConstants.VB_REG_POPUP);
	}

	/***********************************************************************************************************
	 * Description : It will executes the WBScripts Files Created by : Santhosh
	 * R Created Date : 10-Oct-2013 Updated by : Santhosh R LastUpdated :
	 ***********************************************************************************************************/
	public void generateCSVFile() throws Exception {
		currentpath = new java.io.File(".").getCanonicalPath();
		path = currentpath.replace("\\", "\\\\");
		System.out.println("vb path " + path);
		Runtime.getRuntime().exec(
				"wscript " + path + "\\Reports"
						+ TestConstants.CONVERTCSV_REG_POPUP);
	}

	/***********************************************************************************************************
	 * Description : It will Send a mail of Test Result Report Created by :
	 * Santhosh R Created Date : 10-Oct-2013 Updated by : Santhosh R LastUpdated
	 * :
	 ***********************************************************************************************************/
	public void mail() throws Exception {

		String currentpath, path;
		currentpath = new java.io.File(".").getCanonicalPath();
		path = currentpath.replace("\\", "\\\\");
		final String username = EMAIL.getProperty("username");
		final String password = EMAIL.getProperty("password");
		String from = EMAIL.getProperty("from");
		String to = EMAIL.getProperty("to");
		String cc = EMAIL.getProperty("cc");
		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");
		Session session = Session.getInstance(props,
				new javax.mail.Authenticator() {
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication(username, password);
					}
				});
		try {
			// Create a default MimeMessage object.
			MimeMessage message = new MimeMessage(session);
			// Set From: header field of the header.
			message.setFrom(new InternetAddress(from));
			// Set To: Single User
			// message.addRecipient(Message.RecipientType.TO, new
			// InternetAddress(to));
			// Send Multiple Users with TO and CC
			message.addRecipients(Message.RecipientType.TO,
					InternetAddress.parse(to));
			/*
			 * message.addRecipients(Message.RecipientType.CC,InternetAddress.parse
			 * (cc));
			 */
			// Set Subject: header field
			message.setSubject(EMAIL.getProperty("subject"));
			// Create the message part
			BodyPart messageBodyPart = new MimeBodyPart();
			// Fill the message
			messageBodyPart.setText(EMAIL.getProperty("bodymsg"));
			// Create a multipar message
			Multipart multipart = new MimeMultipart();
			// Set text message part
			multipart.addBodyPart(messageBodyPart);
			// Part two is attachment
			messageBodyPart = new MimeBodyPart();
			String filename = path
					+ TestConstants.EMAIL_TESTREPORT_RESULT_DIR_PATH;
			String filename1 = path + TestConstants.PIECHART_SCREENSHOT_PATH;
			addAttachment(multipart, filename1);
			addAttachment(multipart, filename);
			/*
			 * DataSource source = new FileDataSource(filename1);
			 * messageBodyPart.setDataHandler(new DataHandler(source));
			 * messageBodyPart.setFileName("TestResults");
			 * multipart.addBodyPart(messageBodyPart);
			 */
			// Send the complete message parts
			message.setContent(multipart);
			// Send message
			Transport.send(message);
			System.out.println("Sent message successfully....");
		} catch (MessagingException mex) {
			mex.printStackTrace();
			log.info("", mex);
		}
	}

	private void addAttachment(Multipart multipart, String filename)
			throws Exception {
		BodyPart messageBodyPart = new MimeBodyPart();
		DataSource source = new FileDataSource(filename);
		messageBodyPart.setDataHandler(new DataHandler(source));
		messageBodyPart.setFileName(filename);
		multipart.addBodyPart(messageBodyPart);
	}

	/***********************************************************************************************************
	 * Description : Generate Pie-Chart Created by : Santhosh R Created Date :
	 * 10-Oct-2013 Updated by : Santhosh R LastUpdated :
	 * 
	 * @throws Exception
	 ***********************************************************************************************************/
	public void generatePieChart() throws Exception {

		WebDriver driver = new FirefoxDriver();
		driver.get(path + TestConstants.PIECHART_HTML_PATH);
		File scrFile = ((TakesScreenshot) driver)
				.getScreenshotAs(OutputType.FILE);
		// Now you can do whatever you need to do with it, for example copy
		// somewhere
		FileUtils.copyFile(scrFile, new File(path
				+ TestConstants.PIECHART_SCREENSHOT_PATH));
		driver.quit();

	}

	/***********************************************************************************************************
	 * Description : It will Close the Webbrowser Created by : Santhosh R
	 * Created Date : 10-Oct-2013 Updated by : Santhosh R LastUpdated :
	 ***********************************************************************************************************/
	public void closeBrowser(WebDriver driver) throws Exception {
		Thread.sleep(3000);
		driver.quit();
		String tcID = null;
		/*System.out.println("Thread Max count is-------->>>>>"
				+ ConstantsThreads.threadsMaxCount);
		System.out.println("Thread Min count is-------->>>>>"
				+ ConstantsThreads.threadMinCount);*/

		if (ConstantsThreads.threadsMaxCount == ConstantsThreads.threadMinCount) {
			for (int i = 0; i < stepsDesc.size(); i++) {
				
				String TestLinkID=stepsDesc.get(i).testLinkID;
				// Mac Results
				String macMFTestResults = null;
				String macMFTestLinkID = null;
				if (ConstantsThreads.stepsMacMF.size() > 0) {
					macMFTestResults = ConstantsThreads.stepsMacMF.get(i).result;
					macMFTestLinkID = ConstantsThreads.stepsMacMF.get(i).testLinkID;
				}
				String macGCStatus = null;
				String macGCTestLinkID = null;
				if (ConstantsThreads.stepsMacGC.size() > 0) {
					macGCStatus = ConstantsThreads.stepsMacGC.get(i).result;
					macGCTestLinkID=ConstantsThreads.stepsMacGC.get(i).testLinkID;
				}
				String macSafariStatus = null;
				String macSafariTestLinkID = null;
				if (ConstantsThreads.stepsMacSafari.size() > 0) {
					macSafariStatus = ConstantsThreads.stepsMacSafari.get(i).result;
					macSafariTestLinkID=ConstantsThreads.stepsMacSafari.get(i).testLinkID;
				}
				// Windows Results
				String windowsMFResults = null;
				String windowsMFTestLinkID=null;
				if (ConstantsThreads.stepsWindowsMF.size() > 0) {
					windowsMFResults = ConstantsThreads.stepsWindowsMF.get(i).result;
					windowsMFTestLinkID=ConstantsThreads.stepsWindowsMF.get(i).testLinkID;
				}
				String windowGCResults = null;
				String windowGCTestLinkID = null;
				if (ConstantsThreads.stepsWindowsGC.size() > 0) {
					windowGCResults = ConstantsThreads.stepsWindowsGC.get(i).result;
					windowGCTestLinkID = ConstantsThreads.stepsWindowsGC.get(i).testLinkID;
				}
				String windowIEResults = null;
				String windowIETestLinkID = null;
				if (ConstantsThreads.stepsWindowsIE.size() > 0) {
					windowIEResults = ConstantsThreads.stepsWindowsIE.get(i).result;
					windowIETestLinkID=ConstantsThreads.stepsWindowsIE.get(i).testLinkID;
				}
				String testCaseID = stepsDesc.get(i).testcaseid;
				String testCaseDESC = stepsDesc.get(i).desc;

				if (testCaseID.equals(tcID)) {
					EmailReportUtil.addTestCaseSteps("",TestLinkID, testCaseDESC,
							windowsMFResults, windowIEResults, windowGCResults,
							macMFTestResults, macSafariStatus, macGCStatus,windowsMFTestLinkID,windowIETestLinkID,windowGCTestLinkID,macMFTestLinkID,macSafariTestLinkID,macGCTestLinkID);
				} else {
					tcID = testCaseID;
					EmailReportUtil.addTestCaseSteps(tcID,TestLinkID, testCaseDESC,
							windowsMFResults, windowIEResults, windowGCResults,
							macMFTestResults, macSafariStatus, macGCStatus,windowsMFTestLinkID,windowIETestLinkID,windowGCTestLinkID,macMFTestLinkID,macSafariTestLinkID,macGCTestLinkID);
				}
			}
			DriverScript.endScript();
		}
		ConstantsThreads.threadMinCount++;
	}

	public void scrollBar(WebDriver driver, WebElement element) {
		((JavascriptExecutor) driver).executeScript(
				"arguments[0].scrollIntoView(true);", element);
	}

	public String getAttributeValue(WebDriver driver, String keyword,
			String keywordtype, String object, String objectProp)
			throws Exception {
		Keywords keywords = new Keywords();
		String expData = V.getProperty(objectProp);
		String veryTxt = null;
		ConstantsThreads ct = keywords.actionElement(driver, keyword,
				keywordtype, object, objectProp);
		WebElement wb = ct.welement;
		log.debug("Executing verifyText");
		String actual = wb.getAttribute("value");
		log.info(expData);
		log.info(actual);
		try {
			Assert.assertEquals(actual, expData);
			veryTxt = "Pass";
		} catch (Throwable t) {
			// error
			log.info("Error in text - " + object);
			log.info("Actual - " + actual);
			log.info("Expected -" + expData);
			log.fatal("test verify");
			log.error("error");
			veryTxt = "Fail";
		}
		return veryTxt;
	}

	public String getAttributeAlt(WebDriver driver, String keyword,
			String keywordtype, String object, String objectProp)
			throws Exception {
		Keywords keywords = new Keywords();
		String expData = V.getProperty(objectProp);
		String veryTxt = null;
		ConstantsThreads ct = keywords.actionElement(driver, keyword,
				keywordtype, object, objectProp);
		WebElement wb = ct.welement;
		log.debug("Executing verifyText");
		String actual = wb.getAttribute("alt");
		log.info(expData);
		log.info(actual);
		try {
			Assert.assertEquals(actual, expData);
			veryTxt = "Pass";
		} catch (Throwable t) {
			// error
			log.info("Error in text - " + object);
			log.info("Actual - " + actual);
			log.info("Expected -" + expData);
			log.fatal("test verify");
			log.error("error");
			veryTxt = "Fail";
		}
		return veryTxt;
	}

	public String isDisplayed(WebDriver driver, String keyword,
			String keywordtype, String object, String objectProp)
			throws Exception {
		String isDisplay = null;
		Keywords keywords = new Keywords();
		ConstantsThreads ct = keywords.actionElement(driver, keyword,
				keywordtype, object, objectProp);
		WebElement wb = ct.welement;
		try {
			wb.isDisplayed();
			isDisplay = "Pass";
		} catch (Exception e) {
			isDisplay = "Fail";
		}

		return isDisplay;
	}

	public String isSelected(WebDriver driver, String keyword,
			String keywordtype, String object, String objectProp)
			throws Exception {
		String isSelected = null;
		Keywords keywords = new Keywords();
		ConstantsThreads ct = keywords.actionElement(driver, keyword,
				keywordtype, object, objectProp);
		WebElement wb = ct.welement;
		try {
			wb.isDisplayed();
			isSelected = "Pass";
		} catch (Exception e) {
			isSelected = "Fail";
		}
		return isSelected;
	}

	public String isEnabled(WebDriver driver, String keyword,
			String keywordtype, String object, String objectProp)
			throws Exception {
		String isEnabled = null;
		Keywords keywords = new Keywords();
		ConstantsThreads ct = keywords.actionElement(driver, keyword,
				keywordtype, object, objectProp);
		WebElement wb = ct.welement;
		try {
			wb.isEnabled();
			isEnabled = "Pass";
		} catch (Exception e) {
			isEnabled = "Fail";
		}
		return isEnabled;
	}

	public String verifyPageTitle(WebDriver driver, String keyword,
			String keywordtype, String object, String objectProp)
			throws Exception {
		String veryTxt = null;
		String expData = V.getProperty(objectProp);
		String actual = driver.getTitle();
		log.info("Excepted Data--------------->>"+expData);
		log.info("Actual Data-------------->>"+actual);
		try {
			Assert.assertEquals(actual, expData);
			veryTxt = "Pass";
		} catch (Throwable t) {
			// error
			log.info("Error in text - " + object);
			log.info("Actual - " + actual);
			log.info("Expected -" + expData);
			log.fatal("test verify");
			log.error("error");
			veryTxt = "Fail";
		}
		return veryTxt;
	}

	public String compareTwoFiles(WebDriver driver, String keyword,
			String keywordtype, String object, String data) throws Exception {
		String result = null;
		log.info("creating new text file-------------");
		Keywords keywords = new Keywords();
		ConstantsThreads ct = keywords.actionElement(driver, keyword,
				keywordtype, object, data);
		String webText = ct.welement.getText();
		BufferedWriter fileWriter = new BufferedWriter(new FileWriter(path
				+ TestConstants.DYNAMICFILES + data + ".txt"));
		fileWriter.write(webText);
		fileWriter.close();
		log.info("reading<------>" + data + "<---->text files");
		File f1 = new File(path + TestConstants.DYNAMICFILES + data + ".txt");
		File f2 = new File(path + TestConstants.STATICFILES + data + ".txt");
		FileInputStream fi1 = new FileInputStream(f1);
		FileInputStream fi2 = new FileInputStream(f2);

		DataInputStream di1 = new DataInputStream(fi1);
		DataInputStream di2 = new DataInputStream(fi2);

		BufferedReader br1 = new BufferedReader(new InputStreamReader(di1));
		BufferedReader br2 = new BufferedReader(new InputStreamReader(di2));
		String s1, s2;
		int failCount = 0;
		while ((s1 = br1.readLine()) != null && (s2 = br2.readLine()) != null) {
			if (!s1.equals(s2)) {
				failCount++;
			}
		}
		if (failCount > 0) {
			result = "Fail";
		} else {
			result = "Pass";
		}
		return result;
	}
	/************************************* Management Portal functions
	 * @throws Exception *******************************************/
	
	
	public void deleteBlackList(String object,String data,WebDriver driver) throws Exception{
		//int tableCount=webTable(object);
		int tableCount=driver.findElements(By.xpath(object)).size();
		System.out.println("black list table row count is=========>>"+tableCount);
		for (int i = tableCount-1; i >= 1; i--) {
			Thread.sleep(3000);
			System.out.println("Ip Address ID isss=====?>>>>>>"+"ip_start_"+i);
			WebElement ipAddress=driver.findElement(By.id("ip_start_"+i));
			String ipAdd=ipAddress.getAttribute("value");
			System.out.println("Ip Address Value-================>>>"+ipAdd);
			
			int deleteImg=i+1;
			System.out.println("delete img---------==========>>"+deleteImg);
//			System.out.println("//*[@id='ipAddress_details']/table/tbody/tr["+deleteImg+"]/td[7]/a/img");
			if (ipAdd.equalsIgnoreCase(data)) {
			driver.findElement(By.xpath("//*[@id='ipAddress_details']/table/tbody/tr["+deleteImg+"]/td[7]/a/img")).click();
			try {
				Thread.sleep(3000);
				driver.switchTo().alert().accept();
				Thread.sleep(3000);
				driver.switchTo().alert().accept();
			} catch (Exception e) {
				// TODO: handle exception
			}
			}
		}
	}
	
	public void deleteWhiteList(String object,String data,WebDriver driver) throws Exception{
		//int tableCount=webTable(object);
		int tableCount=driver.findElements(By.xpath(object)).size();
		System.out.println("black list table row count is=========>>"+tableCount);
		for (int i = tableCount-1; i >= 1; i--) {
			Thread.sleep(3000);
			System.out.println("Ip Address ID isss=====?>>>>>>"+"ip_start_"+i);
			WebElement ipAddress=driver.findElement(By.id("ip_start_"+i));
			String ipAdd=ipAddress.getAttribute("value");
			System.out.println("Ip Address Value-================>>>"+ipAdd);
			
			int deleteImg=i+1;
			System.out.println("delete img---------==========>>"+deleteImg);
//			System.out.println("//*[@id='ipAddress_details']/table/tbody/tr["+deleteImg+"]/td[7]/a/img");
			if (ipAdd.equalsIgnoreCase(data)) {
			driver.findElement(By.xpath("//*[@id='ipAddress_details']/table/tbody/tr["+deleteImg+"]/td[8]/a/img")).click();
			try {
				Thread.sleep(3000);
				driver.switchTo().alert().accept();
				Thread.sleep(3000);
				driver.switchTo().alert().accept();
			} catch (Exception e) {
				// TODO: handle exception
			}
			}
		}
	}	
	
	public int editErroMeassages(String object, String data,int colom, int row, String tdshetnum,WebDriver driver) throws Exception{
		
		int tableCount=driver.findElements(By.xpath(object)).size();
		System.out.println("table count=============>>>"+tableCount);
		for (int i = tableCount-1; i >= 1; i--) {
			WebElement errorMSG=driver.findElement(By.id("error_name_"+i));
			String erroMSG=errorMSG.getAttribute("value");
			System.out.println("Key Values are==========>>>"+erroMSG);
			if (erroMSG.equals(data)) {
				data= (String) testData(colom,row,tdshetnum);
				data=(String) data;
				colom++;
				System.out.println("test Data Error mEssage====>>>"+data);
				System.out.println("Err value is======>"+"ip_start_"+i);
				System.out.println("ErroMessage value is======>"+"//*[@id='error_message_"+i+"']");

				driver.findElement(By.xpath("//*[@id='error_message_"+i+"']")).clear();
				Thread.sleep(2000);
				driver.findElement(By.xpath("//*[@id='error_message_"+i+"']")).sendKeys(data);
				Thread.sleep(5000);
				driver.findElement(By.xpath("//*[@id='error_message_"+i+"']")).sendKeys(Keys.TAB);
				Thread.sleep(10000);
				driver.findElement(By.xpath("//*[@id='error_details']/table/tbody/tr["+i+"]/td[4]/a/img")).click();
				//*[@id='error_details']/table/tbody/tr[5]/td[4]/a/img
				Thread.sleep(10000);
				driver.switchTo().alert().accept();
			}
		}
		return colom;
	}
	
public String verifyingErroMeassages(String object, String actual,int colom, int row, String tdshetnum,WebDriver driver) throws Exception{
	String result = null;
		int tableCount=driver.findElements(By.xpath(object)).size();
		System.out.println("table count=============>>>"+tableCount);
		for (int i = tableCount-1; i >= 1; i--) {
			WebElement errorMSG=driver.findElement(By.id("error_name_"+i));
			String erroMSG=errorMSG.getAttribute("value");
			if (erroMSG.equals(actual)) {
				actual= (String) testData(colom,row,tdshetnum);
				actual=(String) actual;
				colom++;
				WebElement  actualData=driver.findElement(By.xpath("//*[@id='error_message_"+i+"']"));
				String expData=actualData.getText();
				if (actual.equals(expData)) {
					result="pass";
				}else{
					result="Fail";
				}
			}
		}
		return result;
	}
	public int webTable(String object, WebDriver driver){
		int rCount=driver.findElements(By.xpath(object)).size();
		System.out.println("object count is------"+rCount);
		
		/**
		 * Getting text randomly form webpage
		 */
		/*for (int i = 3; i <= rCount; i++) {
			String txt=driver.findElement(By.xpath("//*[@id='availabilityTable0']/tbody/tr["+i+"]")).getText();
			System.out.println("table values---"+txt);
		}
		int Scount = (int) (3+(Math.random()*(6- 3)));
		System.out.println("random value---"+Scount);
		driver.findElement(By.xpath("//*[@id='availabilityTable0']/tbody/tr["+Scount+"]/td[5]/p/input")).click();
		System.out.println("random value---"+Scount);*/
		return rCount-1;
	}
}