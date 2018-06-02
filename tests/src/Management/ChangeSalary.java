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

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by Golpar on 4/19/2018 AD.
 */
@RunWith(OrderedRunner.class)
public class ChangeSalary {
    private static WebDriver driver;
    private static String employeeUsername;

    @BeforeClass
    public static void setUp() {
        driver = new FirefoxDriver();
        GeneralReusables.setUpToHomepage(driver);
        GeneralReusables.loginAsTheManager(driver);

        employeeUsername = ManagerReusables.getAnEmployee();
    }

    @Test
    @Order(order = 1)
    public void preConditionTest() {
        WebElement employee = driver.findElement(By.name("employee"));
        employee.click();
        assertEquals(driver.getTitle(), ManagerReusables.EMPLOYEES_PAGE_TITLE);
    }

    @Test
    @Order(order = 2)
    public void invalidUsernameTest() {
        WebElement salaryChangeBox = driver.findElement(By.name("salary-change-box"));
        WebElement usernameInput = salaryChangeBox.findElement(By.id("employee-username-salary"));
        usernameInput.sendKeys("invalid_username");
        WebElement salaryInput = salaryChangeBox.findElement(By.id("salary"));
        salaryInput.sendKeys("10000");
        WebElement setKey = salaryChangeBox.findElement(By.id("set-button"));
        setKey.click();

        WebElement message = driver.findElement(By.name("message"));
        assertTrue(!message.getText().equals("") && message.getText().equals(GeneralReusables.reusableStrings.get("invalid-username-error")));
    }

    @Test
    @Order(order = 3)
    public void changeSalaryTest() {
        WebElement salaryChangeBox = driver.findElement(By.name("salary-change-box"));

        WebElement usernameInput = salaryChangeBox.findElement(By.id("employee-username-salary"));
        usernameInput.clear();
        usernameInput.sendKeys(employeeUsername);

        int formerSalary = ManagerReusables.getSalary(employeeUsername);
        int anotherSalary = formerSalary + 100;
        WebElement salaryInput = salaryChangeBox.findElement(By.id("salary"));
        salaryInput.clear();
        salaryInput.sendKeys(String.valueOf(anotherSalary));

        WebElement setKey = salaryChangeBox.findElement(By.id("set-button"));
        setKey.click();

        int newSalary = ManagerReusables.getSalary(employeeUsername);
        assertEquals(newSalary, anotherSalary);
    }

    @AfterClass
    public static void tearDown() {
        GeneralReusables.logout(driver);
    }
}
