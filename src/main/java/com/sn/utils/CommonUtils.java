package com.sn.utils;
/**
 * This file is for reference only. 
 */

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import com.aventstack.extentreports.Status;
import com.google.common.collect.ImmutableList;
import com.sn.config.BaseConfig;
import com.sn.listeners.ExtentTestManager;

/**
 * @author Pankaj Tarar
 */
public class CommonUtils {

    private static final String FAILED = "FAILED";

    private static final Logger LOGGER = LogManager.getLogger(CommonUtils.class);

    private BaseConfig baseConfig;

    private Boolean pass;

    
    public static final String SUCCESSFULLY = " successfully";

    /**
     * 
     */
    public static final String BUTTON = "Button";

    /**
     * @param baseConfig BaseConfig
     */
    @SuppressWarnings("squid:S3366")
    public CommonUtils(final BaseConfig baseConfig) {
        this.baseConfig = baseConfig;
        PageFactory.initElements(baseConfig.getBaseDriver(), this);
        LOGGER.info("Constructor initialize in CommonUtils class.");
    }

    /**
     * @param element WebElement
     * @return driver of CommonUtils
     */
    public CommonUtils clickOnButton(final WebElement element) {
        String elementText = null;
        try {
            baseConfig.getExplicitWaits().waitforElementToBeClickable(
                baseConfig.getBaseDriver(),
                element,
                100);
            elementText = element.getText();
            baseConfig.getKeyboardactions().clickOnElement(baseConfig.getBaseDriver(), element);

            LOGGER.info("Clicked on Button::" + elementText + SUCCESSFULLY);
            ExtentTestManager.getTest().log(
                Status.PASS,
                "Button clicked " + elementText + SUCCESSFULLY);
        } catch (final Exception e) {
            LOGGER.error("Click on button  is" + elementText + FAILED + e);
            ExtentTestManager.getTest().log(
                Status.FAIL,
                BUTTON + elementText + " click Failed " + e.getMessage());
            throw e;
        }
        return this;
    }

    /**
     * @param buttonName String
     * @return driver of CommonUtils
     * @throws Exception
     */
    @SuppressWarnings("squid:S00112")
    public CommonUtils clickOnButtonByName(final String buttonName) throws Exception {
        String elementText = null;
        try {

            final String xPath =
                "//a[not(contains(@class,'x-btn-disabled'))]//span[text()='" + buttonName + "']";

            final WebElement element = baseConfig.getBaseDriver().findElement(By.xpath(xPath));

            Thread.sleep(1000);
            baseConfig.getExplicitWaits().waitforElementToBeClickable(
                baseConfig.getBaseDriver(),
                element,
                120);
            elementText = element.getText();
            baseConfig.getKeyboardactions().clickOnElement(baseConfig.getBaseDriver(), element);
            Thread.sleep(3000);
            LOGGER.info("Clicked on Button " + elementText + SUCCESSFULLY);
            ExtentTestManager.getTest().log(
                Status.PASS,
                "Clicked on Button " + elementText + SUCCESSFULLY);
        } catch (final Exception e) {
            LOGGER.error("Button clicked is " + elementText + FAILED + e);
            ExtentTestManager.getTest().log(
                Status.FAIL,
                BUTTON + elementText + " click Failed " + e.getMessage());
            throw e;
        }
        return this;
    }

    /**
     * Verify button is enabled or disabled.
     * 
     * @param element WebElement
     * @return driver of ProductionFlowCommon class
     */
    public CommonUtils verifyButtonIsEnabled(final WebElement element) {
        try {

            final Boolean buttonSelection = element.isEnabled();

            if (buttonSelection.booleanValue()) {
                LOGGER.info(BUTTON + element.getText() + " is enabled");
                ExtentTestManager.getTest().log(Status.PASS, element.getText() + " is enabled.");
            } else {
                LOGGER.error(BUTTON + element.getText() + " is disabled");
                ExtentTestManager.getTest().log(Status.FAIL, element.getText() + " is disabled.");
            }
        } catch (final Exception e) {
            LOGGER.error(e);
            ExtentTestManager.getTest().log(
                Status.FAIL,
                element.getText() + " button Verification Failed" + '\n' + e.getMessage());
            throw e;
        }
        return this;

    }

    /**
     * @param element WebElement
     * @param text String
     * @param logMessage String logmessage
     * @return driver of CommonUtils
     */
    public CommonUtils setText(
            final WebElement element,
            final String text,
            final String logMessage) {
        try {
            baseConfig.getExplicitWaits().waitforVisibilityOfElementPresent(
                baseConfig.getBaseDriver(),
                element,
                15);
            baseConfig.getExplicitWaits().waitforElementToBeClickable(
                baseConfig.getBaseDriver(),
                element,
                100);
            baseConfig.getKeyboardactions().clearText(baseConfig.getBaseDriver(), element);
            baseConfig.getKeyboardactions().sendKeys(baseConfig.getBaseDriver(), element, text);
            ExtentTestManager.getTest().log(Status.PASS, logMessage);
            LOGGER.info(logMessage);
        } catch (final Exception e) {
            LOGGER.error("Unaable to set text " + element + FAILED + e);
            ExtentTestManager.getTest().log(
                Status.FAIL,
                text + "is not set for the element" + element + e.getMessage());
            throw e;
        }
        return this;
    }

    /**
     * Method to verify Quotes Dashboard dropdown values..
     * 
     * @return boolean
     * @param dropdownlist Actual list of dropdown values
     * @param dropdownArrow webelement representing dropdown arrow
     * @param expectedLabelsList Expected list of dropdown values
     */
    public Boolean verifyDropDownLabels(
            final List<WebElement> dropdownlist,
            final WebElement dropdownArrow,
            final ImmutableList<String> expectedLabelsList) {

        try {
            baseConfig.getKeyboardactions().clickOnElement(
                baseConfig.getBaseDriver(),
                dropdownArrow);

            final List<String> actualLabels = new ArrayList<>();

            for (WebElement webElement : dropdownlist) {

                actualLabels.add(webElement.getText());

            }

            pass = expectedLabelsList.equals(actualLabels);

            if (pass != null && pass.booleanValue()) {

                LOGGER.info("Dropdown list is verified successfully");
                ExtentTestManager.getTest().log(
                    Status.INFO,
                    "Dropdown list elements verified successfuly.");
            } else {

                LOGGER.info("Dropdown list elements do not match");
                ExtentTestManager.getTest().log(Status.INFO, "Dropdown list elements do not match");
            }
        } catch (final Exception e) {
            LOGGER.error(e);
        }
        return pass;
    }

    /**
     * Select any dropdown value .
     * 
     * @param dropdownValue String
     * @param btnDropdownXpath String
     * @return this class driver
     */
    public CommonUtils selectDropDowns(final String dropdownValue, final String btnDropdownXpath) {

        try {

            baseConfig.getExplicitWaits().waitforVisibilityOfElementLocated(
                baseConfig.getBaseDriver(),
                By.xpath(btnDropdownXpath),
                10);

            baseConfig.getKeyboardactions().scrollIntoView(
                baseConfig.getBaseDriver(),
                baseConfig.getBaseDriver().findElement(By.xpath(btnDropdownXpath)));

            baseConfig.getImouseactions().selectDropDownxCP(
                baseConfig.getBaseDriver(),
                btnDropdownXpath,
                dropdownValue);
            LOGGER.info("Dropdown selected " + dropdownValue);
            ExtentTestManager.getTest().log(
                Status.PASS,
                "Selection of dropdown " + dropdownValue + " successful.");
        } catch (final Exception e) {
            LOGGER.error("Dropdown selection failed " + e);
            ExtentTestManager.getTest().log(
                Status.FAIL,
                "Selection of dropdown " + dropdownValue + FAILED);
            throw e;
        }
        return this;

    }

    /**
     * Method to verify Quotes Dashboard Column name.
     * 
     * @return boolean
     * @param columnNumber index of column
     * @param expectedColumnName Expected Column name
     */
    public boolean verifyColumnName(final int columnNumber, final String expectedColumnName) {

        final WebElement colName =
            baseConfig.getBaseDriver().findElement(
                By.xpath(
                    "//*[contains(@class,'x-grid-header')]" + "/div/div/div[" + columnNumber
                        + "]"));
        final JavascriptExecutor jse = (JavascriptExecutor) baseConfig.getBaseDriver();
        jse.executeScript("arguments[0].scrollIntoView(true);", colName);
        if (colName.getText().equalsIgnoreCase(expectedColumnName)) {

            LOGGER.info(colName.getText() + " column is present");
            ExtentTestManager.getTest().log(Status.PASS, colName.getText() + " column is present");
            return true;
        } else {
            LOGGER.info(colName.getText() + " column is not present");
            ExtentTestManager.getTest().log(
                Status.FAIL,
                colName.getText() + " column is not present");
            return false;
        }

    }

    /**
     * Method to click on a web element.
     * 
     * @param element Webelement
     * @return this driver of class
     */
    public CommonUtils clickOnElement(final WebElement element) {

        try {

            baseConfig.getExplicitWaits().waitforVisibilityOfElementPresent(
                baseConfig.getBaseDriver(),
                element,
                20);

            baseConfig.getExplicitWaits().waitforElementToBeClickable(
                baseConfig.getBaseDriver(),
                element,
                60);
            ExtentTestManager.getTest().log(
                Status.PASS,
                "Clicked on button " + element.getText() + SUCCESSFULLY);
            LOGGER.info("Clicked on button " + element.getText() + SUCCESSFULLY);
            baseConfig.getKeyboardactions().clickOnElement(baseConfig.getBaseDriver(), element);

        } catch (final Exception e) {
            ExtentTestManager.getTest().log(
                Status.FAIL,
                " unable to click on button" + e.getMessage());
            try {
                throw e;
            } catch (final Exception e1) {
                ExtentTestManager.getTest().log(
                    Status.FAIL,
                    " unable to catch exception" + e.getMessage());
                throw e1;
            }
        }
        return this;

    }

    /**
     * @param element WebElement
     * @return driver CommonUtils driver
     */
    public CommonUtils verifyElement(final WebElement element) {
        try {

            baseConfig.getExplicitWaits().waitforVisibilityOfElementPresent(
                baseConfig.getBaseDriver(),
                element,
                10);

            baseConfig.getKeyboardactions().verifyElement(baseConfig.getBaseDriver(), element);

            ExtentTestManager.getTest().log(
                Status.PASS,
                "Verified element " + element.getText() + SUCCESSFULLY);
            LOGGER.info("Verified element " + element.getText() + SUCCESSFULLY);

        } catch (final Exception e) {
            ExtentTestManager.getTest().log(
                Status.FAIL,
                " Unable to verify element" + e.getMessage());
            throw e;
        }
        return this;
    }

    /**
     * Method to get attribute value of a web element.
     * 
     * @param element Webelement
     * @return String
     */
    @SuppressWarnings("squid:S1854")
    public String getAttributeValue(final WebElement element) {

        boolean staleElement = true;
        String attributeValue = null;
        while (staleElement) {

            try {

                attributeValue = element.getAttribute("value");
                staleElement = false;

            } catch (final StaleElementReferenceException e) {

                staleElement = true;
                throw e;

            }

        }
        return attributeValue;
    }

    /**
     * @param message String message
     * @return this
     */
    public CommonUtils printFailLog(final String message) {

        LOGGER.error(message);
        ExtentTestManager.getTest().log(Status.FAIL, message);
        return this;

    }

    /**
     * @return the screenShotPath
     */
    public static String getScreenShotPath() {
        return System.getProperty("user.dir") + File.separator + BaseConfig.TARGET + File.separator
            + "Screenshots" + File.separator;
    }

    /**
     * @param lableName String lableName
     * @param indexNumber int indexNumber
     * @param value String actual value to pass against lable
     * @return driver CommonUtils class driver
     */
    public CommonUtils setForLable(
            final String lableName,
            final int indexNumber,
            final String value) {
        final String xPath =
            "(//label[.='" + lableName + "']/following::*[@data-ref='inputEl'])[" + indexNumber
                + "]";

        final WebElement element = baseConfig.getBaseDriver().findElement(By.xpath(xPath));
        setText(element, value, "Value :: " + value + " ::" + " set successfully for " + lableName);

        return this;
    }

    /**
     * Method to verify the webElement if it not present on the screen.
     * 
     * @param element element
     * @return boolean
     */
    @SuppressWarnings("squid:S1155")
    public boolean verifyElementExistence(final String element) {
        try {
            final List<WebElement> weblist =
                baseConfig.getBaseDriver().findElements(By.xpath(element));
            return weblist.size() > 0;

        } catch (final Exception e) {
            ExtentTestManager.getTest().log(
                Status.FAIL,
                " Unable to verify element" + e.getMessage());
            throw e;
        }
    }

    /**
     * this method stores the value based on given row and col number.
     * 
     * @param rowNum int rownumber
     * @param colNum int colnumber
     * @return value String
     */
    public String storeTableValueByRowCol(final int rowNum, final int colNum) {

        final String locatorxPath =
            "//div[contains(@class,'xcp_results_list-cls')]" + "//table[" + rowNum + "]//td["
                + colNum + "]";

        final WebElement element = baseConfig.getBaseDriver().findElement(By.xpath(locatorxPath));

        baseConfig.getExplicitWaits().waitforVisibilityOfElementPresent(
            baseConfig.getBaseDriver(),
            element,
            10);

        LOGGER.info("The value :: " + element.getText() + " stored " + SUCCESSFULLY);
        ExtentTestManager.getTest().log(
            Status.PASS,
            "The value:: " + element.getText() + " stored " + SUCCESSFULLY);
        return element.getText();

    }

    /**
     * @param placeHolderName String placeHolderName
     * @param indexNumber int indexNumber
     * @param value String actual value to pass against placeHolderName
     * @return driver CommonUtils class driver
     */
    public CommonUtils setTextUsingPlaceholder(
            final String placeHolderName,
            final int indexNumber,
            final String value) {
        final String xPath = "(//*[@placeholder='" + placeHolderName + "'])[" + indexNumber + "]";

        final WebElement element = baseConfig.getBaseDriver().findElement(By.xpath(xPath));
        setText(
            element,
            value,
            "Value :: " + value + " ::" + " set successfully for " + placeHolderName);

        return this;
    }

}
