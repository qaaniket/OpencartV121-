package testBase;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.Properties;

import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Platform;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;

import org.apache.logging.log4j.LogManager;//Log4j
import org.apache.logging.log4j.Logger;//Log4j


//this 'BaseClass' we can keep in separate package means this 'testBase' package or we can keep in that 'testCases' package

//whatever things we created which are commonly required for multiple test cases we will separate them into this class
//this base class is for all the test cases classes means this base class is a parent class of all the test case classes
//so now onwards every test case class that must be 'extends' from 'BaseClass'
//this base class contains reusable methods, whatever required for all the test cases those methods we will keep inside the base class
//purpose - so we can achieve the reusability at the same time we can avoid duplication

public class BaseClass {

//BaseClass anyway we create one driver so this is referring everywhere but additionally in ExtentReportManager the BaseClass object also is having their own driver because we created an object, we are not directly accessing the driver from the BaseClass so this have another driver but if we have two driver instances definitely there will be conflict, execution will not be happen
//so we have to make it as a common variable across multiple objects only by making the variable is static
	
	public static WebDriver driver;//global class variable
	
//here writing some piece of code which will generate the Log file	
	public Logger logger;//Log4j
	
	public Properties p;
	
//if I am not specify @BeforeClass & @AfterClass then I will get this error msg [Cannot invoke "org.openqa.selenium.SearchContext.findElement(org.openqa.selenium.By)" because "this.searchContext" is null]
//we no need to specify setup() & tearDown() methods on dependent methods so depends on methods parameter accepts only test methods
		@BeforeClass(groups = {"Sanity", "Regression", "Master"})//we can also DataDriven test but it takes more time so will not add
		
		@Parameters({"os","browser"})//here for Sequential, cross-browser & parallel testing we have to add parameter tag & pass the parameters
		
		public void setup(String os, String br) throws IOException
		{
			//Loading config.properties file
//config.properties file we have to load at the beginning stage because setup() will execute first before all methods
			FileReader file=new FileReader("./src//test//resources//config.properties");
			p=new Properties();
			p.load(file);
						
			
//at the run time whichever class we are running, it should take class name dynamically by using this.getClass()
		  logger=LogManager.getLogger(this.getClass());//this is always representing the class

           //if execution environment is 'remote' (Grid setup)
		  
//from properties file if we pass 'remote' execution environment
//we are taking the 'environment value' from the 'properties file' but still we are taking the 'operating system, browser name' value from 'xml file' 
		  if(p.getProperty("execution_env").equalsIgnoreCase("remote"))
		  {
			  DesiredCapabilities capabilities=new DesiredCapabilities();
			  
//here we have to decide 'operating system'
			 if(os.equalsIgnoreCase("windows"))
			 {
				capabilities.setPlatform(Platform.WIN11); 				
			 }
			 else if(os.equalsIgnoreCase("linux"))
			 {
				 capabilities.setPlatform(Platform.LINUX);
			 }
			 else if(os.equalsIgnoreCase("mac"))
			 {
				 capabilities.setPlatform(Platform.MAC);
			 }
			 else
			 {
				 System.out.println("No matching os");
				 return;
			 }
			 
//here we have to decide 'browser'		 
			 switch (br.toLowerCase()) 
			 {
			  case "chrome": capabilities.setBrowserName("chrome");break;
			  case "edge": capabilities.setBrowserName("MicrosoftEdge");break;
			  case "firefox": capabilities.setBrowserName("firefox");break;
			  default: System.out.println("No matching browser"); return;				
			 }
				
			 driver=new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), capabilities);
			 		 
		  }
		  
		  //if execution environment is 'local' then we don't need to decide operating system & any browser 
		  
		  if(p.getProperty("execution_env").equalsIgnoreCase("local"))
		  {
			  switch(br.toLowerCase())
			  {
			  case "chrome" : driver=new ChromeDriver();break;
			  case "edge" : driver=new EdgeDriver(); break;
			  case "firefox" : driver=new FirefoxDriver(); break;
			  default : System.out.println("Invalid browser name...");return;
			  }
		  }
		  
//if we use Docker or VM's Standalone environment this above code for 'remote' & 'local' will not be change	but only difference is that creating single or multiple 'Grid' environment 
		
		
		  
		  
		  driver.manage().deleteAllCookies();//if we already store some cookies information so this command will delete all the cookies
		  driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
			
		  driver.get(p.getProperty("appURL"));//reading URL from properties file. for every time in email, password we have to specify [p.getProperty()]
		  driver.manage().window().maximize();
				
		}
		
		@AfterClass(groups = {"Sanity", "Regression", "Master"})
		public void tearDown()
		{
			driver.close();
		}
		
		
//same type of data like name, last name, email id we can't registered it again	
//so we need to prepare test data randomly	
//there are two kinds of test data
//1. before executing the test cases we will prepare our own data, static data that will never change
//2. dynamic data, that will create random data
//Excel only prefer when we will do data driven test cases so here we are not doing data driven test cases, it is single test case with single set of data
//so we will to generate randomly email ids & first name, last name also many times, for that we have to create user defined Java method	
				
			
		public String randomString()//when call this method it will generate random String
		{
			String generated_string=RandomStringUtils.randomAlphabetic(5);//how much characters I want then I am adding here
			return generated_string;
		}
			
			public String randomNumber()//when call this method it will generate random Number
		{
			
		//it is generated_number still it is in String format only
			String generated_number=RandomStringUtils.randomNumeric(10);//how much numbers I want then I am adding here
			return generated_number;
		}
				
			public String randomAlphaNumeric()//when call this methods it will generate random Number & String
		{
			String generated_string=RandomStringUtils.randomAlphabetic(5);//how much characters I want then I am adding here
			String generated_number=RandomStringUtils.randomNumeric(3);//how much numbers I want then I am adding here
			return (generated_string+"$#"+generated_number);//there is no built method to generate special characters so we can add any special characters here what I want
		}
			
//whenever Test method is got failed then we have to execute this method
//String tname - we are giving some name to the screenshot as a 'tname'
		
		public String captureScreen(String tname) throws IOException 
		{
			
//screenshot we have to save with time stamp because sometimes same test case if we run multiple times it capture multiple screenshot if we give same name the old screenshot will be replaced by new screenshot so always we have to save with timestamp
			String timeStamp = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
						
			TakesScreenshot takesScreenshot = (TakesScreenshot) driver;
			File sourceFile = takesScreenshot.getScreenshotAs(OutputType.FILE);
				
			String targetFilePath=System.getProperty("user.dir")+"\\screenshots\\" + tname + "_" + timeStamp + ".png";
			File targetFile=new File(targetFilePath);
				
			sourceFile.renameTo(targetFile);
			
//if we targetfile just copied that but we have not return that then screenshot is available in the screenshot folder but that is not part of the report so that's why we have to return					
			return targetFilePath;

			}
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
	
}
