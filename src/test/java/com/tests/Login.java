package com.tests;

import com.basepage.BasePage;
import com.functions.LoginFunc;
import org.testng.annotations.Test;

public class Login extends BasePage {

    LoginFunc logf = new LoginFunc();

    @Test
    public void LoginTest() {
        logf.login();
    }
}


