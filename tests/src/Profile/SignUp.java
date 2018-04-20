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

public class SignUp {
    private WebDriver driver;




    @BeforeClass
    public void setUp() {
        // Initialize the WebDriver
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        //for checking already registered info
        ProfileReusables.signUpUser1(driver);
        GeneralReusables.setUpToHomepage(driver);
        // Go to Sign up page
        WebElement signUpButton = driver.findElement(By.name("sign-up"));
        signUpButton.click();

    }


    @Test
    public void preConditionTest() {
        String title = driver.getTitle();
        assertEquals(title, ProfileReusables.SignUpTitle);
    }

    @Test
    public void invalidEmail() throws Exception {


        ProfileReusables.enterValidUsername(driver);

        // Enter an invalid email
        WebElement email = driver.findElement(By.id("email"));
        email.sendKeys("email.com");   //wrong format

        ProfileReusables.enterValidPhoneNumber(driver);
        ProfileReusables.enterValidShomareHesab(driver);
        ProfileReusables.enterValidPassword(driver);


        WebElement submitButton = driver.findElement(By.id("submit-button"));
        submitButton.click();

        //  Verify that error message is displayed for authentication failure.
        String emailErrorText = driver.findElement(By.name("email-error-text")).getText();
        assertEquals(emailErrorText, ProfileReusables.invalidEmailError);
    }

    @Test
    public void registeredEmail() throws Exception {

        ProfileReusables.enterValidUsername(driver);


        // Enter an already registered email
        WebElement email = driver.findElement(By.id("email"));
        email.sendKeys(ProfileReusables.email1);   //already registered

        ProfileReusables.enterValidPhoneNumber(driver);
        ProfileReusables.enterValidShomareHesab(driver);
        ProfileReusables.enterValidPassword(driver);

        WebElement submitButton = driver.findElement(By.id("submit-button"));
        submitButton.click();

        //  Verify that error message is displayed for authentication failure.
        String emailErrorText = driver.findElement(By.name("email-error-text")).getText();
        assertEquals(emailErrorText, ProfileReusables.alreadyRegisteredEmailError);
    }

    @Test
    public void invalidUsername() throws Exception {

        //invalid username
        WebElement username = driver.findElement(By.id("username"));
        username.clear();
        username.sendKeys("");  //wrong format //TODO


        ProfileReusables.enterValidEmail(driver);
        ProfileReusables.enterValidPhoneNumber(driver);
        ProfileReusables.enterValidShomareHesab(driver);
        ProfileReusables.enterValidPassword(driver);



        WebElement submitButton = driver.findElement(By.id("submit-button"));
        submitButton.click();


        //    Verify that error message is displayed for authentication failure.
        String usernameErrorText = driver.findElement(By.name("username-error-text")).getText();
        assertEquals(usernameErrorText, ProfileReusables.invalidUsernameError);
    }

    @Test
    public void registeredUsername() throws Exception {



        // Enter an already registered username
        WebElement email = driver.findElement(By.id("username"));
        email.sendKeys(ProfileReusables.username1);

        ProfileReusables.enterValidEmail(driver);
        ProfileReusables.enterValidPhoneNumber(driver);
        ProfileReusables.enterValidShomareHesab(driver);
        ProfileReusables.enterValidPassword(driver);

        WebElement submitButton = driver.findElement(By.id("submit-button"));
        submitButton.click();

        //  Verify that error message is displayed for authentication failure.
        String emailErrorText = driver.findElement(By.name("username-error-text")).getText();
        assertEquals(emailErrorText, ProfileReusables.alreadyRegisteredUsernameError);
    }

    @Test
    public void invalidPhoneNumber() throws Exception {


        ProfileReusables.enterValidUsername(driver);
        ProfileReusables.enterValidEmail(driver);

        WebElement phoneNumber = driver.findElement(By.id("phone-number"));
        phoneNumber.clear();
        phoneNumber.sendKeys("");  // wrong format //TODO


        ProfileReusables.enterValidShomareHesab(driver);
        ProfileReusables.enterValidPassword(driver);


        WebElement submitButton = driver.findElement(By.id("submit-button"));
        submitButton.click();


        //    Verify that error message is displayed for authentication failure.
        String usernameErrorText = driver.findElement(By.name("phone-number-error-text")).getText();
        assertEquals(usernameErrorText, ""); //TODO : complete the message
    }


    @Test
    public void invalidShomareHesab() throws Exception {


        ProfileReusables.enterValidUsername(driver);
        ProfileReusables.enterValidEmail(driver);
        ProfileReusables.enterValidPhoneNumber(driver);


        WebElement shomareHesab = driver.findElement(By.id("shomare-hesab"));
        shomareHesab.clear();
        shomareHesab.sendKeys("");  //wrong format //TODO

        ProfileReusables.enterValidPassword(driver);

        WebElement submitButton = driver.findElement(By.id("submit-button"));
        submitButton.click();


        //    Verify that error message is displayed for authentication failure.
        String usernameErrorText = driver.findElement(By.name("shomare-hesab-error-text")).getText();
        assertEquals(usernameErrorText, ProfileReusables.invalidPhoneNumberError);
    }

    @Test
    public void registeredShomareHesab() throws Exception {

        ProfileReusables.enterValidUsername(driver);
        ProfileReusables.enterValidEmail(driver);
        ProfileReusables.enterValidPhoneNumber(driver);


        WebElement shomareHesab = driver.findElement(By.id("shomare-hesab"));
        shomareHesab.clear();
        shomareHesab.sendKeys(ProfileReusables.ShomareHesab1);

        ProfileReusables.enterValidPassword(driver);


        WebElement submitButton = driver.findElement(By.id("submit-button"));
        submitButton.click();


        //    Verify that error message is displayed for authentication failure.
        String usernameErrorText = driver.findElement(By.name("shomare-hesab-error-text")).getText();
        assertEquals(usernameErrorText, ProfileReusables.alreadyRegisteredShomareHesabError);
    }


    @Test
    public void invalidPassword() throws Exception {

        ProfileReusables.enterValidUsername(driver);
        ProfileReusables.enterValidEmail(driver);
        ProfileReusables.enterValidPhoneNumber(driver);
        ProfileReusables.enterValidShomareHesab(driver);

        WebElement password = driver.findElement(By.id("password"));
        password.clear();
        password.sendKeys("");  //wrong format //TODO




        WebElement submitButton = driver.findElement(By.id("submit-button"));
        submitButton.click();


        //    Verify that error message is displayed for authentication failure.
        String usernameErrorText = driver.findElement(By.name("password-error-text")).getText();
        assertEquals(usernameErrorText, ProfileReusables.invalidPasswordError); //TODO : complete the message
    }


    @Test
    public void validSignUp() throws Exception {
        ProfileReusables.enterValidUsername(driver);
        ProfileReusables.enterValidEmail(driver);
        ProfileReusables.enterValidPhoneNumber(driver);
        ProfileReusables.enterValidShomareHesab(driver);
        ProfileReusables.enterValidPassword(driver);


        WebElement submitButton = driver.findElement(By.id("submit-button"));
        submitButton.click();

        //TODO : what does happen?


    }


    @AfterClass
    public void tearDown() {
        // TODO : dummy account should be deleted???

    }







}
