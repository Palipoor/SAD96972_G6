package Transactions;

import Reusables.*;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import static junit.framework.TestCase.assertEquals;

/**
 * Created by Golpar on 5/2/2018 AD.
 */
@RunWith(Reusables.OrderedRunner.class)
public class ReverseCharge {
    private static WebDriver driver;

    @BeforeClass
    public static void setUp() {
        driver = new FirefoxDriver();
        GeneralReusables.setUpToHomepage(driver);
        GeneralReusables.loginAsACustomer(driver);
        WebElement reverseCharge = driver.findElement(By.name("reverse-charge"));
        reverseCharge.click();
    }

    @Test
    @Order(order = 1)
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
    @Order(order = 2)
    public void moreThanCredit() {
        double moreThanRialCredit = WalletUsersReusables.getWalletCredit(driver, "rial") + 200;
        String amountValue = String.valueOf(moreThanRialCredit);
        WebElement amount = driver.findElement(By.name("amount"));
        amount.clear();
        amount.sendKeys(amountValue);

        WebElement submit = driver.findElement(By.name("submit-button"));
        submit.click();


        WebElement error = driver.findElement(By.name("error"));
        assertEquals(error.getText(), WalletUsersReusables.reusableStrings.get("not-enough-error"));
    }

    @Test
    @Order(order = 3)
    public void paymentsAreDone(){
        double rialCredit = WalletUsersReusables.getWalletCredit(driver, "rial");
        double lessThanCredit = rialCredit - 1;
        String amountValue = String.valueOf(lessThanCredit);
        WebElement amount = driver.findElement(By.name("amount"));
        amount.clear();
        amount.sendKeys(amountValue);

        WebElement submit = driver.findElement(By.name("submit-button"));
        submit.click();

        double newRialCredit = WalletUsersReusables.getWalletCredit(driver, "rial");

        assertEquals(newRialCredit, rialCredit - lessThanCredit, GeneralReusables.delta);
    }

    @AfterClass
    public static void tearDown(){
        GeneralReusables.logout(driver);
    }
}
