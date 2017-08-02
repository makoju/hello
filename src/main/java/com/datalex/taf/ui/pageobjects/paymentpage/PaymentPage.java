package com.datalex.taf.ui.pageobjects.paymentpage;

import com.datalex.taf.ui.data.TestData;
import com.datalex.taf.ui.helpers.ElementHelper;
import com.datalex.taf.ui.helpers.Utils;
import com.datalex.taf.ui.pageobjects.paymentpage.data.CreditCard;
import com.datalex.taf.ui.pageobjects.confirmationpage.ConfirmationPage;
import com.datalex.taf.ui.pageobjects.exceptions.PaymentPageException;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.text.ParseException;

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

    @FindBy(xpath = "//input[@id='creditCard.number:creditCard.numberDisplay']/..//iframe")
    public WebElement creditCardNumberForm;

    @FindBy(id = "creditCard.expirationMonth")
    public WebElement creditCardExpiryMonthForm;

    @FindBy(id = "creditCard.expirationYear")
    public WebElement creditCardExpiryYearForm;

    @FindBy(xpath = "//input[@id='creditCard.securityCode:creditCard.securityCodeDisplay']/..//iframe")
    public WebElement creditCardSecurityCodeForm;

    @FindBy(id = "creditCard.cardHolderName")
    public WebElement creditCardHolderNameForm;

    @FindBy(id = "creditCard.type")
    public WebElement creditCardTypeSelection;

    @FindBy(id = "billingAddress.addressLine1")
    public WebElement billingAddressLine1Form;

    @FindBy(id = "billingAddress.addressLine2")
    public WebElement billingAddressLine2Form;

    @FindBy(id = "billingAddress.city")
    public WebElement billingCityForm;

    @FindBy(id = "billingAddress.country")
    public WebElement billingCountryForm;

    @FindBy(id = "billingAddress.postalCode")
    public WebElement billingZipCodeForm;

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

    /**
     * This method in this pageObject is design to handle payment with Paypal.
     */
    public void payWithPayPal() {
        elementHelper.waitForElementToBeClickable(payPal);
        payPal.click();
        acceptTermsAndConditionsAndPay();
        new Utils().waitTime(10000);
        By acceptButton = By.id("btn_Accept");
        elementHelper.waitForPresenceOfElementLocated(acceptButton);
        driver.findElement(acceptButton).click();
    }

    public void payWithOnlineBanking(TestData testData) {
        elementHelper.waitForElementToBeClickable(onlineBank);
        onlineBank.click();
        elementHelper.selectOptionByValue(onlineBankDropDown, testData.getPaymentType());
    }

    public void payWithCreditCards(TestData testData) throws ParseException, Exception {
        CreditCard cc = new CreditCard().buildCreditCardObjectByCreditCardType(testData.getPaymentType());
        log.info(cc.toString());
        fillInCreditCardDetails(cc);
        fillInBillingAddressDetails(cc.retrieveRandomBillingInformation());
        elementHelper.waitForElementToBeClickable(acceptTermsAndConditionsCheckBox);
        acceptTermsAndConditionsCheckBox.click();
        confirmAndPay.click();

    }


    public void fillInCreditCardDetails(CreditCard cc) throws Exception{
        elementHelper.waitForElementToBeClickable(creditCard);
        creditCard.click();
        elementHelper.waitForElementToBeClickable(creditCardTypeSelection);
        elementHelper.selectOptionByValue(creditCardTypeSelection, cc.typeByOptionValue);
        elementHelper.waitForPresenceOfElementLocated(By.xpath("//input[@id='creditCard.number:creditCard.numberDisplay']/..//iframe"));
        elementHelper.waitForElementDisplayed(creditCardExpiryMonthForm);
        log.debug(cc.expiryDate.toString());
        elementHelper.selectOptionByValue(creditCardExpiryMonthForm,
                String.format("%02d", new Utils().getDateAttribute("MONTH", cc.expiryDate) + 1));
        elementHelper.selectOptionByText(creditCardExpiryYearForm, String.valueOf(new Utils().getDateAttribute("YEAR",cc.expiryDate)));
        elementHelper.waitForElementDisplayed(creditCardSecurityCodeForm);
        creditCardNumberForm.sendKeys(cc.number);
        creditCardSecurityCodeForm.sendKeys(cc.securityCode);
        creditCardHolderNameForm.sendKeys(cc.cardHolder);
    }

    public void fillInBillingAddressDetails(CreditCard cc){
        elementHelper.waitForElementDisplayed(billingAddressLine1Form);
        billingAddressLine1Form.sendKeys(cc.addressLine1);
        billingAddressLine2Form.sendKeys(cc.addressLine2);
        billingCityForm.sendKeys(cc.city);
        elementHelper.selectOptionByValue(billingCountryForm, cc.country);
        billingZipCodeForm.sendKeys(cc.postalCode);
    }

    public ConfirmationPage populatePaymentPage(TestData testData) throws PaymentPageException, Exception {
        switch (testData.getPaymentType()) {
            //NOTE: CREDITCARD GROUP
            case "VISA":
            case "MASTERCARD":
            case "AMEX":
            case "DISCOVER":
            case "DINERS":
                payWithCreditCards(testData);
                break;
            //CREDITCARD GROUP ENDS!
            case "MAESTRO":
            case "UATP":
                break;
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
