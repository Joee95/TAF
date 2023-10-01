package tests;


import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import data.LoadProperties;

public class SwinjiCourseCreation {

	public WebDriver driver;

	String Username = LoadProperties.userData.getProperty("Username");
	String Password = LoadProperties.userData.getProperty("Password");
	String Selectsubject = LoadProperties.userData.getProperty("Selectsubject");
	String Selectgrade = LoadProperties.userData.getProperty("Selectgrade");

	@BeforeSuite
	public void startDriver() 
	{
		System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")+"/drivers/chromedriver.exe");
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--incognito");
		driver = new ChromeDriver(options);
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(120, TimeUnit.SECONDS);
		driver.navigate().to("https://swinji.azurewebsites.net");
	}

	@Test(priority = 1)
	public void signIn ()
	{
		// Login Page
		WebElement emailInput = driver.findElement(By.xpath("//input[@id='Email']"));
		emailInput.sendKeys(Username);

		WebElement passwordInput = driver.findElement(By.xpath("//input[@id='inputPassword']"));
		passwordInput.sendKeys(Password);

		WebElement loginButton = driver.findElement(By.id("btnLogin"));
		loginButton.click();
	}

	@Test(priority = 2)
	public void CreateNewCourse () 
	{


		// Open the courses page
		WebDriverWait wait = new WebDriverWait(driver, 10);
		WebElement coursesLink = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@id='btnMyCoursesList']")));
		coursesLink.click();

		// Click on the "Create Course" button
		WebElement createCourseButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("btnListAddCourse")));
		createCourseButton.click();

		// Fill in the basic course info and save
		WebElement nameInput = driver.findElement(By.xpath("//input[@id='txtCourseName']"));
		nameInput.sendKeys("Selenium webdriver");

		WebElement SelectSubject = driver.findElement(By.xpath("//select[@id='courseSubject']"));
		Select selectsubject = new Select(SelectSubject);
		selectsubject.selectByVisibleText(Selectsubject);

		// scroll down to find element 
		WebElement selectGrade = driver.findElement(By.name("//i[@class='lms-margin-end-5 lms-position-relative-imp']"));
		String JS_Syntax = "arguments[0].scrollIntoView();";
		((JavascriptExecutor)driver).executeScript(JS_Syntax, selectGrade);
		selectGrade.click();

		WebElement teacher = driver.findElement(By.xpath("//input[@type='search']"));
		teacher.click();

		WebElement teachername = driver.findElement(By.xpath("//span[@id='lnkCourseOwnerMail']"));
		teachername.click();

		WebElement chooseNone = driver.findElement(By.xpath("//i[@class='lms-position-relative-imp']"));
		chooseNone.click();

		WebElement CreateButton = driver.findElement(By.xpath("//button[@id='btnSaveAsDraftCourse']"));
		CreateButton.click();

		Assert.assertEquals(coursesLink.isDisplayed(), true);
	}

	@AfterSuite
	public void CloseDriver ()
	{
		driver.quit();
	}
}
