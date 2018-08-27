package com.etoak.crawl.main;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.etoak.crawl.main.ChinaProvince.mapper.PlaceMapper;
import com.etoak.crawl.main.ChinaProvince.pojo.Place;

public class Test {
    public static void main(String[] args) {
       /* ApplicationContext ac=new ClassPathXmlApplicationContext("spring-mybatis.xml");
        PlaceMapper placeMapper=ac.getBean(PlaceMapper.class);
        Place place=new Place();
        place.setID("test");
        place.setName("test");
        place.setNumber("test");
        placeMapper.insertPlace(place);*/
       String str1="http://www.stats.gov.cn/tjsj/tjbz/tjyqhdmhcxhfdm/2015/54/5402.html";
       String str2="http://www.miibeian.gov.cn/";
       String pattern="\\S*.html";
       Pattern p=Pattern.compile(pattern);
       Matcher m=p.matcher(str1);
       Matcher m1=p.matcher(str2);
       System.out.println(m.matches()+" "+m1.matches());
      
    
    }
}
