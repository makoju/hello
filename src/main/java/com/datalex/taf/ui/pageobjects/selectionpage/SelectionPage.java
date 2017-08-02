package com.datalex.taf.ui.pageobjects.selectionpage;

import com.datalex.taf.ui.data.TestData;
import com.datalex.taf.ui.helpers.ElementHelper;
import com.datalex.taf.ui.helpers.Utils;
import com.datalex.taf.ui.pageobjects.summarypage.SummaryPage;
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
    private ElementHelper elementHelper;

    @FindBy(id = "pgButtonNext")
    public WebElement buttonSelection;

    public SelectionPage(WebDriver driver) {
        log.info("Initiating Selection Page");
        this.driver = driver;
        elementHelper = new ElementHelper(driver);
        elementHelper.waitForPresenceOfElementLocated(By.id("pgFlightSelection"));
        elementHelper.waitForElementToBeClickable(By.id("pgButtonNext"));
        PageFactory.initElements(driver, this);
    }

    public void selectOutboundFareFamily(String fareFamily) {
        log.info("Selecting Outbound Fare Family");
        elementHelper.waitForElementDisplayed(getSelectFlightRadioLocator(getFFAlias(fareFamily), 0));
        elementHelper.waitForElementToBeClickable(getSelectFlightRadioLocator(getFFAlias(fareFamily), 0));
        getSelectFlightRadioLocator(getFFAlias(fareFamily), 0).click();
    }

    public void selectInboundFareFamily(String fareFamily) {
        log.info("Selecting Inbound Fare Family");
        elementHelper.waitForElementDisplayed(getSelectFlightRadioLocator(getFFAlias(fareFamily), 1));
        elementHelper.waitForElementToBeClickable(getSelectFlightRadioLocator(getFFAlias(fareFamily), 1));
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
            case "BIZZ&CLASS":
                return "BUSINESS";
            case "CHECK&GO":
            case "BUDGET":
                return "BUDGET";
            case "COMFORT":
            case "LIGHT&RELAX":
                return "COMFORT";
            case "BEST":
            case "FLEX&FAST":
                return "BEST";
            default:
                return "";
        }
    }

    public void selectFareFamily(TestData testData) {
        log.info("Select fare family: " + testData.getFareFamily());
        selectOutboundFareFamily(testData.getFareFamily());
        if (("RT").equalsIgnoreCase(testData.getTripType()))
            selectInboundFareFamily(testData.getFareFamily());
    }

    public SummaryPage doSelection() {
        log.info("Flight Selection activity completed. Proceeding onwards...");
        new Utils().waitTime(2000);
        elementHelper.waitForElementToBeClickable(buttonSelection);
        buttonSelection.click();
        return new SummaryPage(driver);
    }
}