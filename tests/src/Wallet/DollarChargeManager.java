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

import static org.junit.Assert.assertEquals;

/**
 * Created by Golpar on 4/27/2018 AD.
 */
public class DollarChargeManager {
    private static WebDriver driver;
    private String amount = "1";

    @BeforeClass
    public static void setUp() {
        driver = new ChromeDriver();
        GeneralReusables.setUpToHomepage(driver);
        GeneralReusables.loginAsTheManager(driver);
        WalletUsersReusables.navigateToWallet(driver, "dollar");
    }

    @Test
    public void preConditionTest() {
        String title = driver.getTitle();
        assertEquals(title, WalletUsersReusables.reusableStrings.get("dollar-wallet-title"));
    }

    @Test
    public void conversionTest() {// مبلغی که نشون می‌ده برابر با مبلغ وارد شده ضربدر قیمت دلار باشه.
        //todo ye kare behtari ba in kon
        WebElement desiredAmount = driver.findElement(By.name("desired-amount"));
        desiredAmount.sendKeys(amount);
        double dollarPrice = GeneralReusables.getPrice("dollar");
        double rial = dollarPrice * Double.valueOf(amount);
        WebElement rialAmount = driver.findElement(By.name("rial-amount"));
        double shownRial = Double.valueOf(rialAmount.getText());
        assertEquals(rial, shownRial, GeneralReusables.delta);
    }

    @Test
    public void decreaseTest() {// مبلغی که کسر می‌شه برابر با مبلغی که نشون می‌ده باشه.
        double rialCredit = WalletUsersReusables.getWalletCredit(driver, "rial");
        WebElement chargeButton = driver.findElement(By.name("charge-button"));
        WebElement rialAmount = driver.findElement(By.name("rial-amount"));
        double shownRial = Double.valueOf(rialAmount.getText());
        chargeButton.click();
        double currentRialCredit = WalletUsersReusables.getWalletCredit(driver, "rial");
        assertEquals(currentRialCredit, rialCredit - shownRial, GeneralReusables.delta);
    }

    @Test
    public void increaseTest() {// مبلغی که کسر می‌شه برابر با مبلغی که نشون می‌ده باشه.
        double dollarCredit = WalletUsersReusables.getWalletCredit(driver, "dollar");
        WebElement chargeButton = driver.findElement(By.name("charge-button"));
        Double increaseAmount = Double.valueOf(amount);
        chargeButton.click();
        double currentDollarCredit = WalletUsersReusables.getWalletCredit(driver, "dollar");
        assertEquals(currentDollarCredit, dollarCredit + increaseAmount, GeneralReusables.delta);
    }

    @Test
    public void invalidDecreaseTest() {
        double rialCredit = WalletUsersReusables.getWalletCredit(driver, "rial");
        double decreaseAmount = Math.round((rialCredit + 2) / GeneralReusables.getPrice("dollar")); // بیشتر از آن چه دارد.
        WebElement desiredAmount = driver.findElement(By.name("desired-amount"));
        desiredAmount.sendKeys(String.valueOf(decreaseAmount));
        WebElement chargeButton = driver.findElement(By.name("charge-button"));
        chargeButton.click();
        WebElement errorMessage = driver.findElement(By.name("error"));
        assertEquals(errorMessage.getText(), WalletUsersReusables.NOT_ENOUGH_ERROR_MESSAGE);// ارور خالی نباشد!
    }


    @AfterClass
    public static void tearDown() {
        GeneralReusables.logout(driver);
    }
}
