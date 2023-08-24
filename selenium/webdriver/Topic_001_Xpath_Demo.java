package webdriver;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import javax.print.DocFlavor;
import java.util.concurrent.TimeUnit;

public class Topic_001_Xpath_Demo {
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
    public void TC_01_Login() {
        driver.get("https://gps3.binhanh.vn/auth/login");
        WebElement txtUsername = driver.findElement(By.xpath("//input[@name='username']"));
        txtUsername.sendKeys("kpibaoyen");

        WebElement txtPassword = driver.findElement(By.xpath("//input[@name='password']"));
        txtPassword.sendKeys("123@123a");

        WebElement btnLogin = driver.findElement(By.xpath("//form//button"));
        btnLogin.click();

        Assert.assertEquals(driver.getCurrentUrl(),"https://gps3.binhanh.vn/online");

    }
    @Test
    public void TC_02_CloseAds() {
        driver.get("https://gps3.binhanh.vn/auth/login");
        WebElement txtUsername = driver.findElement(By.xpath("//input[@name='username']"));
        txtUsername.sendKeys("kpibaoyen");

        WebElement txtPassword = driver.findElement(By.xpath("//input[@name='password']"));
        txtPassword.sendKeys("123@123a");

        WebElement btnLogin = driver.findElement(By.xpath("//form//button"));
        btnLogin.click();

        WebElement btnCloseAds = driver.findElement(By.xpath("//div[@class='close-button']"));
        btnCloseAds.click();

    }

    //@Test
    public void TC_03_Logout() {
        driver.get("https://gps3.binhanh.vn/auth/login");
        WebElement txtUsername = driver.findElement(By.xpath("//input[@name='username']"));
        txtUsername.sendKeys("kpibaoyen");

        WebElement txtPassword = driver.findElement(By.xpath("//input[@name='password']"));
        txtPassword.sendKeys("123@123a");

        WebElement btnLogin = driver.findElement(By.xpath("//form//button"));
        btnLogin.click();

        WebElement btnCloseAds = driver.findElement(By.xpath("//div[@class='close-button']"));
        btnCloseAds.click();

        WebElement lblUsername = driver.findElement(By.xpath("//a[@id='navbardrop']"));
        lblUsername.click();

        WebElement btnLogout = driver.findElement(By.xpath("//div[@id='menu-user']//button[contains(text(),'Đăng xuất')]"));
        btnLogout.click();

        WebElement btnSubmit_Logout = driver.findElement(By.xpath("//div[@class='modal-content']//button[contains(text(),'Đồng ý')]"));
        btnSubmit_Logout.click();

        Assert.assertEquals(driver.getCurrentUrl(),"https://gps3.binhanh.vn/auth/login");

    }

    @Test
    public void TC_04_MenuKPI() {
        driver.get("https://gps3.binhanh.vn/auth/login");
        WebElement txtUsername = driver.findElement(By.xpath("//input[@name='username']"));
        txtUsername.sendKeys("kpibaoyen");

        WebElement txtPassword = driver.findElement(By.xpath("//input[@name='password']"));
        txtPassword.sendKeys("123@123a");

        WebElement btnLogin = driver.findElement(By.xpath("//form//button"));
        btnLogin.click();

        WebElement btnCloseAds = driver.findElement(By.xpath("//div[@class='close-button']"));
        btnCloseAds.click();

        WebElement menuKPIlaixe = driver.findElement(By.xpath("//ul[@id='nav-auto']//a[contains(text(),'KPI Lái xe')]"));

        WebElement menuDuyetChuyen = driver.findElement(By.xpath("//ul[@id='nav-auto']//a[@href='/kpi-driver/report-trip-review']"));

    }
    @AfterClass
    public void afterClass() {
        driver.quit();
    }
}