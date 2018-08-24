package Management;

import Reusables.*;
import junit.framework.Assert;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import static junit.framework.Assert.assertTrue;
import static junit.framework.TestCase.assertEquals;

/**
 * Created by Golpar on 5/2/2018 AD.
 */
@RunWith(OrderedRunner.class)
public class AddEmployee {
    private static WebDriver driver;
    private static String employeeUsername;

    @BeforeClass
    public static void setUp() {
        driver = new FirefoxDriver();
        GeneralReusables.setUpToHomepage(driver);
        GeneralReusables.loginAsTheManager(driver);
        WebElement employee = driver.findElement(By.name("employee"));
        employee.click();
        long currentTime = System.currentTimeMillis();
        String currentTimeString = String.valueOf(currentTime);
        employeeUsername = "test_employee_" + currentTimeString;
    }

    @Test
    @Order(order = 1)
    public void invalidName() {
        WebElement theForm = driver.findElement(By.name("add-employee"));

        WebElement name = theForm.findElement(By.name("persian_first_name"));
        name.clear();
        name.sendKeys("!");

        WebElement familyname = theForm.findElement(By.name("persian_last_name"));
        familyname.clear();
        familyname.sendKeys("تست نام خانوادگی");

		WebElement english_name = theForm.findElement(By.name("first_name"));
		english_name.clear();
		english_name.sendKeys("!");

		WebElement english_familyname = theForm.findElement(By.name("last_name"));
		english_familyname.clear();
		english_familyname.sendKeys("test last name");


		WebElement username = theForm.findElement(By.name("username"));
        username.clear();
        username.sendKeys(employeeUsername);

        WebElement salary = theForm.findElement(By.name("current_salary"));
        salary.clear();
        salary.sendKeys("100000");

		WebElement email = theForm.findElement(By.name("email"));
		email.clear();
		long currentTime = System.currentTimeMillis();
		String currentTimeString = String.valueOf(currentTime);
		email.sendKeys("dorna" + currentTimeString+ "@gmail.com");



		WebElement submit = driver.findElement(By.name("add_employee_form"));
        submit.click();
		GeneralReusables.waitForSeconds(1);


		WebElement error = driver.findElement(By.name("persian-first-name-error"));
        assertEquals(error.getText(), GeneralReusables.reusableStrings.get("invalid-persian-name-error"));
    }



    @Test
    @Order(order = 2)
    public void invalidUsername() {
		WebElement theForm = driver.findElement(By.name("add-employee"));

		WebElement name = theForm.findElement(By.name("persian_first_name"));
		name.clear();
		name.sendKeys("تست نام");

		WebElement familyname = theForm.findElement(By.name("persian_last_name"));
		familyname.clear();
		familyname.sendKeys("تست نام خانوادگی");

		WebElement english_name = theForm.findElement(By.name("first_name"));
		english_name.clear();
		english_name.sendKeys("test names");

		WebElement english_familyname = theForm.findElement(By.name("last_name"));
		english_familyname.clear();
		english_familyname.sendKeys("test last name");


		WebElement username = theForm.findElement(By.name("username"));
		username.clear();
		username.sendKeys("!!");

		WebElement salary = theForm.findElement(By.name("current_salary"));
		salary.clear();
		salary.sendKeys("100000");

		WebElement email = theForm.findElement(By.name("email"));
		email.clear();
		long currentTime = System.currentTimeMillis();
		String currentTimeString = String.valueOf(currentTime);
		email.sendKeys("dorna" + currentTimeString+ "@gmail.com");


		WebElement submit = driver.findElement(By.name("add_employee_form"));
		submit.click();

		GeneralReusables.waitForSeconds(1);

		WebElement error = driver.findElement(By.name("username-error"));
        assertEquals(error.getText(), GeneralReusables.reusableStrings.get("invalid-username-error"));

    }


    @Test
    @Order(order = 3)
    public void isEmployeeAdded() {

        WebElement theForm = driver.findElement(By.name("add-employee"));
        WebElement username = theForm.findElement(By.name("username"));
        username.clear();
        username.sendKeys(employeeUsername);

        WebElement submit = driver.findElement(By.name("add_employee_form"));
        submit.click();

		GeneralReusables.waitForSeconds(1);

		boolean isAdded = ManagerReusables.employeeExists(employeeUsername);
        assertTrue(isAdded);
        assertEquals(ManagerReusables.getSalary(employeeUsername), 100000);
    }

    @Test
    @Order(order = 4)
    public void existingUsername() {
		WebElement theForm = driver.findElement(By.name("add-employee"));

		WebElement name = theForm.findElement(By.name("persian_first_name"));
		name.clear();
		name.sendKeys("تست نام");

		WebElement familyname = theForm.findElement(By.name("persian_last_name"));
		familyname.clear();
		familyname.sendKeys("تست نام خانوادگی");

		WebElement english_name = theForm.findElement(By.name("first_name"));
		english_name.clear();
		english_name.sendKeys("!");

		WebElement english_familyname = theForm.findElement(By.name("last_name"));
		english_familyname.clear();
		english_familyname.sendKeys("test last name");


		WebElement username = theForm.findElement(By.name("username"));
		username.clear();
		username.sendKeys(employeeUsername);

		WebElement salary = theForm.findElement(By.name("current_salary"));
		salary.clear();
		salary.sendKeys("100000");

		WebElement email = theForm.findElement(By.name("email"));
		email.clear();
		long currentTime = System.currentTimeMillis();
		String currentTimeString = String.valueOf(currentTime);
		email.sendKeys("dorna" + currentTimeString+ "@gmail.com");

		WebElement submit = driver.findElement(By.name("add_employee_form"));
		submit.click();

		GeneralReusables.waitForSeconds(1);


		WebElement error = driver.findElement(By.name("username-error"));
        assertEquals(error.getText(), GeneralReusables.reusableStrings.get("username-exists"));
    }


    @AfterClass
    public static void TearDown() {
        GeneralReusables.logout(driver);
    }
}
