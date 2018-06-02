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
import org.openqa.selenium.firefox.FirefoxDriver;

import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;

@RunWith(Reusables.OrderedRunner.class)
public class ConfirmWithdrawal {
    static WebDriver driver;
    static double rialDeposit;

    @BeforeClass
    public static void setUp() {
        driver = new FirefoxDriver();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        //createNewTransaction(String type);   TODO
        rialDeposit = ManagerReusables.getCompanyCredit("rial");
        GeneralReusables.setUpToHomepage(driver);
        GeneralReusables.loginAsAnEmployee(driver);
        WebElement cell = ManagerReusables.getNewestRequest(driver);
        WebElement link = cell.findElement(By.tagName("a"));
        link.click();
    }

    @Test
    @Order(order = 1)
    public void preConditionTest() {
        assertEquals(driver.getTitle(), EmployeeReusables.transactionDetailTitle);
    }

    @Test
    @Order(order = 2)
    public void confirm() {
        WebElement confirm = driver.findElement(By.name("confirm"));
        confirm.submit();
        //TODO: check the status.
        GeneralReusables.logout(driver);
        //TODO: how much does it change?

        assertEquals( ManagerReusables.getCompanyCredit("dollar"), rialDeposit, 1);
    }

    //TODO: write test for invalid case

    @AfterClass
    public static void tearDown() {
        GeneralReusables.logout(driver);
        //driver.close();
    }
}

