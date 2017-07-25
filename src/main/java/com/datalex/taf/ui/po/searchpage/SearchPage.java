package com.datalex.taf.ui.po.searchpage;

import com.datalex.taf.core.readers.property.TAFProperties;
import com.datalex.taf.ui.data.TestData;
import com.datalex.taf.ui.helpers.ElementHelper;
import com.datalex.taf.ui.helpers.General;
import com.datalex.taf.ui.po.loginpage.LoginPage;
import com.datalex.taf.ui.po.selectionpage.SelectionPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/**
 * SearchPage Class
 */
public class SearchPage implements ISearchPage {

    private WebDriver driver;

    @FindBy(id = "loginLinkFromLoginBlock")
    public WebElement loginButton;

    @FindBy(id = "outboundOption.originLocationName")
    public WebElement inputFrom;

    @FindBy(id = "outboundOption.destinationLocationName")
    public WebElement inputToCode;

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

    @FindBy(id = "tripTypeRT")
    public WebElement tripTypeRT;

    @FindBy(id = "redemptionControl")
    public WebElement loopPrice;

    @FindBy(xpath = "//table[@class='botButton1 botButtonSearch']")
    public WebElement buttonSearch;

    public SearchPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
        driver.get(TAFProperties.getPROJECTIP() + "/BEL/ApplicationStartAction.do?" + TAFProperties.getPOS());
    }

    public void setOriginLocation(String code) throws Exception {
        new General().typeFlight(driver, inputFromHidden, inputFrom, code, "");
    }

    public void setDestinationLocation(String code) throws Exception {
        new General().typeFlight(driver, inputToCodeHidden, inputToCode, code, "");
    }

    public void inputDepartureDate(String daysFromToday) throws Exception {
        new General().inputDateByCalendar(driver, inputDepartOn, daysFromToday);
    }

    public void inputReturnDate(String daysFromToday) throws Exception {
        new General().inputDateByCalendar(driver, inputReturnOn, daysFromToday);
    }

    public void setSearchType(String searchType) {
        if ("OW".equalsIgnoreCase(searchType)) {
            tripTypeOW.click();
        } else {
            tripTypeRT.click();
        }
    }

    public void chooseLoopPrice(TestData testData){
        if ("Signed in".equalsIgnoreCase(testData.getLoopProfile()) && ("Loops".equalsIgnoreCase(testData.getFrequentFlierProgram()))) {
            loopPrice.click();
        }
    }

    public LoginPage goToLoginPage() {
        new ElementHelper(driver).waitForElementToBeClickable(loginButton);
        loginButton.click();
        new ElementHelper(driver).waitForFrameToBeAvailableAndSwitchToIt("ifrmLogin");
        return new LoginPage(driver);
    }

    public SelectionPage doSearch() {
        new ElementHelper(driver).waitForElementToBeClickable(buttonSearch);
        buttonSearch.click();
        return new SelectionPage(driver);
    }
}