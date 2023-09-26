package webdriver;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Topic_14_Popup_Part_P1_Fixed_In_DOM {
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

        FirefoxOptions options = new FirefoxOptions();
        options.setProfile(new FirefoxProfile());
        options.addPreference(("dom.webnotifications.enabled"),false);
        driver = new FirefoxDriver(options);

        //driver = new ChromeDriver();

        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        //driver.manage().window().maximize();
    }

    @Test
    public void TC_01_Fixed_In_DOM_NgoaiNgu24h() {
        driver.get("https://ngoaingu24h.vn/");
        sleepInSecond(2);

        By loginPopup = By.cssSelector("div#modal-login-v1 div.modal-content");
        // Verify popup login is undisplayed
        Assert.assertFalse(driver.findElement(loginPopup).isDisplayed());

        // Click button Đăng nhập
        driver.findElement(By.cssSelector("div#button-login-dialog>button.login_")).click();
        sleepInSecond(2);

        // Verify popup login is displayed
        Assert.assertTrue(driver.findElement(loginPopup).isDisplayed());

        driver.findElement(By.cssSelector("input#account-input")).sendKeys("automationfc");
        driver.findElement(By.cssSelector("input#password-input")).sendKeys("automationfc");
        driver.findElement(By.cssSelector("button.btn-login-v1")).click();
        sleepInSecond(2);

        Assert.assertEquals(driver.findElement(By.cssSelector("div.error-login-panel")).getText(),"Tài khoản không tồn tại!");

    }

    @Test
    public void TC_02_Fixed_In_DOM_Kyna() {
        driver.get("https://skills.kynaenglish.vn/");
        sleepInSecond(2);

        By loginPopup = By.cssSelector("div#k-popup-account-login");
        // Verify popup login is undisplayed
        Assert.assertFalse(driver.findElement(loginPopup).isDisplayed());

        // Click button Đăng nhập
        driver.findElement(By.cssSelector("a.login-btn")).click();
        sleepInSecond(2);

        // Verify popup login is displayed
        Assert.assertTrue(driver.findElement(loginPopup).isDisplayed());

        driver.findElement(By.cssSelector("input#user-login")).sendKeys("automation@gmail.com");
        driver.findElement(By.cssSelector("input#user-password")).sendKeys("123456");
        sleepInSecond(2);
        driver.findElement(By.cssSelector("button#btn-submit-login")).click();
        sleepInSecond(2);

        Assert.assertEquals(driver.findElement(By.cssSelector("div#password-form-login-message")).getText(),"Sai tên đăng nhập hoặc mật khẩu");

        driver.findElement(By.cssSelector("button.k-popup-account-close")).click();
        sleepInSecond(2);
        // Verify popup login is undisplayed
        Assert.assertFalse(driver.findElement(loginPopup).isDisplayed());




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