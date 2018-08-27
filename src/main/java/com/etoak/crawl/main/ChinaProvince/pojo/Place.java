package com.etoak.crawl.main.ChinaProvince.pojo;

public class Place {
    //省级或者地方辖区的名称
    String name;
    //省级辖区的编号开头,保存前三位,其余保存全部
    String ID;
    //城乡编号最低一级才有城乡编号,可为空
    String number;
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getID() {
        return ID;
    }
    public void setID(String iD) {
        ID = iD;
    }
    public String getNumber() {
        return number;
    }
    public void setNumber(String number) {
        this.number = number;
    }
    
}
