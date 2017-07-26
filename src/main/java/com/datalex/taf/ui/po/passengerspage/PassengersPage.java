package com.datalex.taf.ui.po.passengerspage;

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

    @FindBy(id = "travellersInfo[0].emailAddress")
    public WebElement travellerEmailAddress;

    @FindBy(id = "travellersInfo[0].confirmEmail")
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
        for (int i = 0; i <= travellersQty - 1; i++) {
            Faker faker = new Faker();
            WebElement title = driver.findElement(By.id("travellersInfo[" + i + "].title"));
            new ElementHelper().selectOptionByText(title, "Mr");
            driver.findElement(By.id("travellersInfo[" + i + "].firstName")).sendKeys(faker.name().firstName());
            driver.findElement(By.id("travellersInfo[" + i + "].middleName")).sendKeys(faker.name().firstName());
            driver.findElement(By.id("travellersInfo[" + i + "].lastName")).sendKeys(faker.name().lastName());

            WebElement country = driver.findElement(By.id("travellersInfo[" + i + "].citizenCountry"));
            new ElementHelper().selectOptionByText(country, "Serbia");

            WebElement loyalty = driver.findElement(By.id("travellersInfo[" + i + "].loyaltyMemberships[0]"));
            new ElementHelper().selectOptionByText(loyalty, testData.getFrequentFlierProgram());
        }
        Faker faker = new Faker();
        travellerEmailAddress.sendKeys(testData.getEmail());
        travellerConfirmEmail.sendKeys(testData.getEmail());
        travellerPhoneNumber.sendKeys(faker.phoneNumber().phoneNumber());
        new ElementHelper().selectOptionByValue(travellerCountryCode, "AO");
    }


}
