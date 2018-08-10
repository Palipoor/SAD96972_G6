package Transactions;

import Reusables.GeneralReusables;
import Reusables.WalletUsersReusables;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import static junit.framework.TestCase.assertEquals;

/**
 * Created by Golpar on 5/2/2018 AD.
 */
public class ReverseCharge {
    private static WebDriver driver;

    @BeforeClass
    public static void setUp() {
        driver = new ChromeDriver();
        GeneralReusables.setUpToHomepage(driver);
        GeneralReusables.loginAsACustomer(driver);
        WebElement reverseCharge = driver.findElement(By.name("reverse-charge"));
        reverseCharge.click();
    }

    @Test
    public void invalidAmount() {
        WebElement amount = driver.findElement(By.name("amount"));
        amount.clear();
        amount.sendKeys("abc");

        WebElement submit = driver.findElement(By.name("submit-button"));
        submit.click();

        WebElement error = driver.findElement(By.name("amount-error"));
        assertEquals(error.getText(), GeneralReusables.reusableStrings.get("invalid-amount-error"));
    }

    @Test
    public void moreThanCredit() {
        double moreThanRialCredit = WalletUsersReusables.getWalletCredit(driver, "rial") + 200;
        String amountValue = String.valueOf(moreThanRialCredit);
        WebElement amount = driver.findElement(By.name("amount"));
        amount.clear();
        amount.sendKeys(amountValue);

        WebElement error = driver.findElement(By.name("error"));
        assertEquals(error.getText(), WalletUsersReusables.reusableStrings.get("not-enough-error"));
    }

    @Test
    public void paymentsAreDone() {

    }
}
