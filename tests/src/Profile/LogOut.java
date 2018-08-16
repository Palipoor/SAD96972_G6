package Profile;

import Reusables.GeneralReusables;
import Reusables.ProfileReusables;
import Reusables.WalletUsersReusables;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;

public class LogOut {
    private static WebDriver driver;


    @BeforeClass
    public static void setUp() {
        driver = new FirefoxDriver();
        driver.manage().timeouts().implicitlyWait(100, TimeUnit.SECONDS);
        GeneralReusables.setUpToHomepage(driver);
        GeneralReusables.loginAsACustomer(driver);
    }


    @Test
    public void signOut() {

        WebElement userMenu = driver.findElement(By.name("user menu"));
        userMenu.click();
        WebElement logoutButton = driver.findElement(By.name("logout"));
        logoutButton.click();

		GeneralReusables.waitForSeconds(1);
		assertEquals(driver.getCurrentUrl(),GeneralReusables.reusableStrings.get("homepage") + "/");
    }


    @AfterClass
    public static void tearDown() {
        driver.close();
    }




}
