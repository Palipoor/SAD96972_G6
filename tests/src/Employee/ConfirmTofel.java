package Employee;

import Reusables.CustomerReusables;
import Reusables.EmployeeReusables;
import Reusables.GeneralReusables;
import Reusables.ManagerReusables;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by Golpar on 8/25/2018 AD.
 */
public class ConfirmTofel {
	static WebDriver driver;
	static String transactionId;
	static double amount;

	@BeforeClass
	public static void setUp() {
		driver = new FirefoxDriver();
		GeneralReusables.setUpToHomepage(driver);
		GeneralReusables.loginAsAnEmployeeWithoutName(driver);
		Pair<Double,Double> cost = CustomerReusables.createNewTofel();
		amount = cost.getLeft() - cost.getRight();
		transactionId = ManagerReusables.getNewestTransactionId();
	}


	@Test
	public void acceptTransactionDollar() {

		double dollar_credit = ManagerReusables.getCompanyCredit("rial");

		System.out.println("transaction id " + transactionId);
		EmployeeReusables.acceptTransaction(transactionId);
		driver.navigate().refresh();
		String status = get_status(transactionId);

		assertEquals(status, EmployeeReusables.ACCEPT);

		double new_dollar_credit = ManagerReusables.getCompanyCredit("rial");

		System.out.println("amount " + amount);
		System.out.println("dollar credit" + dollar_credit);
		System.out.printf("dollar new credit" + new_dollar_credit);
		assertEquals(amount, dollar_credit - new_dollar_credit, GeneralReusables.delta);
	}

	private String get_status(String transactionId) {
		WebElement theTable = driver.findElement(By.name("transactions-table"));
		List<WebElement> tableHeader = theTable.findElements(By.xpath("//thead"));
		List<WebElement> headerTitles = tableHeader.get(0).findElements(By.xpath("//th"));
		int statusIndex = 0;
		for (int i = 0; i < headerTitles.size(); i++) {
			if (headerTitles.get(i).getText().contains("وضعیت")) {
				statusIndex = i;
			}
		}

		WebElement idSearchBox = theTable.findElement(By.name("شناسه تراکنش"));
		idSearchBox.clear();
		idSearchBox.sendKeys(transactionId);
		List<WebElement> tableRows = theTable.findElements(By.xpath("//tbody//tr"));
		List<WebElement> transactionDetails = tableRows.get(0).findElements(By.xpath("//td"));

		return transactionDetails.get(statusIndex).getText();
	}


	@AfterClass
	public static void tearDown() {
		GeneralReusables.logout(driver);
	}
}
