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

    @FindBy(id = "pgSeatSelection")
    public WebElement pgSeatSelect;

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
        elementHelper.waitForPresenceOfElementLocated(By.id("idSeatMapPlaceHolder"));
        PageFactory.initElements(driver, this);
    }

    public void goToNextFlight() {
        nextFlightBtn.click();
    }

    public PaymentPage goToPayment() {
        continueBtn.click();
        return new PaymentPage(driver);
    }

    public PaymentPage skipSeatSelection(TestData testData) {
        if ("RT".equalsIgnoreCase(testData.getTripType())) {
            nextFlightBtn.click();
            log.info("Clicked skip seat selection for RT");
        }
        elementHelper.waitForElementToBeClickable(skipSeatSelectionButton);
        PageFactory.initElements(driver, this);
        nextFlightBtn.click();
        log.info("Clicked skip seat selection for OW");
        return new PaymentPage(driver);
    }
}
