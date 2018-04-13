package xtest;

import apps.xtest_conf;
import apps.txtFun;

import java.io.File;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/**
 * 这个类，主要作用是执行发送测试数据到xtest系统，它会先读取临时文件temp.txt的内容，然后发送到xtest系统；
 * 注意：在执行这个类之前，需要先执行一下testng.xml,这样才会有测试数据产生，并写入到临时文件temp.txt。
 */


public class start {

    //程序入口
    public static void main(String args[]){

        //实例化配置文件类
        xtest_conf conf = new xtest_conf();
        String app_id = conf.app_id;
        String app_key = conf.app_key;

        //定义测试结果数据集合
        JSONObject test_result = new JSONObject();

        //实例化sdk
        sdk sdk = new sdk();

        //定义测试结果数据json体
        JSONObject test_res_json = new JSONObject();

        //实例化txtFun类
        txtFun txt = new txtFun();

        //获取当前project目录
        String path = System.getProperty("user.dir").replace("\\", "/");
        File tempfile = new File(path+"/src/main/java/apps/temp.txt");

        //读取测试结果数据所在的TXT文件，并打印出来
        String content = txt.readTxtFile(tempfile);
        test_res_json= JSON.parseObject(content);
        //System.out.println(test_res_json);

        //获取认证信息
        boolean auth_res = sdk.get_api_auth(app_id, app_key);
        assert auth_res ==true :"获取认证信息失败";
        //开始上传测试数据
        if(auth_res == true){
            String post_result = sdk.post_unit_test_data(test_res_json);
            //System.out.println(post_result);
        }else{
            System.out.println("getting auth_res error!");
        }

    }


}
