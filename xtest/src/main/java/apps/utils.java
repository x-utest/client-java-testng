package apps;


import com.github.kevinsawicki.http.HttpRequest;
import com.zf.zson.ZSON;
import com.zf.zson.result.ZsonResult;

import java.util.List;

/**
 * 这个类是一个工具类，所有的通用方法都可以在这个类中定义
 */

public class utils {

    //实例化配置文件类
    xtest_conf conf = new xtest_conf();
    String base_url = conf.base_url;


    //获取xtest项目版本信息的方法
    public String get_xtest_version(){
        String appinfo_url = base_url+"/app-info/";
        String response = HttpRequest.get(appinfo_url).body();
        System.out.println("获取Xtest的版本信息:"+response);
        ZsonResult zr = ZSON.parseJson(response);
        List<Object> code = zr.getValues("//code");
        String Status_code = ZSON.toJsonString(code);
        Status_code = Status_code.replaceAll("[\\[\\]]", "");
        if(Status_code.equals("200")){
            List<Object> version = zr.getValues("//app_version");
            String xtest_version = ZSON.toJsonString(version);
            xtest_version = xtest_version.replaceAll("[\\[\\]]", "");
            //System.out.println("获取到Xtest的版本信息是："+xtest_version);
            return xtest_version;
        }else{
            return null;
        }
    }

}
