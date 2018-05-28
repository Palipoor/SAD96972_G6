package Profile;


import Reusables.ProfileReusables;
import Reusables.GeneralReusables;
import org.junit.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

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
    public void invalidEmail()  throws Exception {
        ProfileReusables.enterValidUsername(driver);


        // Enter an invalid email
        WebElement email = driver.findElement(By.name("email"));
        email.sendKeys("email.com");


        ProfileReusables.enterValidPassword(driver);


        ProfileReusables.clickForLogIn(driver);


        //    Verify that error message is displayed for authentication failure.
        String emailErrorText = driver.findElement(By.name("not valid")).getText();
        assertEquals(emailErrorText, ProfileReusables.invalidEmailError);
    }

    @Test
    public void notRegisteredEmail()  throws Exception {



        // Enter an not registered email
        WebElement email = driver.findElement(By.name("email"));
        email.clear();
        email.sendKeys(ProfileReusables.notRegisteredEmail);

        ProfileReusables.enterValidPassword(driver);


        ProfileReusables.clickForLogIn(driver);


        //    Verify that error message is displayed for authentication failure.
        String emailErrorText = driver.findElement(By.name("not valid")).getText();
        assertEquals(emailErrorText,ProfileReusables.notRegisteredEmailError);
    }

    @Test
    public void wrongPassword() throws Exception {


        ProfileReusables.enterValidEmail(driver);


        WebElement password  = driver.findElement(By.name("password"));
        password.clear();
        password.sendKeys(ProfileReusables.wrongPassword);


        ProfileReusables.clickForLogIn(driver);


        //    Verify that error message is displayed for authentication failure.
        String passwordErrorText = driver.findElement(By.name("not valid")).getText();
        assertEquals(passwordErrorText, ProfileReusables.wrongPasswordError);
    }



    @Test
    public void validLogIn() throws Exception {

        ProfileReusables.enterValidEmail(driver);

        ProfileReusables.enterValidPassword(driver);


        ProfileReusables.clickForLogIn(driver);

        List<WebElement> userMenu = driver.findElements(By.name("user menu"));
        assertNotEquals(userMenu.size(), 0);// exists such element in the page




    }


    @AfterClass
    public void tearDown() {
        GeneralReusables.logout(driver);
    }







}
