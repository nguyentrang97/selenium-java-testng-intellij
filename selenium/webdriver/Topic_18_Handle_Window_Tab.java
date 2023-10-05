package webdriver;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.Random;
import java.util.Set;
import java.util.concurrent.TimeUnit;

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

    }

    @Test
    public void TC_02_() {

    }

    @Test
    public void TC_03_() {

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