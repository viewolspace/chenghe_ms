package com.chenghe.category.controller;

/**
 * describe:
 *
 * @date: 2019/07/21 19:36:19:36
 * @version: V1.0
 * @review:
 */
public class TreeNode {
    private String id;
    private String pid;
    private String title;
    private int status;

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
}
