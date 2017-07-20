package com.datalex.taf.ui.po.selectionpage;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/**
 * SelectionPage class
 */
public class SelectionPage implements ISelectionPage {

    private WebDriver driver;

    public SelectionPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(id = "pgButtonNext")
    public WebElement buttonSelection;

    @Override
    public void clickButtonSelection() {
        buttonSelection.click();
    }
}
