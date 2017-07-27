package com.datalex.taf.ui.po.selectionpage;

import com.datalex.taf.ui.helpers.ElementHelper;
import com.datalex.taf.ui.po.summarypage.SummaryPage;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/**
 * SelectionPage class
 *
 * @author Aleksandar Vulovic
 */
@Log4j2
public class SelectionPage implements ISelectionPage {
    private WebDriver driver;

    @FindBy(id = "pgButtonNext")
    public WebElement buttonSelection;

    public SelectionPage(WebDriver driver) {
        log.info("Initiating Selection Page");
        this.driver = driver;
        new ElementHelper(driver).waitForPresenceOfElementLocated(By.id("pgFlightSelection"));
        new ElementHelper(driver).waitForElementToBeClickable(By.id("pgButtonNext"));
        PageFactory.initElements(driver, this);
    }

    public void selectOutboundFareFamily(String fareFamily) {
        log.info("Selecting Outbound Fare Family");
        new ElementHelper(driver).waitForElementDisplayed(getSelectFlightRadioLocator(getFFAlias(fareFamily), 0));
        new ElementHelper(driver).waitForElementToBeClickable(getSelectFlightRadioLocator(getFFAlias(fareFamily), 0));
        getSelectFlightRadioLocator(getFFAlias(fareFamily), 0).click();
    }

    public void selectInboundFareFamily(String fareFamily) {
        log.info("Selecting Inbound Fare Family");
        new ElementHelper(driver).waitForElementDisplayed(getSelectFlightRadioLocator(getFFAlias(fareFamily), 1));
        new ElementHelper(driver).waitForElementToBeClickable(getSelectFlightRadioLocator(getFFAlias(fareFamily), 1));
        getSelectFlightRadioLocator(getFFAlias(fareFamily), 1).click();
    }

    public WebElement getSelectFlightRadioLocator(String fareFamilyAlias, int blockNumber) {
        return driver.findElement(By.xpath("//input[contains(@name, 'flightItineraryGroupId[" + blockNumber + "]')" +
                " or contains(@name, 'flightItineraryId[" + blockNumber + "]')]" +
                "[contains(@onclick, '" + fareFamilyAlias + "')]" +
                "[not(@type='hidden')]"));
    }

    public String getFFAlias(String fareFamilyName) {
        switch (fareFamilyName.toUpperCase()) {
            case "ECONOMY":
                return "CODESHAREECONOMY";
            case "BUSINESS":
                return "CODESHAREBUSINESS";
            case "CHECK&GO":
            case "BUDGET":
                return "BUDGET";
            case "COMFORT":
            case "LIGHTRELAX":
                return "COMFORT";
            case "BEST":
            case "FLEXFAST":
                return "BEST";
            default:
                return "";
        }
    }

    public SummaryPage doSelection() {
        log.info("Flight Selection activity completed. Proceeding onwards...");
        new ElementHelper(driver).waitForElementToBeClickable(buttonSelection);
        buttonSelection.click();
        return new SummaryPage(driver);
    }
}