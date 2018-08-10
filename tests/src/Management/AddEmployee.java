package Management;

import Reusables.GeneralReusables;
import Reusables.ManagerReusables;
import Reusables.ProfileReusables;
import junit.framework.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import static junit.framework.Assert.assertTrue;
import static junit.framework.TestCase.assertEquals;

/**
 * Created by Golpar on 5/2/2018 AD.
 */
public class AddEmployee {
    private static WebDriver driver;
    private static String employeeUsername;

    @BeforeClass
    public static void setUp() {
        driver = new ChromeDriver();
        GeneralReusables.setUpToHomepage(driver);
        GeneralReusables.loginAsTheManager(driver);
        WebElement employee = driver.findElement(By.name("employee"));
        employee.click();
        long currentTime = System.currentTimeMillis();
        String currentTimeString = String.valueOf(currentTime);
        employeeUsername = "test_employee_" + currentTimeString;
    }

    @Test
    public void invalidName() {
        WebElement theForm = driver.findElement(By.name("add-employee"));

        WebElement name = theForm.findElement(By.id("name"));
        name.clear();
        name.sendKeys("!");

        WebElement familyname = theForm.findElement(By.id("familyname"));
        familyname.clear();
        familyname.sendKeys("تست نام خانوادگی");

        WebElement username = theForm.findElement(By.id("employeeUsername"));
        username.clear();
        username.sendKeys(employeeUsername);

        WebElement salary = theForm.findElement(By.id("salary"));
        salary.clear();
        salary.sendKeys("100000");

        WebElement submit = driver.findElement(By.name("submit-button"));
        submit.click();

        WebElement error = driver.findElement(By.name("error"));
        assertEquals(error.getText(), GeneralReusables.reusableStrings.get("invalid-first-name-error"));
    }

    @Test
    public void invalidFamilyName() {
        WebElement theForm = driver.findElement(By.name("add-employee"));

        WebElement name = theForm.findElement(By.id("name"));
        name.clear();
        name.sendKeys("تست نام");

        WebElement familyname = theForm.findElement(By.id("familyname"));
        familyname.clear();
        familyname.sendKeys("!");

        WebElement submit = driver.findElement(By.name("submit-button"));
        submit.click();

        WebElement error = driver.findElement(By.name("error"));
        assertEquals(error.getText(), GeneralReusables.reusableStrings.get("invalid-family-name-error"));

    }

    @Test
    public void invalidUsername() {
        WebElement theForm = driver.findElement(By.name("add-employee"));

        WebElement familyname = theForm.findElement(By.id("familyname"));
        familyname.clear();
        familyname.sendKeys("تست نام خانوادگی");

        WebElement username = theForm.findElement(By.id("employeeUsername"));
        username.clear();
        username.sendKeys("!");

        WebElement submit = driver.findElement(By.name("submit-button"));
        submit.click();

        WebElement error = driver.findElement(By.name("error"));
        assertEquals(error.getText(), GeneralReusables.reusableStrings.get("invalid-username-error"));

    }

    @Test
    public void invalidSalary() {
        WebElement theForm = driver.findElement(By.name("add-employee"));
        WebElement username = theForm.findElement(By.id("employeeUsername"));
        username.clear();
        username.sendKeys(employeeUsername);

        WebElement salary = theForm.findElement(By.id("salary"));
        salary.clear();
        salary.sendKeys("abc");

        WebElement submit = driver.findElement(By.name("submit-button"));
        submit.click();

        WebElement error = driver.findElement(By.name("error"));
        assertEquals(error.getText(), GeneralReusables.reusableStrings.get("invalid-amount-error"));
    }

    @Test
    public void isEmployeeAdded() {

        WebElement theForm = driver.findElement(By.name("add-employee"));
        WebElement salary = theForm.findElement(By.id("salary"));
        salary.clear();
        salary.sendKeys("10000");

        WebElement submit = driver.findElement(By.name("submit-button"));
        submit.click();

        boolean isAdded = ManagerReusables.employeeExists(employeeUsername);
        assertTrue(isAdded);
        assertEquals(ManagerReusables.getSalary(employeeUsername), 10000);
    }

    @Test
    public void existingUsername() {
        WebElement theForm = driver.findElement(By.name("add-employee"));

        WebElement name = theForm.findElement(By.id("name"));
        name.clear();
        name.sendKeys("!");

        WebElement familyname = theForm.findElement(By.id("familyname"));
        familyname.clear();
        familyname.sendKeys("تست نام خانوادگی");

        WebElement username = theForm.findElement(By.id("employeeUsername"));
        username.clear();
        username.sendKeys(employeeUsername);

        WebElement salary = theForm.findElement(By.id("salary"));
        salary.clear();
        salary.sendKeys("100000");

        WebElement submit = driver.findElement(By.name("submit-button"));
        submit.click();

        WebElement error = driver.findElement(By.name("error"));
        assertEquals(error.getText(), GeneralReusables.reusableStrings.get("username-exists"));
    }

}
