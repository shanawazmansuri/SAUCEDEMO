package com.functions;

import com.basepage.BasePage;
import com.pages.LoginPg;
import com.utilities.ExtentReportConf;

public class LoginFunc extends BasePage {

    public void login()
    {
        enterText(LoginPg.email(),pro.prop.getProperty("userName"));
        ExtentReportConf.reportPassLog("Entered User Id as "+pro.prop.getProperty("userName"));
        enterText(LoginPg.password(),pro.prop.getProperty("pwd"));
        ExtentReportConf.reportPassLog("Entered Password as "+pro.prop.getProperty("pwd"));
        click(LoginPg.signInBtn());
        ExtentReportConf.reportPassLog("Clicked on SignIn button");
        boolean stat = isDisplayed(LoginPg.userText());
        assertTrue(stat);
    }
}
