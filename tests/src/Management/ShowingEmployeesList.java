package Management;

import Reusables.GeneralReusables;
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
public class ShowingEmployeesList {
    private static WebDriver driver;

    @BeforeClass
    public static void setUp() {
        driver = new FirefoxDriver();
        GeneralReusables.setUpToHomepage(driver);
        GeneralReusables.loginAsTheManager(driver);
    }

    @Test
    public void employeesListExistsTest() {
        WebElement employee = driver.findElement(By.name("employee"));
        employee.click();
        List<WebElement> tables = driver.findElements(By.xpath("//table"));
        assertTrue(tables.size() > 0);
    }

    @AfterClass
    public static void tearDown() {
        GeneralReusables.logout(driver);
    }
}
