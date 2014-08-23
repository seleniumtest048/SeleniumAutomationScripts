package com.projectname.scripts;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;


public class HomePage {

public static WebDriver driver;


@BeforeTest

public void driverdec(){
	
	driver = new FirefoxDriver();
	driver.get("https://qawhois.team-center.net/");
	driver.manage().window().maximize();
	
}

@Test

public void Logic() throws InterruptedException{
	
	/*String Text = driver.findElement(By.id("domain_search")).getAttribute("value");
	System.out.println("Text in the textbox is " + "....." +Text);
	driver.findElement(By.linkText("Sign In")).click();
	String Text1 = driver.findElement(By.xpath("//html/body/form/div[2]/table/tbody/tr/td[3]/table/tbody/tr[3]/td")).getText();
	String str1=Text1.split("\n")[0].toString()+" "+Text1.split("\n")[1].toString();
	//String str="ssss";
	//Text1.replace("\n", "");
	System.out.println(str1);
	
	String str2="New to WhoIs.net? Request an account here.";
	
	System.out.println(str2.length());
	if(Text1.split("\n")[0].toString() == "New to WhoIs.net?")
	{
	System.out.println("true");
	}
	else
	{
		System.out.println("false");
	}
driver.close();	*/
	
	
	//Tool Tip
	driver.switchTo().frame(driver.findElement(By.xpath("//*[starts-with(@id,'I0_')]")));	
	Actions act=new Actions(driver);
	WebElement wb=driver.findElement(By.xpath("//div/div/span/div/div/div/div"));
	act.moveToElement(wb).perform();
	driver.switchTo().defaultContent();
	Thread.sleep(5000);
	driver.switchTo().frame(driver.findElement(By.xpath("//*[starts-with(@id,'I0_')]")));
	String title=driver.findElement(By.xpath("//*[@id='widget_bounds']/table/tbody/tr")).getText();
	System.out.println("title is ------------------>>"+title);
	driver.close();
}
}




