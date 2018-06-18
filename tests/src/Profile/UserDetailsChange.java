package Profile;

import Reusables.GeneralReusables;
import Reusables.Order;
import Reusables.ProfileReusables;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;

/**
 * Created by Golpar on 4/13/2018 AD.
 */
@RunWith(Reusables.OrderedRunner.class)
public class UserDetailsChange {
    static WebDriver driver;
    static String userDetailChangeTitle = "پنل مدیریت | ویرایش اطلاعات کاربری";
    static String newFirstName= "firstname2";
    static String newFamilyName="familyname2";
    static String newLatinFirstName= "latinfistname2";
    static String newLatinFamilyName="latinfamilyname2";
    static String newPhoneNumber="09132035660";
    static String newEmail="dorna@gmail.com";
    static String newAccountNumber="1234567898";
    static String invalidLatinName="";

    public void change(String elementName, String newField, String submitButtonName){
        WebElement element = driver.findElement(By.name(elementName));
        element.sendKeys(newField);
        WebElement button = driver.findElement(By.name(submitButtonName));
       // button.submit();

    }


    public void gotoUserDetails(){
        driver.findElement(By.name("profile-list")).click();
        driver.findElement(By.name("profile-view")).click();

    }
    public void gotoUserSettings(){
        driver.findElement(By.name("profile-list")).click();
        driver.findElement(By.name("user-settings")).click();

    }
    public void checkForChange(String elementName, String expected){
        gotoUserDetails();
       // String text = driver.findElement(By.name(elementName)).getText(); //TODO
        String text = "";
        assertEquals(expected, text);

    }

    @BeforeClass
    public static void setUp() {
        driver = new FirefoxDriver();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        GeneralReusables.setUpToHomepage(driver);
        GeneralReusables.loginAsACustomer(driver);
        driver.findElement(By.name("profile-list")).click();
        driver.findElement(By.name("user-settings")).click();
    }


    @Test
    @Order(order = 1)
    public void preConditionTest() {
        String title = driver.getTitle();
        assertEquals(title, userDetailChangeTitle);
    }
    @Test
    @Order(order = 2)
    public void validFirstNameChange() {
      change("first name", newFirstName, "submit name");
      checkForChange("", newFirstName);


    }
    @Test
    @Order(order = 3)
    public void validFamilyNameChange() {
        gotoUserSettings();
        change("family name", newFamilyName, "submit name");
        checkForChange("", newFamilyName);



    }
    @Test
    @Order(order = 4)
    public void validLatinFirstNameChange() {
        gotoUserSettings();
        change("latin first name", newLatinFirstName, "submit name");
        checkForChange("", newLatinFirstName);




    }
    @Test
    @Order(order = 5)

    public void validLatinFamilyNameChange() {
        gotoUserSettings();
        change("latin family name", newLatinFamilyName, "submit name");
        checkForChange("", newLatinFamilyName);

    }



    @Test
    @Order(order = 6)
    public void invalidNameChange() {
        gotoUserSettings();
        change("first name", ProfileReusables.invalidName, "submit name");
        String error= driver.findElement(By.name("not valid 1")).getText();
        assertEquals(error, ProfileReusables.invalidName);
    }


    @Test
    @Order(order = 7)
    public void invalidLatinNameChange() {
        gotoUserSettings();
        change("latin first name", invalidLatinName, "submit name");
        String error = driver.findElement(By.name("not valid 1")).getText();
        assertEquals(error, ProfileReusables.invalidName);



    }

    @Test
    @Order(order = 8)
    public void validEmailChange() {
        gotoUserSettings();
        change("email", newEmail, "submit contact");
        checkForChange("", newAccountNumber);

    }
    @Test
    @Order(order = 9)
    public void invalidEmailChange() {
        gotoUserSettings();
        change("latin first name", invalidLatinName, "submit name");
        String error = driver.findElement(By.name("not valid 2")).getText();
        assertEquals(error, ProfileReusables.invalidEmailError);


    }
    @Test
    @Order(order = 10)
    public void validPhoneNumberChange() {
        gotoUserSettings();
        change("phone number", newPhoneNumber, "submit contact");
        checkForChange("", newPhoneNumber);

    }

    @Test
    @Order(order = 11)
    public void setContactMethodEmail() {
        gotoUserSettings();
        WebElement emailRadio= driver.findElement(By.id("optionsRadios1"));
        emailRadio.click();
        checkForChange("", "ایمیل");

    }
    @Test
    @Order(order = 12)
    public void setContactMethodPhone() {
        gotoUserSettings();
        WebElement phoneRadio= driver.findElement(By.id("optionsRadios2"));
        phoneRadio.click();
        checkForChange("", "پیامک");
}


    @Test
    @Order(order = 13)
    public void validAccountNumberChange() {
        gotoUserSettings();
        change("account number", newAccountNumber, "submit account number");
        checkForChange("", newAccountNumber);//TODO

    }

    @Test
    @Order(order = 14)
    public void invalidAccountNumberChange() {
        gotoUserSettings();
        change("account number", ProfileReusables.invalidAccountNumber, "submit account number");
        String error = driver.findElement(By.name("not valid 3")).getText();
        assertEquals(error, ProfileReusables.invalidAccountNumber);


    }



    @AfterClass
    public static void tearDown() {
        GeneralReusables.logout(driver);
        //TODO: revert the changes
    }







}
