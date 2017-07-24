package com.datalex.taf.ui.po.summarypage;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

/**
 * Summary Page class
 */
public class SummaryPage {

    private WebDriver driver;

    public SummaryPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }
}
