package com.datalex.taf.ui.base.exceptions;

/**
 * TAFScreenshotException
 *
 * @author Aleksandar Vulovic
 */
public class TAFScreenshotException extends Exception {

    /**
     * General TAFSelenium screenshot exception
     *
     * @param message message
     */
    public TAFScreenshotException(String message) {
        super(message);
    }
}