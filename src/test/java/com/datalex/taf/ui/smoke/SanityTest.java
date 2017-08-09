package com.datalex.taf.ui.smoke;

import com.datalex.taf.ui.base.TAFSelenium;
import com.datalex.taf.ui.data.TestData;
import com.datalex.taf.ui.pageobjects.searchpage.SearchPage;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Created by jossie.saul on 09/08/2017.
 */
@Test(groups = {"sanity"})
public class SanityTest {

    @Test(dataProvider = "Data", description = "Basic End to End booking flow")
    public void flightSearchPageReadyTest() throws Exception {
        new TAFSelenium().initDriver();
        WebDriver driver = TAFSelenium.getDriver();
        SearchPage searchPage = new SearchPage(driver);
        Assert.assertEquals(searchPage.isSearchPageVisible(), true);
    }

}
