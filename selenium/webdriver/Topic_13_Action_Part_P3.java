package webdriver;

import org.openqa.selenium.*;
import org.openqa.selenium.Point;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.Color;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.awt.*;
import java.awt.event.InputEvent;
import java.io.*;
import java.nio.charset.Charset;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.awt.AWTException;
import java.awt.Robot;


public class Topic_13_Action_Part_P3 {
    WebDriver driver;
    Actions action;
    JavascriptExecutor jsExecutor;
    String projectPath = System.getProperty("user.dir");
    String osName = System.getProperty("os.name");
    String dragAndDropHelperPath = projectPath + "\\dragAndDrop\\drag_and_drop_helper.js";

    @BeforeClass
    public void beforeClass() {
        if (osName.contains("Windows")) {
            System.setProperty("webdriver.chrome.driver", projectPath + "\\browserDrivers\\chromedriver.exe");
        } else {
            System.setProperty("webdriver.chrome.driver", projectPath + "/browserDrivers/chromedriver");
        }

        driver = new FirefoxDriver();
        action = new Actions(driver);
        jsExecutor = (JavascriptExecutor) driver;
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        driver.manage().window().maximize();
    }

    @Test
    public void TC_01_Double_Click() {
        driver.get("https://automationfc.github.io/basic-form/index.html");

        // Scroll đến element đó
        jsExecutor.executeScript("arguments[0].scrollIntoView(true);", driver.findElement(By.xpath("//button[text()='Double click me']")));
        sleepInSecond(2);

        action.doubleClick(driver.findElement(By.xpath("//button[text()='Double click me']"))).perform();
        sleepInSecond(2);

        Assert.assertEquals(driver.findElement(By.cssSelector("p#demo")).getText(),"Hello Automation Guys!");


    }

    @Test
    public void TC_02_Right_Click() {
        driver.get("http://swisnl.github.io/jQuery-contextMenu/demo.html");

        action.contextClick(driver.findElement(By.xpath("//span[text()='right click me']"))).perform();
        sleepInSecond(3);
        Assert.assertTrue(driver.findElement(By.cssSelector("li.context-menu-icon-quit")).isDisplayed());

        action.moveToElement(driver.findElement(By.cssSelector("li.context-menu-icon-quit"))).perform();
        sleepInSecond(2);
        Assert.assertTrue(driver.findElement(By.cssSelector("li.context-menu-icon-quit.context-menu-visible")).isDisplayed());

        action.click(driver.findElement(By.cssSelector("li.context-menu-icon-quit"))).perform();
        sleepInSecond(2);

        driver.switchTo().alert().accept();
        sleepInSecond(2);
        Assert.assertFalse(driver.findElement(By.cssSelector("li.context-menu-icon-quit")).isDisplayed());

    }

    @Test
    public void TC_03_Drag_And_Drop_HTML4() {
        driver.get("https://automationfc.github.io/kendo-drag-drop/");

        WebElement smallCircle = driver.findElement(By.id("draggable"));
        WebElement bigCircle = driver.findElement(By.id("droptarget"));

        action.dragAndDrop(smallCircle,bigCircle).perform();
        sleepInSecond(2);

        //verify text
        Assert.assertEquals(bigCircle.getText(),"You did great!");

        //Verify Background Color
        String bigCircleRGB = bigCircle.getCssValue("background-color");
        System.out.println(bigCircleRGB);

        String bigCircleHexa = Color.fromString(bigCircleRGB).asHex();
        System.out.println(bigCircleHexa);

        Assert.assertEquals(bigCircleHexa.toUpperCase(),"#03A9F4");

    }

    @Test
    public void TC_04_Drag_And_Drop_HTML5() throws IOException {
        String jsHelper = getContentFile(dragAndDropHelperPath);

        driver.get("https://automationfc.github.io/drag-drop-html5/");

        String sourceCss  = "div#column-a";
        String targetCss  = "div#column-b";

        jsHelper = jsHelper + "$(\"" + sourceCss + "\").simulateDragDrop({ dropTarget: \"" + targetCss + "\"});";

        // Drag A to B
        jsExecutor.executeScript(jsHelper);
        sleepInSecond(3);

        Assert.assertTrue(driver.findElement(By.xpath("//div[@id='column-a']/header[text()='B']")).isDisplayed());
        Assert.assertTrue(driver.findElement(By.xpath("//div[@id='column-b']/header[text()='A']")).isDisplayed());

        // Drag A to B
        jsExecutor.executeScript(jsHelper);
        sleepInSecond(3);

        Assert.assertTrue(driver.findElement(By.xpath("//div[@id='column-a']/header[text()='A']")).isDisplayed());
        Assert.assertTrue(driver.findElement(By.xpath("//div[@id='column-b']/header[text()='B']")).isDisplayed());


    }

    @Test
    public void TC_05_Drag_And_Drop_HTML5() throws AWTException {
        driver.get("https://automationfc.github.io/drag-drop-html5/");

        // Drag A to B
        dragAndDropHTML5ByXpath("//div[@id='column-a']","//div[@id='column-b']");
        sleepInSecond(5);

        Assert.assertTrue(driver.findElement(By.xpath("//div[@id='column-a']/header[text()='B']")).isDisplayed());
        Assert.assertTrue(driver.findElement(By.xpath("//div[@id='column-b']/header[text()='A']")).isDisplayed());

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

    public void dragAndDropHTML5ByXpath(String sourceLocator, String targetLocator) throws AWTException {

        WebElement source = driver.findElement(By.xpath(sourceLocator));
        WebElement target = driver.findElement(By.xpath(targetLocator));

        // Setup robot
        Robot robot = new Robot();
        robot.setAutoDelay(500);

        // Get size of elements
        Dimension sourceSize = source.getSize();
        Dimension targetSize = target.getSize();

        // Get center distance
        int xCentreSource = sourceSize.width / 2;
        int yCentreSource = sourceSize.height / 2;
        int xCentreTarget = targetSize.width / 2;
        int yCentreTarget = targetSize.height / 2;

        Point sourceLocation = source.getLocation();
        Point targetLocation = target.getLocation();

        // Make Mouse coordinate center of element
        sourceLocation.x += 20 + xCentreSource;
        sourceLocation.y += 110 + yCentreSource;
        targetLocation.x += 20 + xCentreTarget;
        targetLocation.y += 110 + yCentreTarget;

        // Move mouse to drag from location
        robot.mouseMove(sourceLocation.x, sourceLocation.y);

        // Click and drag
        robot.mousePress(InputEvent.BUTTON1_MASK);
        robot.mousePress(InputEvent.BUTTON1_MASK);
        robot.mouseMove(((sourceLocation.x - targetLocation.x) / 2) + targetLocation.x, ((sourceLocation.y - targetLocation.y) / 2) + targetLocation.y);

        // Move to final position
        robot.mouseMove(targetLocation.x, targetLocation.y);

        // Drop
        robot.mouseRelease(InputEvent.BUTTON1_MASK);
    }

    public String getContentFile(String filePath) throws IOException, FileNotFoundException {
        Charset cs = Charset.forName("UTF-8");
        FileInputStream stream = new FileInputStream(filePath);
        try {
            Reader reader = new BufferedReader(new InputStreamReader(stream, cs));
            StringBuilder builder = new StringBuilder();
            char[] buffer = new char[8192];
            int read;
            while ((read = reader.read(buffer, 0, buffer.length)) > 0) {
                builder.append(buffer, 0, read);
            }
            return builder.toString();
        } finally {
            stream.close();
        }
    }


    @AfterClass
    public void afterClass() {
        driver.quit();
    }
}