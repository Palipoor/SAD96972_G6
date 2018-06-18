package Reusables;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

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

    public static double delta = 0.05;

    public static final Map<String, String> reusableStrings;

    static {
        reusableStrings = new HashMap<String, String>();
        reusableStrings.put("panel-title","پنل مدیریت");
        reusableStrings.put("pishkhan","پیشخوان");
        reusableStrings.put("invalid-transaction-id", "1000");
        reusableStrings.put("wrong-id-error", "شناسه تراکنش اشتباه است.");
        reusableStrings.put("invalid-amount-error", "مبلغ وارد شده معتبر نیست.");
        reusableStrings.put("invalid-username-error", "نام کاربری وارد شده معتبر نیست.");
        reusableStrings.put("invalid-email-error", "لطفا یک ایمیل معتبر وارد کنید.");
        reusableStrings.put("invalid-first-name-error", "نام وارد شده معتبر نیست.");
        reusableStrings.put("invalid-family-name-error", "نام خانوادگی وارد شده معتبر نیست.");
        reusableStrings.put("invalid-phone-number-error", "شماره تماس وارد شده معتبر نیست.");
        reusableStrings.put("invalid-account-number-error", "شماره حساب وارد شده معتبر نیست");
        reusableStrings.put("access-denied-error", "شما به این صفحه دسترسی ندارید.");
        reusableStrings.put("no-such-user-error", "کاربری با این مشخصات وجود ندارد.");
        reusableStrings.put("done-transaction", "انجام شده");
        reusableStrings.put("pending-transaction", "در انتظار");
        reusableStrings.put("failed-transaction", "ناموفق");
        reusableStrings.put("reported-transaction", "گزارش شده");
        reusableStrings.put("username-exists", "کاربری با این نام کاربری قبلا ثبت شده است.");
        reusableStrings.put("request-status", "شرایط درخواست");
        reusableStrings.put("successfully-sent","با موفقیت ارسال شد.");
    }

    public static void setUpToHomepage(WebDriver driver) {
        String homePageAddress = "http://127.0.0.1:8000/";
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

    public static void loginAsAnEmployeeWithoutName(WebDriver homepage) {
        homepage.navigate().to("http://127.0.0.1:8000/employee/dashboard");
//        String email = "customerEmail";
//        String password = "customerPassword";
//        login(homepage, email, password);
    }

    public static String getUsername(WebDriver panel) {
        WebElement userDetails = panel.findElement(By.name("user-details"));
        userDetails.click();
        WebElement usernameElement = panel.findElement(By.name("my-username"));
        panel.navigate().back();
        return usernameElement.getText();
    }

    public static void logout(WebDriver panel) {// از هر جایی در پنل کاربری مي‌شه لاگ اوت کرد!
        WebElement userMenu = panel.findElement(By.name("user menu"));
        userMenu.click();
        WebElement logoutButton = panel.findElement(By.name("logout"));
        logoutButton.click();
        panel.close();

    }
    public static void backToLogin(WebDriver panel) {// از هر جایی در پنل کاربری مي‌شه لاگ اوت کرد!
        WebElement userMenu = panel.findElement(By.name("user menu"));
        userMenu.click();
        WebElement logoutButton = panel.findElement(By.name("logout"));
        logoutButton.click();
        setUpToHomepage(panel);
        // Go to Log In page
        String linkToOpen = panel.findElement(By.name("log in")).getAttribute("href");
        panel.get(linkToOpen);

    }

    public static int getPrice(String currency) {
        String elementName = currency + "-price";
        WebDriver homepage = new FirefoxDriver();
        setUpToHomepage(homepage);
        WebElement priceElement = homepage.findElement(By.name(elementName));
        int price = Integer.valueOf(priceElement.getText());
        homepage.close();
        return price;
    }

    public static void waitForSeconds(int seconds){
        try {
            Thread.sleep(seconds*1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}