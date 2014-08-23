package com.projectname.grid.scripts;

import java.io.FileInputStream;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.projectname.utils.TestConstants;

import jxl.Sheet;
import jxl.Workbook;


public class RunThread  {
	
	public static String URL;
	public static String threadMaxCount;
	public static void main(String[] args) throws Exception{
    	String currentpath = new java.io.File( "." ).getCanonicalPath();
    	String path=currentpath.replace("\\", "\\\\");
    	FileInputStream fi=new FileInputStream(path+TestConstants.TEST_DATA_DIR_PATH);
    	Workbook testDataWrkBook=Workbook.getWorkbook(fi);
    	Sheet browsers=testDataWrkBook.getSheet("Browser");
    	URL=browsers.getCell(3, 1).getContents();
    	threadMaxCount=browsers.getCell(0, 1).getContents();
    	
    	String macMFBrowser=browsers.getCell(1, 1).getContents();
    	String macMFBrowserRunmode=browsers.getCell(2, 1).getContents();
    	
    	String macSafariBrowser=browsers.getCell(1, 2).getContents();
    	String macSafariBrowserRunmode=browsers.getCell(2, 2).getContents();
    	
    	String macGCBrowser=browsers.getCell(1, 3).getContents();
    	String macGCBrowserRunmode=browsers.getCell(2, 3).getContents();
    	
    	String windowsMFBrowser=browsers.getCell(1, 4).getContents();
    	String windowsMFBrowserRunmode=browsers.getCell(2, 4).getContents();
    	
    	String windowsGCBrowser=browsers.getCell(1, 5).getContents();
    	String windowsGCBrowserRunmode=browsers.getCell(2, 5).getContents();
    	
    	String windowsIEBrowser=browsers.getCell(1, 6).getContents();
    	String windowsIEBrowserRunmode=browsers.getCell(2, 6).getContents();
    	
    	if (macMFBrowserRunmode.equalsIgnoreCase("y")) {
    		BrowsersThread macMF= new BrowsersThread(macMFBrowser);
    		macMF.start();
    	}	
    	if (macSafariBrowserRunmode.equalsIgnoreCase("y")) {
    		BrowsersThread macSF= new BrowsersThread(macSafariBrowser);
    		macSF.start();
    	}  
    	if (macGCBrowserRunmode.equalsIgnoreCase("y")) {
    		BrowsersThread macGC= new BrowsersThread(macGCBrowser);
    		macGC.start();
    	}
    	if ( windowsMFBrowserRunmode.equalsIgnoreCase("y")) {
    		System.out.println("in fire fox..");
    		BrowsersThread windowsMF= new BrowsersThread(windowsMFBrowser);
    		windowsMF.start();
    	}   
    	if (windowsGCBrowserRunmode.equalsIgnoreCase("y")) {
    		BrowsersThread windowsGC= new BrowsersThread(windowsGCBrowser);
    		windowsGC.start();
    	} 
    	if (windowsIEBrowserRunmode.equalsIgnoreCase("y")) {
    		BrowsersThread windowsIE= new BrowsersThread(windowsIEBrowser);
    		windowsIE.start();
    	}
	}
}