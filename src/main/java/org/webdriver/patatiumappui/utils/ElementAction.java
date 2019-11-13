package org.webdriver.patatiumappui.utils;

import com.google.common.io.Files;
import io.appium.java_client.MultiTouchAction;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.nativekey.KeyEvent;
import io.appium.java_client.functions.ExpectedCondition;
import io.appium.java_client.touch.LongPressOptions;
import io.appium.java_client.touch.TapOptions;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.ElementOption;
import io.appium.java_client.touch.offset.PointOption;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * app元素操作类方法
 * @author zhengshuheng 郑树恒
 *
 */
public class ElementAction extends TestBaseCase{
	private Log log=new Log(this.getClass());
	public static ArrayList<Exception> noSuchElementExceptions=new ArrayList<Exception>();
	/**
	 * 获取手机网络状态
	 * Possible values:
	 * 网络的bitmask掩码如下：
	 Value (Alias)      | Data | Wifi | Airplane Mode
	 -------------------------------------------------
	 0 (None)           | 0    | 0    | 0
	 1 (Airplane Mode)  | 0    | 0    | 1
	 2 (Wifi only)      | 0    | 1    | 0
	 4 (Data only)      | 1    | 0    | 0
	 6 (All network on) | 1    | 1    | 0
	 bitMask用二进制表示：0b001,0b010,0b100,0b110
	 driver.getConnection()返回当前手机的bitMask
	 */
	public boolean isAirplaneModeEnabled(){
		return driver.getConnection().isAirplaneModeEnabled();
	}
	public boolean isWiFiEnabled(){
		return driver.getConnection().isWiFiEnabled();
	}
	public boolean isDataEnabled(){
		return driver.getConnection().isDataEnabled();
	}
	/**
	 * 设置网络状态
	 *通过bitMask设置网络状态开关，如当前关闭则打开，如当前打开则关闭
	 */
	public void setNetworkStateMode(int bitMask)
	{
		switch (bitMask){
			case 0b001:
				driver.toggleAirplaneMode();
				break;
			case 0b010:
				driver.toggleWifi();
				break;
			case 0b100:
				driver.toggleData();
				break;
			case 0b110:
				driver.toggleWifi();
				driver.toggleData();
				System.out.println("double");
				break;
		}

	}
	/**
	*打印出当前app所有context上下文（LD添加）
	 * */
	public void printContexts() {
		Set<String> contextSet = driver.getContextHandles();
		for (String context : contextSet) {
			System.out.println(context);
		}
	}
	/**
	 * 多点触摸之放大屏幕（LD添加）
	 *
	 */
	public void zoomIn()
	{
		MultiTouchAction multiTouchAction=new MultiTouchAction(driver);
		TouchAction touchAction1=new TouchAction(driver);
		TouchAction touchAction2=new TouchAction(driver);
		//得到屏幕的宽度和高度
		int x=driver.manage().window().getSize().getWidth();
		int y=driver.manage().window().getSize().getHeight();

		touchAction1.press(PointOption.point(x*4/10,y*4/10)).
				waitAction(WaitOptions.waitOptions(Duration.ofSeconds(1))).
				moveTo(PointOption.point(x*2/10,y*2/10));
		touchAction2.press(PointOption.point(x*6/10,y*6/10)).
				waitAction(WaitOptions.waitOptions(Duration.ofSeconds(1))).
				moveTo(PointOption.point(x*8/10,y*8/10));
		multiTouchAction.add(touchAction1).add(touchAction2);
		multiTouchAction.perform();
	}
	/**
	 * 多点触摸之缩小屏幕（LD添加）
	 *
	 */
	public void zoomOut()
	{
		MultiTouchAction multiTouchAction=new MultiTouchAction(driver);
		TouchAction touchAction1=new TouchAction(driver);
		TouchAction touchAction2=new TouchAction(driver);
		//得到屏幕的宽度和高度
		int x=driver.manage().window().getSize().getWidth();
		int y=driver.manage().window().getSize().getHeight();

		touchAction1.press(PointOption.point(x*2/10,y*2/10)).
				waitAction(WaitOptions.waitOptions(Duration.ofSeconds(1))).
				moveTo(PointOption.point(x*4/10,y*4/10));
		touchAction2.press(PointOption.point(x*8/10,y*8/10)).
				waitAction(WaitOptions.waitOptions(Duration.ofSeconds(1))).
				moveTo(PointOption.point(x*6/10,y*6/10));
		multiTouchAction.add(touchAction1).add(touchAction2);
		multiTouchAction.perform();
	}

	/**
	 * 向手机push文件（LD添加）
	 *
	 */
	public void pushFileFuc(String filePath,String pushPath)
	{
		File file = new File(filePath);
		String content = null;
		byte[] byteArray=null;
		try {
			byteArray = FileUtils.readFileToByteArray(file);
		} catch (IOException e) {
		e.printStackTrace();
		}
		driver.pushFile(pushPath, Base64.encodeBase64(byteArray));
	}
	/**
	 * 从手机pull文件（LD添加）
	 *
	 */
	public void pullFileFuc(String filePath,String pullPath) throws IOException {
		File outfile = new File(pullPath);
		byte resultDate[] =driver.pullFile(filePath);
		OutputStream out = new FileOutputStream(outfile);
		out.write(resultDate);
		out.flush();
		out.close();
	}


	/**
	 * 安装app
	 */
	public  void installApp(String appPath)
	{
		driver.installApp(appPath);
	}

	/**
	 * 判断是否已安装app
	 * @param PackageName
	 */
	public  boolean  isInstallApp(String PackageName)
	{
		return  driver.isAppInstalled(PackageName);
	}
	/**
	 * 判断是否锁屏
	 */
	public  boolean  isLockedscreen()
	{
        return  driver.isDeviceLocked();
	}

	/**
	 * 打开通知栏界面
	 */
	public void openNotifications()
	{
		driver.openNotifications();
	}
	/**
	 * 按下安卓手机键.例如home,back等
	 * HOME键:3(AndroidKey.HOME)
	 * 菜单键menu:82(AndroidKey.MENU)
	 * 返回键back:4(AndroidKey.BACK)
	 * @param androidKeyCode 通过 AndroidKeyCode 枚举类获取
	 * 调用方式：action.pressAndroidKey(new KeyEvent(AndroidKey.HOME));
	 */
	public void pressAndroidKey(KeyEvent androidKeyCode)
	{
		driver.pressKey(androidKeyCode);
	}

	/**
	 * 长按下安卓手机键.例如home,back等
	 * @param androidKeyCode 通过 AndroidKeyCode 枚举类获取
	 * 调用方式：action.longPressAndroidKey(new KeyEvent(AndroidKey.HOME));
	 */
	public void longPressAndroidKey(KeyEvent androidKeyCode)
	{
		driver.longPressKey(androidKeyCode);
	}
	/**
	 *长按某个元素，后放开手指
	 * @param locator  元素locator
	 */
	public  void longPressAppElement(Locator locator,int durationSeconds)
	{
		TouchAction touchAction=new TouchAction(driver);
		touchAction.perform().longPress(LongPressOptions.longPressOptions().withElement(ElementOption.element(findElement(locator))).withDuration(Duration.ofSeconds(durationSeconds))).cancel();

	}

	/**
	 * 长按某个坐标位置，当定位到元素后
	 * @param locator 元素定位信息
	 * @param x 元素X坐标
	 * @param y 元素Y坐标
	 */
	public  void longPressAppElement(Locator locator,int x,int y,int durationSeconds)
	{
		TouchAction touchAction=new TouchAction(driver);
		touchAction.perform().longPress(LongPressOptions.longPressOptions().withElement(ElementOption.element(findElement(locator))).withPosition(PointOption.point(x,y)).withDuration(Duration.ofSeconds(durationSeconds))).cancel();
	}

	/**
	 * 长按手机界面某个坐标位置
	 * @param x
	 * @param y
	 */
	public  void longPressPosition(int x,int y,int durationSeconds)
	{
		TouchAction touchAction=new TouchAction(driver);
		touchAction.perform().longPress(LongPressOptions.longPressOptions().withPosition(PointOption.point(x,y)).withDuration(Duration.ofSeconds(durationSeconds))).cancel();
	}

	/**
	 * 按住手机界面某个位置
	 * @param x
	 * @param y
	 */
	public  void pressPosition(int x,int y)
	{
		TouchAction touchAction=new TouchAction(driver);
		touchAction.perform().press(PointOption.point(x,y));

	}



	/**
	 * 移动到某个元素上,appium7.3.0版本无法实现该功能
	 * @param locator
	 */
//	public void movetoElement(Locator locator)
//	{
//		TouchAction touchAction=new TouchAction(driver);
//		touchAction.moveTo(findElement(locator));
//	}

	/**
	 * 从x,y目标移动到元素，appium7.3.0版本无法实现该功能
	 * @param locator
	 * @param x
	 * @param y
	 */
//	public  void movetoElementPostion(Locator locator,int x,int y)
//	{
//		touchAction.moveTo(findElement(locator),x,y);
//	}

	/**
	 * 移动到某个位置
	 * @param x
	 * @param y
	 */
	public  void movetoPostion(int x,int y)
	{
		TouchAction touchAction=new TouchAction(driver);
		touchAction.perform().moveTo(PointOption.point(x,y));
	}

	/**
	 * 从一个地方滑动到另外一个地方，等待几秒松开
	 * @param x1
	 * @param y1
	 * @param x2
	 * @param y2
	 * @param durationSeconds 等待几秒松开
	 */
	public  void swipe(int x1,int y1,int x2,int y2,int durationSeconds)
	{
		TouchAction action1=new TouchAction(driver).press(PointOption.point(x1, y1)).waitAction(WaitOptions.waitOptions(Duration.ofSeconds(durationSeconds)))
				.moveTo(PointOption.point(x2, y2)).release();
		action1.perform();
	}

	/**
	 * 在控件中心点轻按下
	 * @param locator
	 */
	public  void tap(Locator locator)
	{
		TouchAction touchAction=new TouchAction(driver);
		touchAction.perform().tap(TapOptions.tapOptions().withElement(ElementOption.element(findElement(locator)))).cancel();
	}
	/**
	 * 在控件某个点轻按下
	 * @param locator
	 */
	public  void tap(Locator locator,int x,int y)
	{
		TouchAction touchAction=new TouchAction(driver);
		touchAction.perform().tap(TapOptions.tapOptions().withElement(ElementOption.element(findElement(locator))).withPosition(PointOption.point(x,y))).cancel();
	}
	/**
	 * 文本框输入操作
	 * @param locator  元素locator
	 * @param value 输入值
	 */
	public void type(Locator locator,String value)
	{
		try {
			WebElement webElement=findElement(locator);
			webElement.sendKeys(value);
			//log.info("input输入："+locator.getLocalorName()+"["+"By."+locator.getType()+":"+locator.getElement()+"  value:"+value+"]");
			log.info("input输入："+locator.getLocalorName()+"["+"By."+locator.getType()+":"+locator.getElement()+"  value:"+"*******"+"]");
		} catch (NoSuchElementException e) {
			// TODO: handle exception
			log.error("找不到元素，input输入失败:"+locator.getLocalorName()+"["+"By."+locator.getType()+":"+locator.getElement()+"]");
			e.printStackTrace();
			//throw e;
			//Assertion.flag=false;
		}

	}
	public void type_action(Locator locator,String value)
	{
		Actions actions =new Actions(driver);
		WebElement weElement=findElement(locator);
		actions.sendKeys(weElement, value);
	}
	/**
	 * 普通单击操作
	 * @param locator  元素locator
	 */
	public  void click(Locator locator)
	{
		try {
			WebElement webElement=findElement(locator);
			webElement.click();
			log.info("click元素："+locator.getLocalorName()+"["+"By."+locator.getType()+":"+locator.getElement()+"]成功！");
		} catch (NoSuchElementException e) {
			// TODO: handle exception
			log.error("找不到元素，click失败:"+locator.getLocalorName()+"["+"By."+locator.getType()+":"+locator.getElement()+"]");
			e.printStackTrace();
			throw e;
		}

	}
	/**
	 * 选择下拉框操作
	 * @param locator  元素locator
	 * @param text 选择下拉值
	 */
	public  void selectByText(Locator locator,String text)
	{
		try {
			WebElement webElement=findElement(locator);
			Select select=new Select(webElement);
			log.info("选择select标签："+locator.getLocalorName()+"["+"By."+locator.getType()+":"+locator.getElement()+"]");
			try {
				//select.selectByValue(value);
				select.selectByVisibleText(text);
				log.info("选择下拉列表项：" + text);

			} catch (NoSuchElementException notByValue) {
				// TODO: handle exception
				log.info("找不到下拉值，选择下拉列表项失败 " + text);
				throw notByValue;
			}
		} catch (NoSuchElementException e) {
			// TODO: handle exception
			log.error("找不到元素，选择select标签失败:"+locator.getLocalorName()+"["+"By."+locator.getType()+":"+locator.getElement()+"]");
			throw e;
		}
	}
	/**
	 * 选择下拉框操作
	 * @param locator  元素locator
	 * @param value 选择下拉value
	 */
	public  void selectByValue(Locator locator,String value)
	{
		Select select;
		try {
			WebElement webElement=findElement(locator);
			select=new Select(webElement);
			log.info("选择select标签:"+locator.getLocalorName()+"["+"By."+locator.getType()+":"+locator.getElement()+"]");
		} catch (NoSuchElementException e) {
			// TODO: handle exception
			log.error("找不到元素，选择select标签失败:"+locator.getLocalorName()+"["+"By."+locator.getType()+":"+locator.getElement()+"]");
			throw e;
		}
		try {
			select.selectByValue(value);
			log.info("选择下拉列表项：" + value);

		} catch (NoSuchElementException notByValue) {
			// TODO: handle exception
			log.info("找不到下拉值，选择下拉列表项失败 " + value);
			throw notByValue;
		}
	}
	/**
	 * 通过下拉列表的index选择元素
	 * @param locator
	 * @param index
	 */
	public void selectByIndex(Locator locator, int index) {
		// TODO 自动生成的方法存根
		Select select;
		try {
			WebElement webElement=findElement(locator);
			select=new Select(webElement);
			log.info("选择select标签:"+locator.getLocalorName()+"["+"By."+locator.getType()+":"+locator.getElement()+"]");
		} catch (NoSuchElementException e) {
			// TODO: handle exception
			log.error("找不到元素，选择select标签失败"+locator.getLocalorName()+"["+"By."+locator.getType()+":"+locator.getElement()+"]");
			throw e;
		}
		try {
			//select.selectByValue(value);
			select.selectByIndex(index);
			log.info("选择下拉列表项：" + index);

		} catch (NoSuchElementException notByindex) {
			// TODO: handle exception
			log.info("找不到下拉值，选择下拉列表项失败 " + index);
			throw notByindex;
		}
	}
	/**
	 * 获取下拉列表的value属性值
	 * @param selectLocator 下拉列表 select标签定位信息
	 * @return 返回String
	 */
	public String  getSelectOptionValue(Locator selectLocator,String optinText)
	{
		WebElement webElement=driver.findElement(By.xpath(selectLocator.getElement()+"//option[text()='"
				+ optinText
				+ "']"));
		return webElement.getAttribute("value");
	}
	public String getSelectOptionText(Locator selectLocator,String optinValue)
	{
		WebElement webElement=driver.findElement(By.xpath(selectLocator.getElement()+"//option[text()='"
				+ optinValue
				+ "']"));
		return webElement.getText();
	}
	/**
	 * 点击确认按钮
	 */
	public void alertConfirm()
	{
		Alert alert=driver.switchTo().alert();
		try {
			alert.accept();
			log.info("点击确认按钮");
		} catch (NoAlertPresentException notFindAlert) {
			// TODO: handle exception
			//throw notFindAlert;
			log.error("找不到确认按钮");
			throw notFindAlert;
		}
	}
	/**
	 * 点击取消按钮
	 */
	public  void alertDismiss()
	{
		Alert alert= driver.switchTo().alert();
		try {
			alert.dismiss();
			log.info("点击取消按钮");
		} catch (NoAlertPresentException notFindAlert) {
			// TODO: handle exception
			//throw notFindAlert;
			log.error("找不到取消按钮");
			throw notFindAlert;
		}
	}
	/**
	 * 获取对话框文本
	 * @return 返回String
	 */
	public String getAlertText()
	{
		Alert alert=driver.switchTo().alert();
		try {
			String text=alert.getText().toString();
			log.info("获取对话框文本："+text);
			return text;
		} catch (NoAlertPresentException notFindAlert) {
			// TODO: handle exception
			log.error("找不到对话框");
			//return "找不到对话框";
			throw notFindAlert;

		}
	}
	/**
	 * 双击操作
	 * @param locator  元素locator
	 */
	public void click_double(Locator locator)
	{
		WebElement webElement=findElement(locator);
		Actions actions=new Actions(driver);
		actions.doubleClick(webElement).perform();
		//actions.perform();

	}
	/**
	 * 清除文本框内容
	 * @param locator  元素locator
	 */
	public void clear(Locator locator){
		try {
			WebElement webElement=findElement(locator);
			webElement.clear();
			log.info("清除input值:"+locator.getLocalorName()+"["+"By."+locator.getType()+":"+locator.getElement()+"]");
		} catch (NoSuchElementException e) {
			// TODO: handle exception
			log.error("清除input值失败:"+locator.getLocalorName()+"["+"By."+locator.getType()+":"+locator.getElement()+"]");

		}

	}
	/**
	 * 切换frame/iframe框架
	 * @param locator  元素locator
	 */
	public void switchToFrame(Locator locator)
	{
		WebElement frameElement=findElement(locator);
		driver.switchTo().frame(frameElement);
	}
	/**
	 * 切回默认窗口框架
	 */
	public void switchToDefaultFrame()
	{
		driver.switchTo().defaultContent();
	}
	/**
	 * 多窗口切换
	 * @param i 第几个窗口
	 */
	public void switchToWindow(int i)
	{
		String[] handls=new String[driver.getWindowHandles().size()];
		driver.getWindowHandles().toArray(handls);
		driver.switchTo().window(handls[i]);
	}
	/**
	 * 隐式等待
	 * @param  t  最大等待时间，秒为单位
	 **/
	public void Waitformax(int t)
	{
		driver.manage().timeouts().implicitlyWait(t,TimeUnit.SECONDS);
	}
	/**
	 * 获取元素文本
	 * @param locator  元素locator
	 */
	public String getText(Locator locator)
	{
		WebElement webElement=findElement(locator);
		String text=webElement.getText();
		return text;

	}
	/**
	 * 获取元素某属性的值
	 * @param locator  元素locator
	 * @param attributeName
	 * @return 返回String
	 */
	public String getAttribute(Locator locator,String attributeName)
	{
		WebElement webElement=findElement(locator);
		String value= webElement.getAttribute(attributeName);
		return value;
	}
	/**
	 * 获取当前url
	 * @return
	 */
	public String getUrl()
	{
		String url=driver.getCurrentUrl();
		return url;
	}
	/**
	 * 获取当前网页标题
	 * @return 返回String
	 */
	public String getTitle()
	{
		String title=driver.getTitle();
		return title;
	}
	/**
	 * 截屏方法
	 * @param FileDriver  文件保存路径
	 * @param Filename  文件名
	 * @throws Exception  抛出Exception异常
	 */
	public void Snapshot(String FileDriver,String Filename) throws Exception
	{
		File scrFile=((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		try {
			Files.copy(scrFile, new File(FileDriver+Filename));
			System.out.println("错误截图："+FileDriver+Filename);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			throw e;
		}
	}
	/**
	 * 显式等待，程序休眠暂停
	 * @param time 以秒为单位
	 */
	public void sleep(long time)
	{
		try {
			log.info("等待"+time+"秒");
			Thread.sleep(time*1000);
		} catch (InterruptedException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
	}
	/**
	 * 显式等待 判断页面是否完全加载完成
//	 * @param time 已秒为单位
	 */
	public void pagefoload(long time)
	{
		ExpectedCondition<Boolean> pageLoad= new
				ExpectedCondition<Boolean>() {
					public Boolean apply(WebDriver driver) {
						return ((JavascriptExecutor)driver).executeScript("return document.readyState").equals("complete");
					}
				};
		WebDriverWait wait = new WebDriverWait(driver, time*1000);
		wait.until(pageLoad);
	}
	public void executeJS(String js) {
		((JavascriptExecutor) driver).executeScript(js);
		System.out.println("执行JS脚本："+js);

	}
	/**
	 * 判断医组元素是否存在
	 * @param locator 一组元素定位信息
	 * @param timeOut 超时时间 秒
	 * @return 返回boolean true存在，false不存在
	 * @throws InterruptedException
	 */
	public  boolean isElementsPresent(Locator locator, int timeOut) throws InterruptedException
	{
		log.info("等待"+timeOut+"秒判断元素："+locator.getElement()+"是否存在");
		boolean isPresent = false;
		Thread.sleep(timeOut * 1000);
		List<WebElement> we =findElements(locator);
		if (we.size() != 0) {
			isPresent = true;
		}
		log.info("判断结果为："+isPresent);
		return isPresent;
	}
	/**
	 * 执行cmd命令
	 */
	public  void executeCmd(String cmd) throws IOException {
		Runtime runtime=Runtime.getRuntime();
		runtime.exec("cmd /c start "+cmd);
	}
	/**
	 * 判断元素是否显示
	 * @param locator 元素定位信息
	 * @return  返回boolean true显示，false隐藏
	 */
	public boolean isElementDisplayed(Locator locator)
	{
		ElementAction action =new ElementAction();
		WebElement webElement=action.findElement(locator);
		webElement.isEnabled();
		log.info("元素显示状态为："+ webElement.isDisplayed());
		return webElement.isDisplayed();
	}
	/**
	 * 等待30秒让元素可见
	 * @param locator
	 */
	public void DisplayElement(Locator locator)
	{
		ElementAction action =new ElementAction();
		WebDriverWait webDriverWait=new WebDriverWait(driver, 30);
		webDriverWait.until(ExpectedConditions.visibilityOf(action.findElement(locator))).isDisplayed();
	}
	/**
	 * 查找一组元素
	 * @param locator 元素定位信息
	 * @return
	 */
	public List<WebElement>  findElements(final Locator locator)
	{

		/**
		 * 查找某个元素等待几秒
		 */
		//Waitformax(Integer.valueOf(locator.getWaitSec()));
		List<WebElement>  webElements=null;
		try {
			webElements=(new WebDriverWait(driver, 20)).until(
					new ExpectedCondition<List<WebElement>>() {

						@Override
						public List<WebElement> apply(WebDriver driver) {
							// TODO 自动生成的方法存根
							List<WebElement> element=null;
							element=getElements(locator);
							return element;}
					});
			return webElements;
		} catch (NoSuchElementException e) {
			// TODO: handle exception
			log.info("无法定位页面元素");
			e.printStackTrace();
			Assertion.assertInfolList.add("failed,找不到元素：["+locator.getType()+":"+locator.getElement()+"等待:"+locator.getWaitSec());
			noSuchElementExceptions.add(e);
			Assertion.messageList.add("找不到所需页面元素["+locator.getElement()+"]:failed");
			ScreenShot screenShot=new ScreenShot(driver);
			//设置截图名字
			Date nowDate=new Date();
			screenShot.setscreenName(this.formatDate(nowDate));
			screenShot.takeScreenshot();
			//报表展示截图
			this.showscreenShot(nowDate);
			log.info(this.formatDate(nowDate));
			return webElements;
		}
		catch (TimeoutException e) {
			// TODO: handle exception
			log.info("查找页面元素超时");
			e.printStackTrace();
			Assertion.assertInfolList.add("failed,超时找不到元素：["+locator.getType()+":"+locator.getElement()+"等待:"+locator.getWaitSec());
			noSuchElementExceptions.add(e);
			Assertion.messageList.add("超时找不到所需页面元素["+locator.getElement()+"]:failed");
			ScreenShot screenShot=new ScreenShot(driver);
			//设置截图名字
			Date nowDate=new Date();
			screenShot.setscreenName(this.formatDate(nowDate));
			screenShot.takeScreenshot();
			//报表展示截图
			this.showscreenShot(nowDate);
			log.info(this.formatDate(nowDate));
			//Assertion.assertInfolList.add(arg0)
			return webElements;
		}
		catch (ElementNotVisibleException e) {
			// TODO: handle exception
			log.info("查找页面元素超时");
			e.printStackTrace();
			Assertion.assertInfolList.add("failed,页面元素不可视：["+locator.getType()+":"+locator.getElement()+"等待:"+locator.getWaitSec());
			noSuchElementExceptions.add(e);
			Assertion.messageList.add("超时页面元素不可视["+locator.getElement()+"]:failed");
			ScreenShot screenShot=new ScreenShot(driver);
			//设置截图名字
			Date nowDate=new Date();
			screenShot.setscreenName(this.formatDate(nowDate));
			screenShot.takeScreenshot();
			//展示报表截图
			this.showscreenShot(nowDate);
			log.info(this.formatDate(nowDate));
			//Assertion.assertInfolList.add(arg0)
			return webElements;
		}

	}
	public WebElement findElement( final Locator locator)
	{
		/**
		 * 查找某个元素等待几秒
		 */
		//Waitformax(Integer.valueOf(locator.getWaitSec()));
		WebElement webElement=null;
		try {
			webElement=(new WebDriverWait(driver, 20)).until(
					new ExpectedCondition<WebElement>() {

						@Override
						public WebElement apply(WebDriver driver) {
							// TODO 自动生成的方法存根
							WebElement element=null;
							element=getElement(locator);
							return element;
						}
					});
			return webElement;
		} catch (NoSuchElementException e) {
			// TODO: handle exception
			log.info("无法定位页面元素");
			e.printStackTrace();
			Assertion.assertInfolList.add("failed,找不到元素：["+locator.getType()+":"+locator.getElement()+"等待:"+locator.getWaitSec());
			noSuchElementExceptions.add(e);
			Assertion.messageList.add("找不到所需页面元素["+locator.getElement()+"]:failed");
			ScreenShot screenShot=new ScreenShot(driver);
			//设置截图名字
			Date nowDate=new Date();
			screenShot.setscreenName(this.formatDate(nowDate));
			screenShot.takeScreenshot();
			//展示报表截图
			this.showscreenShot(nowDate);
			log.info(this.formatDate(nowDate));
			return webElement;
		}
		catch (TimeoutException e) {
			// TODO: handle exception
			log.info("超时无法定位页面元素");
			e.printStackTrace();
			Assertion.assertInfolList.add("failed,超时找不到元素：["+locator.getType()+":"+locator.getElement()+"等待:"+locator.getWaitSec());
			noSuchElementExceptions.add(e);
			Assertion.messageList.add("超时找不到所需页面元素["+locator.getElement()+"]:failed");
			ScreenShot screenShot=new ScreenShot(driver);
			//设置截图名字
			Date nowDate=new Date();
			screenShot.setscreenName(this.formatDate(nowDate));
			screenShot.takeScreenshot();
			//展示报表截图
			this.showscreenShot(nowDate);
			log.info(this.formatDate(nowDate));
			return webElement;
		}
		catch (ElementNotVisibleException e) {
			// TODO: handle exception
			log.info("超时无法定位页面元素");
			e.printStackTrace();
			Assertion.assertInfolList.add("failed,超时找不到元素：["+locator.getType()+":"+locator.getElement()+"等待:"+locator.getWaitSec());
			noSuchElementExceptions.add(e);
			Assertion.messageList.add("超时页面元素不可视["+locator.getElement()+"]:failed");
			ScreenShot screenShot=new ScreenShot(driver);
			//设置截图名字
			Date nowDate=new Date();
			screenShot.setscreenName(this.formatDate(nowDate));
			screenShot.takeScreenshot();
			//展示报表截图
			this.showscreenShot(nowDate);
			log.info(this.formatDate(nowDate));
			return webElement;
		}
	}

	/**
	 * 通过定位信息获取元素
	 * @param locator  元素locator
	 * @return 返回WebElement
	 * @throws NoSuchElementException 找不到元素异常
	 */
	public WebElement getElement(Locator locator)
	{

		/**
		 * locator.getElement(),获取对象库对象定位信息
		 */
		log.info("查找元素："+locator.getLocalorName()+"方式"+"["+"By."+locator.getType()+":"+locator.getElement()+"]");
		WebElement webElement;
		switch (locator.getType())
		{
			case xpath :
				webElement=driver.findElement(By.xpath(locator.getElement()));
				break;
			case id:
				webElement=driver.findElement(By.id(locator.getElement()));
				break;
			case cssSelector:
				webElement=driver.findElement(By.cssSelector(locator.getElement()));
				break;
			case name:
				webElement=driver.findElement(By.name(locator.getElement()));
				break;
			case className:
				webElement=driver.findElement(By.className(locator.getElement()));
				break;
			case linkText:
				webElement=driver.findElement(By.linkText(locator.getElement()));
				break;
			case partialLinkText:
				webElement=driver.findElement(By.partialLinkText(locator.getElement()));
				break;
			case tagName:
				webElement=driver.findElement(By.tagName(locator.getElement()));
				break;
			default :
				webElement=driver.findElement(By.xpath(locator.getElement()));
				break;

		}
		return webElement;
	}
	/**
	 * 通过定位信息获取一组元素
	 * @param locator  元素locator
	 * @return 返回WebElement
	 * @throws NoSuchElementException 找不到元素异常
	 */
	public List<WebElement> getElements(Locator locator)
	{
		/**
		 * locator.getElement(),获取对象库对象定位信息
		 */
		//locator=getLocator(locatorMap.get(key));
		log.info("查找一组元素："+locator.getLocalorName()+" 方式"+"["+"By."+locator.getType()+":"+locator.getElement()+"]");
		List<WebElement> webElements;
		switch (locator.getType())
		{
			case xpath :
				webElements=driver.findElements(By.xpath(locator.getElement()));
				/**
				 * 出现找不到元素的时候，记录日志文件
				 */
				break;
			case id:
				webElements=driver.findElements(By.id(locator.getElement()));
				break;
			case cssSelector:
				webElements=driver.findElements(By.cssSelector(locator.getElement()));
				break;
			case name:
				webElements=driver.findElements(By.name(locator.getElement()));
				break;
			case className:
				webElements=driver.findElements(By.className(locator.getElement()));
				break;
			case linkText:
				webElements=driver.findElements(By.linkText(locator.getElement()));
				break;
			case partialLinkText:
				webElements=driver.findElements(By.partialLinkText(locator.getElement()));
				break;
			case tagName:
				webElements=driver.findElements(By.partialLinkText(locator.getElement()));
				break;
			default :
				webElements=driver.findElements(By.xpath(locator.getElement()));
				break;

		}
		return webElements;
	}
	private String formatDate(Date date)
	{
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HHmmssSSS");
		return formatter.format(date).toString();
	}
	//报表展示截图
	private  void  showscreenShot(Date nowDate)
	{
		Assertion.messageList.add("&lt;a class=\"clickbox\" href=\"#url\"&gt;\n"
				+ "&lt;img src=\"snapshot/"
				+ this.formatDate(nowDate)
				+ ".jpg\" height=\"100\" width=\"100\" alt=\"\" /&gt;\n"
				+ "&lt;b class=\"lightbox\"&gt;\n"
				+ "&lt;b class=\"light\"&gt;&lt;/b&gt;\n"
				+ "&lt;b class=\"box\"&gt;\n"
				+ "&lt;img src=\"snapshot/"
				+ this.formatDate(nowDate)
				+ ".jpg\" height=\"530\" width=\"1024\" onmousewheel=\"return bigimg(this)\" alt=\"\" /&gt;\n"
				+ "&lt;span&gt;滚动鼠标缩放大小,点击X关闭当前图片，返回报表界面.&lt;br /&gt;&lt;i&gt;&lt;/i&gt;&lt;/span&gt;\n"
				+ "&lt;/b&gt;\n"
				+ "&lt;/b&gt;\n"
				+ "&lt;/a&gt;\n"
				+ "&lt;br class=\"clear\" /&gt;\n"
				+"&lt;a class=\"clickbox\" href=\"#url\"&gt;"
				+ "点击查看大图"
				+ "&lt;b class=\"lightbox\"&gt;"
				+ "&lt;b class=\"light\"&gt;&lt;/b&gt;"
				+ "&lt;b class=\"box\"&gt;&lt;img src=\"snapshot/"
				+ this.formatDate(nowDate)
				+ ".jpg\" height=\"530\" width=\"1024\" onmousewheel=\"return bigimg(this)\" alt=\"\" /&gt;"
				+ "&lt;span&gt;滚动鼠标缩放大小,点击X关闭当前图片，返回报表界面."
				+ "&lt;br /&gt;&lt;i&gt;&lt;/i&gt;&lt;/span&gt;"
				+ "&lt;/b&gt;"
				+ "&lt;/b&gt;"
				+ " &lt;/a&gt;\n&lt;/br&gt;"
				+ "&lt;div id=\"close\"&gt;&lt;/div&gt;\n");
	}


}
