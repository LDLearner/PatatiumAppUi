package org.webdriver.patatiumappui.ld;

import org.testng.IAnnotationTransformer;
import org.testng.IRetryAnalyzer;
import org.testng.annotations.ITestAnnotation;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

/**
 * Created by ld_ab on 2019/10/30.
 * 失败用例重跑监听器
 */
public class RetryListener implements IAnnotationTransformer {
    public void transform(ITestAnnotation annotation, Class testClass, Constructor testConstructor, Method testMethod) {
        IRetryAnalyzer retry = annotation.getRetryAnalyzer();
        if (retry == null) {
            annotation.setRetryAnalyzer(TestNGRetry.class);

        }
    }

//    public synchronized void onTestFailure(ITestResult arg0) {
//
//        if (arg0.getMethod().getRetryAnalyzer() != null) {
//            TestNGRetry testRetryAnalyzer = (TestNGRetry) arg0.getMethod().getRetryAnalyzer();
//            if (testRetryAnalyzer.getCount() <= testRetryAnalyzer.getMaxCount()) {
//                arg0.setStatus(ITestResult.SKIP);
//                Reporter.setCurrentTestResult(null);
//            } else
//                failedCases.addResult(arg0, arg0.getMethod());
//            isRetryHandleNeeded = true;
//        }
//    }
}
