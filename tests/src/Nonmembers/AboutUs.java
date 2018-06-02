package Nonmembers;

import Reusables.GeneralReusables;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.FirefoxDriver()();

import static org.junit.Assert.assertTrue;

/**
 * Created by Golpar on 4/21/2018 AD.
 */
public class AboutUs {
    private static WebDriver driver;

    @BeforeClass
    public static void setUp() {
        driver = new FirefoxDriver()()();
        GeneralReusables.setUpToHomepage(driver);
        WebElement about = driver.findElement(By.name("about"));
        about.click();
    }

    @Test
    public void containsText() {
        WebElement title = driver.findElement(By.name("about-us-header"));
        WebElement content = driver.findElement(By.name("about-us-content"));
        assertTrue(!title.getText().equals("") && !content.getText().equals(""));
    }

    @AfterClass
    public static void tearDown() {
        driver.close();
    }
}
