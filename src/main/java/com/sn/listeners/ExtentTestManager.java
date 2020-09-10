package com.sn.listeners;

import java.util.HashMap;
import java.util.Map;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;

/**
 * @author Pankaj Tarar
 */
public final class ExtentTestManager {

    @SuppressWarnings("squid:S2293")
    private static Map<Integer, ExtentTest> extentTestMap = new HashMap<Integer, ExtentTest>();

    /**
     * 
     */
    private static ExtentReports extent = ExtentManager.getInstance();

    /**
     * 
     */
    private ExtentTestManager() {

    }

    /**
     * @return getTest
     */
    public static synchronized ExtentTest getTest() {
        return extentTestMap.get((int) (Thread.currentThread().getId()));
    }

    /**
     * 
     */
    public static synchronized void endTest() {
        extent.flush();
    }

    /**
     * @param testName String
     * @return ExtentTest
     */
    public static synchronized ExtentTest startTest(final String testName) {
        final ExtentTest test = extent.createTest(testName);
        extentTestMap.put((int) (Thread.currentThread().getId()), test);
        return test;
    }
}
