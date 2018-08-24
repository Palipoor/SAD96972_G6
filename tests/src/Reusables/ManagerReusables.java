package Reusables;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Golpar on 4/19/2018 AD.
 */
public class ManagerReusables {

	public static final Map<String, String> reusableStrings;

	static {
		reusableStrings = new HashMap<String, String>();
		reusableStrings.put("employees-page-title", "پنل مدیریت | کارمندان");
		reusableStrings.put("customers-page-title", "پنل مدیریت | مشتریان");
	}


	public static boolean customerExists(String username) {
		WebDriver driver = new FirefoxDriver();
		GeneralReusables.setUpToHomepage(driver);
		GeneralReusables.loginAsTheManager(driver);

		WebElement customers = driver.findElement(By.name("customers"));
		customers.click();
		WebElement customersTable = driver.findElement(By.name("users-table"));
		WebElement usernameSearchBox = customersTable.findElement(By.name("نام کاربری"));
		usernameSearchBox.clear();
		usernameSearchBox.sendKeys(username);

		List<WebElement> tableRows = customersTable.findElements(By.xpath("//tbody//tr"));
		GeneralReusables.logout(driver);
		return (tableRows.size() > 0);
	}

	public static boolean employeeExists(String username) {
		WebDriver driver = new FirefoxDriver();
		GeneralReusables.setUpToHomepage(driver);
		GeneralReusables.loginAsTheManager(driver);

		WebElement employee = driver.findElement(By.name("employee"));
		employee.click();
		WebElement employeetable = driver.findElement(By.name("users-table"));
		WebElement usernameSearchBox = employeetable.findElement(By.name("نام کاربری کارمند"));
		usernameSearchBox.clear();
		usernameSearchBox.sendKeys(username);

		List<WebElement> tableRows = employeetable.findElements(By.xpath("//tbody//tr"));
		GeneralReusables.logout(driver);
		return (tableRows.size() > 0);
	}

	public static String getAnEmployee() { // returns the username
		WebDriver driver = new FirefoxDriver();
		GeneralReusables.setUpToHomepage(driver);
		GeneralReusables.loginAsTheManager(driver);
		WebElement employee = driver.findElement(By.name("employee"));
		employee.click();
		WebElement employeetable = driver.findElement(By.name("users-table"));
		List<WebElement> tableRows = employeetable.findElements(By.xpath("//tbody//tr"));

		if (tableRows.size() == 0) {
			return createEmployee(driver);
		} else {
			List<WebElement> tableHeader = employeetable.findElements(By.xpath("//thead"));
			List<WebElement> headerTitles = tableHeader.get(0).findElements(By.xpath("//th"));
			int usernameIndex = 0;
			for (int i = 0; i < headerTitles.size(); i++) {
				if (headerTitles.get(i).getText().equals("نام کاربری")) {
					usernameIndex = i;
				}
			}
			List<WebElement> employeeDetails = tableRows.get(0).findElements(By.xpath("//td"));
			String username = employeeDetails.get(usernameIndex).getText();
			GeneralReusables.logout(driver);
			return username;
		}
	}

	private static String createEmployee(WebDriver driver) {
		long currentTime = System.currentTimeMillis();
		String currentTimeString = String.valueOf(currentTime);
		String newUsername = "test_employee_" + currentTimeString;
		WebElement theForm = driver.findElement(By.name("add-employee"));

		WebElement name = theForm.findElement(By.id("name"));
		name.clear();
		name.sendKeys("تست نام");

		WebElement familyname = theForm.findElement(By.id("familyname"));
		familyname.clear();
		familyname.sendKeys("تست نام خانوادگی");

		WebElement username = theForm.findElement(By.id("username"));
		username.clear();
		username.sendKeys(newUsername);

		WebElement salary = theForm.findElement(By.id("salary"));
		salary.clear();
		salary.sendKeys("100000");

		WebElement submit = driver.findElement(By.name("submit-button"));
		submit.click();
		GeneralReusables.logout(driver);
		return newUsername;
	}

	public static String getACustomerUsername() {
		WebDriver driver = new FirefoxDriver();
		GeneralReusables.setUpToHomepage(driver);
		GeneralReusables.loginAsTheManager(driver);
		WebElement customers = driver.findElement(By.name("customers"));
		customers.click();
		WebElement customersTable = driver.findElement(By.name("users-table"));
		List<WebElement> tableRows = customersTable.findElements(By.xpath("//tbody//tr"));

		if (tableRows.size() == 0) {
			return createCustomer("desiredpassword");
		} else {
			List<WebElement> tableHeader = customersTable.findElements(By.xpath("//thead"));
			List<WebElement> headerTitles = tableHeader.get(0).findElements(By.xpath("//th"));
			int idIndex = 0;
			for (int i = 0; i < headerTitles.size(); i++) {
				if (headerTitles.get(i).getText().equals("شناسه مشتری")) {
					idIndex = i;
				}
			}
			List<WebElement> employeeDetails = tableRows.get(0).findElements(By.xpath("//td"));
			GeneralReusables.logout(driver);
			return employeeDetails.get(idIndex).getText();
		}
	}

	public static String createCustomer(String desiredPassword) {
		long currentTime = System.currentTimeMillis();
		String currentTimeString = String.valueOf(currentTime);
		String newUsername = "test_customer_" + currentTimeString;
		String newEmail = "test_customer_" + currentTimeString + "@gmail.com";

		WebDriver driver = new FirefoxDriver();
		GeneralReusables.setUpToHomepage(driver);
		String register = GeneralReusables.reusableStrings.get("homepage") + "/register";
		driver.get(register);
		GeneralReusables.waitForSeconds(2);
		WebElement name = driver.findElement(By.name("first_name"));
		name.clear();
		name.sendKeys("name test");
		WebElement familyname = driver.findElement(By.name("last_name"));
		familyname.clear();
		familyname.sendKeys("last name");
		WebElement username = driver.findElement(By.name("username"));
		username.clear();
		username.sendKeys(newUsername);
		WebElement email = driver.findElement(By.name("email"));
		email.clear();
		email.sendKeys(newEmail);
		WebElement number = driver.findElement(By.name("phone_number"));
		number.clear();
		number.sendKeys("09379605628");
		WebElement account = driver.findElement(By.name("account_number"));
		account.clear();
		account.sendKeys("10203040");
		WebElement password = driver.findElement(By.name("password"));
		password.clear();
		password.sendKeys(desiredPassword);
		WebElement passwordAgain = driver.findElement(By.name("password2"));
		passwordAgain.clear();
		passwordAgain.sendKeys(desiredPassword);
		driver.findElement(By.className("iCheck-helper")).click();

		WebElement submitButton = driver.findElement(By.name("sign up"));
		submitButton.click();

		driver.close();
		return newUsername;
	}


	public static int getSalary(String employeeUsername) {
		WebDriver driver = new FirefoxDriver();
		GeneralReusables.setUpToHomepage(driver);
		GeneralReusables.loginAsTheManager(driver);

		WebElement employee = driver.findElement(By.name("employee"));
		employee.click();
		WebElement employeetable = driver.findElement(By.name("users-table"));
		WebElement usernameSearchBox = employeetable.findElement(By.name("نام کاربری کارمند"));
		usernameSearchBox.clear();
		usernameSearchBox.sendKeys(employeeUsername);

		List<WebElement> tableRows = employeetable.findElements(By.xpath("//tbody"));
		List<WebElement> tableHeader = employeetable.findElements(By.xpath("//thead//tr"));
		List<WebElement> headerTitles = tableHeader.get(0).findElements(By.xpath("//th"));
		int salaryIndex = 0;
		for (int i = 0; i < headerTitles.size(); i++) {
			if (headerTitles.get(i).getText().equals("حقوق")) {
				salaryIndex = i;
			}
		}
		if (tableRows.size() > 0) {
			List<WebElement> employeeDetails = tableRows.get(0).findElements(By.xpath("//td"));
			int salary = Integer.valueOf(employeeDetails.get(salaryIndex).getText());
			GeneralReusables.logout(driver);
			return salary;
		}
		return -1;
	}

	public static boolean reportExists(String id, String username) {
		WebDriver driver = new FirefoxDriver();
		GeneralReusables.setUpToHomepage(driver);
		GeneralReusables.loginAsTheManager(driver);

		WebElement reportsTable = driver.findElement(By.name("reports-table"));
		WebElement idSearchBox = reportsTable.findElement(By.name("شناسه"));
		idSearchBox.clear();
		idSearchBox.sendKeys(id);

		List<WebElement> tableHeader = reportsTable.findElements(By.xpath("//thead"));
		List<WebElement> headerTitles = tableHeader.get(0).findElements(By.xpath("//th"));
		int usernameIndex = 0;
		for (int i = 0; i < headerTitles.size(); i++) {
			if (headerTitles.get(i).getText().equals("گزارش دهنده")) {
				usernameIndex = i;
			}
		}
		List<WebElement> tableRows = reportsTable.findElements(By.xpath("//tbody//tr"));

		for (WebElement tableRow : tableRows) {
			List<WebElement> employeeDetails = tableRow.findElements(By.xpath("//td"));
			String theUsername = employeeDetails.get(usernameIndex).getText();
			if (theUsername.equals(username)) {
				GeneralReusables.logout(driver);
				return true;
			}
		}
		GeneralReusables.logout(driver);
		return false;
	}

	public static String getTransactionStatus(String id) {
		WebDriver driver = new FirefoxDriver();
		GeneralReusables.setUpToHomepage(driver);
		GeneralReusables.loginAsTheManager(driver);

		WebElement theTable = driver.findElement(By.name("transactions-table"));
		List<WebElement> tableHeader = theTable.findElements(By.xpath("//thead"));
		List<WebElement> headerTitles = tableHeader.get(0).findElements(By.xpath("//th"));
		int statusIndex = 0;
		for (int i = 0; i < headerTitles.size(); i++) {
			if (headerTitles.get(i).getText().equals("وضعیت")) {
				statusIndex = i;
			}
		}

		WebElement idSearchBox = theTable.findElement(By.name("شناسه تراکنش"));
		idSearchBox.clear();
		idSearchBox.sendKeys(id);
		List<WebElement> tableRows = theTable.findElements(By.xpath("//tbody//tr"));
		List<WebElement> transactionDetails = tableRows.get(0).findElements(By.xpath("//td"));

		GeneralReusables.logout(driver);
		return transactionDetails.get(statusIndex).getText();
	}

	public static String getTransactionsCustomerUsername(String transactionId) {
		WebDriver driver = new FirefoxDriver();
		GeneralReusables.setUpToHomepage(driver);
		GeneralReusables.loginAsTheManager(driver);

		WebElement theTable = driver.findElement(By.name("transactions-table"));
		List<WebElement> tableHeader = theTable.findElements(By.xpath("//thead"));
		List<WebElement> headerTitles = tableHeader.get(0).findElements(By.xpath("//th"));
		int usernameIndex = 0;
		for (int i = 0; i < headerTitles.size(); i++) {
			if (headerTitles.get(i).getText().equals("شناسه درخواست‌دهنده")) {
				usernameIndex = i;
			}
		}

		WebElement idSearchBox = theTable.findElement(By.name("شناسه تراکنش"));
		idSearchBox.clear();
		idSearchBox.sendKeys(transactionId);
		List<WebElement> tableRows = theTable.findElements(By.xpath("//tbody//tr"));
		List<WebElement> transactionDetails = tableRows.get(0).findElements(By.xpath("//td"));

		GeneralReusables.logout(driver);
		return transactionDetails.get(usernameIndex).getText();
	}

	public static String getNewestTransactionId() {
		WebDriver driver = new FirefoxDriver();
		GeneralReusables.setUpToHomepage(driver);
		GeneralReusables.loginAsTheManager(driver);

		WebElement theTable = driver.findElement(By.name("transactions-table"));
		List<WebElement> tableHeader = theTable.findElements(By.xpath("//thead"));
		List<WebElement> headerTitles = tableHeader.get(0).findElements(By.xpath("//th"));
		int idIndex = 0;
		for (int i = 0; i < headerTitles.size(); i++) {
			if (headerTitles.get(i).getText().equals("شناسه تراکنش")) {
				idIndex = i;
			}
			if (headerTitles.get(i).getText().equals("تاریخ تراکنش")) {
				headerTitles.get(i).click();// sort by time
			}
		}
		WebElement tableRow = theTable.findElements(By.xpath("//tbody//tr")).get(0);
		List<WebElement> transactionDetails = tableRow.findElements(By.xpath("//td"));
		String id = transactionDetails.get(idIndex).findElement(By.tagName("a")).getText();
		GeneralReusables.logout(driver);
		return id;

	}

	public static double getCompanyCredit(String currency) {
		WebDriver driver = new FirefoxDriver();
		GeneralReusables.setUpToHomepage(driver);
		GeneralReusables.loginAsTheManager(driver);
		double credit = WalletUsersReusables.getWalletCredit(driver, currency);
		driver.close();
		return credit;
	}

	public static boolean newTransactionExists(String type) {
		WebDriver driver = new FirefoxDriver();
		GeneralReusables.setUpToHomepage(driver);
		GeneralReusables.loginAsTheManager(driver);

		WebElement theTable = driver.findElement(By.name("transactions-table"));
		List<WebElement> tableHeader = theTable.findElements(By.xpath("//thead"));
		List<WebElement> headerTitles = tableHeader.get(0).findElements(By.xpath("//th"));
		int typeIndex = 0;
		for (int i = 0; i < headerTitles.size(); i++) {
			if (headerTitles.get(i).getText().equals("نوع تراکنش")) {
				typeIndex = i;
			}

		}
		WebElement tableRow = theTable.findElements(By.xpath("//tbody//tr")).get(0);
		List<WebElement> transactionDetails = tableRow.findElements(By.xpath("//td"));
		String the_type = transactionDetails.get(typeIndex).getText();
		System.out.println("type type" + the_type);
		boolean result = the_type.equals(type);
		GeneralReusables.logout(driver);
		return result;

	}
}
