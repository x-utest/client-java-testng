package apps;

import java.io.File;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;

/**
 * 这个类是一个TXT文本处理功能的类；
 * 包含了两种方法：读取TXT——》readTxtFile()，写入内容到TXT——》writeTxtFile
 */


public class txtFun {


    public String readTxtFile(File file){
        String result = "";
        try {
            InputStreamReader reader = new InputStreamReader(new FileInputStream(file),"gbk");
            BufferedReader br = new BufferedReader(reader);
            String s = null;
            while((s=br.readLine())!=null){
                result = result  + s;
                //System.out.println(s);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }


    public boolean writeTxtFile(String content,File fileName)throws Exception{
        boolean flag=false;
        FileOutputStream fileOutputStream=null;
        try {
            fileOutputStream = new FileOutputStream(fileName);
            fileOutputStream.write(content.getBytes("utf-8"));
            fileOutputStream.close();
            flag=true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }




}