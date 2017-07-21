package com.datalex.taf.ui.data;

import com.datalex.taf.core.loggers.TAFLogger;
import org.apache.commons.lang.builder.ToStringBuilder;
import lombok.Data;

/**
 * TestData class
 *
 * @author Aleksandar Vulovic
 */
@Data
public class TestData {
    private String testCaseName;
    private String tripType;
    private String outboundPOS;
    private String inputFrom;
    private String inputTo;
    private String departOn;
    private String returnOn;
    private String fromMC1;
    private String toMC1;
    private String fromMC2;
    private String toMC2;
    private String fromMC3;
    private String toMC3;
    private String departOnMC3;
    private String adt;
    private String chd;
    private String inf;
    private String cabinClass;
    private String fareFamily;
    private String insuranceSummaryPage;
    private String baggage;
    private String delayedPayment;
    private String meals;
    private String seats;
    private String pwd;
    private String paymentType;
    private String loopProfile;
    private String frequentFlierProgram;
    private String age;
    private String insuranceEconomyInformationPage;
    private String baggageEconomyInformationPage;
    private String testName;

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("testCaseName", testCaseName)
                .append("tripType", tripType)
                .append("outboundPOS", outboundPOS)
                .append("inputFrom", inputFrom)
                .append("inputTo", inputTo)
                .append("departOn", departOn)
                .append("returnOn", returnOn)
                .append("fromMC1", fromMC1)
                .append("toMC1", toMC1)
                .append("fromMC2", fromMC2)
                .append("toMC2", toMC2)
                .append("fromMC3", fromMC3)
                .append("toMC3", toMC3)
                .append("departOnMC3", departOnMC3)
                .append("adt", adt)
                .append("chd", chd)
                .append("inf", inf)
                .append("cabinClass", cabinClass)
                .append("fareFamily", fareFamily)
                .append("insuranceSummaryPage", insuranceSummaryPage)
                .append("baggage", baggage)
                .append("delayedPayment", delayedPayment)
                .append("meals", meals)
                .append("seats", seats)
                .append("pwd", pwd)
                .append("paymentType", paymentType)
                .append("loopProfile", loopProfile)
                .append("frequentFlierProgram", frequentFlierProgram)
                .append("age", age)
                .append("insuranceEconomyInformationPage", insuranceEconomyInformationPage)
                .append("baggageEconomyInformationPage", baggageEconomyInformationPage)
                .toString();
    }

    public void printTestData() {
        TAFLogger.info(this.toString());
    }
}