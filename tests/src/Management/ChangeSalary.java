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
		WebElement employee = driver.findElement(By.name("employee"));
		employee.click();

	}


    @Test
    @Order(order = 2)
    public void invalidUsernameTest() {
        WebElement salaryChangeBox = driver.findElement(By.name("salary-change-box"));
        WebElement usernameInput = salaryChangeBox.findElement(By.name("username"));
        usernameInput.sendKeys("invalid_username");
        WebElement salaryInput = salaryChangeBox.findElement(By.name("current_salary"));
        salaryInput.sendKeys("10000");
        WebElement setKey = salaryChangeBox.findElement(By.name("change_salary_form"));
        setKey.click();

        WebElement message = driver.findElement(By.name("username-error-change-salary"));
        assertTrue(!message.getText().equals(""));
		System.out.println(message.getText());
		assertEquals(message.getText(),GeneralReusables.reusableStrings.get("employee-not-found"));
    }

    @Test
    @Order(order = 3)
    public void changeSalaryTest() {
        WebElement salaryChangeBox = driver.findElement(By.name("salary-change-box"));

        WebElement usernameInput = salaryChangeBox.findElement(By.name("username"));
        usernameInput.clear();
        usernameInput.sendKeys(employeeUsername);

        int formerSalary = ManagerReusables.getSalary(employeeUsername);
        int anotherSalary = formerSalary + 10000;
        WebElement salaryInput = salaryChangeBox.findElement(By.name("current_salary"));
        salaryInput.clear();
        salaryInput.sendKeys(String.valueOf(anotherSalary));

        WebElement setKey = salaryChangeBox.findElement(By.name("change_salary_form"));
        setKey.click();

        int newSalary = ManagerReusables.getSalary(employeeUsername);
        assertEquals(newSalary, anotherSalary);
    }

    @AfterClass
    public static void tearDown() {
        GeneralReusables.logout(driver);
    }
}
