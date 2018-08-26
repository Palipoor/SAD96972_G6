package Transactions;

import Reusables.CustomerReusables;
import Reusables.GeneralReusables;
import Reusables.ManagerReusables;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;

/**
 * Created by Golpar on 8/24/2018 AD.
 */
public class AnonymousPayment {

	private static WebDriver driver;

	@BeforeClass
	public static void setUp() {
		driver = new FirefoxDriver();
		GeneralReusables.setUpToHomepage(driver);
		GeneralReusables.loginAsACustomer(driver);
		driver.get(GeneralReusables.reusableStrings.get("homepage") + "/customer/create_unkowntrans");
	}

	@Test
	public void validCreationDollar() {
		WebElement amount = driver.findElement(By.id("id_amount"));
		amount.clear();
		amount.sendKeys("5");

		WebElement actual = driver.findElement(By.id("id_payable"));
		double payable = Double.valueOf(actual.getAttribute("value"));
		double dollar_credit = CustomerReusables.get_credit("dollar");


		WebElement currency = driver.findElement(By.name("source_wallet"));
		Select dropdown = new Select(currency);
		dropdown.selectByVisibleText("dollar");


		String email = "TEST_" + System.currentTimeMillis() + "pegah@gmail.com";
		String phone = String.valueOf(System.currentTimeMillis());

		WebElement emailElement = driver.findElement(By.id("id_email"));
		emailElement.clear();
		emailElement.sendKeys(email);


		WebElement phoneElement = driver.findElement(By.id("id_phone_number"));
		phoneElement.clear();
		phoneElement.sendKeys(phone);


		WebElement bank_name = driver.findElement(By.name("bank_name"));
		bank_name.clear();
		bank_name.sendKeys("جهانی");

		WebElement account_number = driver.findElement(By.name("account_number"));
		account_number.clear();
		account_number.sendKeys(String.valueOf(System.currentTimeMillis()));

		WebElement button = driver.findElement(By.name("create"));
		button.click();
		GeneralReusables.waitForSeconds(1);

		WebElement message = driver.findElement(By.name("message"));
		assertEquals(message.getText(), GeneralReusables.reusableStrings.get("successful-creation"));
		assertTrue(ManagerReusables.newTransactionExists("banktrans"));


		double new_dollar_credit = CustomerReusables.get_credit("dollar");
		assertEquals(payable, dollar_credit - new_dollar_credit, GeneralReusables.delta);
	}

	@Test
	public void validCreationRial() {
		WebElement amount = driver.findElement(By.id("id_amount"));
		amount.clear();
		amount.sendKeys("5");

		WebElement actual = driver.findElement(By.id("id_payable"));
		double payable = Double.valueOf(actual.getAttribute("value"));
		double rial_credit = CustomerReusables.get_credit("rial");


		WebElement currency = driver.findElement(By.name("source_wallet"));
		Select dropdown = new Select(currency);
		dropdown.selectByVisibleText("rial");


		String email = "TEST_" + System.currentTimeMillis() + "pegah@gmail.com";
		String phone = String.valueOf(System.currentTimeMillis());

		WebElement emailElement = driver.findElement(By.id("id_email"));
		emailElement.clear();
		emailElement.sendKeys(email);


		WebElement phoneElement = driver.findElement(By.id("id_phone_number"));
		phoneElement.clear();
		phoneElement.sendKeys(phone);


		WebElement bank_name = driver.findElement(By.name("bank_name"));
		bank_name.clear();
		bank_name.sendKeys("جهانی");

		WebElement account_number = driver.findElement(By.name("account_number"));
		account_number.clear();
		account_number.sendKeys(String.valueOf(System.currentTimeMillis()));

		WebElement button = driver.findElement(By.name("create"));
		button.click();
		GeneralReusables.waitForSeconds(1);

		WebElement message = driver.findElement(By.name("message"));
		assertEquals(message.getText(), GeneralReusables.reusableStrings.get("successful-creation"));
		assertTrue(ManagerReusables.newTransactionExists("banktrans"));


		double new_rial_credit = CustomerReusables.get_credit("rial");
		assertEquals(payable, rial_credit - new_rial_credit, GeneralReusables.delta);
	}

	@AfterClass
	public static void tearDown() {
		GeneralReusables.logout(driver);
	}
}
