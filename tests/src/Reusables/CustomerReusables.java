package Reusables;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.List;

/**
 * Created by Golpar on 4/19/2018 AD.
 */
public class CustomerReusables {

    public static String createNewTransaction(String type) { //requests a new transaction and returns its id
        WebDriver driver = new ChromeDriver();
        GeneralReusables.setUpToHomepage(driver);
        GeneralReusables.loginAsACustomer(driver);
        switch (type) {
            case "foreign-payment":
                createNewForeignPayment(driver);
                break;
            case "reverse-charge":
                createNewReverseCharge(driver);
                break;
            case "anonymous-payment":
                creteNewAnonymousPayment(driver);
                break;
        }


        WebElement newestTransactions = driver.findElement(By.name("new-transactions"));
        List<WebElement> tableHeader = newestTransactions.findElements(By.xpath("//thead//tr"));
        List<WebElement> headerTitles = tableHeader.get(0).findElements(By.xpath("//th"));
        int idIndex = 0;
        for (int i = 0; i < headerTitles.size(); i++) {
            if (headerTitles.get(i).getText().equals("شناسه تراکنش")) {
                idIndex = i;
            }
        }
        List<WebElement> tableRows = newestTransactions.findElements(By.xpath("//tbody//tr"));
        driver.close();
        return tableRows.get(0).findElements(By.xpath("//td")).get(idIndex).getText();

    }

    private static void creteNewAnonymousPayment(WebDriver driver) {
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
    }

    private static void createNewReverseCharge(WebDriver driver) {
        WebElement amount = driver.findElement(By.name("amount"));
        amount.clear();
        amount.sendKeys("1000");

        WebElement submit = driver.findElement(By.name("submit-button"));
        submit.click();
    }

    private static void createNewForeignPayment(WebDriver driver) {
        WebElement amount = driver.findElement(By.name("amount"));
        amount.clear();
        amount.sendKeys("10");

        WebElement currency = driver.findElement(By.name("dollar-radio"));
        currency.click();

        WebElement accountNumber = driver.findElement(By.name("destination"));
        accountNumber.clear();
        accountNumber.sendKeys("1234567890");

        WebElement submit = driver.findElement(By.name("submit-button"));
        submit.click();
    }
}
