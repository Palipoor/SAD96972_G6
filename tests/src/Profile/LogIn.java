package Profile;

import Reusables.DBManager;
import Reusables.Order;
import Reusables.ProfileReusables;
import Reusables.GeneralReusables;
import org.junit.*;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;

@RunWith(Reusables.OrderedRunner.class)
public class LogIn {
	private static WebDriver driver;


	@BeforeClass
	public static void setUp() {
		// Initialize the WebDriver
		driver = new FirefoxDriver();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

		GeneralReusables.setUpToHomepage(driver);
		// Go to Log In page
		driver.get("http://127.0.0.1:8000/login");
		GeneralReusables.waitForSeconds(2);

	}


	@Test
	@Order(order = 1)
	public void preConditionTest() {
		String title = driver.getTitle();
		assertEquals(title, ProfileReusables.reusableStrings.get("log-in-title"));
	}

	@Test
	@Order(order = 2)
	public void invalidUsername() throws Exception {


		// Enter an invalid email
		WebElement username = driver.findElement(By.name("username"));
		username.sendKeys("?");


		ProfileReusables.enterValidPassword(driver);

		GeneralReusables.waitForSeconds(2);

		ProfileReusables.clickForLogIn(driver);
		String errorText = "";

		if (driver.getTitle().equals(ProfileReusables.reusableStrings.get("log-in-title"))) {
			errorText = driver.findElement(By.name("error")).getText();
		} else
			GeneralReusables.backToLogin(driver); //This only happens when the test fails

		GeneralReusables.waitForSeconds(2);


		assertFalse(driver.getCurrentUrl().contains("dashboard"));
		assertEquals(errorText, ProfileReusables.loginError);


	}

	@Test
	@Order(order = 3)
	public void notRegisteredEmail() throws Exception {


		// Enter an not registered email
		WebElement email = driver.findElement(By.name("username"));
		email.clear();
		email.sendKeys(ProfileReusables.notRegisteredEmail);

		ProfileReusables.enterValidPassword(driver);


		ProfileReusables.clickForLogIn(driver);

		String errorText = "";

		if (driver.getTitle().equals(ProfileReusables.reusableStrings.get("log-in-title"))) {
			errorText = driver.findElement(By.name("error")).getText();
		} else
			GeneralReusables.backToLogin(driver);

		GeneralReusables.waitForSeconds(2);


		assertFalse(driver.getCurrentUrl().contains("dashboard"));
		assertEquals(errorText, ProfileReusables.loginError);
	}

	@Test
	@Order(order = 4)
	public void wrongPassword() throws Exception {


		ProfileReusables.enterValidUsername(driver);


		WebElement password = driver.findElement(By.name("password"));
		password.clear();
		password.sendKeys(ProfileReusables.wrongPassword);


		ProfileReusables.clickForLogIn(driver);
		String errorText = "";

		if (driver.getTitle().equals(ProfileReusables.reusableStrings.get("log-in-title"))) {
			errorText = driver.findElement(By.name("error")).getText();
		} else
			GeneralReusables.backToLogin(driver);


		GeneralReusables.waitForSeconds(2);

		assertFalse(driver.getCurrentUrl().contains("dashboard"));
		assertEquals(errorText, ProfileReusables.loginError);
	}


	@Test
	@Order(order = 5)
	public void validLogIn() throws Exception {

		ProfileReusables.signUpUser1(driver);

		driver.get(GeneralReusables.reusableStrings.get("homepage") + "/login");
		WebElement username = driver.findElement(By.name("username"));
		username.clear();

		username.sendKeys(ProfileReusables.username1);

		WebElement password = driver.findElement(By.name("password"));
		password.clear();

		password.sendKeys(ProfileReusables.password1);

		ProfileReusables.clickForLogIn(driver);

		GeneralReusables.waitForSeconds(2);

		List<WebElement> userMenu = driver.findElements(By.name("user menu"));
		assertNotEquals(userMenu.size(), 0);// exists such element in the page
	}


	@AfterClass
	public static void tearDown() {
		GeneralReusables.logout(driver);
		DBManager manager = new DBManager();
		manager.connect();
		manager.deleteCustomers();
	}


}
