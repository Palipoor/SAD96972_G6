package Management;

import Reusables.CustomerReusables;
import Reusables.EmployeeReusables;
import Reusables.GeneralReusables;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.util.List;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by Golpar on 4/19/2018 AD.
 */
public class ShowingFaultReports {
    private static WebDriver driver;

    @BeforeClass
    public static void setUp() {
        driver = new FirefoxDriver();
        GeneralReusables.setUpToHomepage(driver);
        GeneralReusables.loginAsTheManager(driver);
    }

    @Test
    public void reportExistsTest() {
        List<WebElement> tables = driver.findElements(By.xpath("//table"));
        boolean isThereAnyReportsTable = false;
        for (WebElement table : tables) {
            if (table.getAttribute("name").equals("reports")) {
                isThereAnyReportsTable = true;
            }
        }

        assertTrue(isThereAnyReportsTable);
    }

    @Test
    public static void tearDown() {
        GeneralReusables.logout(driver);
    }
}
