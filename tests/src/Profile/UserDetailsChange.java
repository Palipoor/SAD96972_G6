package Profile;

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
import org.openqa.selenium.firefox.FirefoxDriver;

import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;

/**
 * Created by Golpar on 4/13/2018 AD.
 */
@RunWith(Reusables.OrderedRunner.class)
public class UserDetailsChange {
	static WebDriver driver;
	static String userDetailChangeTitle = "ویرایش مشخصات کاربری";
	static String newFirstName = "فرست نیم";
	static String newFamilyName = "فمیلی نیم";
	static String newLatinFirstName = "latinfistname2";
	static String newLatinFamilyName = "latinfamilyname2";
	static String newPhoneNumber = "09132035660";
	static String newEmail = "dorna@gmail.com";
	static String newAccountNumber = "1234567898";
	static String invalidLatinName = "سلام";

	public void change(String elementName, String newField, String submitButtonName) {
		WebElement element = driver.findElement(By.name(elementName));
		element.clear();
		element.sendKeys(newField);
		WebElement button = driver.findElement(By.name(submitButtonName));
		button.click();

	}

	public void gotoUserSettings() {
		driver.findElement(By.name("profile-list")).click();
		driver.findElement(By.name("user-settings")).click();

	}

	public void checkForChange(String elementName, String expected) {
		String text = driver.findElement(By.name(elementName)).getAttribute("value");
		assertEquals(expected, text);

	}

	@BeforeClass
	public static void setUp() {
		driver = new FirefoxDriver();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		GeneralReusables.setUpToHomepage(driver);
		GeneralReusables.loginAsACustomer(driver);
		driver.findElement(By.name("profile-list")).click();
		driver.findElement(By.name("user-settings")).click();
	}


	@Test
	@Order(order = 1)
	public void preConditionTest() {
		String title = driver.getTitle();
		assertEquals(title, userDetailChangeTitle);
	}

	@Test
	@Order(order = 2)
	public void validFirstNameChange() {
		change("persian_first_name", newFirstName, "button_1");
		checkForChange("persian_first_name", newFirstName);


	}

	@Test
	@Order(order = 3)
	public void validFamilyNameChange() {
		change("persian_last_name", newFamilyName, "button_1");
		checkForChange("persian_last_name", newFamilyName);


	}

	@Test
	@Order(order = 4)
	public void validLatinFirstNameChange() {
		change("first_name", newLatinFirstName, "button_1");
		checkForChange("first_name", newLatinFirstName);


	}

	@Test
	@Order(order = 5)

	public void validLatinFamilyNameChange() {
		change("last_name", newLatinFamilyName, "button_1");
		checkForChange("last_name", newLatinFamilyName);

	}


	@Test
	@Order(order = 6)
	public void invalidNameChange() {
		change("persian_first_name", "!", "button_1");
		String error = driver.findElement(By.name("persian-first-name-error")).getText();
		assertEquals(error, GeneralReusables.reusableStrings.get("invalid-persian-name-error"));
	}


	@Test
	@Order(order = 7)
	public void invalidLatinNameChange() {
		change("first_name", invalidLatinName, "button_1");
		String error = driver.findElement(By.name("first-name-error")).getText();
		assertEquals(error, GeneralReusables.reusableStrings.get("invalid-first-name-error"));


	}

	@Test
	@Order(order = 8)
	public void validEmailChange() {
		change("email", newEmail, "button_2");
		checkForChange("email", newEmail);

	}

	@Test
	@Order(order = 9)
	public void invalidEmailChange() {
		change("email", "a@b.c", "button_2");
		String error = driver.findElement(By.name("email-error")).getText();
		assertEquals(error, ProfileReusables.invalidEmailError);


	}

	@Test
	@Order(order = 10)
	public void validPhoneNumberChange() {
		change("phone_number", newPhoneNumber, "button_2");
		checkForChange("phone_number", newPhoneNumber);

	}

	@Test
	@Order(order = 13)
	public void validAccountNumberChange() {
		change("account_number", newAccountNumber, "button_3");
		checkForChange("account_number", newAccountNumber);//TODO

	}


	@AfterClass
	public static void tearDown() {
		GeneralReusables.logout(driver);
		//TODO: revert the changes
	}


}
