package Wallet;

import Reusables.GeneralReusables;
import Reusables.WalletUsersReusables;
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
 * Created by Golpar on 4/13/2018 AD.
 */
public class WalletCreditCustomer {
    private WebDriver driver;

    @BeforeClass
    public void setUp() {
        driver = new ChromeDriver();
        GeneralReusables.setUpToHomepage(driver);
        GeneralReusables.loginAsACustomer(driver);
    }

    @Test
    public void preCondtionTest() {
        String title = driver.getTitle();
        assertEquals(title, WalletUsersReusables.RIAL_WALLET_TITLE);
    }

    @Test
    public void rialWalletCreditTest() {
        WalletUsersReusables.navigateToWallet(driver, "rial");
        List<WebElement> credits = driver.findElements(By.name("credit"));
        assertNotEquals(credits.size(), 0);
    }

    @Test
    public void rialWalletCreditTextTest() {
        WalletUsersReusables.navigateToWallet(driver, "rial");
        WebElement credit = driver.findElement(By.name("credit"));
        assertNotEquals(credit.getText(), "");
    }

    @Test
    public void dollarWalletCreditTest() {
        WalletUsersReusables.navigateToWallet(driver, "dollar");
        List<WebElement> credits = driver.findElements(By.name("credit"));
        assertNotEquals(credits.size(), 0);
    }

    @Test
    public void dollarWalletCreditTextTest() {
        WalletUsersReusables.navigateToWallet(driver, "dollar");
        WebElement credit = driver.findElement(By.name("credit"));
        assertNotEquals(credit.getText(), "");
    }

    @Test
    public void euroWalletCreditTest() {
        WalletUsersReusables.navigateToWallet(driver, "euro");
        List<WebElement> credits = driver.findElements(By.name("credit"));
        assertNotEquals(credits.size(), 0);
    }

    @Test
    public void euroWalletCreditTextTest() {
        WalletUsersReusables.navigateToWallet(driver, "euro");
        WebElement credit = driver.findElement(By.name("credit"));
        assertNotEquals(credit.getText(), "");
    }
}
