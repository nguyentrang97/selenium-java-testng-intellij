package webdriver;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.locators.RelativeLocator;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.concurrent.TimeUnit;

public class Topic_03_Relative_Locator {
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
        driver.get("https://demo.nopcommerce.com/login");
    }

    @Test
    public void TC_01_Relative_Locator_1() {
        //Login button
        By loginButtonBy = By.cssSelector("button.login-button");
        WebElement loginButton = driver.findElement(By.cssSelector("button.login-button"));

        //Remember me checkbox
        By rememberCheckbboxBy = By.cssSelector("input#RememberMe");
        WebElement remeberCheckbox = driver.findElement(By.cssSelector("input#RememberMe"));

        //Forgot password link
        WebElement forgotPassword = driver.findElement(By.cssSelector("span.forgot-password"));

        //Password input
        WebElement passWord = driver.findElement(By.cssSelector("input#Password"));

        WebElement element = driver.findElement(RelativeLocator.with(By.tagName("label"))
                .above(loginButton)
                .toRightOf(rememberCheckbboxBy)
                .below(passWord)
                .near(forgotPassword)
                .toLeftOf(forgotPassword)
        );
        Assert.assertEquals(element.getText(), "Remember me?");
    }

    @Test
    public void TC_02_Relative_Locator_2() {
        //Email input
        WebElement email = driver.findElement(By.cssSelector("input#Email"));

        //Password input
        WebElement passWord = driver.findElement(By.cssSelector("input#Password"));

        //Returning Customer label
        WebElement returningCustomer = driver.findElement(By.cssSelector("form>div.title>strong"));

        WebElement element2 = driver.findElement(RelativeLocator.with(By.tagName("label"))
                .near(email)
                .above(passWord)
                .below(returningCustomer)
                .toLeftOf(email)
        );

        Assert.assertEquals(element2.getText(),"Email:");

    }
    @AfterClass
    public void afterClass() {
        driver.quit();
    }
}