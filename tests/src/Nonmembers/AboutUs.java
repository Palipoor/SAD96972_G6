package Nonmembers;

import Reusables.GeneralReusables;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import static org.junit.Assert.assertTrue;

/**
 * Created by Golpar on 4/21/2018 AD.
 */
public class AboutUs {
    private static WebDriver driver;

    @BeforeClass
    public static void setUp() {
        driver = new ChromeDriver();
        GeneralReusables.setUpToHomepage(driver);
        WebElement about = driver.findElement(By.name("contact"));
        about.click();
    }

    @Test
    public void containsText() {
        WebElement title = driver.findElement(By.name("about-us-header"));
        WebElement content = driver.findElement(By.name("about-us-content"));
        assertTrue(!title.getText().equals("") && !content.getText().equals(""));
    }

    @Test
    public static void tearDown() {
        driver.close();
    }
}
