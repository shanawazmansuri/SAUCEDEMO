package com.tests;

import com.basepage.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

import java.util.List;

public class WebTable extends BasePage {


    public void webTable(String text)
    {
        scrollToElementUsingJavascriptExecutor(driver.findElement(By.xpath("//table")),driver);
        List<WebElement> rows = driver.findElements(By.xpath("//table[@id='customers']/tbody/tr"));
        List<WebElement> cols =driver.findElements(By.xpath("//*[@id='customers']/tbody/tr[2]/td"));
        int rowsize = rows.size();
        int colsize = cols.size();


        for(int i=2;i<=rowsize;i++)
        {
            for(int j= 1;j<=colsize;j++)
            {

               WebElement eachdata = driver.findElement(By.xpath("//table[@id='customers']/tbody/tr["+i+"]/td["+j+"]"));
                String names = eachdata.getText();
                System.out.println(names);
                if(names.equals(text))
                {

                    eachdata.findElement(By.xpath("//table[@id='customers']/tbody/tr["+i+"]/td["+j+"]/preceding-sibling::td/input")).click();
                    eachdata.findElement(By.xpath("//table[@id='customers']/tbody/tr["+i+"]/td["+j+"]/following-sibling::td/a")).click();
                    return;
                }
            }
        }
    }

    @Test
    public void dynamicTable()
    {
            webTable("Open Source");
    }

}