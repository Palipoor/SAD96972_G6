package Transactions;

import Reusables.CustomerReusables;
import Reusables.GeneralReusables;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.List;

import static org.junit.Assert.assertTrue;

/**
 * Created by Golpar on 4/27/2018 AD.
 */
public class ShowingRequestsManager {
    private static WebDriver driver;

    @BeforeClass
    public static void setUp() {
        driver = new ChromeDriver();
        GeneralReusables.setUpToHomepage(driver);
        GeneralReusables.loginAsTheManager(driver);
        CustomerReusables.createNewTransaction();
    }

    @Test
    public void requestsListExistsTest() {
        WebElement transactionsTable = driver.findElement(By.name("transactions-table"));
        WebElement searchBox = transactionsTable.findElement(By.name("وضعیت"));
        searchBox.clear();
        searchBox.sendKeys("در انتظار");
        List<WebElement> tableRows = transactionsTable.findElements(By.xpath("//tbody//tr"));

        assertTrue(tableRows.size() > 0);
    }

    @Test
    public static void tearDown() {
        GeneralReusables.logout(driver);
    }
}
