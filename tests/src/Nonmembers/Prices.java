package Nonmembers;

import Reusables.GeneralReusables;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import static org.junit.Assert.assertTrue;

/**
 * Created by Golpar on 4/21/2018 AD.
 */
public class Prices {
    private static WebDriver driver;

    @BeforeClass
    public static void setUp() {
        driver = new FirefoxDriver();
        GeneralReusables.setUpToHomepage(driver);
        WebElement prices = driver.findElement(By.name("prices"));
        prices.click();
    }

    @Test
    public void containsText() {
        WebElement content = driver.findElement(By.name("prices-content"));
        WebElement euroPrice = content.findElement(By.name("euro-price"));
        WebElement dollarPrice = content.findElement(By.name("dollar-price"));

        assertTrue(!dollarPrice.getText().equals("") && !euroPrice.getText().equals(""));
    }

    @AfterClass
    public static void tearDown() {
        driver.close();
    }
}
