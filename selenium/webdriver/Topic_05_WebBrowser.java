package webdriver;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;


import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;


public class Topic_05_WebBrowser {
    WebDriver driver;
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
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        driver.manage().window().maximize();
    }

    @Test
    public void TC_01_() {
        // >=2: Nó sẽ đóng tab/window mà nó đang đứng
        //  =1: Nó cũng đóng Browser
        driver.close();   //*có sử dụng

        //Không quan tâm có bao nhiêu tab/window  -> đóng Browser
        driver.quit();  //**sử dụng nhiều

        //Tìm 1 element
        WebElement emailTextbox = driver.findElement(By.xpath("//input[@id='email']"));   //**sử dụng nhiều

        //Tìm nhiều element
        List<WebElement> checkboxes = driver.findElements(By.xpath(""));   //*có sử dụng

        //Mở ra 1 URL nào đó
        driver.get("https://www.facebook.com");    //**sử dụng nhiều

        //Click vào link: Tiếng Việt

        //Trả về URL của page hiện tại
        Assert.assertEquals(driver.getCurrentUrl(),"https://vi-vn.facebook.com/");

        //Trả về Source Code HTML của page hiện tại
        //Verify tương đối
        Assert.assertTrue((driver.getPageSource().contains("Facebook giúp bạn kết nối và chia sẻ với mọi người trong cuộc sống của bạn.")));
        Assert.assertTrue((driver.getPageSource().contains("trong cuộc sống của bạn.")));
        Assert.assertTrue((driver.getPageSource().contains("Facebook giúp bạn kết nối và chia sẻ")));

        //Trả về title của page hiện tại
        Assert.assertEquals(driver.getTitle(), "Facebook - Đăng nhập hoặc đăng ký");

        //WebDriver API - Window/Tab
        //Lấy ra ID của wwinddoww/tab mà driver đang đứng (active)
        String loginWindowID = driver.getWindowHandle();     //*có sử dụng

        //Lấy ra ID của tất cả Window/Tab
        Set<String> allIDs = driver.getWindowHandles();     //*có sử dụng

        //Cookie/Cache
        WebDriver.Options opt = driver.manage();
        //Login thành công - lưu lại
        opt.getCookies();       //*có sử dụng
        //Testcase khác -> Set cookie vào lại -> Không cần phải login lại nữa
        opt.logs();

        WebDriver.Timeouts time = opt.timeouts();

        //Implicit wait and depend on : FindElement/ FindElements
        //Khoảng thời gian chờ element xuất hiện trong vòng x giây

        time.implicitlyWait(5, TimeUnit.SECONDS);      //**sử dụng nhiều
        time.implicitlyWait(5000, TimeUnit.MILLISECONDS);
        time.implicitlyWait(5000000, TimeUnit.MICROSECONDS);

        //Khoảng thời gian chờ page load xong trong vòng x giây
        time.pageLoadTimeout(5, TimeUnit.SECONDS);

        //WebDriver API - Javascript Executor (JavascriptExecutor library)
        //Khoảng thời gian chờ script được thực thi trong vòng x giây
        time.setScriptTimeout(5, TimeUnit.SECONDS);

        WebDriver.Window  win = opt.window();
        win.fullscreen();
        win.maximize();   //**sử dụng nhiều

        //Test FUI: Functional
        //Test GUI: Font/Size/Color/Position/Location/...
        win.getPosition();
        win.getSize();

        WebDriver.Navigation nav = driver.navigate();
        nav.back();
        nav.refresh();
        nav.forward();
        nav.to("https://www.facebook.com/");

        WebDriver.TargetLocator tar = driver.switchTo();
        //WebDriver API - Alert/Authenication Alert (Alert library)
        tar.alert();  //*có sử dụng

        //WebDriver API - Frame/Iframe (Frame library)
        tar.frame(""); //*có sử dụng

        //WebDriver API - Window/Tabs
        tar.window("");  //*có sử dụng

    }

    @Test
    public void TC_02_() {

    }

    @AfterClass
    public void afterClass() {
        driver.quit();
    }
}