package Management;

import Reusables.GeneralReusables;
import Reusables.ManagerReusables;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import static junit.framework.TestCase.assertEquals;

/**
 * Created by Golpar on 4/19/2018 AD.
 */
public class RemoveAccess {
    private static WebDriver driver;

    @BeforeClass
    public static void setUp() {
        driver = new ChromeDriver();
        GeneralReusables.setUpToHomepage(driver);
        GeneralReusables.loginAsTheManager(driver);
    }

    @Test
    public void preConditionTest() {
        WebElement employee = driver.findElement(By.name("customers"));
        employee.click();
        assertEquals(driver.getTitle(), ManagerReusables.CUSTOMERS_PAGE_TITLE);
    }

    @Test
    public static void formFillingForCustomerTest(){

    }
}
