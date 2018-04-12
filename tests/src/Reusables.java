import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * Created by Golpar on 4/12/2018 AD.
 */

/// این کلاس برای تامین توابع دوباره استفاده کردنی در بخش‌های مختلف تست‌ها ساخته شده. کافی ست این کلاس در فایل هر تستی اینکلود بشه و از توابع
// استاتیکش استفاده بشه.
// صرفا باید وبدراویر رو بهش بدیم تا عوضش کنه به جایی که باید.

// در توابعی که قرار نیست وبدرایور عوض بشه، باید برای استفاده یک وبدرایور ساخت و بهش داد.

public class ReUsables {

    public static void login(WebDriver homepage, String email, String password) {// درایور را در هوم پیج می‌گیرد و لاگین می‌کند با مشخصات مربوط.
        WebElement loginButton = homepage.findElement(By.name("log in"));
        loginButton.click();
        WebElement emailField = homepage.findElement(By.name("email"));
        emailField.sendKeys(email);
        WebElement passwordField = homepage.findElement(By.name("password"));
        emailField.sendKeys(password);

        WebElement finalButton = homepage.findElement(By.name("log in button"));
        finalButton.click();
    }

    public static void loginAsACustomer(WebDriver homepage) {
        String email = "customerEmail";
        String password = "customerPassword";
        login(homepage, email, password);
    }

    public static void loginAsTheManager(WebDriver homepage) {
        String email = "customerEmail";
        String password = "customerPassword";
        login(homepage, email, password);
    }

    public static void loginAsAnEmployee(WebDriver homepage) {
        String email = "customerEmail";
        String password = "customerPassword";
        login(homepage, email, password);
    }

    public static void logout(WebDriver panel) {// از هر جایی در پنل کاربری مي‌شه لاگ اوت کرد!
        WebElement userMenu = panel.findElement(By.name("user menu"));
        userMenu.click();
        WebElement logoutButton = panel.findElement(By.name("logout"));
        logoutButton.click();
    }

    public static int getPrice(WebDriver homepage, String currency) { // حتما بهش یک وبدرایور جدید که آدرس هومپیج رو باز کرده بدین! به باد می‌رین مگرنه.
        return 0;
    }

    public static int getWalletCredit(WebDriver panel, String currency) { // currency parameter could be "dollar" or "euro" or "rial"
        return 0;
    }

}
