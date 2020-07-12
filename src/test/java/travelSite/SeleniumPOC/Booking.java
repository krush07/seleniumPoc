package travelSite.SeleniumPOC;

import java.io.FileInputStream;

import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;


public class Booking {
	
	WebDriver driver;
	WebDriverWait wait;
	public Properties prop;
	
			
	@BeforeClass 
	public void init() throws Exception {
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
	
	@Test(priority=0)
	public void launchURL()  {
		System.out.println("Test launchURL");
		driver.get("https://www.booking.com/");	
	}
	
	@Test(dependsOnMethods="launchURL")
	public void carRentals() {
		System.out.println("Test Vacation");
		WebElement vacation = driver.findElement(By.xpath("//span[contains(text(),'Car Rentals')]"));	
		vacation.click();
		}
	
	@Test(dependsOnMethods="carRentals")
		public void selectTripType() throws InterruptedException {	
		System.out.println("Enter Trip Type : One Way or Round");
		WebElement tripType = (new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//label[contains(text(),'Return to different location')]"))));
		tripType.click();

	}
	
	@Test(dependsOnMethods="selectTripType")
	public void selectDriveFromAddress() {
		
		System.out.println("Enter address of the city where to Drive from");

		WebElement driveFrom = (new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='ss_origin']"))));	
		driveFrom.click();
		driveFrom.sendKeys("Genoa"); 		
		WebElement firstOpt = (new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(text(),'Genoa')]"))));	
		firstOpt.click();
	
	}
	
	@Test(dependsOnMethods="selectDriveFromAddress")
	public void selectDriveToAddress() {
		System.out.println("Enter address of the city where to Drive to ");
		WebElement driveTo = (new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(By.id("ss"))));	
		driveTo.click();
		driveTo.sendKeys("Rome");
		WebElement firstOpt1 = (new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[contains(text(),'Rome Fiumicino Airport')]"))));		
		firstOpt1.click();		
	}
	
	
	@Test(dependsOnMethods="selectDriveToAddress")
	public void search() {
        
        WebElement searchButton = driver.findElement(By.xpath("//span[contains(text(),'Search')]"));
        searchButton.click();
	}
	
	
	@Test(dependsOnMethods="search")
	public void searchPageLoad() {
		System.out.println("Search Results are displayed");
		WebElement pickup = (new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='ab-SearchSummary_PickUp-headline']"))));		
		Assert.assertEquals(pickup.isDisplayed(), true);				
	}
	
	@AfterClass
	public void tearDown() {
		System.out.println("Shutting down the Driver");
		driver.quit();
	}
	
}