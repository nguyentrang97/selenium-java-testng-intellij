package webdriver;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.IOException;
import java.time.Duration;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Topic_10_Alert {
    WebDriver driver;
    Alert alert;
    WebDriverWait expliciWait;
    String projectPath = System.getProperty("user.dir");
    String osName = System.getProperty("os.name");
    String username = "admin";
    String password = "admin";
    String authenChrome = projectPath + "\\autoIT\\authen_chrome.exe";
    String authenFirefox = projectPath + "\\autoIT\\authen_firefox.exe";

    @BeforeClass
    public void beforeClass() {
        if (osName.contains("Windows")) {
            System.setProperty("webdriver.gecko.driver", projectPath + "\\browserDrivers\\geckodriver.exe");
        } else {
            System.setProperty("webdriver.gecko.driver", projectPath + "/browserDrivers/geckodriver");
        }

        driver = new FirefoxDriver();
        expliciWait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        driver.manage().window().maximize();
    }

    @Test
    public void TC_01_Accept_Alert() {
        driver.get("https://automationfc.github.io/basic-form/index.html");

        driver.findElement(By.xpath("//button[text()='Click for JS Alert']")).click();
        sleepInSecond(3);

        // 1 - Có thể switch qua và tương tác luôn
        //alert = driver.switchTo().alert();

        // 2 - Cần wait trước khi nó xuất hiện thì mới swtich qua và tương tác được
        // Cách 2 sẽ ổn định và ít fail hơn
        alert = expliciWait.until(ExpectedConditions.alertIsPresent());

        // Verify alert title đúng như mong đợi
        Assert.assertEquals(alert.getText(),"I am a JS Alert");

        // Accept cái alert này
        alert.accept();
        sleepInSecond(3);

        Assert.assertEquals(driver.findElement(By.cssSelector("p#result")).getText(),"You clicked an alert successfully");

    }

    @Test
    public void TC_02_Confirm_Alert() {
        driver.get("https://automationfc.github.io/basic-form/index.html");

        driver.findElement(By.xpath("//button[text()='Click for JS Confirm']")).click();
        sleepInSecond(3);

        // 1 - Có thể switch qua và tương tác luôn
        //alert = driver.switchTo().alert();

        // 2 - Cần wait trước khi nó xuất hiện thì mới swtich qua và tương tác được
        // Cách 2 sẽ ổn định và ít fail hơn
        alert = expliciWait.until(ExpectedConditions.alertIsPresent());

        // Verify alert title đúng như mong đợi
        Assert.assertEquals(alert.getText(),"I am a JS Confirm");

        // Cancel cái alert này
        alert.dismiss();
        sleepInSecond(3);

        Assert.assertEquals(driver.findElement(By.cssSelector("p#result")).getText(),"You clicked: Cancel");

    }
    @Test
    public void TC_03_Prompt_Alert() {
        driver.get("https://automationfc.github.io/basic-form/index.html");

        driver.findElement(By.xpath("//button[text()='Click for JS Prompt']")).click();
        sleepInSecond(2);

        // 1 - Có thể switch qua và tương tác luôn
        //alert = driver.switchTo().alert();

        // 2 - Cần wait trước khi nó xuất hiện thì mới swtich qua và tương tác được
        // Cách 2 sẽ ổn định và ít fail hơn
        alert = expliciWait.until(ExpectedConditions.alertIsPresent());

        // Verify alert title đúng như mong đợi
        Assert.assertEquals(alert.getText(),"I am a JS prompt");

        // Nhập text vào Alert
        String courseName = "Automation Testing";
        alert.sendKeys(courseName);
        sleepInSecond(2);

        // Accept cái alert này
        alert.accept();
        sleepInSecond(2);

        Assert.assertEquals(driver.findElement(By.cssSelector("p#result")).getText(),"You entered: " + courseName);

    }

    @Test
    public void TC_04_Authentication_Alert_I() {

        // Truyền trực tiếp username password vào url này --> Tự động sign in luôn
        // http:// username : password @ the-internet.herokuapp.com/basic_auth

        //driver.get("http://admin:admin@the-internet.herokuapp.com/basic_auth");

        driver.get("https://the-internet.herokuapp.com/");

        String authenUrl = driver.findElement(By.xpath("//a[text()='Basic Auth']")).getAttribute("href");
        driver.get(userAndPassToUrl(authenUrl,username, password));

        Assert.assertTrue(driver.findElement(By.xpath("//p[contains(text(),'Congratulations! You must have the proper credentials.')]")).isDisplayed());


    }

    @Test
    public void TC_05_Authentication_Alert_II() throws IOException {

        if (driver.toString().contains("firefox")){
            //Runtime.getRuntime().exec  :   Thực thi 1 file exe trong Java
            Runtime.getRuntime().exec(new String[] { authenFirefox, username, password});
        } else if (driver.toString().contains("chrome")){
            Runtime.getRuntime().exec(new String[] { authenChrome, username, password});
        }

        driver.get("http://the-internet.herokuapp.com/basic_auth");
        sleepInSecond(5);
        Assert.assertTrue(driver.findElement(By.xpath("//p[contains(text(),'Congratulations! You must have the proper credentials.')]")).isDisplayed());

        //Chỉ dùng cho Windows (Không dùng cho MAC/Linux được)
        //Không chạy ở trên các CI tool được (headless) - chạy ko bật giao diện

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

    public String userAndPassToUrl (String url, String username, String password){
        // url = http://the-internet.herokuapp.com/basic_auth
        // username = admin
        // password = admin
        String[] arrayUrl = url.split("//");
        return arrayUrl[0] + "//" + username + ":" + password + "@" + arrayUrl[1];
    }

    @AfterClass
    public void afterClass() {
        driver.quit();
    }
}