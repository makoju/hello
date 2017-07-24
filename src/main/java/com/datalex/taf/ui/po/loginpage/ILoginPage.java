package com.datalex.taf.ui.po.loginpage;

import com.datalex.taf.ui.data.TestData;
import com.datalex.taf.ui.po.searchpage.SearchPage;

/**
 * Login Page interface
 *
 * @author Aleksandar Vulovic
 */
public interface ILoginPage {

    SearchPage login(TestData testData);
}
