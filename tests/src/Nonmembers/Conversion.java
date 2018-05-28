package Nonmembers;

import Reusables.GeneralReusables;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

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

    @BeforeClass
    public static void setUp() {
        driver = new ChromeDriver();
        GeneralReusables.setUpToHomepage(driver);
        WebElement prices = driver.findElement(By.name("prices"));
        prices.click();
        dropdown = new Select(driver.findElement(By.id("conversion-type")));
        valueBox = driver.findElement(By.name("value"));
        result = driver.findElement(By.name("result"));
        calculateButton = driver.findElement(By.name("calculate"));
    }

    @Test
    public void dollarToRial() {
        dropdown.selectByValue("dollar2rial");
        int twoDollarsPrice = 2 * GeneralReusables.getPrice("dollar");
        valueBox.clear();
        valueBox.sendKeys("2");
        calculateButton.click();
        assertEquals(result.getText(), String.valueOf(twoDollarsPrice));
    }

    @Test
    public void rialToDollar() {
        dropdown.selectByValue("rial2dollar");
        int twoDollarsPrice = 2 * GeneralReusables.getPrice("dollar");
        valueBox.clear();
        valueBox.sendKeys(String.valueOf(twoDollarsPrice));
        calculateButton.clear();
        assertEquals(result.getText(), "2");
    }

    @Test
    public void euroToRial() {
        dropdown.selectByValue("euro2Rial");
        int twoEurosPrice = 2 * GeneralReusables.getPrice("euro");
        valueBox.clear();
        valueBox.sendKeys("2");
        calculateButton.click();
        assertEquals(result.getText(), String.valueOf(twoEurosPrice));
    }

    @Test
    public void rialToEuro() {
        dropdown.selectByValue("rial2euro");
        int threeEurosPrice = 3 * GeneralReusables.getPrice("euro");
        valueBox.clear();
        valueBox.sendKeys(String.valueOf(threeEurosPrice));
        calculateButton.clear();
        assertEquals(result.getText(), "3");
    }

    @AfterClass
    public static void tearDown() {
        driver.close();
    }
}
