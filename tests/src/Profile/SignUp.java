package Profile;


import Reusables.DBManager;
import Reusables.ProfileReusables;
import Reusables.GeneralReusables;
import org.junit.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;

public class SignUp {
	private static WebDriver driver;
	private static DBManager dbManager;

	public static void goToSignupPage() {
		GeneralReusables.setUpToHomepage(driver);
		// Go to Sign up page
		String linkToOpen = driver.findElement(By.name("sign up")).getAttribute("href");
		driver.get(linkToOpen);
	}

	String successTitle = ""; //TODO;


	@BeforeClass
	public static void setUp() {
		// Initialize the WebDriver
		driver = new FirefoxDriver();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		//for checking already registered info
		ProfileReusables.signUpUser1(driver);
		goToSignupPage();


	}

	@Test
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
	public void registeredEmail() {
		goToSignupPage();

		ProfileReusables.enterValidFirstName(driver);
		ProfileReusables.enterValidFamilyName(driver);

		ProfileReusables.enterValidUsername(driver);


		// Enter an already registered email
		WebElement email = driver.findElement(By.name("email"));
		email.sendKeys(ProfileReusables.reusableStrings.get("email"));   //already registered

		ProfileReusables.enterValidPhoneNumber(driver);
		ProfileReusables.enterValidAccountNumber(driver);
		ProfileReusables.enterValidPassword(driver);
		ProfileReusables.repeatValidPassword(driver);

		ProfileReusables.clickForSignUp(driver);

		GeneralReusables.waitForSeconds(3);

		//  Verify that error message is displayed for authentication failure.
		String errorText = "";
		errorText = driver.findElement(By.name("email-error")).getText();


		assertEquals(errorText, GeneralReusables.reusableStrings.get("username-exists"));
	}

	@Test
	public void invalidUsername() {
		goToSignupPage();

		ProfileReusables.enterValidFirstName(driver);
		ProfileReusables.enterValidFamilyName(driver);

		//invalid username
		WebElement username = driver.findElement(By.name("username"));
		username.clear();
		username.sendKeys(ProfileReusables.invalidName);


		ProfileReusables.enterValidEmail(driver);
		ProfileReusables.enterValidPhoneNumber(driver);
		WebElement shomareHesab = driver.findElement(By.name("account_number"));
		shomareHesab.clear();
		shomareHesab.sendKeys("17385962846");
		ProfileReusables.enterValidPassword(driver);
		ProfileReusables.repeatValidPassword(driver);


		ProfileReusables.clickForSignUp(driver);

		GeneralReusables.waitForSeconds(3);

		String errorText = "";
		errorText = driver.findElement(By.name("username-error")).getText();

		assertEquals(errorText, GeneralReusables.reusableStrings.get("invalid-username-error"));
	}

	@Test
	public void registeredUsername() {

		goToSignupPage();


		ProfileReusables.enterValidFirstName(driver);
		ProfileReusables.enterValidFamilyName(driver);

		// Enter an already registered username
		WebElement username = driver.findElement(By.name("username"));
		username.clear();
		username.sendKeys(ProfileReusables.username1);

		ProfileReusables.enterValidEmail(driver);
		ProfileReusables.enterValidPhoneNumber(driver);
		ProfileReusables.enterValidAccountNumber(driver);
		ProfileReusables.enterValidPassword(driver);
		ProfileReusables.repeatValidPassword(driver);

		ProfileReusables.clickForSignUp(driver);

		GeneralReusables.waitForSeconds(3);

		String errorText = "";
		errorText = driver.findElement(By.name("username-error")).getText();

		assertEquals(errorText, GeneralReusables.reusableStrings.get("username-exists"));
	}


	@Test
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
	public void registeredAccountNumber() {
		goToSignupPage();


		ProfileReusables.enterValidFirstName(driver);
		ProfileReusables.enterValidFamilyName(driver);
		ProfileReusables.enterValidUsername(driver);
		ProfileReusables.enterValidEmail(driver);
		ProfileReusables.enterValidPhoneNumber(driver);


		WebElement accountNumber = driver.findElement(By.name("account_number"));
		accountNumber.clear();
		accountNumber.sendKeys(ProfileReusables.reusableStrings.get("account-number"));

		ProfileReusables.enterValidPassword(driver);
		ProfileReusables.repeatValidPassword(driver);

		ProfileReusables.clickForSignUp(driver);

		GeneralReusables.waitForSeconds(3);

		//    Verify that error message is displayed for authentication failure.
		String errorText = "";
		errorText = driver.findElement(By.name("account-number-error")).getText();

		assertEquals(errorText, GeneralReusables.reusableStrings.get("username-exists"));
	}


	@Test

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


		ProfileReusables.clickForSignUp(driver);

		GeneralReusables.waitForSeconds(3);


		//assertEquals(driver.getTitle(), successTitle);
		GeneralReusables.setUpToHomepage(driver);
		GeneralReusables.login(driver, ProfileReusables.reusableStrings.get("email"), ProfileReusables.password1);
		assertEquals(driver.getTitle(), GeneralReusables.reusableStrings.get("panel-title"));


	}


	@AfterClass
	public static void tearDown() {
		driver.close();
		dbManager = new DBManager();
		dbManager.connect();
		dbManager.deleteCustomers();
	}


}
