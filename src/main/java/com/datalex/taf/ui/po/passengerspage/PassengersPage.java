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

    public void fillPassengersPage() throws Exception {
        log.info("Filling All Passengers Information");
        Traveller traveller = new Traveller(driver);

        for (int i = 0; i <= (traveller.getHowManyPassengerSelected()-1); i++){
            traveller.fillTravellerInformation(traveller.determinePassengerType(i), i);
        }

//        int adtQty = Integer.parseInt(testData.getAdt());
//        int chdQty = Integer.parseInt(testData.getChd());
//        int infQty = Integer.parseInt(testData.getInf());
//
//        int travellersQty = adtQty + chdQty + infQty;
//        for (int i = 0; i <= travellersQty - 1; i++) {
//            populatePassengerDetails(testData, i);
//            populatePassportData(testData, i);
//            setFrequentFlierProgram(testData, i);
//            populatePassengerDoB(testData, i);
//        }
//        populateContactDetails(testData);

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

//    protected void fillPaxDetail(int passengerNumber) {
//        Faker faker = new Faker();
//        WebElement title = driver.findElement(By.id("travellersInfo[" + passengerNumber + "].title"));
//        eh.waitForElementDisplayed(title);
//        eh.selectOptionByText(title, "Mr");
//        driver.findElement(By.id("travellersInfo[" + passengerNumber + "].firstName")).sendKeys(faker.name().firstName());
//        driver.findElement(By.id("travellersInfo[" + passengerNumber + "].middleName")).sendKeys(faker.name().firstName());
//        driver.findElement(By.id("travellersInfo[" + passengerNumber + "].lastName")).sendKeys(faker.name().lastName());
//    }

    public void populatePassportData(TestData testData, int passengerNumber) {
        WebElement country = driver.findElement(By.id("travellersInfo[" + passengerNumber + "].citizenCountry"));
        eh.waitForElementDisplayed(country);
        eh.selectOptionByValue(country, "US");

        WebElement passport = driver.findElement(By.id("travellersInfo[" + passengerNumber + "].advancedPassengerDetails(foid)"));
        eh.waitForElementDisplayed(passport);
        eh.selectOptionByValue(passport, "ID_CARD");

        WebElement foidNumber = driver.findElement(By.id("travellersInfo[" + passengerNumber + "].advancedPassengerDetails(foidNumber)"));
        eh.waitForElementDisplayed(foidNumber);
        foidNumber.sendKeys("1234567890");

        WebElement foidExpireDay = driver.findElement(By.id("travellersInfo[" + passengerNumber + "].advancedPassengerDetails(foidExpireDay)"));
        eh.waitForElementDisplayed(foidExpireDay);
        eh.selectOptionByValue(foidExpireDay, "1");

        WebElement foidExpireMonth = driver.findElement(By.id("travellersInfo[" + passengerNumber + "].advancedPassengerDetails(foidExpireMonth)"));
        eh.waitForElementDisplayed(foidExpireMonth);
        eh.selectOptionByValue(foidExpireMonth, "1");

        WebElement foidExpireYear = driver.findElement(By.id("travellersInfo[" + passengerNumber + "].advancedPassengerDetails(foidExpireYear)"));
        eh.waitForElementDisplayed(foidExpireYear);
        eh.selectOptionByValue(foidExpireYear, "2022");

        WebElement issuingCountry = driver.findElement(By.id("travellersInfo[" + passengerNumber + "].advancedPassengerDetails(foidCountry)"));
        eh.waitForElementDisplayed(issuingCountry);
        eh.selectOptionByValue(issuingCountry, "US");
    }


    public void populatePassengerDoB(TestData testData, int passengerNumber) {
        WebElement calendarDialog = driver.findElement(By.id("travellersInfo[" + passengerNumber + "].advancedPassengerDetails(dobDate)"));
        calendarDialog.click();
        eh.waitForElementPresent(calendarDialog);
        eh.executeScript(driver, "document.getElementById('travellersInfo[" + passengerNumber + "].advancedPassengerDetails(dobDay)').setAttribute('value', '12')");
        eh.executeScript(driver, "document.getElementById('travellersInfo[" + passengerNumber + "].advancedPassengerDetails(dobMonth)').setAttribute('value', '12')");
        eh.executeScript(driver, "document.getElementById('travellersInfo[" + passengerNumber + "].advancedPassengerDetails(dobYear)').setAttribute('value', '1984')");
        driver.findElement(By.xpath("//td[. = \"12\"]")).click();
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
