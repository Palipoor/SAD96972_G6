
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
        reusableStrings.put("sign-up-title", "سپاا | ثبت‌ نام");
        reusableStrings.put("log-in-title", "سپاا | ورود");
        reusableStrings.put("password-change-title", "تغییر پسوورد");
        reusableStrings.put("user-details-title", "مشخصات مشتری");
        reusableStrings.put("first-name", "");
        reusableStrings.put("sur-name", "");
        reusableStrings.put("username", "");
        reusableStrings.put("email", "");
        reusableStrings.put("phone-number", "");
        reusableStrings.put("account-number", "");
        reusableStrings.put("not-registered-email", "");
        reusableStrings.put("wrong-password", "");
        reusableStrings.put("invalid-password", "");
        reusableStrings.put("not-matched-password", "");
        reusableStrings.put("invalid-name", "");
        reusableStrings.put("invalid-phone-number", "");
        reusableStrings.put("invalid-account-number", "");


    }
    public static String firstName1 = "Dorna";
    public static String surName1 = "Abdolazimi";
    public static String username1 = "Dorna";
    public static String password1 = "12345Dorna";

    public static String notRegisteredEmail = "dorna.gmail.com";
    public static String invalidEmail = "dorna";

    public static String wrongPassword = "duck";
    public static String invalidPassword = "";
    public static String notMatchedPassword = "somethingElse";
    public static String invalidName = "!";
    public static String invalidPhoneNumber = "1";
    public static String invalidAccountNumber = "1";

    public static String invalidEmailError = "ایمیل وارد شده معتبر نیست.";
    public static String invalidPasswordError = "رمز عبور وارد شده معتبر نیست.";
    public static String invalidPasswordRepaetError = "رمز عبور ها مطابق نیستند.";
    public static String notRegisteredEmailError = "ایمیل وارد شده در سامانه نیست.";
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

        WebElement accountNumber = driver.findElement(By.name("account number"));
        accountNumber.sendKeys(ProfileReusables.reusableStrings.get("account-number"));

        enterValidPassword(driver);
        repeatValidPassword(driver);

        clickForSignUp(driver);

    }

    public static void enterValidFirstName(WebDriver driver) {
        WebElement email = driver.findElement(By.name("first name"));
        email.clear();
        email.sendKeys(firstName1);

    }

    public static void enterValidFamilyName(WebDriver driver) {
        WebElement email = driver.findElement(By.name("family name"));
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
        WebElement phoneNumber = driver.findElement(By.name("contact number"));
        phoneNumber.clear();
        phoneNumber.sendKeys(ProfileReusables.reusableStrings.get("phone-number"));
    }

    public static void enterValidAccountNumber(WebDriver driver) {
        WebElement shomareHesab = driver.findElement(By.name("account number"));
        shomareHesab.clear();
        shomareHesab.sendKeys(reusableStrings.get("account-number"));
    }

    public static void enterValidPassword(WebDriver driver) {
        WebElement password = driver.findElement(By.name("password"));
        password.clear();
        password.sendKeys(ProfileReusables.password1);
    }


    public static void repeatValidPassword(WebDriver driver) {

        WebElement password = driver.findElement(By.name("password repeat"));
        password.clear();
        password.sendKeys(ProfileReusables.password1);
    }


    public static void clickForSignUp(WebDriver driver) {

        WebElement submitButton = driver.findElement(By.name("sign up"));
        submitButton.click();
    }


    public static void clickForLogIn(WebDriver driver) {
        WebElement submitButton = driver.findElement(By.name("log in button"));
        submitButton.click();
    }

}

