package com.herokuapp.theinternet;

import static org.testng.Assert.assertEquals;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class ExeptionsTests {
	private WebDriver driver;

	@Parameters({ "browser" })
	@BeforeMethod(alwaysRun = true)
	private void setUp(@Optional("chrome") String browser) {
		// Create driver
		switch (browser) {
		case "chrome":
			driver = new ChromeDriver();
			break;
		case "firefox":
			
			/*
			 * driver = new FirefoxDriver(); break;
			 */
		default:
			System.out.println("Do not know how to start " + browser + ", starting chrome instead");
			driver = new ChromeDriver();
			break;
		}


		System.out.println("Browser started");
		// Maximize browser window
		driver.manage().window().maximize();
		System.out.println("Page is opened");

	}
	

	
	@Test()
	public void noSuchElementExceptionTest() {
		System.out.println("Test started");
		
	//Open page
	String url = "https://practicetestautomation.com/practice-test-exceptions/";
	driver.get(url);
	
	//Click Add button
	WebElement addButton = driver.findElement(By.id("add_btn"));
	addButton.click();

	//Explicit way
	WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	WebElement row2Input = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='row2']/input")));
		
	//Verify Row 2 input field is 
	Assert.assertTrue(row2Input.isDisplayed(), "Row 2 is not displayed");
		
	}
	
	@Test
	public void elementNotInteractableExceptionTest() {
	//Open page
	driver.get("https://practicetestautomation.com/practice-test-exceptions/");
	
	//Click Add button
	WebElement addButton = driver.findElement(By.id("add_btn"));
	addButton.click();
	
	//Wait for the second row to load
	WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	WebElement row2Input = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='row2']/input")));
	
	//Type text into the second input field
	row2Input.sendKeys("Sushi");
	
	//Push Save button using locator By.name(“Save”)
	WebElement saveButton = driver.findElement(By.xpath("//div[@id='row2']/button[@name='Save']"));
	saveButton.click();
	
	//Verify text saved
	WebElement confirmationMessage = driver.findElement(By.id("confirmation"));
	String messageText = confirmationMessage.getText();
	assertEquals(messageText, "Row 2 was saved", "Confirmation message text is not as expected");
	}
	
	@Test
	public void invalidElementStateExceptionTest() {
		
	//Open page
	driver.get("https://practicetestautomation.com/practice-test-exceptions/");

	//Clear input field
	WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	WebElement row1Input = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='row1']/input")));
	WebElement editButton = driver.findElement(By.id("edit_btn"));
	editButton.click();
	wait.until(ExpectedConditions.elementToBeClickable(row1Input));
	row1Input.clear();

	//Type text into the input field
	row1Input.sendKeys("Sushi");
		
	WebElement saveButton = driver.findElement(By.id("save_btn"));
	saveButton.click();

	//Verify text changed
	String value = row1Input.getAttribute("value");
	Assert.assertEquals(value, "Sushi", "Input 1 field value is not as expected");

	//Verify text saved
	WebElement confirmationMessage =wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("confirmation")));
	String messageText = confirmationMessage.getText();
	assertEquals(messageText, "Row 1 was saved", "Confirmation message text is not as expected");

	
	}
	
	@Test
	public void staleElementReferenceException() {

	//Open page
	driver.get("https://practicetestautomation.com/practice-test-exceptions/");	

	//Click Add button
	WebElement addButton = driver.findElement(By.id("add_btn"));
	addButton.click();		

	//Verify instruction text element is no longer displayed
	WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	Assert.assertTrue(wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("instructions"))), "Instructions are still displayed");
	
	}
	
	@Test()
	public void timeoutException() {
		System.out.println("Test started");
		
	//Open page
	String url = "https://practicetestautomation.com/practice-test-exceptions/";
	driver.get(url);
			
	//Click Add button
	WebElement addButton = driver.findElement(By.id("add_btn"));
	addButton.click();

	//Wait for 3 seconds for the second input field to be displayed
	WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(6));
	WebElement row2Input = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='row2']/input")));
		
	//Verify Row 2 input field is 
	Assert.assertTrue(row2Input.isDisplayed(), "Row 2 is not displayed");
		
	}
	
	@AfterMethod(alwaysRun = true)
	private void tearDown() {
		// Close Browser
		driver.quit();
	}
}
