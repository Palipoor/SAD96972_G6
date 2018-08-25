package Employee;


import Reusables.*;

import org.apache.commons.lang3.tuple.Pair;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;

@RunWith(Reusables.OrderedRunner.class)
public class RejectTransaction {
	static WebDriver driver;
	static double dollar_amount;
	static String transactionId;

	@BeforeClass
	public static void setUp() {
		driver = new FirefoxDriver();
		GeneralReusables.setUpToHomepage(driver);
		GeneralReusables.loginAsAnEmployeeWithoutName(driver);
		Pair<Double,Double> cost = CustomerReusables.createNewBankTransfer("dollar", String.valueOf(dollar_amount));
		dollar_amount = cost.getLeft();
		transactionId = ManagerReusables.getNewestTransactionId();
	}


	@Test
	public void rejectTransaction() {

		double dollar_credit = CustomerReusables.get_credit("dollar");

		EmployeeReusables.rejectTransaction(transactionId);
		driver.navigate().refresh();
		String status = get_status(transactionId);

		assertEquals(status, EmployeeReusables.REJECT);

		double new_dollar_credit = CustomerReusables.get_credit("dollar");


		assertEquals(dollar_amount, new_dollar_credit - dollar_credit, GeneralReusables.delta);

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

		String status = transactionDetails.get(statusIndex).getText();
		GeneralReusables.logout(driver);
		return status;
	}


	@AfterClass
	public static void tearDown() {
		GeneralReusables.logout(driver);
	}
}

