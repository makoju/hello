package com.datalex.taf.ui.po.passengerspage;

import com.datalex.taf.core.loggers.TAFLogger;
import com.datalex.taf.ui.data.TestData;
import com.datalex.taf.ui.helpers.ElementHelper;
import com.datalex.taf.ui.travellers.Adult;
import com.datalex.taf.ui.travellers.Child;
import com.datalex.taf.ui.travellers.Infant;
import com.datalex.taf.ui.travellers.Traveller;
import com.github.javafaker.Faker;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/**
 * Passengers page class
 *
 * @author Aleksandar Vulovic
 */
@Log4j2
public class PassengersPage implements IPassengersPage {
    private WebDriver driver;

    @FindBy(id = "pgButtonProceed")
    public WebElement buttonProceed;

    @FindBy(id = "pgSeatSelect")
    public WebElement seatSelectButton;

    @FindBy(id = "emailAddress")
    public WebElement travellerEmailAddress;

    @FindBy(id = "confirmEmail")
    public WebElement travellerConfirmEmail;

    @FindBy(id = "mobilePhoneNumberCountryCode")
    public WebElement travellerCountryCode;

    @FindBy(id = "travellersInfo[0].mobilePhone.phoneNumber")
    public WebElement travellerPhoneNumber;

    private ElementHelper eh;

    public PassengersPage(WebDriver driver) {
        log.info("Initiating Passenger Page");
        this.driver = driver;
        eh = new ElementHelper(driver);
        eh.waitForPresenceOfElementLocated(By.id("pgTravellers"));
        eh.waitForElementToBeClickable(By.id("pgButtonProceed"));
        PageFactory.initElements(driver, this);
    }

    public void fillPassengersPage(TestData testData) throws Exception {
        log.info("Filling All Passengers Information");
        Traveller traveller = new Traveller(driver);

        for (int i = 0; i <= (traveller.getHowManyPassengerSelected()-1); i++){
            traveller.fillTravellerInformation(traveller.determinePassengerType(i), i);
            if(driver.findElements(By.id("travellersInfo[" + i + "].loyaltyMemberships[0]")).size() > 0)
                setFrequentFlierProgram(testData, i);
        }
        //traveller.fillTravellerEmergencyContactInformation(testData);
        populateContactDetails(testData);
    }

    public void setFrequentFlierProgram(TestData testData, int passengerNumber) {
        WebElement loyalty = driver.findElement(By.id("travellersInfo[" + passengerNumber + "].loyaltyMemberships[0]"));
        WebElement loyaltyNumber = driver.findElement(By.id("travellersInfo[" + passengerNumber + "].loyaltyNumbers[0]"));
        switch (testData.getFrequentFlierProgram()) {
            case "Agean":
                new ElementHelper().selectOptionByValue(loyalty, "A3");
                loyaltyNumber.sendKeys("992003004357384");
                break;
            case "Miles and More":
                new ElementHelper().selectOptionByValue(loyalty, "LH");
                loyaltyNumber.sendKeys("992003004357384");
                break;
            case "LOOP":
                new ElementHelper().selectOptionByValue(loyalty, "SN");
                break;
            default:
                TAFLogger.info("invalid frequent flier program");
        }
    }


    public void populateContactDetails(TestData testData) {
        Faker faker = new Faker();
        eh.waitForElementDisplayed(travellerEmailAddress);
        travellerEmailAddress.sendKeys(testData.getEmail());
        eh.waitForElementDisplayed(travellerConfirmEmail);
        travellerConfirmEmail.sendKeys(testData.getEmail());
        eh.waitForElementDisplayed(travellerPhoneNumber);
        travellerPhoneNumber.sendKeys(faker.phoneNumber().phoneNumber());
        eh.selectOptionByValue(travellerCountryCode, "US");
    }

    public void goToSeatSelect(){
        log.info("Passenger details filled. Going to Seat Selection page");
        eh.waitForElementToBeClickable(seatSelectButton);
        seatSelectButton.click();
    }

    public void goToPayment() {
        log.info("Passenger details filled. Going to Payment page");
        eh.waitForElementToBeClickable(buttonProceed);
        buttonProceed.click();
    }


}
