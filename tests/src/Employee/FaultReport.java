package Employee;

import Reusables.CustomerReusables;
import Reusables.GeneralReusables;
import Reusables.ManagerReusables;
import com.sun.source.tree.AssertTree;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import static org.junit.Assert.assertTrue;

/**
 * Created by Golpar on 4/19/2018 AD.
 */
public class FaultReport {
    private static WebDriver driver;
    private static String transactionId;
    private static String reason = "some random reason";
    private static String employeeUsername;

    @BeforeClass
    public static void setUp() {
        transactionId = CustomerReusables.createNewTransaction();
        driver = new FirefoxDriver();
        GeneralReusables.setUpToHomepage(driver);
        driver.close();
        employeeUsername = GeneralReusables.loginAsAnEmployee(driver);
    }

    @Test
    public void invalidIdTest() {
        WebElement idEntry = driver.findElement(By.id("transaction-id"));
        idEntry.clear();
        idEntry.sendKeys(GeneralReusables.reusableStrings.get("invalid-transaction-id"));
        WebElement reasonEntry = driver.findElement(By.id("why"));
        reasonEntry.sendKeys(reason);
        WebElement sendButton = driver.findElement(By.name("send"));
        sendButton.click();
        WebElement message = driver.findElement(By.name("message"));
        assertTrue(!message.getText().equals("") && message.getText().equals(GeneralReusables.reusableStrings.get("wrong-id-error")));
    }

    @Test
    public void successfulReportTest() {
        WebElement idEntry = driver.findElement(By.id("transaction-id"));
        idEntry.clear();
        idEntry.sendKeys(transactionId);
        WebElement reasonEntry = driver.findElement(By.id("why"));
        reasonEntry.clear();
        reasonEntry.sendKeys(reason);
        WebElement sendButton = driver.findElement(By.name("send"));
        sendButton.click();
        WebElement message = driver.findElement(By.name("message"));
        assertTrue(!message.getText().equals("") && message.getText().equals(GeneralReusables.reusableStrings.get("successfully-sent")));
    }

    @Test
    public void successfulReceiveReportTest() {
        assertTrue(ManagerReusables.reportExists(transactionId, reason, employeeUsername) && ManagerReusables.getTransactionStatus(transactionId).equals(GeneralReusables.reusableStrings.get("reported-transaction")));
    }


    @AfterClass
    public static void tearDown() {
        GeneralReusables.logout(driver);
    }

}
