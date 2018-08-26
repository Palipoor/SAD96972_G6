package Wallet;

import Reusables.DBManager;
import Reusables.GeneralReusables;
import Reusables.WalletUsersReusables;
import org.apache.xalan.xsltc.util.IntegerArray;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import static org.junit.Assert.*;

/**
 * Created by Golpar on 4/12/2018 AD.
 */
public class RialChargeManager {

	private static WebDriver driver;

	@BeforeClass
	public static void setUp() {
		driver = new FirefoxDriver();
		GeneralReusables.setUpToHomepage(driver);
		GeneralReusables.loginAsTheManager(driver);
		WalletUsersReusables.navigateToWallet(driver, "rial");
	}

	@Test
	public void rialWalletCharge() {
		double rialCredit = Double.valueOf(driver.findElement(By.name("credit")).getText());
		System.out.println("credit " + rialCredit);
		WebElement amountBox = driver.findElement(By.name("amount"));
		amountBox.clear();
		amountBox.sendKeys("10000");
		WebElement chargeButton = driver.findElement(By.name("charge-button"));
		chargeButton.click();
		double newRialCredit = Double.valueOf(driver.findElement(By.name("credit")).getText());
		System.out.println("new credit " + newRialCredit);
		double charged = newRialCredit - rialCredit;
		assertEquals(charged, 10000.0, GeneralReusables.delta); // oon ghadri ke lazeme charge shode bashe
	}

	@AfterClass
	public static void tearDown() {
		GeneralReusables.logout(driver);
		DBManager manager = new DBManager();
		manager.connect();
		manager.setCustomersToDefault();	}
}
