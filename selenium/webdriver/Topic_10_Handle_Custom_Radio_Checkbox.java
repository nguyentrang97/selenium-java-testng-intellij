package webdriver;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Topic_10_Handle_Custom_Radio_Checkbox {
    WebDriver driver;
    JavascriptExecutor jsExecutor;
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

        //Luôn khởi tạo sau biến driver này
        jsExecutor = (JavascriptExecutor) driver;
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        driver.manage().window().maximize();
    }

    @Test
    public void TC_01_() {
        driver.get("https://tiemchungcovid19.gov.vn/portal/register-person");
        sleepInSecond(3);

        /* Case 1 */
        //Thẻ input bị che nên không thao tác được
        //Thẻ input lại dùng để verify -> Vì hàm isSelected() nó chỉ work với thẻ input.

        //Thao tác chọn
        driver.findElement(By.xpath("//div[text()='Đăng ký cho người thân']/preceding-sibling::div/input")).click();

        //Verify đã chọn thành công
        Assert.assertTrue(driver.findElement(By.xpath("//div[text()='Đăng ký cho người thân']/preceding-sibling::div/input")).isSelected());

    }

    @Test
    public void TC_02_() {
        driver.get("https://tiemchungcovid19.gov.vn/portal/register-person");
        sleepInSecond(3);

        /* Case 2 */
        //Thẻ khác với thẻ input để click (span/div/label/..) --> đang hiển thị là được
        //Thẻ này lại không dùng để verify được  --> Vì hàm isSelected() nó chỉ work với thẻ input.

        //Thao tác chọn
        driver.findElement(By.xpath("//div[text()='Đăng ký cho người thân']/parent::label")).click();

        //Verify đã chọn thành công
        Assert.assertTrue(driver.findElement(By.xpath("//div[text()='Đăng ký cho người thân']/parent::label")).isSelected());

        //Thẻ span/div/label luôn luôn trả về false

    }

    @Test
    public void TC_03_() {
        driver.get("https://tiemchungcovid19.gov.vn/portal/register-person");
        sleepInSecond(3);

        /* Case 3 */
        //Thẻ khác với thẻ input để click (span/div/label/..) --> đang hiển thị là được
        //Thẻ input lại dùng để verify -> Vì hàm isSelected() nó chỉ work với thẻ input.

        //Thao tác chọn
        driver.findElement(By.xpath("//div[text()='Đăng ký cho người thân']/parent::label")).click();

        //Verify đã chọn thành công
        Assert.assertTrue(driver.findElement(By.xpath("//div[text()='Đăng ký cho người thân']/preceding-sibling::div/input")).isSelected());


        // Ở trường hợp này viết basic demo thì được
        //Nếu apply vào framework/ dự án thực tế thì không nên
        //Vì 1 element phải define tới nhiều locator (dễ bị hiểu nhầm/mất thơi gian để maintain)
    }

    @Test
    public void TC_04_() {
        driver.get("https://tiemchungcovid19.gov.vn/portal/register-person");
        sleepInSecond(3);

        //Thẻ input bị ẩn nhưng vẫn dùng để click
        /* Case 4 */
        //Hàm click() của WebElement nó sẽ không thao tác vào element bị ẩn được
        //  ==> Thẻ element must be visible and it must have a height and width greater then 0.

        //Nên sẽ dùng 1 hàm click() của Javascript để click (click vào element bị ẩn được)
        //Selenium nó cung cấp 1 thử viện đ có thể nhúng các đoạn code JS vào kịch bản test được ==> JavascriptExecutor

        //Thẻ input lại dùng để verify -> Vì hàm isSelected() nó chỉ work với thẻ input.

        By radioButton = By.xpath("//div[text()='Đăng ký cho người thân']/preceding-sibling::div/input");

        //Thao tác chọn
        jsExecutor.executeScript("arguments[0].click();", driver.findElement(radioButton));
        sleepInSecond(3);

        //arguments[0]    => element 1
        //arguments[0]    => element 2
        //jsExecutor.executeScript("arguments[0].click(); arguments[1].click();",driver.findElement(By.xpath("")),driver.findElement(By.xpath("")));


        //Verify đã chọn thành công
        Assert.assertTrue(driver.findElement(radioButton).isSelected());


    }

    @Test
    public void TC_05_() {
        driver.get("https://tiemchungcovid19.gov.vn/portal/register-person");
        sleepInSecond(3);

        By radioButton = By.cssSelector("");
        By checkbox = By.cssSelector("");

        //Thao tasc chonj
        jsExecutor.executeScript("arguments[0].click();", driver.findElement(radioButton));
        sleepInSecond(2);

        jsExecutor.executeScript("arguments[0].click();", driver.findElement(checkbox));
        sleepInSecond(2);

        //Verify đã chọn thành công
        //Cách 1
        Assert.assertTrue(driver.findElement(By.cssSelector("")).isDisplayed());
        Assert.assertTrue(driver.findElement(By.cssSelector("")).isDisplayed());

        //Cách 2
        Assert.assertEquals(driver.findElement(radioButton).getAttribute("aria-checked"), "true");
        Assert.assertEquals(driver.findElement(checkbox).getAttribute("aria-checked"), "true");


    }

    public void sleepInSecond(long timeInSecond) {
        try {
            Thread.sleep(timeInSecond * 1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public int getRandomNumber() {
        Random ran = new Random();
        return ran.nextInt(99999);
    }


    @AfterClass
    public void afterClass() {
        driver.quit();
    }
}