package Transactions;

import Reusables.*;
import junit.framework.Assert;
import Reusables.WalletUsersReusables;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import static junit.framework.TestCase.assertEquals;

/**
 * Created by Golpar on 5/2/2018 AD.
 */
@RunWith(Reusables.OrderedRunner.class)
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
    @Order(order = 1)
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
        password.sendKeys("mypassword");

        WebElement submit = driver.findElement(By.name("submit-button"));
        submit.click();

        WebElement error = driver.findElement(By.name("amount-error"));
        assertEquals(error.getText(), GeneralReusables.reusableStrings.get("invalid-amount-error"));

    }

    @Test
    @Order(order = 2)
    public void moreThanCredit() {
        double moreThanDollarCredit = WalletUsersReusables.getWalletCredit(driver, "dollar") + 3;
        String paymentAmount = String.valueOf(moreThanDollarCredit);

        WebElement amount = driver.findElement(By.name("amount"));
        amount.clear();
        amount.sendKeys(paymentAmount);

        WebElement submit = driver.findElement(By.name("submit-button"));
        submit.click();

        WebElement error = driver.findElement(By.name("error"));
        assertEquals(error.getText(), WalletUsersReusables.reusableStrings.get("not-enough-error"));
    }

    @Test
    @Order(order = 3)
    public void paymentsAreDone() {

        String paymentAmount = "1";
        double dollarCredit = WalletUsersReusables.getWalletCredit(driver, "dollar");
        double rialCredit = WalletUsersReusables.getWalletCredit(driver, "rial");
        double companyRialCredit = ManagerReusables.getCompanyCredit("rial");
        double companyDollarCredit = ManagerReusables.getCompanyCredit("dollar");

        WebElement amount = driver.findElement(By.name("amount"));
        amount.clear();
        amount.sendKeys(paymentAmount);

        WebElement karmozd = driver.findElement(By.name("karmozd"));
        double karmozdValue = Double.valueOf(karmozd.getText());
        WebElement submit = driver.findElement(By.name("submit-button"));
        submit.click();

        double newDollarCredit = WalletUsersReusables.getWalletCredit(driver, "dollar");
        double newRialCredit = WalletUsersReusables.getWalletCredit(driver, "rial");
        double newCompanyRialCredit = ManagerReusables.getCompanyCredit("rial");
        double newCompanyDollarCredit = ManagerReusables.getCompanyCredit("dollar");

        assertEquals(newDollarCredit, dollarCredit - 1.0, GeneralReusables.delta);
        assertEquals(newRialCredit, rialCredit - karmozdValue, GeneralReusables.delta);
        assertEquals(newCompanyDollarCredit, companyDollarCredit + 1.0, GeneralReusables.delta);
        assertEquals(newCompanyRialCredit, companyRialCredit + karmozdValue, GeneralReusables.delta);

    }

    @Test
    @Order(order = 4)
    public void transactionIsAdded() {
        String myUsername = GeneralReusables.getUsername(driver);
        String otherUsername = ManagerReusables.getTransactionsCustomerUsername(ManagerReusables.getNewestTransactionId());

        assertEquals(otherUsername, myUsername);
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
