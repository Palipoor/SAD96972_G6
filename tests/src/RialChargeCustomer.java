import ReUsables.GeneralReusables;
import ReUsables.WalletUsersReusables;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.*;

/**
 * Created by Golpar on 4/12/2018 AD.
 */
public class RialChargeCustomer {

    private WebDriver driver;

    @BeforeClass
    public void setUp() {
        GeneralReusables.setUpToHomepage(driver);
        GeneralReusables.loginAsACustomer(driver);
        WalletUsersReusables.navigateToRialWallet(driver);
    }

    @Test
    public void postCondtionTest() {
        String title = driver.getTitle();
        assertEquals(title, WalletUsersReusables.RIAL_WALLET_TITLE);
    }

    @Test
    public void rialWalletCharge() {
        int rialCredit = WalletUsersReusables.getWalletCredit(driver,"rial");
        WebElement amountBox = driver.findElement(By.name("desired-amount"));
        amountBox.sendKeys("10000");
        WebElement chargeButton = driver.findElement(By.name("charge-button"));
        chargeButton.click();
        //todo چیزی که باید از درگاه برمی‌گرده ببینه رو این جا بنویس.
        int newRialCredit = WalletUsersReusables.getWalletCredit(driver,"rial");
        int charged = newRialCredit - rialCredit;
        assertEquals(charged,10000); // oon ghadri ke lazeme charge shode bashe
    }

    @AfterClass
    public void tearDown() {
        GeneralReusables.logout(driver);
    }
}
