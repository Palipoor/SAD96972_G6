package Profile;

import Reusables.Order;
import Reusables.ProfileReusables;
import Reusables.GeneralReusables;
import org.junit.*;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;

@RunWith(Reusables.OrderedRunner.class)
public class LogIn {
    private static WebDriver driver;


    @BeforeClass
    public static void setUp() {
        // Initialize the WebDriver
        driver = new FirefoxDriver();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        GeneralReusables.setUpToHomepage(driver);
        // Go to Log In page
        String linkToOpen = driver.findElement(By.name("log in")).getAttribute("href");
        driver.get(linkToOpen);

    }


    @Test
    @Order(order = 1)
    public void preConditionTest() {
        String title = driver.getTitle();
        assertEquals(title, ProfileReusables.reusableStrings.get("log-in-title"));
    }

    @Test
    @Order(order = 2)
    public void invalidEmail() throws Exception {


        // Enter an invalid email
        WebElement email = driver.findElement(By.name("email"));
        email.sendKeys("email.com");


        ProfileReusables.enterValidPassword(driver);


        ProfileReusables.clickForLogIn(driver);
        String errorText = "";

        if (driver.getTitle().equals(ProfileReusables.reusableStrings.get("log-in-title"))) {
            errorText = driver.findElement(By.name("not valid")).getText();
        } else
            GeneralReusables.backToLogin(driver); //This only happens when the test fails

        assertFalse(driver.getCurrentUrl().contains("dashboard"));
        assertEquals(errorText, ProfileReusables.invalidEmailError);


    }

    @Test
    @Order(order = 3)
    public void notRegisteredEmail() throws Exception {


        // Enter an not registered email
        WebElement email = driver.findElement(By.name("email"));
        email.clear();
        email.sendKeys(ProfileReusables.notRegisteredEmail);

        ProfileReusables.enterValidPassword(driver);


        ProfileReusables.clickForLogIn(driver);

        String errorText = "";

        if (driver.getTitle().equals(ProfileReusables.reusableStrings.get("log-in-title"))) {
            errorText = driver.findElement(By.name("not valid")).getText();
        } else
            GeneralReusables.backToLogin(driver);

        assertFalse(driver.getCurrentUrl().contains("dashboard"));
        assertEquals(errorText, ProfileReusables.notRegisteredEmailError);
    }

    @Test
    @Order(order = 4)
    public void wrongPassword() throws Exception {


        ProfileReusables.enterValidEmail(driver);


        WebElement password = driver.findElement(By.name("password"));
        password.clear();
        password.sendKeys(ProfileReusables.wrongPassword);


        ProfileReusables.clickForLogIn(driver);
        String errorText = "";

        if (driver.getTitle().equals(ProfileReusables.reusableStrings.get("log-in-title"))) {
            errorText = driver.findElement(By.name("not valid")).getText();
        } else
            GeneralReusables.backToLogin(driver);

        assertFalse(driver.getCurrentUrl().contains("dashboard"));
        assertEquals(errorText, ProfileReusables.wrongPasswordError);
    }


    @Test
    @Order(order = 5)
    public void validLogIn() throws Exception {

        ProfileReusables.enterValidEmail(driver);

        ProfileReusables.enterValidPassword(driver);


        ProfileReusables.clickForLogIn(driver);

        List<WebElement> userMenu = driver.findElements(By.name("user menu"));
        assertNotEquals(userMenu.size(), 0);// exists such element in the page


    }


    @AfterClass
    public static void tearDown() {
        GeneralReusables.logout(driver);
    }


}
