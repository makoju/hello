package com.datalex.taf.ui.helpers;

import lombok.extern.log4j.Log4j2;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

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
        return start + (int) Math.round(Math.random() * (end - start));
    }

    /**
     * Append PNR number in CSV file
     *
     * @param pnr PNR
     * @throws IOException if error occurs
     */
    public void savePNRinCSV(String pnr) throws IOException {
        File file = new File("./work/reservationNumbers.csv");
        try {
            FileUtils.writeStringToFile(file, "\n" + pnr, true);
        } catch (IOException e) {
            log.error(e);
            throw new IOException(e);
        }
    }
}
