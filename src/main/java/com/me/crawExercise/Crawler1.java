package com.me.crawExercise;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * 通过urlconnection抓取信息:
 * 步骤:
 * 1.获取url
 * 2.获取http请求
 * 3.获取状态码
 * 4.根据状态码返回信息
 * 
 * @author fuwenjun01
 *
 */
public class Crawler1 {
	static void  getPage(String _url){
		//保存每次读到的行数据
		String row;

		try {
			//新建url对象,表示访问网页
			URL url=new URL(_url);
			//建立http连接,返回连接对象,urlConnection
			HttpURLConnection urlConnection=(HttpURLConnection) url.openConnection();
			//获取对应的http状态码
			int responseCode=urlConnection.getResponseCode();
			//对于状态码进行判断
			if(responseCode==200){
				//获取成功,由URLConnection对象获取输入流来得到网页源代码
				BufferedReader reader=new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), "utf-8"));
				while((row=reader.readLine())!=null)
					System.out.println(row);
			}else{
				System.out.println("获取不到源码,服务器返回码为:"+responseCode);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
	public static void main(String[] args) {
		Crawler1.getPage("https://blog.csdn.net/leexyzqwe/article/details/77750867");
	}
}
