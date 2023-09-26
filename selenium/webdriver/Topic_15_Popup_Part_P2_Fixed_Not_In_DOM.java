package webdriver;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.nio.file.LinkOption;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Topic_15_Popup_Part_P2_Fixed_Not_In_DOM {
    WebDriver driver;
    String projectPath = System.getProperty("user.dir");
    String osName = System.getProperty("os.name");

    @BeforeClass
    public void beforeClass() {
        if (osName.contains("Windows")) {
            System.setProperty("webdriver.chrome.driver", projectPath + "\\browserDrivers\\chromedriver.exe");
        } else {
            System.setProperty("webdriver.chrome.driver", projectPath + "/browserDrivers/chromedriver");
        }

        Map<String, Integer> prefs = new HashMap<String, Integer>();
        prefs.put("profile.default_content_setting_values.nofitications", 2);
        ChromeOptions options = new ChromeOptions();
        options.setExperimentalOption("prefs", prefs);
        driver = new ChromeDriver(options);
        driver.manage().window().maximize();

        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

    }

    @Test
    public void TC_01_Fixed_Not_In_DOM_Tiki() {
        driver.get("https://tiki.vn/");
        sleepInSecond(3);

        //By - Chưa có đi tìm element
        By loginPopup = By.xpath("//button[@class='btn-close']//parent::div/parent::div");

        // Verify nó chưa hiển thị khi kích vào login button
        Assert.assertEquals(driver.findElements(loginPopup).size(),0);

        // Click cho bật login popup lên
        driver.findElement(By.cssSelector("div[data-view-id*='header_account']")).click();
        sleepInSecond(2);

        // Verify nó hiển thị
        Assert.assertEquals(driver.findElements(loginPopup).size(),1);
        Assert.assertTrue(driver.findElement(loginPopup).isDisplayed());

        // Đăng nhập bằng email
        driver.findElement(By.cssSelector("p.login-with-email")).click();
        driver.findElement(By.xpath("//button[text()='Đăng nhập']")).click();
        sleepInSecond(2);

        Assert.assertTrue(driver.findElement(By.xpath("//span[text()='Email không được để trống']")).isDisplayed());
        Assert.assertTrue(driver.findElement(By.xpath("//span[text()='Mật khẩu không được để trống']")).isDisplayed());

        // Close popup
        driver.findElement(By.cssSelector("div.ReactModal__Content img.close-img")).click();

        // Verify nó không còn hiển thị
        Assert.assertEquals(driver.findElements(loginPopup).size(),0);


    }

    @Test
    public void TC_02_Fixed_Not_In_DOM_Facebook() {
        driver.get("https://www.facebook.com/");



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
        //driver.quit();
    }
}