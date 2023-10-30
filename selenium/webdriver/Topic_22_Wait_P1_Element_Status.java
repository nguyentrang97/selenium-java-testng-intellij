package webdriver;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Topic_22_Wait_P1_Element_Status {
    WebDriver driver;
    WebDriverWait explicitWait;
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

        //Cho việc tìm element (findElement / findElements)  -> Chung chung
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

        //Cho trạng thái của element -> Cụ thể
        explicitWait = new WebDriverWait(driver, Duration.ofSeconds(30));

        driver.manage().window().maximize();
    }

    @Test
    public void TC_01_Visible_Displayed() {
        driver.get("https://www.facebook.com");

        //Điều kiện 1: Element có trên UI và có trong cây HTML

        //Chờ cho cái Email textbox được hiển thị trước khi senkey vào nó
        //Chờ trong khoảng thời gian là 30 giây
        explicitWait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input#email")));

        driver.findElement(By.cssSelector("input#email")).sendKeys("trang@gmail.com");
    }

    @Test
    public void TC_02_Invisible_Undisplayed_Case_1() {
        //Điều kiện 1: Element không có (ko nhìn thấy) trên UI nhưng vẫn có trong cây HTML
        driver.get("https://www.facebook.com");
        driver.findElement(By.cssSelector("a[data-testid='open-registration-form-button']")).click();

        //Confirmation Email textbox is undisplay
        explicitWait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector("input[name='reg-email']")));

    }

    @Test
    public void TC_02_Invisible_Undisplayed_Case_2() {
        //Điều kiện 1: Element không có (ko nhìn thấy) trên UI nhưng vẫn có trong cây HTML
        driver.get("https://www.facebook.com");
        driver.findElement(By.cssSelector("a[data-testid='open-registration-form-button']")).click();

        //Confirmation Email textbox is undisplay
        explicitWait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector("input[name='reg_email_confirmation__']")));

    }

    @Test
    public void TC_03_Presence_Case_1() {
        //Điều kiện 1: Element trên UI và có trong cây HTML
        driver.get("https://www.facebook.com");

        explicitWait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("input#email")));

    }
    @Test
    public void TC_03_Presence_Case_2() {
        //Điều kiện 1: Element không có (ko nhìn thấy) trên UI nhưng vẫn có trong cây HTML
        driver.get("https://www.facebook.com");

        //Confirmation Email textbox is presence
        explicitWait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("input[name='reg_email_confirmation__']")));

    }

    @Test
    public void TC_04_Staleness() {
        //Apply cả: có trong HTML và sau đó apply điều kiện 3
        driver.get("https://www.facebook.com");

        driver.findElement(By.cssSelector("a[data-testid='open-registration-form-button']")).click();
        explicitWait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("input[name='reg_email_confirmation__']")));

        //B1: Element phải có trong HTML
        WebElement confirmEmailTextbox = driver.findElement(By.cssSelector("input[name='reg_email_confirmation__']"));
        driver.findElement(By.xpath("//div[text()='Sign Up']/parent::div/preceding-sibling::img")).click();

        //Wait cho confirm emali staleness -> chạy nhanh
        explicitWait.until(ExpectedConditions.stalenessOf(confirmEmailTextbox));

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