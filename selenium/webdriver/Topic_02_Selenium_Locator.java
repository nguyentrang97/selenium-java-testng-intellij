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

    }

    @Test
    public void TC_03_Name() {

    }
}
