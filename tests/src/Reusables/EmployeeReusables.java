package Reusables;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.util.List;

/**
 * Created by Golpar on 4/19/2018 AD.
 */
public class EmployeeReusables {

    public static String transactionDetailTitle= "پنل مدیریت | جزئیات تراکنش و درخواست";


    public static void bringMeTheDetails(String transactionId, WebDriver driver) {
        WebElement theTable = driver.findElement(By.name("transactions-table"));
        WebElement searchBox = theTable.findElement(By.name("شناسه تراکنش"));
        searchBox.clear();
        searchBox.sendKeys(transactionId);

        List<WebElement> tableHeader = theTable.findElements(By.xpath("//thead"));
        List<WebElement> headerTitles = tableHeader.get(0).findElements(By.xpath("//th"));
        int idIndex = 0;
        for (int i = 0; i < headerTitles.size(); i++) {
            if (headerTitles.get(i).getText().equals("شناسه تراکنش")) {
                idIndex = i;
            }
        }

        List<WebElement> tableRows = theTable.findElements(By.xpath("//tbody//tr"));
        List<WebElement> transactionDetails = tableRows.get(0).findElements(By.xpath("//td"));
        System.out.println(transactionDetails.get(idIndex).findElement(By.tagName("a")));
        transactionDetails.get(idIndex).findElement(By.tagName("a")).click();
    }

    public static void acceptTransaction(String transactionId) {
        WebDriver driver = new FirefoxDriver();
        GeneralReusables.setUpToHomepage(driver);
        GeneralReusables.loginAsAnEmployee(driver);

        bringMeTheDetails(transactionId, driver);

        // hala mire too details

        WebElement accept = driver.findElement(By.name("accept"));
        accept.click();
        WebElement done = driver.findElement(By.name("done"));
        done.click();
        GeneralReusables.logout(driver);
    }
    public static void acceptTransactionGivenDetailPage(WebDriver driver) { //TODO
        WebElement accept = driver.findElement(By.name("accept"));
        accept.click();
        WebElement done = driver.findElement(By.name("done"));
        done.click();
    }

    public static void rejectTransaction(String transactionId) {
        WebDriver driver = new FirefoxDriver();
        GeneralReusables.setUpToHomepage(driver);
        GeneralReusables.loginAsAnEmployee(driver);

        bringMeTheDetails(transactionId, driver);

        // hala mire too details

        WebElement accept = driver.findElement(By.name("reject"));
        accept.click();
        WebElement done = driver.findElement(By.name("done"));
        done.click();
        GeneralReusables.logout(driver);
    }
}
