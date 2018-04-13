import ReUsables.GeneralReusables;
import com.sun.tools.javac.jvm.Gen;
import org.junit.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

/**
 * Created by Golpar on 4/12/2018 AD.
 */
public class WalletExistenceCustomer {

    private WebDriver driver;

    @BeforeClass
    public void setUp() {
        driver = new ChromeDriver();
        GeneralReusables.setUpToHomepage(driver);
        GeneralReusables.loginAsACustomer(driver);
    }

    @Test
    public void postConditionTest() {
        String title = driver.getTitle();
        assertEquals(title, GeneralReusables.PANEL_TITLE);
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
    public void tearDown() {
        GeneralReusables.logout(driver);
    }

}
