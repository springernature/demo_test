package com.sn.listeners;

import java.io.File;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

/**
 * @author Pankaj Tarar
 */

public final class ExtentManager {

    private static final Logger LOGGER = LogManager.getLogger(ExtentManager.class);

    private static ExtentReports extent;

    /**
     * created variable for target directory.
     */
    public static final String TARGET = "target";

    private static String reportFileName = "ExecutionReport" + ".html";

    private static String fileSeperator = System.getProperty("file.separator");

    private static String reportFilepath = System.getProperty("user.dir") + fileSeperator + TARGET;

    private static String reportFileLocation = reportFilepath + fileSeperator + reportFileName;

    /**
     * 
     */
    private ExtentManager() {

    }

    /**
     * @return extent
     */
    public static ExtentReports getInstance() {
        if (extent == null) createInstance();
        return extent;
    }

    /**
     * Create an extent report instance.
     * 
     * @return createInstance
     */
    public static ExtentReports createInstance() {
        final String fileName = getReportPath(reportFilepath);

        final ExtentHtmlReporter htmlReporter = new ExtentHtmlReporter(fileName);
        htmlReporter.config().setChartVisibilityOnOpen(true);
        htmlReporter.config().setTheme(Theme.STANDARD);
        htmlReporter.config().setDocumentTitle(reportFileName);
        htmlReporter.config().setEncoding("utf-8");
        htmlReporter.config().setReportName(reportFileName);
        htmlReporter.config().getClass();

        extent = new ExtentReports();
        extent.attachReporter(htmlReporter);
        // Set environment details
        extent.setSystemInfo("Application Environment", "INT");

        return extent;
    }

    /**
     * Create the report path.
     * 
     * @param path String
     * @return String
     */
    @SuppressWarnings("squid:S4797")
    private static String getReportPath(final String path) {
        final File testDirectory = new File(path);
        if (!testDirectory.exists()) {
            if (testDirectory.mkdir()) {
                LOGGER.info("Directory: " + path + " is created!");
                return reportFileLocation;
            } else {
                LOGGER.info("Failed to create directory: " + path);
                return System.getProperty("user.dir");
            }
        } else {
            LOGGER.info("Directory already exists: " + path);
        }
        return reportFileLocation;
    }

}
