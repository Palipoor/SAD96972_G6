package Reusables;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

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
        reusableStrings.put("not-enough-error-rial", "موجودی کیف پول ریالی کافی نیست.");
		reusableStrings.put("not-enough-error-dollar", "موجودی کیف پول دلار کافی نیست.");
		reusableStrings.put("not-enough-error-euro", "موجودی کیف پول یورو کافی نیست.");
	}

    public static void navigateToWallet(WebDriver panel, String currency) {
        WebElement wallets = panel.findElement(By.name("my-wallets"));
        wallets.click();
        GeneralReusables.waitForSeconds(5);
        WebElement wallet = panel.findElement(By.name(currency + "-wallet"));
        wallet.click();
    }

    public static double getWalletCredit(WebDriver panel, String currency) { // currency parameter could be "dollar" or "euro" or "rial"
        //if (!Objects.equals(panel.getTitle(), reusableStrings.get("dollar-wallet-title")))
        navigateToWallet(panel, currency);
        WebElement credit = panel.findElement(By.name("credit"));
        double creditValue = Double.valueOf(credit.getText());
        panel.navigate().back();
        return creditValue;
    }
}