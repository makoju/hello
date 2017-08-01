package com.datalex.taf.ui.base;

import com.datalex.taf.core.readers.property.LoadProperties;
import com.datalex.taf.core.readers.property.TAFProperties;
import com.datalex.taf.core.utilities.OSDetection;
import com.datalex.taf.ui.base.exceptions.TAFSeleniumException;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import static com.datalex.taf.ui.base.constants.DriverConstants.*;

/**
 * TAFSelenium class
 * <p>
 * TAFSelenium can be started in "local" mode and in "grid" mode.
 * If runMode=local in taf.properties, it will start local WebDriver
 * If runMode=grid in taf.properties it will use RemoteWebDriver
 *
 * @author Aleksandar Vulovic
 */
@Log4j2
public class TAFSelenium {
    private static ThreadLocal<WebDriver> driver = new ThreadLocal<>();
    private static DesiredCapabilities capability;
    private static final String RUN_MODE_LOCAL = "local";

    /**
     * Empty private constructor
     */
    private TAFSelenium() {
    }

    /**
     * TAFSelenium init method
     * Example of usage: TAFSelenium.initDriver();
     * NOTE: It is important to use getDriver() because of parallel runs
     *
     * @throws TAFSeleniumException if taf.properties browserType field is empty
     * @throws IOException          if reading from taf.properties fails
     */
    public static void initDriver() throws IOException {
        LoadProperties.propertyLoader();
        String browserName = TAFProperties.getBrowserType().toUpperCase();
        URL driverURL = new URL(TAFProperties.getGridHost() + ":" + TAFProperties.getGridPort() + "/wd/hub");

        switch (browserName) {
            case "FIREFOX":
                setFirefoxBrowser();
                break;
            case "CHROME":
                setChromeBrowser();
                break;
            case "EDGE":
                setEDGEBrowser();
                break;
            case "IE":
                setIEBrowser();
                break;
            default:
                throw new TAFSeleniumException("Choose browserType in taf.properties file!");
        }

        if (!RUN_MODE_LOCAL.equalsIgnoreCase(TAFProperties.getSeleniumRunModeValue())) {
            TAFSelenium.driver.set(new RemoteWebDriver(driverURL, capability));
        }
        driver.get().manage().deleteAllCookies();
        //FIXME: to be enabled after fix of FF driver issue
        if (!"FIREFOX".equals(browserName))
            driver.get().manage().window().maximize();
        log.info("TAFSelenium initialized!");
    }

    /**
     * Function to get driver instance
     * Example of usage: WebDriver driver = TAFSelenium.getDriver();
     *
     * @return WebDriver
     */
    public static WebDriver getDriver() {
        return driver.get();
    }

    /**
     * Set Firefox driver
     */
    private static void setFirefoxBrowser() {
        File geckoDriver;
        if (OSDetection.isWindows()) {
            geckoDriver = new File(GECKO_DRIVER_WINDOWS_PATH);
        } else {
            geckoDriver = new File(GECKO_DRIVER_LINUX_PATH);
        }
        System.setProperty(GECKO_DRIVER_SYSTEM_PROPERTY, geckoDriver.getAbsolutePath());
        capability = DesiredCapabilities.firefox();
        capability.setBrowserName("firefox");
        capability.setPlatform(Platform.ANY);
        capability.setCapability(FirefoxDriver.MARIONETTE, true);
        capability.setJavascriptEnabled(true);

        if (RUN_MODE_LOCAL.equalsIgnoreCase(TAFProperties.getSeleniumRunModeValue())) {
            driver.set(new FirefoxDriver(capability));
        }
    }

    /**
     * Set Chrome driver
     */
    private static void setChromeBrowser() {
        if (OSDetection.isWindows()) {
            final File file = new File(CHROME_DRIVER_WINDOWS_PATH);
            System.setProperty(CHROME_DRIVER_SYSTEM_PROPERTY, file.getAbsolutePath());
        } else if (OSDetection.isUnix()) {
            final File file = new File(CHROME_DRIVER_LINUX_PATH);
            System.setProperty(CHROME_DRIVER_SYSTEM_PROPERTY, file.getAbsolutePath());
        }
        capability = DesiredCapabilities.chrome();
        capability.setJavascriptEnabled(true);
        ChromeOptions options = new ChromeOptions();
        options.addArguments("no-sandbox");
        options.addArguments("--disable-extensions");
        options.addArguments("test-type");
        capability.setCapability(ChromeOptions.CAPABILITY, options);

        if (RUN_MODE_LOCAL.equalsIgnoreCase(TAFProperties.getSeleniumRunModeValue())) {
            driver.set(new ChromeDriver(capability));
        }
    }

    /**
     * Set EDGE driver
     *
     * @throws TAFSeleniumException if driver file reading fails
     */
    private static void setEDGEBrowser() throws TAFSeleniumException {
        if (OSDetection.isWindows()) {
            final File file = new File(MS_DRIVER_WINDOWS_PATH);
            System.setProperty(EDGE_DRIVER_SYSTEM_PROPERTY, file.getAbsolutePath());
        } else {
            throw new TAFSeleniumException("Not a windows machine. Please start up the the driver in selenium server");
        }
        capability = DesiredCapabilities.edge();
        capability.setJavascriptEnabled(true);

        if (RUN_MODE_LOCAL.equalsIgnoreCase(TAFProperties.getSeleniumRunModeValue())) {
            driver.set(new EdgeDriver(capability));
        }
    }

    /**
     * Set IE driver
     *
     * @throws TAFSeleniumException if driver file reading fails
     */
    private static void setIEBrowser() throws TAFSeleniumException {
        if (OSDetection.isWindows()) {
            final File file = new File(IE_DRIVER_WINDOWS_PATH);
            System.setProperty(IE_DRIVER_SYSTEM_PROPERTY, file.getAbsolutePath());
        } else {
            throw new TAFSeleniumException("Not a windows machine. Please start up the the driver in selenium server");
        }
        capability = DesiredCapabilities.internetExplorer();
        capability.setJavascriptEnabled(true);
        capability.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);

        if (RUN_MODE_LOCAL.equalsIgnoreCase(TAFProperties.getSeleniumRunModeValue())) {
            driver.set(new InternetExplorerDriver(capability));
        }
    }
}