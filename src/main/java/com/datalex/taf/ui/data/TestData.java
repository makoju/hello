package com.datalex.taf.ui.data;

import com.opencsv.bean.CsvBindByName;
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
    @CsvBindByName
    private String testCaseName;
    @CsvBindByName
    private String tripType;
    @CsvBindByName
    private String outboundPOS;
    @CsvBindByName
    private String inputFrom;
    @CsvBindByName
    private String inputTo;
    @CsvBindByName
    private String departOn;
    @CsvBindByName
    private String returnOn;
    @CsvBindByName
    private String fromMC1;
    @CsvBindByName
    private String toMC1;
    @CsvBindByName
    private String fromMC2;
    @CsvBindByName
    private String toMC2;
    @CsvBindByName
    private String fromMC3;
    @CsvBindByName
    private String toMC3;
    @CsvBindByName
    private String departOnMC3;
    @CsvBindByName
    private String adt;
    @CsvBindByName
    private String chd;
    @CsvBindByName
    private String inf;
    @CsvBindByName
    private String cabinClass;
    @CsvBindByName
    private String fareFamily;
    @CsvBindByName
    private String insuranceSummaryPage;
    @CsvBindByName
    private String baggage;
    @CsvBindByName
    private String delayedPayment;
    @CsvBindByName
    private String meals;
    @CsvBindByName
    private String seats;
    @CsvBindByName
    private String pwd;
    @CsvBindByName
    private String paymentType;
    @CsvBindByName
    private String loopProfile;
    @CsvBindByName
    private String frequentFlierProgram;
    @CsvBindByName
    private String age;
    @CsvBindByName
    private String insuranceEconomyInformationPage;
    @CsvBindByName
    private String baggageEconomyInformationPage;
    @CsvBindByName
    private String email;
    @CsvBindByName
    private String password;
    @CsvBindByName
    private String promotion;
    @CsvBindByName
    private String flightDates;

    /**
     * Method to log TestData
     */
    public void printTestData() {
        log.info(this.toString());
    }
}