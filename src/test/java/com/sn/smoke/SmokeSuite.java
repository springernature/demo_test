package com.sn.smoke;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.sn.config.BaseConfig;
import com.sn.pageobjects.LoginPage;




public class SmokeSuite extends BaseConfig {

    LoginPage Loginobj;


    private static final Logger LOGGER = LogManager.getLogger(SmokeSuite.class);

    @BeforeClass
    public void beforeClass() {
        Loginobj = new LoginPage(this);
       

    }

    @Test(
            description = "Test case to Verify login button is available on MPO webpage.",
            priority = 0)
    public void testCaseVerifyLoginButton() {
        Loginobj.verifyLoginButtonIsAvailable();
        LOGGER.info("Verified login button");
    }
}
