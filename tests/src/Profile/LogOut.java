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
import org.openqa.selenium.chrome.ChromeDriver;

import static org.junit.Assert.assertEquals;

public class LogOut {
    private WebDriver driver;


    @BeforeClass
    public void setUp() {
        driver = new ChromeDriver();
        GeneralReusables.setUpToHomepage(driver);
        GeneralReusables.loginAsACustomer(driver); //TODO:!!!!!!!!!!!!!
    }

    @Test
    public void preConditionTest() {
        String title = driver.getTitle();
        assertEquals(title, GeneralReusables.PANEL_TITLE);
    }

    @Test
    public void signOut() {

        WebElement user_menu = driver.findElement(By.name("user menu"));
        user_menu.click();

        WebElement logout = driver.findElement(By.name("logout"));
        logout.submit();

        driver.get(ProfileReusables.panelAddress);
        boolean present =  driver.findElements( By.id("log in button") ).size() != 0;
        assertEquals(true, present);



    }


    @AfterClass
    public void tearDown() {


    }




}
