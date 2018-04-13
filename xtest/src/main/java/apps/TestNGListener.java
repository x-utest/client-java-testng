package apps;

import apps.txtFun;
import apps.utils;
import apps.xtest_conf;
import com.alibaba.fastjson.JSONObject;
import com.github.kevinsawicki.http.HttpRequest;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.zf.zson.ZSON;
import com.zf.zson.result.ZsonResult;
import org.testng.ITestResult;
import org.testng.ITestContext;
import org.testng.TestListenerAdapter;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.io.File;



/**
 * 监听测试过程：实现了监听测试过程并记录日志，以及如果测试失败，则保存截图。
 *
 */

public class TestNGListener extends TestListenerAdapter{

    //定义测试开始时间和结束时间
    public long start_time;
    public long end_time;


    //实例化配置文件类
    xtest_conf conf = new xtest_conf();
    String project_id = conf.project_id;
    //String base_url = conf.base_url;

    //新建一个日期类对象，获取当前的时间戳
    Date nowTime=new Date();
    SimpleDateFormat time=new SimpleDateFormat("yyyy:MM:dd HH:mm:ss");
    String curTime=time.format(nowTime);

    //获取当前时间函数
    public String getFormatCurrentTime(){
        //新建一个日期类对象，获取当前的时间戳
        Date nowTime=new Date();
        SimpleDateFormat time=new SimpleDateFormat("yyyy:MM:dd HH:mm:ss");
        String curTime=time.format(nowTime);
        return curTime;
    }


    //获取当前时间函数
    public Date getNowTime() {
        return nowTime;
    }

    //总测试开始执行
    public void onStart(ITestContext testContext){
        //打印开始时间
        System.out.println("测试套件执行已经开始："+curTime);
        super.onStart(testContext);
        this.start_time = System.nanoTime();
    }


    //单个测试用例执行结果为：成功时，打印日志
    public void onTestSuccess(ITestResult tr){
        System.out.println("TestCase passed");
        super.onTestSuccess(tr);

    }


    //单个测试用例执行结果为：失败时，打印当前目录
    public void onTestFailure(ITestResult tr) {
        System.out.println("TestCase Failure");
        super.onTestFailure(tr);

    }


    //单个测试用例执行结果为：跳过时，打印日志
    public void  onTestSkipped(ITestResult tr){
        System.out.println("TestCase Skipped");
        super.onTestSkipped(tr);

    }



    //总测试执行结束
    public void onFinish(ITestContext testContext){
        //打印结束时间
        String curTime2=time.format(nowTime);
        System.out.println("测试套件执行已经结束："+curTime2);
        super.onFinish(testContext);

        System.out.println("________________以下为关建数据________________");
        //计算运行时间(纳秒级精度)
        this.end_time = System.nanoTime();
        long time = this.end_time - this.start_time;
        double run_time = time/1000000000.0;

        //定义json格式的测试结果集合
        JSONObject test_result = new JSONObject();
        //定义json格式的详细信息集合
        JSONArray details = new JSONArray();
        //遍历执行失败的用例集合，并获取各项信息
        for(ITestResult FailedTests : testContext.getFailedTests().getAllResults()){
            JSONObject detail = new JSONObject();
            String test_class = FailedTests.getTestClass().getName().toString();
            detail.put("status","failures");
            detail.put("note",FailedTests.getThrowable().toString());
            detail.put("test_case",FailedTests.getName().toString());
            detail.put("explain",test_class);
            details.add(detail);

        }

        //获取用例执行数据
        int passed_num = testContext.getPassedTests().size();
        int failed_num = testContext.getFailedTests().size();
        int skipped_num = testContext.getSkippedTests().size();
        int total_num = passed_num+failed_num+skipped_num;
        //定义本次测试结果，是成功还是失败，返回一个布尔值
        boolean was_successful = (failed_num == 0) ? true:false;

        //获取当前xtest系统版本信息
        utils utils= new utils();
        String pro_version = utils.get_xtest_version();
        if(pro_version == null){
            pro_version = "0.0.0.0";
        }

        //添加各项测试数据到json格式的测试结果集合test_result
        test_result.put("was_successful", was_successful);
        test_result.put("errors", 0);
        test_result.put("failures", failed_num);
        test_result.put("skipped", skipped_num);
        test_result.put("total", total_num);
        test_result.put("run_time", run_time);
        test_result.put("pro_id", project_id);
        test_result.put("pro_version", pro_version);
        test_result.put("details", details);
        //打印测试结果数据集合
        System.out.println("获取测试结果数据集合:"+test_result);

        //实例化txtFun类
        txtFun txt = new txtFun();
        //获取当前 project 目录
        String path = System.getProperty("user.dir").replace("\\", "/");
        File tempfile = new File(path+"/src/main/java/apps/temp.txt");
        String content = test_result.toString();
        //将测试数据写入文件
        try {
            boolean flag = txt.writeTxtFile(content, tempfile);
            assert flag == true : "写入内容失败";
        }
        catch (Exception e)
        {
            System.out.println(e);
        }


    }



}




