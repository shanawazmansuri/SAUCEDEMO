package com.basepage;

import com.config.PropFile;
import com.utilities.ExtentReportConf;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.*;

import static com.basepage.BaseRemoteDriver.getDriver;

public class BaseRemoteDriver {

    private static ThreadLocal<WebDriver> driver = new ThreadLocal<>();

    // Get driver for current thread
    public static WebDriver getDriver() {
        return driver.get();
    }


    // Set up driver (local or remote)

    public static void browser(String browser, String url) throws MalformedURLException {
        String runMode = System.getProperty("mode", "local"); // local or docker

        if (runMode.equalsIgnoreCase("docker")) {
            DesiredCapabilities capabilities = new DesiredCapabilities();
            capabilities.setBrowserName(browser.toLowerCase());

            driver.set(new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), capabilities));
        } else {
            if (browser.equalsIgnoreCase("chrome")) {
                WebDriverManager.chromedriver().setup();

                ChromeOptions options = new ChromeOptions();
                options.addArguments("--remote-allow-origins=*");
                options.addArguments("--headless", "--no-sandbox", "--disable-dev-shm-usage");
                driver.set(new ChromeDriver(options));
            } else if (browser.equalsIgnoreCase("firefox")) {
                WebDriverManager.firefoxdriver().setup();
                FirefoxOptions options = new FirefoxOptions();
                options.setHeadless(true);
                driver.set(new FirefoxDriver(options));
            }
        }

        getDriver().manage().window().maximize();
        getDriver().get(url);
    }

    // Quit driver for current thread
    @AfterTest
    public void quitDriver() {
        ExtentReportConf.reportInfoLog("Browser Closed Successfully");
        ExtentReportConf.reportTearDown();
        if (getDriver() != null) {
            getDriver().quit();
            driver.remove();
        }
    }


    @Parameters("browser")
    @BeforeTest
    public BaseRemoteDriver setUp(String browser) throws MalformedURLException {
        PropFile pro = new PropFile();
        String className = this.getClass().getSimpleName();
        ExtentReportConf.reportSetUp(className);
        browser(browser, (pro.prop.getProperty("url")));
        ExtentReportConf.reportInfoLog("Opened Browser Successfully");
        maximizeBrowser();
        ExtentReportConf.reportInfoLog("Maximized the Browser");
        implicitWait(10);
        waitPageLoadTimeout(60);
        return this;

    }


    public BaseRemoteDriver tearDown() {
        // quitBrowser();
        return this;
    }

    public static WebElement getElement(String identifierType, String identifierValue) {

        switch (identifierType.toUpperCase()) {
            case "ID":
                return getDriver().findElement(By.id(identifierValue));

            case "CSS":
                return getDriver().findElement(By.cssSelector(identifierValue));

            case "TAGNAME":
                return getDriver().findElement(By.tagName(identifierValue));

            case "XPATH":
                return getDriver().findElement(By.xpath(identifierValue));

            case "NAME":
                return getDriver().findElement(By.name(identifierValue));

            case "CLASSNAME":
                return getDriver().findElement(By.className(identifierValue));
            default:
                return null;

        }
    }

    // Quiting the Browser//
    public BaseRemoteDriver quitBrowser() {
        getDriver().quit();
        return this;
    }

    // Closing the Browser Tab//
    public BaseRemoteDriver closeBrowser() {
        getDriver().close();
        return this;
    }

    // Maximize Browser//
    public BaseRemoteDriver maximizeBrowser() {
        getDriver().manage().window().maximize();
        return this;
    }

    // Minimize Browser//
    public BaseRemoteDriver minimizeBrowser() {
        getDriver().manage().window().setPosition(new Point(-2000, 0));
        return this;
    }

    // ResizeBrowser Browser//
    public BaseRemoteDriver resizeBrowser() {
        Dimension d = new Dimension(800, 480);
        getDriver().manage().window().setSize(d);
        return this;
    }

    // Full Screen//
    public BaseRemoteDriver fullScreen() {
        getDriver().manage().window().fullscreen();
        return this;
    }

    // Delete Cookies//
    public BaseRemoteDriver deleteCookies() {
        getDriver().manage().deleteAllCookies();
        return this;
    }

    // Navigate//
    public BaseRemoteDriver navigate(String url) {
        getDriver().navigate().to(url);
        return this;
    }

    // Back//
    public BaseRemoteDriver back() {
        getDriver().navigate().back();
        return this;
    }

    // Forward//
    public BaseRemoteDriver forward() {
        getDriver().navigate().forward();
        return this;
    }

    // Open Tab in Browser//
    public BaseRemoteDriver openTab() {
        getDriver().switchTo().newWindow(WindowType.TAB);
        return this;

    }

    // Open New Window//
    public BaseRemoteDriver openNewWindow() {
        getDriver().switchTo().newWindow(WindowType.WINDOW);
        return this;
    }

    // Accept Alert//
    public BaseRemoteDriver alertAccept() {
        getDriver().switchTo().alert().accept();
        return this;

    }

    // Dismiss Alert//
    public BaseRemoteDriver alertDismiss() {
        getDriver().switchTo().alert().dismiss();
        return this;

    }

    // Alert getText//
    public String alertGetText() {
        String alertText = getDriver().switchTo().alert().getText();
        return alertText;

    }

    // Alert EnterText //
    public BaseRemoteDriver alertEnterText(String values) {
        getDriver().switchTo().alert().sendKeys(values);
        return this;

    }

    // Refresing Browser

    public BaseRemoteDriver refresh() {
        getDriver().navigate().refresh();
        return this;
    }

    // ClearText Browser//

    public BaseRemoteDriver clearText(WebElement element) {
        element.clear();
        return this;
    }

    // Enter Key//
    public BaseRemoteDriver enterKey(WebElement element) {
        element.sendKeys(Keys.ENTER);
        return this;

    }

    // Enter Key//
    public BaseRemoteDriver submit(WebElement element) {
        element.submit();
        return this;
    }

    // click on Element//
    public BaseRemoteDriver click(WebElement element) {
        element.click();
        return this;
    }

    // Element Displayed//
    public boolean isDisplayed(WebElement element) {
        try {
            boolean status = element.isDisplayed();
            if (status == true) {
                return true;
            }
        } catch (Exception e) {
            return false;

        }
        return false;
    }

    // Element Enabled//

    public boolean isEnabled(WebElement element) {
        boolean status = element.isEnabled();
        if (status == true) {
            return true;
        } else {
            return false;
        }
    }

    // Element Selected//
    public boolean isSelected(WebElement element) {
        boolean isSelected = element.isSelected();
        if (isSelected == true) {
            return true;
        } else {
            return false;
        }
    }

    // IsDisplayedElements for List WebElements
    public boolean isDisplayedElements(List<WebElement> elements, int index) {
        boolean isDisplayedElements = elements.get(index).isDisplayed();
        if (isDisplayedElements == true) {
            return true;
        } else {
            return false;
        }
    }

    // IsEnabledElements for List WebElements
    public BaseRemoteDriver isEnabledElements(List<WebElement> elements, int index) {
        boolean isEnabledElements = elements.get(index).isEnabled();
        if (isEnabledElements == true) {
            System.out.println("Element is enabled");
        } else {
            System.out.println("Element is disabled");
        }
        return this;
    }

    // IsSelectedElements for List WebElements
    public boolean isSelectedElements(List<WebElement> elements, int index) {
        boolean isSelectedElements = elements.get(index).isSelected();
        if (isSelectedElements == true) {
            return true;
        } else {
            return false;
        }
    }

    // Locators 1 //
    public static WebElement id(String locator) {
        WebElement element = getDriver().findElement(By.id(locator));
        return element;
    }

    public static WebElement cssSelector(String locator) {
        WebElement element = getDriver().findElement(By.cssSelector(locator));
        return element;
    }

    public static WebElement tagName(String locator) {
        WebElement element = getDriver().findElement(By.tagName(locator));
        return element;
    }

    public static WebElement name(String locator) {
        WebElement element = getDriver().findElement(By.name(locator));
        return element;
    }

    public static WebElement linkText(String locator) {
        WebElement element = getDriver().findElement(By.linkText(locator));
        return element;
    }

    public static WebElement partialLinkText(String locator) {
        WebElement element = getDriver().findElement(By.partialLinkText(locator));
        return element;
    }

    public static WebElement xpath(String locator) {
        WebElement element = getDriver().findElement(By.xpath(locator));
        return element;
    }

    public static WebElement className(String locator) {
        WebElement element = getDriver().findElement(By.className(locator));
        return element;
    }

    public static List<WebElement> elements(String xpath) {

        List<WebElement> elements = getDriver().findElements(By.xpath(xpath));
        return elements;
    }

    public static List<WebElement> getElements(String identifierType, String identifierValue) {

        switch (identifierType) {
            case "ID":
                return getDriver().findElements(By.id(identifierValue));

            case "TAGNAME":
                return getDriver().findElements(By.tagName(identifierValue));

            case "XPATH":
                return getDriver().findElements(By.xpath(identifierValue));
            default:
                return null;

        }
    }

    // GetTitle///
    public String getTitle() {

        String title = getDriver().getTitle();
        return title;
    }

    // Get Current URL///
    public String getCurrentURL() {

        String currentUrl = getDriver().getCurrentUrl();
        return currentUrl;
    }

    // Gettext//
    public String gettext(WebElement element) {
        String text = element.getText();
        return text;
    }

    // Gettext by Attribute//
    public String gettextByAttribute(WebElement element, String attributeName) {
        String text = element.getAttribute(attributeName);
        return text;
    }

    // Implicit Wait///
    public BaseRemoteDriver implicitWait(int time) {
        getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(time));
        return this;
    }

    // Explicit Wait//
    public BaseRemoteDriver waitToClick(WebElement element, int time) {
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(time));
        wait.until(ExpectedConditions.elementToBeClickable(element));
        return this;
    }

    public BaseRemoteDriver waitTovisibleElement(WebElement element, int time) {
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(time));
        wait.until(ExpectedConditions.visibilityOf(element));
        return this;
    }

    public BaseRemoteDriver waitAlertPresent(int time) {
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(time));
        wait.until(ExpectedConditions.alertIsPresent());
        return this;
    }


    public BaseRemoteDriver waitcontainTitle(int time, String title) {
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(time));
        wait.until(ExpectedConditions.titleContains(title));
        return this;

    }

    public BaseRemoteDriver waitcontaTitleIs(int time, String title) {
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(time));
        wait.until(ExpectedConditions.titleIs(title));
        return this;

    }

    public BaseRemoteDriver waitPresenceOfElement(int time, By element) {
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(time));
        wait.until(ExpectedConditions.presenceOfElementLocated(element));
        return this;

    }

    public BaseRemoteDriver waitPageLoadTimeout(int time) {
        getDriver().manage().timeouts().pageLoadTimeout(Duration.ofSeconds(time));
        return this;
    }

    // Explicit Wait 2//
    public static void explicitWait(WebElement element, String condition, int time) {

        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(time));
        try {
            if (condition.equalsIgnoreCase("visibility")) {
                wait.until(ExpectedConditions.visibilityOf(element));

            } else if (condition.equalsIgnoreCase("clickable")) {
                wait.until(ExpectedConditions.elementToBeClickable(element));

            } else if (condition.equalsIgnoreCase("selected")) {
                wait.until(ExpectedConditions.elementToBeSelected(element));

            } else if (condition.equalsIgnoreCase("alertpresent")) {
                wait.until(ExpectedConditions.alertIsPresent());

            } else
                System.out.println("Please enter proper explicit wait condition");
        } catch (NoSuchElementException e) {

        }


    }

    // Thread. Sleep///
    public BaseRemoteDriver Wait(long time) {
        try {
            Thread.sleep(time);

        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return this;
    }

    public BaseRemoteDriver screenShot(WebDriver driver) {

        try {
            TakesScreenshot ts = (TakesScreenshot) getDriver();

            File src = ts.getScreenshotAs(OutputType.FILE);

            FileUtils.copyFile(src, new File(".//ScreenShots" + System.currentTimeMillis() + ".png"));

            System.out.println("Screenshot taken");
        } catch (Exception e) {

            System.out.println("Exception while taking screenshot " + e.getMessage());
        }
        return this;
    }

    // Highlight Elements///
    public BaseRemoteDriver highLightElement(WebDriver driver, WebElement element) {
        JavascriptExecutor js = (JavascriptExecutor) getDriver();
        js.executeScript("arguments[0].setAttribute('style', 'background: yellow; border: 2px solid red;');", element);
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }
        js.executeScript("arguments[0].setAttribute('style','border: solid 2px white');", element);
        return this;
    }


    // Enter text//
    public BaseRemoteDriver enterText(WebElement element, String values) {
        element.sendKeys(values);
        return this;

    }

    // /Mouse Hover///
    public BaseRemoteDriver mouseHover(WebElement element) {
        Actions actions = new Actions(getDriver());
        actions.moveToElement(element).build().perform();
        return this;
    }

    public BaseRemoteDriver assertEquals(WebElement actual, String expected) {
        String act = actual.getText();
        Assert.assertEquals(act, expected);
        return this;

    }

    public BaseRemoteDriver assertEqualsByAttribute(WebElement element, String attribute, String expected) {
        String act = element.getAttribute(attribute);
        Assert.assertEquals(act, expected);
        return this;

    }

    public BaseRemoteDriver assertTrueContains(WebElement actual, String expected) {
        String act = actual.getText();
        Assert.assertTrue(act.contains(expected), "Text not matched");
        return this;
    }

    public BaseRemoteDriver assertTrueEquals(WebElement actual, String expected) {
        String act = actual.getText();
        Assert.assertTrue(act.equals(expected), "Text not matched");
        return this;
    }

    public BaseRemoteDriver assertTrueEqualsIgnoreCase(WebElement actual, String expected) {
        String act = actual.getText();
        Assert.assertTrue(act.equalsIgnoreCase(expected), "Text not matched");
        return this;
    }

    public BaseRemoteDriver assertTrue(boolean booleanValue) {
        Assert.assertTrue(booleanValue, "Condition not matched");
        return this;
    }

    public BaseRemoteDriver assertFalse(boolean booleanValue) {
        Assert.assertFalse(booleanValue, "Condition not matched");
        return this;
    }

    // select the dropdown using "select by visible text"//
    public BaseRemoteDriver dropDownByVisibleText(WebElement element, String visibleText) {
        Select sel = new Select(element);
        sel.selectByVisibleText(visibleText);
        return this;
    }

    // select the dropdown using "select by index"//
    public BaseRemoteDriver dropDownByIndex(WebElement element, int indexValue) {
        Select sel = new Select(element);
        sel.selectByIndex(indexValue);
        return this;
    }

    // select the dropdown using "select by value", //
    public BaseRemoteDriver dropDownByValue(WebElement element, String value) {
        Select sel = new Select(element);
        sel.selectByValue(value);
        return this;
    }

    // select the dropdown using "select by value", //
    public BaseRemoteDriver dropDownBootStrap(WebElement clickingElement, List<WebElement> ValueElement, String expectedValue) {
        click(clickingElement);
        for (WebElement ele : ValueElement) {
            String eleTxt = ele.getAttribute("innerHTML");
            System.out.println("Values are " + eleTxt);
            if (eleTxt.equalsIgnoreCase(expectedValue)) {
                ele.click();
                break;
            }
        }
        return this;
    }

    // DropDown values
    public String dropDownValues(WebElement element) {
        Select sel = new Select(element);
        List<WebElement> allValues = sel.getOptions();
        for (WebElement ele : allValues) {
            String AllDropValues = ele.getAttribute("innerHTML");
            return AllDropValues;

        }
        return null;
    }

    // DropDown First Values
    public String dropDownFirstSelectedValue(WebElement element) {
        Select dropdownValues = new Select(element);
        WebElement firstValue = dropdownValues.getFirstSelectedOption();
        System.out.println("First Value selected is " + firstValue.getText());
        String firVal = firstValue.getText();
        return firVal;
    }

    // DropDown Selected Values
    public BaseRemoteDriver dropDownSelectedValues(WebElement element) {
        Select dropValues = new Select(element);
        List<WebElement> allValueSelected = dropValues.getAllSelectedOptions();
        for (WebElement ele : allValueSelected) {
            System.out.println("Selected Values in Drop Down are " + ele.getText());
        }

        return this;
    }

    // Enter Date
    public String dateEnter(WebDriver driver, WebElement element, String dateValue) {
        JavascriptExecutor js = ((JavascriptExecutor) getDriver());
        js.executeScript("arguments[0].setAttribute('value','" + dateValue + "');", element);
        return dateValue;
    }

    // Dynamic Date
    public BaseRemoteDriver dateDynamic() throws InterruptedException {

        String date = "32-May-2017";
        String dateArr[] = date.split("-"); // {18,September,2017}
        String day = dateArr[0];
        String month = dateArr[1];
        String year = dateArr[2];

        Select select = new Select(getDriver().findElement(By.name("slctMonth")));
        select.selectByVisibleText(month);

        Select select1 = new Select(getDriver().findElement(By.name("slctYear")));
        select1.selectByVisibleText(year);

        // *[@id='crmcalendar']/table/tbody/tr[2]/td/table/tbody/tr[2]/td[1]
        // *[@id='crmcalendar']/table/tbody/tr[2]/td/table/tbody/tr[2]/td[2]
        // *[@id='crmcalendar']/table/tbody/tr[2]/td/table/tbody/tr[2]/td[6]

        String beforeXpath = "//*[@id='crmcalendar']/table/tbody/tr[2]/td/table/tbody/tr[";
        String afterXpath = "]/td[";

        final int totalWeekDays = 7;

        // 2-1 2-2 2-3 2-4 2-5 2-6 2-7
        // 3-2 3-2 3-3 3-4 3-5 3-6 3-7
        boolean flag = false;
        String dayVal = null;
        for (int rowNum = 2; rowNum <= 7; rowNum++) {

            for (int colNum = 1; colNum <= totalWeekDays; colNum++) {
                try {
                    dayVal = getDriver().findElement(By.xpath(beforeXpath + rowNum + afterXpath + colNum + "]")).getText();
                } catch (NoSuchElementException e) {
                    System.out.println("Please enter a correct date value");
                    flag = false;
                    break;
                }
                System.out.println(dayVal);
                if (dayVal.equals(day)) {
                    getDriver().findElement(By.xpath(beforeXpath + rowNum + afterXpath + colNum + "]")).click();
                    flag = true;
                    break;
                }
            }
            if (flag) {
                break;
            }
        }

        return this;
    }

    // DragandDrop//
    public BaseRemoteDriver dragandDrop(WebElement elementSrc, WebElement elementTarget) {
        Actions act = new Actions(getDriver());
        act.dragAndDrop(elementSrc, elementTarget).build().perform();
        return this;
    }

    // Radio button//
    public BaseRemoteDriver radioButtons(List<WebElement> elements, String value, String attribute) {
        for (WebElement element : elements) {
            String radios = element.getAttribute(attribute);
            System.out.println("Values are " + radios);
            if (radios.equals(value)) {
                element.click();
            }
        }

        return this;
    }

    // Radio Button values
    public BaseRemoteDriver radioButtonValues(List<WebElement> elements, String attribute) {
        for (WebElement eachele : elements) {
            String radioValues = eachele.getAttribute(attribute);
            System.out.println("Radio buttons values are " + radioValues);
        }

        return this;
    }

    // check boxes//
    public BaseRemoteDriver checkboxes(List<WebElement> elements, String value, String attribute) {
        List<String> list = new ArrayList<String>(Arrays.asList(value.split(",")));
        for (String check : list) {
            for (WebElement chk : elements) {
                if (chk.getAttribute(attribute).equalsIgnoreCase(check))
                    chk.click();
            }
        }

        return this;
    }

    // check boxes//
    public BaseRemoteDriver checkboxeSelectAll(List<WebElement> elements) {
        for (WebElement check : elements) {
            check.click();
        }

        return this;
    }

    // Checkboxes values
    public BaseRemoteDriver checkboxesValues(List<WebElement> elements, String attribute) {
        for (WebElement eachele : elements) {
            String checkValues = eachele.getAttribute(attribute);
            System.out.println("Checkboxes values are " + checkValues);
        }

        return this;
    }

    // Multi select Dropdown//
    public BaseRemoteDriver multiSelectSinglevalue(WebElement element, String values) {
        Select sel = new Select(element);
        List<WebElement> allopt = sel.getOptions();
        List<String> list = new ArrayList<String>(Arrays.asList(values.split(",")));
        for (String check : list) {
            sel.selectByVisibleText(check);
        }

        return this;
    }

    // Multi select All Valuesdropndown//
    public BaseRemoteDriver multiSelectAllValues(WebElement element) {
        Select sel = new Select(element);
        List<WebElement> allval = sel.getOptions();
        for (WebElement check : allval) {
            check.click();
        }
        return this;
    }

    // MultiSelect values
    public BaseRemoteDriver multiSelectvalues(WebElement element) {

        Select sel = new Select(element);
        List<WebElement> allValues = sel.getOptions();
        for (WebElement ele : allValues) {
            String allMultiValues = ele.getText();
            System.out.println("Multi Select values are " + allMultiValues);
        }
        return this;
    }

    // MultiSelect Selected Values
    public BaseRemoteDriver multiSelectedvaluesVerify(WebElement element) {
        Select dropValues = new Select(element);
        List<WebElement> allValueSelected = dropValues.getAllSelectedOptions();
        for (WebElement ele : allValueSelected) {
            System.out.println("Multi Selected Values in Drop Down are " + ele.getText());
        }

        return this;
    }

    // AutoComplete//
    public BaseRemoteDriver autoComplete(List<WebElement> elements, String expectedValue) {
        for (WebElement eachEle : elements) {
            String eachElement = eachEle.getAttribute("innerHTML");
            if (eachElement.equalsIgnoreCase(expectedValue)) {
                eachEle.click();
                break;
            }
        }
        return this;
    }

    // AutoComplete values
    public BaseRemoteDriver autoCompleteValues(List<WebElement> elements) {
        for (WebElement eachEle : elements) {
            String autoValues = eachEle.getAttribute("innerHTML");
            System.out.println("Values of Auto Complete fields are " + autoValues);
        }
        return this;
    }

    // UploadFile//
    public BaseRemoteDriver uploadFile(WebElement element, String path) {
        element.sendKeys(path);
        return this;
    }

    // Frames by name
    public BaseRemoteDriver framebyName(String value) {
        getDriver().switchTo().frame(value);
        return this;
    }

    // Frames by Index
    public BaseRemoteDriver framebyIndex(int value) {
        getDriver().switchTo().frame(value);
        return this;
    }

    // Frames by WebElement
    public BaseRemoteDriver framebyWebElement(WebElement element) {
        getDriver().switchTo().frame(element);
        return this;
    }

    // Frames Parent
    public BaseRemoteDriver frameParent() {
        getDriver().switchTo().parentFrame();
        return this;
    }

    // GetWindowHandlesCloseChildWindows
    public BaseRemoteDriver getWindowHandlesCloseChildWindows() {
        String mainWindow = getDriver().getWindowHandle();
        Set<String> s1 = getDriver().getWindowHandles();
        Iterator<String> iter = s1.iterator();
        while (iter.hasNext()) {
            String ChildWindow = iter.next();

            if (!mainWindow.equalsIgnoreCase(ChildWindow)) {
                getDriver().switchTo().window(ChildWindow);
                getDriver().close();
            }
            getDriver().switchTo().window(mainWindow);
        }

        return this;
    }

    // GetWindowHandle
    public BaseRemoteDriver getWindowHandle() {
        String mainWindow = getDriver().getWindowHandle();
        String homepage = getDriver().getWindowHandle();
        System.out.println("Home page id is " + homepage);
        return this;
    }

    // GetWindowHandles
    public BaseRemoteDriver getWindowHandles() {
        String Homepage = getDriver().getWindowHandle();
        System.out.println("Home page id is " + Homepage);
        Set<String> windows = getDriver().getWindowHandles();
        int noOfWindows = windows.size();
        System.out.println("Number of window opened is " + noOfWindows);
        return this;
    }

    public BaseRemoteDriver switchtoParentWindow() {
        Set<String> windows = getDriver().getWindowHandles();
        Iterator iter = windows.iterator();
        String parentwindow = iter.next().toString();
        System.out.println("current window id is " + parentwindow);
        getDriver().switchTo().window(parentwindow);
        return this;
    }

    public BaseRemoteDriver switchtoChildWindow() {
        Set<String> windows = getDriver().getWindowHandles();
        int noOfWindows = windows.size();
        System.out.println("Number of window opened is " + noOfWindows);
        Iterator iter = windows.iterator();
        String parentWindow = iter.next().toString();
        String childWindows = iter.next().toString();
        System.out.println("current window id is " + parentWindow);
        System.out.println("current window id is " + childWindows);
        getDriver().switchTo().window(childWindows);
        return this;
    }

    public BaseRemoteDriver iterator(String window) {

        Set<String> windows = getDriver().getWindowHandles();
        Iterator iter = windows.iterator();
        iter.next();
        return this;
    }

    // Click and Hold
    public BaseRemoteDriver clickandHold(WebElement element) {
        Actions action = new Actions(getDriver());
        action.clickAndHold(element);
        return this;
    }

    // Double Click
    public BaseRemoteDriver doubleClick(WebElement element) {
        Actions action = new Actions(getDriver());
        action.doubleClick(element).build().perform();
        return this;
    }

    // Release
    public BaseRemoteDriver releaseClick(WebElement element) {
        Actions action = new Actions(getDriver());
        action.release(element);
        return this;
    }

    // Right Click
    public BaseRemoteDriver rightClick(WebElement element) {
        Actions action = new Actions(getDriver());
        action.contextClick(element).build().perform();
        return this;
    }

    // Move to Element
    public BaseRemoteDriver movetoElement(WebElement element) {
        Actions action = new Actions(getDriver());
        action.moveToElement(element);
        return this;
    }

    // Action Click
    public BaseRemoteDriver clickAction(WebElement element) {
        Actions action = new Actions(getDriver());
        action.click(element).perform();
        return this;
    }

    public BaseRemoteDriver movetoEleClickAction(WebElement element) {
        Actions action = new Actions(getDriver());
        action.moveToElement(element).click().perform();
        return this;
    }

    public BaseRemoteDriver clickUsingJavascriptExecutor(WebElement element, WebDriver driver) {
        try {
            JavascriptExecutor javaScriptExecutor = (JavascriptExecutor) getDriver();
            // WebElement webElement = getDriver().findElement(locator);
            javaScriptExecutor.executeScript("arguments[0].click();", element);

        } catch (NoSuchElementException e) {

        }

        return this;

    }


    public BaseRemoteDriver scrollToElementUsingJavascriptExecutor(WebElement element, WebDriver driver) {
        try {
            JavascriptExecutor js = (JavascriptExecutor) getDriver();
            // WebElement webElement = getDriver().findElement(locator);
            js.executeScript("arguments[0].scrollIntoView(true);", element);
        } catch (NoSuchElementException e) {

        }

        return this;

    }

    public BaseRemoteDriver scrollDownUsingJavascriptExecutor() {
        JavascriptExecutor jse = (JavascriptExecutor) getDriver();
        jse.executeScript("window.scrollTo(0, document.body.scrollHeight)");
        return this;
    }

    public BaseRemoteDriver scrollUpUsingJavascriptExecutor() {
        JavascriptExecutor jse = (JavascriptExecutor) getDriver();
        jse.executeScript("window.scrollBy(0,-250)");
        return this;
    }

}

