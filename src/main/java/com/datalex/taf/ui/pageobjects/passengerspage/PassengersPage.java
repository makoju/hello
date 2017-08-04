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

    /**
     * Passengers Page constructor
     *
     * @param driver
     */
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

    /**
     * This method will fill Passengers Page using Test Data class
     *
     * @param testData TestData class
     * @throws Exception if error occurs
     */
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

    /**
     * Method will check for loyalty membership
     *
     * @param paxNumber int number of pax
     * @return boolean
     */
    public boolean isLoyalty(int paxNumber) {
        List<WebElement> loyaltyMemberships = driver.findElements(By.id("travellersInfo[" + paxNumber + "].loyaltyMemberships[0]"));
        return loyaltyMemberships.size() > 0;
    }

    /**
     * This method will set frequent flier program on Passengers page
     *
     * @param testData        TestData class
     * @param passengerNumber int number of pax
     */
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

    /**
     * This method will determine pax type using locator
     *
     * @param passengerNumber int number of pax
     * @return String with pax type
     */
    public String determinePassengerType(int passengerNumber) {
        WebElement travelersInfo = driver.findElement(By.xpath("//input[@id='travellersInfo[" + passengerNumber + "].travellerType']"));
        log.debug("Traveller Type is: " + travelersInfo.getAttribute("value"));
        return travelersInfo.getAttribute("value");
    }

    /**
     * This method will return number of passengers selected
     *
     * @return int number of pax
     */
    public int getHowManyPassengerSelected() {
        List<WebElement> passengerBlock = driver.findElements(By.xpath("//div[contains(@class,'passengerBlock')]"));
        log.debug("Number of Passengers" + passengerBlock.size());
        return passengerBlock.size();
    }

    /**
     * This page will go to SeatSelection Page
     *
     * @return SeatsPage instance
     */
    public SeatsPage goToSeatSelect() {
        log.info("Passenger details filled. Going to Seat Selection page");
        elementHelper.waitForElementToBeClickable(buttonProceed);
        buttonProceed.click();
        return new SeatsPage(driver);
    }
}