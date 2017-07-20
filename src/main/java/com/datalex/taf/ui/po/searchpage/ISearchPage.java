package com.datalex.taf.ui.po.searchpage;

import com.datalex.taf.ui.po.selectionpage.SelectionPage;

/**
 * SearchPage interface
 */
public interface ISearchPage {

    void setOriginLocation(String code) throws Exception;

    void setDestinationLocation(String code) throws Exception;

    void setSearchType(String searchType) throws Exception;

    SelectionPage doSearch();
}