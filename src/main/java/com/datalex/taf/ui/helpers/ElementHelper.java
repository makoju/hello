package com.datalex.taf.ui.helpers;

import org.apache.logging.log4j.LogManager;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import java.util.ArrayList;
import java.util.List;

/**
 * ElementHelper class
 *
 * @author Aleksandar Vulovic
 */
public class ElementHelper {

    private static org.apache.logging.log4j.Logger TAFLogger = LogManager.getLogger(ElementHelper.class);

    public void waitForElementDisplayed(WebElement element) {
        final int timeMs = 15000;
        for (int i = 500; i < timeMs; i += 100) {
            TAFLogger.info("Waiting for element displayed (ms): " + i);
            new Utils().waitTime500ms();
            if (isElementDisplayed(element)) {
                TAFLogger.info(String.format("ElementHelper is displayed after %d ms", i));
                return;
            }
        }
        TAFLogger.error(String.format("ElementHelper is not displayed after %d seconds", timeMs / 1000));
    }

    public boolean isElementEnabled(WebElement element) {
        try {
            return element.isEnabled();
        } catch (Exception e) {
            TAFLogger.error(e.getMessage());
        }
        return false;
    }

    public boolean isElementDisplayed(WebElement element) {
        boolean isDisplayed = false;
        try {
            String tagName = element.getTagName();
            if (!tagName.isEmpty()) {
                isDisplayed = element.isDisplayed();
            }
        } catch (Exception e) {
            TAFLogger.error(e.getMessage());
        }
        return isDisplayed;
    }

    public boolean isElementSelected(WebElement element) {
        boolean isSelected = false;
        try {
            isSelected = element.isSelected();
        } catch (Exception e) {
            TAFLogger.error(e.getMessage());
        }
        return isSelected;
    }

    public boolean isElementChecked(WebElement element) {
        try {
            String attribute = element.getAttribute("checked");
            return attribute.contains("true");
        } catch (Exception e) {
            TAFLogger.error(e.getMessage());
        }
        return false;
    }

    public String getFirstAvailableOption(WebElement element) {
        List<String> options = getAllAvailableOptions(element);
        return options.get(1);
    }

    public String getLastAvailableOption(WebElement element) {
        List<String> options = getAllAvailableOptions(element);
        return options.get(options.size() - 1);
    }

    public String getSelectedOptionText(WebElement element) {
        Select select = new Select(element);
        WebElement option = select.getFirstSelectedOption();
        return option.getText().trim();
    }

    public int getNumberOfElements(List<WebElement> elements) {
        return elements.size();
    }

    public void selectOptionByText(WebElement element, String text) {
        if (text == null) {
            return;
        }
        try {
            Select select = new Select(element);
            select.selectByVisibleText(text);
        } catch (Exception e) {
            TAFLogger.error(e.getMessage());
        }
    }

    public void selectOptionByText(WebElement parent, List<WebElement> children, String text) {
        parent.click();
        new Utils().waitTime500ms();
        for (WebElement option : children) {
            TAFLogger.debug("Current option: " + option.getText());
            if (text.equals(option.getText())) {
                TAFLogger.info("Click option: " + option.getText());
                option.click();
                new Utils().waitTime500ms();
                return;
            }
        }
    }

    public List<String> getAllAvailableOptions(WebElement element) {
        Select select = new Select(element);
        List<WebElement> options = select.getOptions();
        List<String> captions = new ArrayList<String>();
        for (WebElement option : options) {
            captions.add(option.getText().trim());
        }
        return captions;
    }
}