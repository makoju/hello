package com.datalex.taf.ui.po.passengerspage;

import com.datalex.taf.core.loggers.TAFLogger;
import com.datalex.taf.ui.data.TestData;
import com.datalex.taf.ui.helpers.ElementHelper;
import com.datalex.taf.ui.po.seatspage.SeatsPage;
import com.github.javafaker.Faker;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.Locale;

/**
 * Passengers page class
 *
 * @author Aleksandar Vulovic
 */
@Log4j2
public class PassengersPage implements IPassengersPage {
    private WebDriver driver;
    private ElementHelper elementHelper;

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

    @FindBy(id = "cdrControlMonth")
    public WebElement month;

    @FindBy(id = "cdrControlYear")
    public WebElement year;

    @FindBy(id = "travellersInfo[0].mobilePhone.phoneNumber")
    public WebElement travellerPhoneNumber;

    public PassengersPage(WebDriver driver) {
        log.info("Initiating Passenger Page");
        this.driver = driver;
        elementHelper = new ElementHelper(driver);
        elementHelper.waitForPresenceOfElementLocated(By.id("pgTravellers"));
        elementHelper.waitForElementToBeClickable(By.id("pgButtonProceed"));
        PageFactory.initElements(driver, this);
    }

    public void fillTravellersPage(TestData testData) {
        int adtQty = Integer.parseInt(testData.getAdt());
        int chdQty = Integer.parseInt(testData.getChd());
        int infQty = Integer.parseInt(testData.getInf());

        int travellersQty = adtQty + chdQty + infQty;
        for (int i = 0; i <= travellersQty - 1; i++) {
            populatePassengerDetails(testData, i);
            populatePassportData(testData, i);
            setFrequentFlierProgram(testData, i);
            populatePassengerDoB(testData, i);
        }
        populateContactDetails(testData);

    }

    public void setFrequentFlierProgram(TestData testData, int passengerNumber) {
        WebElement loyalty = driver.findElement(By.id("travellersInfo[" + passengerNumber + "].loyaltyMemberships[0]"));
        WebElement loyaltyNumber = driver.findElement(By.id("travellersInfo[" + passengerNumber + "].loyaltyNumbers[0]"));
        if (testData.getFrequentFlierProgram() == null) {
            log.warn("FrequentFlierProgram is null");
        } else {
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
    }

    public void populatePassportData(TestData testData, int passengerNumber) {
        WebElement country = driver.findElement(By.id("travellersInfo[" + passengerNumber + "].citizenCountry"));
        elementHelper.waitForElementDisplayed(country);
        new ElementHelper().selectOptionByValue(country, "US");

        WebElement passport = driver.findElement(By.id("travellersInfo[" + passengerNumber + "].advancedPassengerDetails(foid)"));
        elementHelper.waitForElementDisplayed(passport);
        new ElementHelper().selectOptionByValue(passport, "ID_CARD");

        WebElement foidNumber = driver.findElement(By.id("travellersInfo[" + passengerNumber + "].advancedPassengerDetails(foidNumber)"));
        elementHelper.waitForElementDisplayed(foidNumber);
        foidNumber.sendKeys("1234567890");

        WebElement foidExpireDay = driver.findElement(By.id("travellersInfo[" + passengerNumber + "].advancedPassengerDetails(foidExpireDay)"));
        elementHelper.waitForElementDisplayed(foidExpireDay);
        new ElementHelper().selectOptionByValue(foidExpireDay, "1");

        WebElement foidExpireMonth = driver.findElement(By.id("travellersInfo[" + passengerNumber + "].advancedPassengerDetails(foidExpireMonth)"));
        elementHelper.waitForElementDisplayed(foidExpireMonth);
        new ElementHelper().selectOptionByValue(foidExpireMonth, "1");

        WebElement foidExpireYear = driver.findElement(By.id("travellersInfo[" + passengerNumber + "].advancedPassengerDetails(foidExpireYear)"));
        elementHelper.waitForElementDisplayed(foidExpireYear);
        new ElementHelper().selectOptionByValue(foidExpireYear, "2022");

        WebElement issuingCountry = driver.findElement(By.id("travellersInfo[" + passengerNumber + "].advancedPassengerDetails(foidCountry)"));
        elementHelper.waitForElementDisplayed(issuingCountry);
        new ElementHelper().selectOptionByValue(issuingCountry, "US");
    }

    public void populatePassengerDetails(TestData testData, int passengerNumber) {
        Faker faker = new Faker();
        WebElement title = driver.findElement(By.id("travellersInfo[" + passengerNumber + "].title"));
        elementHelper.waitForElementDisplayed(title);
        new ElementHelper().selectOptionByText(title, "Mr");
        driver.findElement(By.id("travellersInfo[" + passengerNumber + "].firstName")).sendKeys(faker.name().firstName());
        driver.findElement(By.id("travellersInfo[" + passengerNumber + "].middleName")).sendKeys(faker.name().firstName());
        driver.findElement(By.id("travellersInfo[" + passengerNumber + "].lastName")).sendKeys(faker.name().lastName());
    }

    public void populatePassengerDoB(TestData testData, int passengerNumber) {
        WebElement calendarDialog = driver.findElement(By.id("travellersInfo[" + passengerNumber + "].advancedPassengerDetails(dobDate)"));
        calendarDialog.click();
        elementHelper.waitForElementPresent(calendarDialog);
        elementHelper.waitForElementPresent(year);
        new ElementHelper().selectOptionByValue(year, "1984");
        elementHelper.waitForElementPresent(month);
        new ElementHelper().selectOptionByValue(month, "12");
        driver.findElement(By.xpath("//td[. = \"12\"]")).click();
    }

    public void populateContactDetails(TestData testData) {
        Faker faker = new Faker(new Locale("{en-US}"));
        elementHelper.waitForElementDisplayed(travellerEmailAddress);
        travellerEmailAddress.sendKeys(testData.getEmail());
        elementHelper.waitForElementDisplayed(travellerConfirmEmail);
        travellerConfirmEmail.sendKeys(testData.getEmail());
        elementHelper.waitForElementDisplayed(travellerPhoneNumber);
        travellerPhoneNumber.sendKeys(faker.phoneNumber().cellPhone());
        new ElementHelper().selectOptionByValue(travellerCountryCode, "US");
    }

    public SeatsPage goToSeatSelect() {
        log.info("Passenger details filled. Going to Seat Selection page");
        elementHelper.waitForElementToBeClickable(buttonProceed);
        buttonProceed.click();
        return new SeatsPage(driver);
    }
}
