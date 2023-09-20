package webdriver;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Topic_12_Action_Part_P2 {
    WebDriver driver;
    Actions action;
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
        action = new Actions(driver);
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        driver.manage().window().maximize();
    }

    @Test
    public void TC_01_Click_And_Hold() {
        driver.get("https://automationfc.github.io/jquery-selectable/");

        List<WebElement> listNumber = driver.findElements(By.cssSelector("ol#selectable li"));
        // Đang chưa 12 số / item bất kỳ trong list này

        // 1 - Click vào 1 số (source) - 2 -> Vẫn giữ chuột / chưa nhả ra
        action.clickAndHold(listNumber.get(0))
                // 3 - Di chuột tới số (target)
                .moveToElement(listNumber.get(7))

                // 4 - Nhả chuột trái ra
                .release()

                // Execute - thực thi
                .perform();

        sleepInSecond(2);

        List<WebElement> listSelectedNumber = driver.findElements(By.cssSelector("ol#selectable li.ui-selected"));
        Assert.assertEquals(listSelectedNumber.size(),8);

    }

    @Test
    public void TC_02_Click_And_Hold_Random() {
        driver.get("https://automationfc.github.io/jquery-selectable/");

        //Chạy được cho cả MAC / Windows
        Keys key = null;
        if (osName.contains("Windows")){
            key = Keys.CONTROL;
        } else {
            key = Keys.COMMAND;
        }

        List<WebElement> listNumber = driver.findElements(By.cssSelector("ol#selectable li"));
        // Đang chưa 12 số / item trong List ny

        //Nhấn Ctrl xuống
        action.keyDown(key).perform();

        //Click chọn các số random
        action.click(listNumber.get(0))
                .click(listNumber.get(2))
                .click(listNumber.get(4))
                .click(listNumber.get(7))
                .click(listNumber.get(9))
                .click(listNumber.get(11)).perform();

        //Nhả phím Ctrl ra
        action.keyUp(key).perform();

        List<WebElement> listSelectedNumber = driver.findElements(By.cssSelector("ol#selectable li.ui-selected"));
        Assert.assertEquals(listSelectedNumber.size(),6);


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