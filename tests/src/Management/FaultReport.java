package Management;

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
import org.openqa.selenium.chrome.ChromeDriver;

import static org.junit.Assert.assertTrue;

/**
 * Created by Golpar on 4/19/2018 AD.
 */
public class FaultReport {
    private static WebDriver driver;
    private static String transactionId;
    private String reason = "some random reason";
    private static String employeeUsername;

    @BeforeClass
    public static void setUp() {
        int id = CustomerReusables.createNewTransaction();
        transactionId = String.valueOf(id);
        driver = new ChromeDriver();
        GeneralReusables.setUpToHomepage(driver);
        employeeUsername = GeneralReusables.loginAsAnEmployee(driver);
    }

    @Test
    public void invalidIdTest() {
        WebElement idEntry = driver.findElement(By.id("transaction-id"));
        idEntry.clear();
        idEntry.sendKeys(GeneralReusables.INVALID_TRANSACTION_ID);
        WebElement reasonEntry = driver.findElement(By.id("why"));
        reasonEntry.sendKeys(reason);
        WebElement sendButton = driver.findElement(By.name("send"));
        sendButton.click();
        WebElement message = driver.findElement(By.name("message"));
        assertTrue(!message.getText().equals("") && message.getText().equals(GeneralReusables.WRONG_ID_ERROR));
    }

    @Test
    public void successfulReportTest() {
        WebElement idEntry = driver.findElement(By.id("transaction-id"));
        idEntry.clear();
        idEntry.sendKeys(transactionId);//todo in bayad intor she ke bere bebine che transaction haii hast yekio bardare?
        WebElement reasonEntry = driver.findElement(By.id("why"));
        reasonEntry.clear();
        reasonEntry.sendKeys(reason);
        WebElement sendButton = driver.findElement(By.name("send"));
        sendButton.click();
        WebElement message = driver.findElement(By.name("message"));
        assertTrue(!message.getText().equals("") && message.getText().equals(GeneralReusables.SUCCESSFULLY_SENT));
    }

    @Test
    public void successfulReceiveReportTest() {
        assertTrue(ManagerReusables.reportExists(transactionId, reason, employeeUsername));
    }


    @AfterClass
    public void tearDown() {
        GeneralReusables.logout(driver);
    }

}
