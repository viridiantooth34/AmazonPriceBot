//@author Subhayan Bakshi
// Used- Selenium, Apache Commons mail
package test1;


import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import io.github.bonigarcia.wdm.WebDriverManager;

import java.io.*;
import java.util.*;

public class Main1 {

	public static void main(String[] args) throws InterruptedException, EmailException, IOException {
		//Input amazon product page url
		
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter the URL of the Product Page:");
		
		String url_amzn= sc.nextLine();
		System.out.println("Enter the Expected Product Price");
		int expected_price = sc.nextInt();
		
		System.out.println("Your expected price is Rs."+expected_price+'\n'+"The process has started!!!!!");
		
		
		//Property File
		FileReader reader= new FileReader("PropertyFile/Credentials.properties.txt");
		
		 Properties p=new Properties();  
		 p.load(reader);  
		//System.out.println(p.getProperty("sender mail"));
		
		System.setProperty("webdriver.chrome.driver", "BrowserDrivers/chromedriver.exe");
		WebDriver driver = new ChromeDriver();
		
		 ChromeOptions chromeOptions = new ChromeOptions();
			
			
		 
		 
		/* WebDriverManager.chromedriver().setup();
		 WebDriver driver = new ChromeDriver(chromeOptions);
		 
		 */
		driver.manage().window().maximize();
		//invoke the product page 
		Calendar cal = Calendar.getInstance();
			
		long starttime=cal.getTimeInMillis();
		
		//long currenttime=starttime;
		
		
		while(cal.getTimeInMillis()<starttime+60000) {
			
			
		//	int limit= 4900; // Enter the Start Limit of the Price
		


		
		//driver.get("https://www.amazon.in/Western-Digital-SN550-Internal-WDS500G2B0C/dp/B07YFF3JCN/ref=sr 1 1?crid=EBIH9TE3Z649&keywords=sn550&qid=1641170699&sprefix=sn550%2Caps%2C581&sr=8-1");
		driver.get(url_amzn);
		String price="";
		String price1="";
		String price2="";
		String price3="";
		int price_int;
		price= driver.findElement(By.xpath("//div[@id='apex_desktop']//div[@class]//span[@data-a-size and not(@data-a-strike='true' )]")).getText();
		
		price1= price.substring(1);
		
		//System.out.println(price);
		int i=0;
		price2= price1.replace(",", "");
		
		for(i=0;i<price2.length();i++) {
			if(price2.charAt(i)=='.'||price2.charAt(i)=='\n') {
				
				break;}
			else {
				
				price3= price3+price2.charAt(i);
				
			}
		}
	//	System.out.println(price3);
	//	System.out.println("price3="+price3);
		price_int= Integer.valueOf(price3);
		
////////////////// Price Fetching is done
		
		
			
			System.out.println("Current Price is RS. "+price_int);
			if(price_int<=expected_price) {
				System.out.println("Price of the product is now low! \r\n"
									+"Sending E-Mail....");
				String productName="";
				productName=driver.findElement(By.id("productTitle")).getText();
				
				// class ob = new constructor();
				
				
				//Emailing if price is reduced:
				
				Email email = new SimpleEmail();
				//email.setHostName("smtp.googlemail.com");
				email.setHostName("smtp-mail.outlook.com");
				//email.setSmtpPort(465);
				email.setSmtpPort(587);
				email.setAuthenticator(new DefaultAuthenticator(p.getProperty("sender_mail"), p.getProperty("sender_pass")));
				email.setSSLOnConnect(true);
				email.setFrom(p.getProperty("sender_mail"));
				email.setSubject("Price is reduced");
				email.setMsg("Hi,\r\n"
						+ "\r\n"
						+ "The price of "+productName + " is now reduced to Rs." + price3+"\r\n\n\n"
						+driver.getCurrentUrl()
						+ "\r\n"
						+ "\r\n\n\n\n\n\n\n"
						+ "Thanks and regards,\r\n"
						+ "Team ViridianTooth34 ");
				email.addTo(p.getProperty("receiver_mail"));
				//
				email.setSSL(true);
				
				
				//
				
				email.send();
				System.out.println("E-Mail Successfully Sent");
				driver.quit();
				break;
				
				
			}
		//	Thread.sleep(60000*30);
			
		
		}
			

		driver.quit();
		
		
	}

}
