package com.datalex.taf.ui.pageobjects.paymentpage.data;

import com.github.javafaker.Faker;
import lombok.extern.log4j.Log4j2;

import java.util.Date;

/**
 * Created by jossie.saul on 01/08/2017.
 */
@Log4j2
public class CreditCard extends CreditCardData implements ICreditCard {

    public String type;
    public String typeByOptionValue;
    public String cardHolder;
    public String number;
    public String securityCode;
    public Date issueDate;
    public Date expiryDate;
    public int issue;

    public String addressLine1;
    public String addressLine2;
    public String city;
    public String country = "US";
    public String postalCode;


    public CreditCard(){

    }

    @Override
    public ICreditCard name() {
        return this;
    }

    @Override
    public CreditCard setType(String m_type) {
        type = m_type.toUpperCase();
        return this;
    }

    public CreditCard setTypeByOptionValue(String m_type) {
        typeByOptionValue = m_type.toUpperCase();
        return this;
    }

    @Override
    public CreditCard setCardHolder(String m_cardholder) {
        cardHolder = m_cardholder;
        return this;
    }

    @Override
    public CreditCard setNumber(String m_number) {
        number = m_number;
        return this;
    }

    @Override
    public CreditCard setSecurityCode(String m_securityCode) {
        securityCode = m_securityCode;
        return this;
    }

    @Override
    public CreditCard setIssueDate(Date m_issueDate) {
        issueDate = m_issueDate;
        return this;
    }

    @Override
    public CreditCard setExpiryDate(Date m_expiryDate) {
        expiryDate = m_expiryDate;
        return this;
    }

    @Override
    public CreditCard setIssue(int m_issue) {
        issue = m_issue;
        return this;
    }

    public CreditCard retrieveRandomBillingInformation(){
        Faker fake = new Faker();
        addressLine1 = fake.address().streetAddress();
        addressLine2 = fake.address().streetAddress();
        city = fake.address().city();
        country = "US";
        postalCode = fake.address().zipCode();
        return this;
    }

}
