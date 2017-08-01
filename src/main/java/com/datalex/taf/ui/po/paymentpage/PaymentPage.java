package com.datalex.taf.ui.po.paymentpage;

import com.datalex.taf.ui.data.TestData;
import com.datalex.taf.ui.helpers.ElementHelper;
import com.datalex.taf.ui.helpers.Utils;
import com.datalex.taf.ui.po.confirmationpage.ConfirmationPage;
import com.datalex.taf.ui.po.exceptions.PaymentPageException;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/**
 * PaymentPage class
 *
 * @author Aleksandar Vulovic
 */
@Log4j2
public class PaymentPage {
    private WebDriver driver;
    private ElementHelper elementHelper;

    @FindBy(id = "pgButtonPurchase")
    public WebElement confirmAndPay;

    @FindBy(id = "acceptTermsAndConditionsCheckBox")
    public WebElement acceptTermsAndConditionsCheckBox;

    @FindBy(id = "formOfPayment(ONLINEBANK).selected")
    public WebElement onlineBank;

    @FindBy(id = "formOfPayment(CREDITCARD_POS).selected")
    public WebElement creditCard;

    @FindBy(id = "formOfPayment(PAYPAL).selected")
    public WebElement payPal;

    @FindBy(id = "formOfPayment(DEBITCARD).selected")
    public WebElement debitCard;

    @FindBy(id = "onlineBank.bankId")
    public WebElement onlineBankDropDown;

    public PaymentPage(WebDriver driver) {
        log.info("Initiating Payment page...");
        this.driver = driver;
        elementHelper = new ElementHelper(driver);
        elementHelper.waitForPresenceOfElementLocated(By.id("pgPayment"));
        elementHelper.waitForElementToBeClickable(By.id("pgButtonPurchase"));
        PageFactory.initElements(driver, this);
    }

    public void payWithPayPal() {
        elementHelper.waitForElementToBeClickable(payPal);
        payPal.click();
        acceptTermsAndConditionsAndPay();
        new Utils().waitTime(10000);
        elementHelper.waitForPresenceOfElementLocated(By.id("btn_Accept"));
        driver.findElement(By.id("btn_Accept")).click();
    }

    public void payWithOnlineBanking(TestData testData) {
        elementHelper.waitForElementToBeClickable(onlineBank);
        onlineBank.click();
        elementHelper.selectOptionByValue(onlineBankDropDown, testData.getPaymentType());
    }

    public void payWithCreditCards(TestData testData) {

    }

    public void payWithDebitCards(TestData testData) {
    }

    public ConfirmationPage populatePaymentPage(TestData testData) throws PaymentPageException {
        switch (testData.getPaymentType()) {
            case "VISA":
            case "MASTERCARD":
            case "AMEX":
            case "DISCOVER":
            case "DINERS":
            case "MAESTRO":
            case "UATP":
            case "PAYPAL":
                payWithPayPal();
                break;
            case "BANCONTACT":
            case "SOFORT":
            case "ING":
            case "KBC":
            case "CBC":
            case "BELFIUS":
            case "IDEAL":
            default:
                throw new PaymentPageException("Payment method not specified!");
        }
        log.info("Payment done! Going to confirmation page...");
        return new ConfirmationPage(driver);
    }

    public void acceptTermsAndConditionsAndPay() {
        elementHelper.waitForElementToBeClickable(acceptTermsAndConditionsCheckBox);
        acceptTermsAndConditionsCheckBox.click();
        elementHelper.waitForElementToBeClickable(confirmAndPay);
        confirmAndPay.click();
    }
}
