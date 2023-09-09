package webdriver;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Topic_08_Handle_Default_Dropdown {
    WebDriver driver;
    Random ran;
    String projectPath = System.getProperty("user.dir");
    String osName = System.getProperty("os.name");

    String firstName, lastName, dateOfBirthDay, monthOfBirthDay, yearofBirthday, email, companyName, password, countryAddress;
    String address1, address2, postalCode, phoneNumber, faxNumber;
    @BeforeClass
    public void beforeClass() {
        if (osName.contains("Windows")) {
            System.setProperty("webdriver.gecko.driver", projectPath + "\\browserDrivers\\geckodriver.exe");
        } else {
            System.setProperty("webdriver.gecko.driver", projectPath + "/browserDrivers/geckodriver");
        }
        ran = new Random();
        driver = new FirefoxDriver();
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        driver.manage().window().maximize();

        firstName = "Nguyen";
        lastName = "Trang";
        dateOfBirthDay = "22";
        monthOfBirthDay = "July";
        yearofBirthday = "2020";
        email = "nguyentrang" + getRandomNumber() + "@gmail.com";
        companyName = "Công ty TNHH Hương Hương";
        password = "12341234";
        countryAddress = "France";
        address1 = "806 Kim Giang";
        address2 = "816 Kim Giang";
        postalCode = "54565";
        phoneNumber = "0987654321";
        faxNumber = "0123456789";
    }

    @Test
    public void TC_01_Register() {
        driver.get("https://demo.nopcommerce.com/");
        driver.findElement(By.xpath("//a[text()='Register']")).click();

        driver.findElement(By.id("gender-female")).click();
        driver.findElement(By.id("FirstName")).sendKeys(firstName);
        driver.findElement(By.id("LastName")).sendKeys(lastName);
        new Select(driver.findElement(By.name("DateOfBirthDay"))).selectByVisibleText(dateOfBirthDay);
        new Select(driver.findElement(By.name("DateOfBirthMonth"))).selectByVisibleText(monthOfBirthDay);
        new Select(driver.findElement(By.name("DateOfBirthYear"))).selectByVisibleText(yearofBirthday);
        driver.findElement(By.id("Email")).sendKeys(email);
        driver.findElement(By.id("Company")).sendKeys(companyName);
        driver.findElement(By.id("Password")).sendKeys(password);
        driver.findElement(By.id("ConfirmPassword")).sendKeys(password);
        driver.findElement(By.id("register-button")).click();


        Assert.assertEquals(driver.findElement(By.cssSelector("div.result")).getText(),"Your registration completed");
        sleepInSecond(3);

        driver.findElement(By.xpath("//a[text()='Log in']")).click();
        driver.findElement(By.id("Email")).sendKeys(email);
        driver.findElement(By.id("Password")).sendKeys(password);
        driver.findElement(By.cssSelector("button.login-button")).click();
        sleepInSecond(3);
        driver.findElement(By.xpath("//a[text()='My account']")).click();
        sleepInSecond(3);

        Assert.assertTrue(driver.findElement(By.id("gender-female")).isSelected());
        Assert.assertEquals(driver.findElement(By.id("FirstName")).getAttribute("value"),firstName);
        Assert.assertEquals(driver.findElement(By.id("LastName")).getAttribute("value"),lastName);
        Assert.assertEquals(new Select(driver.findElement(By.name("DateOfBirthDay"))).getFirstSelectedOption().getText(),dateOfBirthDay);
        Assert.assertEquals(new Select(driver.findElement(By.name("DateOfBirthMonth"))).getFirstSelectedOption().getText(),monthOfBirthDay);
        Assert.assertEquals(new Select(driver.findElement(By.name("DateOfBirthYear"))).getFirstSelectedOption().getText(),yearofBirthday);
        Assert.assertEquals(driver.findElement(By.id("Email")).getAttribute("value"),email);
        Assert.assertEquals(driver.findElement(By.id("Company")).getAttribute("value"),companyName);

    }

    @Test
    public void TC_02_Add_New_Address() {
        driver.findElement(By.cssSelector("li.customer-addresses>a")).click();
        driver.findElement(By.cssSelector("button.add-address-button")).click();

        driver.findElement(By.id("Address_FirstName")).sendKeys(firstName);
        driver.findElement(By.id("Address_LastName")).sendKeys(lastName);
        driver.findElement(By.id("Address_Email")).sendKeys(email);
        driver.findElement(By.id("Address_Company")).sendKeys(companyName);
        new Select(driver.findElement(By.id("Address_CountryId"))).selectByVisibleText(countryAddress);
        driver.findElement(By.id("Address_City")).sendKeys(countryAddress + countryAddress);
        driver.findElement(By.id("Address_Address1")).sendKeys(address1);
        driver.findElement(By.id("Address_Address2")).sendKeys(address2);
        driver.findElement(By.id("Address_ZipPostalCode")).sendKeys(postalCode);
        driver.findElement(By.id("Address_PhoneNumber")).sendKeys(phoneNumber);
        driver.findElement(By.id("Address_FaxNumber")).sendKeys(faxNumber);

        driver.findElement(By.cssSelector("button.save-address-button")).click();
        sleepInSecond(3);

        Assert.assertEquals(driver.findElement(By.cssSelector("li.name")).getText(),firstName + " " + lastName);
        Assert.assertTrue(driver.findElement(By.cssSelector("li.email")).getText().contains(email));
        Assert.assertTrue(driver.findElement(By.cssSelector("li.phone")).getText().contains(phoneNumber));
        Assert.assertTrue(driver.findElement(By.cssSelector("li.fax")).getText().contains(faxNumber));
        Assert.assertEquals(driver.findElement(By.cssSelector("li.company")).getText(),companyName);
        Assert.assertEquals(driver.findElement(By.cssSelector("li.address1")).getText(),address1);
        Assert.assertEquals(driver.findElement(By.cssSelector("li.address2")).getText(),address2);
        Assert.assertEquals(driver.findElement(By.cssSelector("li.city-state-zip")).getText(),countryAddress + countryAddress + ", " + postalCode);
        Assert.assertEquals(driver.findElement(By.cssSelector("li.country")).getText(),countryAddress);


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