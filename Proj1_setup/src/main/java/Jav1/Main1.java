//@author Subhayan Bakshi
// Used- Selenium, Apache Commons Email
package Jav1;


import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;
import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
import java.io.*;
import java.util.*;

public class Main1 {

	public static void main(String[] args) throws InterruptedException, EmailException {
		 
		System.setProperty("webdriver.chrome.driver", "BrowserDrivers/chromedriver.exe");
		ChromeDriver driver = new ChromeDriver();
		
		driver.manage().window().maximize();
		//invoke the product page
		Calendar cal = Calendar.getInstance();
			
		long starttime=cal.getTimeInMillis();
		
		long currenttime=starttime;
		
		
		while(cal.getTimeInMillis()<starttime+60000) {
			
			
			int limit= 4900; // Enter the Start Limit of the Price
		
		//Enter below the Product Page URL
		driver.get("https://www.amazon.in/Western-Digital-SN550-Internal-WDS500G2B0C/dp/B07YFF3JCN/ref=sr_1_1?crid=EBIH9TE3Z649&keywords=sn550&qid=1641170699&sprefix=sn550%2Caps%2C581&sr=8-1");
		
		String price="";
		String price1="";
		String price2="";
		String price3="";
		int price_int;
		price= driver.findElement(By.xpath("(//div[@class=\"a-section\"])[2]")).getText();
		price1= price.substring(1);
		int i=0;
		price2= price1.replace(",", "");
		
		for(i=0;i<price2.length();i++) {
			if(price2.charAt(i)=='.') {
				
				break;}
			else {
				
				price3= price3+price2.charAt(i);
				
			}
		}
		
		price_int= Integer.valueOf(price3);
		
////////////////// Price Fetching is done
		
		
			
			System.out.println("Current Price is RS. "+price_int);
			if(price_int<limit) {
				System.out.println("Price of the product is now low! \r\n"
									+"Sending E-Mail....");
				String productName="";
				productName=driver.findElement(By.id("productTitle")).getText();
				
				
				//Emailing if price is reduced:
				
				Email email = new SimpleEmail();
				email.setHostName("smtp.gmail.com");
				email.setSmtpPort(465);
				email.setAuthenticator(new DefaultAuthenticator("your-email@gmail.com", "your_password"));
				email.setSSLOnConnect(true);
				email.setFrom("email2@gmail.com");
				email.setSubject("Price is reduced");
				email.setMsg("Hi,\r\n"
						+ "\r\n"
						+ "The price of "+productName + " is now reduced to Rs." + price3+"\r\n"
						+ "\r\n"
						+ "\r\n"
						+ "Thanks and regards,\r\n"
						+ "Team ViridianTooth34 ");
				email.addTo("Sender's email");
				email.send();
				System.out.println("E-Mail Successfully Sent");
				driver.quit();
				break;
				
				
			}
			Thread.sleep(60000*30);
			
		
		}
			

		driver.quit();
		
		
	}

}
