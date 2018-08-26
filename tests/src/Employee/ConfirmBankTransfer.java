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
public class ConfirmBankTransfer {
	static WebDriver driver;
	static double amount = 10;
	static double fee;
	static String transactionId;

	@BeforeClass
	public static void setUp() {
		driver = new FirefoxDriver();
		GeneralReusables.setUpToHomepage(driver);
		GeneralReusables.loginAsAnEmployeeWithoutName(driver);
		Pair<Double, Double> cost = CustomerReusables.createNewBankTransfer("dollar", String.valueOf(amount));
		amount = cost.getLeft() - cost.getRight();
		transactionId = ManagerReusables.getNewestTransactionId();
	}


	@Test
	public void acceptTransactionDollar() {

		double dollar_credit = ManagerReusables.getCompanyCredit("dollar");

		EmployeeReusables.acceptTransaction(transactionId);
		driver.navigate().refresh();
		String status = get_status(transactionId);

		assertEquals(status, EmployeeReusables.ACCEPT);
		double new_dollar_credit = ManagerReusables.getCompanyCredit("dollar");

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
		GeneralReusables.waitForSeconds(5);
		List<WebElement> tableRows = theTable.findElements(By.xpath("//tbody//tr"));
		List<WebElement> transactionDetails = tableRows.get(0).findElements(By.xpath("//td"));

		return transactionDetails.get(statusIndex).getText();
	}


	@AfterClass
	public static void tearDown() {
		GeneralReusables.logout(driver);
	}
}

