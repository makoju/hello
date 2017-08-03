package com.datalex.taf.ui.pageobjects.passengerspage;

import com.datalex.taf.core.loggers.TAFLogger;
import com.datalex.taf.ui.data.TestData;
import com.datalex.taf.ui.helpers.ElementHelper;
import com.datalex.taf.ui.pageobjects.passengerspage.sections.ContactDetails;
import com.datalex.taf.ui.pageobjects.passengerspage.sections.PassengerDetails;
import com.datalex.taf.ui.pageobjects.seatspage.SeatsPage;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

/**
 * Passengers page class
 *
 * @author Aleksandar Vulovic
 */
@Log4j2
public class PassengersPage implements IPassengersPage {
    private WebDriver driver;
    private ElementHelper elementHelper;
    private PassengerDetails passengerDetails;
    private ContactDetails contactDetails;

    @FindBy(id = "pgButtonProceed")
    public WebElement buttonProceed;

    public PassengersPage(WebDriver driver) {
        log.info("Initiating Passenger Page");
        this.driver = driver;
        elementHelper = new ElementHelper(driver);
        elementHelper.waitForPresenceOfElementLocated(By.id("pgTravellers"));
        elementHelper.waitForElementToBeClickable(By.id("pgButtonProceed"));
        passengerDetails = new PassengerDetails(driver);
        contactDetails = new ContactDetails(driver);
        PageFactory.initElements(driver, this);
    }

    public void fillPassengersPage(TestData testData) throws Exception {
        log.info("Filling All Passengers Information");

        for (int i = 0; i <= (getHowManyPassengerSelected() - 1); i++) {
            passengerDetails.fillTravellerInformation(determinePassengerType(i), i);
            if (isLoyalty(i))
                setFrequentFlierProgram(testData, i);
        }
        //new EmergencyContactDetails(driver).fillTravellerEmergencyContactInformation(testData);
        contactDetails.populateContactDetails(testData);
    }

    public boolean isLoyalty(int paxNumber) {
        List<WebElement> loyaltyMemberships = driver.findElements(By.id("travellersInfo[" + paxNumber + "].loyaltyMemberships[0]"));
        return loyaltyMemberships.size() > 0;
    }

    public void setFrequentFlierProgram(TestData testData, int passengerNumber) {
        WebElement loyalty = driver.findElement(By.id("travellersInfo[" + passengerNumber + "].loyaltyMemberships[0]"));
        WebElement loyaltyNumber = driver.findElement(By.id("travellersInfo[" + passengerNumber + "].loyaltyNumbers[0]"));
        if (testData.getFrequentFlierProgram() == null) {
            log.warn("FrequentFlierProgram is null");
        } else {
            switch (testData.getFrequentFlierProgram()) {
                case "Agean":
                    elementHelper.selectOptionByValue(loyalty, "A3");
                    loyaltyNumber.sendKeys("992003004357384");
                    break;
                case "Miles and More":
                    elementHelper.selectOptionByValue(loyalty, "LH");
                    loyaltyNumber.sendKeys("992003004357384");
                    break;
                case "LOOP":
                    elementHelper.selectOptionByValue(loyalty, "SN");
                    break;
                default:
                    TAFLogger.info("invalid frequent flier program");
            }
        }
    }

    public String determinePassengerType(int passengerNumber) {
        WebElement travelersInfo = driver.findElement(By.xpath("//input[@id='travellersInfo[" + passengerNumber + "].travellerType']"));
        log.debug("Traveller Type is: " + travelersInfo.getAttribute("value"));
        return travelersInfo.getAttribute("value");
    }

    public int getHowManyPassengerSelected() {
        List<WebElement> passengerBlock = driver.findElements(By.xpath("//div[contains(@class,'passengerBlock')]"));
        log.debug("Number of Passengers" + passengerBlock.size());
        return passengerBlock.size();
    }

    public SeatsPage goToSeatSelect() {
        log.info("Passenger details filled. Going to Seat Selection page");
        elementHelper.waitForElementToBeClickable(buttonProceed);
        buttonProceed.click();
        return new SeatsPage(driver);
    }
}