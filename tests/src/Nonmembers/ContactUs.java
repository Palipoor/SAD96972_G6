package Nonmembers;

import Reusables.GeneralReusables;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by Golpar on 4/20/2018 AD.
 */
public class ContactUs {
    private static WebDriver driver;
    private static WebElement theForm;
    private String theSubject;

    @BeforeClass
    public static void setUp() {
        driver = new ChromeDriver();
        GeneralReusables.setUpToHomepage(driver);
        WebElement contactUs = driver.findElement(By.name("contact"));
        contactUs.click();
        theForm = driver.findElement(By.name("contact-form"));
    }

    @Test
    public void emptyNameTest() {
        WebElement email = theForm.findElement(By.id("email"));
        email.clear();
        email.sendKeys("palipoor@ce.sharif.edu");

        WebElement subject = theForm.findElement(By.id("subject"));
        subject.clear();
        long currentTime = System.currentTimeMillis();
        String currentTimeString = String.valueOf(currentTime);
        theSubject = "test_subject_" + currentTimeString;
        subject.sendKeys(theSubject);

        WebElement content = theForm.findElement(By.id("message"));
        content.clear();
        content.sendKeys("my message");

        WebElement sendButton = theForm.findElement(By.name("end message"));
        sendButton.click();

        WebElement nameError = theForm.findElement(By.name("name-error"));
        assertTrue(nameError.isDisplayed());
    }

    @Test
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

        WebElement emailError = theForm.findElement(By.name("email-error"));
        assertTrue(emailError.isDisplayed());
    }

    @Test
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

        WebElement emailError = theForm.findElement(By.name("email-val-error"));
        assertTrue(emailError.isDisplayed());
    }

    @Test
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

        WebElement messageError = theForm.findElement(By.name("message-error"));
        assertTrue(messageError.isDisplayed());
    }

    @Test
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

        WebElement successMessage = theForm.findElement(By.name("success"));
        assertFalse(successMessage.getText().equals(""));
        //// TODO: 4/20/2018 AD find a way to login and check wether the email is received
    }

    @Test
    public void isReceivedTest() {
        driver.get("https://accounts.google.com/ServiceLogin?service=mail&passive=true&rm=false&continue=https%3A%2F%2Fmail.google.com%2Fmail%2F&ss=1&scc=1&ltmpl=default&ltmplcache=2&hl=en&emr=1&elo=1");
        driver.findElement(By.id("Email")).sendKeys("palipoor976");
        driver.findElement(By.xpath("//input[@id='Passwd']")).sendKeys("mardechini");
        driver.findElement(By.xpath("//input[@type='checkbox']")).click();
        driver.findElement(By.name("signIn")).click();

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
