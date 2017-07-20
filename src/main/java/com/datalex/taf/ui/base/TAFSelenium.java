package com.datalex.taf.ui.base;

import com.datalex.taf.core.helpers.PathConstants;
import com.datalex.taf.core.readers.property.LoadProperties;
import com.datalex.taf.core.readers.property.TAFProperties;
import com.datalex.taf.core.utilities.OSDetection;
import org.apache.logging.log4j.LogManager;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * TAFSelenium class
 *
 * @author Aleksandar Vulovic
 */
public class TAFSelenium {

    private static final org.apache.logging.log4j.Logger TAFLogger = LogManager.getLogger(TAFSelenium.class);
    private static ThreadLocal<RemoteWebDriver> m_driver = new ThreadLocal<RemoteWebDriver>();
    private static final int MAX_SCRIPT_RUN_TIME = 600;
    private String testCaseIdUni;

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
                //File file = new File("firebug-1.7.3-fx.xpi");
                FirefoxProfile m_driverProfile = new FirefoxProfile();

                // if-block for an enabling modify header
                if (TAFProperties.getModifyHeader().toUpperCase().equals("ON")) {
                    m_driverProfile = initModifyHeadersWithParamFF(m_driverProfile, TAFProperties.getHeaderParameterName(),
                            defineWJAloginWhenModifyHeaders());
                }

                if (TAFProperties.isFirebugProfileAttach()) {
                    File fileFireBug = new File("Plugins/firebug-1.13.0a6.xpi");
                    File fileAddOn = new File("Plugins/firepath-0.9.7-fx.xpi");
                    m_driverProfile.addExtension(fileFireBug);
                    m_driverProfile.setPreference("extensions.firebug.currentVersion", "1.13.0a6.xpi"); // Avoid startup screen
                    m_driverProfile.addExtension(fileAddOn);
                }
                m_driverProfile.setEnableNativeEvents(true);

                //Trying to set network.http.phishy-userpass-length to 255 to disable FF prmts while http auth
                try {
                    m_driverProfile.setPreference("network.http.phishy-userpass-length", Integer.parseInt("255"));

                } catch (Exception e) {
                    TAFLogger.error("Couldn't set network.http.phishy-userpass-length");
                    TAFLogger.info(e.getMessage());
                }
                m_driverProfile.setPreference("dom.max_script_run_time", MAX_SCRIPT_RUN_TIME);
                m_driverProfile.setPreference("dom.max_chrome_script_run_time", MAX_SCRIPT_RUN_TIME);

                File geckoDriver;
                if (OSDetection.isWindows()) {
                    geckoDriver = new File("C:\\drivers\\geckodriver.exe");
                } else {
                    geckoDriver = new File("/home/pstuser/drivers/geckodriver");
                }

                TAFLogger.info(geckoDriver.getAbsolutePath());
                System.setProperty("webdriver.gecko.driver", geckoDriver.getAbsolutePath());
                capability = DesiredCapabilities.firefox();
                capability.setBrowserName("firefox");
                capability.setPlatform(Platform.ANY);
                capability.setCapability(FirefoxDriver.MARIONETTE, true);
                capability.setJavascriptEnabled(true);
                capability.setCapability(FirefoxDriver.PROFILE, m_driverProfile);
                TAFLogger.info("Firefox browser");
                break;
            default:
                throw new Exception("Choose browserType in taf.properties file!");
        }

        RemoteWebDriver driver = new RemoteWebDriver(driverURL, capability);
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

    /**
     * Method for configuring modify header extension
     *
     * @param ffProfile profile to modify
     * @param name      header parameter name
     * @param value     header parameter value
     * @return FirefoxProfile with modified header or null in case of failure
     */

    private static FirefoxProfile initModifyHeadersWithParamFF(FirefoxProfile ffProfile, String name, String value) {
        try {
            TAFLogger.debug("FFProfile modification");

            //Connect extension to FF
            String xpiPath = "modify_headers-0.7.1.1-fx.xpi";
            if (OSDetection.isUnix()) {
                xpiPath = "./modify_headers-0.7.1.1-fx.xpi";
            }
            TAFLogger.debug("Adding " + xpiPath);
            File fileXpi = new File(xpiPath);
            ffProfile.addExtension(fileXpi);

            TAFLogger.debug("Modify header with parameter: " + name + " and value: " + value);

            //Configure extension
            ffProfile.setPreference("modifyheaders.config.active", true);
            ffProfile.setPreference("modifyheaders.config.alwaysOn", true);
            ffProfile.setPreference("modifyheaders.headers.count", 1);
            ffProfile.setPreference("modifyheaders.headers.action0", "Add");
            ffProfile.setPreference("modifyheaders.headers.name0", name);
            ffProfile.setPreference("modifyheaders.headers.value0", value);
            ffProfile.setPreference("modifyheaders.headers.enabled0", true);

            TAFLogger.debug("ModifyHeaders init ok");

            return ffProfile;
        } catch (Exception e) {
            TAFLogger.error("Couldn't connect modifyHeaders xpi to Firefox");
            TAFLogger.info(e.getMessage());

            StringBuilder sb = new StringBuilder();

            for (StackTraceElement element : e.getStackTrace()) {
                sb.append(element.toString());
                sb.append("\n");
            }
            TAFLogger.error(sb.toString());
            return null;
        }
    }

    private String defineWJAloginWhenModifyHeaders() {
        try {
            TAFLogger.debug("Define if WESTJET user should be read from a csv file");
            if (TAFProperties.getPROJECTNAME().equalsIgnoreCase("westjet")) {
                Map<String, String> usersMap = new HashMap<String, String>();

                String userNameToFind = "user" + testCaseIdUni;

                String usersFileName = PathConstants.TEST_SCENARIOS_FOLDER
                        + "WestJet" + PathConstants.fS + "WestJetUsers.csv";

                TAFLogger.debug("Using file " + usersFileName);

                BufferedReader userNamesFile = new BufferedReader(new FileReader(usersFileName));
                File fUsers = new File(usersFileName);
                if (!fUsers.exists()) {
                    TAFLogger.debug("Users file for WESTJET project does NOT exist. Using default username");
                    return TAFProperties.getHeaderParameterValue();
                }
                String str;

                TAFLogger.debug("Looking for " + userNameToFind);

                while ((str = userNamesFile.readLine()) != null) {
                    int i = str.indexOf(",");
                    if (i > 0) {
                        String name = str.substring(0, i);
                        String value = str.substring(i + 1);
                        usersMap.put(name.trim(), value.trim());
                        TAFLogger.debug("Value: " + value);
                    }
                }

                userNamesFile.close();

                if (!(usersMap.get(userNameToFind) == null)) {
                    TAFLogger.debug("Return user: " + userNameToFind);
                    return usersMap.get(userNameToFind);
                }
            }

            return TAFProperties.getHeaderParameterValue();
        } catch (Exception e) {
            TAFLogger.debug(e.getMessage());
            return TAFProperties.getHeaderParameterValue();
        }
    }
}