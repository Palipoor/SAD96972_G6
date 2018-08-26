package Nonmembers;

import Reusables.GeneralReusables;
import Reusables.Order;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by Golpar on 4/20/2018 AD.
 */
@RunWith(Reusables.OrderedRunner.class)
public class ContactUs {
	private static WebDriver driver;
	private static WebElement theForm;
	private String theSubject;

	private static JavascriptExecutor js;

	@BeforeClass
	public static void setUp() {
		driver = new FirefoxDriver();
		GeneralReusables.setUpToHomepage(driver);
		js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollBy(0,1500)");
		GeneralReusables.waitForSeconds(5);
		WebElement contactUs = driver.findElement(By.name("contact"));
		contactUs.click();
		theForm = driver.findElement(By.name("contact-form"));
	}

	@Test
	@Order(order = 1)
	public void emptyNameTest() {
		WebElement email = theForm.findElement(By.name("email"));
		GeneralReusables.waitForSeconds(2);
		email.clear();
		email.sendKeys("palr@gmail.com");

		WebElement subject = theForm.findElement(By.name("subject"));
		subject.clear();
		long currentTime = System.currentTimeMillis();
		String currentTimeString = String.valueOf(currentTime);
		theSubject = "test_subject_" + currentTimeString;
		subject.sendKeys(theSubject);

		WebElement content = theForm.findElement(By.name("message"));
		content.clear();
		content.sendKeys("my message");

		WebElement sendButton = theForm.findElement(By.name("contact_form"));
		sendButton.click();

		js.executeScript("window.scrollBy(0,5000)");

		GeneralReusables.waitForSeconds(5);

		WebElement nameError = driver.findElement(By.name("name-error"));

		assertTrue(nameError.isDisplayed());
	}

	@Test
	@Order(order = 2)
	public void emptyEmailTest() {
		theForm = driver.findElement(By.name("contact-form"));
		WebElement email = theForm.findElement(By.name("email"));
		GeneralReusables.waitForSeconds(2);
		email.clear();

		WebElement name = theForm.findElement(By.name("name"));
		name.clear();
		name.sendKeys("تست نام");

		WebElement subject = theForm.findElement(By.name("subject"));
		subject.clear();
		long currentTime = System.currentTimeMillis();
		String currentTimeString = String.valueOf(currentTime);
		theSubject = "test_subject_" + currentTimeString;
		subject.sendKeys(theSubject);

		WebElement content = theForm.findElement(By.name("message"));
		content.clear();
		content.sendKeys("my message");

		WebElement sendButton = theForm.findElement(By.name("contact_form"));
		sendButton.click();
		js.executeScript("window.scrollBy(0,5000)");

		WebElement emailError = driver.findElement(By.name("email-error"));
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		assertTrue(emailError.isDisplayed());
	}

	@Test
	@Order(order = 3)
	public void invalidEmailTest() {
		theForm = driver.findElement(By.name("contact-form"));

		WebElement email = theForm.findElement(By.name("email"));
		GeneralReusables.waitForSeconds(2);
		email.clear();
		email.sendKeys("invalidemail");

		WebElement name = theForm.findElement(By.name("name"));
		name.clear();
		name.sendKeys("تست نام");

		WebElement subject = theForm.findElement(By.name("subject"));
		subject.clear();
		long currentTime = System.currentTimeMillis();
		String currentTimeString = String.valueOf(currentTime);
		theSubject = "test_subject_" + currentTimeString;
		subject.sendKeys(theSubject);

		WebElement content = theForm.findElement(By.name("message"));
		content.clear();
		content.sendKeys("my message");

		WebElement sendButton = theForm.findElement(By.name("contact_form"));
		sendButton.click();

		GeneralReusables.waitForSeconds(5);
		js.executeScript("window.scrollBy(0,5000)");


		WebElement emailError = driver.findElement(By.name("email-error"));
		GeneralReusables.waitForSeconds(1);
		assertTrue(emailError.isDisplayed());
	}

	@Test
	@Order(order = 4)
	public void emptyContentTest() {
		theForm = driver.findElement(By.name("contact-form"));

		WebElement email = theForm.findElement(By.name("email"));
		GeneralReusables.waitForSeconds(2);
		email.clear();
		email.sendKeys("palipoor976@gmail.com");

		WebElement name = theForm.findElement(By.name("name"));
		name.clear();
		name.sendKeys("تست نام");

		WebElement subject = theForm.findElement(By.name("subject"));
		subject.clear();
		long currentTime = System.currentTimeMillis();
		String currentTimeString = String.valueOf(currentTime);
		theSubject = "test_subject_" + currentTimeString;
		subject.sendKeys(theSubject);

		WebElement content = theForm.findElement(By.name("message"));
		content.clear();

		GeneralReusables.waitForSeconds(5);

		WebElement sendButton = theForm.findElement(By.name("contact_form"));
		sendButton.click();

		js.executeScript("window.scrollBy(0,5000)");

		WebElement messageError = driver.findElement(By.name("message-error"));
		GeneralReusables.waitForSeconds(5);
		assertTrue(messageError.isDisplayed());
	}

	@Test
	@Order(order = 5)
	public void everythingValidTest() {
		theForm = driver.findElement(By.name("contact-form"));

		WebElement email = theForm.findElement(By.name("email"));
		GeneralReusables.waitForSeconds(2);
		email.clear();
		email.sendKeys("palipoor976@gmail.com");

		WebElement name = theForm.findElement(By.name("name"));
		name.clear();
		name.sendKeys("تست نام");

		WebElement subject = theForm.findElement(By.name("subject"));
		subject.clear();
		long currentTime = System.currentTimeMillis();
		String currentTimeString = String.valueOf(currentTime);
		theSubject = "test_subject_" + currentTimeString;
		subject.sendKeys(theSubject);

		WebElement content = theForm.findElement(By.name("message"));
		content.clear();
		content.sendKeys("my message");
		GeneralReusables.waitForSeconds(5);

		WebElement sendButton = theForm.findElement(By.name("contact_form"));
		sendButton.click();

		js.executeScript("window.scrollBy(0,5000)");

		WebElement successMessage = driver.findElement(By.name("success"));
		assertNotEquals(successMessage, "");
	}

	@Test
	@Order(order = 6)
	public void isReceivedTest() {

		String url = "http://accounts.google.com/signin";
		driver.get(url);
		WebElement email_phone = driver.findElement(By.xpath("//input[@id='identifierId']"));
		email_phone.sendKeys("palipoor976");
		driver.findElement(By.name("identifierNext")).click();
		WebElement password = driver.findElement(By.xpath("//input[@name='password']"));
		WebDriverWait wait = new WebDriverWait(driver, 10);
		wait.until(ExpectedConditions.elementToBeClickable(password));
		password.sendKeys("mardechini");
		GeneralReusables.waitForSeconds(3);
		driver.findElement(By.id("passwordNext")).click();
		driver.get("http://gmail.com");
		List<WebElement> emails = driver.findElements(By.cssSelector("div.xT>div.y6>span>b"));

		boolean isReceived = false;
		for (WebElement emailsub : emails) {
			if (emailsub.getText().equals(theSubject)) {
				emailsub.click();
				isReceived = true;
			}
		}

		assertTrue(isReceived);
	}

	@AfterClass
	public static void tearDown() {
		driver.close();
	}
}
