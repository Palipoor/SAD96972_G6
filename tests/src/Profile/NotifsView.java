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

import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

@RunWith(Reusables.OrderedRunner.class)
public class NotifsView {
    static WebDriver driver;


    @BeforeClass
    public static void setUp() {
        driver = new FirefoxDriver();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        GeneralReusables.setUpToHomepage(driver);
        GeneralReusables.loginAsACustomer(driver);

    }

    @Test
    @Order(order = 1)
    public void preConditionTest() {
        String title = driver.getTitle();
        assertEquals(title, GeneralReusables.reusableStrings.get("pishkhan"));
    }

    @Test
    public void viewNotifs() {
        driver.findElement(By.name("notif")).click();
        driver.findElement(By.name("see-all")).click();
        String title = driver.getTitle();
        assertEquals(title, GeneralReusables.reusableStrings.get("notification-title"));

    }

    @AfterClass
    public static void tearDown() {
        GeneralReusables.logout(driver);
    }

}
