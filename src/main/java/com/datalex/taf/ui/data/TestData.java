package com.datalex.taf.ui.data;

import lombok.Data;
import lombok.ToString;
import lombok.extern.log4j.Log4j2;

/**
 * TestData class
 *
 * @author Aleksandar Vulovic
 */
@Data
@ToString
@Log4j2
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
    private String email;
    private String password;
    private String promotion;
    private String flightDates;

    /**
     * Method to log TestData
     */
    public void printTestData() {
        log.info(this.toString());
    }
}