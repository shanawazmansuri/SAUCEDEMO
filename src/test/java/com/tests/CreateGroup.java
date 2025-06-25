package com.tests;

import com.basepage.BasePage;
import com.functions.GroupsFunc;
import com.functions.LoginFunc;
import com.utilities.ExcelUtils;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class CreateGroup extends BasePage {

    LoginFunc log = new LoginFunc();


    @DataProvider(name = "CreateGrp")
    public Object[][] grpData() {
        Object[][] data = ExcelUtils.excelData(".\\ExcelData\\HIVEDATA.xls", "Group");
        return data;
    }

    @Test(dataProvider = "CreateGrp", description = "Validating by creating open and private groups", priority = 1, enabled = false)
    public void grpCreation(String grpName, String grpDesc, String grpImg,String grpType) {
        try {
            GroupsFunc grp1 = new GroupsFunc(grpName, grpDesc, grpImg,grpType);
            log.login();
            grp1.createGroup();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }



    @Test(description = "Validating by Switching groups type and searching groups ", priority = 1, enabled = true)
    public void grpCreation() {
        try {
            GroupsFunc grp2 = new GroupsFunc();
            log.login();
            grp2.switchingGroupTypes();
            grp2.selectGroupTab(2);
            grp2.searchGrp("20 August");

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}