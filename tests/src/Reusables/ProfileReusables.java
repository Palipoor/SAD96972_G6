
package Reusables;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Golpar on 4/12/2018 AD.
 */
public class ProfileReusables {

    public static final Map<String, String> reusableStrings;

	static {
        reusableStrings = new HashMap<String, String>();
        reusableStrings.put("sign-up-title", "");
        reusableStrings.put("log-in-title", "سپاا | ورود");
        reusableStrings.put("password-change-title", "تغییر پسوورد");
        reusableStrings.put("user-details-title", "مشخصات مشتری");
        reusableStrings.put("email", "dorna@gmail.com");
        reusableStrings.put("phone-number", "09123456789");
        reusableStrings.put("account-number", "1020304050");
        reusableStrings.put("wrong-password", "رمز عبور اشتباه است.");
        reusableStrings.put("invalid-name", "لطفا فقط از حروف انگلیسی و فاصله استفاده کنید.");
        reusableStrings.put("invalid-phone-number", "شماره تلفن وارد شده معتبر نیست.");
        reusableStrings.put("invalid-account-number", "شماره حساب وارد شده معتبر نیست.");


    }
    public static String firstName1 = "Dorna";
    public static String surName1 = "Abdolazimi";
    public static String username1 = "Dorna";
    public static String password1 = "12345Dorna";

	public static String invalidLogin = "نام کاربری یا رمز عبور اشتباه اند.";
	public static String notRegisteredEmail = "dorna.gmail.com";
    public static String invalidEmail = "dorna";

    public static String wrongPassword = "duck";
    public static String notMatchedPassword = "somethingElse";
    public static String invalidName = "!";
    public static String invalidPhoneNumber = "!";
    public static String invalidAccountNumber = "!";

    public static String invalidEmailError = "لطفا یک ایمیل معتبر وارد کنید.";
	public static String invalidPasswordRepaetError = "تکرار رمز عبور با آن یکسان نیست.";
    public static String notRegisteredEmailError = "کاربری با این مشخصات وجود ندارد.";
    public static String loginError = "نام کاربری یا رمز عبور اشتباه اند.";
	public static String wrongPasswordError = "رمز عبور وارد شده غلط است.";


    public static void signUpUser1(WebDriver driver) {
        GeneralReusables.setUpToHomepage(driver);
        String linkToOpen = driver.findElement(By.name("sign up")).getAttribute("href");
        driver.get(linkToOpen);

        enterValidFirstName(driver);
        enterValidFamilyName(driver);


        WebElement username = driver.findElement(By.name("username"));
        username.sendKeys(ProfileReusables.username1);

        WebElement email = driver.findElement(By.name("email"));
        email.sendKeys(ProfileReusables.reusableStrings.get("email"));

        enterValidPhoneNumber(driver);

        WebElement accountNumber = driver.findElement(By.name("account_number"));
        accountNumber.sendKeys(ProfileReusables.reusableStrings.get("account-number"));

        enterValidPassword(driver);
        repeatValidPassword(driver);

        clickForSignUp(driver);

		GeneralReusables.waitForSeconds(10);

    }

    public static void enterValidFirstName(WebDriver driver) {
        WebElement email = driver.findElement(By.name("first_name"));
        email.clear();
        email.sendKeys(firstName1);

    }

    public static void enterValidFamilyName(WebDriver driver) {
        WebElement email = driver.findElement(By.name("last_name"));
        email.clear();
        email.sendKeys(surName1);

    }

    public static void enterValidUsername(WebDriver driver) {
        WebElement username = driver.findElement(By.name("username"));
        username.clear();
        username.sendKeys("dorna" + System.currentTimeMillis());  //TODO

    }

    public static void enterValidEmail(WebDriver driver) {
        WebElement email = driver.findElement(By.name("email"));
        email.clear();
        email.sendKeys("dorna" + System.currentTimeMillis() + "@gmail.com");  //TODO

    }


    public static void enterValidPhoneNumber(WebDriver driver) {
        WebElement phoneNumber = driver.findElement(By.name("phone_number"));
        phoneNumber.clear();
        phoneNumber.sendKeys(ProfileReusables.reusableStrings.get("phone-number"));
    }

    public static void enterValidAccountNumber(WebDriver driver) {
        WebElement shomareHesab = driver.findElement(By.name("account_number"));
        shomareHesab.clear();
        shomareHesab.sendKeys(reusableStrings.get("account-number"));
    }

    public static void enterValidPassword(WebDriver driver) {
        WebElement password = driver.findElement(By.name("password"));
        password.clear();
        password.sendKeys(ProfileReusables.password1);
    }


    public static void repeatValidPassword(WebDriver driver) {

        WebElement password = driver.findElement(By.name("password2"));
        password.clear();
        password.sendKeys(ProfileReusables.password1);
    }


    public static void clickForSignUp(WebDriver driver) {

		driver.findElement(By.className("iCheck-helper")).click();
        WebElement submitButton = driver.findElement(By.name("sign up"));
        submitButton.click();
    }


    public static void clickForLogIn(WebDriver driver) {
        WebElement submitButton = driver.findElement(By.name("log in button"));
        submitButton.click();
    }

}

