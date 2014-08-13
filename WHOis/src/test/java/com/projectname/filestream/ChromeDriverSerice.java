
package com.projectname.filestream;




import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

public class ChromeDriverSerice  {
	private static ChromeDriverService service;
	   private WebDriver driver;
	 
	   @BeforeClass
	   public static void createAndStartService() throws Exception {
	     service = new ChromeDriverService.Builder()
	         .usingDriverExecutable(new File("E:\\SeleniumFrameWorkWithJarBased\\FrameWorkJarFile\\libs\\chromedriver.exe"))
	         .usingAnyFreePort()
	         .build();
	     service.start();
	   }
	 
	   @AfterClass
	   public static void createAndStopService() {
	     service.stop();
	   }
	 
	   @Before
	   public void createDriver() {
	     driver = new RemoteWebDriver(service.getUrl(),
	         DesiredCapabilities.chrome());
	   }
	 
	   @After
	   public void quitDriver() {
	   //  driver.quit();
	   }
	 
	   @Test
	   public void testGoogleSearch() {
	     driver.get("http://www.gmail.com");
	     
	     driver.findElement(By.id("Email")).sendKeys("sdfhsdkfksdfdsfsdfsfd");
	     driver.findElement(By.id("Passwd")).sendKeys("test");
	     driver.findElement(By.id("signIn")).click();
	     /*WebElement searchBox = driver.findElement(By.name("q"));
	     searchBox.sendKeys("webdriver");
	     ((WebDriver) searchBox).quit();
	     assertEquals("webdriver - Google Search", driver.getTitle());*/
	     
	     
	   }
	}