package Management;

import Reusables.GeneralReusables;
import Reusables.ManagerReusables;
import Reusables.Order;
import Reusables.OrderedRunner;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by Golpar on 4/19/2018 AD.
 */
@RunWith(OrderedRunner.class)
public class RemoveAccessCustomer {
    private static WebDriver driver;

    @BeforeClass
    public static void setUp() {
        driver = new FirefoxDriver();
        GeneralReusables.setUpToHomepage(driver);
        GeneralReusables.loginAsTheManager(driver);
		WebElement customer = driver.findElement(By.name("customers"));
		customer.click();
    }
    @Test
    @Order(order = 2)
    public void invalidUsernameTest() {
        WebElement accessRemoveBox = driver.findElement(By.name("access-remove-box"));
        WebElement usernameField = accessRemoveBox.findElement(By.name("username"));
        usernameField.clear();
        usernameField.sendKeys("not-existing-username");
        WebElement reason = accessRemoveBox.findElement(By.name("reason"));
        reason.sendKeys("blah blah");

        WebElement submit = driver.findElement(By.name("remove_access_form"));

		GeneralReusables.waitForSeconds(3);
        submit.click();

        WebElement errorMessage = driver.findElement(By.name("username-error-remove-customer"));
        assertTrue(!errorMessage.getText().equals(""));
        assertTrue(errorMessage.getText().equals(GeneralReusables.reusableStrings.get("customer-not-found")));

    }

    @Test
    @Order(order = 3)
    public void fillTheFormCorrectly() {
        WebElement accessRemoveBox = driver.findElement(By.name("access-remove-box"));
        WebElement usernameField = accessRemoveBox.findElement(By.name("username"));
        usernameField.clear();
        String username = ManagerReusables.createCustomer("desiredPassword");
        usernameField.sendKeys(username);
        WebElement reason = accessRemoveBox.findElement(By.name("reason"));
        reason.sendKeys("blah blah");

        WebElement submit = driver.findElement(By.name("remove_access_form"));
        submit.click();

        assertFalse(successfulLogin(username, "desiredPassword"));
    }

    @AfterClass
    public static void tearDown(){
        GeneralReusables.logout(driver);
    }

    private boolean successfulLogin(String username, String password) {

		driver.get(GeneralReusables.reusableStrings.get("homepage") + "/login");
		GeneralReusables.waitForSeconds(1);
        WebElement the_username = driver.findElement(By.name("username")); //// TODO: 6/2/2018 AD hame beshan username dige.
        the_username.clear();
        the_username.sendKeys(username);

        WebElement passwordElement = driver.findElement(By.name("password"));
        passwordElement.clear();
        passwordElement.sendKeys(password);

        WebElement submitButton = driver.findElement(By.name("log in button"));
        submitButton.click();

		WebElement error = driver.findElement(By.name("error"));
        return !error.isDisplayed();
    }

}

