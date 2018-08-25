package Reusables;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;

/**
 * Created by Golpar on 4/19/2018 AD.
 */
public class CustomerReusables {
    public static final Map<String, String> reusableStrings;

    static {
        reusableStrings = new HashMap<String, String>();
        reusableStrings.put("amount","10");
        reusableStrings.put("profit","1");
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

	public static void createNewBankTransfer(String currency, String howmuch) {

		WebDriver driver = new FirefoxDriver();
		GeneralReusables.loginAsACustomer(driver);
		driver.get(GeneralReusables.reusableStrings.get("homepage") + "/customer/create_banktrans");

		WebElement amount = driver.findElement(By.name("amount"));
		amount.clear();
		amount.sendKeys(howmuch);

		WebElement wallet = driver.findElement(By.name("source_wallet"));
		Select dropdown= new Select(wallet);
		dropdown.selectByVisibleText(currency);


		WebElement bank_name = driver.findElement(By.name("bank_name"));
		bank_name.clear();
		bank_name.sendKeys("جهانی");

		WebElement account_number = driver.findElement(By.name("account_number"));
		account_number.clear();
		account_number.sendKeys("1997-0335-0337");

		WebElement button = driver.findElement(By.name("create"));
		button.click();
		GeneralReusables.waitForSeconds(1);
	}

	public static double get_credit(String currency) {
		WebDriver driver = new FirefoxDriver();
		GeneralReusables.loginAsACustomer(driver);
		return WalletUsersReusables.getWalletCredit(driver, currency);
	}
}
