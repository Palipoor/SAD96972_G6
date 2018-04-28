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
        WebElement signUpButton = driver.findElement(By.name("sign-up")); //TODO: !!!!!!!!!!!!
        signUpButton.click();

    }


    @Test
    public void preConditionTest() {
        String title = driver.getTitle();
        assertEquals(title, ProfileReusables.SignUpTitle);
    }


    @Test
    public void invalidFirstName() throws Exception{

        WebElement firstName = driver.findElement(By.name("first name"));
        firstName.sendKeys("");   //wrong format //TODO

        ProfileReusables.enterValidFamilyName(driver);
        ProfileReusables.enterValidUsername(driver);
        ProfileReusables.enterValidEmail(driver);
        ProfileReusables.enterValidPhoneNumber(driver);
        ProfileReusables.enterValidAccountNumber(driver);
        ProfileReusables.enterValidPassword(driver);
        ProfileReusables.repeatValidPassword(driver);



        ProfileReusables.clickForSignUp(driver);

        //  Verify that error message is displayed for authentication failure.
        String firstNameErrorText = driver.findElement(By.name("not valid")).getText();
        assertEquals(firstNameErrorText, ProfileReusables.invalidFirstNameError);
    }

    @Test
    public void invalidFamilyName() throws Exception{

        ProfileReusables.enterValidFirstName(driver);

        WebElement familyName = driver.findElement(By.name("family name"));
        familyName.clear();
        familyName.sendKeys("");   //wrong format //TODO

        ProfileReusables.enterValidUsername(driver);
        ProfileReusables.enterValidEmail(driver);
        ProfileReusables.enterValidPhoneNumber(driver);
        ProfileReusables.enterValidAccountNumber(driver);
        ProfileReusables.enterValidPassword(driver);
        ProfileReusables.repeatValidPassword(driver);



        ProfileReusables.clickForSignUp(driver);

        //  Verify that error message is displayed for authentication failure.
        String firstNameErrorText = driver.findElement(By.name("not valid")).getText();
        assertEquals(firstNameErrorText, ProfileReusables.invalidFirstNameError);
    }

    @Test
    public void invalidEmail() throws Exception{


        ProfileReusables.enterValidFirstName(driver);
        ProfileReusables.enterValidFamilyName(driver);

        ProfileReusables.enterValidUsername(driver);

        // Enter an invalid email
        WebElement email = driver.findElement(By.name("email"));
        email.clear();
        email.sendKeys("email.com");   //wrong format

        ProfileReusables.enterValidPhoneNumber(driver);
        ProfileReusables.enterValidAccountNumber(driver);
        ProfileReusables.enterValidPassword(driver);
        ProfileReusables.repeatValidPassword(driver);



        ProfileReusables.clickForSignUp(driver);

        //  Verify that error message is displayed for authentication failure.
        String emailErrorText = driver.findElement(By.name("not valid")).getText();
        assertEquals(emailErrorText, ProfileReusables.invalidEmailError);
    }

    @Test
    public void registeredEmail()  throws Exception{

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
        String emailErrorText = driver.findElement(By.name("not valid")).getText();
        assertEquals(emailErrorText, ProfileReusables.alreadyRegisteredEmailError);
    }

    @Test
    public void invalidUsername() throws Exception{

        ProfileReusables.enterValidFirstName(driver);
        ProfileReusables.enterValidFamilyName(driver);

        //invalid username
        WebElement username = driver.findElement(By.name("username"));
        username.clear();
        username.sendKeys("");  //wrong format //TODO


        ProfileReusables.enterValidEmail(driver);
        ProfileReusables.enterValidPhoneNumber(driver);
        ProfileReusables.enterValidAccountNumber(driver);
        ProfileReusables.enterValidPassword(driver);
        ProfileReusables.repeatValidPassword(driver);



       ProfileReusables.clickForSignUp(driver);

        //    Verify that error message is displayed for authentication failure.
        String usernameErrorText = driver.findElement(By.name("not valid")).getText();
        assertEquals(usernameErrorText, ProfileReusables.invalidUsernameError);
    }

    @Test
    public void registeredUsername()   throws Exception{


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

        //  Verify that error message is displayed for authentication failure.
        String usernameErrorText = driver.findElement(By.name("not valid")).getText();
        assertEquals(usernameErrorText, ProfileReusables.alreadyRegisteredUsernameError);
    }

    @Test
    public void invalidPhoneNumber()  throws Exception {


        ProfileReusables.enterValidFirstName(driver);
        ProfileReusables.enterValidFamilyName(driver);
        ProfileReusables.enterValidUsername(driver);
        ProfileReusables.enterValidEmail(driver);

        WebElement phoneNumber = driver.findElement(By.name("contact number"));
        phoneNumber.clear();
        phoneNumber.sendKeys("");  // wrong format //TODO


        ProfileReusables.enterValidAccountNumber(driver);
        ProfileReusables.enterValidPassword(driver);
        ProfileReusables.repeatValidPassword(driver);


        ProfileReusables.clickForSignUp(driver);


        //    Verify that error message is displayed for authentication failure.
        String phoneNumberErrorText = driver.findElement(By.name("not valid")).getText();
        assertEquals(phoneNumberErrorText, ProfileReusables.invalidPhoneNumberError);
    }


    @Test
    public void invalidAccountNumber()  throws Exception{


        ProfileReusables.enterValidFirstName(driver);
        ProfileReusables.enterValidFamilyName(driver);
        ProfileReusables.enterValidUsername(driver);
        ProfileReusables.enterValidEmail(driver);
        ProfileReusables.enterValidPhoneNumber(driver);


        WebElement accountNumber = driver.findElement(By.name("account number"));
        accountNumber.clear();
        accountNumber.sendKeys("");  //wrong format //TODO

        ProfileReusables.enterValidPassword(driver);
        ProfileReusables.repeatValidPassword(driver);

        ProfileReusables.clickForSignUp(driver);

        //    Verify that error message is displayed for authentication failure.
        String accountNumberErrorText = driver.findElement(By.name("not valid")).getText();
        assertEquals(accountNumberErrorText, ProfileReusables.invalidAccountNumberError);
    }

    @Test
    public void registeredAccountNumber()  throws Exception{

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
        String accountNumberErrorText = driver.findElement(By.name("not valid")).getText();
        assertEquals(accountNumberErrorText, ProfileReusables.alreadyRegisteredAccountNumberError);
    }



    @Test
    public void invalidPassword()  throws Exception {

        ProfileReusables.enterValidFirstName(driver);
        ProfileReusables.enterValidFamilyName(driver);
        ProfileReusables.enterValidUsername(driver);
        ProfileReusables.enterValidEmail(driver);
        ProfileReusables.enterValidPhoneNumber(driver);
        ProfileReusables.enterValidAccountNumber(driver);

        WebElement password = driver.findElement(By.name("password"));
        password.clear();
        password.sendKeys("");  //wrong format //TODO

        ProfileReusables.repeatValidPassword(driver);


        ProfileReusables.clickForSignUp(driver);



        //    Verify that error message is displayed for authentication failure.
        String passwordErrorText = driver.findElement(By.name("not valid")).getText();
        assertEquals(passwordErrorText, ProfileReusables.invalidPasswordError);
    }

    @Test
    public void invalidRepeatPassword()  throws Exception {

        ProfileReusables.enterValidFirstName(driver);
        ProfileReusables.enterValidFamilyName(driver);
        ProfileReusables.enterValidUsername(driver);
        ProfileReusables.enterValidEmail(driver);
        ProfileReusables.enterValidPhoneNumber(driver);
        ProfileReusables.enterValidAccountNumber(driver);
        ProfileReusables.enterValidPassword(driver);


        WebElement password = driver.findElement(By.name("password repeat"));
        password.clear();
        password.sendKeys("something else");  //wrong repeat



        ProfileReusables.clickForSignUp(driver);



        //    Verify that error message is displayed for authentication failure.
        String passwordErrorText = driver.findElement(By.name("not valid")).getText();
        assertEquals(passwordErrorText, ProfileReusables.invalidPasswordRepaetError);
    }


    @Test
    public void validSignUp() throws Exception {

        ProfileReusables.enterValidFirstName(driver);
        ProfileReusables.enterValidFamilyName(driver);
        ProfileReusables.enterValidUsername(driver);
        ProfileReusables.enterValidEmail(driver);
        ProfileReusables.enterValidPhoneNumber(driver);
        ProfileReusables.enterValidAccountNumber(driver);
        ProfileReusables.enterValidPassword(driver);
        ProfileReusables.repeatValidPassword(driver);


        ProfileReusables.clickForSignUp(driver);


        String successMessage = driver.findElement(By.name("success message")).getText();


        assertEquals(successMessage, ProfileReusables.successMessage);




    }


    @AfterClass
    public void tearDown() {
        // TODO : dummy account should be deleted???

    }







}
