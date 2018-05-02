package Transactions;

import Reusables.GeneralReusables;
import Reusables.WalletUsersReusables;
import org.junit.AfterClass;
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
public class ApplicationFee {
    private static WebDriver driver;

    @BeforeClass
    public static void setUp() {
        driver = new ChromeDriver();
        GeneralReusables.setUpToHomepage(driver);
        GeneralReusables.loginAsACustomer(driver);
        WebElement applicationFee = driver.findElement(By.name("application-fee"));
        applicationFee.click();
    }

    @Test
    public void invalidAmount() {
        WebElement universityName = driver.findElement(By.name("university-name"));
        universityName.clear();
        universityName.sendKeys("ETHZ");

        WebElement amount = driver.findElement(By.name("amount"));
        amount.clear();
        amount.sendKeys("abc");

        WebElement currency = driver.findElement(By.name("dollar-radio"));
        currency.click();

        WebElement link = driver.findElement(By.name("link"));
        link.clear();
        link.sendKeys("www.ethz.ch");

        WebElement username = driver.findElement(By.name("username"));
        username.clear();
        username.sendKeys("myusername");

        WebElement password = driver.findElement(By.name("password"));
        password.clear();
        password.sendKeys("myusername");

        WebElement submit = driver.findElement(By.name("submit-button"));
        submit.click();

        WebElement error = driver.findElement(By.name("amount-error"));
        assertEquals(error.getText(), GeneralReusables.reusableStrings.get("wrong-amount-error"));
    }

    @Test
    public void moreThanCredit() {
        int moreThanDollarCredit = WalletUsersReusables.getWalletCredit(driver, "dollar") + 3;
        String paymentAmount = String.valueOf(moreThanDollarCredit);

        WebElement universityName = driver.findElement(By.name("university-name"));
        universityName.clear();
        universityName.sendKeys("ETHZ");

        WebElement amount = driver.findElement(By.name("amount"));
        amount.clear();
        amount.sendKeys(paymentAmount);

        WebElement currency = driver.findElement(By.name("dollar-radio"));
        currency.click();

        WebElement link = driver.findElement(By.name("link"));
        link.clear();
        link.sendKeys("www.ethz.ch");

        WebElement username = driver.findElement(By.name("username"));
        username.clear();
        username.sendKeys("myusername");

        WebElement password = driver.findElement(By.name("password"));
        password.clear();
        password.sendKeys("myusername");

        WebElement submit = driver.findElement(By.name("submit-button"));
        submit.click();

        WebElement error = driver.findElement(By.name("error"));
        assertEquals(error.getText(), WalletUsersReusables.reusableStrings.get("not-enough-error"));
    }

    @Test
    public void transactionIsAdded() {

        String paymentAmount = "1";

        WebElement universityName = driver.findElement(By.name("university-name"));
        universityName.clear();
        universityName.sendKeys("ETHZ");

        WebElement amount = driver.findElement(By.name("amount"));
        amount.clear();
        amount.sendKeys(paymentAmount);

        WebElement currency = driver.findElement(By.name("dollar-radio"));
        currency.click();

        WebElement link = driver.findElement(By.name("link"));
        link.clear();
        link.sendKeys("www.ethz.ch");

        WebElement username = driver.findElement(By.name("username"));
        username.clear();
        username.sendKeys("myusername");

        WebElement password = driver.findElement(By.name("password"));
        password.clear();
        password.sendKeys("myusername");

        WebElement submit = driver.findElement(By.name("submit-button"));
        submit.click();
    }

    @AfterClass
    public static void tearDown() {
        GeneralReusables.logout(driver);
    }
}