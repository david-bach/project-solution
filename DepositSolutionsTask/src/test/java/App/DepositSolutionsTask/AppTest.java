package App.DepositSolutionsTask;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.fluentlenium.adapter.junit.FluentTest;
import org.fluentlenium.core.annotation.Page;

import org.junit.Test;

import App.DepositSolutionsTask.RestAssured_core;

public class AppTest extends FluentTest {
	
	// Code to stop Console being spammed by warnings of HTMLUnit that a value in a css is obsolete
	private static final Logger[] PINNED_LOGGERS;
    static {
        System.setProperty("org.apache.commons.logging.simplelog.defaultlog", "fatal");
        PINNED_LOGGERS = new Logger[]{
            Logger.getLogger("com.gargoylesoftware.htmlunit"),
            Logger.getLogger("org.apache.http")
        };

        for (Logger l : PINNED_LOGGERS) {
            l.setLevel(Level.OFF);
        }
    }
	
	// Declares what specific page this is referring to (in this case its AppTaskPage)
    @Page
    AppTestPage page;
    
    // Create a new RestAssured core class to use extended and customised functionality of RestAssured
    static RestAssured_core racore;
    
    static Selenium_core slcore;
    
    // Ensure the driver used is the HTMLUnit Driver, as the default is Firefox and set the base URI and path for the RestAssured testcases
    public AppTest() {
    	setWebDriver("htmlunit");
    	racore = new RestAssured_core();
    	slcore = new Selenium_core();
    }
    
    // -- Tests for intended form functionality below --
	
    // Checks that a new user can be created
	@Test
	public void canCreateNewUser() {
		String randomName = generateName();
		
		slcore.createUserInDB(randomName);
		
		goTo(page)
		.assertUserHasBeenAdded(randomName);
	}
    
	// Checks that the warning label appears when you try to submit without a name
    @Test
    public void cannotSubmitWithoutNameCheckLabel() {
    	String randomName = generateName();
    	
    	goTo(page)
		.writeNamePhraseIn("")
		.writePassPhraseIn("123456")
		.writePassConfirmPhraseIn("123456")
		.writeAddressPhraseIn(randomName + "@exampleaddress.com")
		.submitUserForm()
		.assertNameFieldIsFilledCorrectly();
    }
    
    // Checks the JSON to ensure that an entry doesnt get created anyways even if name is not supplied
    @Test
    public void cannotSubmitWithoutNameCheckJSON() {
    	String randomName = generateName();
    	
    	goTo(page)
		.writeNamePhraseIn("")
		.writePassPhraseIn("123456")
		.writePassConfirmPhraseIn("123456")
		.writeAddressPhraseIn(randomName + "@exampleaddress.com")
		.submitUserForm()
		.assertEmailDataHasNotBeenAdded(randomName);
    }
    
    // Checks that the warning label appears when you try to submit without a password
    @Test
    public void cannotSubmitWithoutPasswordCheckLabel() {
    	String randomName = generateName();
    	
    	goTo(page)
		.writeNamePhraseIn(randomName)
		.writePassPhraseIn("")
		.writePassConfirmPhraseIn("123456")
		.writeAddressPhraseIn(randomName + "@exampleaddress.com")
		.submitUserForm()
		.assertPasswordFieldIsFilledCorrectly();
    }
    
    // Checks the JSON to ensure that an entry doesnt get created anyways even if password is not supplied
    @Test
    public void cannotSubmitWithoutPasswordCheckJSON() {
	  String randomName = generateName();
	  
    	goTo(page)
    	.writeNamePhraseIn(randomName)
		.writePassPhraseIn("")
		.writePassConfirmPhraseIn("123456")
		.writeAddressPhraseIn(randomName + "@exampleaddress.com")
		.submitUserForm()
		.assertEmailDataHasNotBeenAdded(randomName);
    }
    
    // Checks that the warning label appears when you try to submit without an email
    @Test
    public void cannotSubmitWithoutEmailCheckLabel() {
    	String randomName = generateName();
    	
    	goTo(page)
		.writeNamePhraseIn(randomName)
		.writePassPhraseIn("123456")
		.writePassConfirmPhraseIn("123456")
		.writeAddressPhraseIn("")
		.submitUserForm()
		.assertEmailFieldIsFilledCorrectly();
    }
    
    // Checks the JSON to ensure that an entry doesnt get created anyways even if email is not supplied
    @Test
    public void cannotSubmitWithoutEmailCheckJSON() {
    	String randomName = generateName();
    	
    	goTo(page)
		.writeNamePhraseIn(randomName)
		.writePassPhraseIn("123456")
		.writePassConfirmPhraseIn("123456")
		.writeAddressPhraseIn("")
		.submitUserForm()
		.assertNameDataHasNotBeenAdded(randomName);
    }
    
    // Checks that the warning label appears when you try to submit without password and password confirmation matching
    @Test
    public void cannotSubmitWithoutMatchingPasswordsCheckLabel() {
    	String randomName = generateName();
    	
    	goTo(page)
		.writeNamePhraseIn(randomName)
		.writePassPhraseIn("123456")
		.writePassConfirmPhraseIn("654321")
		.writeAddressPhraseIn(randomName + "@exampleaddress.com")
		.submitUserForm()
		.assertPasswordConfirmFieldIsFilledCorrectly();
    }
    
    // Checks the JSON to ensure that an entry doesnt get created anyways even if password and password confirmation don't match
    @Test
    public void cannotSubmitWithoutMatchingPasswordsCheckJSON() {
    	String randomName = generateName();
    	
    	goTo(page)
		.writeNamePhraseIn(randomName)
		.writePassPhraseIn("123456")
		.writePassConfirmPhraseIn("654321")
		.writeAddressPhraseIn(randomName + "@exampleaddress.com")
		.submitUserForm()
		.assertEmailDataHasNotBeenAdded(randomName);
    }
    
    // Checks that the warning label appears when you try to submit without password meeting minimum length requirements
    @Test
    public void cannotSubmitPasswordWithTooFewLettersCheckLabel() {
    	String randomName = generateName();
    	
    	goTo(page)
		.writeNamePhraseIn(randomName)
		.writePassPhraseIn("12345")
		.writePassConfirmPhraseIn("12345")
		.writeAddressPhraseIn(randomName + "@exampleaddress.com")
		.submitUserForm()
		.assertPasswordHasCorrectLength();
    }
    
    // Checks the JSON to ensure that an entry doesnt get created anyways even if the password does not meet minimum length requirements
    @Test
    public void cannotSubmitPasswordWithTooFewLettersCheckJSON() {
    	String randomName = generateName();
    	
    	goTo(page)
		.writeNamePhraseIn(randomName)
		.writePassPhraseIn("12345")
		.writePassConfirmPhraseIn("12345")
		.writeAddressPhraseIn(randomName + "@exampleaddress.com")
		.submitUserForm()
		.assertEmailDataHasNotBeenAdded(randomName);
    }
    
    // Checks that the warning label appears when you try to submit a name that already exists in the database
    @Test
    public void cannotSubmitDuplicateNameCheckLabel() {
    	String randomName = generateName();
    	
    	goTo(page)
		.writeNamePhraseIn("user1")
		.writePassPhraseIn("123456")
		.writePassConfirmPhraseIn("123456")
		.writeAddressPhraseIn(randomName + "@exampleaddress.com")
		.submitUserForm()
		.assertNameDataIsUnique();
    }
    
    // Checks the JSON to ensure that an entry doesnt get created anyways even if the username already exists
    @Test
    public void cannotSubmitDuplicateNameCheckJSON() {
    	String randomName = generateName();
    	
    	goTo(page)
		.writeNamePhraseIn("user1")
		.writePassPhraseIn("123456")
		.writePassConfirmPhraseIn("123456")
		.writeAddressPhraseIn(randomName + "@exampleaddress.com")
		.submitUserForm()
		.assertEmailDataHasNotBeenAdded(randomName);
    }
    
    // Checks that the warning label appears when you try to submit an email that already exists in the database
    @Test
    public void cannotSubmitDuplicateAddressCheckLabel() {
    	String randomName = generateName();
    	
    	goTo(page)
		.writeNamePhraseIn(randomName)
		.writePassPhraseIn("123456")
		.writePassConfirmPhraseIn("123456")
		.writeAddressPhraseIn("user1@exampleaddress.com")
		.submitUserForm()
		.assertEmailDataIsUnique();
    }
    
    // Checks the JSON to ensure that an entry doesnt get created anyways even if the email already exists
    @Test
    public void cannotSubmitDuplicateAddressCheckJSON() {
    	String randomName = generateName();
    	
    	goTo(page)
		.writeNamePhraseIn(randomName)
		.writePassPhraseIn("123456")
		.writePassConfirmPhraseIn("123456")
		.writeAddressPhraseIn("user1@exampleaddress.com")
		.submitUserForm()
		.assertNameDataHasNotBeenAdded(randomName);
    }
    
    // -- Tests for basic webelement properties below --
    
    // General check to see if the name form is visible
    @Test
    public void nameFormIsVisible() {
    	goTo(page)
		.assertThatFormIsVisible("name");
    }
    
    // General check to see if the password form is visible
    @Test
    public void passwordFormIsVisible() {
    	goTo(page)
		.assertThatFormIsVisible("password");
    }
    
    // General check to see if the password confirmation form is visible
    @Test
    public void passwordConfirmFormIsVisible() {
    	goTo(page)
		.assertThatFormIsVisible("passwordConfirm");
    }
    
    // General check to see if the email form is visible
    @Test
    public void addressFormIsVisible() {
    	goTo(page)
		.assertThatFormIsVisible("email");
    }
    
    // General check to see if the submit button is visible
    @Test
    public void submitButtonIsVisible() {
    	goTo(page)
		.assertThatFormIsVisible("submitButton");
    }
    
    // Utility Methods
    
    // Generates and returns the current system time in milliseconds as a String so it can be used as a unique name for users
    public String generateName() {
    	long temp = System.currentTimeMillis();
		String randomName = temp + "";
		
		return randomName;
    }
}