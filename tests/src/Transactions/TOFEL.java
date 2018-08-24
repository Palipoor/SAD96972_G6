package Transactions;

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
public class TOFEL {

	private static WebDriver driver;

	@BeforeClass
	public static void setUp() {
		driver = new FirefoxDriver();
		GeneralReusables.setUpToHomepage(driver);
		GeneralReusables.loginAsACustomer(driver);
		driver.get(GeneralReusables.reusableStrings.get("homepage") + "/customer/create_tofel");
	}

	@Test
	public void validCreation(){
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

		WebElement button = driver.findElement(By.name("create"));
		button.click();
		GeneralReusables.waitForSeconds(1);

		WebElement message = driver.findElement(By.name("message"));
		assertEquals(message.getText(), GeneralReusables.reusableStrings.get("successful-creation"));
		assertTrue(ManagerReusables.newTransactionExists("tofel"));

	}

	@AfterClass
	public static void tearDown(){
		GeneralReusables.logout(driver);
	}
}
