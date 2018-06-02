package Nonmembers;

import Reusables.GeneralReusables;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

/**
 * Created by Golpar on 4/26/2018 AD.
 */
public class Karmozd {
    private static WebDriver driver;

    @BeforeClass
    public static void setUp() {
        driver = new FirefoxDriver();
        GeneralReusables.setUpToHomepage(driver);
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0,1500)");
        GeneralReusables.waitForSeconds(5);
        WebElement features = driver.findElement(By.name("features"));
        features.click();
    }

    @Test
    public void transactionExistsTest() {
        List<WebElement> transactions = driver.findElements(By.name("transaction-title"));
        assertNotEquals(transactions.size(), 0);
    }

    @Test
    public void karmozdExistsTest() {
        List<WebElement> transactions = driver.findElements(By.name("transaction-title"));
        List<WebElement> karmozds = driver.findElements(By.name("karmozd"));
        assertEquals(transactions.size(), karmozds.size());
    }

    @AfterClass
    public static void tearDown() {
        driver.close();
    }
}
