package com.herokuapp.theinternet;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class LoginTest {
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
			
			driver = new FirefoxDriver();
			break;
		default:
			System.out.println("Do not know how to start " + browser + ", starting chrome instead");
			driver = new ChromeDriver();
			break;
		}

		// WebDriver driver = new ChromeDriver();
		this.driver = new ChromeDriver();
		System.out.println("Browser started");
		// Maximize browser window
		driver.manage().window().maximize();
		System.out.println("Page is opened");

	}

	@Test(priority = 1, groups = { "positiveTests", "smokeTests" })
	public void positiveLoginTest() {
		System.out.println("Test started");

//		open test page
		String url = "https://the-internet.herokuapp.com/login";
		driver.get(url);
		System.out.println("Page is opened.");

//		enter username
		WebElement username = driver.findElement(By.id("username"));
		username.sendKeys("tomsmith");

//		enter password
		WebElement password = driver.findElement(By.name("password"));
		password.sendKeys("SuperSecretPassword!");

//		click login button
		WebElement logInButton = driver.findElement(By.tagName("button"));
		logInButton.click();

//		verifications:
//			new url
		String expectedUrl = "https://the-internet.herokuapp.com/secure";
		String actualUrl = driver.getCurrentUrl();
		Assert.assertEquals(actualUrl, expectedUrl, "Actual page url is not the same as expected");

//			logout button is visible
		WebElement logOutButton = driver.findElement(By.xpath("//a[@class='button secondary radius']"));
		Assert.assertTrue(logOutButton.isDisplayed(), "Log Out button is not vissible");

//			succesful login message
		// WebElement successMessage = driver.findElement(By.cssSelector("div#flash"));
		WebElement successMessage = driver.findElement(By.xpath("//div[@id='flash']"));
		String expectedMessage = "You logged into a secure area!";
		String actualMessage = successMessage.getText();
		// Assert.assertEquals(actualMessage, expectedMessage, "Actual message is not
		// the same as expected");
		Assert.assertTrue(actualMessage.contains(expectedMessage),
				"Actual message does not contain expected message.\nActual Message: " + actualMessage
						+ "\nExpected Message: " + expectedMessage);

		driver.close();
		System.out.println("Test Finished");

	}

	@Parameters({ "username", "password", "expectedMessage" })

	@Test(priority = 2, groups = { "negativeTests", "smokeTests" })
	public void negativeLoginTest(String username, String password, String expectedErrorMessage) {
		System.out.println("Starting negativeLoginTest" + username + " and " + password);

//	open test page
		String url = "https://the-internet.herokuapp.com/login";
		driver.get(url);

//	enter username 
		WebElement usernameElement = driver.findElement(By.id("username"));
		usernameElement.sendKeys(username);

//	enter password
		WebElement passwordElement = driver.findElement(By.name("password"));
		passwordElement.sendKeys(password);

//	click login button
		WebElement logInButton = driver.findElement(By.tagName("button"));
		logInButton.click();

		// verifications
		WebElement errorMessage = driver.findElement(By.id("flash"));
		String actualErrorMessage = errorMessage.getText();

		Assert.assertTrue(actualErrorMessage.contains(expectedErrorMessage),
				"Actual error message does not contain expected.\nActual: " + actualErrorMessage + "\nExpected: "
						+ expectedErrorMessage);

	}

	@AfterMethod(alwaysRun = true)
	private void tearDown() {
		// Close Browser
		driver.quit();
	}

}
