package com.datalex.taf.ui.helpers;

import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.List;

/**
 * ElementHelper class
 *
 * @author Aleksandar Vulovic
 */
@Log4j2
public class ElementHelper {
    private WebDriverWait wait;

    /**
     * Empty constructor for methods that are NOT using WebDriverWait
     */
    public ElementHelper() {
    }

    /**
     * Constructor for methods that are using WebDriverWait
     *
     * @param driver WebDriver
     */
    public ElementHelper(WebDriver driver) {
        this.wait = new WebDriverWait(driver, 30);
    }

    /**
     * Constructor for methods that are using WebDriverWait with dynamic wait time
     *
     * @param driver   WebDriver
     * @param waitTime wait time in seconds
     */
    public ElementHelper(WebDriver driver, int waitTime) {
        this.wait = new WebDriverWait(driver, waitTime);
    }

    /**
     * Wait for element to be displayed on page
     *
     * @param element WebElement
     */
    public void waitForElementDisplayed(WebElement element) {
        log.debug("Waiting for element displayed - Element: " + element.toString());
        wait.until(ExpectedConditions.visibilityOf(element));
    }

    /**
     * Method to check is element enabled
     *
     * @param element WebElement
     * @return boolean
     */
    public boolean isElementEnabled(WebElement element) {
        try {
            return element.isEnabled();
        } catch (Exception e) {
            log.error(e);
        }
        return false;
    }

    /**
     * Method to check is element displayed
     *
     * @param element WebElement
     * @return boolean
     */
    public boolean isElementDisplayed(WebElement element) {
        boolean isDisplayed = false;
        String tagName = element.getTagName();
        if (!tagName.isEmpty()) {
            isDisplayed = element.isDisplayed();
        }
        return isDisplayed;
    }

    /**
     * Method to check is element selected
     *
     * @param element WebElement
     * @return boolean
     */
    public boolean isElementSelected(WebElement element) {
        boolean isSelected = false;
        try {
            isSelected = element.isSelected();
        } catch (Exception e) {
            log.error(e);
        }
        return isSelected;
    }

    /**
     * Method to check is element checked
     *
     * @param element WebElement
     * @return boolean
     */
    public boolean isElementChecked(WebElement element) {
        try {
            String attribute = element.getAttribute("checked");
            return attribute.contains("true");
        } catch (Exception e) {
            log.error(e);
        }
        return false;
    }

    /**
     * Method to get first available option from drop down
     *
     * @param element WebElement
     * @return String with first option name
     */
    public String getFirstAvailableOption(WebElement element) {
        List<String> options = getAllAvailableOptions(element);
        return options.get(1);
    }

    /**
     * Method to get last available option from drop down
     *
     * @param element WebElement
     * @return String with last option name
     */
    public String getLastAvailableOption(WebElement element) {
        List<String> options = getAllAvailableOptions(element);
        return options.get(options.size() - 1);
    }

    /**
     * Method returning selected option text
     *
     * @param element WebElement
     * @return String with selected option
     */
    public String getSelectedOptionText(WebElement element) {
        Select select = new Select(element);
        WebElement option = select.getFirstSelectedOption();
        return option.getText().trim();
    }

    /**
     * Method to get number of elements from WebElement List
     *
     * @param elements WebElement List
     * @return number of elements
     */
    public int getNumberOfElements(List<WebElement> elements) {
        return elements.size();
    }

    /**
     * Select option from drop down by text
     *
     * @param element WebElement
     * @param text    text to select
     */
    public void selectOptionByText(WebElement element, String text) {
        if (text == null) {
            return;
        }
        try {
            Select select = new Select(element);
            select.selectByVisibleText(text);
        } catch (Exception e) {
            log.error(e);
        }
    }

    /**
     * Select option from drop down by value
     *
     * @param element WebElement
     * @param value   value to select
     */
    public void selectOptionByValue(WebElement element, String value) {
        log.debug("Selecting Option :" + value + " in:" + element.toString());
        if (value == null) {
            return;
        }
        try {
            Select select = new Select(element);
            select.selectByValue(value);
        } catch (Exception e) {
            log.error(e);
        }
    }

    /**
     * Select option by text in parent element
     *
     * @param parent   parent element
     * @param children list of children elements
     * @param text     test to select
     */
    public void selectOptionByText(WebElement parent, List<WebElement> children, String text) {
        parent.click();
        new Utils().waitTime500ms();
        for (WebElement option : children) {
            log.debug("Current option: " + option.getText());
            if (text.equals(option.getText())) {
                log.debug("Click option: " + option.getText());
                option.click();
                new Utils().waitTime500ms();
                return;
            }
        }
    }

    /**
     * Get list of all available options
     *
     * @param element WebElement
     * @return List of available options
     */
    public List<String> getAllAvailableOptions(WebElement element) {
        Select select = new Select(element);
        List<WebElement> options = select.getOptions();
        List<String> captions = new ArrayList<>();
        for (WebElement option : options) {
            captions.add(option.getText().trim());
        }
        return captions;
    }

    /**
     * Wait for element to be clickable
     *
     * @param element WebElement
     */
    public void waitForElementToBeClickable(WebElement element) {
        wait.until(ExpectedConditions.elementToBeClickable(element));
    }

    /**
     * Wait for element to be clickable
     *
     * @param element By element
     */
    public void waitForElementToBeClickable(By element) {
        wait.until(ExpectedConditions.elementToBeClickable(element));
    }

    /**
     * Wait for presence of element located on page
     *
     * @param element WebElement
     */
    public void waitForPresenceOfElementLocated(By element) {
        wait.until(ExpectedConditions.presenceOfElementLocated(element));
    }

    /**
     * Wait for frame to be available and switch to it
     *
     * @param element String element: "ifrmLogin"
     */
    public void waitForFrameToBeAvailableAndSwitchToIt(String element) {
        wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(element));
    }

    /**
     * Function to execute Javascript using WebDriver
     * <p>
     * To use it here are some following example:
     * <p>
     * driver.executeScript("window.scrollBy(0,150)");
     * driver.executeScript("document.getElementById('" + elementId + "').value").toString();
     *
     * @param driver      Webdriver
     * @param scriptToRun script
     * @return JavaScript object
     */
    public Object executeScript(WebDriver driver, String scriptToRun) {
        return ((JavascriptExecutor) driver).executeScript(scriptToRun);
    }
}