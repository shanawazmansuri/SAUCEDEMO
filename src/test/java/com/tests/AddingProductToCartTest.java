package com.tests;

import com.basepage.BaseRemoteDriver;
import com.functions.AddtoCart;
import org.openqa.selenium.By;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

public class AddingProductToCartTest extends BaseRemoteDriver {

    @Test
    public void addToCart() {
        AddtoCart add = new AddtoCart();
        add.addingCartTest();



    }
}