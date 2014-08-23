package com.projectname.utils;

import java.util.ArrayList;
import java.util.HashMap;

import org.openqa.selenium.WebElement;

import com.projectname.grid.scripts.RunThread;
import com.projectname.scripts.TestReport;

public class ConstantsThreads {

	public static boolean threadValue;
	public static ArrayList<TestReport>	 stepsMacMF = new ArrayList<TestReport>();;
	public static ArrayList<TestReport>	 stepsMacSafari = new ArrayList<TestReport>();;
	public static ArrayList<TestReport>	 stepsMacGC = new ArrayList<TestReport>();;
	
	public static ArrayList<TestReport>  stepsWindowsMF = new ArrayList<TestReport>();;
	public static ArrayList<TestReport> stepsWindowsIE = new ArrayList<TestReport>();;
	public static ArrayList<TestReport> stepsWindowsGC = new ArrayList<TestReport>();;
	
	public static int threadsMaxCount=Integer.parseInt(RunThread.threadMaxCount);
	public static int threadMinCount=1;
	
	public  String result;
	public WebElement welement=null;
	
//	public static ArrayList<String> screenShotPathWindowsMF=new ArrayList<String>();;;
//	public static ArrayList<String> screenShotPathWindowsGC=new ArrayList<String>();;;
//	public static ArrayList<String> screenShotPathWindowsIE=new ArrayList<String>();;;
//	public static ArrayList<String> screenShotPathMacMF=new ArrayList<String>();;;
//	public static ArrayList<String> screenShotPathMacSafari=new ArrayList<String>();;;
//	public static ArrayList<String> screenShotPathMacGC=new ArrayList<String>();;;
	
	
	public static HashMap<String, String> screenShotPathWindowsMF = new HashMap<String,String>();
	public static HashMap<String, String> screenShotPathWindowsGC = new HashMap<String,String>();
	public static HashMap<String, String> screenShotPathWindowsIE = new HashMap<String,String>();
	
	public static HashMap<String, String> screenShotPathMacMF = new HashMap<String,String>();
	public static HashMap<String, String> screenShotPathMacSafari = new HashMap<String,String>();
	public static HashMap<String, String> screenShotPathMacGC = new HashMap<String,String>();
	
	
	

}
