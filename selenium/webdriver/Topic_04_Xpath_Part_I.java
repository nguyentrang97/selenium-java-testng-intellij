package webdriver;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.concurrent.TimeUnit;

public class Topic_04_Xpath_Part_I {
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
    public void TC_01_EmptyData() {
        driver.get("https://alada.vn/tai-khoan/dang-ky.html");

        WebElement btnDangKy = driver.findElement(By.xpath("//form[@id='frmLogin']//button[text()='ĐĂNG KÝ']"));
        btnDangKy.click();

        WebElement lblFirstNameError = driver.findElement(By.xpath("//label[@id='txtFirstname-error']"));
        WebElement lblEmailError = driver.findElement(By.xpath("//label[@id='txtEmail-error']"));
        WebElement lblCEmailError = driver.findElement(By.xpath("//label[@id='txtCEmail-error']"));
        WebElement lblPasswordError = driver.findElement(By.xpath("//label[@id='txtPassword-error']"));
        WebElement lblCPasswordError = driver.findElement(By.xpath("//label[@id='txtCPassword-error']"));
        WebElement lblPhoneNumberError = driver.findElement(By.xpath("//label[@id='txtPhone-error']"));

        Assert.assertEquals(lblFirstNameError.getText(),"Vui lòng nhập họ tên");
        Assert.assertEquals(lblEmailError.getText(),"Vui lòng nhập email");
        Assert.assertEquals(lblCEmailError.getText(),"Vui lòng nhập lại địa chỉ email");
        Assert.assertEquals(lblPasswordError.getText(),"Vui lòng nhập mật khẩu");
        Assert.assertEquals(lblCPasswordError.getText(),"Vui lòng nhập lại mật khẩu");
        Assert.assertEquals(lblPhoneNumberError.getText(),"Vui lòng nhập số điện thoại.");

    }

    @Test
    public void TC_02_InvalidEmail() {
        driver.get("https://alada.vn/tai-khoan/dang-ky.html");

        driver.findElement(By.id("txtFirstname")).sendKeys("Trang Nguyen");
        driver.findElement(By.id("txtEmail")).sendKeys("trangnguyen");
        driver.findElement(By.id("txtCEmail")).sendKeys("trangnguyen");
        driver.findElement(By.id("txtPassword")).sendKeys("12345678");
        driver.findElement(By.id("txtCPassword")).sendKeys("12345678");
        driver.findElement(By.id("txtPhone")).sendKeys("0337719721");

        driver.findElement(By.xpath("//form[@id='frmLogin']//button[text()='ĐĂNG KÝ']")).click();

        Assert.assertEquals(driver.findElement(By.id("txtEmail-error")).getText(),"Vui lòng nhập email hợp lệ");
        Assert.assertEquals(driver.findElement(By.id("txtCEmail-error")).getText(),"Email nhập lại không đúng");

    }

    @Test
    public void TC_03_IncorrectConfirmEmail() {
        driver.get("https://alada.vn/tai-khoan/dang-ky.html");

        driver.findElement(By.id("txtFirstname")).sendKeys("Trang Nguyen");
        driver.findElement(By.id("txtEmail")).sendKeys("trangnguyen@gmail.com");
        driver.findElement(By.id("txtCEmail")).sendKeys("trangnguyen");
        driver.findElement(By.id("txtPassword")).sendKeys("12345678");
        driver.findElement(By.id("txtCPassword")).sendKeys("12345678");
        driver.findElement(By.id("txtPhone")).sendKeys("0337719721");

        driver.findElement(By.xpath("//form[@id='frmLogin']//button[text()='ĐĂNG KÝ']")).click();

        Assert.assertEquals(driver.findElement(By.id("txtCEmail-error")).getText(),"Email nhập lại không đúng");

    }
    @Test
    public void TC_04_PasswordLess6Character() {
        driver.get("https://alada.vn/tai-khoan/dang-ky.html");

        driver.findElement(By.id("txtFirstname")).sendKeys("Trang Nguyen");
        driver.findElement(By.id("txtEmail")).sendKeys("trangnguyen@gmail.com");
        driver.findElement(By.id("txtCEmail")).sendKeys("trangnguyen@gmail.com");
        driver.findElement(By.id("txtPassword")).sendKeys("12345");
        driver.findElement(By.id("txtCPassword")).sendKeys("12345");
        driver.findElement(By.id("txtPhone")).sendKeys("0337719721");

        driver.findElement(By.xpath("//form[@id='frmLogin']//button[text()='ĐĂNG KÝ']")).click();

        Assert.assertEquals(driver.findElement(By.id("txtPassword-error")).getText(),"Mật khẩu phải có ít nhất 6 ký tự");
        Assert.assertEquals(driver.findElement(By.id("txtCPassword-error")).getText(),"Mật khẩu phải có ít nhất 6 ký tự");
    }

    @Test
    public void TC_05_IncorrectConfirmPassword() {
        driver.get("https://alada.vn/tai-khoan/dang-ky.html");

        driver.findElement(By.id("txtFirstname")).sendKeys("Trang Nguyen");
        driver.findElement(By.id("txtEmail")).sendKeys("trangnguyen@gmail.com");
        driver.findElement(By.id("txtCEmail")).sendKeys("trangnguyen@gmail.com");
        driver.findElement(By.id("txtPassword")).sendKeys("12345678");
        driver.findElement(By.id("txtCPassword")).sendKeys("123456789");
        driver.findElement(By.id("txtPhone")).sendKeys("0337719721");

        driver.findElement(By.xpath("//form[@id='frmLogin']//button[text()='ĐĂNG KÝ']")).click();

        Assert.assertEquals(driver.findElement(By.id("txtCPassword-error")).getText(),"Mật khẩu bạn nhập không khớp");
    }

    @Test
    public void TC_06_InvalidPhoneNumber() {
        driver.get("https://alada.vn/tai-khoan/dang-ky.html");

        driver.findElement(By.id("txtFirstname")).sendKeys("Trang Nguyen");
        driver.findElement(By.id("txtEmail")).sendKeys("trangnguyen@gmail.com");
        driver.findElement(By.id("txtCEmail")).sendKeys("trangnguyen@gmail.com");
        driver.findElement(By.id("txtPassword")).sendKeys("12345678");
        driver.findElement(By.id("txtCPassword")).sendKeys("123456789");

        //Nhập < 10 ký tự
        driver.findElement(By.id("txtPhone")).sendKeys("033771972");
        driver.findElement(By.xpath("//form[@id='frmLogin']//button[text()='ĐĂNG KÝ']")).click();
        Assert.assertEquals(driver.findElement(By.id("txtPhone-error")).getText(),"Số điện thoại phải từ 10-11 số.");

        //Nhập > 11 ký tự
        driver.findElement(By.id("txtPhone")).clear();
        driver.findElement(By.id("txtPhone")).sendKeys("033771972123");
        driver.findElement(By.xpath("//form[@id='frmLogin']//button[text()='ĐĂNG KÝ']")).click();
        Assert.assertEquals(driver.findElement(By.id("txtPhone-error")).getText(),"Số điện thoại phải từ 10-11 số.");

        //Nhập > 11 ký tự
        driver.findElement(By.id("txtPhone")).clear();
        driver.findElement(By.id("txtPhone")).sendKeys("3377197211");
        driver.findElement(By.xpath("//form[@id='frmLogin']//button[text()='ĐĂNG KÝ']")).click();
        Assert.assertEquals(driver.findElement(By.id("txtPhone-error")).getText(),"Số điện thoại bắt đầu bằng: 09 - 03 - 012 - 016 - 018 - 019 - 088 - 03 - 05 - 07 - 08");
    }

    @AfterClass
    public void afterClass() {
        driver.quit();
    }
}