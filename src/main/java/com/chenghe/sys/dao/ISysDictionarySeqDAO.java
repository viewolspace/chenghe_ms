package com.chenghe.sys.dao;


import com.chenghe.sys.pojo.SysDictionarySeq;

/**
 * Created by lenovo on 2018/6/28.
 */
public interface ISysDictionarySeqDAO {

    int addSysDictionarySeq(SysDictionarySeq sysDictionarySeq);


    int updateSysDictionarySeq(String parentId);


    SysDictionarySeq getSysDictionarySeq(String parentId);


}
