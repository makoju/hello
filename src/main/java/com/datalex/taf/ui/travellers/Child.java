package com.datalex.taf.ui.travellers;

import com.datalex.taf.ui.helpers.ElementHelper;
import com.datalex.taf.ui.helpers.General;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * Child pax class
 *
 * @author Jossie Soul
 */
@Log4j2
public class Child extends Detail {
    private WebDriver driver;
    private ElementHelper elementHelper;
    private int paxNumber;

    /**
     * Simple Constructor
     *
     * @param driver WebDriver
     */
    public Child(WebDriver driver) {
        this.driver = driver;
        elementHelper = new ElementHelper(driver);
    }

    /**
     * Constructor to Automatically Construct Child Object with Data
     * @param driver WebDriver
     * @param passengerNumber int pax number
     * @throws Exception if error occurs
     */
    public Child(WebDriver driver, int passengerNumber) throws Exception {
        log.info("Filling Child PAX Information " + (passengerNumber + 1));
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

    /**
     * Set passenger number for current passenger
     *
     * @param passengerNumber int pax number
     */
    public void setPassengerNumber(int passengerNumber) {
        this.paxNumber = passengerNumber;
    }

    /**
     * Select pax title on passenger page
     *
     * @param titleSel title to select
     */
    public void selectTitle(String titleSel) {
        WebElement title = driver.findElement(By.id("travellersInfo[" + this.paxNumber + "].title"));
        elementHelper.waitForElementDisplayed(title);
        elementHelper.selectOptionByValue(title, titleSel);
    }

    /**
     * Input first name on passenger page
     */
    public void inputFirstName() {
        driver.findElement(By.id("travellersInfo[" + this.paxNumber + "].firstName")).sendKeys(firstName);
    }

    /**
     * Input middle name on passenger page
     */
    public void inputMiddleName() {
        driver.findElement(By.id("travellersInfo[" + this.paxNumber + "].middleName")).sendKeys(middleName);
    }

    /**
     * Input last name on passenger page
     */
    public void inputLastName() {
        driver.findElement(By.id("travellersInfo[" + this.paxNumber + "].lastName")).sendKeys(lastName);
    }

    /**
     * Select citizenship on passenger page
     */
    public void selectCitizenship() {
        WebElement citizenSelectionElement = driver.findElement(By.id("travellersInfo[" + this.paxNumber + "].citizenCountry"));
        elementHelper.waitForElementDisplayed(citizenSelectionElement);
        elementHelper.selectOptionByValue(citizenSelectionElement, citizenship);
    }

    /**
     * Select form of id on passenger page
     *
     * @param foidType type of foid
     */
    public void selectFormOfID(String foidType) {
        WebElement foidTypeElement = driver.findElement(By.id("travellersInfo[" + this.paxNumber + "].advancedPassengerDetails(foid)"));
        elementHelper.waitForElementDisplayed(foidTypeElement);
        elementHelper.selectOptionByValue(foidTypeElement, foidType);
    }

    /**
     * Input form of id number to passenger page
     */
    public void inputFormOfIDNumber() {
        WebElement foidNumberElement = driver.findElement(By.id("travellersInfo[" + this.paxNumber + "].advancedPassengerDetails(foidNumber)"));
        elementHelper.waitForElementDisplayed(foidNumberElement);
        foidNumberElement.sendKeys(foidNumber);
    }

    /**
     * Input form of id expiry date
     */
    public void inputFormOfIDExpiryDate() {
        WebElement foidExpireDayElement = driver.findElement(By.id("travellersInfo[" + this.paxNumber + "].advancedPassengerDetails(foidExpireDay)"));
        elementHelper.waitForElementDisplayed(foidExpireDayElement);
        elementHelper.selectOptionByValue(foidExpireDayElement, "1");

        WebElement foidExpireMonthElement = driver.findElement(By.id("travellersInfo[" + this.paxNumber + "].advancedPassengerDetails(foidExpireMonth)"));
        elementHelper.waitForElementDisplayed(foidExpireMonthElement);
        elementHelper.selectOptionByValue(foidExpireMonthElement, "1");

        WebElement foidExpireYearElement = driver.findElement(By.id("travellersInfo[" + this.paxNumber + "].advancedPassengerDetails(foidExpireYear)"));
        elementHelper.waitForElementDisplayed(foidExpireYearElement);
        elementHelper.selectOptionByValue(foidExpireYearElement, "2027");
    }

    /**
     * Input issuing country
     */
    public void inputIssuingCountry() {
        WebElement issuingCountryElement = driver.findElement(By.id("travellersInfo[" + this.paxNumber + "].advancedPassengerDetails(foidCountry)"));
        elementHelper.waitForElementDisplayed(issuingCountryElement);
        elementHelper.selectOptionByValue(issuingCountryElement, issuingCountry);
    }

    /**
     * Check if Date of Birth Calendar is displayed
     *
     * @param isAPD check if this is a Advance Passenger Detail
     * @return boolean
     */
    public boolean checkDateOfBirthFormExist(boolean isAPD) {
        String dobEl = isAPD ? "travellersInfo[" + this.paxNumber + "].advancedPassengerDetails(dobDate).calendarIcon" : "travellersInfo[" + paxNumber + "].travellerBirthDate.calendarIcon";
        return driver.findElements(By.id(dobEl)).size() > 0;
    }

    /**
     * Get correct date of birth locator
     *
     * @param isAPD boolean
     * @return WebElement
     */
    public WebElement retrieveCorrectDoBLocator(boolean isAPD) {
        WebElement dob;
        if (isAPD) {
            dob = driver.findElement(By.id("travellersInfo[" + this.paxNumber + "].advancedPassengerDetails(dobDate).calendarIcon"));
        } else {
            dob = driver.findElement(By.id("travellersInfo[" + this.paxNumber + "].travellerBirthDate.calendarIcon"));
        }
        return dob;
    }

    /**
     * Fill date of birth by calendar on passenger page
     *
     * @param isAPD boolean
     * @throws Exception if error occurs
     */
    public void fillDateOfBirthByCalendar(boolean isAPD) throws Exception {
        new General().handleDatePickerCalender(driver, retrieveCorrectDoBLocator(isAPD), renderYearFromToday("CHD"));
    }
}