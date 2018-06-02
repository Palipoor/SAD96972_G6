package CustomersTransactions;

import Reusables.GeneralReusables;
import Reusables.Order;
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

@RunWith(Reusables.OrderedRunner.class)
public class TransactionDetail {
    static WebDriver driver;
    String myTransactionTitle= "تراکنش‌های من";
    String transactionDetailTitle= "پنل مدیریت | جزئیات تراکنش";



    @BeforeClass
    public static void setUp() {
        driver = new FirefoxDriver();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        /*GeneralReusables.setUpToHomepage(driver);
        GeneralReusables.loginAsACustomer(driver);
        WebElement myTransactions= driver.findElement(By.name("my transactions"));
        myTransactions.click();*/
        driver.get("http://127.0.0.1:8000/customer/mytransactions");

    }

    @Test
    @Order(order = 1)
    public void preConditionTest() {
        String title = driver.getTitle();
        assertEquals(title, myTransactionTitle);
    }

   @Test
   @Order(order = 2)
   public void transactionDetail() {
        WebElement table = driver.findElement(By.id("table"));
        List<WebElement> allRows = table.findElements(By.tagName("tr"));
        if(allRows.size() != 0) {
            WebElement firstCell = table.findElement(By.tagName("td"));
            WebElement link = firstCell.findElement(By.tagName("a"));
            link.click();
            assertEquals(driver.getTitle(),transactionDetailTitle);
        }

    }

    @AfterClass
    public static void tearDown() {
        GeneralReusables.logout(driver);
        //driver.close();
    }
}
