package variousConcepts;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class LearnTestNG {

	WebDriver driver;
	String browser;
	String url;

	// Storing element using By class
	By USERNAME_FIELD = By.xpath("//*[@id=\"username\"]");
	By PASSWORD_FIELD = By.xpath("//*[@id=\"password\"]");
	By SIGNIN_BUTTON_FIELD = By.xpath("/html/body/div/div/div/form/div[3]/button");
	By DASHBOARD_HEADER_FIELD = By.xpath("//*[@id=\"page-wrapper\"]/div[2]/div/h2");
	By CUSTOMER_MENU_FIELD = By.xpath("//span[contains(text(), 'Customers')]");
	By ADD_CUSTOMER_MENU_FIELD = By.xpath("//a[contains(text(), 'Add Customer')]");
	By FULL_NAME_FIELD = By.xpath("//*[@id=\"account\"]");
	By COMPANY_NAME_FIELD = By.xpath("//select[@id=\"cid\"]");
	By EMAIL_FIELD = By.xpath("//*[@id=\"email\"]");
	By COUNTRY_NAME_FIELD = By.xpath("//*[@id=\"country\"]");

	// Login Data
	String userName = "demo@techfios.com";
	String password = "abc123";

	// Test Data
	String fullName = "TestNg Selenium";
	String company = "Techfios";
	String email = "abc432@techfios.com";
	String country = "Albania";

	@BeforeClass
	public void readConfig() {
		// InputStream //BufferedReader //Scanner //FileReader

		try {

			InputStream input = new FileInputStream("src\\main\\java\\config\\config.properties");
			Properties prop = new Properties();
			prop.load(input);
			browser = prop.getProperty("browser");
			System.out.println("Browser used: " + browser);
			url = prop.getProperty("url");

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@BeforeMethod
	public void init() {

		if (browser.equalsIgnoreCase("chrome")) {
			System.setProperty("webdriver.chrome.driver", "drivers\\chromedriver.exe");
			driver = new ChromeDriver();
		} else if (browser.equalsIgnoreCase("firefox")) {
			System.setProperty("webdriver.gecko.driver", "drivers\\geckodriver.exe");
			driver = new FirefoxDriver();
		}

		driver.get(url);
		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

	}

	// @Test(priority = 1)
	public void loginTest() {
		// System.out.println("=============== Positive test");
		driver.findElement(USERNAME_FIELD).sendKeys(userName);
		driver.findElement(PASSWORD_FIELD).sendKeys(password);
		driver.findElement(SIGNIN_BUTTON_FIELD).click();

		String expectedDashboarHeader = driver.findElement(DASHBOARD_HEADER_FIELD).getText();
		Assert.assertEquals(expectedDashboarHeader, "Dashboard", "Wrong Page!!");
	}

	@Test(priority = 2)
	public void addContactTest() {

		driver.findElement(USERNAME_FIELD).sendKeys(userName);
		driver.findElement(PASSWORD_FIELD).sendKeys(password);
		driver.findElement(SIGNIN_BUTTON_FIELD).click();

		String expectedDashboarHeader = driver.findElement(DASHBOARD_HEADER_FIELD).getText();
		Assert.assertEquals(expectedDashboarHeader, "Dashboard", "Wrong Page!!");

		driver.findElement(CUSTOMER_MENU_FIELD).click();
		driver.findElement(ADD_CUSTOMER_MENU_FIELD).click();

		Random rnd = new Random();
		int generatedNum = rnd.nextInt(999);

		driver.findElement(FULL_NAME_FIELD).sendKeys(fullName + generatedNum);

		selectFromDropdown(driver.findElement(COMPANY_NAME_FIELD), company);

		driver.findElement(EMAIL_FIELD).sendKeys(generatedNum + email);
		
		selectFromDropdown(driver.findElement(COUNTRY_NAME_FIELD), country);
//		driver.findElement(COUNTRY_NAME_FIELD).sendKeys(country);
	}

	private void selectFromDropdown(WebElement dropdownElement, String visibleText) {
		Select sel = new Select(dropdownElement);
		sel.selectByVisibleText(visibleText);

	}

	

	// @AfterMethod
	public void tearDown() {
		driver.close();
		driver.quit();
	}

}
