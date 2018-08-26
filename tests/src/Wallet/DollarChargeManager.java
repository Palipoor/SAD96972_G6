package Wallet;

import Reusables.DBManager;
import Reusables.GeneralReusables;
import Reusables.ManagerReusables;
import Reusables.WalletUsersReusables;
import org.junit.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

/**
 * Created by Golpar on 4/12/2018 AD.
 */
public class DollarChargeManager {
	private static WebDriver driver;
	private String amount = "1";

	@BeforeClass
	public static void setUp() {
		driver = new FirefoxDriver();
		GeneralReusables.setUpToHomepage(driver);
		GeneralReusables.loginAsTheManager(driver);
		WalletUsersReusables.navigateToWallet(driver, "dollar");
	}

	@Test
	public void conversionTest() {// مبلغی که نشون می‌ده برابر با مبلغ وارد شده ضربدر قیمت دلار باشه.
		WebElement desiredAmount = driver.findElement(By.name("amount"));
		desiredAmount.clear();
		desiredAmount.sendKeys(amount);
		double dollarPrice = GeneralReusables.getPrice("dollar");
		double rial = dollarPrice * Double.valueOf(amount);
		WebElement rialAmount = driver.findElement(By.name("rial-amount"));
		double shownRial = Double.valueOf(rialAmount.getText());
		assertEquals(rial, shownRial, GeneralReusables.delta);
	}

	@Test
	public void decreaseTest() {// مبلغی که کسر می‌شه برابر با مبلغی که نشون می‌ده باشه.
		double rialCredit = ManagerReusables.getCompanyCredit("rial");
		WebElement desiredAmount = driver.findElement(By.name("amount"));
		desiredAmount.clear();
		desiredAmount.sendKeys(amount);
		double dollarPrice = GeneralReusables.getPrice("dollar");
		WebElement chargeButton = driver.findElement(By.name("charge-button"));
		WebElement rialAmount = driver.findElement(By.name("rial-amount"));
		double shownRial = Double.valueOf(rialAmount.getText());
		chargeButton.click();
		double currentRialCredit = ManagerReusables.getCompanyCredit("rial");
		assertEquals(currentRialCredit, rialCredit - shownRial, GeneralReusables.delta);
	}

	@Test
	public void increaseTest() {// مبلغی که کسر می‌شه برابر با مبلغی که نشون می‌ده باشه.
		double dollarCredit = ManagerReusables.getCompanyCredit("dollar");
		WebElement chargeButton = driver.findElement(By.name("charge-button"));
		double increaseAmount = Double.valueOf(amount);
		chargeButton.click();
		double currentDollarCredit = WalletUsersReusables.getWalletCredit(driver, "dollar");
		assertEquals(currentDollarCredit, dollarCredit + increaseAmount, GeneralReusables.delta);
	}

	@Test
	public void invalidDecreaseTest() {
		double rialCredit = ManagerReusables.getCompanyCredit("rial");
		double decreaseAmount = (rialCredit + 20000) / GeneralReusables.getPrice("dollar"); // بیشتر از آن چه دارد.
		WebElement desiredAmount = driver.findElement(By.name("amount"));
		desiredAmount.sendKeys(String.valueOf(decreaseAmount));
		WebElement chargeButton = driver.findElement(By.name("charge-button"));
		chargeButton.click();
		WebElement errorMessage = driver.findElement(By.name("amount-error"));
		assertEquals(errorMessage.getText(), WalletUsersReusables.reusableStrings.get("not-enough-error-rial"));
	}


	@AfterClass
	public static void tearDown() {
		GeneralReusables.logout(driver);

	}
}
