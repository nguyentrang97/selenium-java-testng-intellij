package webdriver;

import graphql.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.Random;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import static io.ous.jtoml.impl.Token.TokenType.Key;

public class Topic_18_Handle_Window_Tab {
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
    public void TC_01_Github_With_Two_Window_Tab() {
        //Driver đang tại trang Github
        driver.get("https://automationfc.github.io/basic-form/index.html");

        //Trả về ID của driver đang đứng tại đó -> Số it -> Chỉ 1 ID
        String githubID = driver.getWindowHandle();
        System.out.println("Github ID: " + githubID);
        System.out.println("Page title - Github = " + driver.getTitle());

        //Click vào Google link -> Theo Business nó sẽ mở rra trang Google
        //Driver vẫn đang ở trang Github
        driver.findElement(By.xpath("//a[text()='GOOGLE']")).click();
        sleepInSecond(2);
        System.out.println("Page title - Github = " + driver.getTitle());

        switchToWindowByID(githubID);

        //Driver đang tại trang Google rồi
        System.out.println("Page title - Google = " + driver.getTitle());

        //Quay lại trang Github
        //Trả về ID của driver đang đứng tại đó
        String googleID = driver.getWindowHandle();
        System.out.println("Google ID: " + googleID);

        switchToWindowByID(googleID);
        System.out.println("Page title - Github = " + driver.getTitle());

    }

    @Test
    public void TC_02_Github_Greater_Two_Windown_Tab() {
        //Driver đang đứng tại trang Github
        driver.get("https://automationfc.github.io/basic-form/index.html");

        String githubID =driver.getWindowHandle();

        driver.findElement(By.xpath("//a[text()='GOOGLE']")).click();
        sleepInSecond(2);

        //Chuyển qua trang Google
        switchToWindowByTitle("Google");
        System.out.println("Page title - Google = " + driver.getTitle());

        driver.findElement(By.xpath("//textarea[@name='q']")).sendKeys("Automation testing");
        driver.findElement(By.xpath("//textarea[@name='q']")).sendKeys(Keys.ENTER);

        //Về lại trang Github
        switchToWindowByTitle("Selenium WebDriver");
        System.out.println("Page title - Github = " + driver.getTitle());

        //Click vào Facebook
        driver.findElement(By.xpath("//a[text()='FACEBOOK']")).click();
        sleepInSecond(2);

        //Chuyển qua trang facebook
        switchToWindowByTitle("Facebook – log in or sign up");
        System.out.println("Page title - Facebook = " + driver.getTitle());

        driver.findElement(By.xpath("//input[@id='email']")).sendKeys("trangnguyen@gmail.com");
        driver.findElement(By.xpath("//input[@id='pass']")).sendKeys("123456789");
        sleepInSecond(2);

        //Về lại trang Github
        switchToWindowByTitle("Selenium WebDriver");
        System.out.println("Page title - Github = " + driver.getTitle());

        //Click vào Tiki
        driver.findElement(By.xpath("//a[text()='TIKI']")).click();
        sleepInSecond(2);

        //Chuyển qua trang TIKI
        switchToWindowByTitle("Tiki - Mua hàng online giá tốt, hàng chuẩn, ship nhanh");
        System.out.println("Page title - Tiki = " + driver.getTitle());

        closeAllWindowWithoutParentID(githubID);

    }

    @Test
    public void TC_03_Techpanda() {
        driver.get("http://live.techpanda.org/index.php");

        driver.findElement(By.xpath("//a[text()='Mobile']")).click();
        sleepInSecond(2);

        driver.findElement(By.xpath("//a[text()='Samsung Galaxy']/parent::h2/following-sibling::div[@class='actions']//a[text()='Add to Compare']")).click();
        Assert.assertTrue(driver.findElement(By.xpath("//li[@class='success-msg']//span[text()='The product Samsung Galaxy has been added to comparison list.']")).isDisplayed());

        driver.findElement(By.xpath("//a[text()='Sony Xperia']/parent::h2/following-sibling::div[@class='actions']//a[text()='Add to Compare']")).click();
        Assert.assertTrue(driver.findElement(By.xpath("//li[@class='success-msg']//span[text()='The product Sony Xperia has been added to comparison list.']")).isDisplayed());

        driver.findElement(By.xpath("//button[@title='Compare']")).click();
        sleepInSecond(3);

        switchToWindowByTitle("Products Comparison List - Magento Commerce");
        System.out.println("Page title = " + driver.getTitle());

        //sau khi kích Close thì về mặt Business nó đã close window này rồi
        //Nhưng driver vn đang đúng ở trang Products Comparison List
        driver.findElement(By.xpath("//button[@title='Close Window']")).click();
        sleepInSecond(3);

        switchToWindowByTitle("Mobile");
        System.out.println("Page title = " + driver.getTitle());


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

    //Dùng cho case duy nhất 2 tab/window
    public void switchToWindowByID(String pageID){
        Set<String> allIDs = driver.getWindowHandles();

        for (String id : allIDs){
            if (!id.equals(pageID)){
                driver.switchTo().window(id);
                sleepInSecond(1);
            }
        }
    }

    //Dùng cho cả từ 2 tab/window trở lên
    public void switchToWindowByTitle (String pageTitle){
        //Lấy ta hết tất cả các ID của tất cả các tab/window
        Set<String> allIDs = driver.getWindowHandles();

        //Dùng vòng lặp để duyệt qua từng item
        for (String id : allIDs){
            //Switch vào từng item trước
            driver.switchTo().window(id);
            sleepInSecond(1);
            String actualPageTitle = driver.getTitle();
            if (actualPageTitle.equals(pageTitle)){
                //Thoát khỏi vòng lặp không duyệt item tiếp theo nữa
                break;
            }
        }
    }

    public void closeAllWindowWithoutParentID (String parentID){
        Set<String> allIDs = driver.getWindowHandles();

        for (String id : allIDs){
            if (!id.equals(parentID)){
                driver.switchTo().window(id);
                driver.close();
            }
        }

        driver.switchTo().window(parentID);
    }


    @AfterClass
    public void afterClass() {
        driver.quit();
    }
}