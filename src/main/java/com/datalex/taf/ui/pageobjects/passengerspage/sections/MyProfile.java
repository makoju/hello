package com.datalex.taf.ui.pageobjects.passengerspage.sections;

import com.datalex.taf.ui.helpers.ElementHelper;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/**
 * Fill Emergency Contact details section on Passenger Page
 *
 * @author Aleksandar Vulovic
 */
public class MyProfile {
    private WebDriver driver;
    private ElementHelper elementHelper;

    @FindBy(id = "login")
    public WebElement login;

    @FindBy(id = "password")
    public WebElement password;

    @FindBy(className = "btn_bordered btn_grey btn_fullWidth")
    public WebElement loginButton;

    public MyProfile(WebDriver driver) {
        this.driver = driver;
        elementHelper = new ElementHelper(driver);
        PageFactory.initElements(driver, this);
    }
}