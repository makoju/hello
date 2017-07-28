package com.datalex.taf.ui.base.exceptions;

import java.io.IOException;

/**
 * TAFSeleniumException class
 *
 * @author Aleksandar Vulovic
 */
public class TAFSeleniumException extends IOException {

    /**
     * General TAFSelenium exception
     *
     * @param message message
     */
    public TAFSeleniumException(String message) {
        super(message);
    }
}