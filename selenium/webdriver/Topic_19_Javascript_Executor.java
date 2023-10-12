package webdriver;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Topic_19_Javascript_Executor {
    WebDriver driver;
    JavascriptExecutor jsExecutor;
    String projectPath = System.getProperty("user.dir");
    String osName = System.getProperty("os.name");

    @BeforeClass
    public void beforeClass() {
        if (osName.contains("Windows")) {
            System.setProperty("webdriver.gecko.driver", projectPath + "\\browserDrivers\\geckodriver.exe");
        } else {
            System.setProperty("webdriver.gecko.driver", projectPath + "/browserDrivers/geckodriver");
        }

        driver = new FirefoxDriver();
        jsExecutor = (JavascriptExecutor) driver;

        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        driver.manage().timeouts().setScriptTimeout(30,TimeUnit.SECONDS);

    }

    @Test
    public void TC_01_TechPanda() {
        driver.get("http://live.techpanda.org/");

        String email = "testing" + getRandomNumber() + "@hotmail.com";
        navigateToUrlByJS("http://live.techpanda.org/");
        sleepInSecond(5);

        Assert.assertEquals(executeForBrowser("return document.domain;"),"live.techpanda.org");
        Assert.assertEquals(executeForBrowser("return document.URL;"),"http://live.techpanda.org/");

        clickToElementByJS("//a[text()='Mobile']");
        sleepInSecond(3);

        clickToElementByJS("//a[text()='Samsung Galaxy']/parent::h2/following-sibling::div[@class='actions']/button");
        sleepInSecond(2);

        Assert.assertTrue(getInnerText().contains("Samsung Galaxy was added to your shopping cart."));
        Assert.assertTrue(areExpectedTextInInnerText("Samsung Galaxy was added to your shopping cart."));

        clickToElementByJS("//a[text()='Customer Service']");
        sleepInSecond(3);

        Assert.assertEquals(executeForBrowser("return document.title;"),"Customer Service");

        scrollToElementOnDown("//input[@name='email']");
        sleepInSecond(3);

        sendkeyToElementByJS("//input[@name='email']",email);
        sleepInSecond(2);

        clickToElementByJS("//button[@title='Subscribe']");
        sleepInSecond(3);

        Assert.assertTrue(getInnerText().contains("Thank you for your subscription."));
        Assert.assertTrue(areExpectedTextInInnerText("Thank you for your subscription."));

        navigateToUrlByJS("https://demo.guru99.com/v4/");
        sleepInSecond(3);

        Assert.assertEquals(executeForBrowser("return document.domain;"),"demo.guru99.com");
        Assert.assertEquals(executeForBrowser("return document.URL;"),"https://demo.guru99.com/v4/");


    }

    @Test
    public void TC_02_Rode() {
        driver.get("https://warranty.rode.com/login");

        By createAccountButton = By.xpath("//a[contains(text(),'Create an Account')]");
        By registerButton = By.xpath("//button[text()=' Register ']");

        driver.findElement(createAccountButton).click();
        sleepInSecond(3);

        Assert.assertEquals(getElementValidationMessage("//input[@id='name']"),"Vui lòng điền vào trường này.");
        getElement("//input[@id='name']").sendKeys("Automation");

        driver.findElement(registerButton).click();
        sleepInSecond(3);

        Assert.assertEquals(getElementValidationMessage("//input[@id='email']"),"Vui lòng điền vào trường này.");
        getElement("//input[@id='email']").sendKeys("FC");

        driver.findElement(registerButton).click();
        sleepInSecond(3);

        Assert.assertEquals(getElementValidationMessage("//input[@id='password']"),"Vui lòng điền vào trường này.");
        getElement("//input[@id='password']").sendKeys("123456789");

        Assert.assertEquals(getElementValidationMessage("//input[@id='password_confirmation']"),"Vui lòng điền vào trường này.");
        getElement("//input[@id='password_confirmation']").sendKeys("123456789");



    }

    public Object executeForBrowser(String javaScript) {
        return jsExecutor.executeScript(javaScript);
    }

    public String getInnerText() {
        return (String) jsExecutor.executeScript("return document.documentElement.innerText;");
    }

    public boolean areExpectedTextInInnerText(String textExpected) {
        String textActual = (String) jsExecutor.executeScript("return document.documentElement.innerText.match('" + textExpected + "')[0];");
        return textActual.equals(textExpected);
    }

    public void scrollToBottomPage() {
        jsExecutor.executeScript("window.scrollBy(0,document.body.scrollHeight)");
    }

    public void navigateToUrlByJS(String url) {
        jsExecutor.executeScript("window.location = '" + url + "'");
        sleepInSecond(3);
    }

    public void hightlightElement(String xpath_locator) {
        WebElement element = getElement(xpath_locator);
        String originalStyle = element.getAttribute("style");
        jsExecutor.executeScript("arguments[0].setAttribute('style', arguments[1])", element, "border: 2px solid red; border-style: dashed;");
        sleepInSecond(2);
        jsExecutor.executeScript("arguments[0].setAttribute('style', arguments[1])", element, originalStyle);
    }

    public void clickToElementByJS(String xpath_locator) {
        jsExecutor.executeScript("arguments[0].click();", getElement(xpath_locator));
        sleepInSecond(3);
    }

    public void scrollToElementOnTop(String xpath_locator) {
        jsExecutor.executeScript("arguments[0].scrollIntoView(true);", getElement(xpath_locator));
    }

    public void scrollToElementOnDown(String xpath_locator) {
        jsExecutor.executeScript("arguments[0].scrollIntoView(false);", getElement(xpath_locator));
    }

    public void setAttributeInDOM(String xpath_locator, String attributeName, String attributeValue) {
        jsExecutor.executeScript("arguments[0].setAttribute('" + attributeName + "', '" + attributeValue +"');", getElement(xpath_locator));
    }

    public void removeAttributeInDOM(String xpath_locator, String attributeRemove) {
        jsExecutor.executeScript("arguments[0].removeAttribute('" + attributeRemove + "');", getElement(xpath_locator));
    }

    public void sendkeyToElementByJS(String xpath_locator, String value) {
        jsExecutor.executeScript("arguments[0].setAttribute('value', '" + value + "')", getElement(xpath_locator));
    }

    public String getAttributeInDOM(String xpath_locator, String attributeName) {
        return (String) jsExecutor.executeScript("return arguments[0].getAttribute('" + attributeName + "');", getElement(xpath_locator));
    }

    public String getElementValidationMessage(String xpath_locator) {
        return (String) jsExecutor.executeScript("return arguments[0].validationMessage;", getElement(xpath_locator));
    }

    public boolean isImageLoaded(String xpath_locator) {
        boolean status = (boolean) jsExecutor.executeScript(
                "return arguments[0].complete && typeof arguments[0].naturalWidth != 'undefined' && arguments[0].naturalWidth > 0", getElement(xpath_locator));
        return status;
    }

    public WebElement getElement(String xpath_locator) {
        return driver.findElement(By.xpath(xpath_locator));
    }

    public void sleepInSecond (long timeInSecond){
        try {
            Thread.sleep(timeInSecond * 1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public int getRandomNumber(){
        Random ran = new Random();
        return ran.nextInt(99999);
    }


    @AfterClass
    public void afterClass() {
        driver.quit();
    }
}