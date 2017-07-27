package com.datalex.taf.ui.helpers;

import lombok.extern.log4j.Log4j2;

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
}
