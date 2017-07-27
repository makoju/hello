package com.datalex.taf.ui.po.confirmationpage;

import com.datalex.taf.ui.helpers.ElementHelper;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/**
 * Confirmation page class
 *
 * @author Aleksandar Vulovic
 */
public class ConfirmationPage implements IConfirmationPage {
    private WebDriver driver;

    @FindBy(id = "pgButtonSubmit2")
    public WebElement buttonSubmit;

    public ConfirmationPage(WebDriver driver) {
        this.driver = driver;
        new ElementHelper(driver).waitForPresenceOfElementLocated(By.id("pgConfirmation"));
        PageFactory.initElements(driver, this);
    }
}