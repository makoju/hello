package com.datalex.taf.ui.helpers;

import org.apache.logging.log4j.LogManager;

/**
 * Utils Class
 *
 * @author Aleksandar Vulovic
 */
public class Utils {

    private static final org.apache.logging.log4j.Logger TAFLogger = LogManager.getLogger(Utils.class);

    public void waitTime(long time) {
        try {
            TAFLogger.debug(String.format("Waiting for %sms", time));
            Thread.sleep(time);
        } catch (final InterruptedException e) {
            TAFLogger.error(e.getMessage());
        }
    }

    public void waitTime500ms() {
        waitTime(500);
    }
}
