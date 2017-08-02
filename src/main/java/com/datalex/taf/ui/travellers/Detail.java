package com.datalex.taf.ui.travellers;

import com.datalex.taf.ui.helpers.Utils;
import com.github.javafaker.Faker;
import lombok.Data;
import lombok.extern.log4j.Log4j2;

import java.time.Year;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Random;

/**
 * Detail pax class
 *
 * @author Jossie Soul
 */
@Data
@Log4j2
public class Detail {
    protected String[] titleChoice = {"MR", "MRS", "MS"};
    protected String title;
    protected String firstName;
    protected String middleName;
    protected String lastName;
    protected String citizenship;
    protected String frequentFlyer;
    protected String frequentFlyerNumber;
    protected String foidNumber;

    /**
     * 2.DOC - passport
     * ID_CARD - NationalID
     */
    protected String foidType;
    protected String foidCountry;
    protected String issuingCountry;
    protected Date foidExpiryDate;
    protected Date dateOfBirth;

    public Detail() {
        Faker faker = new Faker();
        this.title = getRandom(titleChoice);
        this.firstName = faker.name().firstName();
        this.middleName = faker.name().firstName();
        this.lastName = faker.name().lastName();
        this.citizenship = "US";
        this.foidType = "2.DOC";
        this.foidNumber = String.valueOf(new Random().nextInt(9000000) + 1000000);
        this.foidCountry = "US";
        this.foidExpiryDate = new Date();
        this.issuingCountry = "US";
    }

    private String getRandom(String[] array) {
        int rnd = new Random().nextInt(array.length);
        return array[rnd];
    }

    protected Date renderYearFromToday(String paxType) {
        int yearBegin = 0;
        int yearEnd = 0;

        switch (paxType) {
            case "ADT":
                yearBegin = 1920;
                yearEnd = Year.now().getValue() - 18;
                break;
            case "CHD":
                yearBegin = Year.now().getValue() - 11;
                yearEnd = Year.now().getValue() - 3;
                break;
            case "INF":
                yearBegin = Year.now().getValue() - 1;
                yearEnd = Year.now().getValue();
                break;
            default:
                log.error("Not supported pax type!");
        }
        GregorianCalendar gc = new GregorianCalendar();
        int year = new Utils().randBetween(yearBegin, yearEnd);
        gc.set(Calendar.YEAR, year);
        int dayOfYear = new Utils().randBetween(1, gc.getActualMaximum(Calendar.DAY_OF_YEAR));
        gc.set(Calendar.DAY_OF_YEAR, dayOfYear);
        return gc.getTime();
    }
}