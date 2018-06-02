package Wallet;

import Reusables.GeneralReusables;
import Reusables.WalletUsersReusables;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import static org.junit.Assert.assertEquals;

/**
 * Created by Golpar on 4/12/2018 AD.
 */
public class EuroChargeCustomer {
    private static WebDriver driver;
    private static String amount = "1";

    @BeforeClass
    public static void setUp() {
        driver = new FirefoxDriver();
        GeneralReusables.setUpToHomepage(driver);
        GeneralReusables.loginAsACustomer(driver);
        WalletUsersReusables.navigateToWallet(driver, "euro");
    }

    @Test
    public void preConditionTest() {
        String title = driver.getTitle();
        assertEquals(title, WalletUsersReusables.reusableStrings.get("euro-wallet-title"));
    }

    @Test
    public void conversionTest() {// مبلغی که نشون می‌ده برابر با مبلغ وارد شده ضربدر قیمت دلار باشه.
        WebElement desiredAmount = driver.findElement(By.name("desired-amount"));
        desiredAmount.sendKeys(amount);
        double euroPrice = GeneralReusables.getPrice("euro");
        double rial = euroPrice * Double.valueOf(amount);
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
        double euroCredit = WalletUsersReusables.getWalletCredit(driver, "euro");
        WebElement chargeButton = driver.findElement(By.name("charge-button"));
        Double increaseAmount = Double.valueOf(amount);
        chargeButton.click();
        double currenteuroCredit = WalletUsersReusables.getWalletCredit(driver, "euro");
        assertEquals(currenteuroCredit, euroCredit + increaseAmount, GeneralReusables.delta);
    }

    @Test
    public void invalidDecreaseTest() {
        double rialCredit = WalletUsersReusables.getWalletCredit(driver, "rial");
        double decreaseAmount = Math.round((rialCredit + 2) / GeneralReusables.getPrice("euro")); // بیشتر از آن چه دارد.
        WebElement desiredAmount = driver.findElement(By.name("desired-amount"));
        desiredAmount.sendKeys(String.valueOf(decreaseAmount));
        WebElement chargeButton = driver.findElement(By.name("charge-button"));
        chargeButton.click();
        WebElement errorMessage = driver.findElement(By.name("error"));
        assertEquals(errorMessage.getText(), WalletUsersReusables.reusableStrings.get("not-enough-error"));// ارور خالی نباشد!
    }


    @AfterClass
    public static void tearDown() {
        GeneralReusables.logout(driver);
    }
}
