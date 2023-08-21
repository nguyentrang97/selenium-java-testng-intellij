package webdriver;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic_02_Selenium_Locator {
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
        driver.get("https://demo.nopcommerce.com/register");
    }


    //TestNG: Order testcase theo Alphabet (0-9 A-Z)
    //Firstname textbox - HTML Code
    //HTML Element: <tagname attribute_name_1='attribute_value_1' attribute_name_2='attribute_value_2' ...>
    //   <input type="text" data-val="true" data-val-required="First name is required." id="FirstName" name="FirstName">

    @Test
    public void TC_01_ID() {
        //Tìm element có ID = FirstName
        driver.findElement(By.id("FirstName")).sendKeys("Trang Nguyen");
        //System.out.println(driver.findElement(By.id("FirstName")));
    }

    @Test
    public void TC_02_Class() {
        driver.findElement(By.className("header-logo"));
    }

    @Test
    public void TC_03_Name() {
        driver.findElement(By.name("DateOfBirthDay"));
    }

    @Test
    public void TC_04_Tagname() {
        driver.findElement(By.tagName("input"));
    }

    @Test
    public void TC_05_LinkText() {
        //Độ chínhxasasc cao = tuyệt đối = toàn bộ
        driver.findElement(By.linkText("Shipping & returns"));
    }

    @Test
    public void TC_06_Partial_Linktext() {
        //Độ chính xác không cao = tuoương đối = 1 phần (đầu/cuối/giữa)
        driver.findElement(By.partialLinkText("Apply for vendor"));
        driver.findElement(By.partialLinkText("vendor account"));
        driver.findElement(By.partialLinkText("for vendor"));
    }
    public void TC_07_Css() {
        //CSS với ID
        driver.findElement(By.cssSelector("input[id='FirstName']"));
        driver.findElement(By.name("input#FirstName"));
        driver.findElement(By.name("#FirstName"));

        //CSS với Class
        driver.findElement(By.cssSelector("div[class='header-menu']"));
        driver.findElement(By.cssSelector("div.header-menu"));
        driver.findElement(By.cssSelector(".header-menu"));

        //CSS với Name
        driver.findElement(By.cssSelector("input[name='FirstName']"));

        //CSS với Tagname
        driver.findElement(By.cssSelector("input"));

        //CSS với link
        driver.findElement(By.cssSelector("a[href='customer/addresses']"));

        //CSS với partial link
        driver.findElement(By.cssSelector("a[href*='addresses']"));  //giữa
        //driver.findElement(By.cssSelector("a[href^='addresses']"));  //đầu
        //driver.findElement(By.cssSelector("a[href$='addresses']"));  //cuối
    }

    public void TC_08_Xpath() {
        //Xpath với ID
        driver.findElement(By.xpath("//input[@id='FirstName']"));

        //Xpath với Class
        driver.findElement(By.xpath("//div[@class='header-menu']"));

        //Xpath với Name
        driver.findElement(By.xpath("//input[@name='FirstName']"));

        //Xpath với Tagname
        driver.findElement(By.xpath("//input"));

        //Xpath với link
        driver.findElement(By.xpath("//a[@href='customer/addresses']"));
        driver.findElement(By.xpath("//a[text()='Addresses']"));

        //Xpath với partial link
        driver.findElement(By.xpath("a[contains(@href,'addresses')]"));
        driver.findElement(By.xpath("a[contains(text(),'Addresses']"));
    }
    @AfterClass
    public void afterClass() {
        //driver.quit();
    }
}
