package com.chenghe.sys.pojo;

import java.util.Date;

/**
 * Created by lenovo on 2018/6/27.
 * 分类的自增序列记录
 */
public class SysDictionarySeq {

    private String parentId;
    private int seq;
    private Date cTime;
    private Date mTime;

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public int getSeq() {
        return seq;
    }

    public void setSeq(int seq) {
        this.seq = seq;
    }

    public Date getcTime() {
        return cTime;
    }

    public void setcTime(Date cTime) {
        this.cTime = cTime;
    }

    public Date getmTime() {
        return mTime;
    }

    public void setmTime(Date mTime) {
        this.mTime = mTime;
    }

    @Override
    public String toString() {
        return "CategorySeq{" +
                "parentId='" + parentId + '\'' +
                ", seq=" + seq +
                ", cTime=" + cTime +
                ", mTime=" + mTime +
                '}';
    }
}
