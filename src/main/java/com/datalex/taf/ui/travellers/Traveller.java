package com.datalex.taf.ui.travellers;

import com.datalex.taf.ui.data.TestData;
import com.datalex.taf.ui.helpers.ElementHelper;
import com.github.javafaker.Faker;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * Created by jossie.saul on 27/07/2017.
 */
@Log4j2
public class Traveller {
    private WebDriver driver;

    private Adult adt;

    private Child chd;

    private Infant inf;

    @FindBy(id = "emailAddress")
    public WebElement travellerEmailAddress;

    @FindBy(id = "confirmEmail")
    public WebElement travellerConfirmEmail;

    @FindBy(id = "mobilePhoneNumberCountryCode")
    public WebElement travellerCountryCode;

    @FindBy(id = "travellersInfo[0].mobilePhone.phoneNumber")
    public WebElement travellerPhoneNumber;

    public ElementHelper eh;

    public Traveller(WebDriver driver){

        this.driver = driver;
        eh = new ElementHelper(driver);
    }

    public void fillTravellerInformation(String paxType, int passengerNumber) throws Exception {
        switch (paxType){
            case "ADT":
                adt = new Adult(driver, passengerNumber);
                break;
            case "CHD":
                chd = new Child(driver, passengerNumber);
                break;
            case "INF":
                inf = new Infant(driver, passengerNumber);
                break;
            default:
                log.error("PaxType is Empty or not Recognized. PaxType{"+paxType+"}");
                throw new Exception();
        }
    }

    public void fillTravellerEmergencyContactInformation(TestData testData) throws Exception{
        Faker faker = new Faker();
        eh.waitForElementDisplayed(travellerEmailAddress);
        travellerEmailAddress.sendKeys(testData.getEmail());
        eh.waitForElementDisplayed(travellerConfirmEmail);
        travellerConfirmEmail.sendKeys(testData.getEmail());
        eh.waitForElementDisplayed(travellerPhoneNumber);
        travellerPhoneNumber.sendKeys(faker.phoneNumber().phoneNumber());
        eh.selectOptionByValue(travellerCountryCode, "US");
    }

    public String determinePassengerType(int passengerNumber){
        log.debug("Traveller Type is: "+ driver.findElement(By.xpath("//input[@id='travellersInfo["+passengerNumber+"].travellerType']")).getAttribute("value"));
        return driver.findElement(By.xpath("//input[@id='travellersInfo["+passengerNumber+"].travellerType']")).getAttribute("value");
    }

    /**
     * Smart determining by Page Element
     *
     * @return
     */
    public int getHowManyPassengerSelected(){
        log.debug("Number of Passengers" + driver.findElements(By.xpath("//div[contains(@class,'passengerBlock')]")).size());
        return driver.findElements(By.xpath("//div[contains(@class,'passengerBlock')]")).size();
    }


}
