package com.sn.dataproviders;
/**
 * This file is for reference only. 
 */

import org.testng.annotations.DataProvider;
/**
 * 
 * @author Pankaj Tarar
 *
 */
public final class BrowserStackCapabilities {
/**
 * 
 */
    private BrowserStackCapabilities() {

    }

    /**
     * @return BrowserStackCapabilities
     */
    @DataProvider(name = "BrowserStackCapabilities")
    public static Object[][] browserStackCapabilities() {

        return new Object[][] {

            { "windows", "7", "chrome", "78", "true", "3.11", "" } };

    }
}
