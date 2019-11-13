import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.webdriver.patatiumappui.pageObject.HefanLive;
import org.webdriver.patatiumappui.utils.Assertion;
import org.webdriver.patatiumappui.utils.ElementAction;
import org.webdriver.patatiumappui.utils.TestBaseCase;

import java.io.IOException;
import java.util.Set;

/**
 * Created by ld_ab on 2019/10/6.
 */
public class HF_HomePage extends TestBaseCase {
    ElementAction action=new ElementAction();
    HefanLive HF=new HefanLive();
    @BeforeClass
    public  void  beforeclass() throws IOException {
        System.out.println("beforeclass2````````````````````````````````````````````````````````````````````");
    }
    @Test(description = "盒饭live引导页测试",priority = 0)
    public  void HF_base() throws IOException {
        action.sleep(2);
//        action.swipe(1000,1000,150,1000,500);
//        action.sleep(1);
//        action.swipe(1000,1000,150,1000,500);
//        action.sleep(1);
//        action.swipe(1000,1000,150,1000,500);
//        action.sleep(1);
//        action.swipe(1000,1000,150,1000,500);
        action.sleep(1);
        action.click(HF.打开盒饭live());
        action.sleep(3);
        //设置检查点
        Assertion.VerityTextPresent("其他登录方式","验证是否通过引导页测试！");
        //设置断言 。判断用例是否失败
        Assertion.VerityError();
        action.click(HF.登录框关闭按钮());
        action.sleep(1);
//        action.swipe(400,400,400,400,500);
    }
    private void runFuc(String btnName){
        System.out.println("开始执行"+btnName);
        while(action.getAttribute(HF.GetElement(btnName),"class").indexOf("complete")==-1){
                action.sleep(2);
                action.click(HF.GetElement(btnName));
                action.sleep(4);
                //lidong action.pressAndroidKey(4);
                action.sleep(4);
            try {
                action.click(HF.GetElement("朕知道了"));
            }catch (Exception e){
                System.out.println("朕知道了按钮定位失败！"+e.getCause());
                action.sleep(5);
                action.click(HF.GetElement("朕知道了"));
            }
        }
        System.out.println("退出执行"+btnName);
    }
    @Test(description = "盒饭live搜索测试",priority = 1)
    public  void HF_search() throws IOException {
        //action.sleep(10);
        action.click(HF.GetElement("首页搜索框"));
        action.sleep(2);
        action.type(HF.GetElement("搜索页搜索框"),"范冰冰");
        action.sleep(2);
        action.click(HF.搜索按钮());
        action.sleep(2);
        //设置检查点
        Assertion.VerityTextPresent("范冰冰","验证是否通过搜索测试！");
        //设置断言 。判断用例是否失败
        Assertion.VerityError();
        action.click(HF.GetElement("搜索页返回按钮"));

    }
    @Test(description = "京东H5活动测试",priority = 1)
    public  void JD_H5() throws IOException {
//        action.click(HF.GetElement("浮层icon"));
//        action.sleep(3);
        Set<String> contextSet = driver.getContextHandles();
        for (String context:contextSet){
            System.out.println(context);
        }
        driver.context("WEBVIEW_com.jingdong.app.mall");
        System.out.println("需切换到WEBVIEW_com.jingdong.app.mall,"+"切换后ConText为："+driver.getContext());
        action.click(HF.GetElement("京东领金币"));
        action.sleep(2);
        runFuc("逛逛好店");
        runFuc("精彩会场");
        runFuc("精选好物");
        runFuc("更多好玩互动");
        runFuc("看直播");
        driver.context("NATIVE_APP");
    }

    @Test(description = "盒饭live后援会引导页测试",priority = 2)
    public  void HF_HYH() throws IOException {
        action.sleep(2);
        action.click(HF.GetElement("下导航后援会"));
        action.sleep(2);
        action.click(HF.GetElement("后援会引导页"));
        //设置检查点
        Assertion.VerityText(HF.GetElement("后援会列表页标题"),"后援会","验证是否通过后援会引导页测试");
        //设置断言 。判断用例是否失败
        Assertion.VerityError();
        action.click(HF.GetElement("后援会列表页返回"));
    }
    @AfterSuite
    public  void openResult()
    {
        WebDriver driver =new FirefoxDriver();
        //driver.get("http://127.0.0.1");
        String ResURL="D:\\JavaCode\\PatatiumAppUi\\test-output\\report.html";
        //System.out.println(ResURL);
        driver.get(ResURL);
    }
}
