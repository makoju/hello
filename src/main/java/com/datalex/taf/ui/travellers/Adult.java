package com.datalex.taf.ui.travellers;

import com.datalex.taf.ui.helpers.ElementHelper;
import com.datalex.taf.ui.helpers.General;
import com.datalex.taf.ui.helpers.Utils;
import lombok.ToString;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import javax.rmi.CORBA.Util;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by jossie.saul on 27/07/2017.
 */
@ToString
@Log4j2
public class Adult extends Detail {
    private WebDriver driver;
    private int paxNumber;

    private ElementHelper eh;

    /**
     * Simple Constructor
     * @param driver
     */
    public Adult(WebDriver driver){
        this.driver = driver;
        eh = new ElementHelper(driver);
    }

    /**
     * Constructor to Automatically Construct Adult Object with Data
     *
     * @param driver
     * @param passengerNumber
     */
    public Adult(WebDriver driver, int passengerNumber) throws Exception {
            log.info("Filling Adult PAX Information " + (passengerNumber+1));
            this.driver = driver;
            eh = new ElementHelper(driver);
            paxNumber = passengerNumber;
            selectTitle(title);
            inputFirstName();
            inputMiddleName();
            inputLastName();
            if(checkDateOfBirthFormExist(false)) fillDateOfBirthByCalendar(false);
            //APD
            selectCitizenship();
            selectFormOfID(foidType);
            inputFormOfIDNumber();
            inputFormOfIDExpiryDate();
            inputIssuingCountry();
            if(checkDateOfBirthFormExist(true)) fillDateOfBirthByCalendar(true);

    }

    public void setPassengerNumber(int passengerNumber){
        paxNumber = passengerNumber;
    }

    public void selectTitle(String titleSel){
        WebElement title = driver.findElement(By.id("travellersInfo["+ paxNumber +"].title"));
        eh.waitForElementDisplayed(title);
        eh.selectOptionByValue(title, titleSel);
    }

    public void inputFirstName(){
        driver.findElement(By.id("travellersInfo[" + paxNumber + "].firstName")).sendKeys(firstName);
    }

    public void inputMiddleName(){
        driver.findElement(By.id("travellersInfo[" + paxNumber + "].middleName")).sendKeys(middleName);
    }

    public void inputLastName(){
        driver.findElement(By.id("travellersInfo[" + paxNumber + "].lastName")).sendKeys(lastName);
    }

    public void selectCitizenship(){
        WebElement citizenSelectionElement = driver.findElement(By.id("travellersInfo[" + paxNumber + "].citizenCountry"));
        eh.waitForElementDisplayed(citizenSelectionElement);
        eh.selectOptionByValue(citizenSelectionElement, citizenship);
        new Utils().waitTime(1500);
    }

    public void selectFormOfID(String foidType){
        WebElement foidTypeElement = driver.findElement(By.id("travellersInfo[" + paxNumber + "].advancedPassengerDetails(foid)"));
        eh.waitForElementDisplayed(foidTypeElement);
        eh.selectOptionByValue(foidTypeElement, foidType);
    }

    public void inputFormOfIDNumber(){
        WebElement foidNumberElement = driver.findElement(By.id("travellersInfo[" + paxNumber + "].advancedPassengerDetails(foidNumber)"));
        eh.waitForElementDisplayed(foidNumberElement);
        foidNumberElement.sendKeys(foidNumber);
    }

    public void inputFormOfIDExpiryDate(){
        WebElement foidExpireDayElement = driver.findElement(By.id("travellersInfo[" + paxNumber + "].advancedPassengerDetails(foidExpireDay)"));
        eh.waitForElementDisplayed(foidExpireDayElement);
        eh.selectOptionByValue(foidExpireDayElement, "1");

        WebElement foidExpireMonthElement = driver.findElement(By.id("travellersInfo[" + paxNumber + "].advancedPassengerDetails(foidExpireMonth)"));
        eh.waitForElementDisplayed(foidExpireMonthElement);
        eh.selectOptionByValue(foidExpireMonthElement, "1");

        WebElement foidExpireYearElement = driver.findElement(By.id("travellersInfo[" + paxNumber + "].advancedPassengerDetails(foidExpireYear)"));
        eh.waitForElementDisplayed(foidExpireYearElement);
        eh.selectOptionByValue(foidExpireYearElement, "2027");
    }

    public void inputIssuingCountry(){
        WebElement issuingCountryElement = driver.findElement(By.id("travellersInfo[" + paxNumber + "].advancedPassengerDetails(foidCountry)"));
        eh.waitForElementDisplayed(issuingCountryElement);
        eh.selectOptionByValue(issuingCountryElement, issuingCountry);
    }

    /**
     * Check if Date of Birth Calendar is displayed
     *
     * @param isAPD check if this is a Advance Passenger Detail
     *
     * @return boolean
     */
    public boolean checkDateOfBirthFormExist(boolean isAPD){
        return eh.isElementDisplayed(retrieveCorrectDoBLocator(isAPD));
    }

    public WebElement retrieveCorrectDoBLocator(boolean isAPD){
        WebElement dob;
        if(isAPD) {
            dob = driver.findElement(By.id("travellersInfo["+paxNumber+"].advancedPassengerDetails(dobDate).calendarIcon"));
        }else{
            dob = driver.findElement(By.id("travellersInfo["+paxNumber+"].travellerBirthDate.calendarIcon"));
        }
        return dob;
    }

    public void fillDateOfBirthByCalendar(boolean isAPD) throws Exception {
        new General().handleDatePickerCalender(driver, retrieveCorrectDoBLocator(isAPD), renderYearFromToday("ADT"));
    }


}
