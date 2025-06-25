package com.tests;

import com.basepage.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

public class Links extends BasePage {

    @Test
    public void linking() throws IOException {
        List<WebElement> alllinks = driver.findElements(By.tagName("a"));
        for(WebElement ele: alllinks)
        {
            String links = ele.getAttribute("href");
            brokenLinks(links);

        }

    }

    public void brokenLinks(String urlLinks)
    {

        try {
            URL ul = new URL(urlLinks);
           HttpURLConnection ht = (HttpURLConnection) ul.openConnection();
           ht.setConnectTimeout(5000);
           ht.connect();
           if(ht.getResponseCode()>=400)
           {
               System.out.println(urlLinks+"======>"+ht.getResponseMessage()+"is Broken");
           }
           else {
               System.out.println(urlLinks+"========>"+ht.getResponseMessage());
           }

        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
