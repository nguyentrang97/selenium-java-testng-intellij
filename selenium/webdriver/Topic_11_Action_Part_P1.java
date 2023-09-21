package webdriver;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.beans.PropertyChangeListener;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Topic_11_Action_Part_P1 {
    WebDriver driver;
    Actions action;
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
        action = new Actions(driver);
        jsExecutor = (JavascriptExecutor) driver;
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        driver.manage().window().maximize();
    }

    @Test
    public void TC_01_Hover_Tooltip() {
        driver.get("https://automationfc.github.io/jquery-tooltip/");
        action.moveToElement(driver.findElement(By.cssSelector("input#age"))).perform();
        sleepInSecond(3);

        Assert.assertEquals(driver.findElement(By.cssSelector("div.ui-tooltip-content")).getText(),"We ask for your age only for statistical purposes.");


    }

    @Test
    public void TC_02_Hover() {
        driver.get("https://www.myntra.com/");
        sleepInSecond(5);

        // Hover và KIDS
        action.moveToElement(driver.findElement(By.xpath("//div[@class='desktop-navLink']//a[text()='Kids']"))).perform();
        sleepInSecond(2);

        driver.findElement(By.xpath("//div[@class='desktop-navLink']//a[text()='Kids Accessories']")).click();
        sleepInSecond(2);

        Assert.assertEquals(driver.findElement(By.cssSelector("span.breadcrumbs-crumb")).getText(),"Kids Accessories");
    }

    @Test
    public void TC_03_Hover() {
        driver.get("https://www.fahasa.com/");
        //Chưa học bài handle popup - manual click cho nó tắt popup đi trước khi hover
        sleepInSecond(10);

        //Hover lần 1
        action.moveToElement(driver.findElement(By.cssSelector("span.icon-menu"))).perform();
        sleepInSecond(3);

        //Hover lần 2
        action.moveToElement(driver.findElement(By.xpath("//a[@title='Sách Trong Nước']"))).perform();
        sleepInSecond(3);

        //Click vào
        driver.findElement(By.xpath("//div[contains(@class,'fhs_menu_content')]//a[text()='Quản Trị - Lãnh Đạo']")).click();

        Assert.assertTrue(driver.findElement(By.xpath("//ol[@class='breadcrumb']//strong[text()='Quản Trị - Lãnh Đạo']")).isDisplayed());
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