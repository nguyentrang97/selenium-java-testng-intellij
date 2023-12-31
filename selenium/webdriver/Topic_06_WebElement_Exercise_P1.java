package webdriver;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.concurrent.TimeUnit;

public class Topic_06_WebElement_Exercise_P1 {
    WebDriver driver;
    String projectPath = System.getProperty("user.dir");
    String osName = System.getProperty("os.name");
    By emailTextbox = By.id("mail");
    By under18RadioButton = By.id("under_18");
    By eduTextbox = By.id("edu");
    By nameUser5Label = By.xpath("//h5[contains(text(),'Name: User5')]");
    By jobRole1Dropdown = By.id("job1");
    By jobRole2Dropdown = By.id("job2");
    By developmentCheckbox = By.id("development");
    By slider1 = By.id("slider-1");
    By passTextbox = By.id("disable_password");
    By radioDisable = By.id("radio-disabled");
    By bioTextarea = By.id("bio");
    By jobRole3Dropdown = By.id("job3");
    By interestCheckboxDisable = By.id("check-disbaled");
    By slider2 = By.id("slider-2");
    By javaCheckbox = By.id("java");

    By email = By.id("email");
    By pass = By.id("new_password");


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

        driver.get("https://automationfc.github.io/basic-form/index.html");
    }

    @Test
    public void TC_01_Displayed() {
        //driver.get("https://automationfc.github.io/basic-form/index.html");

        if (driver.findElement(emailTextbox).isDisplayed()) {
            System.out.println("Email textbox is displayed");
            driver.findElement(emailTextbox).sendKeys("Automation Testing 1");
        } else {
            System.out.println("Email textbox is not displayed");
        }

        if (driver.findElement(under18RadioButton).isDisplayed()) {
            System.out.println("Under 18 radio button is displayed");
            driver.findElement(under18RadioButton).click();
        } else {
            System.out.println("Under 18 radio button is not displayed");
        }

        if (driver.findElement(eduTextbox).isDisplayed()) {
            System.out.println("Education textbox is displayed");
            driver.findElement(eduTextbox).sendKeys("Automation Testing 2");
        } else {
            System.out.println("Education textbox is not displayed");
        }

        if (driver.findElement(nameUser5Label).isDisplayed()) {
            System.out.println("Text 'Name: User5' is displayed");
        } else {
            System.out.println("Text 'Name: User5' is not displayed");
        }

    }

    @Test
    public void TC_02_Enable() {
        //driver.get("https://automationfc.github.io/basic-form/index.html");

        if (driver.findElement(emailTextbox).isEnabled()) {
            System.out.println("Email textbox is enabled");
        } else {
            System.out.println("Email textbox is not enabled");
        }

        if (driver.findElement(under18RadioButton).isEnabled()) {
            System.out.println("Under 18 radio button is enabled");
        } else {
            System.out.println("Under 18 radio button is not enabled");
        }

        if (driver.findElement(eduTextbox).isEnabled()) {
            System.out.println("Education textbox is enabled");
        } else {
            System.out.println("Education textbox is not enabled");
        }

        if (driver.findElement(jobRole1Dropdown).isEnabled()) {
            System.out.println("Job Role 1 is enabled");
        } else {
            System.out.println("Job Role 1 is not enabled");
        }

        if (driver.findElement(jobRole2Dropdown).isEnabled()) {
            System.out.println("Job Role 2 is enabled");
        } else {
            System.out.println("Job Role 2 is not enabled");
        }

        if (driver.findElement(developmentCheckbox).isEnabled()) {
            System.out.println("Development checkbox is enabled");
        } else {
            System.out.println("Development checkbox is not enabled");
        }

        if (driver.findElement(slider1).isEnabled()) {
            System.out.println("Slider 1 is enabled");
        } else {
            System.out.println("slider 1  is not enabled");
        }

        if (driver.findElement(passTextbox).isEnabled()) {
            System.out.println("Password textbox is enabled");
        } else {
            System.out.println("Password textbox is not enabled");
        }

        if (driver.findElement(radioDisable).isEnabled()) {
            System.out.println("Radio is enabled");
        } else {
            System.out.println("Radio is not enabled");
        }

        if (driver.findElement(bioTextarea).isEnabled()) {
            System.out.println("Biography textarea is enabled");
        } else {
            System.out.println("Biography textarea is not enabled");
        }

        if (driver.findElement(jobRole3Dropdown).isEnabled()) {
            System.out.println("Job Role 3 is enabled");
        } else {
            System.out.println("Job Role 3 is not enabled");
        }

        if (driver.findElement(jobRole3Dropdown).isEnabled()) {
            System.out.println("Job Role 3 is enabled");
        } else {
            System.out.println("Job Role 3 is not enabled");
        }

        if (driver.findElement(interestCheckboxDisable).isEnabled()) {
            System.out.println("Interest Checkbox is enabled");
        } else {
            System.out.println("Interest Checkbox is not enabled");
        }

        if (driver.findElement(slider2).isEnabled()) {
            System.out.println("Slider 2 is enabled");
        } else {
            System.out.println("Slider 2 is not enabled");
        }

    }

    @Test
    public void TC_03_Selected() {
        driver.findElement(under18RadioButton).click();
        driver.findElement(javaCheckbox).click();

        if (driver.findElement(under18RadioButton).isSelected()) {
            System.out.println("Under 18 radio is selected");
        } else {
            System.out.println("Under 18 radio is not selected");
        }

        if (driver.findElement(javaCheckbox).isSelected()) {
            System.out.println("Java checkbox is selected");
        } else {
            System.out.println("Java checkbox is not selected");
        }

        driver.findElement(javaCheckbox).click();
        if (driver.findElement(javaCheckbox).isSelected()) {
            System.out.println("Java checkbox is selected");
        } else {
            System.out.println("Java checkbox is not selected");
        }

    }

    @Test
    public void TC_04_Register_MailChimp() {
        driver.get("https://login.mailchimp.com/signup/");
        driver.findElement(email).sendKeys("trang@gmail.com");
        driver.findElement(pass).sendKeys("aaa");

        sleepInSecond(2);
        Assert.assertTrue(driver.findElement(By.cssSelector("li[class='lowercase-char completed']")).isDisplayed());
        Assert.assertTrue(driver.findElement(By.cssSelector("li[class='uppercase-char not-completed']")).isDisplayed());
        Assert.assertTrue(driver.findElement(By.cssSelector("li[class='number-char not-completed']")).isDisplayed());
        Assert.assertTrue(driver.findElement(By.cssSelector("li[class='special-char not-completed']")).isDisplayed());
        Assert.assertTrue(driver.findElement(By.cssSelector("li[class='8-char not-completed']")).isDisplayed());

        driver.findElement(pass).clear();
        driver.findElement(pass).sendKeys("AAAA");
        sleepInSecond(2);
        Assert.assertTrue(driver.findElement(By.cssSelector("li[class='lowercase-char not-completed']")).isDisplayed());
        Assert.assertTrue(driver.findElement(By.cssSelector("li[class='uppercase-char completed']")).isDisplayed());
        Assert.assertTrue(driver.findElement(By.cssSelector("li[class='number-char not-completed']")).isDisplayed());
        Assert.assertTrue(driver.findElement(By.cssSelector("li[class='special-char not-completed']")).isDisplayed());
        Assert.assertTrue(driver.findElement(By.cssSelector("li[class='8-char not-completed']")).isDisplayed());

        driver.findElement(pass).clear();
        driver.findElement(pass).sendKeys("12345");
        sleepInSecond(2);
        Assert.assertTrue(driver.findElement(By.cssSelector("li[class='lowercase-char not-completed']")).isDisplayed());
        Assert.assertTrue(driver.findElement(By.cssSelector("li[class='uppercase-char not-completed']")).isDisplayed());
        Assert.assertTrue(driver.findElement(By.cssSelector("li[class='number-char completed']")).isDisplayed());
        Assert.assertTrue(driver.findElement(By.cssSelector("li[class='special-char not-completed']")).isDisplayed());
        Assert.assertTrue(driver.findElement(By.cssSelector("li[class='8-char not-completed']")).isDisplayed());

        driver.findElement(pass).clear();
        driver.findElement(pass).sendKeys("@#$");
        sleepInSecond(2);
        Assert.assertTrue(driver.findElement(By.cssSelector("li[class='lowercase-char not-completed']")).isDisplayed());
        Assert.assertTrue(driver.findElement(By.cssSelector("li[class='uppercase-char not-completed']")).isDisplayed());
        Assert.assertTrue(driver.findElement(By.cssSelector("li[class='number-char not-completed']")).isDisplayed());
        Assert.assertTrue(driver.findElement(By.cssSelector("li[class='special-char completed']")).isDisplayed());
        Assert.assertTrue(driver.findElement(By.cssSelector("li[class='8-char not-completed']")).isDisplayed());

        driver.findElement(pass).clear();
        driver.findElement(pass).sendKeys("Aa1@");
        sleepInSecond(2);
        Assert.assertTrue(driver.findElement(By.cssSelector("li[class='lowercase-char completed']")).isDisplayed());
        Assert.assertTrue(driver.findElement(By.cssSelector("li[class='uppercase-char completed']")).isDisplayed());
        Assert.assertTrue(driver.findElement(By.cssSelector("li[class='number-char completed']")).isDisplayed());
        Assert.assertTrue(driver.findElement(By.cssSelector("li[class='special-char completed']")).isDisplayed());
        Assert.assertTrue(driver.findElement(By.cssSelector("li[class='8-char not-completed']")).isDisplayed());

        driver.findElement(pass).clear();
        driver.findElement(pass).sendKeys("A564565");
        sleepInSecond(2);
        Assert.assertTrue(driver.findElement(By.cssSelector("li[class='lowercase-char not-completed']")).isDisplayed());
        Assert.assertTrue(driver.findElement(By.cssSelector("li[class='uppercase-char completed']")).isDisplayed());
        Assert.assertTrue(driver.findElement(By.cssSelector("li[class='number-char completed']")).isDisplayed());
        Assert.assertTrue(driver.findElement(By.cssSelector("li[class='special-char not-completed']")).isDisplayed());
        Assert.assertTrue(driver.findElement(By.cssSelector("li[class='8-char not-completed']")).isDisplayed());


        driver.findElement(pass).clear();
        driver.findElement(pass).sendKeys("A564565@a");
        sleepInSecond(2);
        Assert.assertFalse(driver.findElement(By.cssSelector("li[class='lowercase-char completed']")).isDisplayed());
        Assert.assertFalse(driver.findElement(By.cssSelector("li[class='uppercase-char completed']")).isDisplayed());
        Assert.assertFalse(driver.findElement(By.cssSelector("li[class='number-char completed']")).isDisplayed());
        Assert.assertFalse(driver.findElement(By.cssSelector("li[class='special-char completed']")).isDisplayed());
        Assert.assertFalse(driver.findElement(By.cssSelector("li[class='8-char completed']")).isDisplayed());

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