/**
 * Created by ld_ab on 2019/10/30.
 */

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.webdriver.patatiumappui.pageObject.HefanLive;
import org.webdriver.patatiumappui.utils.Assertion;
import org.webdriver.patatiumappui.utils.ElementAction;
import org.webdriver.patatiumappui.utils.TestBaseCase;

import java.io.IOException;

/**
 * Created by ld_ab on 2019/10/6.
 */
public class TestCase extends TestBaseCase {
    ElementAction action=new ElementAction();
    HefanLive HF=new HefanLive();
    @BeforeClass
    public  void  beforeclass() throws IOException {
        System.out.println("beforeclass````````````````````````````````````````````````````````````````````");
    }
    @Test(description = "临时测试用例")
    public  void TestCase1() throws IOException {
        String elementXpath="//android.widget.LinearLayout[@resource-id='com.starunion.hefantv:id/ll_search_top']/android.widget.RelativeLayout[1]";
        //隐性等待
        //driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
        action.sleep(2);
        //无等待
        try {
            WebElement wb = driver.findElement(By.xpath(elementXpath));
            wb.click();
        }catch (TimeoutException e){
            Assertion.messageList.add("超时找不到所需页面元素["+elementXpath+"]:failed");
        }
//        WebDriverWait explicitwait = new WebDriverWait(driver, 10);
//        explicitwait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.LinearLayout[@resource-id='com.starunion.hefantv:id/ll_search_top']/android.widget.RelativeLayout[1]"))).click();
//        explicitwait.until();
        //explicitwait.click();

        //显性等待
//        WebDriverWait wait = new WebDriverWait(driver, 10);
//        WebElement we= wait.until(new  ExpectedCondition<WebElement>() {
//            @Override
//            public WebElement apply(WebDriver d) {
//                return d.findElement(By.xpath(elementXpath));
//            }
//        });
//        we.click();

        //action.click(HF.GetElement("首页搜索框"));
        //action.click(HF.首页搜索框());

//        //设置检查点
        System.out.println("检查点开始运行");
        Assertion.VerityTextPresent("热门搜索","验证是否通过临时测试用例");
//        //设置断言 。判断用例是否失败
        Assertion.VerityError();
        System.out.println("检查点运行完毕");
    }

//    @AfterSuite
//    public  void openResult()
//    {
//        WebDriver driver =new FirefoxDriver();
//        //driver.get("http://127.0.0.1");
//        String ResURL="D:\\JavaCode\\PatatiumAppUi\\test-output\\report.html";
//        //System.out.println(ResURL);
//        driver.get(ResURL);
//    }
@Test(description = "临时用例")
public  void TestCaseTmp() throws IOException {
    //action.pushFileFuc("E:\\ld.jpg","/sdcard/download/ld.jpg");
    action.pullFileFuc("/sdcard/download/ld.jpg","E:\\ldpull.jpg");
}
}

