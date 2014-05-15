package com.projectname.base;

import java.io.File;
import java.io.IOException;
import java.util.Properties;

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

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.Test;

import com.projectname.utils.TestConstants;

public class Html {

	
	@Test
	public void test() throws Exception{
		
		
		WebDriver driver = new FirefoxDriver();
		driver.get("E:\\Framewrk\\SEFramework\\Reports\\piechart.html");
		File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		// Now you can do whatever you need to do with it, for example copy somewhere
		FileUtils.copyFile(scrFile, new File("E:\\Framewrk\\SEFramework\\Reports\\screenshot.png"));
		mail();
	}
	
	
	 public static void mail() throws Exception{

		   String  currentpath,path;     
		   currentpath = new java.io.File( "." ).getCanonicalPath();
		   path=currentpath.replace("\\", "\\\\");
		   Properties props = new Properties();
		   props.put("mail.smtp.auth", "true");
		   props.put("mail.smtp.starttls.enable", "true");
		   props.put("mail.smtp.host", "smtp.gmail.com");
		   props.put("mail.smtp.port", "587");
		   Session session = Session.getInstance(props,
				   new javax.mail.Authenticator() {
			   protected PasswordAuthentication getPasswordAuthentication() {
				   return new PasswordAuthentication("raripineni@bykd.com","sanu0706");
			   }
		   });
		   try{
			   // Create a default MimeMessage object.
			   MimeMessage message = new MimeMessage(session);
			   // Set From: header field of the header.
			   message.setFrom(new InternetAddress("raripineni@bykd.com"));
			   // Set To: Single User
			   //message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
			   //Send Multiple Users with TO and CC
			   message.addRecipients(Message.RecipientType.TO,InternetAddress.parse("raripineni@bykd.com"));
			   /*message.addRecipients(Message.RecipientType.CC,InternetAddress.parse(cc));*/
			   // Set Subject: header field
			   message.setSubject("Pie Chart Report");
			   // Create the message part 
			   BodyPart messageBodyPart = new MimeBodyPart();
			   // Fill the message
			   messageBodyPart.setText("bodymsg");
			   // Create a multipar message
			   Multipart multipart = new MimeMultipart();
			   // Set text message part
			   multipart.addBodyPart(messageBodyPart);
			   // Part two is attachment
			   messageBodyPart = new MimeBodyPart();
			   String filename = path+TestConstants.EMAIL_TESTREPORT_RESULT_DIR_PATH;
			   DataSource source = new FileDataSource("E:\\Framewrk\\SEFramework\\Reports\\screenshot.png");
			   messageBodyPart.setDataHandler(new DataHandler(source));
			   messageBodyPart.setFileName("PIE Chart Report");
			   multipart.addBodyPart(messageBodyPart);
			   // Send the complete message parts
			   message.setContent(multipart );
			   // Send message
			   Transport.send(message);
			   System.out.println("Sent message successfully....");
		   }catch (MessagingException mex) {
			   mex.printStackTrace();
		   }
	}
}
