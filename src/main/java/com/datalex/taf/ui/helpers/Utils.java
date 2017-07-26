package com.datalex.taf.ui.helpers;

import org.apache.logging.log4j.LogManager;

/**
 * Utils Class
 *
 * @author Aleksandar Vulovic
 */
public class Utils {

    private static final org.apache.logging.log4j.Logger mLOG = LogManager.getLogger(Utils.class);

    /**
     * Method for thread sleep
     *
     * @param time time to sleep thread
     */
    public void waitTime(long time) {
        try {
            mLOG.debug(String.format("Waiting for %sms", time));
            Thread.sleep(time);
        } catch (Exception e) {
            mLOG.error(e);
        }
    }

    /**
     * Method for static 500ms wait
     */
    public void waitTime500ms() {
        waitTime(500);
    }
}
