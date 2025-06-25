package com.pages;

import com.basepage.BaseRemoteDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class SauceLoginPg extends BaseRemoteDriver {

public static WebElement userName()
{
       //return getElement("ID","user-name");
    return getDriver().findElement(By.id("user-name"));
}

    public static WebElement password()
    {
        return getElement("ID","password");
    }

    public static WebElement loginBtn()
    {
        return getElement("ID","login-button");
    }

    public static WebElement homePage()
    {
        return getElement("XPATH","//div[text()='Products']");
    }

}
