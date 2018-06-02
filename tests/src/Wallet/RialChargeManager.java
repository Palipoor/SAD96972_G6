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

import static org.junit.Assert.assertEquals;

/**
 * Created by Golpar on 4/27/2018 AD.
 */
@RunWith(Reusables.OrderedRunner.class)
public class RialChargeManager {
    private static WebDriver driver;

    @BeforeClass
    public static void setUp() {
        driver = new FirefoxDriver();
        GeneralReusables.setUpToHomepage(driver);
        GeneralReusables.loginAsTheManager(driver);
        WalletUsersReusables.navigateToWallet(driver, "rial");
    }

    @Test
    @Order(order=1)
    public void preCondtionTest() {
        String title = driver.getTitle();
        assertEquals(title, WalletUsersReusables.reusableStrings.get("rial-wallet-title"));
    }

    @Test
    @Order(order=2)
    public void rialWalletCharge() {
        double rialCredit = WalletUsersReusables.getWalletCredit(driver, "rial");
        WebElement amountBox = driver.findElement(By.name("desired-amount"));
        amountBox.sendKeys("10000");
        WebElement chargeButton = driver.findElement(By.name("charge-button"));
        chargeButton.click();
        double newRialCredit = WalletUsersReusables.getWalletCredit(driver, "rial");
        double charged = newRialCredit - rialCredit;
        assertEquals(charged, 10000, GeneralReusables.delta); // oon ghadri ke lazeme charge shode bashe
    }

    @AfterClass
    public static void tearDown() {
        GeneralReusables.logout(driver);
    }
}
