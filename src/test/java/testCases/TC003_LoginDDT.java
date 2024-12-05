package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.HomePage;
import pageObjects.LoginPage;
import pageObjects.MyAccountPage;
import testBase.BaseClass;
import utilities.DataProviders;



public class TC003_LoginDDT extends BaseClass{

//if we want refer this DataProvider in this test case, we need to specify dataProvider method name along with one more parameter dataProviderClass
	@Test(dataProvider="LoginData",dataProviderClass=DataProviders.class, groups = "Datadriven")//DataProviders is the class from another package so we have import & .class is explicitly specify so this extra parameter we need to pass if DataProvider is created another package
	public void verifyLoginDDT(String email, String pwd, String exp) throws InterruptedException
	{
		logger.info("**** Starting TC003_LoginDDT ****");
				
		try
		{
		//HomePage
		HomePage hp=new HomePage(driver);//this 'driver' coming from BaseClass
		
		hp.ClickMyAccount();
		hp.ClickLogin();
		
		//Login
		LoginPage lp=new LoginPage(driver);
		
//we should not hard code any values in the test cases either we have to maintain in properties file or Excel file
		
		lp.setEmail(email);//this email get from DataProvider
		lp.setPassword(pwd);//this password get from DataProvider
		lp.clickLogin();
		
		//MyAccount
//MyAccount true means login is successful
		MyAccountPage macc=new MyAccountPage(driver);
		
		boolean target_page=macc.isMyAccountExists();
		
//valid data > login successful - test passed - log out
//valid data > login unsuccessful - test failed then still remains same page will be show 

//in-valid data > login successful - test failed - log out
//in-valid data > login unsuccessful - test passed then still remains same page will be show 

//why equalIgnoreCase because sometimes we pass lower case or upper case
		if(exp.equalsIgnoreCase("Valid"))//if data is valid 
		{
			if(target_page==true)//login successful
			{
				macc.clickLogout();//then clicked on logout, here if we write clickLogout() method below to assertTrue() method then clickLogout() statement will not be executed, thats why we will write this way so this is last step
				Assert.assertTrue(true);//1st test is passed
				
			}
			else//login unsuccessful
			{
				Assert.assertTrue(false);//then test is failed then still remains same page will be show, here this is last step  

			}
		}
		
		if(exp.equalsIgnoreCase("Invalid"))//if data is in-valid
		{
			if(target_page==true)//login successful
			{
				macc.clickLogout();//then clicked on logout, here if we write clickLogout() method below to assertTrue() method then clickLogout() statement will not be executed, thats why we will write this way so this is last step
				Assert.assertTrue(false);//1st test is failed
				
			}
			else//login unsuccessful
			{
				Assert.assertTrue(true);//then test is passed then still remains same page will be show, here this is last step   

			}
			
		}
		
		}catch(Exception e)
		{
			Assert.fail();
		}
		
		Thread.sleep(3000);
		logger.info("**** Finished TC003_LoginDDT ****");
		
		
		
		
		
		
		
		
	}
	
	
	
	
	
	
	
	
	
}
