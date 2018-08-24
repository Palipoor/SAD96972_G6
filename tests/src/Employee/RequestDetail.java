package Employee;

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

import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(Reusables.OrderedRunner.class)
public class RequestDetail {
	static WebDriver driver;
	String transactionDetailTitle = "جزئیات تراکنش";
	String requestStatus = "2";


	@BeforeClass
	public static void setUp() {
		driver = new FirefoxDriver();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		GeneralReusables.setUpToHomepage(driver);
		GeneralReusables.loginAsAnEmployeeWithoutName(driver);

	}

	@Test
	@Order(order = 1)

	public void preConditionTest() {
		List<WebElement> tables = driver.findElements(By.id("table"));
		boolean isTherAnyTransactionsTable = false;
		for (WebElement table : tables) {
			if (table.getAttribute("name").equals("transactions-table")) {
				isTherAnyTransactionsTable = true;
			}
		}
		assertTrue(isTherAnyTransactionsTable);
	}

	@Test
	@Order(order = 2)
	public void transactionDetail() {
		WebElement table = driver.findElement(By.name("transactions-table"));
		List<WebElement> allRows = table.findElements(By.tagName("tr"));
		if (allRows.size() != 0) {
			WebElement firstCell = table.findElement(By.tagName("td"));
			WebElement link = firstCell.findElement(By.tagName("a"));
			link.click();
			assertEquals(driver.getTitle(), transactionDetailTitle);
		}
	}

	@AfterClass
	public static void tearDown() {
		GeneralReusables.logout(driver);
		//driver.close();
	}
}

