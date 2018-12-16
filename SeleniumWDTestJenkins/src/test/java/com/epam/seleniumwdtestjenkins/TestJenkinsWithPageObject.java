package com.epam.seleniumwdtestjenkins;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import java.util.concurrent.TimeUnit;

import org.junit.AfterClass;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class TestJenkinsWithPageObject {
	String base_url = "http://localhost:8081/";
	StringBuffer verificationErrors = new StringBuffer();
	WebDriver driver = null;

	@BeforeClass
	public void beforeClass() throws Exception {
		System.setProperty("webdriver.chrome.driver", "c:/Users/User/Downloads/chromedriver_win32/chromedriver.exe");
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
		driver.get("http://localhost:8081/");
		WebElement usernameField = driver.findElement(By.id("j_username"));
		usernameField.sendKeys("SashaShust");
		WebElement passwordField = driver.findElement(By.xpath("//input[@placeholder='Password']"));
		passwordField.sendKeys("1521739");
		WebElement signInButton = driver.findElement(By.xpath("//input[@name='Submit']"));
		signInButton.click();
	}

	@AfterClass
	public void afterClass() {
		driver.quit();

		String verificationErrorString = verificationErrors.toString();
		if (!"".equals(verificationErrorString)) {
			Assert.fail(verificationErrorString);
		}
	}

	@Test
	public void sampleTest() {
		PageObjectForTestJenkins page = PageFactory.initElements(driver, PageObjectForTestJenkins.class);

		page.clickManageJenkins();
		// System.out.print();
		// assertTrue(driver.findElements(By.xpath("//a[@title='Manage Users']")).size()
		// > 0);

		assertTrue((driver.findElements(By.xpath("//a[@href='securityRealm/' and @title='Manage Users']")).size() > 0)
				&& (driver.findElements(By.xpath("//*[@id='main-panel']/div[16]/a/dl/dd[1]")).size() > 0));

		page.clickManageUsersLink();

		assertTrue(driver.findElement(By.xpath("//*[@href='addUser']")).isEnabled());

		page.clickCreateUserLink();

		assertTrue(page.isFormPresentForReal(), "No suitable forms found!");

		assertTrue(page.checkTextField(driver.findElement(By.name("fullname"))));
		assertTrue(page.checkTextField(driver.findElement(By.name("email"))));
		assertTrue(page.checkTextField(driver.findElement(By.xpath("//input[@id='username']"))));
		assertTrue(page.checkTextField(driver.findElement(By.name("password1"))));
		assertTrue(page.checkTextField(driver.findElement(By.name("password2"))));

		page.setFields("someuser", "somepassword", "somepassword", "Some Full Name", "some@addr.dom");
		page.clickCreateUserButton();
		assertTrue(driver.findElements(By.xpath("//a[@href='user/someuser/']")).size() > 0);

		page.clickdeleteUserLink();
		assertTrue(page.textMessageEquals("Are you sure about deleting the user from Jenkins?"));
		page.clickYesButton();
		assertFalse(driver.findElements(By.xpath("//a[@href='user/someuser/']")).size() > 0);
		// assertFalse(driver.findElements(By.xpath("//*[@href='user/someuser/delete']")).size()
		// > 0);
		assertFalse(page.isDeleteButonExist());
		assertFalse(driver.findElements(By.xpath("//*[@href='user/admin/delete']")).size() > 0);
	}
}
