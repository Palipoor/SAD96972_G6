package Management;

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
 * Created by Golpar on 4/19/2018 AD.
 */
public class SeeEmployeesList {
    private static WebDriver driver;

    @BeforeClass
    public static void setUp() {
        driver = new ChromeDriver();
        GeneralReusables.setUpToHomepage(driver);
        GeneralReusables.loginAsTheManager(driver);
    }

    @Test
    public void employeesListExistsTest() {
        List<WebElement> tables = driver.findElements(By.xpath("//table"));
        boolean isthereAnyEmployeesTable = false;
        for (WebElement table : tables) {
            if (table.getAttribute("name").equals("employees")) {
                isthereAnyEmployeesTable = true;
            }
        }
        assertTrue(isthereAnyEmployeesTable);
    }

    @Test
    public void tearDown() {
        GeneralReusables.logout(driver);
    }
}
