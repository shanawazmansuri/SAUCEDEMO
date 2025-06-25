package com.pages;

import com.basepage.BasePage;
import org.openqa.selenium.WebElement;

import java.util.List;

public class GroupsPg extends BasePage {


    public static WebElement groupMenu() {

        return xpath("//span[text()='Groups']");
    }


    public static WebElement createGrpBtn() {

        return xpath("//span[text()='Create Group']");
    }

    public static WebElement imageUpload() {

        return xpath("//button[@aria-label='Upload']");
    }

    public static WebElement groupName() {

        return id("name");
    }

    public static WebElement groupdesc() {

        return xpath("//textarea[@inputid='description']");
    }

    public static WebElement openCloseGrp() {

        return xpath("//div[@class='react-toggle-thumb']");
    }

    public static WebElement createBtn() {

        return xpath("//span[text()='Create']");
    }

    public static WebElement closeBtn() {

        return xpath("//button[@title='Close']");
    }


    public static WebElement grpHeading() {

        return xpath("//h2");
    }

    public static List<WebElement> grpLinks() {
        return elements("//div[@role='tablist']/button/span");
    }

    public static List<WebElement> grpNames() {
        return elements("//h2[starts-with(@class, 'Text')]");
    }

}
