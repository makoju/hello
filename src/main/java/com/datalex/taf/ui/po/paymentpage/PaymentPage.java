package com.datalex.taf.ui.po.paymentpage;

import com.datalex.taf.ui.data.TestData;
import com.datalex.taf.ui.helpers.ElementHelper;
import com.datalex.taf.ui.helpers.Utils;
import com.datalex.taf.ui.payments.CreditCard;
import com.datalex.taf.ui.helpers.Utils;
import com.datalex.taf.ui.po.confirmationpage.ConfirmationPage;
import com.datalex.taf.ui.po.exceptions.PaymentPageException;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.By;
import org.openqa.selenium.NotFoundException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import javax.validation.constraints.Null;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

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
        CreditCard cc = buildCreditCardObject(testData.getPaymentType());
        log.info(cc.toString());
        fillInCreditCardDetails(cc);
        fillInBillingAddressDetails(cc.retrieveRandomBillingInformation());
        elementHelper.waitForElementToBeClickable(acceptTermsAndConditionsCheckBox);
        acceptTermsAndConditionsCheckBox.click();
        confirmAndPay.click();

    }

    protected CreditCard buildCreditCardObject(String ccType) throws ParseException {
            String optionValue = "VI";
            String ccNumber = "41111111111111";
            String securiyCode = "111";

        switch(ccType.toUpperCase()) {
            case "VISA":
                optionValue = "VI";
                ccNumber = "4111111111111111";
                break;
            case "MASTERCARD":
                optionValue = "MC";
                ccNumber = "5399999999999999";
                break;
            case "DINERS":
                optionValue = "DN";
                ccNumber = "36255695580017";
                break;
            case "DISCOVER":
                optionValue = "DI";
                ccNumber = "6011111111111117";
                break;
            case "UATP":
                optionValue = "TP";
                ccNumber = "108112000000004";
                break;
            case "AMEX":
                optionValue = "AM";
                ccNumber = "374111111111111";
                break;
            case "AMERICANEXPRESS":
                optionValue = "AM";
                ccNumber = "";
                break;
            default:
                log.error("CREDIT CARD TYPE NOT FOUND");
                throw new NotFoundException("INVALID CREDITCARD TYPE");
        }
        return new CreditCard()
                .setTypeByOptionValue(optionValue)
                .setCardHolder("John Wayne")
                .setNumber(ccNumber)
                .setSecurityCode(securiyCode)
                .setExpiryDate(new SimpleDateFormat("dd/MM/yyyy").parse("01/01/2025"));
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
            case "VISA":
                payWithCreditCards(testData);
                break;
            case "MASTERCARD":
                payWithCreditCards(testData);
                break;
            case "AMEX":
                payWithCreditCards(testData);
                break;
            case "DISCOVER":
                payWithCreditCards(testData);
                break;
            case "DINERS":
                payWithCreditCards(testData);
                break;
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
