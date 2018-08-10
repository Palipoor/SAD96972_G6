package Profile;

import Reusables.GeneralReusables;
import Reusables.Order;
import Reusables.ProfileReusables;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;
@RunWith(Reusables.OrderedRunner.class)
public class PasswordChange {

    static String newPass = "123454321Dorna";



    static  WebDriver driver;

    public void submit (){
        WebElement submitButton = driver.findElement(By.name("change"));
        submitButton.click();
    }
    @BeforeClass
    public static void setUp() {
        driver = new FirefoxDriver();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        GeneralReusables.setUpToHomepage(driver);
        GeneralReusables.loginAsACustomer(driver);
        driver.findElement(By.name("profile-list")).click();
        driver.findElement(By.name("password-change")).click();

    }

    @Test
    @Order(order = 1)
    public void preConditionTest() {
        String title = driver.getTitle();
        assertEquals(title, ProfileReusables.reusableStrings.get("password-change-title"));
    }


    @Test
    @Order(order = 2)
    public void wrongCurrentPassword() throws Exception {
        WebElement currentPassword = driver.findElement(By.name("current-password"));
        currentPassword.sendKeys(ProfileReusables.wrongPassword);

        WebElement newPassword = driver.findElement(By.name("new-password"));
        newPassword.sendKeys(newPass);

        WebElement newPasswordRep = driver.findElement(By.name("new-password-2"));
        newPasswordRep.sendKeys(newPass);

        submit();


        String errorText = driver.findElement(By.name("not valid")).getText();
        assertEquals(errorText, ProfileReusables.wrongPasswordError);
    }


    @Test
    @Order(order = 4)
    public void notMatchedPassword() throws Exception {
        WebElement currentPassword = driver.findElement(By.name("current-password"));
        currentPassword.clear();
        currentPassword.sendKeys(ProfileReusables.password1);

        WebElement newPassword = driver.findElement(By.name("new-password"));
        newPassword.clear();
        newPassword.sendKeys(newPass);

        WebElement newPasswordRep = driver.findElement(By.name("new-password-2"));
        newPasswordRep.clear();
        newPasswordRep.sendKeys(ProfileReusables.notMatchedPassword);

        submit();


        String errorText = driver.findElement(By.name("not valid")).getText();
        assertEquals(errorText, ProfileReusables.invalidPasswordRepaetError);
    }

    @Test
    @Order(order = 5)
    public void validChange() throws Exception {
        WebElement currentPassword = driver.findElement(By.name("current-password"));
        currentPassword.clear();
        currentPassword.sendKeys(ProfileReusables.password1);

        WebElement newPassword = driver.findElement(By.name("new-password"));
        newPassword.clear();
        newPassword.sendKeys(newPass);

        WebElement newPasswordRep = driver.findElement(By.name("new-password-2"));
        newPasswordRep.clear();
        newPasswordRep.sendKeys(newPass);

        submit();

        GeneralReusables.logout(driver);
        driver = new FirefoxDriver();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        GeneralReusables.setUpToHomepage(driver);
        String linkToOpen = driver.findElement(By.name("log in")).getAttribute("href");
        driver.get(linkToOpen);
        GeneralReusables.login(driver, ProfileReusables.reusableStrings.get("email"), ProfileReusables.password1);

        String passwordErrorText = driver.findElement(By.name("not valid")).getText();
        assertEquals(passwordErrorText, ProfileReusables.wrongPasswordError);


        //TODO : change in order to match for all kind of users
    }







    @AfterClass
    public static void tearDown() { //TODO : it should be loged out after valid change.
        }
}
