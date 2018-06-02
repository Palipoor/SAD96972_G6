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
    }

    @Test
    @Order(order = 1)
    public void preConditionTest() {
        WebElement employee = driver.findElement(By.name("customers"));
        employee.click();
        assertEquals(driver.getTitle(), ManagerReusables.reusableStrings.get("customers-page-title"));
    }

    @Test
    @Order(order = 2)
    public void invalidUsernameTest() {
        WebElement accessRemoveBox = driver.findElement(By.name("access-remove-box"));
        WebElement usernameField = accessRemoveBox.findElement(By.id("username"));
        usernameField.clear();
        usernameField.sendKeys("!");
        WebElement reason = accessRemoveBox.findElement(By.id("why"));
        reason.sendKeys("blah blah");

        WebElement submit = driver.findElement(By.name("submit-button"));
        submit.click();

        WebElement errorMessage = driver.findElement(By.name("error-message-1"));
        assertTrue(!errorMessage.getText().equals(""));
        assertTrue(errorMessage.getText().equals(GeneralReusables.reusableStrings.get("invalid-username-error")));

    }

    @Test
    @Order(order = 3)
    public void fillTheFormCorrectly() {
        WebElement accessRemoveBox = driver.findElement(By.name("access-remove-box"));
        WebElement usernameField = accessRemoveBox.findElement(By.id("username"));
        usernameField.clear();
        String username = ManagerReusables.createCustomer("desiredPassword");
        usernameField.sendKeys(username);
        WebElement reason = accessRemoveBox.findElement(By.id("why"));
        reason.sendKeys("blah blah");

        WebElement submit = driver.findElement(By.name("submit-button"));
        submit.click();

        assertFalse(successfullLogin(username, "desiredPassword"));
    }

    @AfterClass
    public static void tearDown(){
        GeneralReusables.logout(driver);
    }
    private boolean successfullLogin(String username, String password) {
        WebElement email = driver.findElement(By.name("email")); //// TODO: 6/2/2018 AD hame beshan username dige.
        email.clear();
        email.sendKeys(username);

        WebElement passwordElement = driver.findElement(By.name("password"));
        passwordElement.clear();
        passwordElement.sendKeys(password);

        WebElement submitButton = driver.findElement(By.name("log in button"));
        submitButton.click();

        List<WebElement> userMenu = driver.findElements(By.name("user menu"));
        return !(userMenu.size() == 0);
    }

}

