package com.datalex.taf.ui.po.paymentpage;

import com.datalex.taf.ui.helpers.ElementHelper;
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

    public PaymentPage(WebDriver driver) {
        this.driver = driver;
        new ElementHelper(driver).waitForPresenceOfElementLocated(By.id("pgPayment"));
        new ElementHelper(driver).waitForElementToBeClickable(By.id("pgButtonPurchase"));
        PageFactory.initElements(driver, this);
    }
}
