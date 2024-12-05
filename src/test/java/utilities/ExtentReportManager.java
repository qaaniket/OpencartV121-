package utilities;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
//import java.net.URL;
import java.net.URL;

//Extent report 5.x...//version

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.ImageHtmlEmail;
import org.apache.commons.mail.resolver.DataSourceUrlResolver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import testBase.BaseClass;

//no need to remember all these things, it's very difficult remember
//if we not understand all these methods no problem but at least we need to understand why we return/write these below statements
//this is Utility file even if I am not understood there is no problem at all because we just copy & paste this code in our project & I can use it 
public class ExtentReportManager implements ITestListener {
	public ExtentSparkReporter sparkReporter;
	public ExtentReports extent;
	public ExtentTest test;

	String repName;

	public void onStart(ITestContext testContext) {

		// time stamp
//instead of writing these 3 steps we can put in single step
		/*SimpleDateFormat df=new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss");
		Date dt=new Date();
		String currentdatetimestamp=df.format(dt);
		*/
		
		// time stamp
//this single step we can put below
		String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
		
		repName = "Test-Report-" + timeStamp + ".html";
		sparkReporter = new ExtentSparkReporter(".\\reports\\" + repName);// specify location of the report

		sparkReporter.config().setDocumentTitle("opencart Automation Report"); // Title of report
		sparkReporter.config().setReportName("opencart Functional Testing"); // name of the report
		sparkReporter.config().setTheme(Theme.DARK);
		
//these things are not related to properties file
		extent = new ExtentReports();
		extent.attachReporter(sparkReporter);
		extent.setSystemInfo("Application", "opencart");
		extent.setSystemInfo("Module", "Admin");
		extent.setSystemInfo("Sub Module", "Customers");
		extent.setSystemInfo("User Name", System.getProperty("user.name"));
		extent.setSystemInfo("Environemnt", "QA");

//these value captured dynamically from xml file
		String os = testContext.getCurrentXmlTest().getParameter("os");
		extent.setSystemInfo("Operating System", os);

//these value captured dynamically from xml file
//getCurrentXmlTest() method will return the xml file from whichever xml file we have started run that xml file will be return
		String browser = testContext.getCurrentXmlTest().getParameter("browser");
		extent.setSystemInfo("Browser", browser);
	
//getIncludedGroups() this method will capture the groups which we have specified IncludedGroups
		List<String> includedGroups = testContext.getCurrentXmlTest().getIncludedGroups();
		if(!includedGroups.isEmpty()) 
		{
		extent.setSystemInfo("Groups", includedGroups.toString());
		}
	}

//When onTestSuccess() method is triggerd the all 'result' will capture from Test methods
	public void onTestSuccess(ITestResult result) {
	
		test = extent.createTest(result.getTestClass().getName());
		test.assignCategory(result.getMethod().getGroups()); // to display category/groups in report in the test case wise
		test.log(Status.PASS,result.getName()+" got successfully executed");
		
	}

//when Test method is failed then onTestFailure() method will triggered means my Test method is failed then we have to execute captureScreen() method 
	public void onTestFailure(ITestResult result) {
		test = extent.createTest(result.getTestClass().getName());
		test.assignCategory(result.getMethod().getGroups());
		
		test.log(Status.FAIL,result.getName()+" got failed");
		test.log(Status.INFO, result.getThrowable().getMessage());
	
//here we attaching the screenshot where ever failure is happen
//1st we have to capture the screenshot & once we have capture the screenshot then only we can attach the same screenshot to the report but before executing below statement screenshot will be available so we have to capture the screenshot in BaseClass 
		try {
			
//we have to access captureScreen() method from BaseClass that's why we created object for BaseClass & we not storing in another variable, we directly used & from this object called captureScreen() method & it will return imgpath & with this imgpath attaching in addScreenCaptureFromPath
			String imgPath = new BaseClass().captureScreen(result.getName());//captureScreen() method is common for all the test cases so we added in BaseClass
			test.addScreenCaptureFromPath(imgPath);

//if the screenshot is not properly taken or screenshot is not available that times fileNotFoundException we will get so we will put in the catch block
		} catch (IOException e1) 
		{
			e1.printStackTrace();
		}
	}

	public void onTestSkipped(ITestResult result) {
		test = extent.createTest(result.getTestClass().getName());
		test.assignCategory(result.getMethod().getGroups());
		test.log(Status.SKIP, result.getName()+" got skipped");
		test.log(Status.INFO, result.getThrowable().getMessage());
	}

	public void onFinish(ITestContext testContext) {
		
		extent.flush();
	
//normally where the report the will be generate under 'reports' folder & if I want to open the report, we need to go the reports folder then we need to right click on the reports file & refresh it & then again right click & go the 'open with' option then select 'System Editor' & report will open in the browser so we have to do manually
//so instead of doing this suppose as soon as execute my test cases, report is generated immediately it will automatically open means I don't want to open report manually, I want to report automatically for that we add below piece of code
		String pathOfExtentReport = System.getProperty("user.dir")+"\\reports\\"+repName;
		File extentReport = new File(pathOfExtentReport);
		
		try {
			Desktop.getDesktop().browse(extentReport.toURI());//it will automatically open the report on browser
		} catch (IOException e) {
			e.printStackTrace();
		}

//as soon as automation	tests are completed execution immediately if I want to send the report then I can enable the below piece of code
//this particular piece of code will work for only Gmail
		/*  try {
			  URL url = new  URL("file:///"+System.getProperty("user.dir")+"\\reports\\"+repName);
		  
		  // Create the email message 
		  ImageHtmlEmail email = new ImageHtmlEmail();
		  email.setDataSourceResolver(new DataSourceUrlResolver(url));
		  email.setHostName("smtp.googlemail.com"); 
		  email.setSmtpPort(465);
		  email.setAuthenticator(new DefaultAuthenticator("pavanoltraining@gmail.com","password")); 
		  email.setSSLOnConnect(true);
		  email.setFrom("pavanoltraining@gmail.com"); //Sender
		  email.setSubject("Test Results");
		  email.setMsg("Please find Attached Report....");
		  email.addTo("pavankumar.busyqa@gmail.com"); //Receiver in that single email or combine email id's as distribution list(DL)
		  email.attach(url, "extent report", "please check report..."); 
		  email.send(); // send the email 
		  }
		  catch(Exception e) //if any problem at the time of sending or the file is not properly attached or not properly sent the catch block will be executed
		  { 
			  e.printStackTrace(); 
			  }
		 */
		 
	}

}
