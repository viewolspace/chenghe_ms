package com.chenghe.sys.vo;

/**
 * describe:
 *
 * @date: 2019/07/21 19:36:19:36
 * @version: V1.0
 * @review:
 */
public class DictionaryTreeNode {
    private String id;
    private String pid;
    private String title;
    private int status;
    private String value;
    private Integer num;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }
}
