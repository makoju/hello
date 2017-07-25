package com.datalex.taf.ui.helpers.constants;

/**
 * Class contains constants that uses for the different application settings
 */
public final class SettingsConstants {
    /*****************************************************************************************************************************
     * EXECUTION
     ****************************************************************************************************************************/
    public static final String SKIP_WORD = "SKIP_STEP";
    /****************************************************************************************************************************
     * LOCALE
     ****************************************************************************************************************************/
    /**
     * en-American English
     */
    public static final int EN_AMERICAN = 0;

    /**
     * fr-French
     */
    public static final int FR_FRENCH = 1;

    /**
     * de-German
     */
    public static final int DE_GERMAN = 2;

    /**
     * pt-Portuguese
     */
    public static final int PT_PORTUG = 3;

    /**
     * es-Spanish
     */
    public static final int ES_SPANISH = 4;
    /****************************************************************************************************************************
     * CUSTOMERS
     ****************************************************************************************************************************/
    /**
     * TDP Product
     */
    public static final int PRODUCT_CUST = 100;
    /**
     * Frontier
     */
    public static final int FRONTIER_CUST = 101;
    /**
     * COPA
     */
    public static final int COPA_CUST = 102;
    /**
     * PAL
     */
    public static final int PAL_CUST = 103;
    /**
     * AIR CHINA
     */
    public static final int CHINA_CUST = 104;
    /**
     * SPAN AIR
     */
    public static final int SPAIN_CUST = 105;
    /**
     * STA GLOBAL
     */
    public static final int STA_GLOBAL_CUST = 106;
    /**
     * Malta
     */
    public static final int MALTA_CUST = 109;
    /**
     * SITA
     */
    public static final int SITA_CUST = 110;
    /**
     * MalaysiaAirlines
     */
    public static final int MAS_CUST = 111;
    /**
     * WestJet
     */
    public static final int WJA_CUST = 112;
    /**
     * AirPacific
     */
    public static final int FJI_CUST = 120;
    /**
     * GarudaIndonesia
     */
    public static final int GIA_CUST = 121;
    /**
     * SouthAfricanAirways
     */
    public static final int SAA_CUST = 122;
    /**
     * Oman Air
     */
    public static final int OMA_CUST = 123;
    /**
     * MPH Customer
     */
    public static final int MPH_CUST = 124;
    /**
     * Air India Customer
     */
    public static final int AIC_CUST = 125;
    /**
     * Virgin Australia Customer
     */
    public static final int VAU_CUST = 126;
    /**
     * HP Trips Customer
     */
    public static final int HPT_CUST = 127;
    /**
     * Abacus Agent Customer
     */
    public static final int ABS_CUST = 128;
    /**
     * Virgin Atlantic Customer
     */
    public static final int VIR_CUST = 129;
    /**
     * Brussels Airline Customer
     */
    public static final int BEL_CUST = 130;
    /**
     * Jet Blue Airline Customer
     */
    public static final int JBU_CUST = 131;
    /**
     * Air Transat (ST) Airline Customer
     */
    public static final int ST_CUST = 132;
    /**
     * Abacus Customer
     */
    public static final int ABACUS_CUST = 133;
    /**
     * PCI System Test
     */
    public static final int PCI_SYSTEM_TEST = 134;
    /**
     * Edelweiss System Test
     */
    public static final int EDW_CUST = 135;
    /**
     * AerLingus
     */
    public static final int EIN_CUST = 136;
    /**
     * AerLingus
     */
    public static final int SWR_CUST = 137;
    /**
     * System Testing
     */
    public static final int DEV_SYSTEM_TEST = 998;
    /**
     * System Testing
     */
    public static final int SYSTEM_TEST = 999;

    /**
     * Customers
     */
    public static final int[] CUSTOMERS = {PRODUCT_CUST, FRONTIER_CUST, COPA_CUST, PAL_CUST, CHINA_CUST,
            SPAIN_CUST, STA_GLOBAL_CUST, MALTA_CUST, SITA_CUST, MAS_CUST, WJA_CUST,
            FJI_CUST, GIA_CUST, SAA_CUST, OMA_CUST, MPH_CUST, AIC_CUST, VAU_CUST,
            HPT_CUST, ABS_CUST, VIR_CUST, BEL_CUST, JBU_CUST, PCI_SYSTEM_TEST,
            EDW_CUST, EIN_CUST, DEV_SYSTEM_TEST, SYSTEM_TEST};
    /****************************************************************************************************************************
     * Pricing Display Modes
     ****************************************************************************************************************************/
    /**
     * Total fare for all passengers
     */
    public static final int TOTAL_FARE = 1;

    /**
     * Total fare for all passengers, excluding taxes
     */
    public static final int TOTAL_FARE_EXCL_TAXES = 2;

    /**
     * Fare for one passenger
     */
    public static final int PER_PAX_FARE = 3;

    /**
     * Fare for one passenger, excluding taxes
     */
    public static final int PER_PAX_FARE_EXCL_TAXES = 4;
    /****************************************************************************************************************************
     * Side Bar Configuration values
     ****************************************************************************************************************************/
    /**
     * the side bar is off
     */
    public static final String OFF_BAR = "OFF";

    /**
     * the side bar is on and is on the left of the page
     */
    public static final String LEFT_BAR = "LEFT";

    /**
     * the side bar is on and is on the right of the page
     */
    public static final String RIGHT_BAR = "RIGHT";
/*********************************************************************************************************************************
 * TYPES OF AIR
 *********************************************************************************************************************************/
    /**
     * the indicator of the currency type for domestic air
     */
    public static final String DOMESTIC_AIR = "DOMESTIC_AIR";

    /**
     * the indicator of the currency type for the international air
     */
    public static final String INTERNATIONAL_AIR = "INTERNATIONAL_AIR";

    /**
     * the indicator of an unknown currency type
     */
    public static final String UNKNOWN_AIR = "UNKNOWN_AIR";
/*********************************************************************************************************************************
 * STATES OF PROMOTIONS
 *********************************************************************************************************************************/
    /**
     * the active status
     */
    public static final String ACTIVE_PROMO = "ACTIVE";

    /**
     * the inactive status
     */
    public static final String INACTIVE_PROMO = "INACTIVE";

    /**
     * the retired status
     */
    public static final String RET_PROMO = "RETIRED";

/*********************************************************************************************************************************/
    /**
     * the type of discount By Percentage
     */
    public static final int BY_PERCENTAGE = 1;

    /**
     * the type of discount By Amount
     */
    public static final int BY_AMOUNT = 2;

    /**
     * the type of discount By Function
     * this one has been added to reserve item for the possible in future functionality
     */
    public static final int BY_FUNCTION = 3;
/*********************************************************************************************************************************
 * PRICING TYPES
 * ******************************************************************************************************************************/
    /**
     * ATPCO Cat 35 Pricing type
     */
    public static final int ATPCO_CAT_35 = 31;

    /**
     * ATPCO Cat 25 Pricing type
     */
    public static final int ATPCO_CAT_25 = 32;

/**********************************************************************************************************************************
 * PROMOTION TYPE BY PRICING
 *********************************************************************************************************************************/
    /**
     * Discount Amount sets on Host
     */
    public static final String HOST_DISCOUNT = "HD";

    /**
     * Discount Amount sets in MUI
     */
    public static final String MUI_DISCOUNT = "MD";
/*********************************************************************************************************************************
 * POS RELOAD UTILITY
 *********************************************************************************************************************************/
    /**
     * The text that indicates about POS reload has been done successfully
     */
    public static final String RELOAD_SUCCESS = "cache has been successfully cleared";

/**********************************************************************************************************************************
 * CALENDAR TYPE
 *********************************************************************************************************************************/
    /**
     * Effective calendar
     */
    public static final String EFFECTIVE_CALENDAR = "EF";

    /**
     * BlackOut Calendar
     */
    public static final String BLACKOUT_CALENDAR = "B";
}