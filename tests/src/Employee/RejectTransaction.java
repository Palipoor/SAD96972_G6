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

import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(Reusables.OrderedRunner.class)
public class RejectTransaction {
    static WebDriver driver;
    static double  rialDeposit;
    static double personWalletCredit;

    @BeforeClass
    public static void setUp() {
        CustomerReusables.createNewAnonymous();

        rialDeposit = ManagerReusables.getCompanyCredit("rial");

        driver = new FirefoxDriver();
        GeneralReusables.setUpToHomepage(driver);
        GeneralReusables.loginAsACustomer(driver);
        personWalletCredit = WalletUsersReusables.getWalletCredit(driver, "rial");
        GeneralReusables.logout(driver);

        driver = new FirefoxDriver();
        GeneralReusables.setUpToHomepage(driver);
        GeneralReusables.loginAsAnEmployeeWithoutName(driver);
        String transactionId = EmployeeReusables.getNewestTransactionId(driver);
        EmployeeReusables.bringMeTheDetails(transactionId, driver);

    }

    @Test
    @Order(order = 1)
    public void preConditionTest() {
        assertEquals(driver.getTitle(),EmployeeReusables.transactionDetailTitle);

    }

    @Test
    @Order(order = 2)
    public void transactionDetail() {
        EmployeeReusables.rejectTransactionGivenDetailPage(driver);
        GeneralReusables.logout(driver);


        boolean systemCreditCorrectness
                = Math.abs(ManagerReusables.getCompanyCredit("rial") - rialDeposit
                + Integer.parseInt(CustomerReusables.reusableStrings.get(("amount")))
                + Integer.parseInt(CustomerReusables.reusableStrings.get(("profit"))) )
                < 1;


        driver = new FirefoxDriver();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        GeneralReusables.setUpToHomepage(driver);
        GeneralReusables.loginAsACustomer(driver);
        boolean personCreditCorrectness
                = Math.abs( WalletUsersReusables.getWalletCredit(driver, "rial")- personWalletCredit
                - Integer.parseInt(CustomerReusables.reusableStrings.get(("amount")))
                - Integer.parseInt(CustomerReusables.reusableStrings.get(("profit"))) )
                < 1;

        assertTrue(systemCreditCorrectness && personCreditCorrectness);
    }

    @AfterClass
    public static void tearDown() {
        GeneralReusables.logout(driver);
    }
}

