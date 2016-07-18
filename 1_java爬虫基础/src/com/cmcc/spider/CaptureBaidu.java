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
 * 1、第一个爬虫项目：爬取百度首页
 */
public class CaptureBaidu {
    
    //定义即将访问的链接
    String url = "http://www.baidu.com";
    
    @Test
    public void test1() {
        String result = sendGet(url);
        System.out.println(result);
    }
    
    @Test
    public void test2() {
        String target = sendGet(url);
        //抓取logo图片部分
        String regex = "src=\"//www.baidu.com/img/(.*\\.png)\"";
        String img = RegexString(target, regex);
        System.out.println(img);
    }
    
    //根据url爬取页面所有数据
    public String sendGet(String url) {
        //定义一个缓冲字符流
        BufferedReader reader = null;
        //定义一个字符串，用来存储网页内容
        String result = "";
        
        try {
            //将String转换成url对象
            URL realUrl = new URL(url);
            //初始化一个链接到该url的链接
            URLConnection connection = realUrl.openConnection();
//            connection.setRequestProperty("content-type", "text/html;charset=utf-8");
            //开始实际的链接
            connection.connect();
            //初始化BufferedReader输入流来读取url响应
            reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            //用来存储抓取到的每一行数据
            String line = null;
            //遍历抓取到的每一行数据，将其存储到result中
            while((line = reader.readLine()) != null) {
                //中文乱码处理
                String tmp = new String(line.getBytes(), "utf-8");      
                result += tmp;
            }
            
            return result;
        } catch (Exception e) {
            System.out.println("发送get请求出错：" + e);
            e.printStackTrace();
        }finally {
            //使用finally来关闭输入流
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
    
    //通过正则来抓取字符串中我们需要的部分
    public String RegexString(String target, String patternStr) {
        Pattern pattern = Pattern.compile(patternStr);
        Matcher matcher = pattern.matcher(target);
        if(matcher.find()) {
            return matcher.group(1);
        }
        
        return "";
    }

}
