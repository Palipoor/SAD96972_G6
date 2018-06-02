package Transactions;

import Reusables.GeneralReusables;
import Reusables.ManagerReusables;
import Reusables.Order;
import Reusables.WalletUsersReusables;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Ignore;
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
public class AnonymousPayment {
    private static WebDriver driver;

    @BeforeClass
    public static void setUp() {
        driver = new FirefoxDriver();
        GeneralReusables.setUpToHomepage(driver);
        GeneralReusables.loginAsACustomer(driver);
        WebElement anonymousPayment = driver.findElement(By.name("anonymous-payment"));
        anonymousPayment.click();
    }

    @Test
    @Order(order = 1)
    public void invalidAmount() {
        WebElement amount = driver.findElement(By.name("amount"));
        amount.clear();
        amount.sendKeys("abc");

        WebElement email = driver.findElement(By.name("email"));
        email.clear();
        email.sendKeys("palipoor976@gmail.com");

        WebElement phone = driver.findElement(By.name("phone"));
        phone.clear();
        phone.sendKeys("09379605628");

        WebElement button = driver.findElement(By.name("submit-button"));
        button.click();

        WebElement error = driver.findElement(By.name("error"));
        assertEquals(error.getText(), GeneralReusables.reusableStrings.get("wrong-amount-error"));
    }

    @Test
    @Order(order = 2)
    public void invalidEmail() {
        WebElement amount = driver.findElement(By.name("amount"));
        amount.clear();
        amount.sendKeys("1000");

        WebElement email = driver.findElement(By.name("email"));
        email.clear();
        email.sendKeys("palipoor976");


        WebElement button = driver.findElement(By.name("submit-button"));
        button.click();

        WebElement error = driver.findElement(By.name("error"));
        assertEquals(error.getText(), GeneralReusables.reusableStrings.get("invalid-email-error"));
    }

    @Test
    @Order(order = 3)
    public void moreThanCredit() {
        double moreThanRialCredit = WalletUsersReusables.getWalletCredit(driver, "rial") + 200;
        String amountValue = String.valueOf(moreThanRialCredit);
        WebElement amount = driver.findElement(By.name("amount"));
        amount.clear();
        amount.sendKeys(amountValue);
        WebElement email = driver.findElement(By.name("email"));
        email.clear();
        email.sendKeys("palipoor976@gmail.com");

        WebElement phone = driver.findElement(By.name("phone"));
        phone.clear();
        phone.sendKeys("09379605628");

        WebElement button = driver.findElement(By.name("submit-button"));
        button.click();

        WebElement error = driver.findElement(By.name("error"));
        assertEquals(error.getText(), WalletUsersReusables.reusableStrings.get("not-enough-error"));
    }

    @Test
    @Order(order = 4)
    public void paymentsAreDone() { //todo hesabe maghsadam check kone

        String paymentAmount = "1000";
        double rialCredit = WalletUsersReusables.getWalletCredit(driver, "rial");
        double companyRialCredit = ManagerReusables.getCompanyCredit("rial");

        WebElement amount = driver.findElement(By.name("amount"));
        amount.clear();
        amount.sendKeys(paymentAmount);

        WebElement karmozd = driver.findElement(By.name("karmozd"));
        double karmozdValue = Double.valueOf(karmozd.getText());
        WebElement submit = driver.findElement(By.name("submit-button"));
        submit.click();

        double newRialCredit = WalletUsersReusables.getWalletCredit(driver, "rial");
        double newCompanyRialCredit = ManagerReusables.getCompanyCredit("rial");

        assertEquals(newRialCredit, rialCredit - karmozdValue - 1000.0, GeneralReusables.delta);
        assertEquals(newCompanyRialCredit, companyRialCredit + karmozdValue + 1000.0, GeneralReusables.delta);
    }

    @Test
    @Order(order = 5)
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
