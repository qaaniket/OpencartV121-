package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.AccountRegistrationPage;
import pageObjects.HomePage;
import testBase.BaseClass;

//we have to give test case name this way only
//one single class for one test case so we will create multiple classes for multiple test cases
//make everything like methods, constructor as a public so that we are able to access throughout the project

//after seperating common things into base class then Test class contains only test methods

public class TC001_AccountRegistrationTest extends BaseClass //this BaseClass extends from another package so I have to 'import' it
{


	
	@Test(groups = {"Regression", "Master"})
	public void VerifyAccountRegistration()
	{
		logger.info("**** Starting TC001_AccountRegistrationTest ****");//this msg will be display in log file
		
		try
		{
//test case present in different package & page object classes present in different package so we have import 	
		HomePage hp=new HomePage(driver);//this 'driver' coming from BaseClass
		
		hp.ClickMyAccount();
		logger.info("Clicked on MyAccount link ");
		
		hp.ClickRegister();
		logger.info("Clicked on Register link");
		
		AccountRegistrationPage regpage=new AccountRegistrationPage(driver);
		
		logger.info("Providing customer details");
		regpage.setFirstName(randomString().toUpperCase());//here we are calling below randomString() method to generating random FirstName & convert it into uppercase
		regpage.setLastName(randomString().toUpperCase());//here we are calling below randomString() method to generating random LastName & convert it into uppercase
		regpage.setEmail(randomString()+"@gmail.com");//here we are calling below randomString() method to generating random email id's & this should be in 'gmail.com' format
		regpage.setTelephone(randomNumber());
		
//we can call method only time if we call two times it will generate two different Strings so they will not match, so we have to call one time 	& storing the method in another variable then that variable we passing password & confirm password			
		String password=randomAlphaNumeric();
		regpage.setPassword(password);
		regpage.setConfirmPassword(password);
		
		regpage.setPrivacyPolicy();
		regpage.clickContinue();
		
		logger.info("Validating expected message...");
		String confmsg=regpage.getConfirmationMsg();
		
		//Exception is not comes then 'else' block will be execute
		if(confmsg.equals("Your Account Has Been Created!"))//if I want to fail the test case so I do any change like add one more ! mark
		{
			Assert.assertTrue(true);
		}
		else
		{
			logger.error("Test failed..");
			logger.debug("Debug logs..");//if I want to debug logs then I have to change log level in log4j2.xml,   <Root level="INFO"> | <Root level="Debug"> in this way
			Assert.assertTrue(false);
		}
			
		//Assert.assertEquals(confmsg, "Your Account Has Been Created!");//this assertion is no needed because we already compare above & put there assertion
		}
		catch(Exception e)//if any exception comes, catch block will execute & fail the test
		{
			
			Assert.fail();
		}
		
		logger.info("**** Finished TC001_AccountRegistrationTest ****");
	


	
	
	
	
	
	
	
	
		}
	
	
	
	
	
	
}
	

