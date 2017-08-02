package com.datalex.taf.ui.travellers;

import com.datalex.taf.ui.helpers.ElementHelper;
import com.datalex.taf.ui.helpers.General;
import com.datalex.taf.ui.helpers.Utils;
import lombok.ToString;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * Adult pax class
 *
 * @author Jossie Soul
 */
@ToString
@Log4j2
public class Adult extends Detail {
    private WebDriver driver;
    private ElementHelper elementHelper;
    private int paxNumber;

    /**
     * Simple Constructor
     *
     * @param driver WebDriver
     */
    public Adult(WebDriver driver) {
        this.driver = driver;
        elementHelper = new ElementHelper(driver);
    }

    /**
     * Constructor to Automatically Construct Adult Object with Data
     *
     * @param driver          WebDriver
     * @param passengerNumber int Number of passenger
     */
    public Adult(WebDriver driver, int passengerNumber) throws Exception {
        log.info("Filling Adult PAX Information " + (passengerNumber + 1));
        this.driver = driver;
        elementHelper = new ElementHelper(driver);
        this.paxNumber = passengerNumber;
        selectTitle(title);
        inputFirstName();
        inputMiddleName();
        inputLastName();
        if (checkDateOfBirthFormExist(false))
            fillDateOfBirthByCalendar(false);
        //APD
        selectCitizenship();
        selectFormOfID(foidType);
        inputFormOfIDNumber();
        inputFormOfIDExpiryDate();
        inputIssuingCountry();
        if (checkDateOfBirthFormExist(true))
            fillDateOfBirthByCalendar(true);

    }

    public void setPassengerNumber(int passengerNumber) {
        this.paxNumber = passengerNumber;
    }

    public void selectTitle(String titleSel) {
        WebElement title = driver.findElement(By.id("travellersInfo[" + this.paxNumber + "].title"));
        elementHelper.waitForElementDisplayedIterative(title);
        elementHelper.selectOptionByValue(title, titleSel);
    }

    public void inputFirstName() {
        driver.findElement(By.id("travellersInfo[" + this.paxNumber + "].firstName")).sendKeys(firstName);
    }

    public void inputMiddleName() {
        driver.findElement(By.id("travellersInfo[" + this.paxNumber + "].middleName")).sendKeys(middleName);
    }

    public void inputLastName() {
        driver.findElement(By.id("travellersInfo[" + this.paxNumber + "].lastName")).sendKeys(lastName);
    }

    public void selectCitizenship() {
        WebElement citizenSelectionElement = driver.findElement(By.id("travellersInfo[" + this.paxNumber + "].citizenCountry"));
        elementHelper.waitForElementDisplayedIterative(citizenSelectionElement);
        elementHelper.selectOptionByValue(citizenSelectionElement, citizenship);
        new Utils().waitTime(1500);
    }

    public void selectFormOfID(String foidType) {
        WebElement foidTypeElement = driver.findElement(By.id("travellersInfo[" + this.paxNumber + "].advancedPassengerDetails(foid)"));
        elementHelper.waitForElementDisplayedIterative(foidTypeElement);
        elementHelper.selectOptionByValue(foidTypeElement, foidType);
    }

    public void inputFormOfIDNumber() {
        WebElement foidNumberElement = driver.findElement(By.id("travellersInfo[" + this.paxNumber + "].advancedPassengerDetails(foidNumber)"));
        elementHelper.waitForElementDisplayed(foidNumberElement);
        foidNumberElement.sendKeys(foidNumber);
    }

    public void inputFormOfIDExpiryDate() {
        WebElement foidExpireDayElement = driver.findElement(By.id("travellersInfo[" + this.paxNumber + "].advancedPassengerDetails(foidExpireDay)"));
        elementHelper.waitForElementDisplayedIterative(foidExpireDayElement);
        elementHelper.selectOptionByValue(foidExpireDayElement, "1");

        WebElement foidExpireMonthElement = driver.findElement(By.id("travellersInfo[" + this.paxNumber + "].advancedPassengerDetails(foidExpireMonth)"));
        elementHelper.waitForElementDisplayedIterative(foidExpireMonthElement);
        elementHelper.selectOptionByValue(foidExpireMonthElement, "1");

        WebElement foidExpireYearElement = driver.findElement(By.id("travellersInfo[" + this.paxNumber + "].advancedPassengerDetails(foidExpireYear)"));
        elementHelper.waitForElementDisplayedIterative(foidExpireYearElement);
        elementHelper.selectOptionByValue(foidExpireYearElement, "2027");
    }

    public void inputIssuingCountry() {
        WebElement issuingCountryElement = driver.findElement(By.id("travellersInfo[" + this.paxNumber + "].advancedPassengerDetails(foidCountry)"));
        elementHelper.waitForElementDisplayedIterative(issuingCountryElement);
        elementHelper.selectOptionByValue(issuingCountryElement, issuingCountry);
    }

    /**
     * Check if Date of Birth Calendar is displayed
     *
     * @param isAPD check if this is a Advance Passenger Detail
     * @return boolean
     */
    public boolean checkDateOfBirthFormExist(boolean isAPD) {
        return elementHelper.isElementDisplayed(retrieveCorrectDoBLocator(isAPD));
    }

    public WebElement retrieveCorrectDoBLocator(boolean isAPD) {
        WebElement dob;
        if (isAPD) {
            dob = driver.findElement(By.id("travellersInfo[" + this.paxNumber + "].advancedPassengerDetails(dobDate).calendarIcon"));
        } else {
            dob = driver.findElement(By.id("travellersInfo[" + this.paxNumber + "].travellerBirthDate.calendarIcon"));
        }
        return dob;
    }

    public void fillDateOfBirthByCalendar(boolean isAPD) throws Exception {
        new General().handleDatePickerCalender(driver, retrieveCorrectDoBLocator(isAPD), renderYearFromToday("ADT"));
    }
}