package pageObjects;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class AccountRegistrationPage extends BasePage //after extending 'BasePage' class then when click on 'AccountRegistrationPage' class automatically below constructor will be generate
{

//this is mandatory step in every page object class	
//& whenever we get/accommodating new test cases so definitely we have to update existing object classes so we will keep on adding more number of elements & action methods
	
	public AccountRegistrationPage(WebDriver driver) 
	{
		super(driver);
		
	}
	
//we have to follow naming conventions
//txt - text box
//chk - check box
//btn - button
	
	@FindBy(xpath = "//input[@id='input-firstname']") WebElement txt_firstname;
	
	@FindBy(xpath = "//input[@id='input-lastname']") WebElement txt_lastname;
	
	@FindBy(xpath = "//input[@id='input-email']") WebElement txt_email;
	
	@FindBy(xpath = "//input[@id='input-telephone']") WebElement txt_telephone;
	
	@FindBy(xpath = "//input[@id='input-password']") WebElement txt_password;
	
	@FindBy(xpath = "//input[@id='input-confirm']") WebElement txt_confirm_password;
	
	@FindBy(xpath = "//input[@name='agree']") WebElement chk_policy;
	
	@FindBy(xpath = "//input[@value='Continue']") WebElement btn_continue;
	
	@FindBy(xpath = "//h1[normalize-space()='Your Account Has Been Created!']") WebElement msg_confirmation;
	
	
	public void setFirstName(String fname)
	{
		txt_firstname.sendKeys(fname);
	}
	
	public void setLastName(String lname)
	{
		txt_lastname.sendKeys(lname);
	}
	
	public void setEmail(String email)
	{
		txt_email.sendKeys(email);
	}
	
	public void setTelephone(String tel)//eventhough telephone no. we have to pass in String format
	{
		txt_telephone.sendKeys(tel);
	}
	
	public void setPassword(String pwd)
	{
		txt_password.sendKeys(pwd);
	}
	
	public void setConfirmPassword(String pwd)//password & confirm password always same so passing the same parameter
	{
		txt_confirm_password.sendKeys(pwd);
	}
	
	public void setPrivacyPolicy()
	{
		chk_policy.click();
	}
	
	public void clickContinue()
	{
		//sol1		
		btn_continue.click();//if 'click' method is not working then we can try alternative options
		
//sometimes click method is not work, it will throw 'elementClickInterceptedException' or 'elementInteractableException' so we can handle below alternative options		
		
		//sol2
		//btn_continue.submit();
		
		//sol3
		//Actions act=new Actions(driver);
		//act.moveToElement(btn_continue).click().perform();
				
		//sol4
		//JavascriptExecutor js=(JavascriptExecutor)driver;
		//js.executeAsyncScript("arguments[0].click()", btn_continue);
		
		//sol5
		//btn_continue.sendKeys(Keys.RETURN);
		
		//sol6
		//WebDriverWait mywait=new WebDriverWait(driver, Duration.ofSeconds(10));
		//mywait.until(ExpectedConditions.elementToBeClickable(btn_continue)).click();
		
		
	}
	
//validations we will do inside the test cases not inside the page object classes but we write for confirmation message we are creating one method for confirmation message
	
	public String getConfirmationMsg()
	{
		try {
		return(msg_confirmation.getText());//we are capturing the text value & return it, it will not do any validation

//if registration is fail & confirmation msg is not displayed then it will throw exception msg will be return so ultimately return type is also String
	}catch(Exception e) {
		return(e.getMessage());		
	}
	
	

	
	}
	
}
