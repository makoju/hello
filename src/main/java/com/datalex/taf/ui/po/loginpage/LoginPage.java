package com.datalex.taf.ui.po.loginpage;

import com.datalex.taf.ui.data.TestData;
import com.datalex.taf.ui.helpers.ElementHelper;
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

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(id = "login")
    public WebElement userLogin;

    @FindBy(id = "password")
    public WebElement userPassword;

    @FindBy(xpath = ".//*[@id='signInForm']/div[2]/div[3]/button")
    public WebElement submitLogin;

    public void login(TestData testData) {
        new ElementHelper(driver).waitForElementPresent(userLogin);
        userLogin.clear();
        userLogin.sendKeys("alan.peck@datalex.com");
        userPassword.clear();
        userPassword.sendKeys("Datalex123!");
        submitLogin.click();
        driver.switchTo().defaultContent();
    }
}
