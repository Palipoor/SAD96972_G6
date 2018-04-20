package Nonmembers;

import Reusables.GeneralReusables;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by Golpar on 4/20/2018 AD.
 */
public class ContactUs {
    private WebElement theForm;
    private String theSubject;

    @BeforeClass
    public void setUp() {
        WebDriver driver = new ChromeDriver();
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


}
