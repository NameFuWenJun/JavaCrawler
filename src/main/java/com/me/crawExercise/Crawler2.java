package com.me.crawExercise;

import static org.hamcrest.CoreMatchers.startsWith;

import java.io.FileWriter;
import java.io.IOException;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;

public class Crawler2 {
	private static HttpClient httpClient=new HttpClient();
	static GetMethod getMethod;
	public static boolean downloadPage(String path) throws HttpException, IOException {
		getMethod=new GetMethod(path);
		//获得响应状态码
		int statusCode=httpClient.executeMethod(getMethod);
		if(statusCode==HttpStatus.SC_OK){
			System.out.println("response:"+getMethod.getResponseBodyAsString());
			//写入本地文件
			FileWriter fWrite=new FileWriter("hello.txt");
			String pageString=getMethod.getResponseBodyAsString();
			getMethod.releaseConnection();
			fWrite.write(pageString,0,pageString.length());
			fWrite.flush();
			//关闭文件
			fWrite.close();
			//释放资源
			return true;
		}
		return false;
	}
	
	
	public static void main(String[] args) {
		String path="https://blog.csdn.net/leexyzqwe/article/details/77750867";
		System.out.println("program start");
		try {
			Crawler2.downloadPage(path);
		} catch (HttpException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("program stop");
	}
}
