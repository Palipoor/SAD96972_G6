package Wallet;

import Reusables.GeneralReusables;
import Reusables.WalletUsersReusables;
import org.junit.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

/**
 * Created by Golpar on 4/12/2018 AD.
 */
public class DollarChargeCustomer {
    private WebDriver driver;
    private String amount = "1";

    @BeforeClass
    public void setUp() {
        driver = new ChromeDriver();
        GeneralReusables.setUpToHomepage(driver);
        GeneralReusables.loginAsACustomer(driver);
        WalletUsersReusables.navigateToWallet(driver, "dollar");
    }

    @Test
    public void preConditionTest() {
        String title = driver.getTitle();
        assertEquals(title, WalletUsersReusables.DOLLAR_WALLET_TITLE);
    }

    @Test
    public void conversionTest() {// مبلغی که نشون می‌ده برابر با مبلغ وارد شده ضربدر قیمت دلار باشه.
        //todo ye kare behtari ba in kon
        WebElement desiredAmount = driver.findElement(By.name("desired-amount"));
        desiredAmount.sendKeys(amount);
        int dollarPrice = GeneralReusables.getPrice("dollar");
        int rial = dollarPrice * Integer.valueOf(amount);
        WebElement rialAmount = driver.findElement(By.name("rial-amount"));
        int shownRial = Integer.valueOf(rialAmount.getText());
        assertEquals(rial, shownRial);
    }

    @Test
    public void decreaseTest() {// مبلغی که کسر می‌شه برابر با مبلغی که نشون می‌ده باشه.
        int rialCredit = WalletUsersReusables.getWalletCredit(driver, "rial");
        WebElement chargeButton = driver.findElement(By.name("charge-button"));
        WebElement rialAmount = driver.findElement(By.name("rial-amount"));
        int shownRial = Integer.valueOf(rialAmount.getText());
        chargeButton.click();
        int currentRialCredit = WalletUsersReusables.getWalletCredit(driver, "rial");
        assertEquals(currentRialCredit, rialCredit - shownRial);
    }

    @Test
    public void increaseTest() {// مبلغی که کسر می‌شه برابر با مبلغی که نشون می‌ده باشه.
        int dollarCredit = WalletUsersReusables.getWalletCredit(driver, "dollar");
        WebElement chargeButton = driver.findElement(By.name("charge-button"));
        Integer increaseAmount = Integer.valueOf(amount);
        chargeButton.click();
        int currentDollarCredit = WalletUsersReusables.getWalletCredit(driver, "dollar");
        assertEquals(currentDollarCredit, dollarCredit + increaseAmount);
    }

    @Test
    public void invalidDecreaseTest() {
        int rialCredit = WalletUsersReusables.getWalletCredit(driver, "rial");
        int decreaseAmount = Math.round((rialCredit + 2) / GeneralReusables.getPrice("dollar")); // بیشتر از آن چه دارد.
        WebElement desiredAmount = driver.findElement(By.name("desired-amount"));
        desiredAmount.sendKeys(String.valueOf(decreaseAmount));
        WebElement chargeButton = driver.findElement(By.name("charge-button"));
        chargeButton.click();
        WebElement errorMessage = driver.findElement(By.name("error"));
        assertEquals(errorMessage.getText(), WalletUsersReusables.NOT_ENOUGH_ERROR_MESSAGE);// ارور خالی نباشد!
    }


    @AfterClass
    public void tearDown() {
        GeneralReusables.logout(driver);
    }
}
