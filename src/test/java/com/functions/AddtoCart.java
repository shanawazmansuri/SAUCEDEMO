package com.functions;

import com.basepage.BaseRemoteDriver;
import com.pages.AddCartPg;
import org.openqa.selenium.By;

import java.util.ArrayList;
import java.util.List;

public class AddtoCart extends BaseRemoteDriver {

    public void addingCartTest()
    {
        List<String> items = new ArrayList<String>();
        items.add("Sauce Labs Backpack");
        items.add("Sauce Labs Onesie");

        for (String it : items) {

            click(getDriver().findElement(By.xpath("//div[text()='" + it + "']/ancestor::div/following-sibling::div/button")));
        }

        click(AddCartPg.cartBtn());
        click(AddCartPg.checkOutBtn());

        enterText(AddCartPg.firstName(),"Shanawaz");
        enterText(AddCartPg.lastName(),"Mansuri");
        enterText(AddCartPg.postalCode(),"400003");
        click(AddCartPg.continueBtn());
        click(AddCartPg.FinishBtn());
        assertEquals((AddCartPg.orderCompleteText()),"THANK YOU FOR YOUR ORDER");
    }
}