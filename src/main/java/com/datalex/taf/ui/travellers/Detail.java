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
 * Created by jossie.saul on 27/07/2017.
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

    protected String passportNumber;

    protected String passportCountry;

    protected Date   passportExpiryDate;

    protected Date   dateOfBirth;

    public Detail(){
        Faker faker = new Faker();
        title = getRandom(titleChoice);
        firstName = faker.name().firstName();
        middleName = faker.name().firstName();
        lastName = faker.name().lastName();
        citizenship = "US";
        passportNumber = String.valueOf(new Random().nextInt(9000000) + 1000000);
        passportCountry = "US";
        passportExpiryDate = new Date();
    }

    private String getRandom(String[] array) {
        int rnd = new Random().nextInt(array.length);
        return array[rnd];
    }

    protected Date renderYearFromToday(String paxType){

        int yearBegin = 0;
        int yearEnd = 0;

        switch (paxType){
            case "ADT":
                yearBegin = 1920;
                yearEnd = Year.now().getValue()-18;
                break;
            case "CHD":
                yearBegin = Year.now().getValue()-11;
                yearEnd = Year.now().getValue()-3;
                break;
            case "INF":
                yearBegin = Year.now().getValue()-1;
                yearEnd = Year.now().getValue();
                break;
        }
        GregorianCalendar gc = new GregorianCalendar();
        int year = new Utils().randBetween(yearBegin, yearEnd);
        gc.set(gc.YEAR, year);
        int dayOfYear = new Utils().randBetween(1, gc.getActualMaximum(gc.DAY_OF_YEAR));
        gc.set(gc.DAY_OF_YEAR, dayOfYear);
        return gc.getTime();
    }

}
