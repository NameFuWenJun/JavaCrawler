package com.etoak.crawl.link;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

/*
*  Link主要功能;
*  1: 存储已经访问过的URL路径 和 待访问的URL 路径;
*
*
* */
public class Links {

    //已访问的 url 集合  已经访问过的 主要考虑 不能再重复了 使用set来保证不重复;
    private static Set visitedUrlSet = new HashSet();

    //待访问的 url 集合  待访问的主要考虑 1:规定访问顺序;2:保证不提供重复的带访问地址;
    private static LinkedList unVisitedUrlQueue = new LinkedList();

    //获得已经访问的 URL 数目
    public static int getVisitedUrlNum() {
        return visitedUrlSet.size();
    }
    
    private static LinkFilter linkFilter=null;
    
    //添加到访问过的 URL
    public static void addVisitedUrlSet(String url) {
        visitedUrlSet.add(url);
    }
    
    //移除访问过的 URL
    public static void removeVisitedUrlSet(String url) {
        visitedUrlSet.remove(url);
    }



    //获得 待访问的 url 集合
    public static LinkedList getUnVisitedUrlQueue() {
        return unVisitedUrlQueue;
    }

    // 添加到待访问的集合中  保证每个 URL 只被访问一次
    public static boolean addUnvisitedUrlQueue(String url) {
        if (url != null && !url.trim().equals("")  && !visitedUrlSet.contains(url)  && !unVisitedUrlQueue.contains(url)){
           if(linkFilter!=null){
              if(linkFilter.accept(url)){
                  unVisitedUrlQueue.add(url); 
                  return true;
              }
           }else{
               unVisitedUrlQueue.add(url);
               return true;
           }
        }
        return false;
    }

    //删除 待访问的url
    public static Object removeHeadOfUnVisitedUrlQueue() {
        return unVisitedUrlQueue.removeFirst();
    }

    //判断未访问的 URL 队列中是否为空
    public static boolean unVisitedUrlQueueIsEmpty() {
        return unVisitedUrlQueue.isEmpty();
    }

    public static void setLinkFilter(LinkFilter linkFilter) {
        Links.linkFilter = linkFilter;
    }




}
