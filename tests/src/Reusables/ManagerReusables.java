package Reusables;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

/**
 * Created by Golpar on 4/19/2018 AD.
 */
public class ManagerReusables {

    public static String EMPLOYEES_PAGE_TITLE = "پنل مدیریت | کارمندان";
    public static String  CUSTOMERS_PAGE_TITLE = "پنل مدیریت | مشتریان";

    public static boolean customerExists(String username) {
        WebDriver driver = new ChromeDriver();
        GeneralReusables.setUpToHomepage(driver);
        GeneralReusables.loginAsTheManager(driver);

        WebElement customers = driver.findElement(By.name("customers"));
        customers.click();
        WebElement customersTable = driver.findElement(By.name("customers-table"));

        return true;
    }

    public static boolean employeeExists(String username) {
        return true; //// TODO: 4/19/2018 AD complete
    }

    public static String getAnEmployee(){
        // چک میکنه اگر ایمپلویی داشتیم یوزرنیمش رو میده. اگر نداشتیم یکی می‌سازه!
        return "";
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

    public int getNewestTransactionId(){
        return 0;
    }

}
