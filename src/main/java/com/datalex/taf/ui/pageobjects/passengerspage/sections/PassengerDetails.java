package com.datalex.taf.ui.pageobjects.passengerspage.sections;

import com.datalex.taf.ui.pageobjects.exceptions.PassengerPageException;
import com.datalex.taf.ui.travellers.Adult;
import com.datalex.taf.ui.travellers.Child;
import com.datalex.taf.ui.travellers.Infant;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.WebDriver;

/**
 * Fill Passenger details section on Passenger Page
 *
 * @author Jossie Soul
 */
@Log4j2
public class PassengerDetails {
    private WebDriver driver;

    public PassengerDetails(WebDriver driver) {
        this.driver = driver;
    }

    public void fillTravellerInformation(String paxType, int passengerNumber) throws Exception {
        switch (paxType) {
            case "ADT":
                new Adult(driver, passengerNumber);
                break;
            case "CHD":
                new Child(driver, passengerNumber);
                break;
            case "INF":
                new Infant(driver, passengerNumber);
                break;
            default:
                log.error("PaxType is Empty or not Recognized. PaxType{" + paxType + "}");
                throw new PassengerPageException("Pax type not recognized!");
        }
    }
}