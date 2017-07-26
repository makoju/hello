package com.datalex.taf.ui.po.searchpage;

import com.datalex.taf.ui.po.exceptions.SearchPageException;
import com.datalex.taf.ui.po.selectionpage.SelectionPage;

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