package com.sn.listeners;

import java.io.File;
import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.sn.apt.util.AutoPilotUtil;
import com.sn.config.BaseConfig;
import com.sn.utils.CommonUtils;

/**
 * @author Pankaj Tarar
 */
public class TestListener extends BaseConfig implements ITestListener {

    private static final Logger LOGGER = LogManager.getLogger(TestListener.class);

    @Override
    public void onStart(final ITestContext context) {
        LOGGER.info("*** Test Suite " + context.getName() + " started ***");

        ExtentTestManager.getTest();
    }

    @Override
    public void onFinish(final ITestContext context) {
        LOGGER.info(("*** Test Suite " + context.getName() + " ending ***"));
        ExtentTestManager.endTest();
        ExtentManager.getInstance().flush();
    }

    @Override
    public void onTestStart(final ITestResult result) {
        LOGGER.info(("*** Running test method " + result.getMethod().getMethodName() + "..."));

        final String name =
            result.getInstanceName().substring(18) + " :: " + result.getMethod().getMethodName();
        ExtentTestManager.startTest(name);

    }

    @Override
    public void onTestSuccess(final ITestResult result) {
        LOGGER.info("*** Executed " + result.getMethod().getMethodName() + " test successfully...");
        ExtentTestManager.getTest().log(Status.PASS, "Test Completed");
    }

    @Override
    public void onTestFailure(final ITestResult result) {
        LOGGER.info("*** Test execution " + result.getMethod().getMethodName() + " failed...");

        try {

            final String cDate = AutoPilotUtil.currentDate("yyyyMMddHHmm");
            AutoPilotUtil.getScreenshot(
                getBaseDriver(),
                result.getMethod().getMethodName(),
                CommonUtils.getScreenShotPath());
            ExtentTestManager.getTest().fail(
                "Test Step failed. Screenshot is attached :: ",
                MediaEntityBuilder
                    .createScreenCaptureFromPath(
                        ".\\Screenshots" + File.separator + result.getMethod().getMethodName() + "_"
                            + cDate + ".png")
                    .build());
            ExtentTestManager.getTest().log(
                Status.FAIL,
                "Exception :: " + result.getName() + " FAIL with error " + result.getThrowable());
        } catch (final IOException ie) {
            ExtentTestManager.getTest().log(
                Status.FAIL,
                " Unable to perform operations on test failure in listeners. " + ie.getMessage());
            LOGGER.error(" Unable to perform operations on test failure in listeners. " + ie);
        }
        ExtentTestManager.getTest().log(Status.FAIL, "Test Failed");
    }

    @Override
    public void onTestSkipped(final ITestResult result) {
        LOGGER.info("*** Test " + result.getMethod().getMethodName() + " skipped...");
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(final ITestResult result) {
        LOGGER.info(
            "*** Test failed but within percentage % " + result.getMethod().getMethodName());
    }

}
