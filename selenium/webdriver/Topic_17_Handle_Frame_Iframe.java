package webdriver;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.Random;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Topic_17_Handle_Frame_Iframe {
    WebDriver driver;
    JavascriptExecutor jExecutor;
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
        jExecutor = (JavascriptExecutor) driver;
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        driver.manage().window().maximize();
    }

    @Test
    public void TC_01_Iframe() {
        driver.get("https://skills.kynaenglish.vn/");

        Assert.assertTrue(driver.findElement(By.cssSelector("div.fanpage  iframe")).isDisplayed());

        //Switch vào Facebool iframe
        driver.switchTo().frame(driver.findElement(By.cssSelector("div.fanpage  iframe")));

        Assert.assertEquals(driver.findElement(By.xpath("//a[@title='Kyna.vn']/parent::div/following-sibling::div")).getText(),"167K người theo dõi");

        //Quay về trang trước đó
        driver.switchTo().defaultContent();

        //Click vào chat iframe
        driver.switchTo().frame(driver.findElement(By.cssSelector("iframe#cs_chat_iframe")));
        sleepInSecond(2);

        jExecutor.executeScript("arguments[0].click();", driver.findElement(By.cssSelector("div.meshim_widget_widgets_BorderOverlay")));
        sleepInSecond(3);

        driver.findElement(By.cssSelector("input.input_name")).sendKeys("TRANG-SAN");
        driver.findElement(By.cssSelector("input.input_phone")).sendKeys("0123456789");
        new Select(driver.findElement(By.cssSelector("select#serviceSelect"))).selectByVisibleText("TƯ VẤN TUYỂN SINH");;
        driver.findElement(By.name("message")).sendKeys("Tư vấn");
        sleepInSecond(2);

        //Quay về trang chính
        driver.switchTo().defaultContent();

        String keyword = "Excel";
        driver.findElement(By.id("live-search-bar")).sendKeys(keyword);
        driver.findElement(By.cssSelector("button.search-button")).click();
        sleepInSecond(4);

        List<WebElement> courseName = driver.findElements(By.cssSelector("div.content>h4"));

        for (WebElement course : courseName){
            System.out.println(course.getText());
            Assert.assertTrue(course.getText().trim().toLowerCase().contains(keyword.trim().toLowerCase()));
        }

    }

    @Test
    public void TC_02_Frame() {
        driver.get("https://netbanking.hdfcbank.com/netbanking/");

        //Switch vào frame
        driver.switchTo().frame("login_page");

        //Thao tác với userID
        driver.findElement(By.name("fldLoginUserId")).sendKeys("trangnguyen12");
        driver.findElement(By.cssSelector("a.login-btn")).click();
        sleepInSecond(3);

        //Switch về default
        driver.switchTo().defaultContent();
        Assert.assertTrue(driver.findElement(By.id("keyboard")).isDisplayed());


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