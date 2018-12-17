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
	private final String USERNAME = "SashaShust";
	private final String PASSWORD = "1521739";

	private final String USERNAME_FOR_CREATE_USER = "someuser";
	private final String FULLNAME_FOR_CREATE_USER = "Some Full Name";
	private final String EMAIL_FOR_CREATE_USER = "some@addr.dom";
	private final String PASSWORD_FOR_CREATE_USER = "somepassword";

	StringBuffer verificationErrors = new StringBuffer();
	WebDriver driver = null;

	@BeforeClass
	public void beforeClass() throws Exception {
		System.setProperty("webdriver.chrome.driver", "c:/Users/User/Downloads/chromedriver_win32/chromedriver.exe");
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
		driver.get(base_url);
		WebElement usernameField = driver.findElement(By.id("j_username"));
		usernameField.sendKeys(USERNAME);
		WebElement passwordField = driver.findElement(By.xpath("//input[@placeholder='Password']"));
		passwordField.sendKeys(PASSWORD);
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

		assertTrue((page.getCreateDeleteModifyUsersThatCanLogInToThisJenkins().isEnabled() && ((page
				.getManageUsersLink().isEnabled()))));

		page.clickManageUsersLink();

		assertTrue(page.getCreateUserLink().isEnabled());

		page.clickCreateUserLink();

		assertTrue(page.isFormPresentForReal(), "No suitable forms found!");

		assertTrue(page.checkTextField(page.getFullname()));
		assertTrue(page.checkTextField(page.getEmail()));
		assertTrue(page.checkTextField(page.getUsername()));
		assertTrue(page.checkTextField(page.getPassword1()));
		assertTrue(page.checkTextField(page.getPassword2()));

		page.setFields(USERNAME_FOR_CREATE_USER, PASSWORD_FOR_CREATE_USER, PASSWORD_FOR_CREATE_USER,
				FULLNAME_FOR_CREATE_USER, EMAIL_FOR_CREATE_USER);
		page.clickCreateUserButton();
		assertTrue(page.getSomeuserLink().isEnabled());

		page.clickdeleteUserLink();
		assertTrue(page.textMessageEquals("Are you sure about deleting the user from Jenkins?"));
		page.clickYesButton();
		assertFalse(driver.findElements(By.xpath("//a[@href='user/someuser/']")).size() > 0);
		assertFalse(page.isDeleteButonExist());
		assertFalse(driver.findElements(By.xpath("//*[@href='user/admin/delete']")).size() > 0);

	}
}
