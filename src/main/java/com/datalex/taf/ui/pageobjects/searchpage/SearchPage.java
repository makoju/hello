package com.datalex.taf.ui.pageobjects.searchpage;

import com.datalex.taf.core.readers.property.TAFProperties;
import com.datalex.taf.ui.data.TestData;
import com.datalex.taf.ui.helpers.ElementHelper;
import com.datalex.taf.ui.helpers.General;
import com.datalex.taf.ui.pageobjects.exceptions.SearchPageException;
import com.datalex.taf.ui.pageobjects.loginpage.LoginPage;
import com.datalex.taf.ui.pageobjects.selectionpage.SelectionPage;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/**
 * SearchPage Class
 *
 * @author Aleksandar Vulovic
 */
@Log4j2
public class SearchPage implements ISearchPage {
    private WebDriver driver;
    private ElementHelper elementHelper;

    @FindBy(id = "loginLinkFromLoginBlock")
    public WebElement loginButton;

    @FindBy(id = "couponLinkBlock")
    public WebElement promotionLink;

    @FindBy(css = "input#coupon")
    public WebElement promotionInput;

    @FindBy(css = "input#flexibleSearch2")
    public WebElement fixedDates;

    @FindBy(css = "input#flexibleSearch1")
    public WebElement flexibleDates;

    @FindBy(id = "cabinClass")
    public WebElement cabinClass;

    @FindBy(id = "guestTypes[0].amount")
    public WebElement adultNo;

    @FindBy(id = "guestTypes[1].amount")
    public WebElement childNo;

    @FindBy(id = "guestTypes[2].amount")
    public WebElement infantNo;

    @FindBy(id = "outboundOption.originLocationName")
    public WebElement inputFrom;

    @FindBy(id = "outboundOption.destinationLocationName")
    public WebElement inputTo;

    @FindBy(id = "outboundOption.originLocationCode:outboundOption.originLocationName")
    public WebElement inputFromHidden;

    @FindBy(id = "outboundOption.destinationLocationCode:outboundOption.destinationLocationName")
    public WebElement inputToCodeHidden;

    @FindBy(id = "departureDate1")
    public WebElement inputDepartOn;

    @FindBy(id = "departureDate2")
    public WebElement inputReturnOn;

    @FindBy(id = "tripTypeOW")
    public WebElement tripTypeOW;

    @FindBy(id = "tripTypeMC")
    public WebElement tripTypeMC;

    @FindBy(id = "tripTypeRT")
    public WebElement tripTypeRT;

    @FindBy(id = "redemptionControl")
    public WebElement loopPrice;

    @FindBy(xpath = "//table[@class='botButton1 botButtonSearch']")
    public WebElement buttonSearch;

    @FindBy(id = "multiCityOptions[0].originLocationName")
    public WebElement inputFromMC1;

    @FindBy(id = "multiCityOptions[0].originLocationCode:multiCityOptions[0].originLocationName")
    public WebElement inputFromMC1CodeHidden;

    @FindBy(id = "multiCityOptions[0].destinationLocationName")
    public WebElement inputToMC1;

    @FindBy(id = "multiCityOptions[0].destinationLocationCode:multiCityOptions[0].destinationLocationName")
    public WebElement inputToMC1CodeHidden;

    @FindBy(id = "multiCityOptions[1].originLocationName")
    public WebElement inputFromMC2;

    @FindBy(id = "multiCityOptions[1].originLocationCode:multiCityOptions[1].originLocationName")
    public WebElement inputFromMC2CodeHidden;

    @FindBy(id = "multiCityOptions[1].destinationLocationName")
    public WebElement inputToMC2;

    @FindBy(id = "multiCityOptions[1].destinationLocationCode:multiCityOptions[1].destinationLocationName")
    public WebElement inputToMC2CodeHidden;

    @FindBy(id = "multiCityOptions[2].originLocationName")
    public WebElement inputFromMC3;

    @FindBy(id = "multiCityOptions[2].originLocationCode:multiCityOptions[2].originLocationName")
    public WebElement inputFromMC3CodeHidden;

    @FindBy(id = "multiCityOptions[2].destinationLocationName")
    public WebElement inputToMC3;

    @FindBy(id = "multiCityOptions[2].destinationLocationCode:multiCityOptions[2].destinationLocationName")
    public WebElement inputToMC3CodeHidden;

    public SearchPage(WebDriver driver) {
        log.info("Initiating Flight Search Page");
        this.driver = driver;
        elementHelper = new ElementHelper(driver);
        PageFactory.initElements(driver, this);
        driver.get(TAFProperties.getPROJECTIP() + "/BEL/ApplicationStartAction.do?" + TAFProperties.getPOS());
    }

    public void setOriginLocation(String code) throws SearchPageException {
        log.info("Setting Origin Location");
        try {
            new General().typeFlight(driver, inputFromHidden, inputFrom, code, "");
        } catch (Exception e) {
            log.error(e);
        }
    }

    public void setDestinationLocation(String code) throws SearchPageException {
        log.info("Setting Destination Location");
        try {
            new General().typeFlight(driver, inputToCodeHidden, inputTo, code, "");
        } catch (Exception e) {
            log.error(e);
        }
    }

    public void inputDepartureDate(String daysFromToday) throws Exception {
        log.info("Selecting Departure Date");
        new General().inputDateByCalendar(driver, inputDepartOn, daysFromToday);
    }

    public void inputReturnDate(String daysFromToday) throws Exception {
        log.info("Selecting Return Date");
        new General().inputDateByCalendar(driver, inputReturnOn, daysFromToday);
    }

    public void inputDepartureDateMC(String daysFromToday, int flightNumber) throws Exception {
        log.info("Selecting Departure Date for MC");
        int flightNumberLocator = flightNumber - 1;
        WebElement mcDepartOn = driver.findElement(By.name("multiCityOptions[" + flightNumberLocator + "].departureDate"));
        new General().inputDateByCalendar(driver, mcDepartOn, daysFromToday);
    }

    public void chooseLoopPrice(TestData testData) {
        log.info("Choosing Loop Price");
        if ("Signed in".equalsIgnoreCase(testData.getLoopProfile()) && ("Loops".equalsIgnoreCase(testData.getFrequentFlierProgram()))) {
            loopPrice.click();
        }
    }

    public void setMC(TestData testData) throws Exception {
        log.info("Trip is OpenJaw");
        tripTypeMC.click();
        if (!(testData.getFromMC1().isEmpty() || "null".equals(testData.getFromMC1()))) {
            new General().typeFlight(driver, inputFromMC1CodeHidden, inputFromMC1, testData.getFromMC1(), "");
            new General().typeFlight(driver, inputToMC1CodeHidden, inputToMC1, testData.getToMC1(), "");
            inputDepartureDateMC(testData.getDepartOn(), 1);
        }
        if (!(testData.getFromMC2().isEmpty() || "null".equals(testData.getFromMC2()))) {
            new General().typeFlight(driver, inputFromMC2CodeHidden, inputFromMC2, testData.getFromMC2(), "");
            new General().typeFlight(driver, inputToMC2CodeHidden, inputToMC2, testData.getToMC2(), "");
            inputDepartureDateMC(testData.getReturnOn(), 2);
        }
        if (!(testData.getFromMC3().isEmpty() || "null".equals(testData.getFromMC3()))) {
            new General().typeFlight(driver, inputFromMC3CodeHidden, inputFromMC3, testData.getFromMC3(), "");
            new General().typeFlight(driver, inputToMC3CodeHidden, inputToMC3, testData.getToMC3(), "");
            inputDepartureDateMC(testData.getDepartOnMC3(), 3);
        }
    }

    public void setRT(TestData testData) throws Exception {
        log.info("Trip is Return Flight");
        tripTypeRT.click();
        setOriginLocation(testData.getInputFrom());
        setDestinationLocation(testData.getInputTo());
        inputDepartureDate(testData.getDepartOn());
        inputReturnDate(testData.getReturnOn());
    }

    public void setOW(TestData testData) throws Exception {
        log.info("Trip is Oneway Flight");
        tripTypeOW.click();
        setOriginLocation(testData.getInputFrom());
        setDestinationLocation(testData.getInputTo());
        inputDepartureDate(testData.getDepartOn());
    }

    public void setPassengers(TestData testData) {
        log.info("Selecting PAX");
        elementHelper.selectOptionByText(adultNo, testData.getAdt());
        elementHelper.selectOptionByText(childNo, testData.getChd());
        elementHelper.selectOptionByText(infantNo, testData.getInf());
    }

    public void setFlightSearchTypes(TestData testData) {
        if (!"MC".equalsIgnoreCase(testData.getTripType())) {
            log.info("Setting Flight Search Type");
            if ("flexibleDates".equals(testData.getFlightDates())) {
                flexibleDates.click();
            }
            if ("fixedDates".equals(testData.getFlightDates())) {
                fixedDates.click();
            }
        }
    }

    public void setPromotion(TestData testData) {
        log.info("Setting Promotion Code");
        if (testData.getPromotion() != null || !"MC".equalsIgnoreCase(testData.getTripType())) {
            elementHelper.waitForElementToBeClickable(promotionLink);
            promotionLink.click();
            elementHelper.waitForElementToBeClickable(promotionInput);
            promotionInput.sendKeys(testData.getPromotion().trim());
        }
    }

    public LoginPage goToLoginPage() {
        log.info("Going Into Login Page");
        elementHelper.waitForElementToBeClickable(loginButton);
        loginButton.click();
        elementHelper.waitForFrameToBeAvailableAndSwitchToIt("ifrmLogin");
        return new LoginPage(driver);
    }

    public SelectionPage doSearch() {
        log.info("Search Interaction Complete. Searching flight now...");
        elementHelper.waitForElementToBeClickable(buttonSearch);
        buttonSearch.click();
        return new SelectionPage(driver);
    }

    public SelectionPage doFlightSearch(TestData testData) throws Exception {
        log.info("Initiating Flight Search Process.");
        switch (testData.getTripType().trim()) {
            case "OW":
                setOW(testData);
                break;
            case "RT":
                setRT(testData);
                break;
            case "MC":
                setMC(testData);
                break;
            default:
                throw new SearchPageException("TripType not specified!");
        }
        setFlightSearchTypes(testData);
        setPassengers(testData);
        setPromotion(testData);
        elementHelper.selectOptionByText(cabinClass, testData.getCabinClass().trim());
        chooseLoopPrice(testData);
        return doSearch();
    }
}