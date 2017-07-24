package com.datalex.taf.ui.base;

import com.datalex.taf.core.readers.property.LoadProperties;
import com.datalex.taf.core.readers.property.TAFProperties;
import com.datalex.taf.core.utilities.OSDetection;
import com.datalex.taf.ui.base.exceptions.TAFSeleniumException;
import org.apache.logging.log4j.LogManager;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.io.File;
import java.io.IOException;
import java.net.URL;

/**
 * TAFSelenium class
 *
 * @author Aleksandar Vulovic
 */
public class TAFSelenium {

    private static final org.apache.logging.log4j.Logger mLOG = LogManager.getLogger(TAFSelenium.class);
    private static ThreadLocal<RemoteWebDriver> mDriver = new ThreadLocal<>();
    private DesiredCapabilities capability;

    public TAFSelenium() throws TAFSeleniumException, IOException {
        LoadProperties.propertyLoader();
        String browserName = TAFProperties.getBrowserType().toUpperCase();
        URL driverURL = new URL(TAFProperties.getGridHost() + ":" + TAFProperties.getGridPort() + "/wd/hub");

        switch (browserName) {
            case "CHROME":
                setChromeBrowser();
                break;
            case "EDGE":
                setEDGEBrowser();
                break;
            case "IE":
                setIEBrowser();
                break;
            case "FIREFOX":
                setFirefoxBrowser();
                break;
            default:
                throw new TAFSeleniumException("Choose browserType in taf.properties file!");
        }

        RemoteWebDriver driver = new RemoteWebDriver(driverURL, capability);
        mDriver.set(driver);
        mDriver.get().manage().deleteAllCookies();
        mDriver.get().manage().window().maximize();
        mLOG.info("TAFSelenium initialized!");
    }

    /**
     * Function to get driver instance
     *
     * @return driver
     */
    public WebDriver getDriver() {
        return mDriver.get();
    }

    private void setFirefoxBrowser() {
        File geckoDriver;
        if (OSDetection.isWindows()) {
            geckoDriver = new File("C:\\drivers\\geckodriver.exe");
        } else {
            geckoDriver = new File("/home/pstuser/drivers/geckodriver");
        }

        System.setProperty("webdriver.gecko.driver", geckoDriver.getAbsolutePath());
        capability = DesiredCapabilities.firefox();
        capability.setBrowserName("firefox");
        capability.setPlatform(Platform.ANY);
        capability.setCapability(FirefoxDriver.MARIONETTE, true);
        capability.setJavascriptEnabled(true);
    }

    private void setChromeBrowser() {
        if (OSDetection.isWindows()) {
            final File file = new File("C:\\drivers\\chromedriver.exe");
            System.setProperty("webdriver.chrome.driver", file.getAbsolutePath());
        } else if (OSDetection.isUnix()) {
            final File file = new File("/home/pstuser/drivers/chromedriver_linux64");
            System.setProperty("webdriver.chrome.driver", file.getAbsolutePath());
        }
        capability = DesiredCapabilities.chrome();
        capability.setJavascriptEnabled(true);
        ChromeOptions options = new ChromeOptions();
        options.addArguments("no-sandbox");
        options.addArguments("--disable-extensions");
        options.addArguments("test-type");
        capability.setCapability(ChromeOptions.CAPABILITY, options);
    }

    private void setIEBrowser() throws TAFSeleniumException {
        if (OSDetection.isWindows()) {
            final File file = new File("C:\\drivers\\IEDriverServer.exe");
            System.setProperty("webdriver.ie.driver", file.getAbsolutePath());
        } else {
            throw new TAFSeleniumException("Not a windows machine. Please start up the the driver in selenium server");
        }
        capability = DesiredCapabilities.internetExplorer();
        capability.setJavascriptEnabled(true);
        capability.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
    }

    private void setEDGEBrowser() throws TAFSeleniumException {
        if (OSDetection.isWindows()) {
            final File file = new File("C:\\drivers\\MicrosoftWebDriver.exe");
            System.setProperty("webdriver.edge.driver", file.getAbsolutePath());
        } else {
            throw new TAFSeleniumException("Not a windows machine. Please start up the the driver in selenium server");
        }
        capability = DesiredCapabilities.edge();
        capability.setJavascriptEnabled(true);
    }
}