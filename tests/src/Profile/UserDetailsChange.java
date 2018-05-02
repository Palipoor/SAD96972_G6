package Profile;

import Reusables.GeneralReusables;
import Reusables.ProfileReusables;
import org.junit.After;
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


    @BeforeClass
    public static void setUp() {
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        GeneralReusables.setUpToHomepage(driver);
        GeneralReusables.loginAsACustomer(driver);
        WebElement userSettingLink = driver.findElement(By.name("user setting"));
        userSettingLink.click();
        WebElement  userDetailsChangeLink= driver.findElement(By.name("user details change"));
        userDetailsChangeLink.click();
    }

    @Test
    public void preConditionTest() {
        String title = driver.getTitle();
        assertEquals(title, userDetailChangeTitle);
    }

    @Test
    public void firstNameChange() {
        WebElement fistName = driver.findElement(By.name("fist name"));
        fistName.sendKeys();

    }
    @Test
    public void familyNameChange() {
        WebElement fistName = driver.findElement(By.name("fist name"));
        fistName.sendKeys();

    }
    @Test
    public void usernameChange() {
        WebElement fistName = driver.findElement(By.name("fist name"));
        fistName.sendKeys();

    }

    @Test
    public void emailChange() {
        WebElement fistName = driver.findElement(By.name("fist name"));
        fistName.sendKeys();

    }
    @Test
    public void phoneNumberChange() {
        WebElement fistName = driver.findElement(By.name("fist name"));
        fistName.sendKeys();

    }



    @Test
    public void contactMethodChange() {
        WebElement fistName = driver.findElement(By.name("fist name"));
        fistName.sendKeys();

    }
    @Test
    public void accountNumberChange() {
        WebElement fistName = driver.findElement(By.name("fist name"));
        fistName.sendKeys();

    }



    @AfterClass
    public static void tearDown() {
        GeneralReusables.logout(driver);
    }


}
