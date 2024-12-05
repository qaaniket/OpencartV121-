package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class MyAccountPage extends BasePage {

	public MyAccountPage(WebDriver driver) 
	{
		super(driver);
	
	}

//here we locating one element currently	
	
	@FindBy(xpath = "//h2[text()='My Account']") WebElement msg_heading;	
//Or	
	//@FindBy(xpath = "//h2[normalize-space()='My Account']") WebElement msg_heading;
	
	@FindBy(xpath = "//a[@class='list-group-item'][normalize-space()='Logout']") WebElement lnkLogout; //added in Step 6 for data driven tests, check Pavan sir's document
	
	
//this will verify, if the page is exist it will be return  & if the page is not exist it will be return exception but will not including any validation here 
	
	public boolean isMyAccountExists()
	{
		try
		{
		return (msg_heading.isDisplayed()); //if it is display return true
		}
		catch(Exception e)//if it is not display it will throw exception
		{
			return false;//so it will display return false
		}
		
	}
	
	public void clickLogout()
	{
		lnkLogout.click();
	}
	
	
	
}
