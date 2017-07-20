package com.datalex.taf.ui.po.searchpage;

import com.datalex.taf.core.readers.property.TAFProperties;
import com.datalex.taf.ui.helpers.General;
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

    public SearchPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
        String dynUrl = TAFProperties.getPROJECTIP() + "/BEL/ApplicationStartAction.do?" + TAFProperties.getPOS();
        driver.get(dynUrl);
    }

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

    @FindBy(xpath = "//table[@class='botButton1 botButtonSearch']")
    public WebElement buttonSearch;

    public void setOriginLocation(String code) throws Exception {
        new General().typeFlight(driver, inputFromHidden, inputFrom, code, "");
    }

    public void setDestinationLocation(String code) throws Exception {
        new General().typeFlight(driver, inputToCodeHidden, inputToCode, code, "");
    }
    public void setSearchType(String searchType) throws Exception {
        if (searchType.toUpperCase().equals("OW")) {
            tripTypeOW.click();
        } else {
            tripTypeRT.click();
        }
    }

    public SelectionPage doSearch() {
        buttonSearch.click();
        return new SelectionPage(driver);
    }
}