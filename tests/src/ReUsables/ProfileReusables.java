
package Reusables;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * Created by Golpar on 4/12/2018 AD.
 */
public class ProfileReusables {

    //TODO
    public static String SignUpTitle = "سپاا | ثبت‌ نام";
    public static String logInTitle = "ورود";
    public static String passwordChangeTitle = "تغییر رمز عبور";
    public static String userDetailTitle = "مشخصات کاربری";



    public static String firstName1 = "Dorna";
    public static String surName1 = "Abdolazimi";
    public static String username1 = "Dorna";
    public static String email1 = "dorna.abdolazimi@gmail.com";
    public static String phoneNumber1 = "09137927608";
    public static String accountNumber1 = "0123456789123";
    public static String password1 = "12345Dorna";

    public static String notRegisteredEmail = "dorna.gmail.com";
    public static String invalidEmail = "dorna";

    public static String wrongPassword = "duck";
    public static String invalidPassword = "";//TODO
    public static String notMatchedPassword = "somethingElse";
    public static String invalidName = "!";
    public static String invalidPhoneNumber = "1";
    public static String invalidAccountNumber = "1";

    public static String invalidFirstNameError = "نام وارد شده معتبر نیست.";
    public static String invalidFamilyNameError = "نام خانوادگی وارد شده معتبر نیست.";
    public static String invalidUsernameError = "نام کاربری وارد شده معتبر نیست.";
    public static String alreadyRegisteredUsernameError = "نام کاربری وارد شده استفاده شده است.";
    public static String invalidEmailError = "ایمیل وارد شده معتبر نیست.";
    public static String alreadyRegisteredEmailError = "ایمیل وارد شده استفاده شده‌ است.";
    public static String invalidPhoneNumberError = "شماره تلفن وارد شده معتبر نیست.";
    public static String invalidAccountNumberError = "شماره حساب وارد شده معتبر نیست.";
    public static String alreadyRegisteredAccountNumberError = "شماره حساب وارد شده استفاده شده است.";
    public static String invalidPasswordError = "رمز عبور وارد شده معتبر نیست.";
    public static String invalidPasswordRepaetError = "رمز عبور ها مطابق نیستند.";
    public static String successMessage = "ثبت نام با موفقیت انجام شد.";
    public static String notRegisteredEmailError = "ایمیل وارد شده در سامانه نیست.";
    public static String wrongPasswordError = "رمز عبور وارد شده غلط است.";
    public static String panelAddress = ""; //TODO:



    public static void signUpUser1(WebDriver driver) {
        GeneralReusables.setUpToHomepage(driver);
        String linkToOpen = driver.findElement(By.name("sign up")).getAttribute("href");
        driver.get(linkToOpen);

        enterValidFirstName(driver);
        enterValidFamilyName(driver);


        WebElement username = driver.findElement(By.name("username"));
        username.sendKeys(ProfileReusables.username1);

        WebElement email = driver.findElement(By.name("email"));
        email.sendKeys(ProfileReusables.email1);

        enterValidPhoneNumber(driver);

        WebElement accountNumber = driver.findElement(By.name("account number"));
        accountNumber.sendKeys(ProfileReusables.accountNumber1);

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
        phoneNumber.sendKeys(ProfileReusables.phoneNumber1);
    }

    public static void enterValidAccountNumber(WebDriver driver) {
        WebElement shomareHesab = driver.findElement(By.name("account number"));
        shomareHesab.clear();
        shomareHesab.sendKeys(accountNumber1);
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

    public static void navigateToWallet(WebDriver panel, String currency) {
        WebElement wallet = panel.findElement(By.name(currency + "-wallet"));
        wallet.click();
    }

    public static int getWalletCredit(WebDriver panel, String currency) { // currency parameter could be "dollar" or "euro" or "rial"
        return 0;//حواست باشه بک بزنی وقتی می‌ری تو صفحات دیگر.
        //// TODO: 4/12/2018 AD implement this
    }
}

