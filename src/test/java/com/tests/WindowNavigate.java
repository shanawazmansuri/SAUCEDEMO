package com.tests;

import com.basepage.BasePage;
import org.openqa.selenium.By;
import org.testng.annotations.Test;

import java.util.Iterator;
import java.util.Set;

public class WindowNavigate extends BasePage
{

    @Test(enabled = false)
    public void links()
    {
        String parentwindow = driver.getWindowHandle();
        driver.findElement(By.xpath("//a[@href='http://saucedemo.com']")).click();
        driver.findElement(By.xpath("//a[@href='https://github.com/cypress-io/cypress-realworld-app']")).click();
        driver.findElement(By.xpath("//a[@href='https://gh-users-search.netlify.app/']")).click();

        Set<String> multi = driver.getWindowHandles();
        for(String st: multi)
        {
            String title = driver.switchTo().window(st).getTitle();
            System.out.println(title);
            if(title.contains("Swag"))
            {
                System.out.println("Switched to "+title);
                break;
            }

        }
    }

    @Test
    public void testing2()
    {
        String parentwindow = driver.getWindowHandle();
        driver.findElement(By.xpath("//a[@href='http://saucedemo.com']")).click();
        driver.findElement(By.xpath("//a[@href='https://github.com/cypress-io/cypress-realworld-app']")).click();
        driver.findElement(By.xpath("//a[@href='https://gh-users-search.netlify.app/']")).click();

        Set<String> multi = driver.getWindowHandles();

        Iterator<String> it = multi.iterator();
        while(it.hasNext())
        {
          String win = it.next();
          driver.switchTo().window(win);

          String title = driver.getTitle();
          if(title.contains("Swag"))
          {
            break;
          }
        }

    }
}
