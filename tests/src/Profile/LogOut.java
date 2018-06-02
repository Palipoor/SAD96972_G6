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
       // GeneralReusables.loginAsACustomer(driver); //TODO:!!!!!!!!!!!!!
        GeneralReusables.login(driver, ProfileReusables.reusableStrings.get("email"), ProfileReusables.password1);
    }

    @Test
    public void preConditionTest() {
        String title = driver.getTitle();
        assertEquals(title, GeneralReusables.reusableStrings.get("panel-title"));
    }

    @Test
    public void signOut() {

        WebElement logout = driver.findElement(By.name("logout"));
        logout.click();

       //TODO: finally, how to check?



    }


    @AfterClass
    public static void tearDown() {
        driver.close();
    }




}
