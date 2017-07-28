package com.datalex.taf.ui.base.constants;

/**
 * Driver constants
 *
 * @author Aleksandar Vulovic
 */
public class DriverConstants {
    /**
     * Windows path constants
     */
    public static final String CHROME_DRIVER_WINDOWS_PATH = "C:\\drivers\\chromedriver.exe";
    public static final String GECKO_DRIVER_WINDOWS_PATH = "C:\\drivers\\geckodriver.exe";
    public static final String MS_DRIVER_WINDOWS_PATH = "C:\\drivers\\MicrosoftWebDriver.exe";
    public static final String IE_DRIVER_WINDOWS_PATH = "C:\\drivers\\IEDriverServer.exe";
    /**
     * Linux path constants
     */
    public static final String GECKO_DRIVER_LINUX_PATH = "/home/pstuser/drivers/geckodriver";
    public static final String CHROME_DRIVER_LINUX_PATH = "/home/pstuser/drivers/chromedriver_linux64";
    /**
     * System property constants
     */
    public static final String GECKO_DRIVER_SYSTEM_PROPERTY = "webdriver.gecko.driver";
    public static final String CHROME_DRIVER_SYSTEM_PROPERTY = "webdriver.chrome.driver";
    public static final String EDGE_DRIVER_SYSTEM_PROPERTY = "webdriver.edge.driver";
    public static final String IE_DRIVER_SYSTEM_PROPERTY = "webdriver.ie.driver";

    /**
     * Private constructor
     */
    private DriverConstants() {
        throw new UnsupportedOperationException("Constants class!");
    }
}