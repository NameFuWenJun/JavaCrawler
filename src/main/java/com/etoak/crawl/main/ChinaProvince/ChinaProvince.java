package com.etoak.crawl.main.ChinaProvince;

import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.etoak.crawl.link.LinkFilter;
import com.etoak.crawl.link.Links;
import com.etoak.crawl.main.MyCrawler;
import com.etoak.crawl.main.ChinaProvince.mapper.PlaceMapper;
import com.etoak.crawl.main.ChinaProvince.pojo.Place;
import com.etoak.crawl.page.Page;
import com.etoak.crawl.page.PageParserTool;
import com.etoak.crawl.page.RequestAndResponseTool;
import com.etoak.crawl.util.FileTool;
/**
 * 抓取中国省份的数据
 * 并存入数据库
 * @author fuwenjun01
 *
 */
public class ChinaProvince {
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
     * @throws InterruptedException 
     */
    public void crawling(String[] seeds) throws InterruptedException {

        //初始化 URL 队列
        initCrawlerWithSeeds(seeds);

        //获取Mapper
        ApplicationContext ac=new ClassPathXmlApplicationContext("spring-mybatis.xml");

        PlaceMapper placeMapper=ac.getBean(PlaceMapper.class);
        //定义过滤器，提取以 http://www.baidu.com 开头的链接
        LinkFilter filter = new LinkFilter() {
            public boolean accept(String url) {
                if (url.startsWith("http://www.baidu.com"))
                    return true;
                else
                    return false;
            }
        };
        int j=0;
        //循环条件：待抓取的链接不空
        while (!Links.unVisitedUrlQueueIsEmpty()) {

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
            //对page进行处理： 访问DOM的某个标签
            Elements es = PageParserTool.select(page,"a");
            if(!es.isEmpty()){
                int i=1;
                String ID="";
                String name="";
                String number="";
                for(Element e:es){
                    // System.out.println(e.text());
                    String pattern ="\\d+.html";
                    Pattern r=Pattern.compile(pattern);
                    //System.out.println(e.outerHtml());
                    Matcher m=r.matcher(e.outerHtml());
                    if(m.find()){
                        if(j==0){
                            //抓取省级参数
                            int index=m.group(0).indexOf(".html");
                            ID=m.group(0).substring(0, index)+"0";
                            name=e.text();
                            Place place=new Place();
                            place.setID(ID);
                            place.setName(name);
                            placeMapper.insertPlace(place);
                        }else{
                            //非省级参数
                            if(i%2!=0){
                                ID=e.text();
                                // System.out.println(ID);
                            }else{
                                name=e.text();
                                //System.out.println(name);
                                Place place=new Place();
                                place.setID(ID);
                                place.setName(name);
                                placeMapper.insertPlace(place);
                            }
                            i++;
                        }
                        //System.out.println(m.group(0));
                    }else{
                       /* System.out.println("last");
                        System.out.println(es);*/
                        //没有找到下一个链接,为最后一页
                        Document document=page.getDoc();
                        Elements elements=document.getElementsByClass("villagetr");
                        //获取最后一级的数据
                        for(Element em:elements){
                            String value=em.text();
                            String [] tex=value.split(" ");
                            ID=tex[0];
                            number=tex[1];
                            name=tex[2];
                            Place place=new Place();
                            place.setID(ID);
                            place.setNumber(number);
                            place.setName(name);
                            placeMapper.insertPlace(place);
                        }
                    }

                }

            }
            j++;
            //将保存文件
            // FileTool.saveToLocal(page);

            //将已经访问过的链接放入已访问的链接中；
            Links.addVisitedUrlSet(visitUrl);

            //得到超链接
            Set<String> links = PageParserTool.getLinks(page,"a");
            for (String link : links) {
                if(link.indexOf("html")!=-1){
                    Links.addUnvisitedUrlQueue(link);
                    // System.out.println("新增爬取路径: " + link);
                }
            }
        }
    }


    //main 方法入口
    public static void main(String[] args) throws InterruptedException {
        ChinaProvince crawler = new ChinaProvince();
        crawler.crawling(new String[]{"http://www.stats.gov.cn/tjsj/tjbz/tjyqhdmhcxhfdm/2015/index.html"});
        System.out.println("stop");
        // crawler.crawling(new String[]{"http://www.stats.gov.cn/tjsj/tjbz/tjyqhdmhcxhfdm/2015/65/01/02/650102002.html"});
        /* String url="<a href='42.html'>湖北省<br></a>";
        String pattern ="\\d+.html";
        Pattern r=Pattern.compile(pattern);
        Matcher m=r.matcher(url);
        if(m.find()){
            System.out.println(m.group(0));
        }
        System.out.println(url.matches(pattern));*/
    }
}
