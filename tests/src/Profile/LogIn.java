package Profile;


import Reusables.ProfileReusables;
import Reusables.GeneralReusables;
import org.junit.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;

public class LogIn {
    private WebDriver driver;




    @BeforeClass
    public void setUp() {
        // Initialize the WebDriver
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        GeneralReusables.setUpToHomepage(driver);
        // Go to Log In page
        WebElement logInButton = driver.findElement(By.name("log in"));
        logInButton.click();

    }


    @Test
    public void preConditionTest() {
        String title = driver.getTitle();
        assertEquals(title, ProfileReusables.logInTitle);
    }

    @Test
    public void invalidEmail() throws Exception {
        ProfileReusables.enterValidUsername(driver);


        // Enter an invalid email
        WebElement email = driver.findElement(By.id("email"));
        email.sendKeys("");   // wrong format //TODO


        ProfileReusables.enterValidPassword(driver);


        WebElement submitButton = driver.findElement(By.id("submit-button"));
        submitButton.click();


        //    Verify that error message is displayed for authentication failure.
        String emailErrorText = driver.findElement(By.name("email-error-text")).getText();
        assertEquals(emailErrorText, ProfileReusables.invalidEmailError);
    }

    @Test
    public void notRegisteredEmail() throws Exception {
        ProfileReusables.enterValidUsername(driver);


        // Enter an not registered email
        WebElement email = driver.findElement(By.id("email"));
        email.clear();
        email.sendKeys(ProfileReusables.email1);



        WebElement submitButton = driver.findElement(By.id("submit-button"));
        submitButton.click();


        //    Verify that error message is displayed for authentication failure.
        String emailErrorText = driver.findElement(By.name("email-error-text")).getText();
        assertEquals(emailErrorText,ProfileReusables.notRegisteredEmailError);
    }

    @Test
    public void invalidPassword() throws Exception {


        ProfileReusables.enterValidEmail(driver);


        WebElement password  = driver.findElement(By.id("password"));
        password.clear();
        password.sendKeys("");  //wrong format //TODO


        WebElement submitButton = driver.findElement(By.id("submit-button"));
        submitButton.click();


        //    Verify that error message is displayed for authentication failure.
        String usernameErrorText = driver.findElement(By.name("password-error-text")).getText();
        assertEquals(usernameErrorText, ProfileReusables.invalidPasswordError); //TODO : complete the message
    }



    @Test
    public void validLogIn() throws Exception {


        ProfileReusables.enterValidPassword(driver);


        WebElement submitButton = driver.findElement(By.id("submit-button"));
        submitButton.click();

        //TODO : what does happen?
    }


    @AfterClass
    public void tearDown() {
        GeneralReusables.logout(driver);
    }







}
