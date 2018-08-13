package Wallet;

import Reusables.GeneralReusables;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

/**
 * Created by Golpar on 4/27/2018 AD.
 */
public class WalletExistenceManager {

    private static WebDriver driver;

    @BeforeClass
    public static void setUp() {
        driver = new ChromeDriver();
        GeneralReusables.setUpToHomepage(driver);
        GeneralReusables.loginAsTheManager(driver);
    }

    @Test
    public void preConditionTest() {
        String title = driver.getTitle();
        assertEquals(title, GeneralReusables.PANEL_TITLE);
    }

    @Test
    public void rialWalletExistenceTest() {
        List<WebElement> rialWallets = driver.findElements(By.name("rial-wallet"));
        assertNotEquals(rialWallets.size(), 0);// exists such element in the page
    }

    @Test
    public void dollarWalletExistenceTest() {
        List<WebElement> dollarWallets = driver.findElements(By.name("dollar-wallet"));
        assertNotEquals(dollarWallets.size(), 0);// exists such element in the page
    }

    @Test
    public void euroWalletExistenceTest() {
        List<WebElement> euroWallets = driver.findElements(By.name("euro-wallet"));
        assertNotEquals(euroWallets.size(), 0);// exists such element in the page
    }

    @AfterClass
    public static void tearDown() {
        GeneralReusables.logout(driver);
    }

}
