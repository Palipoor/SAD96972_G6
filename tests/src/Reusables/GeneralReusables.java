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
		reusableStrings.put("panel-title", "پنل مدیریت");
		reusableStrings.put("notification-title", "اعلان‌ها");
		reusableStrings.put("pishkhan", "پیشخوان");
		reusableStrings.put("invalid-transaction-id", "1000");
		reusableStrings.put("wrong-id-error", "شناسه تراکنش اشتباه است.");
		reusableStrings.put("invalid-amount-error", "مبلغ وارد شده معتبر نیست.");
		reusableStrings.put("invalid-username-error", "نام کاربری وارد شده معتبر نیست. لطفا فقط از حروف انگلیسی، اعداد و علامت ـ استفاده کنید.");
		reusableStrings.put("invalid-email-error", "لطفا یک ایمیل معتبر وارد کنید.");
		reusableStrings.put("invalid-first-name-error", "لطفا فقط از حروف انگلیسی و فاصله استفاده کنید.");
		reusableStrings.put("invalid-persian-name-error", "لطفا فقط از حروف فارسی و فاصله استفاده کنید.");
		reusableStrings.put("invalid-family-name-error", "لطفا فقط از حروف انگلیسی و فاصله استفاده کنید.");
		reusableStrings.put("invalid-phone-number-error", "شماره تلفن وارد شده معتبر نیست");
		reusableStrings.put("invalid-account-number-error", "شماره حساب وارد شده معتبر نیست");
		reusableStrings.put("access-denied-error", "شما به این صفحه دسترسی ندارید.");
		reusableStrings.put("no-such-user-error", "کاربری با این مشخصات وجود ندارد.");
		reusableStrings.put("done-transaction", "انجام شده");
		reusableStrings.put("pending-transaction", "در انتظار");
		reusableStrings.put("failed-transaction", "ناموفق");
		reusableStrings.put("reported-transaction", "گزارش شده");
		reusableStrings.put("username-exists", "کاربری با این مشخصات وجود دارد.");
		reusableStrings.put("request-status", "شرایط درخواست");
		reusableStrings.put("successfully-sent", "با موفقیت ارسال شد.");
		reusableStrings.put("homepage","http://127.0.0.1:8000");
	}

	public static void setUpToHomepage(WebDriver driver) {
		String homePageAddress = "http://127.0.0.1:8000/";
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		driver.get(homePageAddress);
	}

	public static void login(WebDriver homepage, String username, String password) {// درایور را در هوم پیج می‌گیرد و لاگین می‌کند با مشخصات مربوط.
		GeneralReusables.setUpToHomepage(homepage);
		String linkToOpen = reusableStrings.get("homepage") + "/login";
		homepage.get(linkToOpen);
		WebElement emailField = homepage.findElement(By.name("username"));
		emailField.sendKeys(username);
		WebElement passwordField = homepage.findElement(By.name("password"));
		passwordField.sendKeys(password);

		WebElement finalButton = homepage.findElement(By.name("log in button"));
		finalButton.click();
	}

	public static void loginAsACustomer(WebDriver homepage) {
        login(homepage, "customer", "customercustomer");
	}

	public static void loginAsTheManager(WebDriver homepage) {
        login(homepage, "manager", "managermanager");
	}

	public static String loginAsAnEmployee(WebDriver homepage) {
        login(homepage, "employee", "employeeemployee");
		return getUsername(homepage);
	}

	public static void loginAsAnEmployeeWithoutName(WebDriver homepage) {
		login(homepage, "employee", "employeeemployee");
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
		panel.get("http://127.0.0.1:8000/login");
		GeneralReusables.waitForSeconds(2);

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

	public static void waitForSeconds(int seconds) {
		try {
			Thread.sleep(seconds * 1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}