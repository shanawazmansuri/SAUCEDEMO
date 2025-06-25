package com.pages;

import com.basepage.BaseRemoteDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class AddCartPg extends BaseRemoteDriver {

    public static List<WebElement> listProducts()
    {
        return getElements("ID","login-button");
    }

    public static WebElement cartBtn()
    {
        return getElement("XPATH","//span[@class='fa-layers-counter shopping_cart_badge']");
    }

    public static WebElement checkOutBtn()
    {
        return getElement("XPATH","//a[text()='CHECKOUT']");
    }

    public static WebElement firstName()
    {
        return getElement("ID","first-name");
    }

    public static WebElement lastName()
    {
        return getElement("ID","last-name");
    }

    public static WebElement postalCode()
    {
        return getElement("ID","postal-code");
    }

    public static WebElement continueBtn()
    {
        return getElement("XPATH","//input[@value='CONTINUE']");
    }

    public static WebElement FinishBtn()
    {
        return getElement("XPATH","//a[text()='FINISH']");
    }

    public static WebElement orderCompleteText()
    {
        return getElement("XPATH","//h2[@class='complete-header']");
    }
}
