package App.DepositSolutionsTask;

import org.junit.After;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Selenium_core {
	private HtmlUnitDriver driver;
	
	// Create a new HTMLUnitDriver when called
	public Selenium_core() {
		driver = new HtmlUnitDriver();
	}

    // Things to do after each test
    @After
    public void teardown() {
        if (driver != null) {
            driver.quit();
        }
    }
    
    // Returns the driver for use in other classes
    public WebDriver getDriver() {
    	return driver;
    }
    
    // Adds a new user to the JSON database
    public void createUserInDB(String name) {
    	driver.navigate().to("http://85.93.17.135:9000/user/new");
    	
    	// Wait for page to load, just in case something goes wrong and the previous line doesnt wait
    	new WebDriverWait(driver,10).until(ExpectedConditions.visibilityOfElementLocated(By.id("name")));
    	
    	// Find elements and store them in WebElements
    	WebElement username = driver.findElement(By.id("name"));
    	WebElement password = driver.findElement(By.id("password"));
    	WebElement password2 = driver.findElement(By.id("confirmationPassword"));
    	WebElement email = driver.findElement(By.id("email"));
    	WebElement submitbutton = driver.findElement(By.xpath("//button[@type='submit']"));
    	
    	// Interact with website
    	username.sendKeys(name);
    	password.sendKeys("123456");
    	password2.sendKeys("123456");
    	email.sendKeys(name + "@exampleaddress.com");
    	submitbutton.click();
    }
}