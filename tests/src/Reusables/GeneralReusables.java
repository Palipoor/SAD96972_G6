package Reusables;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Created by Golpar on 4/12/2018 AD.
 */

/// این کلاس برای تامین توابع دوباره استفاده کردنی در بخش‌های مختلف تست‌ها ساخته شده. کافی ست این کلاس در فایل هر تستی اینکلود بشه و از توابع
// استاتیکش استفاده بشه.
// صرفا باید وبدراویر رو بهش بدیم تا عوضش کنه به جایی که باید.

// در توابعی که قرار نیست وبدرایور عوض بشه، باید برای استفاده یک وبدرایور ساخت و بهش داد.

public class GeneralReusables {

    //todo درست کردن این فیلدها

    public static double delta = 0.05;

    public static final Map<String, String> reusableStrings;

    static {
        reusableStrings = new HashMap<String, String>();
        reusableStrings.put("invalid-transaction-id", "1000");
        reusableStrings.put("wrong-id-error", "");
        reusableStrings.put("invalid-amount-error", "");
        reusableStrings.put("invalid-username-error", "");
        reusableStrings.put("invalid-email-error", "");
        reusableStrings.put("invalid-first-name-error", "");
        reusableStrings.put("invalid-family-name-error", "");
        reusableStrings.put("invalid-phone-number-error", "");
        reusableStrings.put("invalid-account-number-error", "");
        reusableStrings.put("wrong-amount-error", "");
        reusableStrings.put("wrong-username-error", "");
        reusableStrings.put("access-denied-error", "");
        reusableStrings.put("no-such-user-error", "");
        reusableStrings.put("done-transaction", "");
        reusableStrings.put("pending-transaction", "");
        reusableStrings.put("failed-transaction", "");
        reusableStrings.put("reported-transaction", "");
        reusableStrings.put("username-exists", "");
        reusableStrings.put("request-status", "");
    }

    public static CharSequence INVALID_TRANSACTION_ID = "10000";
    public static String PANEL_TITLE = "پنل مدیریت";
    public static String WRONG_ID_ERROR;
    public static String SUCCESSFULLY_SENT;
    public static String WRONG_USERNAME_ERROR;
    public static String REPORTED_TRANSACTION;


    public static void setUpToHomepage(WebDriver driver) {
        String homePageAddress = "http://127.0.0.1:8000/"; //// TODO: 4/12/2018 AD درست کردن این ادرس
        driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
        driver.get(homePageAddress);
    }

    public static void login(WebDriver homepage, String email, String password) {// درایور را در هوم پیج می‌گیرد و لاگین می‌کند با مشخصات مربوط.
        GeneralReusables.setUpToHomepage(homepage);
        // Go to Sign up page
        String linkToOpen = homepage.findElement(By.name("log in")).getAttribute("href");
        homepage.get(linkToOpen);
        WebElement emailField = homepage.findElement(By.name("email"));
        emailField.sendKeys(email);
        WebElement passwordField = homepage.findElement(By.name("password"));
        emailField.sendKeys(password);

        WebElement finalButton = homepage.findElement(By.name("log in button"));
        finalButton.click();
    }

    public static void loginAsACustomer(WebDriver homepage) {
        homepage.navigate().to("http://127.0.0.1:8000/customer/dashboard");
//        String email = ProfileReusables.email1;
//        String password = ProfileReusables.password1;
//        login(homepage, email, password);
    }

    public static void loginAsTheManager(WebDriver homepage) {
        homepage.navigate().to("http://127.0.0.1:8000/manager/dashboard");
//        String email = "customerEmail";
//        String password = "customerPassword";
//        login(homepage, email, password);
    }

    public static String loginAsAnEmployee(WebDriver homepage) {
        homepage.navigate().to("http://127.0.0.1:8000/employee/dashboard");
//        String email = "customerEmail";
//        String password = "customerPassword";
//        login(homepage, email, password);
//        return getUsername(homepage);
        return getUsername(homepage);
    }

    public static String getUsername(WebDriver panel) {
        WebElement userDetails = panel.findElement(By.name("user-details"));
        userDetails.click();
        WebElement usernameElement = panel.findElement(By.name("my-username"));
        return usernameElement.getText();
    }

    public static void logout(WebDriver panel) {// از هر جایی در پنل کاربری مي‌شه لاگ اوت کرد!
        WebElement userMenu = panel.findElement(By.name("user menu"));
        userMenu.click();
        WebElement logoutButton = panel.findElement(By.name("logout"));
        logoutButton.click();
        panel.close();

    }

    public static int getPrice(String currency) {
        String elementName = currency + "-price";
        WebDriver homepage = new ChromeDriver();
        setUpToHomepage(homepage);
        WebElement priceElement = homepage.findElement(By.name(elementName));
        return Integer.valueOf(priceElement.getText());
    }


}