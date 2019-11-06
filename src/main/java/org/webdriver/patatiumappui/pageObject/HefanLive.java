package org.webdriver.patatiumappui.pageObject;

import org.webdriver.patatiumappui.utils.BaseAction;
import org.webdriver.patatiumappui.utils.Locator;

import java.io.IOException;

/**
 * Created by ld_ab on 2019/10/4.
 */
public class HefanLive extends BaseAction {
    //用于eclipse工程内运行查找对象库文件路径
    private String path = "src/main/java/org/webdriver/patatiumappui/pageObjectConfig/UILibrary.xml";

    public HefanLive() {
    //工程内读取对象库文件
        setXmlObjectPath(path);
        getLocatorMap();
    }

    public Locator GetElement(String ElementName){
        Locator locator;
        System.out.println("开始定位元素");
        locator = getLocator(ElementName);
        //e.printStackTrace();
        return locator;
    }

    public Locator 打开盒饭live() throws IOException {
        Locator locator = getLocator("打开盒饭live");
        return locator;
    }

    public Locator 登录框关闭按钮() throws IOException {
        Locator locator = getLocator("登录框关闭按钮");
        return locator;
    }

    public Locator 首页搜索框() throws IOException {
        Locator locator = getLocator("首页搜索框");
        return locator;
    }

    public Locator 搜索页搜索框() throws IOException {
        Locator locator = getLocator("搜索页搜索框");
        return locator;
    }
    public Locator 搜索按钮() throws IOException {
        Locator locator = getLocator("搜索按钮");
        return locator;
    }
}