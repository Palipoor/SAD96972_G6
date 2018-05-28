package Profile;

import Reusables.GeneralReusables;
import Reusables.ProfileReusables;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

@RunWith(Reusables.OrderedRunner.class)
public class NotifsView {
    static WebDriver driver;


    @BeforeClass
    public static void setUp() {
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        GeneralReusables.setUpToHomepage(driver);
        GeneralReusables.loginAsACustomer(driver);

    }

    @Test
    @Order(order = 1)
    public void preConditionTest() {
        String title = driver.getTitle();
        assertEquals(title, GeneralReusables.PANEL_TITLE);
    }

    @Test
    public void viewNotifs() {
        List<WebElement> notifs = driver.findElements(By.name("notifications menu"));
        assertNotEquals(notifs.size(), 0);// exists such element in the page
    }

    @AfterClass
    public static void tearDown() {
        GeneralReusables.logout(driver);
        driver.close();
    }

}
