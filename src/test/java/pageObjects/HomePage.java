package pageObjects;

//whichever pageObjects we will create that Page object class should extends from BasePage
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

//when extends BasePage whatever constructor, variable is created which is also belongs ti HomePage
public class HomePage extends BasePage {

//without creating this constructor we can't invoke the parent class constructor so this is inheritance
//here we invoked the immediate parent class constructor by using 'super' keyword
//this is mandatory step in every page object class	
	public HomePage(WebDriver driver) 
	{
		
//instead of doing PageFactory.initElements() directly pass same driver to the parent class constructor
		super(driver);
		
	}

	@FindBy(xpath = "//span[normalize-space()='My Account']") WebElement InMyaccount;
	
	@FindBy(xpath = "//a[normalize-space()='Register']") WebElement InRegister;  
	
	//@FindBy(xpath="//a[normalize-space()='Login']") WebElement linkLogin; //Login link added in steps
	@FindBy(linkText = "Login") WebElement linkLogin; //Login link added in steps
	
	public void ClickMyAccount()
	{
		InMyaccount.click();
	}
		
	public void ClickRegister()
	{
		InRegister.click();
	}
	
	public void ClickLogin()
	{
		linkLogin.click();
	}
	
	
}
