package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

//this is just reusable component of all page object classes to achieve reusability & this is not page object class
public class BasePage {

//this BasePage contains only constructor
//this BasePage constructor extended into every page object class so this is parent of all page object classes
//why I make this as a parent because every constructor is same for every page object class so we are creating new BasePage class
	WebDriver driver;
	
	public BasePage(WebDriver driver)
	{
		this.driver=driver;
		PageFactory.initElements(driver, this);
		
	}
	
}
