package com.cmcc.spider;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Test;

/*
 * 1����һ��������Ŀ����ȡ�ٶ���ҳ
 */
public class CaptureBaidu {
    
    //���弴�����ʵ�����
    String url = "http://www.baidu.com";
    
    @Test
    public void test1() {
        String result = sendGet(url);
        System.out.println(result);
    }
    
    @Test
    public void test2() {
        String target = sendGet(url);
        //ץȡlogoͼƬ����
        String regex = "src=\"//www.baidu.com/img/(.*\\.png)\"";
        String img = RegexString(target, regex);
        System.out.println(img);
    }
    
    //����url��ȡҳ����������
    public String sendGet(String url) {
        //����һ�������ַ���
        BufferedReader reader = null;
        //����һ���ַ����������洢��ҳ����
        String result = "";
        
        try {
            //��Stringת����url����
            URL realUrl = new URL(url);
            //��ʼ��һ�����ӵ���url������
            URLConnection connection = realUrl.openConnection();
//            connection.setRequestProperty("content-type", "text/html;charset=utf-8");
            //��ʼʵ�ʵ�����
            connection.connect();
            //��ʼ��BufferedReader����������ȡurl��Ӧ
            reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            //�����洢ץȡ����ÿһ������
            String line = null;
            //����ץȡ����ÿһ�����ݣ�����洢��result��
            while((line = reader.readLine()) != null) {
                //�������봦��
                String tmp = new String(line.getBytes(), "utf-8");      
                result += tmp;
            }
            
            return result;
        } catch (Exception e) {
            System.out.println("����get�������" + e);
            e.printStackTrace();
        }finally {
            //ʹ��finally���ر�������
            try {
                if(reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        
        return "";
    }
    
    //ͨ��������ץȡ�ַ�����������Ҫ�Ĳ���
    public String RegexString(String target, String patternStr) {
        Pattern pattern = Pattern.compile(patternStr);
        Matcher matcher = pattern.matcher(target);
        if(matcher.find()) {
            return matcher.group(1);
        }
        
        return "";
    }

}
