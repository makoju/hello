package com.datalex.taf.ui.helpers;

import com.datalex.taf.core.loggers.TAFLogger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.InputStreamReader;

import static com.datalex.taf.core.helpers.PathConstants.DATA_FOLDER;

/**
 * GeneralHelper class
 */
public class General {

    /**
     * Function to type airport code and select airport name item to form element direct using JS
     *
     * @param driver      instance
     * @param controlName name of element
     * @param airportCode code for airport
     * @param airportName name of airport
     * @throws Exception if error occurs
     */
    public void typeFlight(final WebDriver driver, WebElement controlNameHidden, WebElement controlName, final String airportCode, final String airportName) throws Exception {

        WebElement webElement = null;
        By ajaxElement;
        TAFLogger.info("Inject " + airportCode + " Location into search field {" + airportCode + "}");

        String airportCodeToInject = airportCode;
        String airportNameToInject = "";
        String strLine;

        if (airportName.equals("")) {
            TAFLogger.debug("No Airport Name Specified. Looking through the Airport Codes List");
            FileInputStream fstream;

            fstream = new FileInputStream("./src/test/resources/airportCodesList.txt");

            DataInputStream in = new DataInputStream(fstream);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));

            while ((strLine = br.readLine()) != null) {
                if (strLine.contains("(" + airportCode + ")")) {
                    TAFLogger.debug("Airport Name Found in List = " + strLine);
                    airportNameToInject = strLine;

                }
            }
            in.close();

            if (airportNameToInject.equals("")) {
                TAFLogger.error("Could Not Find Airport Code in list");
                throw new Exception("Could not find Airport Code in AirportCodesList " +
                        "Tried to find " + airportCodeToInject + " in Location into search field {" + controlName + "}");
            }

        } else {
            TAFLogger.info("Airport Name specified");
            airportNameToInject = airportName;
        }

        if (typeUsingJS(driver, controlNameHidden, airportCodeToInject) &&
                typeUsingJS(driver, controlName, airportNameToInject)) {
            TAFLogger.info("Succesfully injected into location Search box");
        } else {
            TAFLogger.error("Failed to Inject Location into Search box");
            throw new Exception("Failed to Inject Location into Search box. " +
                    "Tried to Inject " + airportNameToInject + " (" + airportCodeToInject + ")" +
                    " Location into search field {" + controlName + "}");
        }

    }

    /**
     * Function to type to input using JS
     *
     * @param driver  webdriver
     * @param locator locator for element to type
     * @param value   value to type
     * @return boolean indicator
     */

    public boolean typeUsingJS(WebDriver driver, WebElement locator, String value) {
        try {
            TAFLogger.info("Method with JS executing");
            TAFLogger.debug("selenium.type  " + value + " into " + locator);
            JavascriptExecutor jse = (JavascriptExecutor) driver;
            String scriptExecute;

            scriptExecute = getJSLocator(locator) + ".value = '" + value + "';";
            jse.executeScript(scriptExecute);

            return true;

        } catch (Exception e) {
            TAFLogger.error(e.getMessage());
            return false;
        }
    }

    /**
     * Function to get locator for scriptExecute
     *
     * @param loc    name of element
     * @return String JSlocator
     * @throws Exception if occurs
     */

    public static String getJSLocator(WebElement loc) throws Exception {
        String scriptExecute = "Null";
        scriptExecute = "document.getElementById('" + loc.getAttribute("id") + "')";
        TAFLogger.error(scriptExecute);
        return scriptExecute;
    }
}
