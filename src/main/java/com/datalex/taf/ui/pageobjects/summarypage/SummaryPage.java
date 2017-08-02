package com.datalex.taf.ui.pageobjects.summarypage;

import com.datalex.taf.ui.helpers.ElementHelper;
import com.datalex.taf.ui.pageobjects.passengerspage.PassengersPage;
import lombok.extern.log4j.Log4j2;
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
@Log4j2
public class SummaryPage {
    private WebDriver driver;
    private ElementHelper elementHelper;

    @FindBy(id = "pgButtonNext")
    public WebElement buttonNext;

    @FindBy(id = "changePricingView1")
    public WebElement showPriceInCash;

    @FindBy(id = "changePricingView2")
    public WebElement showPriceInLOOPS;

    @FindBy(xpath = "//*[contains (@class, 'button2')]//*[contains(text(),'Add baggage')]")
    public WebElement addBaggageButton;

    public SummaryPage(WebDriver driver) {
        log.info("Initiating Summary Page");
        this.driver = driver;
        elementHelper = new ElementHelper(driver);
        elementHelper.waitForPresenceOfElementLocated(By.id("pgItinerarySummary"));
        elementHelper.waitForElementToBeClickable(By.id("pgButtonNext"));
        PageFactory.initElements(driver, this);
    }

    public PassengersPage goToPassengersPage() {
        log.info("Summary Page Interaction Completed. Proceeding onwards...");
        elementHelper.waitForElementToBeClickable(buttonNext);
        buttonNext.click();
        return new PassengersPage(driver);
    }
}