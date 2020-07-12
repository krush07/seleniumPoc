package travelSite.SeleniumPOC;

import java.io.FileInputStream;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import com.applitools.eyes.BatchInfo;
import com.applitools.eyes.EyesRunner;
import com.applitools.eyes.RectangleSize;
import com.applitools.eyes.TestResultsSummary;
import com.applitools.eyes.selenium.ClassicRunner;
import com.applitools.eyes.selenium.Eyes;

/**
 * Runs Applitools test for the demo app https://demo.applitools.com
 */

@RunWith(JUnit4.class)
public class TestImage {
	private EyesRunner runner;
	private Eyes eyes;
	private static BatchInfo batch;
	private static WebDriver driver;

	public static Properties prop;
	
	@BeforeClass 
	public static void init() throws Exception {
		prop = new Properties();
		FileInputStream fis = new FileInputStream("./config.properties");
		prop.load(fis);
		
		String operatingSysName = prop.getProperty("opertingSystem");
		System.out.println(operatingSysName);
		
		
		if(prop.getProperty("opertingSystem").equalsIgnoreCase("windows")) 
		{
			//ChromeDriver
			System.setProperty("webdriver.chrome.driver","./drivers/windows/chromedriver.exe");
			driver = new ChromeDriver();
			
			
		}else if(prop.getProperty("opertingSystem").equalsIgnoreCase("mac")) 
		{
			System.setProperty("webdriver.chrome.driver","./drivers/mac/chromedriver.exe");
			driver = new ChromeDriver();
			
		}
		
		else if(prop.getProperty("browser").equalsIgnoreCase("linux")) 
		{
			System.setProperty("webdriver.chrome.driver","./drivers/linux/chromedriver.exe");
			driver = new ChromeDriver();
			
		}
		driver.manage().window().maximize();
		//driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	}
	

	@BeforeClass
	public static void setBatch() {
		// Must be before ALL tests (at Class-level)
		batch = new BatchInfo("Image Testing");
	}

	@Before
	public void beforeEach() {
		// Initialize the Runner for your test.
		runner = new ClassicRunner();

		// Initialize the eyes SDK
		eyes = new Eyes(runner);

		/*
		 * // Raise an error if no API Key has been found.
		 * if(isNullOrEmpty(System.getenv("APPLITOOLS_API_KEY"))) { throw new
		 * RuntimeException("No API Key found; Please set environment variable 'APPLITOOLS_API_KEY'."
		 * ); }
		 */

		// Set your personal Applitols API Key from your environment variables.
		eyes.setApiKey("KAlt101LqvFfUGiVrS0oqTxj104XSvI1h8OR99100bjZ25065M110");

		// set batch name
		eyes.setBatch(batch);

		// Use Chrome browser
		//driver = new ChromeDriver();

	}

	@Test
	public void basicTest() {
		System.out.println("HELLO");
		// Set AUT's name, test name and viewport size (width X height)
		// We have set it to 800 x 600 to accommodate various screens. Feel free to
		// change it.
		eyes.open(driver, "Image Test", "Smoke Test", new RectangleSize(800, 600));
		driver.manage().timeouts().implicitlyWait(5000, TimeUnit.MILLISECONDS);
		// Navigate the browser to the "ACME" demo app.
		driver.get("https://www.apple.com/");

		// To see visual bugs after the first run, use the commented line below instead.
		//driver.get("https://demo.applitools.com/index_v2.html");
				
		// Visual checkpoint #1 - Check the login page.
		eyes.checkWindow("Home Page");

		// This will create a test with two test steps.
		driver.findElement(By.xpath("//div[@class='unit-wrapper theme-dark background']//a[@class='unit-link']")).click();

		// Visual checkpoint #2 - Check the app page.
		eyes.checkWindow("Product Page");

		// End the test.
		eyes.closeAsync();
	}

	@After
	public void afterEach() {
		// Close the browser.
		driver.quit();

		// If the test was aborted before eyes.close was called, ends the test as
		// aborted.
		eyes.abortIfNotClosed();

		// Wait and collect all test results
		TestResultsSummary allTestResults = runner.getAllTestResults();

		// Print results
		System.out.println(allTestResults);
	}
}
