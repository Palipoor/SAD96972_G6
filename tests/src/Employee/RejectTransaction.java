package Employee;


import Reusables.EmployeeReusables;
import Reusables.GeneralReusables;

import Reusables.ManagerReusables;
import Reusables.Order;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(Reusables.OrderedRunner.class)
public class RejectTransaction {
    static WebDriver driver;
    String transactionDetailTitle= "پنل مدیریت | جزئیات تراکنش";




    @BeforeClass
    public static void setUp() {
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        GeneralReusables.setUpToHomepage(driver);
        GeneralReusables.loginAsAnEmployee(driver);

    }

    @Test
    @Order(order = 1)
    public void preConditionTest() {
        WebElement cell = ManagerReusables.getNewestRequest(driver);
        WebElement link = cell.findElement(By.tagName("a"));
        link.click();
        assertEquals(driver.getTitle(),transactionDetailTitle);

    }

    @Test
    @Order(order = 2)
    public void transactionDetail() {
        WebElement reject = driver.findElement(By.name("reject"));
        reject.click();

        //TODO

    }

    @AfterClass
    public static void tearDown() {
        GeneralReusables.logout(driver);
        //driver.close();
    }
}

