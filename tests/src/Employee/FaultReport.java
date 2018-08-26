package Employee;

import Reusables.CustomerReusables;
import Reusables.EmployeeReusables;
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

import java.util.List;

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
		CustomerReusables.createNewBankTransfer("dollar", "2");
        transactionId = ManagerReusables.getNewestTransactionId();
        driver = new FirefoxDriver();
        GeneralReusables.loginAsAnEmployeeWithoutName(driver);
    }

    @Test
    public void invalidIdTest() {
        WebElement idEntry = driver.findElement(By.name("transactionId"));
        idEntry.clear();
        idEntry.sendKeys(GeneralReusables.reusableStrings.get("invalid-transaction-id"));
        WebElement reasonEntry = driver.findElement(By.name("description"));
        reasonEntry.sendKeys(reason);
        List<WebElement> choices = driver.findElements(By.className("form-check-input"));
		WebElement report_choice = choices.get(choices.size() -1 );
		report_choice.click();

		WebElement send = driver.findElement(By.name("send"));
		send.click();
		WebElement message = driver.findElement(By.name("error"));
		assertTrue(!message.getText().equals("") && message.getText().equals(GeneralReusables.reusableStrings.get("wrong-id-error")));
    }

    @Test
    public void successfulReportTest() {
		WebElement idEntry = driver.findElement(By.name("transactionId"));
		idEntry.clear();
		idEntry.sendKeys(transactionId);
		WebElement reasonEntry = driver.findElement(By.name("description"));
		reasonEntry.sendKeys(reason);
		WebElement report = driver.findElement(By.id("id_action_2"));
		report.click();

		WebElement send = driver.findElement(By.name("send"));
		send.click();
		GeneralReusables.waitForSeconds(2);
        WebElement message = driver.findElement(By.name("message"));
        assertTrue(!message.getText().equals("") && message.getText().equals("بررسی تراکنش با موفقیت انجام شد."));
		assertTrue(ManagerReusables.getTransactionStatus(transactionId).equals(EmployeeReusables.REPORT));

	}


    @AfterClass
    public static void tearDown() {
        GeneralReusables.logout(driver);
    }

}
