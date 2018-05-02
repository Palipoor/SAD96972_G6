package Employee;

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
 * Created by Golpar on 4/26/2018 AD.
 */
public class ShowingRequests {
    private static WebDriver driver;

    @BeforeClass
    public static void setUp() {
        driver = new ChromeDriver();
        GeneralReusables.setUpToHomepage(driver);
        GeneralReusables.loginAsTheManager(driver);
    }

    @Test
    public void employeesListExistsTest() {
        WebElement transactionsTable = driver.findElement(By.name("transactions-table"));
        WebElement searchBox = transactionsTable.findElement(By.name("وضعیت"));
        searchBox.clear();
        searchBox.sendKeys("");
    }

    @Test
    public void tearDown() {
        GeneralReusables.logout(driver);
    }
}
