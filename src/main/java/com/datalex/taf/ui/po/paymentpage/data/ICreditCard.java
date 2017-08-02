package com.datalex.taf.ui.po.paymentpage.data;

import java.util.Date;

/**
 * Created by jossie.saul on 01/08/2017.
 */
public interface ICreditCard {
    public ICreditCard name();
    public CreditCard setType(String m_type);
    public CreditCard setCardHolder(String m_cardholder);
    public CreditCard setNumber(String m_number);
    public CreditCard setSecurityCode(String m_securityCode);
    public CreditCard setIssueDate(Date m_issueDate);
    public CreditCard setExpiryDate(Date m_expiryDate);
    public CreditCard setIssue(int m_issue);
}
