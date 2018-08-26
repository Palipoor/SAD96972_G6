package Reusables;

import org.apache.commons.lang3.tuple.Pair;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;

import java.util.HashMap;
import java.util.Map;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;

/**
 * Created by Golpar on 4/19/2018 AD.
 */
public class CustomerReusables {
    public static final Map<String, String> reusableStrings;

    static {
        reusableStrings = new HashMap<String, String>();
        reusableStrings.put("amount","10");
        reusableStrings.put("profit","1");
        reusableStrings.put("anonymous-email","palipoor976@gmail.com");
        reusableStrings.put("anonymous-phone","09379605628");
        reusableStrings.put("foreign-accountNumber","1234567890");


    }

    public static void createNewAnonymous(String howmuch, String wallet){
		Pair<Double, Double> cost;
		WebDriver driver = new FirefoxDriver();
		GeneralReusables.loginAsACustomer(driver);
		driver.get(GeneralReusables.reusableStrings.get("homepage") + "/customer/create_unkowntrans");

		WebElement amount = driver.findElement(By.id("id_amount"));
		amount.clear();
		amount.sendKeys(howmuch);

		WebElement actual = driver.findElement(By.id("id_payable"));
		double payable = Double.valueOf(actual.getAttribute("value"));


		WebElement currency = driver.findElement(By.name("source_wallet"));
		Select dropdown = new Select(currency);
		dropdown.selectByVisibleText(wallet);


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
		account_number.sendKeys("1997-0335-0337");

		WebElement button = driver.findElement(By.name("create"));
		button.click();
		GeneralReusables.waitForSeconds(1);

    }


	public static Pair<Double, Double> createNewBankTransfer(String currency, String howmuch) {

		Pair<Double, Double> cost;
		WebDriver driver = new FirefoxDriver();
		GeneralReusables.loginAsACustomer(driver);
		driver.get(GeneralReusables.reusableStrings.get("homepage") + "/customer/create_banktrans");

		WebElement amount = driver.findElement(By.id("id_amount"));
		amount.clear();
		amount.sendKeys(howmuch);

		WebElement wallet = driver.findElement(By.name("source_wallet"));
		Select dropdown= new Select(wallet);
		dropdown.selectByVisibleText(currency);


		WebElement bank_name = driver.findElement(By.name("bank_name"));
		bank_name.clear();
		bank_name.sendKeys("جهانی");

		WebElement account_number = driver.findElement(By.name("account_number"));
		account_number.clear();
		account_number.sendKeys("1997-0335-0337");

		WebElement payableElement = driver.findElement(By.id("id_payable"));
		double payable = Double.valueOf(payableElement.getAttribute("value"));

		WebElement feeElement = driver.findElement(By.id("id_fee"));
		double fee = Double.valueOf(feeElement.getAttribute("value"));

		WebElement button = driver.findElement(By.name("create"));
		button.click();
		GeneralReusables.waitForSeconds(1);
		GeneralReusables.logout(driver);
		cost = Pair.of(payable, fee);
		return cost;
	}

	public static double get_credit(String currency) {
		WebDriver driver = new FirefoxDriver();
		GeneralReusables.loginAsACustomer(driver);
		return WalletUsersReusables.getWalletCredit(driver, currency);
	}

	public static Pair<Double, Double> createNewTofel() {

		WebDriver driver = new FirefoxDriver();
		GeneralReusables.loginAsACustomer(driver);
		driver.get(GeneralReusables.reusableStrings.get("homepage") + "/customer/create_tofel");

		WebElement username = driver.findElement(By.name("username"));
		username.clear();
		username.sendKeys("username");

		WebElement password = driver.findElement(By.name("password"));
		password.clear();
		password.sendKeys("password");

		WebElement date = driver.findElement(By.name("date"));
		date.clear();
		date.sendKeys("1997-05-07");

		WebElement country = driver.findElement(By.name("country"));
		country.clear();
		country.sendKeys("Iran");

		WebElement city = driver.findElement(By.name("city"));
		city.clear();
		city.sendKeys("Tehran");

		WebElement code = driver.findElement(By.name("test_center_code"));
		code.clear();
		code.sendKeys("123");

		WebElement center_name = driver.findElement(By.name("test_center_name"));
		center_name.clear();
		center_name.sendKeys("آهنچی مرکز");

		WebElement id = driver.findElement(By.name("id_number"));
		id.clear();
		id.sendKeys("002003004");

		WebElement id_type = driver.findElement(By.id("id_id_type"));
		Select dropdown= new Select(id_type);
		dropdown.selectByVisibleText("پاسپورت");

		WebElement destination = driver.findElement(By.name("country_for_study"));
		destination.clear();
		destination.sendKeys("Spain");

		WebElement reason = driver.findElement(By.name("reason"));
		reason.clear();
		reason.sendKeys("Blah Blah");

		WebElement payableElement = driver.findElement(By.id("id_payable"));
		double payable = Double.valueOf(payableElement.getAttribute("value"));

		WebElement feeElement = driver.findElement(By.id("id_fee"));
		double fee = Double.valueOf(feeElement.getAttribute("value"));

		WebElement button = driver.findElement(By.name("create"));
		button.click();

		Pair<Double, Double> cost;
		cost = Pair.of(payable, fee);
		return cost;
	}
}
