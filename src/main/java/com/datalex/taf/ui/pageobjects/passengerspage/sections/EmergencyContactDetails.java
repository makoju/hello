package com.datalex.taf.ui.pageobjects.passengerspage.sections;

import com.datalex.taf.ui.data.TestData;
import com.datalex.taf.ui.helpers.ElementHelper;
import com.github.javafaker.Faker;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/**
 * Fill Emergency Contact details section on Passenger Page
 */
public class EmergencyContactDetails {
    private WebDriver driver;
    private ElementHelper elementHelper;

    @FindBy(id = "emailAddress")
    public WebElement travellerEmailAddress;

    @FindBy(id = "confirmEmail")
    public WebElement travellerConfirmEmail;

    @FindBy(id = "mobilePhoneNumberCountryCode")
    public WebElement travellerCountryCode;

    @FindBy(id = "travellersInfo[0].mobilePhone.phoneNumber")
    public WebElement travellerPhoneNumber;

    public EmergencyContactDetails(WebDriver driver) {
        this.driver = driver;
        elementHelper = new ElementHelper(driver);
        PageFactory.initElements(driver, this);
    }

    public void fillTravellerEmergencyContactInformation(TestData testData) {
        Faker faker = new Faker();
        elementHelper.waitForElementDisplayed(travellerEmailAddress);
        travellerEmailAddress.sendKeys(testData.getEmail());
        elementHelper.waitForElementDisplayed(travellerConfirmEmail);
        travellerConfirmEmail.sendKeys(testData.getEmail());
        elementHelper.waitForElementDisplayed(travellerPhoneNumber);
        travellerPhoneNumber.sendKeys(faker.phoneNumber().phoneNumber());
        elementHelper.selectOptionByValue(travellerCountryCode, "US");
    }
}