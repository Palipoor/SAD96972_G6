import org.openqa.selenium.WebDriver;

/**
 * Created by Golpar on 4/12/2018 AD.
 */

/// این کلاس برای تامین توابع دوباره استفاده کردنی در بخش‌های مختلف تست‌ها ساخته شده. کافی ست این کلاس در فایل هر تستی اینکلود بشه و از توابع
    // استاتیکش استفاده بشه.
    // صرفا باید وبدراویر رو بهش بدیم تا عوضش کنه به جایی که باید.

    // در توابعی که قرار نیست وبدرایور عوض بشه، باید برای استفاده یک وبدرایور ساخت و بهش داد.

public class ReUsables {

    public static void loginAsACustomer(WebDriver homepage){
    }

    public static void loginAsTheManager(WebDriver homepage){
    }

    public static void loginAsAnEmployee(WebDriver homepage){
    }

    public static void logout(WebDriver panel){

    }

    public static int getPrice(WebDriver homepage, String currency){ // حتما بهش یک وبدرایور جدید که آدرس هومپیج رو باز کرده بدین! به باد می‌رین مگرنه.
        return 0;
    }

    public static int getWalletCredit(WebDriver panel, String currency){ // currency parameter could be "dollar" or "euro" or "rial"
        return 0;
    }

}
