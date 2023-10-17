package webdriver;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.File;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Topic_20_Upload_File {
    WebDriver driver;
    JavascriptExecutor jsExecutor;
    String projectPath = System.getProperty("user.dir");
    String osName = System.getProperty("os.name");

    String dalatPhoto = "Da Lat.jpg";
    String hanoiPhoto = "Ha Noi.jpg";
    String quangninhPhoto = "Quang Ninh.jpg";

    String dalatPhotoPath = projectPath + File.separator + "uploadFiles" + File.separator + dalatPhoto;
    String hanoiPhotoPath = projectPath + File.separator + "uploadFiles" + File.separator + hanoiPhoto;
    String quangninhPhotoPath = projectPath + File.separator + "uploadFiles" + File.separator + quangninhPhoto;

    @BeforeClass
    public void beforeClass() {
        if (osName.contains("Windows")) {
            System.setProperty("webdriver.gecko.driver", projectPath + "\\browserDrivers\\geckodriver.exe");
        } else {
            System.setProperty("webdriver.gecko.driver", projectPath + "/browserDrivers/geckodriver");
        }

        driver = new FirefoxDriver();
        jsExecutor = (JavascriptExecutor) driver;
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        //driver.manage().window().maximize();
    }

    @Test
    public void TC_01_One_File_Per_Time() {
        driver.get("https://blueimp.github.io/jQuery-File-Upload/");
        By uploadFile = By.xpath("//input[@type='file']");

        //Tải lên từng file 1
        driver.findElement(uploadFile).sendKeys(dalatPhotoPath);
        sleepInSecond(1);

        driver.findElement(uploadFile).sendKeys(hanoiPhotoPath);
        sleepInSecond(1);

        driver.findElement(uploadFile).sendKeys(quangninhPhotoPath);
        sleepInSecond(1);

        //Verify các file đc load lên thành công
        Assert.assertTrue(driver.findElement(By.xpath("//p[@class='name' and text()='" + dalatPhoto + "']")).isDisplayed());
        Assert.assertTrue(driver.findElement(By.xpath("//p[@class='name' and text()='" + hanoiPhoto + "']")).isDisplayed());
        Assert.assertTrue(driver.findElement(By.xpath("//p[@class='name' and text()='" + quangninhPhoto + "']")).isDisplayed());

        //Click upload cho từng file
        List<WebElement> startButtons = driver.findElements(By.cssSelector("tbody.files button.start"));
        for (WebElement start : startButtons) {
            start.click();
            sleepInSecond(1);
        }

        //Verify các file đuợc upload lên thành công
        Assert.assertTrue(driver.findElement(By.xpath("//a[text()='" + dalatPhoto + "']")).isDisplayed());
        Assert.assertTrue(driver.findElement(By.xpath("//a[text()='" + hanoiPhoto + "']")).isDisplayed());
        Assert.assertTrue(driver.findElement(By.xpath("//a[text()='" + quangninhPhoto + "']")).isDisplayed());

        //Verify các hình này được upload lên là hình thực
        Assert.assertTrue(isImageLoaded("//a[@title='" + dalatPhoto + "']/img"));
        Assert.assertTrue(isImageLoaded("//a[@title='" + hanoiPhoto + "']/img"));
        Assert.assertTrue(isImageLoaded("//a[@title='" + quangninhPhoto + "']/img"));

    }

    @Test
    public void TC_02_Multy_File_Per_Time() {
        driver.get("https://blueimp.github.io/jQuery-File-Upload/");
        By uploadFile = By.xpath("//input[@type='file']");

        //Tải nhiều file 1 lúc
        driver.findElement(uploadFile).sendKeys(dalatPhotoPath + "\n" + hanoiPhotoPath + "\n" + quangninhPhotoPath);
        sleepInSecond(2);

        //Verify các file đc load lên thành công
        Assert.assertTrue(driver.findElement(By.xpath("//p[@class='name' and text()='" + dalatPhoto + "']")).isDisplayed());
        Assert.assertTrue(driver.findElement(By.xpath("//p[@class='name' and text()='" + hanoiPhoto + "']")).isDisplayed());
        Assert.assertTrue(driver.findElement(By.xpath("//p[@class='name' and text()='" + quangninhPhoto + "']")).isDisplayed());

        //Click upload cho từng file
        List<WebElement> startButtons = driver.findElements(By.cssSelector("tbody.files button.start"));
        for (WebElement start : startButtons) {
            start.click();
            sleepInSecond(2);
        }

        //Verify các file đuợc upload lên thành công
        Assert.assertTrue(driver.findElement(By.xpath("//a[text()='" + dalatPhoto + "']")).isDisplayed());
        Assert.assertTrue(driver.findElement(By.xpath("//a[text()='" + hanoiPhoto + "']")).isDisplayed());
        Assert.assertTrue(driver.findElement(By.xpath("//a[text()='" + quangninhPhoto + "']")).isDisplayed());

        //Verify các hình này được upload lên là hình thực
        Assert.assertTrue(isImageLoaded("//a[@title='" + dalatPhoto + "']/img"));
        Assert.assertTrue(isImageLoaded("//a[@title='" + hanoiPhoto + "']/img"));
        Assert.assertTrue(isImageLoaded("//a[@title='" + quangninhPhoto + "']/img"));

    }

    public boolean isImageLoaded(String xpath_locator) {
        boolean status = (boolean) jsExecutor.executeScript(
                "return arguments[0].complete && typeof arguments[0].naturalWidth != 'undefined' && arguments[0].naturalWidth > 0", getElement(xpath_locator));
        return status;
    }

    public WebElement getElement(String xpath_locator) {
        return driver.findElement(By.xpath(xpath_locator));
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
        driver.quit();
    }
}