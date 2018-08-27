package com.etoak.crawl.link;
/**
 * 添加新增链接的过滤器
 * 默认为null
 * @author fuwenjun01
 *
 */
public interface LinkFilter {
    public boolean accept(String url);
}
