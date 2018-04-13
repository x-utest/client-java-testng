package apps;


import apps.TestNGListener;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import org.testng.SkipException;

/**
 *这个类是一个测试demo类，利用testng测试框架，写了几个自动化测试用例，可以作为以后开发自动化用例的参考
 */

@Listeners({TestNGListener.class})
public class testngTestDemo {


    @Test(priority = 0)
    public void Testfun1(){
        System.out.println("Testfun1……");
    }


    @Test(priority = 1)
    public void Testfun2(){
        System.out.println("Testfun2……");
        Assert.assertEquals(1, 2);
    }


    @Test(priority = 2)
    public void Testfun3(){
        System.out.println("Testfun3……");
        throw new SkipException("skip the test");
    }


}


