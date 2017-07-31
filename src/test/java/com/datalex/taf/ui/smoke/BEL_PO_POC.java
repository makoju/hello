package com.datalex.taf.ui.smoke;

import com.datalex.taf.ui.base.TAFSelenium;
import com.datalex.taf.ui.data.TestData;
import com.datalex.taf.ui.helpers.ScreenshotHelper;
import com.datalex.taf.ui.po.loginpage.LoginPage;
import com.datalex.taf.ui.po.passengerspage.PassengersPage;
import com.datalex.taf.ui.po.paymentpage.PaymentPage;
import com.datalex.taf.ui.po.searchpage.SearchPage;
import com.datalex.taf.ui.po.seatspage.SeatsPage;
import com.datalex.taf.ui.po.selectionpage.SelectionPage;
import com.datalex.taf.ui.po.summarypage.SummaryPage;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static com.datalex.taf.ui.data.DataHelper.mapDataFromCSVToObject;

/**
 * BRU PageObject POC Test class
 *
 * @author Aleksandar Vulovic
 */
@Test(groups = {"smoke"})
public class BEL_PO_POC {

    @AfterMethod
    public void tearDown(ITestResult result) throws Exception {
        if (!result.isSuccess())
            new ScreenshotHelper().takeScreenshot(TAFSelenium.getDriver());
        TAFSelenium.getDriver().quit();
    }

    @DataProvider(name = "Data", parallel = false)
    public Object[][] data() throws Exception {
        return mapDataFromCSVToObject("Smoke");
    }

    @Test(dataProvider = "Data", description = "POC Test example")
    public void searchPageTest(TestData testData) throws Exception {
        TAFSelenium.initDriver();
        WebDriver driver = TAFSelenium.getDriver();
        //Login page actions
        SearchPage searchPage = new SearchPage(driver);
        LoginPage loginPage = searchPage.goToLoginPage();
        searchPage = loginPage.login(testData);
        //Search page actions
        SelectionPage selectionPage = searchPage.doFlightSearch(testData);
        //Selection page actions
        selectionPage.selectOutboundFareFamily(testData.getFareFamily());
        if (("RT").equalsIgnoreCase(testData.getTripType()))
            selectionPage.selectInboundFareFamily(testData.getFareFamily());
        //Summary page actions
        SummaryPage summaryPage = selectionPage.doSelection();
        //Passengers page actions
        PassengersPage passengersPage = summaryPage.goToPassengersPage();
        passengersPage.fillTravellersPage(testData);
        //Seats page actions
        SeatsPage seatsPage = passengersPage.goToSeatSelect();
        PaymentPage paymentPage = seatsPage.skipSeatSelection();
        //Payment page actions
        paymentPage.populatePaymentPage(testData);
    }
}