package Profile;

import Reusables.GeneralReusables;
import Reusables.ProfileReusables;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;

public class PasswordChange {

    static String newPass = "123454321Dorna";



    static  WebDriver driver;

    public void submit (){
        WebElement submitButton = driver.findElement(By.name("submit"));
        submitButton.click();
    }
    @BeforeClass
    public void setUp() {
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        GeneralReusables.setUpToHomepage(driver);
        GeneralReusables.loginAsACustomer(driver); //TODO :       how to change in order to match for all kind of users????

        WebElement changePasswordLink = driver.findElement(By.name("password change"));
        changePasswordLink.click(); //TODO : click??????

    }

    @Test
    public void preConditionTest() {
        String title = driver.getTitle();
        assertEquals(title, ProfileReusables.passwordChangeTitle);
    }


    @Test
    public void wrongCurrentPassword() throws Exception {
        WebElement currentPassword = driver.findElement(By.name("current password"));
        currentPassword.sendKeys(ProfileReusables.wrongPassword);

        WebElement newPassword = driver.findElement(By.name("new password"));
        newPassword.sendKeys(newPass);

        WebElement newPasswordRep = driver.findElement(By.name("new password rep"));
        newPasswordRep.sendKeys(newPass);

        submit();


        String errorText = driver.findElement(By.name("not valid")).getText();
        assertEquals(errorText, ProfileReusables.wrongPasswordError);
    }


    @Test
    public void invalidPassword() throws Exception {
        WebElement currentPassword = driver.findElement(By.name("current password"));
        currentPassword.clear();
        currentPassword.sendKeys(ProfileReusables.password1);

        WebElement newPassword = driver.findElement(By.name("new password"));
        newPassword.clear();
        newPassword.sendKeys(ProfileReusables.invalidPassword);

        WebElement newPasswordRep = driver.findElement(By.name("new password rep"));
        newPasswordRep.clear();
        newPasswordRep.sendKeys(ProfileReusables.invalidPassword);

        submit();


        String errorText = driver.findElement(By.name("not valid")).getText();
        assertEquals(errorText, ProfileReusables.invalidPasswordError);
    }

    @Test
    public void notMatchedPassword() throws Exception {
        WebElement currentPassword = driver.findElement(By.name("current password"));
        currentPassword.clear();
        currentPassword.sendKeys(ProfileReusables.password1);

        WebElement newPassword = driver.findElement(By.name("new password"));
        newPassword.clear();
        newPassword.sendKeys(newPass);

        WebElement newPasswordRep = driver.findElement(By.name("new password rep"));
        newPasswordRep.clear();
        newPasswordRep.sendKeys(ProfileReusables.notMatchedPassword);

        submit();


        String errorText = driver.findElement(By.name("not valid")).getText();
        assertEquals(errorText, ProfileReusables.invalidPasswordRepaetError);
    }

    @Test
    public void validChange() throws Exception {
        WebElement currentPassword = driver.findElement(By.name("current password"));
        currentPassword.clear();
        currentPassword.sendKeys(ProfileReusables.password1);

        WebElement newPassword = driver.findElement(By.name("new password"));
        newPassword.clear();
        newPassword.sendKeys(newPass);

        WebElement newPasswordRep = driver.findElement(By.name("new password rep"));
        newPasswordRep.clear();
        newPasswordRep.sendKeys(newPass);

        submit();

        GeneralReusables.logout(driver);
        GeneralReusables.login(driver, ProfileReusables.email1, ProfileReusables.password1);

        String passwordErrorText = driver.findElement(By.name("not valid")).getText();
        assertEquals(passwordErrorText, ProfileReusables.wrongPasswordError);


        //TODO : change in order to match for all kind of users
    }







    @AfterClass
    public void tearDown() { //TODO : it should be loged out after valid change.
        }
}
