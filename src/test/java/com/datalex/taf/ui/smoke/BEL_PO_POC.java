package com.datalex.taf.ui.smoke;

import com.datalex.taf.ui.base.TAFSelenium;
import com.datalex.taf.ui.data.TestData;
import com.datalex.taf.ui.helpers.ScreenshotHelper;
import com.datalex.taf.ui.po.searchpage.SearchPage;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static com.datalex.taf.ui.data.CSVDataHelper.convertDataToObject;
import static com.datalex.taf.ui.data.CSVDataHelper.readDataFromCSVFile;

/**
 * BRU PageObject POC
 */
@Test(groups = {"regression"})
public class BEL_PO_POC {
    private WebDriver driver;

    @AfterMethod
    public void tearDown(ITestResult result) throws Exception {
        new ScreenshotHelper().takeScreenshot(driver, result);
        driver.quit();
    }

    @DataProvider(name = "CSVData", parallel = false)
    public Object[][] csvData() throws Exception {
        return convertDataToObject(readDataFromCSVFile("Smoke"));
    }

    @Test(dataProvider = "CSVData", description = "SearchPageTest")
    public void searchPageTest(TestData testData) throws Exception {
        driver = new TAFSelenium().getDriver();
        SearchPage searchPage = new SearchPage(driver);
        searchPage.setSearchType(testData.getTripType());
        searchPage.setDestinationLocation(testData.getInputFrom());
        searchPage.setOriginLocation(testData.getInputTo());
    }
}
