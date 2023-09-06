package webdriver;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Topic_06_WebElement_Exercise_P2 {
    WebDriver driver;
    String projectPath = System.getProperty("user.dir");
    String osName = System.getProperty("os.name");

    By myAccountLink = By.cssSelector("div.footer a[title='My Account']");
    By loginButton = By.id("send2");
    By emailTextbox = By.id("email");
    By passTextbox = By.id("pass");
    String email, firstName, lastName, fullName;


    @BeforeClass
    public void beforeClass() {
        if (osName.contains("Windows")) {
            System.setProperty("webdriver.gecko.driver", projectPath + "\\browserDrivers\\geckodriver.exe");
        } else {
            System.setProperty("webdriver.gecko.driver", projectPath + "/browserDrivers/geckodriver");
        }

        Random ran = new Random();

        driver = new FirefoxDriver();
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        driver.manage().window().maximize();

        email = "trangnguyen" + ran.nextInt(9999) + "@gmail.com";
        firstName = "Trang";
        lastName = "Nguyen";
        fullName = firstName + " " + lastName;

    }

    @Test
    public void TC_01_EmptyEmailPass() {
        driver.get("http://live.techpanda.org/");
        driver.findElement(myAccountLink).click();
        driver.findElement(loginButton).click();

        Assert.assertEquals(driver.findElement(By.id("advice-required-entry-email")).getText(),"This is a required field.");
        Assert.assertEquals(driver.findElement(By.id("advice-required-entry-pass")).getText(),"This is a required field.");

    }

    @Test
    public void TC_02_InvalidEmail() {
        driver.get("http://live.techpanda.org/");
        driver.findElement(myAccountLink).click();
        driver.findElement(emailTextbox).sendKeys("2134123@3213.34234");
        driver.findElement(passTextbox).sendKeys("123456");
        driver.findElement(loginButton).click();

        Assert.assertEquals(driver.findElement(By.id("advice-validate-email-email")).getText(),"Please enter a valid email address. For example johndoe@domain.com.");

    }

    @Test
    public void TC_03_PassLessThan6Character() {
        driver.get("http://live.techpanda.org/");
        driver.findElement(myAccountLink).click();
        driver.findElement(emailTextbox).sendKeys(email);
        driver.findElement(passTextbox).sendKeys("123");
        driver.findElement(loginButton).click();

        Assert.assertEquals(driver.findElement(By.id("advice-validate-password-pass")).getText(),"Please enter 6 or more characters without leading or trailing spaces.");


    }

    @Test
    public void TC_04_IncorrectEmailPass() {
        driver.get("http://live.techpanda.org/");
        driver.findElement(myAccountLink).click();
        driver.findElement(emailTextbox).sendKeys(email);
        driver.findElement(passTextbox).sendKeys("1233423423");
        driver.findElement(loginButton).click();

        Assert.assertEquals(driver.findElement(By.cssSelector("li[class='error-msg'] span")).getText(),"Invalid login or password.");


    }

    @Test
    public void TC_05_Register() {
        driver.get("http://live.techpanda.org/");
        driver.findElement(myAccountLink).click();
        driver.findElement(By.cssSelector("form#login-form a[title='Create an Account']")).click();

        driver.findElement(By.id("firstname")).sendKeys(firstName);
        driver.findElement(By.id("lastname")).sendKeys(lastName);
        driver.findElement(By.id("email_address")).sendKeys(email);
        driver.findElement(By.id("password")).sendKeys("123456");
        driver.findElement(By.id("confirmation")).sendKeys("123456");

        driver.findElement(By.cssSelector("div[class='buttons-set'] button")).click();

        Assert.assertEquals(driver.findElement(By.cssSelector("li.success-msg span")).getText(), "Thank you for registering with Main Website Store.");

        String contactInfo = driver.findElement(By.xpath("//h3[text()='Contact Information']/parent::div/following-sibling::div/p")).getText();
        System.out.println(contactInfo);

        Assert.assertTrue(contactInfo.contains(fullName));
        Assert.assertTrue(contactInfo.contains(email));

        driver.findElement(By.xpath("//div[@class='account-cart-wrapper']//span[text()='Account']")).click();
        driver.findElement(By.cssSelector("div#header-account a[title='Log Out']")).click();

        Assert.assertTrue(driver.findElement(By.cssSelector("div.page-title h2 img")).isDisplayed());


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