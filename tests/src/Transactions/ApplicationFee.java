package Transactions;

import Reusables.GeneralReusables;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

/**
 * Created by Golpar on 5/2/2018 AD.
 */
public class ApplicationFee {
    private static WebDriver driver;

    @BeforeClass
    public static void setUp() {
        driver = new ChromeDriver();
        GeneralReusables.setUpToHomepage(driver);
        GeneralReusables.loginAsACustomer(driver);
        WebElement applicationFee = driver.findElement(By.name("application-fee"));
        applicationFee.click();
    }

    @Test
    public void invalidAmount() {
        WebElement universityName = driver.findElement(By.name("university-name"));
        universityName.clear();
        universityName.sendKeys("ETHZ");

        WebElement amount = driver.findElement(By.name("amount"));
        amount.clear();
        amount.sendKeys("abc");

        WebElement currency = driver.findElement(By.name("dollar-radio"));
        currency.click();

        WebElement link = driver.findElement(By.name("link"));
        link.clear();
        link.sendKeys("www.ethz.ch");

        WebElement username = driver.findElement(By.name("username"));
        username.clear();
        username.sendKeys("myusername");

        WebElement password = driver.findElement(By.name("password"));
        password.clear();
        password.sendKeys("myusername");

        WebElement submit = driver.findElement(By.name("submit-button"));
        submit.click();

        WebElement error = driver.findElement(By.name("amount-error"));


    }

    @Test
    public void moreThanCredit() {

    }

    @Test
    public void transactionIsAdded() {

    }

    @AfterClass
    public static void tearDown() {
        GeneralReusables.logout(driver);
    }
}
