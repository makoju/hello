package com.datalex.taf.ui.po.summarypage;

import com.datalex.taf.ui.helpers.ElementHelper;
import com.datalex.taf.ui.po.passengerspage.PassengersPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/**
 * Summary Page class
 *
 * @author Aleksandar Vulovic
 */
public class SummaryPage {
    private WebDriver driver;

    @FindBy(id = "pgButtonNext")
    public WebElement buttonNext;

    @FindBy(id = "changePricingView1")
    public WebElement showPriceInCash;

    @FindBy(id = "changePricingView2")
    public WebElement showPriceInLOOPS;

    @FindBy(xpath = "//*[contains (@class, 'button2')]//*[contains(text(),'Add baggage')]")
    public WebElement addBaggageButton;

    public SummaryPage(WebDriver driver) {
        this.driver = driver;
        new ElementHelper(driver).waitForPresenceOfElementLocated(By.id("pgItinerarySummary"));
        new ElementHelper(driver).waitForElementToBeClickable(By.id("pgButtonNext"));
        PageFactory.initElements(driver, this);
    }

    public PassengersPage goToPassengersPage() {
        new ElementHelper(driver).waitForElementToBeClickable(buttonNext);
        buttonNext.click();
        return new PassengersPage(driver);
    }
}