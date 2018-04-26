package Nonmembers;

import Reusables.GeneralReusables;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import static org.junit.Assert.assertTrue;

/**
 * Created by Golpar on 4/26/2018 AD.
 */
public class Laws {

    private static WebDriver driver;

    @BeforeClass
    public static void setUp() {

        driver = new ChromeDriver();
        GeneralReusables.setUpToHomepage(driver);
        WebElement laws = driver.findElement(By.name("law"));
        laws.click();
    }

    @Test
    public void containsText() {
        WebElement title = driver.findElement(By.name("laws-header"));
        WebElement content = driver.findElement(By.name("laws-content"));
        assertTrue(!title.getText().equals("") && !content.getText().equals(""));
    }

    @AfterClass
    public static void tearDown() {
        driver.close();
    }
}
