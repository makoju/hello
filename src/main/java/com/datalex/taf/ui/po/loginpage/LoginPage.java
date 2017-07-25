package com.datalex.taf.ui.po.loginpage;

import com.datalex.taf.ui.data.TestData;
import com.datalex.taf.ui.helpers.ElementHelper;
import com.datalex.taf.ui.helpers.Utils;
import com.datalex.taf.ui.po.searchpage.SearchPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/**
 * Login Page class
 *
 * @author Aleksanar Vulovic
 */
public class LoginPage implements ILoginPage {
    private WebDriver driver;

    @FindBy(id = "login")
    public WebElement userLogin;

    @FindBy(id = "password")
    public WebElement userPassword;

    @FindBy(xpath = ".//*[@id='signInForm']/div[2]/div[3]/button")
    public WebElement submitLogin;

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public SearchPage login(TestData testData) {
        if ("Signed in".equalsIgnoreCase(testData.getLoopProfile())) {
            new ElementHelper(driver).waitForElementToBeClickable(submitLogin);
            new ElementHelper(driver).waitForElementToBeClickable(userLogin);
            userLogin.sendKeys(testData.getEmail());
            new ElementHelper(driver).waitForElementToBeClickable(userPassword);
            userPassword.sendKeys(testData.getPassword());
            submitLogin.click();
            driver.switchTo().defaultContent();
            new Utils().waitTime(5000);
        }
        return new SearchPage(driver);
    }
}