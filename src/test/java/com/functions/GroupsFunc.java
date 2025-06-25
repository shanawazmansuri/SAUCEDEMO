package com.functions;

import com.basepage.BasePage;
import com.pages.GroupsPg;
import org.openqa.selenium.WebElement;

public class GroupsFunc extends BasePage {


    private String grpName;
    private String grpDesc;
    private String grpImg;

    private String grpType;

    public GroupsFunc(String grpName, String grpDesc, String grpImg, String grpType) {
        this.grpName = grpName;
        this.grpDesc = grpDesc;
        this.grpImg = grpImg;
        this.grpType = grpType;
    }

    public GroupsFunc() {
    }


    public void createGroup() {
        click(GroupsPg.groupMenu());
        click(GroupsPg.createGrpBtn());
        enterText(GroupsPg.imageUpload(), grpImg);
        enterText(GroupsPg.groupName(), grpName);
        enterText(GroupsPg.groupdesc(), grpDesc);
        if (grpType.equals("close")) {
            click(GroupsPg.openCloseGrp());
        }
        click(GroupsPg.createBtn());
        waitTovisibleElement(GroupsPg.closeBtn(), 10);
        clickUsingJavascriptExecutor(GroupsPg.closeBtn(), driver);
        waitTovisibleElement(GroupsPg.grpHeading(), 10);
        boolean head = isDisplayed(GroupsPg.grpHeading());
        assertTrue(head);
        Wait(2000);
    }


    public void createPrivateGroup() {
        click(GroupsPg.groupMenu());
        click(GroupsPg.createGrpBtn());
        enterText(GroupsPg.imageUpload(), grpImg);
        enterText(GroupsPg.groupName(), grpName);
        enterText(GroupsPg.groupdesc(), grpDesc);
        click(GroupsPg.openCloseGrp());
    }

    public void switchingGroupTypes() {
        click(GroupsPg.groupMenu());
        for (WebElement ele : GroupsPg.grpLinks()) {
            click(GroupsPg.groupMenu());
            if (GroupsPg.grpLinks().get(0).getText().equals("My Groups")) {
                click(ele);
                assertEquals(GroupsPg.grpLinks().get(0), "My Groups");
            } else if (GroupsPg.grpLinks().get(1).getText().equals("Invitations")) {
                click(ele);
                assertEquals(GroupsPg.grpLinks().get(1), "Invitations");
            } else if (GroupsPg.grpLinks().get(2).getText().equals("Open Groups")) {
                click(ele);
                assertEquals(GroupsPg.grpLinks().get(2), "Open Groups");
            }
        }
        GroupsPg.grpLinks().get(0).click();
    }


    public void selectGroupTab(int tab) {
        GroupsPg.grpLinks().get(tab).click();
    }


    public void searchGrp(String groupName) {

        for (WebElement element : GroupsPg.grpNames()) {
            if (element.getText().equals(groupName)) {
                element.click();
            }

        }
        Wait(10000);
    }
}
