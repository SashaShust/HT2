package com.epam.seleniumwdtestjenkins;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.WebDriverWait;

public class PageObjectForTestJenkins {

	private WebDriverWait wait;
	private final WebDriver driver;

	@FindBy(xpath = "/body")
	private WebElement body;

	@FindBy(xpath = "//a[@href='/manage']")
	private WebElement manageJenkinsLink;

	@FindBy(xpath = "//a[@href='securityRealm/' and @title='Manage Users']")
	private WebElement manageUsersLink;

	@FindBy(xpath = "//*[@id='main-panel']/div[16]/a/dl/dd[1]")
	private WebElement CreateDeleteModifyUsersThatCanLogInToThisJenkins;

	@FindBy(xpath = "//*[@href='addUser']")
	private WebElement createUserLink;

	@FindBy(xpath = "//*[@id='main-panel']/form")
	private WebElement formWithTextsAndPasswords;

	@FindBy(xpath = "//input[@id='username']")
	private WebElement username;

	@FindBy(xpath = "//input[@name='fullname']")
	private WebElement fullname;

	@FindBy(xpath = "//input[@name='email']")
	private WebElement email;

	@FindBy(xpath = "//input[@name='password1']")
	private WebElement password1;

	@FindBy(xpath = "//input[@name='password2']")
	private WebElement password2;

	@FindBy(xpath = "//button[@id='yui-gen1-button']")
	private WebElement createUserButton;

	@FindBy(xpath = "//a[@href='user/someuser/']")
	private WebElement someuserLink;

	@FindBy(xpath = "//*[@href='user/someuser/delete']")
	private WebElement deleteUserLink;

	@FindBy(xpath = "//*[@id='main-panel']/form")
	private WebElement textMessage;

	@FindBy(xpath = "//button[@id='yui-gen1-button']")
	private WebElement yes;

	@FindBy(xpath = "/body")
	private WebElement userAdminDelete;

	public PageObjectForTestJenkins(WebDriver driver) {
		this.driver = driver;
		this.wait = new WebDriverWait(this.driver, 30);

		// Checking the fact that we are on the right page.
		if ((!driver.getTitle().equals("Dashboard [Jenkins]"))
				|| (!driver.getCurrentUrl().equals("http://localhost:8081/"))) {
			throw new IllegalStateException("Wrong site page!");
		}
	}

	public PageObjectForTestJenkins clickManageJenkins() {

		manageJenkinsLink.click();
		return this;
	}

	public WebElement getManageUsersLink() {
		return manageUsersLink;
	}

	public WebElement getCreateDeleteModifyUsersThatCanLogInToThisJenkins() {
		return CreateDeleteModifyUsersThatCanLogInToThisJenkins;
	}

	public PageObjectForTestJenkins clickManageUsersLink() {
		manageUsersLink.click();
		return this;
	}

	public WebElement getCreateUserLink() {
		return createUserLink;
	}

	public PageObjectForTestJenkins clickCreateUserLink() {
		createUserLink.click();
		return this;
	}

	boolean isDeleteButonExist() {
		return driver.findElements(By.xpath("//*[@href='user/someuser/delete']")).size() > 0;
	}

	public boolean isFormPresentForReal() {
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		Collection<WebElement> forms = driver.findElements(By.xpath("//*[@id='main-panel']/form"));
		if (forms.isEmpty()) {
			return false;
		}
		Iterator<WebElement> i = forms.iterator();
		boolean form_found = false;
		WebElement form = null;

		while (i.hasNext()) {
			form = i.next();
			if ((form.findElement(By.xpath("//input[@id='username']")).getAttribute("type").equalsIgnoreCase("text"))
					&& (form.findElement(By.name("fullname")).getAttribute("type").equalsIgnoreCase("text"))
					&& (form.findElement(By.name("email")).getAttribute("type").equalsIgnoreCase("text"))
					&& (form.findElement(By.name("password1")).getAttribute("type").equalsIgnoreCase("password"))
					&& (form.findElement(By.name("password2")).getAttribute("type").equalsIgnoreCase("password"))) {
				form_found = true;
				break;
			}
		}

		return form_found;
	}

	public boolean checkTextField(WebElement field) {
		if (field.getText().length() == 0) {
			return true;
		} else {
			return false;
		}
	}

	// Filling in username.
	public PageObjectForTestJenkins setUsername(String value) {
		username.clear();
		username.sendKeys(value);
		return this;
	}

	// Filling in fullName.
	public PageObjectForTestJenkins setFullName(String value) {
		fullname.clear();
		fullname.sendKeys(value);
		return this;
	}

	// Filling in email.
	public PageObjectForTestJenkins setEmail(String value) {
		email.clear();
		email.sendKeys(value);
		return this;
	}

	// Filling in password1.
	public PageObjectForTestJenkins setPassword1(String value) {
		password1.clear();
		password1.sendKeys(value);
		return this;
	}

	// Filling in password2.
	public PageObjectForTestJenkins setPassword2(String value) {
		password2.clear();
		password2.sendKeys(value);
		return this;
	}

	// Filling in all fields of the form.
	public PageObjectForTestJenkins setFields(String username, String password, String confermPassword,
			String fullName, String email) {
		setUsername(username);
		setPassword1(password);
		setPassword2(confermPassword);
		setFullName(fullName);
		setEmail(email);
		return this;
	}

	public PageObjectForTestJenkins clickCreateUserButton() {
		createUserButton.click();
		return this;
	}

	public PageObjectForTestJenkins clickdeleteUserLink() {
		deleteUserLink.click();
		return this;
	}

	public WebElement getTextMessage() {
		return textMessage;
	}

	public PageObjectForTestJenkins clickYesButton() {
		yes.click();
		return this;
	}

	// Check whether a custom message is equal to a string.
	public boolean textMessageEquals(String search_string) {
		return getTextNode(textMessage).trim().equals(search_string);
	}

	/**
	 * Takes a parent element and strips out the textContent of all child
	 * elements and returns textNode content only
	 * 
	 * @param e
	 *            the parent element
	 * @return the text from the child textNodes
	 */
	public static String getTextNode(WebElement e) {
		String text = e.getText().trim();
		List<WebElement> children = e.findElements(By.xpath("./*"));
		for (WebElement child : children) {
			text = text.replaceFirst(child.getText(), "").trim();
		}
		return text;
	}

	public WebElement getDeleteUserLink() {
		return deleteUserLink;
	}

}
