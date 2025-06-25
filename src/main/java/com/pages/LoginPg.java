package com.pages;

import com.basepage.BasePage;
import org.openqa.selenium.WebElement;

public class LoginPg extends BasePage {


    public static WebElement email() {

        return id("email");
        
        
    }

    public static WebElement password() {

        return id("password");
    }

    public static WebElement signInBtn() {

        return xpath("//span[text() = 'Sign in']");
    }

    public static WebElement userText() {

        return xpath("//h2[contains(text(),'Shanawaz')]");
    }
}
