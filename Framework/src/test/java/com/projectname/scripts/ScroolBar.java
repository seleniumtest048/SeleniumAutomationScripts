package com.projectname.scripts;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.Test;

public class ScroolBar {
	
	@Test
	public void scrollBar() throws Exception{
		
		
		WebDriver driver=new FirefoxDriver();
		driver.get("http://realtytrac.com");
		driver.manage().window().maximize();
		Thread.sleep(5000);
		WebElement element =driver.findElement(By.xpath("//*[@id='container']/h2"));
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
		Thread.sleep(500);
		
	}

}
