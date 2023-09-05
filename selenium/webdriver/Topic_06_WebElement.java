package webdriver;

import io.opentelemetry.exporter.logging.SystemOutLogRecordExporter;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.concurrent.TimeUnit;

public class Topic_06_WebElement {
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
    public void TC_01_WebElement() {
        WebElement element = driver.findElement(By.className(""));

        //Dùng cho các textbox/textarea/dropdown (Editable)
        //Xóa dữ liệu đi trước khi nhập text
        element.clear();       //**sử dụng nhiều

        //Dùng cho các textbox/textarea/dropdown (Editable)
        //Nhập liệu
        element.sendKeys("");       //**sử dụng nhiều

        //Click vào các button/link/checkbox/radio/image/..
        element.click();       //**sử dụng nhiều

        String searchAttribute = element.getAttribute("placeholder");       //**sử dụng nhiều
        String searchTextboxAttribute = element.getAttribute("value");

        //GUI: Font/Size/Color/Location/Position
        element.getCssValue("background-color");     //*có sử dụng

        //Vị trí của elenment só với web (bên ngoài)
        Point point = element.getLocation();
        point.x = 324;
        point.y = 324;

        //Kích thước của element (bên trong)
        Dimension di = element.getSize();
        di.getHeight();
        di.getWidth();

        System.out.println(di.height);
        System.out.println(di.width);

        //Location + Size
        Rectangle rec = element.getRect();

        //Chụp hình khi testcase fail
        element.getScreenshotAs(OutputType.BASE64);     //*có sử dụng
        element.getScreenshotAs(OutputType.FILE);
        element.getScreenshotAs(OutputType.BYTES);

        //Cần lấy ra tên thẻ HTML của element đó  -> Truyền vào cho 1 locator khác
        driver.findElement(By.id("Email")).getTagName();
        driver.findElement(By.name("Email")).getTagName();

        String emailTextboxTagname = driver.findElement(By.cssSelector("#Email")).getTagName();
        driver.findElement(By.xpath("//" + emailTextboxTagname + "[@id='email']"));

        //Lấy text từ Error message/success message.label/header
        element.getText();       //**sử dụng nhiều

        //Khi nào dùng getText - getAtribute
        //Khi cái value mình cần lấy nó nằm bên ngoài - getText
        //Khi cái value mình cần lấy nó nằm bên trong - getAttribute

        //Dùng để verify xem 1 element hiển thị hoặc không
        //Phạm vi: tất cả các element
        Assert.assertTrue(element.isDisplayed());     //**sử dụng nhiều
        Assert.assertFalse(element.isDisplayed());

        //Dùng để verify xem 1 element có thao tác được hay không
        //Phạm vi: tất cả các element
        Assert.assertTrue(element.isEnabled());
        Assert.assertFalse(element.isEnabled());

        //Dùng để verify xem 1 element có được chọn hay chưa
        //Phạm vi: checkbox/radio
        Assert.assertTrue(element.isSelected());     //*có sử dụng
        Assert.assertFalse(element.isSelected());

        //Các element nằm trong thẻ form
        //Tương ứng với hành vi End user (Enter)
        element.submit();

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