package com.etoak.crawl.main.wuxiJob.pojo;
/**
 * 51job上关于无锡的职位信息
 * @author fuwenjun01
 *
 */
public class Job {
    private Integer id;
    //职位信息
    private String position;
    private String positionUrl;
    private String company;
    private String companyUrl;
    //工作地点
    private String place;
    private String salary;
    //法布时间
    private String time;
    
    
    public Job() {
        super();
        // TODO Auto-generated constructor stub
    }
    public Job(String position, String positionUrl, String company, String companyUrl, String place, String salary,
            String time) {
        super();
        this.position = position;
        this.positionUrl = positionUrl;
        this.company = company;
        this.companyUrl = companyUrl;
        this.place = place;
        this.salary = salary;
        this.time = time;
    }
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getPosition() {
        return position;
    }
    public void setPosition(String position) {
        this.position = position;
    }
    public String getPositionUrl() {
        return positionUrl;
    }
    public void setPositionUrl(String positionUrl) {
        this.positionUrl = positionUrl;
    }
    public String getCompany() {
        return company;
    }
    public void setCompany(String company) {
        this.company = company;
    }
    public String getCompanyUrl() {
        return companyUrl;
    }
    public void setCompanyUrl(String companyUrl) {
        this.companyUrl = companyUrl;
    }
    public String getPlace() {
        return place;
    }
    public void setPlace(String place) {
        this.place = place;
    }
    public String getSalary() {
        return salary;
    }
    public void setSalary(String salary) {
        this.salary = salary;
    }
    public String getTime() {
        return time;
    }
    public void setTime(String time) {
        this.time = time;
    }
    
}
