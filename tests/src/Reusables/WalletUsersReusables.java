package Reusables;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Golpar on 4/12/2018 AD.
 */
public class WalletUsersReusables {


    public static final Map<String, String> reusableStrings;

    static {
        reusableStrings = new HashMap<String, String>();
        reusableStrings.put("dollar-wallet-title", "پنل مدیریت |کیف پول دلار");
        reusableStrings.put("euro-wallet-title", "پنل مدیریت | کیف پول یورو");
        reusableStrings.put("rial-wallet-title", "پنل مدیریت | کیف پول ریال");
        reusableStrings.put("not-enough-error", "موجودی شما کافی نیست.");
    }

    public static String DOLLAR_WALLET_TITLE = "پنل مدیریت |کیف پول دلار";
    public static String NOT_ENOUGH_ERROR_MESSAGE = "موجودی شما کافی نیست.";
    public static String RIAL_WALLET_TITLE = "پنل مدیریت | کیف پول ریال";
    public static String EURO_WALLET_TITLE = "پنل مدیریت | کیف پول یورو";

    public static void navigateToWallet(WebDriver panel, String currency) {
        WebElement wallet = panel.findElement(By.name(currency + "-wallet"));
        wallet.click();
    }

    public static double getWalletCredit(WebDriver panel, String currency) { // currency parameter could be "dollar" or "euro" or "rial"
        navigateToWallet(panel, currency);
        WebElement credit = panel.findElement(By.name("credit"));
        int creditValue = Integer.valueOf(credit.getText());
        panel.navigate().back();
        return creditValue;
    }
}