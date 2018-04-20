
package Reusables;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * Created by Golpar on 4/12/2018 AD.
 */
public class ProfileReusables {

    public static  String SignUpTitle= "ثبت‌ نام";
    public static  String logInTitle= "ورود";
    public static String phoneNumberSample1 = "09137927608";
    public static String passwordSample1 = "12345Dorna";
    public static String emailDummySample= "dorna.abdolazimi@gmail.com";
    public static String usernameDummySample = "Dorna";
    public static String ShomareHesabDummySample = "0123456789123"; //TODO
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


        WebElement username = driver.findElement(By.id("username"));
        username.sendKeys(ProfileReusables.usernameDummySample);

        WebElement email = driver.findElement(By.id("email"));
        email.sendKeys(ProfileReusables.emailDummySample);

        WebElement shomareHesab = driver.findElement(By.id("shomare-hesab"));
        shomareHesab.sendKeys(ProfileReusables.ShomareHesabDummySample);

        enterValidPassword(driver);

        enterValidPhoneNumber(driver);

        WebElement submitButton = driver.findElement(By.id("submit-button"));
        submitButton.click();
    }

    public static void enterValidUsername(WebDriver driver){
        WebElement username = driver.findElement(By.id("username"));
        username.clear();
        username.sendKeys("dorna"+ System.currentTimeMillis());

    }
    public static void enterValidEmail(WebDriver driver){
        WebElement email = driver.findElement(By.id("email"));
        email.clear();
        email.sendKeys("dorna"+ System.currentTimeMillis()+"gmail.com");

    }
    public static void enterValidPhoneNumber(WebDriver driver){
        WebElement phoneNumber = driver.findElement(By.id("phone-number"));
        phoneNumber.clear();
        phoneNumber.sendKeys(ProfileReusables.phoneNumberSample1);
    }

    public static void enterValidShomareHesab(WebDriver driver){
        WebElement shomareHesab = driver.findElement(By.id("shomare-hesab"));
        shomareHesab.clear();
        shomareHesab.sendKeys(""); //TODO : shomare hesab ya shomare card???
    }

    public static void enterValidPassword(WebDriver driver){
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



