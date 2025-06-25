package com.tests;

import com.basepage.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

public class Pagination extends BasePage {

    public void webTable(String text) {
        scrollToElementUsingJavascriptExecutor(driver.findElement(By.xpath("//table")), driver);
        List<WebElement> rows = driver.findElements(By.xpath("//table[@id='customers']/tbody/tr"));
        List<WebElement> cols = driver.findElements(By.xpath("//*[@id='customers']/tbody/tr[2]/td"));
        int rowsize = rows.size();
        int colsize = cols.size();
        boolean dataFound = false;

        while (dataFound) {
            for (int i = 2; i <= rowsize; i++) {
                for (int j = 1; j <= colsize; j++) {

                    WebElement eachdata = driver.findElement(By.xpath("//table[@id='customers']/tbody/tr[" + i + "]/td[" + j + "]"));
                    String names = eachdata.getText();
                    System.out.println(names);
                    if (names.equals(text)) {
                      dataFound = true;

                           return;
                    }

                    else
                    {
                        driver.findElement(By.xpath("//ul[@id='pagination']/li/a")).click();
                    }
                }
            }


        }
    }
}
