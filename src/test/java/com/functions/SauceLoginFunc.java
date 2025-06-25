package com.functions;

import com.basepage.BaseRemoteDriver;
import com.config.PropFile;
import com.pages.SauceLoginPg;

public class SauceLoginFunc extends BaseRemoteDriver {
    PropFile pro = new PropFile();

    public void validateLoginTest()
    {
        enterText(SauceLoginPg.userName(),pro.prop.getProperty("userName"));
        enterText(SauceLoginPg.password(),pro.prop.getProperty("pwd"));
        click(SauceLoginPg.loginBtn());
        boolean hp = isDisplayed(SauceLoginPg.homePage());
        assertTrue(hp);
    }
}
