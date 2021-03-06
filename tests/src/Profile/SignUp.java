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
    private static WebDriver driver ;

    public static void goToSignupPage(){
        GeneralReusables.setUpToHomepage(driver);
        // Go to Sign up page
        String linkToOpen= driver.findElement(By.name("sign up")).getAttribute("href");
        driver.get(linkToOpen);
    }

    String successTitle = "" ; //TODO:




    @BeforeClass
    public static void setUp() {
        // Initialize the WebDriver
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        //for checking already registered info
        ProfileReusables.signUpUser1(driver);
        goToSignupPage();


    }


   /* @Test
    public void preConditionTest() {
        // Initialize the WebDriver
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(100, TimeUnit.SECONDS);
        //for checking already registered info
        ProfileReusables.signUpUser1(driver);
        goToSignupPage();
        String title = driver.getTitle();
        //assertEquals(title, ProfileReusables.SignUpTitle);
    }*/


    @Test
    public void invalidFirstName(){

        goToSignupPage();

        WebElement firstName = driver.findElement(By.name("first name"));
        firstName.sendKeys(ProfileReusables.invalidName);

        ProfileReusables.enterValidFamilyName(driver);
        ProfileReusables.enterValidUsername(driver);
        ProfileReusables.enterValidEmail(driver);
        ProfileReusables.enterValidPhoneNumber(driver);
        ProfileReusables.enterValidAccountNumber(driver);
        ProfileReusables.enterValidPassword(driver);
        ProfileReusables.repeatValidPassword(driver);



        ProfileReusables.clickForSignUp(driver);

        //  Verify that error message is displayed for authentication failure.
        String errorText = "";
        if(  driver.getTitle()==ProfileReusables.SignUpTitle){
            errorText = driver.findElement(By.name("not valid")).getText();
        }

        assertEquals(errorText, ProfileReusables.invalidFirstNameError);
    }

    @Test
    public void invalidFamilyName() {
        goToSignupPage();

        ProfileReusables.enterValidFirstName(driver);

        WebElement familyName = driver.findElement(By.name("family name"));
        familyName.clear();
        familyName.sendKeys(ProfileReusables.invalidName);

        ProfileReusables.enterValidUsername(driver);
        ProfileReusables.enterValidEmail(driver);
        ProfileReusables.enterValidPhoneNumber(driver);
        ProfileReusables.enterValidAccountNumber(driver);
        ProfileReusables.enterValidPassword(driver);
        ProfileReusables.repeatValidPassword(driver);



        ProfileReusables.clickForSignUp(driver);

        //  Verify that error message is displayed for authentication failure.
        String errorText = "";
        if(  driver.getTitle()==ProfileReusables.SignUpTitle){
            errorText = driver.findElement(By.name("not valid")).getText();
        }

        assertEquals(errorText, ProfileReusables.invalidFamilyNameError);
    }

    @Test
    public void invalidEmail(){
        goToSignupPage();


        ProfileReusables.enterValidFirstName(driver);
        ProfileReusables.enterValidFamilyName(driver);

        ProfileReusables.enterValidUsername(driver);

        // Enter an invalid email
        WebElement email = driver.findElement(By.name("email"));
        email.clear();
        email.sendKeys(ProfileReusables.invalidEmail);

        ProfileReusables.enterValidPhoneNumber(driver);
        ProfileReusables.enterValidAccountNumber(driver);
        ProfileReusables.enterValidPassword(driver);
        ProfileReusables.repeatValidPassword(driver);



        ProfileReusables.clickForSignUp(driver);

        String errorText = "";
        if(  driver.getTitle()==ProfileReusables.SignUpTitle){
            errorText = driver.findElement(By.name("not valid")).getText();
        }

        assertEquals(errorText, ProfileReusables.invalidEmailError);
    }

    @Test
    public void registeredEmail()  {
        goToSignupPage();

        ProfileReusables.enterValidFirstName(driver);
        ProfileReusables.enterValidFamilyName(driver);

        ProfileReusables.enterValidUsername(driver);


        // Enter an already registered email
        WebElement email = driver.findElement(By.name("email"));
        email.sendKeys(ProfileReusables.email1);   //already registered

        ProfileReusables.enterValidPhoneNumber(driver);
        ProfileReusables.enterValidAccountNumber(driver);
        ProfileReusables.enterValidPassword(driver);
        ProfileReusables.repeatValidPassword(driver);

        ProfileReusables.clickForSignUp(driver);

        //  Verify that error message is displayed for authentication failure.
        String errorText = "";
        if(  driver.getTitle()==ProfileReusables.SignUpTitle){
            errorText = driver.findElement(By.name("not valid")).getText();
        }

        assertEquals(errorText, ProfileReusables.alreadyRegisteredEmailError);
    }

    @Test
    public void invalidUsername() {
        goToSignupPage();

        ProfileReusables.enterValidFirstName(driver);
        ProfileReusables.enterValidFamilyName(driver);

        //invalid username
        WebElement username = driver.findElement(By.name("username"));
        username.clear();
        username.sendKeys(ProfileReusables.invalidName);


        ProfileReusables.enterValidEmail(driver);
        ProfileReusables.enterValidPhoneNumber(driver);
        ProfileReusables.enterValidAccountNumber(driver);
        ProfileReusables.enterValidPassword(driver);
        ProfileReusables.repeatValidPassword(driver);



        ProfileReusables.clickForSignUp(driver);

        String errorText = "";
        if(  driver.getTitle()==ProfileReusables.SignUpTitle){
            errorText = driver.findElement(By.name("not valid")).getText();
        }
        assertEquals(errorText, ProfileReusables.invalidUsernameError);
    }

    @Test
    public void registeredUsername()   {

        goToSignupPage();


        ProfileReusables.enterValidFirstName(driver);
        ProfileReusables.enterValidFamilyName(driver);

        // Enter an already registered username
        WebElement username = driver.findElement(By.name("username"));
        username.clear();
        username.sendKeys(ProfileReusables.username1);

        ProfileReusables.enterValidEmail(driver);
        ProfileReusables.enterValidPhoneNumber(driver);
        ProfileReusables.enterValidAccountNumber(driver);
        ProfileReusables.enterValidPassword(driver);
        ProfileReusables.repeatValidPassword(driver);

        ProfileReusables.clickForSignUp(driver);

        String errorText = "";
        if(  driver.getTitle()==ProfileReusables.SignUpTitle){
            errorText = driver.findElement(By.name("not valid")).getText();
        }
        assertEquals(errorText, ProfileReusables.alreadyRegisteredUsernameError);
    }

    @Test
    public void invalidPhoneNumber()  {
        goToSignupPage();


        ProfileReusables.enterValidFirstName(driver);
        ProfileReusables.enterValidFamilyName(driver);
        ProfileReusables.enterValidUsername(driver);
        ProfileReusables.enterValidEmail(driver);

        WebElement phoneNumber = driver.findElement(By.name("contact number"));
        phoneNumber.clear();
        phoneNumber.sendKeys(ProfileReusables.invalidPhoneNumber);


        ProfileReusables.enterValidAccountNumber(driver);
        ProfileReusables.enterValidPassword(driver);
        ProfileReusables.repeatValidPassword(driver);


        ProfileReusables.clickForSignUp(driver);


        String errorText = "";
        if(  driver.getTitle()==ProfileReusables.SignUpTitle){
            errorText = driver.findElement(By.name("not valid")).getText();
        }
        assertEquals(errorText, ProfileReusables.invalidPhoneNumberError);
    }


    @Test
    public void invalidAccountNumber() {
        goToSignupPage();

        ProfileReusables.enterValidFirstName(driver);
        ProfileReusables.enterValidFamilyName(driver);
        ProfileReusables.enterValidUsername(driver);
        ProfileReusables.enterValidEmail(driver);
        ProfileReusables.enterValidPhoneNumber(driver);


        WebElement accountNumber = driver.findElement(By.name("account number"));
        accountNumber.clear();
        accountNumber.sendKeys(ProfileReusables.invalidAccountNumber);

        ProfileReusables.enterValidPassword(driver);
        ProfileReusables.repeatValidPassword(driver);

        ProfileReusables.clickForSignUp(driver);

        String errorText = "";
        if(  driver.getTitle()==ProfileReusables.SignUpTitle){
            errorText = driver.findElement(By.name("not valid")).getText();
        }
        assertEquals(errorText, ProfileReusables.invalidAccountNumberError);
    }

    @Test
    public void registeredAccountNumber()  {
        goToSignupPage();


        ProfileReusables.enterValidFirstName(driver);
        ProfileReusables.enterValidFamilyName(driver);
        ProfileReusables.enterValidUsername(driver);
        ProfileReusables.enterValidEmail(driver);
        ProfileReusables.enterValidPhoneNumber(driver);


        WebElement accountNumber = driver.findElement(By.name("account number"));
        accountNumber.clear();
        accountNumber.sendKeys(ProfileReusables.accountNumber1);

        ProfileReusables.enterValidPassword(driver);
        ProfileReusables.repeatValidPassword(driver);

        ProfileReusables.clickForSignUp(driver);

        //    Verify that error message is displayed for authentication failure.
        String errorText = "";
        if(  driver.getTitle()==ProfileReusables.SignUpTitle){
            errorText = driver.findElement(By.name("not valid")).getText();
        }
        assertEquals(errorText, ProfileReusables.alreadyRegisteredAccountNumberError);
    }



    @Test
    public void invalidPassword()   {
        goToSignupPage();

        ProfileReusables.enterValidFirstName(driver);
        ProfileReusables.enterValidFamilyName(driver);
        ProfileReusables.enterValidUsername(driver);
        ProfileReusables.enterValidEmail(driver);
        ProfileReusables.enterValidPhoneNumber(driver);
        ProfileReusables.enterValidAccountNumber(driver);

        WebElement password = driver.findElement(By.name("password"));
        password.clear();
        password.sendKeys(ProfileReusables.invalidPassword);

        ProfileReusables.repeatValidPassword(driver);


        ProfileReusables.clickForSignUp(driver);



        //    Verify that error message is displayed for authentication failure.
        String errorText = "";
        if(  driver.getTitle()==ProfileReusables.SignUpTitle){
            errorText = driver.findElement(By.name("not valid")).getText();
        }
        assertEquals(errorText, ProfileReusables.invalidPasswordError);
    }

    @Test
    public void invalidRepeatPassword()  {
        goToSignupPage();

        ProfileReusables.enterValidFirstName(driver);
        ProfileReusables.enterValidFamilyName(driver);
        ProfileReusables.enterValidUsername(driver);
        ProfileReusables.enterValidEmail(driver);
        ProfileReusables.enterValidPhoneNumber(driver);
        ProfileReusables.enterValidAccountNumber(driver);
        ProfileReusables.enterValidPassword(driver);


        WebElement password = driver.findElement(By.name("password repeat"));
        password.clear();
        password.sendKeys(ProfileReusables.notMatchedPassword);  //wrong repeat



        ProfileReusables.clickForSignUp(driver);



        //    Verify that error message is displayed for authentication failure.
        String errorText = "";
        if(  driver.getTitle()==ProfileReusables.SignUpTitle){
            errorText = driver.findElement(By.name("not valid")).getText();
        }
        assertEquals(errorText, ProfileReusables.invalidPasswordRepaetError);
    }


    @Test
    public void validSignUp() {
        goToSignupPage();

        ProfileReusables.enterValidFirstName(driver);
        ProfileReusables.enterValidFamilyName(driver);
        ProfileReusables.enterValidUsername(driver);
        ProfileReusables.enterValidEmail(driver);
        ProfileReusables.enterValidPhoneNumber(driver);
        ProfileReusables.enterValidAccountNumber(driver);
        ProfileReusables.enterValidPassword(driver);
        ProfileReusables.repeatValidPassword(driver);


        ProfileReusables.clickForSignUp(driver);




        //assertEquals(driver.getTitle(), successTitle);
        GeneralReusables.setUpToHomepage(driver);
        GeneralReusables.login(driver,ProfileReusables.email1, ProfileReusables.password1);
        assertEquals(driver.getTitle(), GeneralReusables.PANEL_TITLE);




    }


    @AfterClass
    public static void tearDown() {
        driver.close();
        // TODO : dummy account should be deleted???

    }







}
