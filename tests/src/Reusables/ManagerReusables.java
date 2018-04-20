package Reusables;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.List;

/**
 * Created by Golpar on 4/19/2018 AD.
 */
public class ManagerReusables {

    public static String EMPLOYEES_PAGE_TITLE = "پنل مدیریت | کارمندان";
    public static String CUSTOMERS_PAGE_TITLE = "پنل مدیریت | مشتریان";

    public static boolean customerExists(String username) {
        WebDriver driver = new ChromeDriver();
        GeneralReusables.setUpToHomepage(driver);
        GeneralReusables.loginAsTheManager(driver);

        WebElement customers = driver.findElement(By.name("customers"));
        customers.click();
        WebElement customersTable = driver.findElement(By.name("customers-table"));
        WebElement usernameSearchBox = customersTable.findElement(By.name("نام کاربری"));
        usernameSearchBox.clear();
        usernameSearchBox.sendKeys(username);

        List<WebElement> tableRows = customersTable.findElements(By.xpath("//tbody//tr"));
        driver.close();
        return (tableRows.size() > 0);
    }

    public static boolean employeeExists(String username) {
        WebDriver driver = new ChromeDriver();
        GeneralReusables.setUpToHomepage(driver);
        GeneralReusables.loginAsTheManager(driver);

        WebElement employee = driver.findElement(By.name("employee"));
        employee.click();
        WebElement employeetable = driver.findElement(By.name("employees-table"));
        WebElement usernameSearchBox = employeetable.findElement(By.name("نام کاربری"));
        usernameSearchBox.clear();
        usernameSearchBox.sendKeys(username);

        List<WebElement> tableRows = employeetable.findElements(By.xpath("//tbody//tr"));
        driver.close();
        return (tableRows.size() > 0);
    }

    public static String getAnEmployee() {

        WebDriver driver = new ChromeDriver();
        GeneralReusables.setUpToHomepage(driver);
        GeneralReusables.loginAsTheManager(driver);
        WebElement employee = driver.findElement(By.name("employee"));
        employee.click();
        WebElement employeetable = driver.findElement(By.name("employees-table"));
        List<WebElement> tableRows = employeetable.findElements(By.xpath("//tbody//tr"));

        if (tableRows.size() == 0) {
            return createEmployee(driver);
        } else {
            List<WebElement> tableHeader = employeetable.findElements(By.xpath("//thead//tr"));
            List<WebElement> headerTitles = tableHeader.get(0).findElements(By.xpath("//th"));
            int usernameIndex = 0;
            for (int i = 0; i < headerTitles.size(); i++) {
                if (headerTitles.get(i).getText().equals("نام کاربری")) {
                    usernameIndex = i;
                }
            }
            List<WebElement> employeeDetails = tableRows.get(0).findElements(By.xpath("//td"));
            return employeeDetails.get(usernameIndex).getText();
        }
    }

    private static String createEmployee(WebDriver driver) {
        long currentTime = System.currentTimeMillis();
        String currentTimeString = String.valueOf(currentTime);
        String newUsername = "test_employee_" + currentTimeString;
        WebElement theForm = driver.findElement(By.name("add-employee"));

        WebElement name = theForm.findElement(By.id("name"));
        name.clear();
        name.sendKeys("تست نام");

        WebElement familyname = theForm.findElement(By.id("familyname"));
        familyname.clear();
        familyname.sendKeys("تست نام خانوادگی");

        WebElement username = theForm.findElement(By.id("username"));
        username.clear();
        username.sendKeys(newUsername);

        WebElement salary = theForm.findElement(By.id("salary"));
        salary.clear();
        salary.sendKeys("100000");

        return newUsername;
    }

    public static int getACustomerId() { // چک می‌کنه اگر کاستومر داشتیم آیدیش رو می‌ده وگرنه یکی می‌سازه.
        int id = 0;
        return id; //// TODO: 4/19/2018 AD complete
    }


    public static String getACustomerUsername() { // چک می‌کنه اگر کاستومر داشتیم آیدیش رو می‌ده وگرنه یکی می‌سازه.
        return ""; //// TODO: 4/19/2018 AD complete
    }

    public static int getSalary(String employeeUsername) {
        return 1; //// TODO: 4/19/2018 AD complete
    }

    public static boolean reportExists(String id, String reason, String username) {
        return true; //// TODO: 4/19/2018 AD complete
    }

    public int getNewestTransactionId() {
        return 0;
    }

}
