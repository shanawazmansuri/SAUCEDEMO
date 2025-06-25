package com.tests;

import com.basepage.BaseRemoteDriver;
import com.functions.SauceLoginFunc;
import org.testng.annotations.Test;

public class SauceLoginTest extends BaseRemoteDriver {

    @Test
    public void loginTest()
    {
        SauceLoginFunc sl = new SauceLoginFunc();
        sl.validateLoginTest();

    }
}
