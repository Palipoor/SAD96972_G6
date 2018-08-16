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

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by Golpar on 4/26/2018 AD.
 */
public class History {
    private static WebDriver driver;

    @BeforeClass
    public static void setUp() {

        driver = new FirefoxDriver();
        GeneralReusables.setUpToHomepage(driver);
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0,1500)");
		GeneralReusables.waitForSeconds(3);
		WebElement history = driver.findElement(By.name("history"));
        history.click();
		GeneralReusables.waitForSeconds(1);
	}

    @Test
    public void containsText() {
        WebElement title = driver.findElement(By.name("history-header"));
        WebElement content = driver.findElement(By.name("history-content"));
		System.out.printf(content.getText());
		assertFalse(title.getText().equals(""));
        assertFalse(content.getText().equals(""));
    }

    @AfterClass
    public static void tearDown() {
        driver.close();
    }
}
