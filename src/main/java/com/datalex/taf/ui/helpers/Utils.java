package com.datalex.taf.ui.helpers;

import lombok.extern.log4j.Log4j2;

import java.util.Calendar;
import java.util.Date;

/**
 * Utils Class
 *
 * @author Aleksandar Vulovic
 */
@Log4j2
public class Utils {

    /**
     * Method for thread sleep
     *
     * @param time time to sleep thread
     */
    public void waitTime(long time) {
        try {
            log.debug(String.format("Waiting for %sms", time));
            Thread.sleep(time);
        } catch (Exception e) {
            log.error(e);
        }
    }

    /**
     * Method for static 500ms wait
     */
    public void waitTime500ms() {
        waitTime(500);
    }

    public int randBetween(int start, int end) {
        return start + (int)Math.round(Math.random() * (end - start));
    }

    /**
     * Get Date Attribute on either DAY, MONTH or YEAR of the date provided. Which will return a integer or Index for the month.
     * @param attribute - Choices DAY, MONTH, YEAR
     * @param date - Date Specified
     * @return int - attributeIndex
     */
    public int getDateAttribute(String attribute, Date date) throws Exception{
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int attributeIndex = 0;
        switch (attribute){
            case "DAY":
                attributeIndex = cal.get(Calendar.DAY_OF_MONTH);
                break;
            case "MONTH":
                attributeIndex = cal.get(Calendar.MONTH);
                break;
            case "YEAR":
                attributeIndex = cal.get(Calendar.YEAR);
                break;
            default:
                log.error("INVALID DATE ATTRIBUTE");
                throw new Exception();
        }
        return attributeIndex;
    }
}
