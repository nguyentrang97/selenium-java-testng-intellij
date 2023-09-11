package webdriver;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.Color;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Topic_09_Handle_Button_Radio_Checkbox_Default {
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
    public void TC_01_Button() {
        driver.get("https://www.fahasa.com/customer/account/create");
        driver.findElement(By.cssSelector("li.popup-login-tab-login")).click();

        By loginButton = By.cssSelector("button.fhs-btn-login");

        //Verify login button is disable
        Assert.assertFalse(driver.findElement(loginButton).isEnabled());

        String loginButtonBackground = driver.findElement(loginButton).getCssValue("background-image");
        Assert.assertTrue(loginButtonBackground.contains("rgb(224, 224, 224)"));

        driver.findElement(By.cssSelector("input#login_username")).sendKeys("0987623541");
        driver.findElement(By.cssSelector("input#login_password")).sendKeys("0987623541");
        sleepInSecond(2);

        //Verify login button is enabled
        Assert.assertTrue(driver.findElement(loginButton).isEnabled());
        loginButtonBackground = driver.findElement(loginButton).getCssValue("background-color");
        Color loginButtonBackgroundColor = Color.fromString(loginButtonBackground);
        Assert.assertEquals(loginButtonBackgroundColor.asHex().toUpperCase(),"#C92127");

    }

    @Test
    public void TC_02_Default_Checkbox_Radio_Single() {
        driver.get("https://automationfc.github.io/multiple-fields/");

        //Click chọn 1 checkbox
        driver.findElement(By.xpath("//label[contains(text(),'Rheumatic Fever')]/preceding-sibling::input")).click();

        //Click chọn 1 radio
        driver.findElement(By.xpath("//label[contains(text(),\"I don't drink\")]/preceding-sibling::input")).click();

        //Verify các checkbox/radio đã được chọn rồi
        Assert.assertTrue(driver.findElement(By.xpath("//label[contains(text(),'Rheumatic Fever')]/preceding-sibling::input")).isSelected());
        Assert.assertTrue(driver.findElement(By.xpath("//label[contains(text(),\"I don't drink\")]/preceding-sibling::input")).isSelected());

        //Checkbox có thể tự bỏ chọn được
        driver.findElement(By.xpath("//label[contains(text(),'Rheumatic Fever')]/preceding-sibling::input")).click();

        //Verify checkbox đã tự bỏ chọn được
        Assert.assertFalse(driver.findElement(By.xpath("//label[contains(text(),'Rheumatic Fever')]/preceding-sibling::input")).isSelected());

        //Radio không thể tự bỏ chọn được
        driver.findElement(By.xpath("//label[contains(text(),\"I don't drink\")]/preceding-sibling::input")).click();

        //Verify radio vẫn đang được chọn
        Assert.assertTrue(driver.findElement(By.xpath("//label[contains(text(),\"I don't drink\")]/preceding-sibling::input")).isSelected());

    }

    @Test
    public void TC_03_Default_Checkbox_Radio_Multiple() {
        driver.get("https://automationfc.github.io/multiple-fields/");

        List<WebElement> allcheckboxes = driver.findElements(By.cssSelector("input.form-checkbox"));

        //Dùng vòng lặp để duyệt qua và click vào tất cả các checkbox này
        for (WebElement checkbox : allcheckboxes) {
            checkbox.click();
        }

        //Verify tất cả các checkbox được chọn thành công
        for (WebElement checkbox : allcheckboxes) {
            Assert.assertTrue(checkbox.isSelected());
        }
        sleepInSecond(2);

        //Nếu như gặp 1 checkbox có tên là X thì mới click
        for (WebElement checkbox : allcheckboxes) {
            if (checkbox.getAttribute("value").equals("Epilepsy Seizures") || checkbox.getAttribute("value").equals("Hepatitis")){
                checkbox.click();
            }
        }

    }

    @Test
    public void TC_04_Default_Checkbox_() {
        driver.get("https://demos.telerik.com/kendo-ui/checkbox/index");

        //Chọn nó
        checkToCheckbox(By.xpath("//label[contains(text(),'Heated front and rear seats')]/preceding-sibling::input"));
        Assert.assertTrue(driver.findElement(By.xpath("//label[contains(text(),'Heated front and rear seats')]/preceding-sibling::input")).isSelected());

        //Bỏ chọn nó
        uncheckToCheckbox(By.xpath("//label[contains(text(),'Rear side airbags')]/preceding-sibling::input"));
        Assert.assertFalse(driver.findElement(By.xpath("//label[contains(text(),'Rear side airbags')]/preceding-sibling::input")).isSelected());

    }

    public void checkToCheckbox (By by){
        if (!driver.findElement(by).isSelected()){
            driver.findElement(by).click();
        }
    }

    public void uncheckToCheckbox (By by){
        if (driver.findElement(by).isSelected()){
            driver.findElement(by).click();
        }
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
        //driver.quit();
    }
}