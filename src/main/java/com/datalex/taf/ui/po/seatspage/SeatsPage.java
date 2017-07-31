package com.datalex.taf.ui.po.seatspage;

import com.datalex.taf.ui.data.TestData;
import com.datalex.taf.ui.helpers.ElementHelper;
import com.datalex.taf.ui.po.paymentpage.PaymentPage;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/**
 * Seats page class
 *
 * @author Aleksandar Vulovic
 */
@Log4j2
public class SeatsPage {
    private WebDriver driver;
    private ElementHelper elementHelper;

    @FindBy(id = "idSeatMapPlaceHolderInner")
    public WebElement seatSelectionImg;

    @FindBy(id = "pgButtonContinue")
    public WebElement nextFlightBtn;

    @FindBy(id = "pgButtonSubmit2")
    public WebElement continueBtn;

    @FindBy(xpath = "//a[@class='skipSelectionLink']")
    public WebElement skipSeatSelectionButton;

    @FindBy(xpath = "//*[@id='pgButtonClear']/span/span")
    public WebElement clearSeatSelection;

    public SeatsPage(WebDriver driver) {
        this.driver = driver;
        elementHelper = new ElementHelper(driver);
        elementHelper.waitForPresenceOfElementLocated(By.id("pgSeatSelection"));
        PageFactory.initElements(driver, this);
    }

    public PaymentPage goToPayment() {
        continueBtn.click();
        return new PaymentPage(driver);
    }

    public PaymentPage skipSeatSelection(TestData testData) {
        if ("RT".equalsIgnoreCase(testData.getTripType())) {
            elementHelper.waitForElementDisplayed(seatSelectionImg);
            elementHelper.waitForElementToBeClickable(nextFlightBtn);
            nextFlightBtn.click();
        }
        elementHelper.waitForElementDisplayed(seatSelectionImg);
        elementHelper.waitForElementDisplayed(continueBtn);
        continueBtn.click();
        return new PaymentPage(driver);
    }
}