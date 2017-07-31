package com.datalex.taf.ui.po.seatspage;

import com.datalex.taf.ui.helpers.ElementHelper;
import com.datalex.taf.ui.po.paymentpage.PaymentPage;
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
        elementHelper.waitForPresenceOfElementLocated(By.id("pgSeatSelect"));
        //elementHelper.waitForElementToBeClickable(By.id("pgButtonContinue"));
        PageFactory.initElements(driver, this);
    }

    public void goToNextFlight(){
        nextFlightBtn.click();
    }

    public PaymentPage goToPayment() {
        continueBtn.click();
        return new PaymentPage(driver);
    }

    public PaymentPage skipSeatSelection() {
        skipSeatSelectionButton.click();
        return new PaymentPage(driver);
    }
}
