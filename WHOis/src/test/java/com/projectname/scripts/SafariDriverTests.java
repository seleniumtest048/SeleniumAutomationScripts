package com.projectname.scripts;

import org.junit.After;
import org.openqa.selenium.By;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;


public class SafariDriverTests {

  private WebDriver driver = null;

  /*private static boolean isSupportedPlatform() {
    Platform current = Platform.getCurrent();
    return Platform.MAC.is(current) || Platform.WINDOWS.is(current);
  }*/

  @BeforeClass
  public void createDriver() {
//    assumeTrue(isSupportedPlatform());
//    driver = new SafariDriver();
	  driver=new FirefoxDriver();
  }

@Test
  public void quitDriver() {
  
	  driver.get("http://gmail.com");
	  // driver.quit();
  }
/*
  @org.testng.annotations.Test
  public void shouldBeAbleToPerformAGoogleSearch() {
    driver.get("http://www.google.com");
    driver.findElement(By.name("q")).sendKeys("webdriver");
    driver.findElement(By.name("btnG")).click();
    new WebDriverWait(driver, 3)
        .until(ExpectedConditions.titleIs("webdriver - Google Search"));
  }*/
}
