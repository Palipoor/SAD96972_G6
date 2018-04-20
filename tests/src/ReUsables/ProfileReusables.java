
package Reusables;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.concurrent.TimeUnit;

/**
 * Created by Golpar on 4/12/2018 AD.
 */
public class ProfileReusables {

    public static  String SignUpTitle= "ثبت‌ نام";
    public static  String logInTitle= "ورود";
    public static String emailSample1= "dorna.abdolazimi@gmail.com";
    public static String usernameSample1 = "Dorna";
    public static String phoneNumberSample1 = "09137927608";
    public static String ShomareHesabSample1 = "0123456789123";
    public static String passwordSample1 = "12345Dorna";
    public static String emailDummySample= "dorna.abdolazimi@gmail.com";
    public static String usernameDummySample = "Dorna";
    public static String ShomareHesabDummySample = "0123456789123";
    public static String invalidEmailError = "ایمیل وارد شده معتبر نیست.";
    public static String alreadyRegisteredEmailError = "ایمیل وارد شده استفاده شده‌ است.";
    public static String invalidUsernameError = "نام کاربری وارد شده معتبر نیست.";
    public static String alreadyRegisteredUsernameError = "ایمیل وارد شده استفاده شده است.";
    public static String invalidPhoneNumberError = "شماره تلفن وارد شده معتبر نیست.";
    public static String invalidPasswordError = "پسورد وارد شده معتبر نیست.";
    public static String invalidShomareHesabError = "شماره حساب وارد شده معتبر نیست.";
    public static String alreadyRegisteredShomareHesabError = "شماره حساب وارد شده استفاده شده است.";
    public static String notRegisteredEmailError = "ایمیل وارد شده در سامانه نیست.";







    public static void dummySignUp(WebDriver driver){
        GeneralReusables.setUpToHomepage(driver);
        WebElement signUpButton = driver.findElement(By.name("sign-up"));
        signUpButton.click();


        ProfileReusables.enterValidUsername1(driver);
        WebElement username = driver.findElement(By.id("username"));
        username.sendKeys(ProfileReusables.usernameDummySample);

        WebElement email = driver.findElement(By.id("email"));
        email.sendKeys(ProfileReusables.emailDummySample);

        WebElement shomareHesab = driver.findElement(By.id("shomare-hesab"));
        shomareHesab.sendKeys(ProfileReusables.ShomareHesabDummySample);

        enterValidPassword1(driver);

        enterValidPhoneNumber1(driver);

        WebElement submitButton = driver.findElement(By.id("submit-button"));
        submitButton.click();
    }

    public static void enterValidUsername1(WebDriver driver){
        WebElement username = driver.findElement(By.id("username"));
        username.clear();
        username.sendKeys(ProfileReusables.usernameSample1);

    }
    public static void enterValidEmail1(WebDriver driver){
        WebElement email = driver.findElement(By.id("email"));
        email.clear();
        email.sendKeys(ProfileReusables.emailSample1);

    }
    public static void enterValidPhoneNumber1(WebDriver driver){
        WebElement phoneNumber = driver.findElement(By.id("phone-number"));
        phoneNumber.clear();
        phoneNumber.sendKeys(ProfileReusables.phoneNumberSample1);
    }

    public static void enterValidShomareHesab1(WebDriver driver){
        WebElement shomareHesab = driver.findElement(By.id("shomare-hesab"));
        shomareHesab.clear();
        shomareHesab.sendKeys(ProfileReusables.ShomareHesabSample1);
    }

    public static void enterValidPassword1(WebDriver driver){
        WebElement password = driver.findElement(By.id("password"));
        password.clear();
        password.sendKeys(ProfileReusables.passwordSample1);
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



