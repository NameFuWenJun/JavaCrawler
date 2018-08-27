package com.etoak.crawl.main.wuxiJob;

import com.etoak.crawl.link.Links;
import com.etoak.crawl.main.wuxiJob.mapper.JobMapper;
import com.etoak.crawl.main.wuxiJob.pojo.Job;
import com.etoak.crawl.page.Page;
import com.etoak.crawl.page.PageParserTool;
import com.etoak.crawl.page.RequestAndResponseTool;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Iterator;

public class WuxiJobCrawler {

    /**
     * 使用种子初始化 URL 队列
     *
     * @param seeds 种子 URL
     * @return
     */
    private void initCrawlerWithSeeds(String[] seeds) {
        for (int i = 0; i < seeds.length; i++){
            Links.addUnvisitedUrlQueue(seeds[i]);
        }
    }


    /**
     * 抓取过程
     *
     * @param seeds
     * @return
     */
    public void crawling(String[] seeds) {

        //初始化 URL 队列
        initCrawlerWithSeeds(seeds);
        
        //获取Mapper
        ApplicationContext ac=new ClassPathXmlApplicationContext("spring-mybatis.xml");

        JobMapper jobMapper=ac.getBean(JobMapper.class);
        for(int i=2;i<=1411;i++){
            String links="https://search.51job.com/list/070400,000000,0000,00,9,99,%2520,2,"+i+".html?lang=c&stype=&postchannel=0000&workyear=99&cotype=99&degreefrom=99&jobterm=99&companysize=99&providesalary=99&lonlat=0%2C0&radius=-1&ord_field=0&confirmdate=9&fromType=&dibiaoid=0&address=&line=&specialarea=00&from=&welfare="; 
            Links.addUnvisitedUrlQueue(links);
        }
        //循环条件：待抓取的链接不空且抓取的网页不多于 100000
        while (!Links.unVisitedUrlQueueIsEmpty()  && Links.getVisitedUrlNum() <= 100000) {

            //先从待访问的序列中取出第一个；
            String visitUrl = (String) Links.removeHeadOfUnVisitedUrlQueue();
            if (visitUrl == null){
                continue;
            }

            //根据URL得到page;
            Page page = RequestAndResponseTool.sendRequstAndGetResponse(visitUrl);
            if("unvisit".equals(page.getContentType())){
                Links.addUnvisitedUrlQueue(page.getUrl());
                continue;
            }
            //选择其中id为reslutList的div元素
            Elements pageElements=PageParserTool.select(page, "div#resultList");
            //通过id选择,其实只选择出来一个元素
            Element e=pageElements.get(0);
            //对page进行处理： 访问DOM的某个标签
            Elements es = PageParserTool.select(e,"div.el");
           //Elements es = PageParserTool.select(page,"a");
            if(!es.isEmpty()){
                Iterator iterator  = es.iterator();
                while(iterator.hasNext()) {
                    Element element = (Element) iterator.next();
                    Elements span=element.select("span");
                    Element position=span.get(0);
                    String url="";
                    String positionName=position.text();
                    Elements posEles=position.select("a");
                    if(!posEles.isEmpty()){
                        Element urlEle=posEles.get(0);
                        url=urlEle.attr("href");
                    }
                    Element company=span.get(1);
                    String companyName=company.text();
                    String companyUrl="";
                    Elements comEles=company.select("a");
                    if(!comEles.isEmpty()){
                        Element comEle=comEles.get(0);
                        companyUrl=comEle.attr("href");
                    }
                    Element place=span.get(2);
                    String palceName=place.text();
                    Element salary=span.get(3);
                    String salaryName=salary.text();
                    Element time=span.get(4);
                    String timeName=time.text();
                    Job job=new Job(positionName,url,companyName,companyUrl,palceName,salaryName,timeName);
                    jobMapper.insertJob(job);
                }
            }
            //将保存文件
            //FileTool.saveToLocal(page);

            //将已经访问过的链接放入已访问的链接中；
            Links.addVisitedUrlSet(visitUrl);
        }
    }


    //main 方法入口
    public static void main(String[] args) {
        WuxiJobCrawler crawler = new WuxiJobCrawler();
        crawler.crawling(new String[]{"https://search.51job.com/list/070400,000000,0000,00,9,99,%2520,2,1.html?lang=c&stype=&postchannel=0000&workyear=99&cotype=99&degreefrom=99&jobterm=99&companysize=99&providesalary=99&lonlat=0%2C0&radius=-1&ord_field=0&confirmdate=9&fromType=&dibiaoid=0&address=&line=&specialarea=00&from=&welfare="});
        System.out.println("stop");
    }
}
