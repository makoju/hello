package com.datalex.taf.ui.pageobjects.searchpage;

import com.datalex.taf.ui.pageobjects.exceptions.SearchPageException;
import com.datalex.taf.ui.pageobjects.selectionpage.SelectionPage;

/**
 * SearchPage interface
 *
 * @author Aleksandar Vulovic
 */
public interface ISearchPage {

    void setOriginLocation(String code) throws SearchPageException;

    void setDestinationLocation(String code) throws SearchPageException;

    SelectionPage doSearch();
}