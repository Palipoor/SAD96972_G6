package Employee;

import Reusables.GeneralReusables;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by Golpar on 4/26/2018 AD.
 */
public class ShowingTransactions {
    private static WebDriver driver;

    @BeforeClass
    public static void setUp() {
        driver = new FirefoxDriver();
        GeneralReusables.setUpToHomepage(driver);
        GeneralReusables.loginAsAnEmployeeWithoutName(driver);
    }

    @Test
    public void tableExistsTest() {
        List<WebElement> tables = driver.findElements(By.xpath("//table"));
        boolean isTherAnyTransactionsTable = false;
        for (WebElement table : tables) {
            if (table.getAttribute("name").equals("transactions-table")) {
                isTherAnyTransactionsTable = true;
            }
        }
        assertTrue(isTherAnyTransactionsTable);
    }

    @AfterClass
    public static void tearDown(){
        GeneralReusables.logout(driver);
    }
}
