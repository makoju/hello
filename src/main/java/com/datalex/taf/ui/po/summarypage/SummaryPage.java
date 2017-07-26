package com.datalex.taf.ui.po.summarypage;

import com.datalex.taf.ui.helpers.ElementHelper;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

/**
 * Summary Page class
 *
 * @author Aleksandar Vulovic
 */
public class SummaryPage {

    private WebDriver driver;

    public SummaryPage(WebDriver driver) {
        this.driver = driver;
        new ElementHelper(driver).waitForPresenceOfElementLocated(By.id("pgItinerarySummary"));
        new ElementHelper(driver).waitForElementToBeClickable(By.id("pgButtonNext"));
        PageFactory.initElements(driver, this);
    }
}