package webdriver;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Topic_16_Popup_Part_P3_Random_Popup {
    WebDriver driver;
    String projectPath = System.getProperty("user.dir");
    String osName = System.getProperty("os.name");
    String emailAddress = "testdemo" + getRandomNumber() + "@gmail.com";

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
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    // Yêu cầu
    // Random popup nên nó có thể hiển thị 1 cách ngẫu nhiên hoặc không hiển thị
    // Nếu như nó hiển thị thì mình cần thao tác lên popup  --> Đóng  nó đi để qua step tiếp theo
    // Khi mà đóng nó lại thì có thể refresh trang nó hiện lên lại/ hoặc là không
    // Nếu như nó không hiển thị thì qua step tiếp theo luôn

    @Test
    public void TC_01_Random_In_DOM() {
        driver.get("https://www.javacodegeeks.com/");
        sleepInSecond(15);

        By lePopup = By.cssSelector("div.lepopup-popup-container>div:not([style^='display:none'])");

        // Vì nó luôn có trong DOM nên có thể dùng hàm isDisplayed để kiểm tra được
        if (driver.findElement(lePopup).isDisplayed()){
            // Nhập Email vào
            driver.findElement(By.cssSelector("div.lepopup-input>input")).sendKeys(emailAddress);
            sleepInSecond(1);
            driver.findElement(By.cssSelector("a[data-label='Get the Books],[data-label='OK']>span")).click();
            sleepInSecond(5);

            // Verify
            Assert.assertEquals(driver.findElement(By.cssSelector("div.lepopup-element-html-content>h4")).getText(),"Thank you!");
            Assert.assertTrue(driver.findElement(By.cssSelector("div.lepopup-element-html-content>p")).getText().contains("Your sign-up request"));

            // Đóng popup đi -> Quan step tiếp theo
            // Sau ~ 5s nó sẽ tự động đóng popup
            sleepInSecond(15);

        }

        String articleName = "Agile Testing Explained";
        driver.findElement(By.id("search-input")).sendKeys(articleName);
        driver.findElement(By.id("search-submit")).click();
        sleepInSecond(3);

        Assert.assertEquals(driver.findElement(By.cssSelector("header#search-title-section h1.page-title>span")).getText(),articleName);


    }

    @Test
    public void TC_02_Random_In_DOM() {
        driver.get("https://vnk.edu.vn/");
        sleepInSecond(20);

        By popup = By.cssSelector("div.tve-leads-conversion-object");

        if (driver.findElement(popup).isDisplayed()){
            // Close popup này đi
            driver.findElement(By.cssSelector("svg.tcb-icon")).click();
            sleepInSecond(2);
        }

        driver.findElement(By.xpath("//button[text()='Danh sách khóa học']")).click();
        sleepInSecond(3);

        Assert.assertEquals(driver.getTitle(),"Lịch khai giảng các khóa học tại VNK EDU | VNK EDU");

    }

    @Test
    public void TC_03_Random_Not_In_DOM() {
        driver.get("https://dehieu.vn/");
        sleepInSecond(10);

        By popup = By.cssSelector("div.popup-content");

        if (driver.findElements(popup).size() > 0){
            driver.findElement(By.name("name")).sendKeys("Trang Nguyen");
            driver.findElement(By.name("email")).sendKeys("Trangnguyen@gmail.com");
            driver.findElement(By.name("phone")).sendKeys("0987654321");
            driver.findElement(By.id("close-popup")).click();
            sleepInSecond(2);
        }

        driver.findElement(By.xpath("//a[text()='Tất cả khóa học']")).click();
        sleepInSecond(3);

        String courses = "Khóa học thiết kế hệ thống M&E - Căn hộ, biệt thự";

        driver.findElement(By.id("search-courses")).sendKeys(courses);
        driver.findElement(By.id("search-course-button")).click();
        sleepInSecond(3);

        Assert.assertEquals(driver.findElement(By.cssSelector("h4.name-course")).getText(),courses);

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