package com.datalex.taf.ui.po.passengerspage;

import com.datalex.taf.core.loggers.TAFLogger;
import com.datalex.taf.ui.data.TestData;
import com.datalex.taf.ui.helpers.ElementHelper;
import com.github.javafaker.Faker;
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
public class PassengersPage implements IPassengersPage {
    private WebDriver driver;

    @FindBy(id = "pgButtonProceed")
    public WebElement buttonProceed;

    @FindBy(id = "emailAddress")
    public WebElement travellerEmailAddress;

    @FindBy(id = "confirmEmail")
    public WebElement travellerConfirmEmail;

    @FindBy(id = "mobilePhoneNumberCountryCode")
    public WebElement travellerCountryCode;

    @FindBy(id = "travellersInfo[0].mobilePhone.phoneNumber")
    public WebElement travellerPhoneNumber;

    public PassengersPage(WebDriver driver) {
        this.driver = driver;
        new ElementHelper(driver).waitForPresenceOfElementLocated(By.id("pgTravellers"));
        new ElementHelper(driver).waitForElementToBeClickable(By.id("pgButtonProceed"));
        PageFactory.initElements(driver, this);
    }

    public void fillTravellersPage(TestData testData) {
        int adtQty = Integer.parseInt(testData.getAdt());
        int chdQty = Integer.parseInt(testData.getChd());
        int infQty = Integer.parseInt(testData.getInf());

        int travellersQty = adtQty + chdQty + infQty;
        new ElementHelper().zoomOutPage(driver);
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

    public void populatePassportData(TestData testData, int passengerNumber) {
        WebElement country = driver.findElement(By.id("travellersInfo[" + passengerNumber + "].citizenCountry"));
        new ElementHelper(driver).waitForElementDisplayed(country);
        new ElementHelper().selectOptionByValue(country, "US");

        WebElement passport = driver.findElement(By.id("travellersInfo[" + passengerNumber + "].advancedPassengerDetails(foid)"));
        new ElementHelper(driver).waitForElementDisplayed(passport);
        new ElementHelper().selectOptionByValue(passport, "ID_CARD");

        WebElement foidNumber = driver.findElement(By.id("travellersInfo[" + passengerNumber + "].advancedPassengerDetails(foidNumber)"));
        new ElementHelper(driver).waitForElementDisplayed(foidNumber);
        foidNumber.sendKeys("1234567890");

        WebElement foidExpireDay = driver.findElement(By.id("travellersInfo[" + passengerNumber + "].advancedPassengerDetails(foidExpireDay)"));
        new ElementHelper(driver).waitForElementDisplayed(foidExpireDay);
        new ElementHelper().selectOptionByValue(foidExpireDay, "1");

        WebElement foidExpireMonth = driver.findElement(By.id("travellersInfo[" + passengerNumber + "].advancedPassengerDetails(foidExpireMonth)"));
        new ElementHelper(driver).waitForElementDisplayed(foidExpireMonth);
        new ElementHelper().selectOptionByValue(foidExpireMonth, "1");

        WebElement foidExpireYear = driver.findElement(By.id("travellersInfo[" + passengerNumber + "].advancedPassengerDetails(foidExpireYear)"));
        new ElementHelper(driver).waitForElementDisplayed(foidExpireYear);
        new ElementHelper().selectOptionByValue(foidExpireYear, "2022");

        WebElement issuingCountry = driver.findElement(By.id("travellersInfo[" + passengerNumber + "].advancedPassengerDetails(foidCountry)"));
        new ElementHelper(driver).waitForElementDisplayed(issuingCountry);
        new ElementHelper().selectOptionByValue(issuingCountry, "US");
    }

    public void populatePassengerDetails(TestData testData, int passengerNumber) {
        Faker faker = new Faker();
        WebElement title = driver.findElement(By.id("travellersInfo[" + passengerNumber + "].title"));
        new ElementHelper(driver).waitForElementDisplayed(title);
        new ElementHelper().selectOptionByText(title, "Mr");
        driver.findElement(By.id("travellersInfo[" + passengerNumber + "].firstName")).sendKeys(faker.name().firstName());
        driver.findElement(By.id("travellersInfo[" + passengerNumber + "].middleName")).sendKeys(faker.name().firstName());
        driver.findElement(By.id("travellersInfo[" + passengerNumber + "].lastName")).sendKeys(faker.name().lastName());
    }

    public void populatePassengerDoB(TestData testData, int passengerNumber) {
        WebElement calendarDialog = driver.findElement(By.id("travellersInfo[" + passengerNumber + "].advancedPassengerDetails(dobDate)"));
        calendarDialog.click();
        new ElementHelper(driver).waitForElementPresent(calendarDialog);
        new ElementHelper().executeScript(driver, "document.getElementById('travellersInfo[" + passengerNumber + "].advancedPassengerDetails(dobDay)').setAttribute('value', '12')");
        new ElementHelper().executeScript(driver, "document.getElementById('travellersInfo[" + passengerNumber + "].advancedPassengerDetails(dobMonth)').setAttribute('value', '12')");
        new ElementHelper().executeScript(driver, "document.getElementById('travellersInfo[" + passengerNumber + "].advancedPassengerDetails(dobYear)').setAttribute('value', '1984')");
        driver.findElement(By.xpath("//td[. = \"12\"]")).click();
    }

    public void populateContactDetails(TestData testData) {
        Faker faker = new Faker();
        new ElementHelper(driver).waitForElementDisplayed(travellerEmailAddress);
        travellerEmailAddress.sendKeys(testData.getEmail());
        new ElementHelper(driver).waitForElementDisplayed(travellerConfirmEmail);
        travellerConfirmEmail.sendKeys(testData.getEmail());
        new ElementHelper(driver).waitForElementDisplayed(travellerPhoneNumber);
        travellerPhoneNumber.sendKeys(faker.phoneNumber().phoneNumber());
        new ElementHelper().selectOptionByValue(travellerCountryCode, "US");
    }

    public void goToPayment() {
        new ElementHelper(driver).waitForElementToBeClickable(buttonProceed);
        buttonProceed.click();
    }


}
