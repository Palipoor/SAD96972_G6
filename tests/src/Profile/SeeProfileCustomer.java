package Profile;

import Reusables.GeneralReusables;
import Reusables.ProfileReusables;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;

/**
 * Created by Golpar on 4/13/2018 AD.
 */
public class SeeProfileCustomer {
	static WebDriver driver;


	@BeforeClass
	public static void setUp() {
		driver = new FirefoxDriver();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		GeneralReusables.setUpToHomepage(driver);
		GeneralReusables.loginAsACustomer(driver);
		driver.findElement(By.name("profile-list")).click();
		driver.findElement(By.name("profile-view")).click();

	}

	@Test
	public void userDetailsExistence() {
		String title = driver.getTitle();
		assertEquals(title, ProfileReusables.reusableStrings.get("user-details-title"));
	}

	@AfterClass
	public static void tearDown() {
		GeneralReusables.logout(driver);
	}


}
