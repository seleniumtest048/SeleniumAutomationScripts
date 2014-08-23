package com.projectname.scripts;



import java.util.regex.Pattern;
import java.util.concurrent.TimeUnit;

import org.junit.*;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;

public class SampleFunctions {
  private WebDriver driver;
  private String baseUrl;
  private boolean acceptNextAlert = true;
  private StringBuffer verificationErrors = new StringBuffer();

  @Before
  public void setUp() throws Exception {
    driver = new FirefoxDriver();
    baseUrl = "https://qawhois.team-center.net/";
    driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
  }

  @Test
  public void testSample() throws Exception {
    driver.get(baseUrl + "/");
    driver.findElement(By.linkText("Sign In")).click();
    Thread.sleep(3000);
    
    String act=  driver.findElement(By.xpath("/html/body/form/div[2]/table/tbody/tr/td[3]/table/tbody/tr[3]/td")).getText().replace("\n", "");
    String exp="New to WhoIs.net?Request an account here.";
    Assert.assertEquals(exp, act);
  }
  
  @Test
  public void testSample1() throws Exception {
    driver.get(baseUrl + "/");
    
    driver.findElement(By.linkText("Quick Links")).click();
    Thread.sleep(3000);

 Assert.assertTrue(driver.findElement(By.linkText("Shared Hosting")).isDisplayed());
  }

  @After
  public void tearDown() throws Exception {
    driver.quit();
    String verificationErrorString = verificationErrors.toString();
    if (!"".equals(verificationErrorString)) {
      fail(verificationErrorString);
    }
  }


}