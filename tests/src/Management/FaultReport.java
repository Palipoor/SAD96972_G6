package Management;

import Reusables.GeneralReusables;
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
    private WebDriver driver;

    @BeforeClass
    public void setUp() {
        driver = new ChromeDriver();
        GeneralReusables.setUpToHomepage(driver);
        GeneralReusables.loginAsAnEmployee(driver);
    }

    @Test
    public void invalidIdTest() {
        WebElement idEntry = driver.findElement(By.id("transaction-id"));
        idEntry.sendKeys(GeneralReusables.INVALID_TRANSACTION_ID);
        WebElement reasonEntry = driver.findElement(By.id("why"));
        reasonEntry.sendKeys("some random reason");
        WebElement sendButton = driver.findElement(By.name("send"));
        sendButton.click();
        WebElement message = driver.findElement(By.name("message"));
        assertTrue(!message.getText().equals("") && message.getText().equals(GeneralReusables.WRONG_ID_ERROR));
    }

    @Test
    public void successfulReportTest() {
        WebElement idEntry = driver.findElement(By.id("transaction-id"));
        idEntry.sendKeys("1");//todo in bayad intor she ke bere bebine che transaction haii hast yekio bardare.
        WebElement reasonEntry = driver.findElement(By.id("why"));
        reasonEntry.sendKeys("some random reason");
        WebElement sendButton = driver.findElement(By.name("send"));
        sendButton.click();
        WebElement message = driver.findElement(By.name("message"));
        assertTrue(!message.getText().equals("") && message.getText().equals(GeneralReusables.SUCCESSFULLY_SENT));
    }

    @AfterClass
    public void tearDown() {
        GeneralReusables.logout(driver);
    }

}
