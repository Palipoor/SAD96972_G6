package Transactions;

import Reusables.GeneralReusables;
import Reusables.ManagerReusables;
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
public class ForeignPayment {
    private static WebDriver driver;

    @BeforeClass
    public static void setUp() {
        driver = new ChromeDriver();
        GeneralReusables.setUpToHomepage(driver);
        GeneralReusables.loginAsACustomer(driver);
        WebElement foreignPayment = driver.findElement(By.name("foreign-payment"));
        foreignPayment.click();
    }

    @Test
    public void invalidAmount() {
        WebElement amount = driver.findElement(By.name("amount"));
        amount.clear();
        amount.sendKeys("abc");

        WebElement currency = driver.findElement(By.name("dollar-radio"));
        currency.click();

        WebElement accountNumber = driver.findElement(By.name("destination"));
        accountNumber.clear();
        accountNumber.sendKeys("1234567890");

        WebElement submit = driver.findElement(By.name("submit-button"));
        submit.click();

        WebElement error = driver.findElement(By.name("amount-error"));
        assertEquals(error.getText(), GeneralReusables.reusableStrings.get("invalid-amount-error"));
    }

    @Test
    public void invalidDestination() {
        WebElement amount = driver.findElement(By.name("amount"));
        amount.clear();
        amount.sendKeys("10");

        WebElement currency = driver.findElement(By.name("dollar-radio"));
        currency.click();

        WebElement accountNumber = driver.findElement(By.name("destination"));
        accountNumber.clear();
        accountNumber.sendKeys("1");

        WebElement submit = driver.findElement(By.name("submit-button"));
        submit.click();

        WebElement error = driver.findElement(By.name("amount-error"));
        assertEquals(error.getText(), GeneralReusables.reusableStrings.get("invalid-account-number-error"));
    }

    @Test
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
    public void transactionIsAdded() {
        String myUsername = GeneralReusables.getUsername(driver);
        String otherUsername = ManagerReusables.getTransactionsCustomerUsername(ManagerReusables.getNewestTransactionId());

        assertEquals(otherUsername, myUsername);
    }

    @AfterClass
    public static void tearDown() {
        GeneralReusables.logout(driver);
    }
}
