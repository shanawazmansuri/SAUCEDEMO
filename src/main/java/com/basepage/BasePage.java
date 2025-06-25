package com.basepage;

import com.config.PropFile;
import com.utilities.ExtentReportConf;
import com.utilities.WebEventListener;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.io.File;
import java.net.MalformedURLException;
import java.time.Duration;
import java.util.*;


public class BasePage {

    public static WebDriver driver;
    public static EventFiringWebDriver e_driver;
    public static WebEventListener eventListener;
    public static String path = System.getProperty("user.dir");
    public String className = this.getClass().getSimpleName();
    public PropFile pro = new PropFile();

    public WebDriver browser(String browsername, String url) {

        if (browsername.equalsIgnoreCase("firefox")) {
            WebDriverManager.firefoxdriver().setup();
            driver = new FirefoxDriver();
        } else if (browsername.equalsIgnoreCase("chrome")) {
            WebDriverManager.chromedriver().setup();
            ChromeOptions opt = new ChromeOptions();
            opt.addArguments("--remote-allow-origins=*");
            driver = new ChromeDriver(opt);
        } else if (browsername.equalsIgnoreCase("IE")) {

            WebDriverManager.iedriver().setup();
            driver = new InternetExplorerDriver();
        } else if (browsername.equalsIgnoreCase("edge")) {

            WebDriverManager.edgedriver().setup();
            driver = new EdgeDriver();

        }

        e_driver = new EventFiringWebDriver(driver);
        // Now create object of EventListerHandler to register it with
        // EventFiringWebDriver
        eventListener = new WebEventListener();
        e_driver.register(eventListener);
        driver = e_driver;
        driver.get(url);
        return driver;
    }


    // Headless the Browser//
    public WebDriver headlessBrowser(String browserName, String url) {

        if (browserName.equalsIgnoreCase("firefox")) {
            WebDriverManager.firefoxdriver().setup();
            FirefoxBinary firefoxBinary = new FirefoxBinary();
            firefoxBinary.addCommandLineOptions("--headless");
            FirefoxOptions firefoxOptions = new FirefoxOptions();
            firefoxOptions.setBinary(firefoxBinary);
            driver = new FirefoxDriver(firefoxOptions);

        } else if (browserName.equalsIgnoreCase("chrome")) {
            WebDriverManager.chromedriver().setup();
            ChromeOptions opt = new ChromeOptions();
            opt.addArguments("window-size=1400,800");
            opt.addArguments("headless");
            driver = new ChromeDriver(opt);

        }
        e_driver = new EventFiringWebDriver(driver);
        // Now create object of EventListerHandler to register it with
        // EventFiringWebDriver
        eventListener = new WebEventListener();
        e_driver.register(eventListener);
        driver = e_driver;
        driver.get(url);
        return driver;
    }

    // Without Image Browser

    public WebDriver imagelessBrowser(String browserName, String url) {

        if (browserName.equalsIgnoreCase("firefox")) {
            WebDriverManager.firefoxdriver().setup();
            FirefoxBinary firefoxBinary = new FirefoxBinary();
            firefoxBinary.addCommandLineOptions("--headless");
            FirefoxOptions firefoxOptions = new FirefoxOptions();
            firefoxOptions.setBinary(firefoxBinary);
            driver = new FirefoxDriver(firefoxOptions);

        } else if (browserName.equalsIgnoreCase("chrome")) {
            WebDriverManager.chromedriver().setup();
            ChromeOptions opt = new ChromeOptions();
            disbaleImageChrome(opt);
            driver = new ChromeDriver(opt);

        }
        e_driver = new EventFiringWebDriver(driver);
        // Now create object of EventListerHandler to register it with
        // EventFiringWebDriver
        eventListener = new WebEventListener();
        e_driver.register(eventListener);
        driver = e_driver;
        driver.get(url);
        return driver;
    }

    public static void disbaleImageChrome(ChromeOptions options) {
        HashMap<String, Object> images = new HashMap<String, Object>();
        images.put("images", 2);
        HashMap<String, Object> prefs = new HashMap<String, Object>();
        prefs.put("profile.default_content_setting_values", images);
        options.setExperimentalOption("prefs", prefs);
    }

    public WebDriver existingBrowser() {

        WebDriverManager.chromedriver().setup();
        ChromeOptions opt = new ChromeOptions();
        opt.setExperimentalOption("debuggerAddress", "localhost:9898");
        driver = new ChromeDriver(opt);
        return driver;

    }

    // Quiting the Browser//
    public BasePage quitBrowser() {
        driver.quit();
        return this;
    }

    // Closing the Browser Tab//
    public BasePage closeBrowser() {
        driver.close();
        return this;
    }

    // Maximize Browser//
    public BasePage maximizeBrowser() {
        driver.manage().window().maximize();
        return this;
    }

    // Minimize Browser//
    public BasePage minimizeBrowser() {
        driver.manage().window().setPosition(new Point(-2000, 0));
        return this;
    }

    // ResizeBrowser Browser//
    public BasePage resizeBrowser() {
        Dimension d = new Dimension(800, 480);
        driver.manage().window().setSize(d);
        return this;
    }

    // Full Screen//
    public BasePage fullScreen() {
        driver.manage().window().fullscreen();
        return this;
    }

    // Delete Cookies//
    public BasePage deleteCookies() {
        driver.manage().deleteAllCookies();
        return this;
    }

    // Navigate//
    public BasePage navigate(String url) {
        driver.navigate().to(url);
        return this;
    }

    // Back//
    public BasePage back() {
        driver.navigate().back();
        return this;
    }

    // Forward//
    public BasePage forward() {
        driver.navigate().forward();
        return this;
    }

    // Open Tab in Browser//
    public BasePage openTab() {
        driver.switchTo().newWindow(WindowType.TAB);
        return this;

    }

    // Open New Window//
    public BasePage openNewWindow() {
        driver.switchTo().newWindow(WindowType.WINDOW);
        return this;
    }

    // Accept Alert//
    public BasePage alertAccept() {
        driver.switchTo().alert().accept();
        return this;

    }

    // Dismiss Alert//
    public BasePage alertDismiss() {
        driver.switchTo().alert().dismiss();
        return this;

    }

    // Alert getText//
    public String alertGetText() {
        String alertText = driver.switchTo().alert().getText();
        return alertText;

    }

    // Alert EnterText //
    public BasePage alertEnterText(String values) {
        driver.switchTo().alert().sendKeys(values);
        return this;

    }

    // Refresing Browser

    public BasePage refresh() {
        driver.navigate().refresh();
        return this;
    }

    // ClearText Browser//

    public BasePage clearText(WebElement element) {
        element.clear();
        return this;
    }

    // Enter Key//
    public BasePage enterKey(WebElement element) {
        element.sendKeys(Keys.ENTER);
        return this;

    }

    // Enter Key//
    public BasePage submit(WebElement element) {
        element.submit();
        return this;
    }

    // click on Element//
    public BasePage click(WebElement element) {
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
    public BasePage isEnabledElements(List<WebElement> elements, int index) {
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
        WebElement element = driver.findElement(By.id(locator));
        return element;
    }

    public static WebElement cssSelector(String locator) {
        WebElement element = driver.findElement(By.cssSelector(locator));
        return element;
    }

    public static WebElement tagName(String locator) {
        WebElement element = driver.findElement(By.tagName(locator));
        return element;
    }

    public static WebElement name(String locator) {
        WebElement element = driver.findElement(By.name(locator));
        return element;
    }

    public static WebElement linkText(String locator) {
        WebElement element = driver.findElement(By.linkText(locator));
        return element;
    }

    public static WebElement partialLinkText(String locator) {
        WebElement element = driver.findElement(By.partialLinkText(locator));
        return element;
    }

    public static WebElement xpath(String locator) {
        WebElement element = driver.findElement(By.xpath(locator));
        return element;
    }

    public static WebElement className(String locator) {
        WebElement element = driver.findElement(By.className(locator));
        return element;
    }

    public static List<WebElement> elements(String xpath) {

        List<WebElement> elements = driver.findElements(By.xpath(xpath));
        return elements;
    }

    public WebElement getElement(String identifierType, String identifierValue) {

        switch (identifierType) {
            case "ID":
                return driver.findElement(By.id(identifierValue));

            case "CSS":
                return driver.findElement(By.cssSelector(identifierValue));

            case "TAGNAME":
                return driver.findElement(By.tagName(identifierValue));

            case "XPATH":
                return driver.findElement(By.xpath(identifierValue));

            case "NAME":
                return driver.findElement(By.name(identifierValue));

            case "CLASSNAME":
                return driver.findElement(By.className(identifierValue));
            default:
                return null;

        }
    }

    public List<WebElement> getElements(String identifierType, String identifierValue) {

        switch (identifierType) {
            case "ID":
                return driver.findElements(By.id(identifierValue));

            case "TAGNAME":
                return driver.findElements(By.tagName(identifierValue));

            case "XPATH":
                return driver.findElements(By.xpath(identifierValue));
            default:
                return null;

        }
    }

    // GetTitle///
    public String getTitle() {

        String title = driver.getTitle();
        return title;
    }

    // Get Current URL///
    public String getCurrentURL() {

        String currentUrl = driver.getCurrentUrl();
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
    public BasePage implicitWait(int time) {
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(time));
        return this;
    }

    // Explicit Wait//
    public BasePage waitToClick(WebElement element, int time) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(time));
        wait.until(ExpectedConditions.elementToBeClickable(element));
        return this;
    }

    public BasePage waitTovisibleElement(WebElement element, int time) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(time));
        wait.until(ExpectedConditions.visibilityOf(element));
        return this;
    }

    public BasePage waitAlertPresent(int time) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(time));
        wait.until(ExpectedConditions.alertIsPresent());
        return this;
    }


    public BasePage waitcontainTitle(int time, String title) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(time));
        wait.until(ExpectedConditions.titleContains(title));
        return this;

    }

    public BasePage waitcontaTitleIs(int time, String title) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(time));
        wait.until(ExpectedConditions.titleIs(title));
        return this;

    }

    public BasePage waitPresenceOfElement(int time, By element) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(time));
        wait.until(ExpectedConditions.presenceOfElementLocated(element));
        return this;

    }

    public BasePage waitPageLoadTimeout(int time) {
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(time));
        return this;
    }

    // Explicit Wait 2//
    public static void explicitWait(WebElement element, String condition, int time) {

        WebDriverWait webDriverWait = new WebDriverWait(driver, Duration.ofSeconds(time));
        try {
            if (condition.equalsIgnoreCase("visibility")) {
                webDriverWait.until(ExpectedConditions.visibilityOf(element));

            } else if (condition.equalsIgnoreCase("clickable")) {
                webDriverWait.until(ExpectedConditions.elementToBeClickable(element));

            } else if (condition.equalsIgnoreCase("selected")) {
                webDriverWait.until(ExpectedConditions.elementToBeSelected(element));

            } else if (condition.equalsIgnoreCase("alertpresent")) {
                webDriverWait.until(ExpectedConditions.alertIsPresent());

            } else
                System.out.println("Please enter proper explicit wait condition");
        } catch (NoSuchElementException e) {

        }


    }

    // Thread. Sleep///
    public BasePage Wait(long time) {
        try {
            Thread.sleep(time);

        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return this;
    }

    public BasePage screenShot(WebDriver driver) {

        try {
            TakesScreenshot ts = (TakesScreenshot) driver;

            File src = ts.getScreenshotAs(OutputType.FILE);

            FileUtils.copyFile(src, new File(".//ScreenShots" + System.currentTimeMillis() + ".png"));

            System.out.println("Screenshot taken");
        } catch (Exception e) {

            System.out.println("Exception while taking screenshot " + e.getMessage());
        }
        return this;
    }

    // Highlight Elements///
    public BasePage highLightElement(WebDriver driver, WebElement element) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
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
    public BasePage enterText(WebElement element, String values) {
        element.sendKeys(values);
        return this;

    }

    // /Mouse Hover///
    public BasePage mouseHover(WebElement element) {
        Actions actions = new Actions(driver);
        actions.moveToElement(element).build().perform();
        return this;
    }

    public BasePage assertEquals(WebElement actual, String expected) {
        String act = actual.getText();
        Assert.assertEquals(act, expected);
        return this;

    }

    public BasePage assertEqualsByAttribute(WebElement element, String attribute, String expected) {
        String act = element.getAttribute(attribute);
        Assert.assertEquals(act, expected);
        return this;

    }

    public BasePage assertTrueContains(WebElement actual, String expected) {
        String act = actual.getText();
        Assert.assertTrue(act.contains(expected), "Text not matched");
        return this;
    }

    public BasePage assertTrueEquals(WebElement actual, String expected) {
        String act = actual.getText();
        Assert.assertTrue(act.equals(expected), "Text not matched");
        return this;
    }

    public BasePage assertTrueEqualsIgnoreCase(WebElement actual, String expected) {
        String act = actual.getText();
        Assert.assertTrue(act.equalsIgnoreCase(expected), "Text not matched");
        return this;
    }

    public BasePage assertTrue(boolean booleanValue) {
        Assert.assertTrue(booleanValue, "Condition not matched");
        return this;
    }

    public BasePage assertFalse(boolean booleanValue) {
        Assert.assertFalse(booleanValue, "Condition not matched");
        return this;
    }

    // select the dropdown using "select by visible text"//
    public BasePage dropDownByVisibleText(WebElement element, String visibleText) {
        Select sel = new Select(element);
        sel.selectByVisibleText(visibleText);
        return this;
    }

    // select the dropdown using "select by index"//
    public BasePage dropDownByIndex(WebElement element, int indexValue) {
        Select sel = new Select(element);
        sel.selectByIndex(indexValue);
        return this;
    }

    // select the dropdown using "select by value", //
    public BasePage dropDownByValue(WebElement element, String value) {
        Select sel = new Select(element);
        sel.selectByValue(value);
        return this;
    }

    // select the dropdown using "select by value", //
    public BasePage dropDownBootStrap(WebElement clickingElement, List<WebElement> ValueElement, String expectedValue) {
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
    public BasePage dropDownSelectedValues(WebElement element) {
        Select dropValues = new Select(element);
        List<WebElement> allValueSelected = dropValues.getAllSelectedOptions();
        for (WebElement ele : allValueSelected) {
            System.out.println("Selected Values in Drop Down are " + ele.getText());
        }

        return this;
    }

    // Enter Date
    public String dateEnter(WebDriver driver, WebElement element, String dateValue) {
        JavascriptExecutor js = ((JavascriptExecutor) driver);
        js.executeScript("arguments[0].setAttribute('value','" + dateValue + "');", element);
        return dateValue;
    }

    // Dynamic Date
    public BasePage dateDynamic() throws InterruptedException {

        String date = "32-May-2017";
        String dateArr[] = date.split("-"); // {18,September,2017}
        String day = dateArr[0];
        String month = dateArr[1];
        String year = dateArr[2];

        Select select = new Select(driver.findElement(By.name("slctMonth")));
        select.selectByVisibleText(month);

        Select select1 = new Select(driver.findElement(By.name("slctYear")));
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
                    dayVal = driver.findElement(By.xpath(beforeXpath + rowNum + afterXpath + colNum + "]")).getText();
                } catch (NoSuchElementException e) {
                    System.out.println("Please enter a correct date value");
                    flag = false;
                    break;
                }
                System.out.println(dayVal);
                if (dayVal.equals(day)) {
                    driver.findElement(By.xpath(beforeXpath + rowNum + afterXpath + colNum + "]")).click();
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
    public BasePage dragandDrop(WebElement elementSrc, WebElement elementTarget) {
        Actions act = new Actions(driver);
        act.dragAndDrop(elementSrc, elementTarget).build().perform();
        return this;
    }

    // Radio button//
    public BasePage radioButtons(List<WebElement> elements, String value, String attribute) {
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
    public BasePage radioButtonValues(List<WebElement> elements, String attribute) {
        for (WebElement eachele : elements) {
            String radioValues = eachele.getAttribute(attribute);
            System.out.println("Radio buttons values are " + radioValues);
        }

        return this;
    }

    // check boxes//
    public BasePage checkboxes(List<WebElement> elements, String value, String attribute) {
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
    public BasePage checkboxeSelectAll(List<WebElement> elements) {
        for (WebElement check : elements) {
            check.click();
        }

        return this;
    }

    // Checkboxes values
    public BasePage checkboxesValues(List<WebElement> elements, String attribute) {
        for (WebElement eachele : elements) {
            String checkValues = eachele.getAttribute(attribute);
            System.out.println("Checkboxes values are " + checkValues);
        }

        return this;
    }

    // Multi select Dropdown//
    public BasePage multiSelectSinglevalue(WebElement element, String values) {
        Select sel = new Select(element);
        List<WebElement> allopt = sel.getOptions();
        List<String> list = new ArrayList<String>(Arrays.asList(values.split(",")));
        for (String check : list) {
            sel.selectByVisibleText(check);
        }

        return this;
    }

    // Multi select All Valuesdropndown//
    public BasePage multiSelectAllValues(WebElement element) {
        Select sel = new Select(element);
        List<WebElement> allval = sel.getOptions();
        for (WebElement check : allval) {
            check.click();
        }
        return this;
    }

    // MultiSelect values
    public BasePage multiSelectvalues(WebElement element) {

        Select sel = new Select(element);
        List<WebElement> allValues = sel.getOptions();
        for (WebElement ele : allValues) {
            String allMultiValues = ele.getText();
            System.out.println("Multi Select values are " + allMultiValues);
        }
        return this;
    }

    // MultiSelect Selected Values
    public BasePage multiSelectedvaluesVerify(WebElement element) {
        Select dropValues = new Select(element);
        List<WebElement> allValueSelected = dropValues.getAllSelectedOptions();
        for (WebElement ele : allValueSelected) {
            System.out.println("Multi Selected Values in Drop Down are " + ele.getText());
        }

        return this;
    }

    // AutoComplete//
    public BasePage autoComplete(List<WebElement> elements, String expectedValue) {
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
    public BasePage autoCompleteValues(List<WebElement> elements) {
        for (WebElement eachEle : elements) {
            String autoValues = eachEle.getAttribute("innerHTML");
            System.out.println("Values of Auto Complete fields are " + autoValues);
        }
        return this;
    }

    // UploadFile//
    public BasePage uploadFile(WebElement element, String path) {
        element.sendKeys(path);
        return this;
    }

    // Frames by name
    public BasePage framebyName(String value) {
        driver.switchTo().frame(value);
        return this;
    }

    // Frames by Index
    public BasePage framebyIndex(int value) {
        driver.switchTo().frame(value);
        return this;
    }

    // Frames by WebElement
    public BasePage framebyWebElement(WebElement element) {
        driver.switchTo().frame(element);
        return this;
    }

    // Frames Parent
    public BasePage frameParent() {
        driver.switchTo().parentFrame();
        return this;
    }

    // GetWindowHandlesCloseChildWindows
    public BasePage getWindowHandlesCloseChildWindows() {
        String mainWindow = driver.getWindowHandle();
        Set<String> s1 = driver.getWindowHandles();
        Iterator<String> iter = s1.iterator();
        while (iter.hasNext()) {
            String ChildWindow = iter.next();

            if (!mainWindow.equalsIgnoreCase(ChildWindow)) {
                driver.switchTo().window(ChildWindow);
                driver.close();
            }
            driver.switchTo().window(mainWindow);
        }

        return this;
    }

    // GetWindowHandle
    public BasePage getWindowHandle() {
        String mainWindow = driver.getWindowHandle();
        String homepage = driver.getWindowHandle();
        System.out.println("Home page id is " + homepage);
        return this;
    }

    // GetWindowHandles
    public BasePage getWindowHandles() {
        String Homepage = driver.getWindowHandle();
        System.out.println("Home page id is " + Homepage);
        Set<String> windows = driver.getWindowHandles();
        int noOfWindows = windows.size();
        System.out.println("Number of window opened is " + noOfWindows);
        return this;
    }

    public BasePage switchtoParentWindow() {
        Set<String> windows = driver.getWindowHandles();
        Iterator iter = windows.iterator();
        String parentwindow = iter.next().toString();
        System.out.println("current window id is " + parentwindow);
        driver.switchTo().window(parentwindow);
        return this;
    }

    public BasePage switchtoChildWindow() {
        Set<String> windows = driver.getWindowHandles();
        int noOfWindows = windows.size();
        System.out.println("Number of window opened is " + noOfWindows);
        Iterator iter = windows.iterator();
        String parentWindow = iter.next().toString();
        String childWindows = iter.next().toString();
        System.out.println("current window id is " + parentWindow);
        System.out.println("current window id is " + childWindows);
        driver.switchTo().window(childWindows);
        return this;
    }

    public BasePage iterator(String window) {

        Set<String> windows = driver.getWindowHandles();
        Iterator iter = windows.iterator();
        iter.next();
        return this;
    }

    // Click and Hold
    public BasePage clickandHold(WebElement element) {
        Actions action = new Actions(driver);
        action.clickAndHold(element);
        return this;
    }

    // Double Click
    public BasePage doubleClick(WebElement element) {
        Actions action = new Actions(driver);
        action.doubleClick(element).build().perform();
        return this;
    }

    // Release
    public BasePage releaseClick(WebElement element) {
        Actions action = new Actions(driver);
        action.release(element);
        return this;
    }

    // Right Click
    public BasePage rightClick(WebElement element) {
        Actions action = new Actions(driver);
        action.contextClick(element).build().perform();
        return this;
    }

    // Move to Element
    public BasePage movetoElement(WebElement element) {
        Actions action = new Actions(driver);
        action.moveToElement(element);
        return this;
    }

    // Action Click
    public BasePage clickAction(WebElement element) {
        Actions action = new Actions(driver);
        action.click(element).perform();
        return this;
    }

    public BasePage movetoEleClickAction(WebElement element) {
        Actions action = new Actions(driver);
        action.moveToElement(element).click().perform();
        return this;
    }

    public BasePage clickUsingJavascriptExecutor(WebElement element, WebDriver driver) {
        try {
            JavascriptExecutor javaScriptExecutor = (JavascriptExecutor) driver;
            // WebElement webElement = driver.findElement(locator);
            javaScriptExecutor.executeScript("arguments[0].click();", element);

        } catch (NoSuchElementException e) {

        }

        return this;

    }

    /**
     * This function is to scroll the browser window to a webelement using
     * JavascriptExecutor
     *
     * @param locator - By object of the webelement to which the window has to be
     *                scrolled
     * @param driver
     */
    public BasePage scrollToElementUsingJavascriptExecutor(WebElement element, WebDriver driver) {
        try {
            JavascriptExecutor js = (JavascriptExecutor) driver;
            // WebElement webElement = driver.findElement(locator);
            js.executeScript("arguments[0].scrollIntoView(true);", element);
        } catch (NoSuchElementException e) {

        }

        return this;

    }

    public BasePage scrollDownUsingJavascriptExecutor() {
        JavascriptExecutor jse = (JavascriptExecutor) driver;
        jse.executeScript("window.scrollTo(0, document.body.scrollHeight)");
        return this;
    }

    public BasePage scrollUpUsingJavascriptExecutor() {
        JavascriptExecutor jse = (JavascriptExecutor) driver;
        jse.executeScript("window.scrollBy(0,-250)");
        return this;
    }

    @BeforeMethod
    public BasePage setUp() throws MalformedURLException {

        ExtentReportConf.reportSetUp(className);
        browser(pro.prop.getProperty("browser"), (pro.prop.getProperty("url")));
        ExtentReportConf.reportInfoLog("Opened Browser Successfully");
        maximizeBrowser();
        ExtentReportConf.reportInfoLog("Maximized the Browser");
        implicitWait(10);
        waitPageLoadTimeout(60);
        return this;

    }

    @AfterMethod
    public BasePage tearDown() {
       // quitBrowser();
        ExtentReportConf.reportInfoLog("Browser Closed Successfully");
        ExtentReportConf.reportTearDown();
        return this;
    }

}