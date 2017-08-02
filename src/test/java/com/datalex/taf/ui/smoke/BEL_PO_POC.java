package com.datalex.taf.ui.smoke;

import com.datalex.taf.ui.base.TAFSelenium;
import com.datalex.taf.ui.data.TestData;
import com.datalex.taf.ui.helpers.ScreenshotHelper;
import com.datalex.taf.ui.pageobjects.confirmationpage.ConfirmationPage;
import com.datalex.taf.ui.pageobjects.loginpage.LoginPage;
import com.datalex.taf.ui.pageobjects.passengerspage.PassengersPage;
import com.datalex.taf.ui.pageobjects.paymentpage.PaymentPage;
import com.datalex.taf.ui.pageobjects.searchpage.SearchPage;
import com.datalex.taf.ui.pageobjects.seatspage.SeatsPage;
import com.datalex.taf.ui.pageobjects.selectionpage.SelectionPage;
import com.datalex.taf.ui.pageobjects.summarypage.SummaryPage;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
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

    @DataProvider(name = "Data", parallel = true)
    public Object[][] data() throws Exception {
        return mapDataFromCSVToObject("Smoke");
    }

    @Test(dataProvider = "Data", description = "Basic End to End booking flow")
    public void basicEndToEndBookingFlow(TestData testData) throws Exception {
        new TAFSelenium().initDriver();
        WebDriver driver = TAFSelenium.getDriver();
        //Login page actions
        SearchPage searchPage = new SearchPage(driver);
        LoginPage loginPage = searchPage.goToLoginPage();
        searchPage = loginPage.login(testData);
        //Search page actions
        SelectionPage selectionPage = searchPage.doFlightSearch(testData);
        //Selection page actions
        selectionPage.selectFareFamily(testData);
        //Summary page actions
        SummaryPage summaryPage = selectionPage.doSelection();
        //Passengers page actions
        PassengersPage passengersPage = summaryPage.goToPassengersPage();
        passengersPage.fillPassengersPage(testData);
        //Seats page actions
        SeatsPage seatsPage = passengersPage.goToSeatSelect();
        PaymentPage paymentPage = seatsPage.skipSeatSelection(testData);
        //Payment page actions
        ConfirmationPage confirmationPage = paymentPage.populatePaymentPage(testData);
        //Assert PNR is present
        Assert.assertNotNull(confirmationPage.getPNR());
    }
}