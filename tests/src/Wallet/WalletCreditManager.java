package Wallet;

import Reusables.GeneralReusables;
import Reusables.Order;
import Reusables.WalletUsersReusables;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

/**
 * Created by Golpar on 4/27/2018 AD.
 */
@RunWith(Reusables.OrderedRunner.class)
public class WalletCreditManager {
    private static WebDriver driver;

    @BeforeClass
    public static void setUp() {
        driver = new FirefoxDriver();
        GeneralReusables.setUpToHomepage(driver);
        GeneralReusables.loginAsTheManager(driver);
    }

    @Test
    @Order(order = 1)
    public void preCondtionTest() {
        String title = driver.getTitle();
        assertEquals(title, WalletUsersReusables.reusableStrings.get("rial-wallet-title"));
    }

    @Test
    @Order(order = 2)
    public void rialWalletCreditTest() {
        WalletUsersReusables.navigateToWallet(driver, "rial");
        List<WebElement> credits = driver.findElements(By.name("credit"));
        assertNotEquals(credits.size(), 0);
    }

    @Test
    @Order(order = 3)
    public void rialWalletCreditTextTest() {
        WalletUsersReusables.navigateToWallet(driver, "rial");
        WebElement credit = driver.findElement(By.name("credit"));
        assertNotEquals(credit.getText(), "");
    }

    @Test
    @Order(order = 4)
    public void dollarWalletCreditTest() {
        WalletUsersReusables.navigateToWallet(driver, "dollar");
        List<WebElement> credits = driver.findElements(By.name("credit"));
        assertNotEquals(credits.size(), 0);
    }

    @Test
    @Order(order = 5)
    public void dollarWalletCreditTextTest() {
        WalletUsersReusables.navigateToWallet(driver, "dollar");
        WebElement credit = driver.findElement(By.name("credit"));
        assertNotEquals(credit.getText(), "");
    }

    @Test
    @Order(order = 6)
    public void euroWalletCreditTest() {
        WalletUsersReusables.navigateToWallet(driver, "euro");
        List<WebElement> credits = driver.findElements(By.name("credit"));
        assertNotEquals(credits.size(), 0);
    }

    @Test
    @Order(order = 7)
    public void euroWalletCreditTextTest() {
        WalletUsersReusables.navigateToWallet(driver, "euro");
        WebElement credit = driver.findElement(By.name("credit"));
        assertNotEquals(credit.getText(), "");
    }

    @AfterClass
    public static void tearDown() {
        GeneralReusables.logout(driver);
    }
}
