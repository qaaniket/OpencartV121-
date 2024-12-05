package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.HomePage;
import pageObjects.LoginPage;
import pageObjects.MyAccountPage;
import testBase.BaseClass;

public class TC002_LoginTest extends BaseClass {
	
	@Test(groups={"Sanity", "Master"})//adding Sanity in grouping test cases, whichever group I want to add based upon our requirement
	public void verifyLogin()
	{
		logger.info("*** Starting TC002_LoginTest ***");//here by default 3rd info method we have to use
		
		try
		{
		//HomePage
		HomePage hp=new HomePage(driver);//this 'driver' coming from BaseClass
		
		hp.ClickMyAccount();
		hp.ClickLogin();
		
		//Login
		LoginPage lp=new LoginPage(driver);
		
//we should not hard code any values in the test cases either we have to maintain in properties file or Excel file
		
		lp.setEmail(p.getProperty("email"));//this test case we have to provide valid data
		lp.setPassword(p.getProperty("password"));//this test case we have to provide valid data
		lp.clickLogin();
		
		//MyAccount
		MyAccountPage macc=new MyAccountPage(driver);
		
		boolean target_page=macc.isMyAccountExists();
		
		Assert.assertTrue(target_page);
//Or
		//Assert.assertEquals(target_page, true, "Login failed");//3 parameters it will take, 1st two parameters it will compare if its pass no issue & if it is fail then 3rd parameters msg will display
		}
		catch(Exception e)//any exception comes test case 
		{
			Assert.fail();
		}
		logger.info("*** Finished TC002_LoginTest ***");
		
		
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
