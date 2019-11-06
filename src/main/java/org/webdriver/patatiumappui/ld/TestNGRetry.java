package org.webdriver.patatiumappui.ld;

/**
 * Created by ld_ab on 2019/10/30.
 * 用例失败自动重跑逻辑
 */

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;
import org.webdriver.patatiumappui.utils.Log;

public class TestNGRetry implements IRetryAnalyzer {
    private static final String TEST_RETRY_COUNT = "testRetryCount";
    private Log log=new Log(this.getClass());
    private int retryCount=1;
    private int maxRetryCount=0;
    public void TestRetryAnalyzer() {
        String retryMaxCount = System.getProperty(TEST_RETRY_COUNT);
        if (retryMaxCount != null) {
            maxRetryCount = Integer.parseInt(retryMaxCount);
            System.out.println("retryMaxCount="+maxRetryCount);
        }
    }

    public int getCount() {
        return this.retryCount;
    }
    public int getMaxCount() {
        return this.maxRetryCount;
    }
    public boolean retry(ITestResult result) {
        if (retryCount <= maxRetryCount) {
            result.setStatus(2);
            result.setAttribute("RETRY", new Integer(retryCount));
            String message = "running retry for  '" + result.getName() + "' on class " +
                    this.getClass().getName() + " Retrying " + retryCount + " times";
            log.info(message);
            retryCount++;
            return true;
        }
//        System.out.println("修改前："+result.getStatus());
//        System.out.println("ITestResult.FAILURE:"+ITestResult.FAILURE);
        System.out.println("`````````````````````````````The res1="+result.getStatus());
        result.setStatus(ITestResult.FAILURE);
        System.out.println("`````````````````````````````The res2="+result.getStatus());
//        System.out.println("result状态前："+result.getStatus());
//        ITestListener it=null;
//        it.onTestFailure(result);
//        System.out.println("result状态后："+result.getStatus());
        return false;
    }

}
