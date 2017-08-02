package com.datalex.taf.ui.po.confirmationpage;

import com.datalex.taf.ui.helpers.ElementHelper;
import com.datalex.taf.ui.helpers.Utils;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.io.IOException;

/**
 * Confirmation page class
 *
 * @author Aleksandar Vulovic
 */
@Log4j2
public class ConfirmationPage implements IConfirmationPage {
    private WebDriver driver;
    private ElementHelper elementHelper;

    @FindBy(className = "colConfirmNum")
    public WebElement confirmationNumber;

    public ConfirmationPage(WebDriver driver) {
        log.info("Initiating Confirmation page...");
        this.driver = driver;
        elementHelper = new ElementHelper(driver);
        elementHelper.waitForPresenceOfElementLocated(By.id("pgConfirmation"));
        PageFactory.initElements(driver, this);
    }

    public String getPNR() throws IOException {
        elementHelper.waitForElementDisplayed(confirmationNumber);
        String pnr = confirmationNumber.getText().trim();
        new Utils().savePNRinCSV(pnr);
        log.info("PNR is: " + pnr);
        return pnr;
    }
}