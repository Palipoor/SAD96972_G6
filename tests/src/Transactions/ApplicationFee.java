package Transactions;

import Reusables.CustomerReusables;
import Reusables.GeneralReusables;
import Reusables.ManagerReusables;
import org.junit.AfterClass;
import org.junit.Assert;
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
public class ApplicationFee {

	private static WebDriver driver;

	@BeforeClass
	public static void setUp() {
		driver = new FirefoxDriver();
		GeneralReusables.setUpToHomepage(driver);
		GeneralReusables.loginAsACustomer(driver);
		driver.get(GeneralReusables.reusableStrings.get("homepage") + "/customer/create_universitytrans");
	}

	@Test
	public void validCreation(){

		double dollar_credit = CustomerReusables.get_credit("dollar");

		WebElement username = driver.findElement(By.name("username"));
		username.clear();
		username.sendKeys("username");

		WebElement password = driver.findElement(By.name("password"));
		password.clear();
		password.sendKeys("password");

		WebElement amount = driver.findElement(By.name("amount"));
		amount.clear();
		amount.sendKeys("5");

		WebElement type = driver.findElement(By.id("id_university_transـtype"));
		Select dropdown= new Select(type);
		dropdown.selectByVisibleText("application fee");

		WebElement currency = driver.findElement(By.name("source_wallet"));
		Select dropdown2= new Select(currency);
		dropdown2.selectByVisibleText("dollar");

		WebElement university_name = driver.findElement(By.name("university_name"));
		university_name.clear();
		university_name.sendKeys("ETH");

		WebElement link = driver.findElement(By.name("link"));
		link.clear();
		link.sendKeys("ETH.com");

		WebElement guide = driver.findElement(By.name("guide"));
		guide.clear();
		guide.sendKeys("go there");

		WebElement other_details = driver.findElement(By.name("other_details"));
		other_details.clear();
		other_details.sendKeys("nothing else");

		WebElement button = driver.findElement(By.name("create"));
		button.click();
		GeneralReusables.waitForSeconds(1);

		WebElement message = driver.findElement(By.name("message"));
		assertEquals(message.getText(), GeneralReusables.reusableStrings.get("successful-creation"));
		assertTrue(ManagerReusables.newTransactionExists("universitytrans"));

		double new_dollar_credit = CustomerReusables.get_credit("dollar");
//		Assert.assertEquals(5.0, dollar_credit - new_dollar_credit, GeneralReusables.delta);

	}

	@AfterClass
	public static void tearDown(){
		GeneralReusables.logout(driver);
	}
}
