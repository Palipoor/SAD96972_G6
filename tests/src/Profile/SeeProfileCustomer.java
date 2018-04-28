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
public class SeeProfileCustomer {
    static WebDriver driver;
    static String userDetailTitle = "مشخصات کاربری";


    @BeforeClass
    public void setUp() {
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        GeneralReusables.setUpToHomepage(driver);
        GeneralReusables.loginAsACustomer(driver);
        WebElement viewProfileLink = driver.findElement(By.name("")); //TODO : I could not find it.
        viewProfileLink.click(); //TODO : click??????

    }

    @Test
    public void userDetailsExistence() {
        String title = driver.getTitle();
        assertEquals(title, userDetailTitle);
    }

    @AfterClass
    public void tearDown() {
        GeneralReusables.logout(driver);
    }


}
