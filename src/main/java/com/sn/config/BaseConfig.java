package com.sn.config;

import java.io.File;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.IHookCallBack;
import org.testng.IHookable;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Parameters;
import org.testng.asserts.SoftAssert;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.testng.Reporter;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import com.sn.apt.browser.core.BrowserDriverImpl;
import com.sn.apt.browser.core.CustomChromeDriver;
import com.sn.apt.browser.core.IBrowerDriver;
import com.sn.apt.browser.navigation.IBrowserNavigator;
import com.sn.apt.browser.operations.BrowserOperationsImpl;
import com.sn.apt.browserstack.BrowserStack;
import com.sn.apt.browserstack.ConfigureCapabilities;
import com.sn.apt.documentum.JobHelper;
import com.sn.apt.documentum.QueryHelper;
import com.sn.apt.email.util.SendEmail;
import com.sn.apt.frame.actions.FrameActions;
import com.sn.apt.keyboard.actions.IKeyboardActions;
import com.sn.apt.keyboard.actions.KeyboardActions;
import com.sn.apt.mouse.actions.IMouseActions;
import com.sn.apt.mouse.actions.MouseActions;
import com.sn.apt.util.AutoPilotUtil;
import com.sn.apt.waits.explicit.ExplicitWait;
import com.sn.apt.waits.implicit.ImplicitWait;

/**
 * @author Pankaj Tarar
 */
@SuppressWarnings("squid:S1200")
public class BaseConfig implements IHookable {

    private static final Logger LOGGER = LogManager.getLogger(BaseConfig.class);


    /**
     * driver.
     */
    private static WebDriver baseDriver;

    private static Properties config;

    /**
     * seleniumWaits.
     */
    private static ExplicitWait explicitWaits;

    /**
     * Selenium wait of implicit type.
     */
    private static ImplicitWait implicitWait;

    private static FrameActions frameActions;

    private static SendEmail sendMail;

    private static IBrowerDriver browserUtil;

    private static BrowserDriverImpl browserDriverImpl;

    private static BrowserOperationsImpl browseroprimpl;

    private static IBrowserNavigator browsernavigator;

    private static IKeyboardActions keyboardActions;

    private static IMouseActions imouseActions;

    private static BrowserStack browserStack;

    private static JobHelper jobHelper;

    private static QueryHelper queryHelper;

    private static AutoPilotUtil autopilotUtils;

    private static final String SOFT_ASSERT = "softAssert";

    /**
     * created variable for config properties.
     */
    public static final String CONFIG_PROP = "config.properties";
    
    /**
     * created variable for target directory.
     */
    public static final String TARGET = "target";

    /**
     * created variable for current user directory.
     */

    public static final String USER_DIR = "user.dir";


    private static String executionEnvApp;

    /**
     * It will execute beforeSuite.
     */
    @SuppressWarnings("squid:S2696")
    @BeforeSuite
    public void beforeSuite() {

        config = AutoPilotUtil.getPropertiesFile(CONFIG_PROP);
        frameActions = new FrameActions();
        explicitWaits = new ExplicitWait();
        implicitWait = new ImplicitWait();
        keyboardActions = new KeyboardActions();
        imouseActions = new MouseActions();
        browseroprimpl = new BrowserOperationsImpl(getBaseDriver());
        jobHelper = new JobHelper();
        queryHelper = new QueryHelper();

        LOGGER.info("Before suite set up completed");

    }

    /**
     * It will execute beforeclass.
     */

    /**
     * @param os String
     * @param osVersion String
     * @param browserName String
     * @param browserVersion String
     * @param browserstackLocal String
     * @param browserDriver String
     * @param executionEnv String
     * @param seleniumVersion String
     * @param env String
     */
    @SuppressWarnings("squid:S2696")
    @BeforeClass(alwaysRun = true)
    @Parameters({
        "os",
        "osversion",
        "browserName",
        "browserVersion",
        "browserstackLocal",
        "seleniumVersion",
        "browserDriver",
        "env",
        "executionEnv" })
    public void beforeClass(
            final String os,
            final String osVersion,
            final String browserName,
            final String browserVersion,
            final String browserstackLocal,
            final String seleniumVersion,
            final String browserDriver,
            final String env,
            final String executionEnv) {
        executionEnvApp = executionEnv;
        if ("local".equals(env)) {
            configureLocalBrowser(executionEnv);
        } else {
            configureBrowserStack(
                os,
                osVersion,
                browserName,
                browserVersion,
                browserstackLocal,
                seleniumVersion,
                browserDriver);
        }

    }

    /**
     * @return the executionEnvApp
     */
    public static String getExecutionEnvApp() {
        return executionEnvApp;
    }

    /**
     * This method is to configure browserstack settings.
     * 
     * @param os String
     * @param osVersion String
     * @param browserName String
     * @param browserVersion String
     * @param browserstackLocal String
     * @param seleniumVersion String
     * @param browserDriver String
     */
    @SuppressWarnings("squid:S2696")
    private void configureBrowserStack(
            final String os,
            final String osVersion,
            final String browserName,
            final String browserVersion,
            final String browserstackLocal,
            final String seleniumVersion,
            final String browserDriver) {
        final ConfigureCapabilities caps =
            new ConfigureCapabilities(
                os,
                osVersion,
                browserName,
                browserVersion,
                browserstackLocal,
                seleniumVersion,
                browserDriver);
        browserStack = new BrowserStack(caps, config.getProperty("browserstack_hub_url"));
        setBaseDriver(browserStack.getDriver());
        browseroprimpl = new BrowserOperationsImpl(getBaseDriver());
        implicitWait.implicitWait(getBaseDriver(), 10);
        getBaseDriver().manage().timeouts().pageLoadTimeout(20, TimeUnit.SECONDS);
    }

    /**
     * This method is to configure execution in local browser.
     * 
     * @param executionEnv String
     */
    @SuppressWarnings("squid:S2696")
    @Parameters("executionEnv")
    private void configureLocalBrowser(final String executionEnv) {
        browserUtil = new CustomChromeDriver(config.getProperty("chromedriverVersion"));
        setBaseDriver(browserUtil.getDriver());
        browseroprimpl = new BrowserOperationsImpl(getBaseDriver());
        implicitWait.implicitWait(getBaseDriver(), 20);
        if ("INT".equals(executionEnv)) {
            LOGGER.info("Set environemnt as INTEGRATION");
            getBaseDriver().get(config.getProperty("project_int_url"));
        } else if ("ACC".equals(executionEnv)) {
            getBaseDriver().get(config.getProperty("ACCURL"));
            LOGGER.info("Set environemnt as ACCEPTANCE");
        }
        getBaseDriver().manage().window().maximize();
    }

    /**
     * It will execute afterclass.
     */
    @AfterClass(alwaysRun = false)
    public void afterClass() {
        LOGGER.info("Quiting driver in after class");
        getBaseDriver().quit();
    }

    /**
     * It will execute after Suite.
     */
    @AfterSuite
    public void afterSuite() {
        LOGGER.info("After suite executed");

    }


    /**
     * @return the baseDriver
     */
    public WebDriver getBaseDriver() {
        return baseDriver;
    }

    /**
     * @param baseDriver the baseDriver to set
     */
    @SuppressWarnings("squid:S2696")
    public void setBaseDriver(final WebDriver baseDriver) {
        this.baseDriver = baseDriver;
    }

    /**
     * @return the config
     */
    public Properties getConfig() {
        return config;
    }

    /**
     * @return the explicitWaits
     */
    public ExplicitWait getExplicitWaits() {
        return explicitWaits;
    }

    /**
     * @return the implicitWait
     */
    public ImplicitWait getImplicitWait() {
        return implicitWait;
    }

    /**
     * @return the frameactions
     */
    public FrameActions getFrameactions() {
        return frameActions;
    }

    /**
     * @return the sendmail
     */
    public SendEmail getSendmail() {
        return sendMail;
    }

    /**
     * @return the browserutil
     */
    public IBrowerDriver getBrowserutil() {
        return browserUtil;
    }

    /**
     * @return the browserDriverImpl.
     */
    public BrowserDriverImpl getBrowserDriverImpl() {
        return browserDriverImpl;
    }

    /**
     * @return the browseroprimpl
     */
    public static BrowserOperationsImpl getBrowseroprimpl() {
        return browseroprimpl;
    }

    /**
     * @return the browsernavigator
     */
    public IBrowserNavigator getBrowsernavigator() {
        return browsernavigator;
    }

    /**
     * @return the keyboardactions
     */
    public IKeyboardActions getKeyboardactions() {
        return keyboardActions;
    }

    /**
     * @return the imouseactions
     */
    public IMouseActions getImouseactions() {
        return imouseActions;
    }

    /**
     * @return the browserStack
     */
    public BrowserStack getBrowserStack() {
        return browserStack;
    }

    /**
     * @return the jobhelper
     */
    public JobHelper getJobhelper() {
        return jobHelper;
    }

    /**
     * @return queryHelper
     */
    public QueryHelper getQueryhelper() {
        return queryHelper;
    }

    /**
     * @return the autopilotUtils
     */
    public AutoPilotUtil getAutopilotUtils() {
        return autopilotUtils;
    }

    @Override
    public void run(final IHookCallBack callBack, final ITestResult testResult) {
        SoftAssert softAssert = new SoftAssert();
        testResult.setAttribute(SOFT_ASSERT, softAssert);
        callBack.runTestMethod(testResult);
        softAssert = getSoftAssert(testResult);
        if (!testResult.isSuccess()) {
            Throwable throwable = testResult.getThrowable();
            if (null != throwable) {
                if (null != throwable.getCause()) {
                    throwable = throwable.getCause();
                }
                softAssert.assertNull(throwable, ExceptionUtils.getStackTrace(throwable));
            }
        }
        softAssert.assertAll();
    }

    /**
     * @return softAssert
     */
    public static SoftAssert getSoftAssert() {
        return getSoftAssert(Reporter.getCurrentTestResult());
    }

    /**
     * @param result ITestResult
     * @return (SoftAssert) object
     */
    private static SoftAssert getSoftAssert(final ITestResult result) {
        final Object object = result.getAttribute(SOFT_ASSERT);
        if (object instanceof SoftAssert) {
            return (SoftAssert) object;
        }
        throw new IllegalStateException("Could not find a soft assertion object");
    }

}
