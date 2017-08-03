package com.datalex.taf.ui.pageobjects.paymentpage.data;

import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.NotFoundException;

import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * Created by jossie.saul on 02/08/2017.
 */
@Log4j2
public class CreditCardData {

    public CreditCardData(){

    }

    /**
     * Build the object by Credit Card type using preset data.
     *
     * @param ccType - available option {VISA,MASTERCARD,DINERS,DISCOVER,UATP,AMEX,AMERICANEXPRESS}
     * @return - CreditCard Object
     * @throws ParseException
     */
    public CreditCard buildCreditCardObjectByCreditCardType(String ccType) throws ParseException {
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
}
