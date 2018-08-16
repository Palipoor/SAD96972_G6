package Wallet;

import Reusables.GeneralReusables;
import org.junit.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

/**
 * Created by Golpar on 4/12/2018 AD.
 */
public class WalletExistenceCustomer {

    private static WebDriver driver;

    @BeforeClass
    public static void setUp() {
        driver = new FirefoxDriver();
        GeneralReusables.setUpToHomepage(driver);
        GeneralReusables.loginAsACustomer(driver);
    }


    @Test
    public void rialWalletExistenceTest() {
        List<WebElement> rialWallets = driver.findElements(By.name("rial-wallet"));
        assertNotEquals(rialWallets.size(), 0);// exists such element in the page
    }

    @Test
    public void dollarWalletExistenceTest() {
        List<WebElement> dollarWallets = driver.findElements(By.name("dollar-wallet"));
        assertNotEquals(dollarWallets.size(), 0);// exists such element in the page
    }

    @Test
    public void euroWalletExistenceTest() {
        List<WebElement> euroWallets = driver.findElements(By.name("euro-wallet"));
        assertNotEquals(euroWallets.size(), 0);// exists such element in the page
    }

    @AfterClass
    public static void tearDown() {
        GeneralReusables.logout(driver);
    }

}
