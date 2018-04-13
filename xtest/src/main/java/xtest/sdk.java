package xtest;

import apps.xtest_conf;
import com.alibaba.fastjson.JSONObject;
import com.github.kevinsawicki.http.HttpRequest;
import com.zf.zson.ZSON;
import com.zf.zson.result.ZsonResult;

import java.util.HashMap;

/**
 * 这个类是一个sdk类，包含了和xtest系统通信的两个方法
 */


public class sdk {

    //定义一个空的token
    public String token = "";

    //实例化配置文件类
    xtest_conf conf = new xtest_conf();
    String base_url = conf.base_url;


    //获取认证信息，返回一个布尔值
    public Boolean get_api_auth(String appid, String appkey){
        if(appid == null || appkey == null){
            return false;
        }else{
            String url = base_url+"/testdata/api-auth/";
            HashMap post_data = new HashMap();
            post_data.put("appid_form",appid);
            post_data.put("appkey_form",appkey);
            //发送获取认证信息请求
            String response = HttpRequest.post(url).form(post_data).body();
            System.out.println("获取认证信息的返回体："+response);
            ZsonResult zr = ZSON.parseJson(response);
            //获取code
            String code = zr.getValues("//code").toString();
            code = code.replaceAll("[\\[\\]]", "");
            //System.out.println("本次获取认证信息时的code是："+code);
            //获取token
            token = zr.getValues("//token").toString();
            token = token.replaceAll("[\\[\\]]", "");
            System.out.println("本次获取认证信息时获取的token是："+token);
            if(code.equals("200")){
                return true;
            }else{
                System.out.println("server api call exception~");
                return false;
            }
        }

    }


    //将测试结果发送给服务器
    public String post_unit_test_data(JSONObject test_res_json){
        //定义测试数据上传的接口url
        String url = base_url+"/testdata/create-test-data/?token="+token;
        System.out.println("提交测试数据的url："+url);
        System.out.println("提交的测试数据："+test_res_json);
        //发送post请求，并定义响应体
        HttpRequest httpRequest = HttpRequest.post(url).acceptJson();
        httpRequest.send(test_res_json.toString());
        String response = httpRequest.body();
        System.out.println("提交测试数据之后的返回体："+response);
        //过滤出状态码
        int code = httpRequest.code();
        //System.out.println("本次提交测试数据时获取的code是："+code);
        //打印提交结果
        if(code == 200){
            System.out.println("恭喜，本次测试数据，提交成功了");
        }else{
            System.out.println("很遗憾，本次测试数据，提交失败了");
        }
        return response;
    }



}
