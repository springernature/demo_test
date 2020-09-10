package com.sn.pageobjects;
/**
 * This file is for reference only. 
 */

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;
import com.sn.config.BaseConfig;
import com.sn.listeners.ExtentTestManager;
import com.sn.utils.CommonUtils;

/**
 * This class is created to have all page objects and methods from the login page.
 * 
 * @author Pankaj Tarar
 */

public class LoginPage {

    private static final Logger LOGGER = LogManager.getLogger(LoginPage.class);

    private BaseConfig baseConfig;

    private CommonUtils commonUtils;

    @FindBy(id = "j_username-inputEl")
    private WebElement txtbxUserName;

    @FindBy(id = "j_password-inputEl")
    private WebElement txtbxPassword;

    @FindBy(xpath = "//a[not(contains(@class,'x-btn-disabled'))]//span[text()='Sign In']")
    private WebElement btnLogIn;

    /**
     * This is the constructor for LoginPage class.
     * 
     * @param baseConfig BaseConfig
     */
    @SuppressWarnings("squid:S3366")
    public LoginPage(final BaseConfig baseConfig) {

        this.baseConfig = baseConfig;
        PageFactory.initElements(baseConfig.getBaseDriver(), this);
        this.commonUtils = new CommonUtils(baseConfig);
        LOGGER.info("Constructor 1 initialize in Login Page class.");
    }
    
    
    /**
     * Locator for Activate a Code Button.
     */
    @FindBy(xpath = "//button[text()='Activate a code']")
    private WebElement btnActivateACode;

    /**
     * Locator for Login button.
     */
    @FindBy(xpath = "//button[text()='Login']")
    private WebElement btnLogin;

    /**
     * Locator for Student Registration Button.
     */
    @FindBy(xpath = "//button[text()='Student Registration']")
    private WebElement btnStudentRegistration;

    /**
     * Locator for Teacher Registration Button.
     */
    @FindBy(xpath = "//button[text()='Teacher Registration']")
    private WebElement btnTeacherRegistration;

    /**
     * Locator for Support button.
     */
    @FindBy(xpath = "//button[text()='Support']")
    private WebElement btnSupport;

    /**
     * Locator for user User Guides Button.
     */
    @FindBy(xpath = "//button[text()='User Guides']")
    private WebElement btnUserGuides;

    /**
     * Method to click on Login button
     * 
     * @return Exception
     */
    public LoginPage clickLoginButton() throws InterruptedException {
        baseConfig
            .getExplicitWaits()
            .waitforVisibilityOfElementPresent(baseConfig.getBaseDriver(), btnLogin, 2000);
        commonUtils.clickOnButton(btnLogin);
        Thread.sleep(1000);

        return this;
    }

    /**
     * Method to verify Login button is available on page
     * 
     * @return Exception
     */
    public LoginPage verifyLoginButtonIsAvailable() {
        commonUtils.verifyElement(btnLogin);
        return this;
    }
}
