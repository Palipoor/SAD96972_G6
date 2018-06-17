package Reusables;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static junit.framework.TestCase.assertEquals;

/**
 * Created by Golpar on 4/19/2018 AD.
 */
public class CustomerReusables {
    public static final Map<String, String> reusableStrings;

    static {
        reusableStrings = new HashMap<String, String>();
        reusableStrings.put("amount","10");
        reusableStrings.put("anonymous-email","palipoor976@gmail.com");
        reusableStrings.put("anonymous-phone","09379605628");
        reusableStrings.put("foreign-accountNumber","1234567890");


    }

    public static void createNewAnonymous(){
        WebDriver driver = new FirefoxDriver();
        GeneralReusables.setUpToHomepage(driver);
        GeneralReusables.loginAsACustomer(driver);

        WebElement paymentForm = driver.findElement(By.name("anonymous-payment"));
        paymentForm.click();

        WebElement amount = driver.findElement(By.name("amount"));
        amount.clear();
        amount.sendKeys(reusableStrings.get("amount"));

        WebElement email = driver.findElement(By.name("email"));
        email.clear();
        email.sendKeys(reusableStrings.get("anonymous-email"));

        WebElement phone = driver.findElement(By.name("phone"));
        phone.clear();
        phone.sendKeys(reusableStrings.get("anonymous-phone"));

        WebElement submit = driver.findElement(By.name("submit-button"));
        submit.click();
        GeneralReusables.logout(driver);

    }


    public static void createNewForeign(){
        WebDriver driver = new FirefoxDriver();
        GeneralReusables.setUpToHomepage(driver);
        GeneralReusables.loginAsACustomer(driver);

        WebElement paymentForm = driver.findElement(By.name("foreign-payment"));
        paymentForm.click();
        WebElement amount = driver.findElement(By.name("amount"));
        amount.clear();
        amount.sendKeys(reusableStrings.get("amount"));

        WebElement currency = driver.findElement(By.name("dollar-radio"));
        currency.click();

        WebElement accountNumber = driver.findElement(By.name("destination"));
        accountNumber.clear();
        accountNumber.sendKeys(reusableStrings.get("foreign-accountNumber"));

        WebElement submit = driver.findElement(By.name("submit-button"));
        submit.click();
        GeneralReusables.logout(driver);

    }




    public static void createNewWithdrawal(){
        WebDriver driver = new FirefoxDriver();
        GeneralReusables.setUpToHomepage(driver);
        GeneralReusables.loginAsACustomer(driver);

        WebElement paymentForm = driver.findElement(By.name("reverse-charge"));
        paymentForm.click();
        WebElement amount = driver.findElement(By.name("amount"));
        amount.clear();
        amount.sendKeys(reusableStrings.get("amount"));

        WebElement submit = driver.findElement(By.name("submit-button"));
        submit.click();
        GeneralReusables.logout(driver);

    }


    public static String createNewTransaction() { //requests a new transaction and returns its id
        WebDriver driver = new FirefoxDriver();
        GeneralReusables.setUpToHomepage(driver);
        GeneralReusables.loginAsACustomer(driver);

        WebElement paymentForm = driver.findElement(By.name("anonymous-payment"));
        paymentForm.click();

        WebElement amount = driver.findElement(By.name("amount"));
        amount.clear();
        amount.sendKeys("10");

        WebElement email = driver.findElement(By.name("email"));
        email.clear();
        email.sendKeys("palipoor976@gmail.com");

        WebElement phone = driver.findElement(By.name("phone"));
        phone.clear();
        phone.sendKeys("09379605628");

        WebElement submit = driver.findElement(By.name("submit-button"));
        submit.click();

        driver.navigate().back();

        WebElement newestTransactions = driver.findElement(By.name("newest-transactions"));
        List<WebElement> tableHeader = newestTransactions.findElements(By.xpath("//thead"));
        List<WebElement> headerTitles = tableHeader.get(0).findElements(By.xpath("//th"));
        int idIndex = 0;
        for (int i = 0; i < headerTitles.size(); i++) {
            if (headerTitles.get(i).getText().equals("شناسه تراکنش")) {
                idIndex = i;
            }
        }
        List<WebElement> tableRows = newestTransactions.findElements(By.xpath("//tbody//tr"));
        return tableRows.get(0).findElements(By.xpath("//td")).get(idIndex).getText();

    }
}
