package Profile;


import Reusables.DBManager;
import Reusables.ProfileReusables;
import Reusables.GeneralReusables;
import org.junit.*;
import Reusables.Order;
import Reusables.OrderedRunner;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(Reusables.OrderedRunner.class)
public class SignUp {
	private static WebDriver driver;
	private static DBManager dbManager;

	private static void goToSignupPage() {
		GeneralReusables.setUpToHomepage(driver);
		// Go to Sign up page
		String linkToOpen = driver.findElement(By.name("sign up")).getAttribute("href");
		driver.get(linkToOpen);
	}

	String successTitle = ""; //TODO;


	@BeforeClass
	public static void setUp() {
		driver = new FirefoxDriver();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		goToSignupPage();


	}

	@Test
	@Order(order = 1)
	public void invalidFirstName() {

		goToSignupPage();

		WebElement firstName = driver.findElement(By.name("first_name"));
		firstName.sendKeys(ProfileReusables.invalidName);

		ProfileReusables.enterValidFamilyName(driver);
		ProfileReusables.enterValidUsername(driver);
		ProfileReusables.enterValidEmail(driver);
		ProfileReusables.enterValidPhoneNumber(driver);
		ProfileReusables.enterValidAccountNumber(driver);
		ProfileReusables.enterValidPassword(driver);
		ProfileReusables.repeatValidPassword(driver);


		ProfileReusables.clickForSignUp(driver);

		GeneralReusables.waitForSeconds(3);

		//  Verify that error message is displayed for authentication failure.
		String errorText = "";
		errorText = driver.findElement(By.name("first-name-error")).getText();

		assertEquals(errorText, GeneralReusables.reusableStrings.get("invalid-first-name-error"));
	}

	@Test
	@Order(order = 2)
	public void invalidFamilyName() {
		goToSignupPage();

		ProfileReusables.enterValidFirstName(driver);

		WebElement familyName = driver.findElement(By.name("last_name"));
		familyName.clear();
		familyName.sendKeys(ProfileReusables.invalidName);

		ProfileReusables.enterValidUsername(driver);
		ProfileReusables.enterValidEmail(driver);
		ProfileReusables.enterValidPhoneNumber(driver);
		ProfileReusables.enterValidAccountNumber(driver);
		ProfileReusables.enterValidPassword(driver);
		ProfileReusables.repeatValidPassword(driver);


		ProfileReusables.clickForSignUp(driver);
		GeneralReusables.waitForSeconds(3);

		//  Verify that error message is displayed for authentication failure.
		String errorText = "";
		System.out.println(driver.getTitle());
		errorText = driver.findElement(By.name("last-name-error")).getText();

		assertEquals(errorText, GeneralReusables.reusableStrings.get("invalid-family-name-error"));
	}

	@Test
	@Order(order = 3)
	public void invalidEmail() {
		goToSignupPage();


		ProfileReusables.enterValidFirstName(driver);
		ProfileReusables.enterValidFamilyName(driver);

		ProfileReusables.enterValidUsername(driver);

		// Enter an invalid email
		WebElement email = driver.findElement(By.name("email"));
		email.clear();
		email.sendKeys(ProfileReusables.invalidEmail);

		ProfileReusables.enterValidPhoneNumber(driver);
		ProfileReusables.enterValidAccountNumber(driver);
		ProfileReusables.enterValidPassword(driver);
		ProfileReusables.repeatValidPassword(driver);


		ProfileReusables.clickForSignUp(driver);

		GeneralReusables.waitForSeconds(3);

		String errorText = "";
		errorText = driver.findElement(By.name("email-error")).getText();


		assertEquals(errorText, ProfileReusables.invalidEmailError);
	}


	@Test
	@Order(order = 4)
	public void registeredEmail() {

		ProfileReusables.signUpUser1(driver);
		goToSignupPage();

		ProfileReusables.enterValidFirstName(driver);
		ProfileReusables.enterValidFamilyName(driver);

		// Enter an already registered username
		WebElement username = driver.findElement(By.name("username"));
		username.clear();
		username.sendKeys("TEST_dornadorna");

		WebElement email = driver.findElement(By.name("email"));
		email.clear();
		email.sendKeys(ProfileReusables.reusableStrings.get("email"));

		ProfileReusables.enterValidPhoneNumber(driver);
		WebElement shomareHesab = driver.findElement(By.name("account_number"));
		shomareHesab.clear();
		shomareHesab.sendKeys("123456");
		ProfileReusables.enterValidPassword(driver);
		ProfileReusables.repeatValidPassword(driver);

		ProfileReusables.clickForSignUp(driver);

		String errorText = "";
		errorText = driver.findElement(By.name("email-error")).getText();

		assertEquals(errorText, GeneralReusables.reusableStrings.get("username-exists"));

		dbManager = new DBManager();
		dbManager.connect();
		dbManager.deleteCustomers();
	}


	@Test
	@Order(order = 6)
	public void invalidAccountNumber() {
		goToSignupPage();

		ProfileReusables.enterValidFirstName(driver);
		ProfileReusables.enterValidFamilyName(driver);
		ProfileReusables.enterValidUsername(driver);
		ProfileReusables.enterValidEmail(driver);
		ProfileReusables.enterValidPhoneNumber(driver);


		WebElement accountNumber = driver.findElement(By.name("account_number"));
		accountNumber.clear();
		accountNumber.sendKeys(ProfileReusables.invalidAccountNumber);

		ProfileReusables.enterValidPassword(driver);
		ProfileReusables.repeatValidPassword(driver);

		ProfileReusables.clickForSignUp(driver);

		GeneralReusables.waitForSeconds(3);

		String errorText = "";
		errorText = driver.findElement(By.name("account-number-error")).getText();

		assertEquals(errorText, GeneralReusables.reusableStrings.get("invalid-account-number-error"));
	}


	@Test
	@Order(order = 7)
	public void invalidRepeatPassword() {
		goToSignupPage();

		ProfileReusables.enterValidFirstName(driver);
		ProfileReusables.enterValidFamilyName(driver);
		ProfileReusables.enterValidUsername(driver);
		ProfileReusables.enterValidEmail(driver);
		ProfileReusables.enterValidPhoneNumber(driver);
		ProfileReusables.enterValidAccountNumber(driver);
		ProfileReusables.enterValidPassword(driver);


		WebElement password = driver.findElement(By.name("password2"));
		password.clear();
		password.sendKeys(ProfileReusables.notMatchedPassword);  //wrong repeat


		ProfileReusables.clickForSignUp(driver);

		GeneralReusables.waitForSeconds(3);


		//    Verify that error message is displayed for authentication failure.
		String errorText = "";
		errorText = driver.findElement(By.name("password-repeat-error")).getText();

		assertEquals(errorText, ProfileReusables.invalidPasswordRepaetError);
	}


	@Test
	@Order(order = 8)
	public void validSignUp() {
		goToSignupPage();

		ProfileReusables.enterValidFirstName(driver);
		ProfileReusables.enterValidFamilyName(driver);
		ProfileReusables.enterValidUsername(driver);
		ProfileReusables.enterValidEmail(driver);
		ProfileReusables.enterValidPhoneNumber(driver);
		ProfileReusables.enterValidAccountNumber(driver);
		ProfileReusables.enterValidPassword(driver);
		ProfileReusables.repeatValidPassword(driver);

		GeneralReusables.waitForSeconds(10);
		ProfileReusables.clickForSignUp(driver);

		List<WebElement> successMessages = driver.findElements(By.name("success message"));
		assertTrue(successMessages.size()> 0);

	}


	@After
	public void tearDownEach(){
		dbManager = new DBManager();
		dbManager.connect();
		dbManager.deleteCustomers();
	}
	@AfterClass
	public static void tearDown() {
		driver.close();
		dbManager = new DBManager();
		dbManager.connect();
		dbManager.deleteCustomers();
	}


}
