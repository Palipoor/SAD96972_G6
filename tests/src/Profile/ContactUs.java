import Reusables.GeneralReusables;
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
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by Golpar on 4/20/2018 AD.
 */
@RunWith(OrderedRunner.class)
public class ContactUs {
    private static WebDriver driver;
    private static WebElement theForm;
    private String theSubject;

    @BeforeClass
    public static void setUp() {
        driver = new ChromeDriver();
        GeneralReusables.setUpToHomepage(driver);
//        JavascriptExecutor jse = (JavascriptExecutor)driver;
//        jse.executeScript("window.scrollBy(0,250)", "");
//        WebElement contactUs = driver.findElement(By.name("contact"));
//        contactUs.click();
        theForm = driver.findElement(By.name("contact-form"));
    }

    @Test
    @Order(order = 1)
    public void emptyNameTest() {
        WebElement email = theForm.findElement(By.id("email"));
        email.clear();
        email.sendKeys("palr@gmail.com");

        WebElement subject = theForm.findElement(By.id("subject"));
        subject.clear();
        long currentTime = System.currentTimeMillis();
        String currentTimeString = String.valueOf(currentTime);
        theSubject = "test_subject_" + currentTimeString;
        subject.sendKeys(theSubject);

        WebElement content = theForm.findElement(By.id("message"));
        content.clear();
        content.sendKeys("my message");

        WebElement sendButton = theForm.findElement(By.name("send message"));
        sendButton.click();

        WebElement nameError = driver.findElement(By.name("name-error"));
        assertTrue(nameError.isDisplayed());
    }

    @Test
    @Order(order = 2)
    public void emptyEmailTest() {
        WebElement email = theForm.findElement(By.id("email"));
        email.clear();

        WebElement name = theForm.findElement(By.id("name"));
        name.clear();
        name.sendKeys("تست نام");

        WebElement subject = theForm.findElement(By.id("subject"));
        subject.clear();
        long currentTime = System.currentTimeMillis();
        String currentTimeString = String.valueOf(currentTime);
        theSubject = "test_subject_" + currentTimeString;
        subject.sendKeys(theSubject);

        WebElement content = theForm.findElement(By.id("message"));
        content.clear();
        content.sendKeys("my message");

        WebElement sendButton = theForm.findElement(By.name("send message"));
        sendButton.click();

        WebElement emailError = driver.findElement(By.name("email-error"));
        assertTrue(emailError.isDisplayed());
    }

    @Test
    @Order(order = 3)
    public void invalidEmailTest() {
        WebElement email = theForm.findElement(By.id("email"));
        email.clear();
        email.sendKeys("invalidemail");

        WebElement name = theForm.findElement(By.id("name"));
        name.clear();
        name.sendKeys("تست نام");

        WebElement subject = theForm.findElement(By.id("subject"));
        subject.clear();
        long currentTime = System.currentTimeMillis();
        String currentTimeString = String.valueOf(currentTime);
        theSubject = "test_subject_" + currentTimeString;
        subject.sendKeys(theSubject);

        WebElement content = theForm.findElement(By.id("message"));
        content.clear();
        content.sendKeys("my message");

        WebElement sendButton = theForm.findElement(By.name("send message"));
        sendButton.click();

        WebElement emailError = driver.findElement(By.name("email-val-error"));
        assertTrue(emailError.isDisplayed());
    }

    @Test
    @Order(order = 4)
    public void emptyContentTest() {
        WebElement email = theForm.findElement(By.id("email"));
        email.clear();
        email.sendKeys("palipoor976@gmail.com");

        WebElement name = theForm.findElement(By.id("name"));
        name.clear();
        name.sendKeys("تست نام");

        WebElement subject = theForm.findElement(By.id("subject"));
        subject.clear();
        long currentTime = System.currentTimeMillis();
        String currentTimeString = String.valueOf(currentTime);
        theSubject = "test_subject_" + currentTimeString;
        subject.sendKeys(theSubject);

        WebElement content = theForm.findElement(By.id("message"));
        content.clear();

        WebElement sendButton = theForm.findElement(By.name("send message"));
        sendButton.click();

        WebElement messageError = driver.findElement(By.name("message-error"));
        assertTrue(messageError.isDisplayed());
    }

    @Test
    @Order(order = 5)
    public void everythingValidTest() {
        WebElement email = theForm.findElement(By.id("email"));
        email.clear();
        email.sendKeys("palipoor976@gmail.com");

        WebElement name = theForm.findElement(By.id("name"));
        name.clear();
        name.sendKeys("تست نام");

        WebElement subject = theForm.findElement(By.id("subject"));
        subject.clear();
        long currentTime = System.currentTimeMillis();
        String currentTimeString = String.valueOf(currentTime);
        theSubject = "test_subject_" + currentTimeString;
        subject.sendKeys(theSubject);

        WebElement content = theForm.findElement(By.id("message"));
        content.clear();
        content.sendKeys("my message");

        WebElement sendButton = theForm.findElement(By.name("send message"));
        sendButton.click();

        WebElement successMessage = driver.findElement(By.name("success"));
        assertFalse(successMessage.getText().equals(""));
        //// TODO: 4/20/2018 AD find a way to login and check wether the email is received
    }

    @Test
    @Order(order = 6)
    public void isReceivedTest() {

        String url = "https://accounts.google.com/signin";
        driver.get(url);
        WebElement email_phone = driver.findElement(By.xpath("//input[@id='identifierId']"));
        email_phone.sendKeys("palipoor976");
        driver.findElement(By.id("identifierNext")).click();
        WebElement password = driver.findElement(By.xpath("//input[@name='password']"));
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.elementToBeClickable(password));
        password.sendKeys("mardechini");
        driver.findElement(By.id("passwordNext")).click();

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
