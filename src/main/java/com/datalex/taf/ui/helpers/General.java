package com.datalex.taf.ui.helpers;

import com.datalex.taf.core.readers.property.TAFProperties;
import com.datalex.taf.ui.helpers.constants.SettingsConstants;
import lombok.extern.log4j.Log4j2;
import org.joda.time.DateTime;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * GeneralHelper class
 *
 * @author ES Quality Team members - reused from taf-ui 1.0
 */
@Log4j2
public class General {

    /**
     * Function to type airport code and select airport name item to form element direct using JS
     *
     * @param driver      instance
     * @param controlNameHidden WebElement
     * @param controlName name of element
     * @param airportCode code for airport
     * @param airportName name of airport
     * @throws Exception if error occurs
     */
    public void typeFlight(final WebDriver driver, WebElement controlNameHidden, WebElement controlName, final String airportCode, final String airportName) throws Exception {
        log.info("Inject " + airportCode + " Location into search field {" + airportCode + "}");

        String airportCodeToInject = airportCode;
        String airportNameToInject = "";
        String strLine;

        if (airportName.equals("")) {
            FileInputStream fstream;

            fstream = new FileInputStream("./src/test/resources/airportCodesList.txt");
            DataInputStream in = new DataInputStream(fstream);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));

            while ((strLine = br.readLine()) != null) {
                if (strLine.contains("(" + airportCode + ")")) {
                    airportNameToInject = strLine;

                }
            }
            in.close();
            fstream.close();

            if (airportNameToInject.equals("")) {
                throw new Exception("Could not find Airport Code in AirportCodesList " +
                        "Tried to find " + airportCodeToInject + " in Location into search field {" + controlName + "}");
            }

        } else {
            airportNameToInject = airportName;
        }

        if (typeUsingJS(driver, controlNameHidden, airportCodeToInject) &&
                typeUsingJS(driver, controlName, airportNameToInject)) {
        } else {
            log.error("Failed to Inject Location into Search box");
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
            JavascriptExecutor jse = (JavascriptExecutor) driver;
            String scriptExecute;

            scriptExecute = getJSLocator(locator) + ".value = '" + value + "';";
            jse.executeScript(scriptExecute);

            return true;

        } catch (Exception e) {
            log.error(e);
            return false;
        }
    }

    /**
     * Function to get locator for scriptExecute
     *
     * @param loc name of element
     * @return String JSlocator
     */

    public static String getJSLocator(WebElement loc) {
        return "document.getElementById('" + loc.getAttribute("id") + "')";
    }

    /**
     * Function to input date by calendar icon
     *
     * @param driver       instance
     * @param calendarIcon calendar icon xpath
     * @param dateNum      date in days from today
     * @throws Exception if error occurs
     */
    public void inputDateByCalendar(WebDriver driver, WebElement calendarIcon, String dateNum) throws Exception {
        String day;
        String month;
        String yearDep;
        DateTime dt = new DateTime();

        String dateDOMFormat = "ND";
        JavascriptExecutor jse = (JavascriptExecutor) driver;
        dateDOMFormat = (String) jse.executeScript("return window.Constants.dateEntryPattern;");
        log.debug(dateDOMFormat);

        if (!(dateDOMFormat == null)) {
            dateDOMFormat = dateDOMFormat.trim();
        } else {
            dateDOMFormat = "ND";
        }

        if (!dateNum.contains("/")) {
            if (dateNum.contains("-")) {
                log.debug("Specified Day Detected: " + dateNum + " | Attempting to set new Offset Days");

                String[] specDay = dateNum.split("-");
                int presetOD = Integer.parseInt(specDay[0]);
                int newPresetOD = 0;
                //take out the last 4 characters
                String offsetDay = determineDayOfWeek(presetOD);

                if (offsetDay.equals("ERROR")) {
                    throw new Exception("Error in determineDayOfWeek Method");
                }

                //new dateNum
                newPresetOD = determineDateDifference(offsetDay.toLowerCase(), specDay[1].toLowerCase(), presetOD);

                if (newPresetOD == 0) {
                    throw new Exception("Error in determineDateDifference Method");
                }

                dateNum = String.valueOf(newPresetOD);

                log.debug("New Offset Days: " + dateNum);
            }
            dt = dt.plusDays(Integer.parseInt(dateNum));
            month = "" + dt.getMonthOfYear();
            day = "" + dt.getDayOfMonth();
            yearDep = "" + dt.getYear();
        } else {
            String departureDate1 = dateNum;
            int index11, index22;
            index11 = departureDate1.indexOf('/');
            month = departureDate1.substring(0, index11);

            index22 = departureDate1.lastIndexOf('/');
            day = departureDate1.substring(index11 + 1, index22);

            yearDep = departureDate1.substring(index22 + 1);

        }

        int dayToInt;
        dayToInt = Integer.parseInt(day);
        if (dayToInt < Integer.parseInt("10")) {
            day = day.replaceFirst("0", "");
        }

        int subMonth = Integer.parseInt(month) - 1;
        month = String.valueOf(subMonth);

        log.debug("date from: " + yearDep + " " + month + " " + day);

        if (TAFProperties.getInjectJSCalendar()) {
            log.info("Not interacting with Calender. Injecting Dates Only!");
            //use calendarIcon as prefix to typing using JS
            //rather than doing another if else
            //nMonth Human Readable month

            String nMonth = String.valueOf(Integer.parseInt(month) + 1);

            if (Integer.valueOf(TAFProperties.getCUSTOMERCODE().trim()) == SettingsConstants.JBU_CUST)
                nMonth = String.format("%02d", Integer.parseInt(nMonth));

            String dateFormatAmerica = nMonth + "/" + day + "/" + yearDep;
            String dateFormatEurope = day + "/" + nMonth + "/" + yearDep;
            String dateFormatChinese = yearDep + "/" + nMonth + "/" + day;
            String dateToInject = "";


            if (!dateDOMFormat.equalsIgnoreCase("ND")) {
                dateToInject = dateDOMFormat.toLowerCase().startsWith("mm")
                        ? dateFormatAmerica : dateFormatEurope;
                if (dateDOMFormat.toLowerCase().startsWith("yy"))
                    dateToInject = dateFormatChinese;

                if (dateDOMFormat.toLowerCase().contains(".")) {
                    dateToInject = dateToInject.replace("/", ".");
                }
            } else {
                List<Integer> americaDateFormattedCust = Arrays.asList(
                        SettingsConstants.WJA_CUST, SettingsConstants.FRONTIER_CUST,
                        SettingsConstants.COPA_CUST, SettingsConstants.PAL_CUST,
                        SettingsConstants.JBU_CUST, SettingsConstants.PRODUCT_CUST,
                        SettingsConstants.ST_CUST, SettingsConstants.ABACUS_CUST);

                List<Integer> chineseDateFormattedCust = Arrays.asList(SettingsConstants.CHINA_CUST);


                dateToInject = americaDateFormattedCust.contains(
                        Integer.valueOf(TAFProperties.getCUSTOMERCODE().trim())) ? dateFormatAmerica : dateFormatEurope;

                if (chineseDateFormattedCust.contains(Integer.valueOf(TAFProperties.getCUSTOMERCODE().trim())))
                    dateToInject = dateFormatChinese;
            }

            log.debug("dateToInject " + dateToInject);


            if (populateCalendarJS(driver, calendarIcon, dateToInject,
                    String.format("%02d", Integer.parseInt(day)),
                    String.format("%02d", Integer.parseInt(month)),
                    yearDep)) {
            } else {
                throw new Exception("Injecting into JS Failed...miserably");
            }


//            if (typeUsingJS(driver, calendarIcon + "Date", dateToInject) &&
//                    typeUsingJS(driver, calendarIcon + "Day", String.format("%02d", Integer.parseInt(day))) &&
//                    typeUsingJS(driver, calendarIcon + "Month", String.format("%02d", Integer.parseInt(month))) &&
//                    typeUsingJS(driver, calendarIcon + "Year", yearDep)) ;
//            else
//                throw new Exception("Injecting into JS Failed...miserably");


        } else {
            //Open calendar by clicking icon
            calendarIcon.click();
            //driver.click(controls.get(calendarIcon));

            if (!driver.findElement(By.cssSelector("div#calendar1Outer")).isDisplayed()) {
                new Utils().wait(1000);
            }

            if (driver.findElement(By.cssSelector("div#calendar1Outer")).isDisplayed()) {
                /* NOTE: Tatsiana Kasiankova, 07/28/2011: In Selenium 2.2.0 we have the following ?issue/feature? in css.js
                 * for the case when we try to find an element by using a CSS selector:
    			 * ----------------------------------------------------------
    			 * <css.js> - the part of the code
    			 * ----------------------------------------------------------
    			 * <if (target.split(/,/).length > 1) {
    			 * throw Error('Compound selectors not permitted');>
    			 * ----------------------------------------------------------
    			 * So the locator SHOULN'T contain /,/ symbol
    			 *
    			 * NOTE: IC: Feature, will be removed in future Webdriver versions. Need to verify
    			 */
                int attempts = 60;
                int i = 0;

                boolean calendarShown = false;

                while (i <= attempts) {
                    if (!new ElementHelper(driver).isElementDisplayed(driver.findElement(By.cssSelector("div#calendar1Outer" + " a[onclick*='" +
                            "year:" + yearDep + "'][onclick*='month:" + month + "'][onclick*='day:" + day + "']")))) {

                        WebElement nextMonth = driver.findElement(By.cssSelector("div#calendar1Dialog a[onclick*='nextMonth']"));
                        new ElementHelper(driver).waitForElementPresent(nextMonth);
                        clickElementJS(driver, nextMonth);
                        new Utils().waitTime(1000);
                        i++;
                    } else {
                        calendarShown = true;
                        break;
                    }
                }
                if (!calendarShown) {
                    throw new Exception("FAIL");
                }
                new Utils().waitTime(1000);
                driver.findElement(By.cssSelector("div#calendar1Outer" + " a[onclick*='" +
                        "year:" + yearDep + "'][onclick*='month:" + month + "'][onclick*='day:" + day + "']")).click();
            } else {
                throw new Exception("Calendar has been opened but not visible");
            }
        }
    }

    /**
     * Method to determine the Day of the week on specified Offset Days from Today
     *
     * @param daysOffset number of offset days
     * @return selOffsetDay
     */
    public String determineDayOfWeek(int daysOffset) {
        String selOffsetDay;

        try {
            if (daysOffset < 1) {
                log.error("Offset Days cannot be less than ONE!");
                return "ERROR";
            }
            GregorianCalendar calendar = new GregorianCalendar();
            SimpleDateFormat simpleDateformat = new SimpleDateFormat("E");
            //set offsetDay
            calendar.add(GregorianCalendar.DAY_OF_MONTH, daysOffset);
            Date futureDay = calendar.getTime();

            selOffsetDay = simpleDateformat.format(futureDay);

        } catch (Exception e) {
            log.error(e.getMessage(), e);
            e.printStackTrace();
            return "ERROR";
        }
        return selOffsetDay;
    }

    /**
     * @param offsetDay  preset offset day
     * @param specDay    specified day
     * @param originalOD original offset days
     * @return retVal
     */
    public int determineDateDifference(String offsetDay, String specDay, int originalOD) {
        String[] daysOfTheWeek = {"sun", "mon", "tue", "wed", "thu", "fri", "sat"};
        int retVal = 0;
        int od = 0;
        int sd = 0;
        /** Limited Size*/
        final int limitedsize = 3;

        try {
            if (specDay.length() > limitedsize) {
                specDay = specDay.substring(0, limitedsize);
            }

            if (!Arrays.asList(daysOfTheWeek).contains(specDay)) {
                log.error("Cannot Find {" + specDay + "} in daysOfTheWeek Array");
                return 0;
            } else if (offsetDay.equals(specDay)) {
                //Leave the Original
                retVal = originalOD;
            } else {
                for (int i = 0; i < daysOfTheWeek.length; i++) {
                    if (daysOfTheWeek[i].contains(offsetDay)) {
                        od = i;
                    }
                    if (daysOfTheWeek[i].contains(specDay)) {
                        sd = i;
                    }
                }
                //cal New offset days
                retVal = originalOD + (sd - od);
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            e.printStackTrace();
            return 0;
        }
        return retVal;
    }

    /**
     * Function to click element by JS
     *
     * @param driver  instance
     * @param element element
     * @throws Exception if occurs
     */
    public void clickElementJS(WebDriver driver, WebElement element) throws Exception {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        if (driver.toString().contains("InternetExplorer")) {
            log.info("ie");
            js.executeScript("arguments[0].scrollIntoView(true);arguments[0].fireEvent( \'onclick \');", element);
        } else {
            log.info("ff or other");
            js.executeScript("var evObj=document.createEvent('MouseEvents');" +
                    "evObj.initEvent('click',true,true );arguments[0].dispatchEvent(evObj);", element);
        }
    }

    public boolean populateCalendarJS(WebDriver driver, WebElement element, String date, String day, String month, String year) throws Exception {
        try {
            JavascriptExecutor jse = (JavascriptExecutor) driver;

            String scriptExecuteDate;
            String loc = "document.getElementById('" + element.getAttribute("id") + "')";
            scriptExecuteDate = loc + "Date" + ".value = '" + date + "';";
            jse.executeScript(scriptExecuteDate);

            String scriptExecuteDay;
            scriptExecuteDay = loc + "Day" + ".value = '" + day + "';";
            jse.executeScript(scriptExecuteDay);

            String scriptExecuteMonth;
            scriptExecuteMonth = loc + "Month" + ".value = '" + month + "';";
            jse.executeScript(scriptExecuteMonth);

            String scriptExecuteYear;
            scriptExecuteYear = loc + "Year" + ".value = '" + year + "';";
            jse.executeScript(scriptExecuteYear);
            return true;
        } catch (Exception e) {
            log.error("Failed");
            return false;
        }
    }
}