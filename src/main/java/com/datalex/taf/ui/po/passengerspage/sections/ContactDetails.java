package com.datalex.taf.ui.po.passengerspage.sections;

import com.datalex.taf.ui.data.TestData;
import com.datalex.taf.ui.helpers.ElementHelper;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/**
 * Fill Contact details section on Passenger Page
 */
public class ContactDetails {
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

    public ContactDetails(WebDriver driver) {
        this.driver = driver;
        elementHelper = new ElementHelper();
        PageFactory.initElements(driver, this);
    }

    public void populateContactDetails(TestData testData) {
        elementHelper.waitForElementDisplayed(travellerEmailAddress);
        travellerEmailAddress.sendKeys(testData.getEmail());
        elementHelper.waitForElementDisplayed(travellerConfirmEmail);
        travellerConfirmEmail.sendKeys(testData.getEmail());
        elementHelper.waitForElementDisplayed(travellerPhoneNumber);
        travellerPhoneNumber.sendKeys("4564564564");
        elementHelper.selectOptionByValue(travellerCountryCode, "US");
    }
}