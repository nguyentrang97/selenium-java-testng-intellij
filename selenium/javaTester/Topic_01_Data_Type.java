package javaTester;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ISelect;
import org.openqa.selenium.support.ui.Select;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Vector;

public class Topic_01_Data_Type {
    //Kiểu dữ liệu trong Java - 2 nhóm

    //I - Kiểu dữ liệu nguyên thủy (Primitive Type)
    //Số nguyên: byte - short - int - long
    // Không có phần thập phân: Số nhân viên trong công ty/Số học sinh trong lớp...
    byte bNumber = 40;
    shot sNumber = 3000;
    int iNumber = 1565555555;
    long lNumber = 234343400;

    //Số thực: float - double
    //Có phần thập phân: điểm số, hệ số...
    float fNumber = 9.99f;
    double dNumber = 36.555541d;

    //Ký tự: char
    //Đại diện cho 1 ký tự
    char c = 'M';
    char d = 't';

    //Logic: boolean
    //Chỉ có 2 giá trị true/false, không có ngoại lệ: giới tính...
    boolean status = true;


    //II - Kiểu dữ liệu tham chiếu (Reference Type)
    //Class
    FirefoxDriver firefoxDriver = new FirefoxDriver();
    Select select = new Select(firefoxDriver.findElement(By.className("")));
    Topic_01_Data_Type topic01 = new Topic_01_Data_Type();

    //Interface
    WebDriver driver;
    JavascriptExecutor jsExcecutor;

    //Object
    Object name = "Automation FC";

    //Array (kiểu nguyên thủy/tham chiếu
    int[] studenAge = {15, 20, 8};
    String[] studentName = {"Automation", "Manual", "Testing"};

    //Collection: List/Queue/Set
    List<String> studentAddress = new ArrayList<String>();
    List<String> studentCity = new LinkedList<>();
    List<String> studentPhone = new Vector<>();

    //String - Chuỗi ký tự
    String fullname = "Trang Nguyễn";


    //Khai báo 1 biến để lưu trữ 1 loại dữ liệu nào đó
    //Access Modifier: Phạm vi truy cập (public/private/protected/default)
    //Kiểu dữ liệu
    //Tên biến
    //Giá trị của biến - gán vào với phép =
    //Nếu như không gán: dữ liệu mặc định

    public int studentNumber = 50;
}
