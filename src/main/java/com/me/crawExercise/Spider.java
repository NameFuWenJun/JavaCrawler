package com.me.crawExercise;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * 通过Jsoup获取网页信息
 * 我个人比较喜欢这个
 * @author fuwenjun01
 *
 */
public class Spider {
	public static void main(String[] args) throws IOException {
		String url="http://www.stats.gov.cn/tjsj/tjbz/tjyqhdmhcxhfdm/2015/index.html";
		Document document=Jsoup.connect(url).timeout(3000).get();
		//通过Document的select方法获取属性结点集合
		Elements elements=document.select("a");
		//得到结点的第一个对象
		Element element=elements.get(0);
		System.out.println(element);
	}
}
