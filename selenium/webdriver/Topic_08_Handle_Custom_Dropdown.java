package webdriver;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.concurrent.TimeUnit;
import java.util.List;

public class Topic_08_Handle_Custom_Dropdown {
    WebDriver driver;
    WebDriverWait expliciWait;
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
        expliciWait = new WebDriverWait(driver, Duration.ofSeconds(30));
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        driver.manage().window().maximize();
    }

    @Test
    public void TC_01_Jquery() {
        driver.get("https://jqueryui.com/resources/demos/selectmenu/default.html");

        selectItemInDropdown("span#speed-button","ul#speed-menu div[role='option']","Fast");
        Assert.assertEquals(driver.findElement(By.cssSelector("span#speed-button>span.ui-selectmenu-text")).getText(),"Fast");

        sleepInSecond(2);
        selectItemInDropdown("span#speed-button","ul#speed-menu div[role='option']","Slow");
        Assert.assertEquals(driver.findElement(By.cssSelector("span#speed-button>span.ui-selectmenu-text")).getText(),"Slow");

        sleepInSecond(2);
        selectItemInDropdown("span#salutation-button","ul#salutation-menu div[role='option']","Mrs.");
        Assert.assertEquals(driver.findElement(By.cssSelector("span#salutation-button>span.ui-selectmenu-text")).getText(),"Mrs.");

        sleepInSecond(2);
        selectItemInDropdown("span#salutation-button","ul#salutation-menu div[role='option']","Prof.");
        Assert.assertEquals(driver.findElement(By.cssSelector("span#salutation-button>span.ui-selectmenu-text")).getText(),"Prof.");

    }

    @Test
    public void TC_02_ReactJS() {
        driver.get("https://react.semantic-ui.com/maximize/dropdown-example-selection/");

        selectItemInDropdown("div[role='listbox']","div[role='listbox'] span.text","Stevie Feliciano");
        Assert.assertEquals(driver.findElement(By.cssSelector("div.divider.text")).getText(),"Stevie Feliciano");

        sleepInSecond(3);
        selectItemInDropdown("div[role='listbox']","div[role='listbox'] span.text","Matt");
        Assert.assertEquals(driver.findElement(By.cssSelector("div.divider.text")).getText(),"Matt");

    }

    @Test
    public void TC_03_VueJS() {
        driver.get("https://mikerodham.github.io/vue-dropdowns/");

        selectItemInDropdown("div.btn-group","div.btn-group>ul>li>a","Second Option");
        System.out.println(driver.findElement(By.cssSelector("li.dropdown-toggle")).getText());
        Assert.assertTrue(driver.findElement(By.cssSelector("li.dropdown-toggle")).getText().contains("Second Option"));
        Assert.assertEquals(driver.findElement(By.cssSelector("li.dropdown-toggle")).getText().trim(),"Second Option");



        sleepInSecond(3);
        selectItemInDropdown("div.btn-group","div.btn-group>ul>li>a","Third Option");
        System.out.println(driver.findElement(By.cssSelector("li.dropdown-toggle")).getText());
        Assert.assertTrue(driver.findElement(By.cssSelector("li.dropdown-toggle")).getText().contains("Third Option"));
        Assert.assertEquals(driver.findElement(By.cssSelector("li.dropdown-toggle")).getText().trim(),"Third Option");

    }

    @Test
    public void TC_04_Editable() {
        driver.get("https://react.semantic-ui.com/maximize/dropdown-example-search-selection/");

        enterAndSelectItemInDropdown("input.search","div.menu.transition span.text", "Australia");
        Assert.assertEquals(driver.findElement(By.cssSelector("div.divider.text")).getText().trim(),"Australia");

        sleepInSecond(2);
        enterAndSelectItemInDropdown("input.search","div.menu.transition span.text", "Bangladesh");
        Assert.assertEquals(driver.findElement(By.cssSelector("div.divider.text")).getText().trim(),"Bangladesh");

    }

    public void sleepInSecond (long timeInSecond){
        try {
            Thread.sleep(timeInSecond * 1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void selectItemInDropdown(String parentBy, String allItemsBy, String expectedTextItem){
        //1 - Click vào 1 thẻ bất ký để làm sao cho nó xổ ra hết các item của dropdown
        driver.findElement(By.cssSelector(parentBy)).click();
        sleepInSecond(1);

        //2 - Chờ cho tất cả các item được load ra thành công
        //Locator phải lấy đẻ đại diện cho tất cả các item
        //Lấy đến thẻ chứa text
        expliciWait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.cssSelector(allItemsBy)));

        //Đưa hê các item trong dropdown thành 1 list
        List<WebElement> speedDropdownItems = driver.findElements(By.cssSelector(allItemsBy));

        //3 - Tìm item xem đúng cái đang cần không (dùng vòng lặp duyệt qua)
        for (WebElement tempItem : speedDropdownItems) {
            String itemText = tempItem.getText();

            //4 - Kiểm tra cái text của item đúng với cái mình mong muốn
            if (itemText.equals(expectedTextItem)){
                //5 - Click vào item đó
                tempItem.click();

                //Thoát ra khỏi vòng lặp không xét cho các case còn lại nữa
                break;
            }
        }
    }

    public void enterAndSelectItemInDropdown(String textboxByCss, String allItemsBy, String expectedTextItem){
        //1 - Nhập expected text item vào - xở ra tất cả các item matching
        driver.findElement(By.cssSelector(textboxByCss)).sendKeys(expectedTextItem);
        sleepInSecond(1);

        //2 - Chờ cho tất cả các item được load ra thành công
        //Locator phải lấy đẻ đại diện cho tất cả các item
        //Lấy đến thẻ chứa text
        expliciWait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.cssSelector(allItemsBy)));

        //Đưa hết các item trong dropdown thành 1 list
        List<WebElement> speedDropdownItems = driver.findElements(By.cssSelector(allItemsBy));

        //3 - Tìm item xem đúng cái đang cần không (dùng vòng lặp duyệt qua)
        for (WebElement tempItem : speedDropdownItems) {


            //4 - Kiểm tra cái text của item đúng với cái mình mong muốn
            if (tempItem.getText().trim().equals(expectedTextItem)){
                //5 - Click vào item đó
                tempItem.click();

                //Thoát ra khỏi vòng lặp không xét cho các case còn lại nữa
                break;
            }
        }
    }

    @AfterClass
    public void afterClass() {
        driver.quit();
    }
}