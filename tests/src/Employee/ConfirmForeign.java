package Employee;


import Reusables.*;

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
public class ConfirmForeign {
    static WebDriver driver;
    static double dollarDeposit;

    @BeforeClass
    public static void setUp() {
        driver = new FirefoxDriver();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        CustomerReusables.createNewForeign();
        dollarDeposit = ManagerReusables.getCompanyCredit("dollar");
        GeneralReusables.setUpToHomepage(driver);
        GeneralReusables.loginAsAnEmployeeWithoutName(driver);
        String transactionId = EmployeeReusables.getNewestTransactionId(driver);
        EmployeeReusables.bringMeTheDetails(transactionId, driver);

    }

    @Test
    @Order(order = 1)
    public void preConditionTest() {
        assertEquals(driver.getTitle(), EmployeeReusables.transactionDetailTitle);

    }

    @Test
    @Order(order = 2)
    public void confirm() {

        EmployeeReusables.acceptTransactionGivenDetailPage(driver);
        assertEquals( ManagerReusables.getCompanyCredit("rial"),
                dollarDeposit - Integer.parseInt(CustomerReusables.reusableStrings.get("amount")), 1);


    }

    @AfterClass
    public static void tearDown() {
        GeneralReusables.logout(driver);
    }
}

