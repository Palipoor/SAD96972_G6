package Nonmembers;

import Reusables.GeneralReusables;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;

import static junit.framework.Assert.assertTrue;
import static junit.framework.TestCase.assertEquals;


/**
 * Created by Golpar on 4/21/2018 AD.
 */
public class Conversion {
    private static WebDriver driver;
    private static Select dropdown;
    private static WebElement valueBox;
    private static WebElement result;
    private static WebElement calculateButton;
	private static JavascriptExecutor js;


	@BeforeClass
    public static void setUp() {
        driver = new FirefoxDriver();
        GeneralReusables.setUpToHomepage(driver);
        js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0,4500)");
        GeneralReusables.waitForSeconds(5);
        WebElement prices = driver.findElement(By.name("prices"));
        prices.click();
        dropdown = new Select(driver.findElement(By.name("conversion_type")));
    }

    @Test
    public void dollarToRial() {
		valueBox = driver.findElement(By.name("amount"));
		calculateButton = driver.findElement(By.name("convert_form"));
		dropdown = new Select(driver.findElement(By.name("conversion_type")));
		dropdown.selectByValue("dollar2rial");
        int twoDollarsPrice = 2 * GeneralReusables.getPrice("dollar");
        valueBox.clear();
        valueBox.sendKeys("2");
        calculateButton.click();
		GeneralReusables.waitForSeconds(1);
		js.executeScript("window.scrollBy(0,4500)");
		GeneralReusables.waitForSeconds(10);
		result = driver.findElement(By.name("result"));
		assertTrue(result.getText().contains(String.valueOf(twoDollarsPrice)));
    }

    @Test
    public void rialToDollar() {
		valueBox = driver.findElement(By.name("amount"));
		calculateButton = driver.findElement(By.name("convert_form"));
		dropdown = new Select(driver.findElement(By.name("conversion_type")));
		dropdown.selectByValue("rial2dollar");
        int twoDollarsPrice = 2 * GeneralReusables.getPrice("dollar");
        valueBox.clear();
        valueBox.sendKeys(String.valueOf(twoDollarsPrice));
        calculateButton.click();
		GeneralReusables.waitForSeconds(1);
		js.executeScript("window.scrollBy(0,4500)");
		result = driver.findElement(By.name("result"));
		assertTrue(result.getText().contains("2"));
    }

    @Test
    public void euroToRial() {
		valueBox = driver.findElement(By.name("amount"));
		calculateButton = driver.findElement(By.name("convert_form"));
		dropdown = new Select(driver.findElement(By.name("conversion_type")));
		dropdown.selectByValue("euro2rial");
        int twoEurosPrice = 2 * GeneralReusables.getPrice("euro");
        valueBox.clear();
        valueBox.sendKeys("2");
        calculateButton.click();
		GeneralReusables.waitForSeconds(1);
		js.executeScript("window.scrollBy(0,4500)");
		result = driver.findElement(By.name("result"));
		assertTrue(result.getText().contains(String.valueOf(twoEurosPrice)));
    }

    @Test
    public void rialToEuro() {
		valueBox = driver.findElement(By.name("amount"));
		calculateButton = driver.findElement(By.name("convert_form"));
		dropdown = new Select(driver.findElement(By.name("conversion_type")));
		dropdown.selectByValue("rial2euro");
        int threeEurosPrice = 3 * GeneralReusables.getPrice("euro");
        valueBox.clear();
        valueBox.sendKeys(String.valueOf(threeEurosPrice));
        calculateButton.click();
		GeneralReusables.waitForSeconds(1);
		js.executeScript("window.scrollBy(0,4500)");
		result = driver.findElement(By.name("result"));
		assertTrue(result.getText().contains(String.valueOf("3")));
    }

    @AfterClass
    public static void tearDown() {
        driver.close();
    }
}
