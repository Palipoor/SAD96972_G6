package CustomersTransactions;

import Reusables.GeneralReusables;
import Reusables.Order;
import Reusables.ProfileReusables;
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

@RunWith(Reusables.OrderedRunner.class)
public class TransactionsView {
    static WebDriver driver;
    String myTransactionTitle= "تراکنش‌های من";


    @BeforeClass
    public static void setUp() {
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        GeneralReusables.setUpToHomepage(driver);
        GeneralReusables.loginAsACustomer(driver);

    }

    @Test
    @Reusables.Order(order = 1)
    public void preConditionTest() {
        String title = driver.getTitle();
        assertEquals(title, GeneralReusables.PANEL_TITLE);
    }

    @Test
    @Order(order = 2)
    public void transaction() {
        WebElement myTransactions= driver.findElement(By.name("my transactions"));
        myTransactions.click();
        String title = driver.getTitle();
        assertEquals(title, myTransactionTitle);
    }

    @AfterClass
    public static void tearDown() {
        GeneralReusables.logout(driver);
        driver.close();
    }

}
