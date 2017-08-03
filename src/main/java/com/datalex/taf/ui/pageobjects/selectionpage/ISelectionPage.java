package com.datalex.taf.ui.pageobjects.selectionpage;

import com.datalex.taf.ui.pageobjects.summarypage.SummaryPage;
import org.openqa.selenium.WebElement;

/**
 * Selection Page interface
 *
 * @author Aleksandar Vulovic
 */
public interface ISelectionPage {

    void selectOutboundFareFamily(String fareFamily);

    void selectInboundFareFamily(String fareFamily);

    WebElement getSelectFlightRadioLocator(String fareFamilyAlias, int blockNumber);

    String getFFAlias(String fareFamilyName);

    SummaryPage doSelection();
}
