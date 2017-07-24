package com.datalex.taf.ui.po.selectionpage;

import com.datalex.taf.ui.po.summarypage.SummaryPage;
import org.openqa.selenium.WebElement;

/**
 * Selection Page interface
 */
public interface ISelectionPage {

    void selectInboundFareFamily(String fareFamily);

    void selectReturnFareFamily(String fareFamily);

    WebElement getSelectFlightRadioLocator(String fareFamilyAlias, int blockNumber);

    String getFFAlias(String fareFamilyName);

    SummaryPage doSelection();
}
