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

/**
 * Created by Golpar on 4/13/2018 AD.
 */
public class UserDetailsChange {
    static WebDriver driver;
    static String userDetailChangeTitle = "پنل مدیریت | ویرایش اطلاعات کاربری";
    static String newFirstName = "";
    static String newFamilyName = "";
    static String newLatinFirstName = "";
    static String newLatinFamilyName = "";
    static String newPhoneNumber = "";
    static String newEmail = "";
    static String newAccountNumber = "";
    static String invalidLatinName = "";

    public void change(String elementName, String newField, String submitButtonName) {
        WebElement element = driver.findElement(By.name(elementName));
        element.sendKeys(newField);
        WebElement button = driver.findElement(By.name(submitButtonName));
        button.submit();

    }

    public void gotoUserDetails() {
        WebElement viewProfileLink = driver.findElement(By.name("")); // TODO : I could not find it.
        viewProfileLink.click(); // TODO : click??????

    }

    public void checkForChange(String elementName, String expected) {
        gotoUserDetails();
        String text = driver.findElement(By.name(elementName)).getText();
        assertEquals(expected, text);
    }

    @BeforeClass
    public static void setUp() {
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        GeneralReusables.setUpToHomepage(driver);
        GeneralReusables.loginAsACustomer(driver);
        WebElement userSettingLink = driver.findElement(By.name("user setting"));
        userSettingLink.click();
        WebElement userDetailsChangeLink = driver.findElement(By.name("user details change"));
        userDetailsChangeLink.click();
    }

    @Test
    public void preConditionTest() {
        String title = driver.getTitle();
        assertEquals(title, userDetailChangeTitle);
    }

    @Test
    public void validFirstNameChange() {
        change("first name", newFirstName, "submit name");
        checkForChange("", newFirstName);// TODO

    }

    @Test
    public void validFamilyNameChange() {
        change("family name", newFamilyName, "submit name");
        checkForChange("", newFamilyName);// TODO

    }

    @Test
    public void validLatinFirstNameChange() {
        change("latin first name", newLatinFirstName, "submit name");
        checkForChange("", newLatinFirstName);// TODO

    }

    @Test
    public void validLatinFamilyNameChange() {
        change("first name", newLatinFamilyName, "submit name");
        checkForChange("", newLatinFamilyName);// TODO

    }

    @Test
    public void invalidNameChange() {
        change("first name", ProfileReusables.invalidName, "submit name");
        // TODO: add error
    }

    @Test
    public void invalidLatinNameChange() {
        change("latin first name", invalidLatinName, "submit name");
        // TODO: add error

    }

    @Test
    public void validEmailChange() {
        change("email", newEmail, "submit contact");
        checkForChange("", newAccountNumber);// TODO

    }

    @Test
    public void invalidEmailChange() {
        change("email", ProfileReusables.invalidEmail, "submit contact");
        // TODO: add error

    }

    @Test
    public void validPhoneNumberChange() {
        change("phoneNumber", newPhoneNumber, "submit contact");
        checkForChange("", newPhoneNumber);// TODO

    }

    @Test
    public void setContactMethodEmail() {
        WebElement emailRadio = driver.findElement(By.name("email radio"));
        emailRadio.click();
        checkForChange("", "ایمیل");// TODO

    }

    @Test
    public void setContactMethodPhone() {
        WebElement phoneRadio = driver.findElement(By.name("phone radio"));
        phoneRadio.click();
        checkForChange("", "پیامک");// TODO

    }

    @Test
    public void validAccountNumberChange() {
        change("account number", newAccountNumber, "submit account number");
        checkForChange("", newAccountNumber);// TODO

    }

    @Test
    public void invalidAccountNumberChange() {
        change("account number", ProfileReusables.invalidAccountNumber, "submit account number");
        // TODO: add error

    }

    @AfterClass
    public static void tearDown() {
        GeneralReusables.logout(driver);
        // TODO: revert the changes
    }

}
