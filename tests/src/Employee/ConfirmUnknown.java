package Employee;


import Reusables.*;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(Reusables.OrderedRunner.class)
public class ConfirmUnknown {
    static WebDriver driver;
    static double rialDeposit;
    static double personWalletCredit;






    @BeforeClass
    public static void setUp() {
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        //createNewTransaction(String type);   TODO
        rialDeposit = ManagerReusables.getCompanyCredit("rial");
        GeneralReusables.setUpToHomepage(driver);

        GeneralReusables.login(driver, " ", " ");//TODO email and password of receiver
        personWalletCredit = WalletUsersReusables.getWalletCredit(driver, "rial");
        GeneralReusables.logout(driver);
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
         boolean systemCreditCorrectness = ManagerReusables.getCompanyCredit("rial")== rialDeposit; //TODO almost ==
        GeneralReusables.login(driver, " ", " ");//TODO email and password of receiver
        boolean personCreditCorrectness
                = WalletUsersReusables.getWalletCredit(driver, "rial") == personWalletCredit;//TODO alnost ==
        assertTrue(systemCreditCorrectness && personCreditCorrectness);



    }

    //TODO: write test for invalid case and the case in which the person does not have an account


    @AfterClass
    public static void tearDown() {
        GeneralReusables.logout(driver);
        //driver.close();
    }
}

