package Wallet;

import Reusables.GeneralReusables;
import Reusables.WalletUsersReusables;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import static org.junit.Assert.*;

/**
 * Created by Golpar on 4/12/2018 AD.
 */
public class RialChargeCustomer {

    private static WebDriver driver;

    @BeforeClass
    public static void setUp() {
        driver = new ChromeDriver();
        GeneralReusables.setUpToHomepage(driver);
        GeneralReusables.loginAsACustomer(driver);
        WalletUsersReusables.navigateToWallet(driver, "rial");
    }

    @Test
    public void preCondtionTest() {
        String title = driver.getTitle();
        assertEquals(title, WalletUsersReusables.RIAL_WALLET_TITLE);
    }

    @Test
    public void rialWalletCharge() {
        double rialCredit = WalletUsersReusables.getWalletCredit(driver, "rial");
        WebElement amountBox = driver.findElement(By.name("desired-amount"));
        amountBox.sendKeys("10000");
        WebElement chargeButton = driver.findElement(By.name("charge-button"));
        chargeButton.click();
        //todo چیزی که باید از درگاه برمی‌گرده ببینه رو این جا بنویس.
        double newRialCredit = WalletUsersReusables.getWalletCredit(driver, "rial");
        double charged = newRialCredit - rialCredit;
        assertEquals(charged, 10000.0, GeneralReusables.delta); // oon ghadri ke lazeme charge shode bashe
    }

    @AfterClass
    public static void tearDown() {
        GeneralReusables.logout(driver);
    }
}
