package Management;

import Reusables.CustomerReusables;
import Reusables.EmployeeReusables;
import Reusables.GeneralReusables;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.List;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by Golpar on 4/19/2018 AD.
 */
public class SeeFaultReports { // اول باید یک تراکنش درخواست بشه. بعد باید فالتش گزارش بشه بعد باید این ور دیده بشه!
    private WebDriver driver;
    private String transactionId;
    private String reporterUsername;

    @BeforeClass
    public void setUp() {
        int id = CustomerReusables.createNewTransaction();
        transactionId = String.valueOf(id);
        reporterUsername = EmployeeReusables.reportFault(transactionId);

        driver = new ChromeDriver();
        GeneralReusables.setUpToHomepage(driver);
        GeneralReusables.loginAsTheManager(driver);
    }

    @Test
    public void reportExistsTest() {
        WebElement reportsTable = driver.findElement(By.name("reports"));

        WebElement searchBox = reportsTable.findElement(By.name("شناسه تراکنش"));
        searchBox.click();
        searchBox.clear();
        searchBox.sendKeys(transactionId);

        List<WebElement> rows = reportsTable.findElements(By.xpath("//tbody//tr"));
        assertNotEquals(rows.size(), 0);
    }

    @Test
    public void reportIsValidTest() {

        WebElement reportsTable = driver.findElement(By.name("reports"));

        WebElement searchBox = reportsTable.findElement(By.name("شناسه تراکنش"));
        searchBox.click();
        searchBox.clear();
        searchBox.sendKeys(transactionId);

        List<WebElement> titleRows = reportsTable.findElements(By.xpath("//thead//tr"));
        List<WebElement> headers = titleRows.get(0).findElements(By.tagName("//th"));
        int idIndex = 0, usernameIndex = 0;


        for (int i = 0; i < headers.size(); i++) {
            if (headers.get(i).getText().equals("شناسه تراکنش")) {
                idIndex = i;
            } else if (headers.get(i).getText().equals("گزارش دهنده")) {
                usernameIndex = i;
            }
        }
        List<WebElement> rows = reportsTable.findElements(By.xpath("//tbody//tr"));
        WebElement report = rows.get(0);

        List<WebElement> reportDetails = report.findElements(By.tagName("td"));
        assertTrue(reportDetails.get(idIndex).getText().equals(transactionId) && reportDetails.get(usernameIndex).getText().equals(reporterUsername));
    }


    @Test
    public void tearDown() {
        GeneralReusables.logout(driver);
    }
}
