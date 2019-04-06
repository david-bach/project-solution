package App.DepositSolutionsTask;

import static io.restassured.RestAssured.given;

import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.not;

import org.fluentlenium.core.FluentPage;
import org.fluentlenium.core.annotation.PageUrl;
import org.fluentlenium.core.domain.FluentWebElement;

import org.openqa.selenium.support.FindBy;

// Set URL of webpage
@PageUrl("http://85.93.17.135:9000/user/new")
public class AppTestPage extends FluentPage {
    
    static RestAssured_core racore;

    // Find and label specific elements for easier readability and accessibility
    @FindBy(id = "name")
    private FluentWebElement name;
    
    @FindBy(id = "user.name.error")
    private FluentWebElement nameError;

    @FindBy(id = "password")
    private FluentWebElement password;
    
    @FindBy(id = "user.password.error")
    private FluentWebElement passwordError;
    
    @FindBy(id = "confirmationPassword")
    private FluentWebElement passwordConfirm;
    
    @FindBy(id = "user.confirmationPassword.error")
    private FluentWebElement passwordConfirmError;
    
    @FindBy(id = "email")
    private FluentWebElement email;
    
    @FindBy(id = "user.email.error")
    private FluentWebElement emailError;
    
    @FindBy(xpath = "//button[@type='submit']")
    private FluentWebElement submitButton;
    
    // Effectively getters/setters
    public String readNamePhraseOut() {
    	return name.value();
    }
    
    public AppTestPage writeNamePhraseIn(String text) {
    	name.click().write(text);
    	return this;
    }
    
    public String readPassPhraseOut() {
    	return password.value();
    }
    
    public AppTestPage writePassPhraseIn(String text) {
    	password.click().write(text);
    	return this;
    }
    
    public String readPassConfirmPhraseOut() {
    	return passwordConfirm.value();
    }
    
    public AppTestPage writePassConfirmPhraseIn(String text) {
    	passwordConfirm.click().write(text);
    	return this;
    }
    
    public String readAddressPhraseOut() {
    	return email.value();
    }
    
    public AppTestPage writeAddressPhraseIn(String text) {
    	email.click().write(text);
    	return this;
    }

    // Submit the content of the form
    public AppTestPage submitUserForm() {
    	submitButton.submit();
        return this;
    }
    
    // Checks that the name field is correctly filled
    public void assertNameFieldIsFilledCorrectly() {
    	assert(nameError.text().equals("Required"));
    }
    
    // Checks that the name is unique
    public void assertNameDataIsUnique() {
    	assert(nameError.text().equals("Must be unique"));
    }
    
    // Checks that the Email field is correctly filled
    public void assertEmailFieldIsFilledCorrectly() {
    	assert(emailError.text().equals("Required"));
    }
    
    // Checks that the Email is unique
    public void assertEmailDataIsUnique() {
    	assert(emailError.text().equals("Must be unique"));
    }
    
    // Checks that the password field is correctly filled
    public void assertPasswordFieldIsFilledCorrectly() {
    	assert(passwordError.text().equals("Required"));
    }
    
    // Checks that the password confirmation field is correctly filled
    public void assertPasswordConfirmFieldIsFilledCorrectly() {
    	assert(passwordConfirmError.text().equals("passwords are not the same"));
    }
    
    // Checks that the password meets minimum length requirements
    public void assertPasswordHasCorrectLength() {
    	assert(passwordError.text().equals("Minimum size is 6"));
    }
    
    // Check the JSON to confirm that an email does not exist
    public void assertEmailDataHasNotBeenAdded(String s) {
    	given().when().get("user/all/json").then()
    	.body("email", not(hasItems(s + "@exampleaddress.com")));
    }
    
    // Check the JSON to confirm that a name does not exist
    public void assertNameDataHasNotBeenAdded(String s) {
    	given().when().get("user/all/json").then()
    	.body("name", not(hasItems(s)));
    }
    
    /* Check that a user has actually been added to the database.
     * Would expand this to check that other entries for specific user have been correctly filled as well
     */
    public void assertUserHasBeenAdded(String s) {
    	given().when().get("user/all/json").then()
    	.body("name", hasItems(s));
    }
    
    // Check that a specific item is being displayed based on string parameter
    public void assertThatFormIsVisible(String elementToUse) {
    	if(elementToUse.equals("name")) {
    		assert(name.getElement().isDisplayed());
    	} else if(elementToUse.equals("password")) {
    		assert(password.getElement().isDisplayed());
    	} else if(elementToUse.equals("passwordConfirm")) {
    		assert(passwordConfirm.getElement().isDisplayed());
    	} else if(elementToUse.equals("email")) {
    		assert(email.getElement().isDisplayed());
    	} else if(elementToUse.equals("submitButton")) {
    		assert(submitButton.getElement().isDisplayed());
    	} else {
    		System.out.println("WebElementUsed has not been defined. See AppTestPage.assertThatFormIsVisible");
    	}
    }
}