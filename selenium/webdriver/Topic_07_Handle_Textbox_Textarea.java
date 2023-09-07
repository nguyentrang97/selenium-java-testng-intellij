package webdriver;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.GeckoDriverInfo;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Topic_07_Handle_Textbox_Textarea {
    WebDriver driver;
    Random ran = new Random();
    String projectPath = System.getProperty("user.dir");
    String osName = System.getProperty("os.name");
    String employeeID = String.valueOf(ran.nextInt(99999));
    String passportNumber = "456454-5466-7999";
    String comments = "Đây là nội dung1\nĐây là nội dung 2";

    @BeforeClass
    public void beforeClass() {
        if (osName.contains("Windows")) {
            System.setProperty("webdriver.gecko.driver", projectPath + "\\browserDrivers\\geckodriver.exe");
        } else {
            System.setProperty("webdriver.gecko.driver", projectPath + "/browserDrivers/geckogedriver");
        }

        driver = new FirefoxDriver();
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        driver.manage().window().maximize();
    }

    @Test
    public void TC_01_Create_New_Employee() {
        driver.get("https://opensource-demo.orangehrmlive.com/web/index.php/auth/login");

        driver.findElement(By.name("username")).sendKeys("Admin");
        driver.findElement(By.name("password")).sendKeys("admin123");
        driver.findElement(By.cssSelector("button.orangehrm-login-button")).click();
        sleepInSecond(6);

        driver.findElement(By.xpath("//span[text()='PIM']")).click();
        sleepInSecond(4);

        driver.findElement(By.xpath("//a[text()='Add Employee']")).click();
        sleepInSecond(4);

        driver.findElement(By.name("firstName")).sendKeys("Nguyen");
        driver.findElement(By.name("middleName")).sendKeys("Thu");
        driver.findElement(By.name("lastName")).sendKeys("Trang");

        WebElement employeeIDTextbox = driver.findElement(By.xpath("//label[text()='Employee Id']/parent::div/following-sibling::div/input"));
        employeeIDTextbox.sendKeys(Keys.chord(Keys.CONTROL,"a"));
        employeeIDTextbox.sendKeys(Keys.DELETE);
        employeeIDTextbox.sendKeys(employeeID);

        driver.findElement(By.xpath("//p[text()='Create Login Details']/parent::div//span")).click();
        sleepInSecond(2);

        driver.findElement(By.xpath("//label[text()='Username']/parent::div/following-sibling::div/input")).sendKeys("trangntt" + employeeID);
        driver.findElement(By.xpath("//label[text()='Password']/parent::div/following-sibling::div/input")).sendKeys("Trangntt@1997");
        driver.findElement(By.xpath("//label[text()='Confirm Password']/parent::div/following-sibling::div/input")).sendKeys("Trangntt@1997");

        driver.findElement(By.xpath("//button[contains(string(),'Save')]")).click();
        sleepInSecond(10);

        Assert.assertEquals(driver.findElement(By.cssSelector("input[name='firstName']")).getAttribute("value"),"Nguyen");
        Assert.assertEquals(driver.findElement(By.cssSelector("input[name='middleName']")).getAttribute("value"),"Thu");
        Assert.assertEquals(driver.findElement(By.cssSelector("input[name='lastName']")).getAttribute("value"),"Trang");
        Assert.assertEquals(driver.findElement(By.xpath("//label[text()='Employee Id']/parent::div/following-sibling::div/input")).getAttribute("value"),employeeID);

        driver.findElement(By.xpath("//a[text()='Immigration']")).click();
        sleepInSecond(4);

        driver.findElement(By.xpath("//h6[text()='Assigned Immigration Records']/parent::div/button")).click();
        sleepInSecond(5);

        driver.findElement(By.xpath("//label[text()='Number']/parent::div/following-sibling::div/input")).sendKeys(passportNumber);
        driver.findElement(By.xpath("//label[text()='Comments']/parent::div/following-sibling::div/textarea")).sendKeys(comments);

        driver.findElement(By.xpath("//button[contains(string(),'Save')]")).click();
        sleepInSecond(6);

        driver.findElement(By.cssSelector("i.bi-pencil-fill")).click();
        sleepInSecond(6);

        Assert.assertEquals(driver.findElement(By.xpath("//label[text()='Number']/parent::div/following-sibling::div/input")).getAttribute("value"),passportNumber);
        Assert.assertEquals(driver.findElement(By.xpath("//label[text()='Comments']/parent::div/following-sibling::div/textarea")).getAttribute("value"),comments);

        driver.findElement(By.cssSelector("p.oxd-userdropdown-name")).click();
        driver.findElement(By.xpath("//a[text()='Logout']")).click();
        sleepInSecond(3);

        driver.findElement(By.name("username")).sendKeys("trangntt" + employeeID);
        driver.findElement(By.name("password")).sendKeys("Trangntt@1997");
        driver.findElement(By.cssSelector("button.orangehrm-login-button")).click();
        sleepInSecond(5);

        driver.findElement(By.xpath("//span[text()='My Info']")).click();
        sleepInSecond(6);

        Assert.assertEquals(driver.findElement(By.name("firstName")).getAttribute("value"),"Nguyen");
        Assert.assertEquals(driver.findElement(By.name("middleName")).getAttribute("value"),"Thu");
        Assert.assertEquals(driver.findElement(By.name("lastName")).getAttribute("value"),"Trang");
        Assert.assertEquals(driver.findElement(By.xpath("//label[text()='Employee Id']/parent::div/following-sibling::div/input")).getAttribute("value"),employeeID);

        driver.findElement(By.xpath("//a[text()='Immigration']")).click();
        sleepInSecond(5);

        driver.findElement(By.cssSelector("i.bi-pencil-fill")).click();
        sleepInSecond(6);

        Assert.assertEquals(driver.findElement(By.xpath("//label[text()='Number']/parent::div/following-sibling::div/input")).getAttribute("value"),passportNumber);
        Assert.assertEquals(driver.findElement(By.xpath("//label[text()='Comments']/parent::div/following-sibling::div/textarea")).getAttribute("value"),comments);

    }

    @Test
    public void TC_02_() {

    }

    public void sleepInSecond (long timeInSecond){
        try {
            Thread.sleep(timeInSecond * 1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }


    @AfterClass
    public void afterClass() {
        driver.quit();
    }
}