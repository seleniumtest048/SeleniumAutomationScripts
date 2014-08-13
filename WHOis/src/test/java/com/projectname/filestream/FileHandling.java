package com.projectname.filestream;

import java.io.*;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.Test;

public class FileHandling
{
    private WebDriver driver;
    private StringBuffer verificationErrors = new StringBuffer();
    @Test
    public void testUntitled() throws Exception {
     driver=new FirefoxDriver();
      driver.get("https://qawhois.team-center.net/");
      Thread.sleep(5000);
      driver.findElement(By.id("lmToggle")).click();
       String W = driver.findElement(By.xpath("//*[@id='page_content']/table/tbody/tr/td")).getText();
       BufferedWriter o_br1 = new  BufferedWriter(new FileWriter("C:\\Users\\Administrator\\Desktop\\Input1.txt") );
       o_br1.write(W);
       o_br1.close();
       
    	fh();
    }
    public void fh() throws FileNotFoundException, IOException{
        File f1=new File("C:\\Users\\Administrator\\Desktop\\Input1.txt");
        File f2=new File("C:\\Users\\Administrator\\Desktop\\Input2.txt");
        FileInputStream fi1=new FileInputStream(f1);
        FileInputStream fi2=new FileInputStream(f2); 
        
        DataInputStream di1=new DataInputStream(fi1);
        DataInputStream di2=new DataInputStream(fi2);
        
        BufferedReader br1=new BufferedReader(new InputStreamReader(di1));
        BufferedReader br2=new BufferedReader(new InputStreamReader(di2));
        String s1, s2;  

        while ((s1=br1.readLine())!=null && (s2=br2.readLine())!=null) 
         {
        	/*System.out.println("INput file 1 data-------"+s1);
        	System.out.println("INput file 2 data-------"+s2);*/
        if(!s1.equals(s2)){
        System.out.println(s1);
        System.out.println(s1);
          }else{
        	  System.out.println("Passed");
          }
        
        } 
    }

} 
