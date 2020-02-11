package com.chenghe.sys.pojo;

import java.util.Date;

/**
 * Created by lenovo on 2018/6/27.
 */
public class SysDictionary {

    private String id;
    private String parentId;
    private String value; //字典值
    private Integer num;
    private String logo;
    private String name;
    private Date cTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getcTime() {
        return cTime;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public void setcTime(Date cTime) {
        this.cTime = cTime;
    }

    @Override
    public String toString() {
        return "Category{" +
                "id='" + id + '\'' +
                ", parentId='" + parentId + '\'' +
                ", value=" + value +
                ", num=" + num +
                ", logo='" + logo + '\'' +
                ", name='" + name + '\'' +
                ", cTime=" + cTime +
                '}';
    }
}
