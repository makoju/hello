package com.datalex.taf.ui.po.paymentpage;

import com.datalex.taf.ui.data.TestData;
import com.datalex.taf.ui.helpers.ElementHelper;
import com.datalex.taf.ui.po.exceptions.PaymentPageException;
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
        this.driver = driver;
        elementHelper = new ElementHelper(driver);
        elementHelper.waitForPresenceOfElementLocated(By.id("pgPayment"));
        elementHelper.waitForElementToBeClickable(By.id("pgButtonPurchase"));
        PageFactory.initElements(driver, this);
    }

    public void payWithPayPal(TestData testData) {
        elementHelper.waitForElementToBeClickable(payPal);
        payPal.click();
    }

    public void payWithOnlineBanking(TestData testData) {
        elementHelper.waitForElementToBeClickable(onlineBank);
        onlineBank.click();
        new ElementHelper().selectOptionByValue(onlineBankDropDown, testData.getPaymentType());
    }

    public void payWithCreditCards(TestData testData) {

    }

    public void payWithDebitCards(TestData testData) {
    }

    public void populatePaymentPage(TestData testData) throws PaymentPageException {
        switch (testData.getPaymentType()) {
            case "VISA":
            case "MASTERCARD":
            case "AMEX":
            case "DISCOVER":
            case "DINERS":
            case "MAESTRO":
            case "UATP":
            case "PAYPAL":
                payWithPayPal(testData);
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
        elementHelper.waitForElementToBeClickable(acceptTermsAndConditionsCheckBox);
        acceptTermsAndConditionsCheckBox.click();
        elementHelper.waitForElementToBeClickable(confirmAndPay);
        confirmAndPay.click();
    }
}
