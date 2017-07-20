package com.datalex.taf.ui.base;

import com.datalex.taf.core.readers.property.LoadProperties;
import com.datalex.taf.core.readers.property.TAFProperties;
import com.datalex.taf.core.utilities.OSDetection;
import org.apache.logging.log4j.LogManager;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.io.File;
import java.net.URL;

/**
 * TAFSelenium class
 *
 * @author Aleksandar Vulovic
 */
public class TAFSelenium {

    private static final org.apache.logging.log4j.Logger TAFLogger = LogManager.getLogger(TAFSelenium.class);
    private static ThreadLocal<RemoteWebDriver> m_driver = new ThreadLocal<RemoteWebDriver>();

    public TAFSelenium() throws Exception {
        LoadProperties.propertyLoader();
        DesiredCapabilities capability;
        URL driverURL;

        String browserName = TAFProperties.getBrowserType().toUpperCase();
        driverURL = new URL(TAFProperties.getGridHost() + ":" + TAFProperties.getGridPort() + "/wd/hub");

        switch (browserName) {
            case "CHROME":
                if (OSDetection.isWindows()) {
                    TAFLogger.debug("Initiating Chrome Driver @ WINDOWS");
                    final File file = new File("C:\\drivers\\chromedriver.exe");
                    System.setProperty("webdriver.chrome.driver", file.getAbsolutePath());
                } else if (OSDetection.isUnix()) {
                    TAFLogger.debug("Initiating Chrome Driver @ LINUX 64Bits");
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
                TAFLogger.info("Chrome browser");
                break;
            case "EDGE":
                if (OSDetection.isWindows()) {
                    TAFLogger.debug("initiating Edge Driver @ Windows");
                    final File file = new File("C:\\drivers\\MicrosoftWebDriver.exe");
                    System.setProperty("webdriver.edge.driver", file.getAbsolutePath());
                } else {
                    TAFLogger.info("Not a windows machine. Please start up the the driver in selenium server");
                    throw new Exception("Not a windows machine. Please start up the the driver in selenium server");
                }
                capability = DesiredCapabilities.edge();
                capability.setJavascriptEnabled(true);
                TAFLogger.info("MS EDGE browser");
                break;
            case "IE":
                if (OSDetection.isWindows()) {
                    TAFLogger.debug("initiating IE Driver @ Windows 32");
                    final File file = new File("C:\\drivers\\IEDriverServer.exe");
                    System.setProperty("webdriver.ie.driver", file.getAbsolutePath());
                } else {
                    TAFLogger.info("Not a windows machine. Please start up the the driver in selenium server");
                    throw new Exception("Not a windows machine. Please start up the the driver in selenium server");
                }
                capability = DesiredCapabilities.internetExplorer();
                capability.setJavascriptEnabled(true);
                capability.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
                TAFLogger.info("MS IE browser");
                break;

            case "FIREFOX":
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
                TAFLogger.info("Firefox browser and Gecko driver loaded " + geckoDriver.getAbsolutePath());
                break;
            default:
                throw new Exception("Choose browserType in taf.properties file!");
        }

        RemoteWebDriver driver = new RemoteWebDriver(driverURL, capability);
        TAFLogger.info("TAFSelenium initialized!");
        m_driver.set(driver);
        m_driver.get().manage().deleteAllCookies();
        m_driver.get().manage().window().maximize();
    }

    /**
     * Function to get driver instance
     *
     * @return driver
     */
    public WebDriver getDriver() {
        return m_driver.get();
    }
}