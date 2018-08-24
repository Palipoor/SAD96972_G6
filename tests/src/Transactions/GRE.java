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
public class GRE {

	private static WebDriver driver;

	@BeforeClass
	public static void setUp() {
		driver = new FirefoxDriver();
		GeneralReusables.setUpToHomepage(driver);
		GeneralReusables.loginAsACustomer(driver);
		driver.get(GeneralReusables.reusableStrings.get("homepage") + "/customer/create_gre");
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

		WebElement education = driver.findElement(By.name("educational_status"));
		Select dropdown= new Select(education);
		dropdown.selectByVisibleText("دیگر");

		WebElement citizenship = driver.findElement(By.name("citizenship"));
		Select dropdown2= new Select(citizenship);
		dropdown2.selectByVisibleText("blah");

		WebElement major_field_name = driver.findElement(By.name("major_filed_name"));
		major_field_name.clear();
		major_field_name.sendKeys("CS");

		WebElement major_field_code = driver.findElement(By.name("major_filed_code"));
		major_field_code.clear();
		major_field_code.sendKeys("1234");

		WebElement button = driver.findElement(By.name("create"));
		button.click();
		GeneralReusables.waitForSeconds(1);

		WebElement message = driver.findElement(By.name("message"));
		assertEquals(message.getText(), GeneralReusables.reusableStrings.get("successful-creation"));
		assertTrue(ManagerReusables.newTransactionExists("gre"));

	}

	@AfterClass
	public static void tearDown(){
		GeneralReusables.logout(driver);
	}
}
