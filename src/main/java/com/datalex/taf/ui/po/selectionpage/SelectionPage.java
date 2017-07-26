package com.datalex.taf.ui.po.selectionpage;

import com.datalex.taf.ui.helpers.ElementHelper;
import com.datalex.taf.ui.po.summarypage.SummaryPage;
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
public class SelectionPage implements ISelectionPage {
    private WebDriver driver;

    @FindBy(id = "pgButtonNext")
    public WebElement buttonSelection;

    public SelectionPage(WebDriver driver) {
        this.driver = driver;
        new ElementHelper(driver).waitForPresenceOfElementLocated(By.id("pgFlightSelection"));
        new ElementHelper(driver).waitForElementToBeClickable(By.id("pgButtonNext"));
        PageFactory.initElements(driver, this);
    }

    public void selectInboundFareFamily(String fareFamily) {
        new ElementHelper(driver).waitForElementDisplayed(getSelectFlightRadioLocator(getFFAlias(fareFamily), 0));
        new ElementHelper(driver).waitForElementToBeClickable(getSelectFlightRadioLocator(getFFAlias(fareFamily), 0));
        getSelectFlightRadioLocator(getFFAlias(fareFamily), 0).click();
    }

    public void selectReturnFareFamily(String fareFamily) {
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
        new ElementHelper(driver).waitForElementToBeClickable(buttonSelection);
        buttonSelection.click();
        return new SummaryPage(driver);
    }
}