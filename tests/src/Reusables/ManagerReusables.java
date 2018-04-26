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
        GeneralReusables.logout(driver);
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
        GeneralReusables.logout(driver);
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
            GeneralReusables.logout(driver);
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
        GeneralReusables.logout(driver);
        return newUsername;
    }

    public static String getACustomerId() { // چک می‌کنه اگر کاستومر داشتیم آیدیش رو می‌ده وگرنه یکی می‌سازه.
        WebDriver driver = new ChromeDriver();
        GeneralReusables.setUpToHomepage(driver);
        GeneralReusables.loginAsTheManager(driver);
        WebElement customers = driver.findElement(By.name("customers"));
        customers.click();
        WebElement customersTable = driver.findElement(By.name("customers-table"));
        List<WebElement> tableRows = customersTable.findElements(By.xpath("//tbody//tr"));

        if (tableRows.size() == 0) {
            return createCustomer();
        } else {
            List<WebElement> tableHeader = customersTable.findElements(By.xpath("//thead//tr"));
            List<WebElement> headerTitles = tableHeader.get(0).findElements(By.xpath("//th"));
            int idIndex = 0;
            for (int i = 0; i < headerTitles.size(); i++) {
                if (headerTitles.get(i).getText().equals("شناسه مشتری")) {
                    idIndex = i;
                }
            }
            List<WebElement> employeeDetails = tableRows.get(0).findElements(By.xpath("//td"));
            GeneralReusables.logout(driver);
            return employeeDetails.get(idIndex).getText();
        }
    }

    private static String createCustomer() {
        long currentTime = System.currentTimeMillis();
        String currentTimeString = String.valueOf(currentTime);
        String newUsername = "test_customer_" + currentTimeString;
        String newEmail = "test_customer_" + currentTimeString + "@gmail.com";

        WebDriver driver = new ChromeDriver();
        GeneralReusables.setUpToHomepage(driver);
        WebElement signupButton = driver.findElement(By.name("sign up"));
        signupButton.click();

        WebElement name = driver.findElement(By.id("first name"));
        name.clear();
        name.sendKeys("تست نام");
        WebElement familyname = driver.findElement(By.id("family name"));
        familyname.clear();
        familyname.sendKeys("تست نام خانوادگی");
        WebElement username = driver.findElement(By.id("username"));
        username.clear();
        username.sendKeys(newUsername);
        WebElement email = driver.findElement(By.id("email"));
        email.clear();
        email.sendKeys(newEmail);
        WebElement number = driver.findElement(By.id("contact number"));
        number.clear();
        number.sendKeys("09379605628");
        WebElement account = driver.findElement(By.id("account number"));
        account.clear();
        account.sendKeys("10203040");
        WebElement password = driver.findElement(By.id("password"));
        password.clear();
        password.sendKeys("passwordpassword");
        WebElement passwordAgain = driver.findElement(By.id("password repeat"));
        passwordAgain.clear();
        passwordAgain.sendKeys("passwordpassword");

        WebElement iAgree = driver.findElement(By.name("i-agree"));
        iAgree.click();

        signupButton = driver.findElement(By.name("sign up"));
        signupButton.click();
        GeneralReusables.logout(driver);
        return getCustomerIdByUsername(newUsername);
    }

    private static String getCustomerIdByUsername(String theUsername) {
        WebDriver driver = new ChromeDriver();
        GeneralReusables.setUpToHomepage(driver);
        GeneralReusables.loginAsTheManager(driver);

        WebElement customers = driver.findElement(By.name("customers"));
        customers.click();
        WebElement customersTable = driver.findElement(By.name("customers-table"));
        WebElement usernameSearchBox = customersTable.findElement(By.name("نام کاربری"));
        usernameSearchBox.clear();
        usernameSearchBox.sendKeys(theUsername);
        List<WebElement> tableRows = customersTable.findElements(By.xpath("//tbody//tr"));

        List<WebElement> tableHeader = customersTable.findElements(By.xpath("//thead//tr"));
        List<WebElement> headerTitles = tableHeader.get(0).findElements(By.xpath("//th"));
        int idIndex = 0;
        for (int i = 0; i < headerTitles.size(); i++) {
            if (headerTitles.get(i).getText().equals("شناسه مشتری")) {
                idIndex = i;
            }
        }
        List<WebElement> employeeDetails = tableRows.get(0).findElements(By.xpath("//td"));
        GeneralReusables.logout(driver);
        return employeeDetails.get(idIndex).getText();
    }


    public static String getACustomerUsernameById(String Id) {
        WebDriver driver = new ChromeDriver();
        GeneralReusables.setUpToHomepage(driver);
        GeneralReusables.loginAsTheManager(driver);

        WebElement customers = driver.findElement(By.name("customers"));
        customers.click();
        WebElement customersTable = driver.findElement(By.name("customers-table"));
        WebElement idSearchBox = customersTable.findElement(By.name("شناسه مشتری"));
        idSearchBox.clear();
        idSearchBox.sendKeys(Id);
        List<WebElement> tableRows = customersTable.findElements(By.xpath("//tbody//tr"));

        List<WebElement> tableHeader = customersTable.findElements(By.xpath("//thead//tr"));
        List<WebElement> headerTitles = tableHeader.get(0).findElements(By.xpath("//th"));
        int usernameIndex = 0;
        for (int i = 0; i < headerTitles.size(); i++) {
            if (headerTitles.get(i).getText().equals("نام کاربری")) {
                usernameIndex = i;
            }
        }
        List<WebElement> employeeDetails = tableRows.get(0).findElements(By.xpath("//td"));
        GeneralReusables.logout(driver);
        return employeeDetails.get(usernameIndex).getText();
    }

    public static int getSalary(String employeeUsername) {
        WebDriver driver = new ChromeDriver();
        GeneralReusables.setUpToHomepage(driver);
        GeneralReusables.loginAsTheManager(driver);

        WebElement employee = driver.findElement(By.name("employee"));
        employee.click();
        WebElement employeetable = driver.findElement(By.name("employees-table"));
        WebElement usernameSearchBox = employeetable.findElement(By.name("نام کاربری"));
        usernameSearchBox.clear();
        usernameSearchBox.sendKeys(employeeUsername);

        List<WebElement> tableRows = employeetable.findElements(By.xpath("//tbody//tr"));
        List<WebElement> tableHeader = employeetable.findElements(By.xpath("//thead//tr"));
        List<WebElement> headerTitles = tableHeader.get(0).findElements(By.xpath("//th"));
        int usernameIndex = 0;
        for (int i = 0; i < headerTitles.size(); i++) {
            if (headerTitles.get(i).getText().equals("میزان حقوق")) {
                usernameIndex = i;
            }
        }
        List<WebElement> employeeDetails = tableRows.get(0).findElements(By.xpath("//td"));
        GeneralReusables.logout(driver);
        return Integer.valueOf(employeeDetails.get(usernameIndex).getText());
    }

    public static boolean reportExists(String id, String reason, String username) {
        WebDriver driver = new ChromeDriver();
        GeneralReusables.setUpToHomepage(driver);
        GeneralReusables.loginAsTheManager(driver);

        WebElement reportsTable = driver.findElement(By.name("reports"));
        WebElement idSearchBox = reportsTable.findElement(By.name("شناسه تراکنش"));
        idSearchBox.clear();
        idSearchBox.sendKeys(id);

        List<WebElement> tableHeader = reportsTable.findElements(By.xpath("//thead//tr"));
        List<WebElement> headerTitles = tableHeader.get(0).findElements(By.xpath("//th"));
        int usernameIndex = 0;
        for (int i = 0; i < headerTitles.size(); i++) {
            if (headerTitles.get(i).getText().equals("گزارش دهنده")) {
                usernameIndex = i;
            }
        }
        List<WebElement> tableRows = reportsTable.findElements(By.xpath("//tbody//tr"));

        for (WebElement tableRow : tableRows) {
            List<WebElement> employeeDetails = tableRow.findElements(By.xpath("//td"));
            String theUsername = employeeDetails.get(usernameIndex).getText();
            if (theUsername.equals(username)) {
                GeneralReusables.logout(driver);
                return true;
            }
        }
        GeneralReusables.logout(driver);
        return false;
    }

    public static String getTransactionStatus(String id) {
        WebDriver driver = new ChromeDriver();
        GeneralReusables.setUpToHomepage(driver);
        GeneralReusables.loginAsTheManager(driver);

        WebElement theTable = driver.findElement(By.name("transactions-table"));
        List<WebElement> tableHeader = theTable.findElements(By.xpath("//thead//tr"));
        List<WebElement> headerTitles = tableHeader.get(0).findElements(By.xpath("//th"));
        int statusIndex = 0;
        for (int i = 0; i < headerTitles.size(); i++) {
            if (headerTitles.get(i).getText().equals("وضعیت")) {
                statusIndex = i;
            }
        }

        WebElement idSearchBox = theTable.findElement(By.name("وضعیت"));
        idSearchBox.clear();
        idSearchBox.sendKeys(id);
        List<WebElement> tableRows = theTable.findElements(By.xpath("//tbody//tr"));
        List<WebElement> transactionDetails = tableRows.get(0).findElements(By.xpath("//td"));

        GeneralReusables.logout(driver);
        return transactionDetails.get(statusIndex).getText();
    }

    public String getNewestTransactionId() {
        WebDriver driver = new ChromeDriver();
        GeneralReusables.setUpToHomepage(driver);
        GeneralReusables.loginAsTheManager(driver);

        WebElement theTable = driver.findElement(By.name("transactions-table"));
        List<WebElement> tableHeader = theTable.findElements(By.xpath("//thead//tr"));
        List<WebElement> headerTitles = tableHeader.get(0).findElements(By.xpath("//th"));
        int idIndex = 0;
        for (int i = 0; i < headerTitles.size(); i++) {
            if (headerTitles.get(i).getText().equals("شناسه تراکنش")) {
                idIndex = i;
            }
            if (headerTitles.get(i).getText().equals("تاریخ تراکنش")) {
                headerTitles.get(i).click();// sort by time
            }
        }
        GeneralReusables.logout(driver);
        WebElement tableRow = theTable.findElements(By.xpath("//tbody//tr")).get(0);
        List<WebElement> transactionDetails = tableRow.findElements(By.xpath("//td"));
        return transactionDetails.get(idIndex).getText();
    }


}
