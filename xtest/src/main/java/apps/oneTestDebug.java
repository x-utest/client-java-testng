package apps;


/**
 * 这个文件时作为调试用的，如果有任何不确定的功能，可以先在这里运行调试
 */

public class oneTestDebug {
    public static void main(String args[]){

        // 获取当前 project 目录
        String path = System.getProperty("user.dir").replace("\\", "/");
        System.out.println(path);
        System.out.println(path+"/src/main/java/apps/temp.txt");

        //调试功能……
    }


}
