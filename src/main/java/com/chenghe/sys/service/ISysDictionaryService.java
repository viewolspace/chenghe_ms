package com.chenghe.sys.service;

import com.chenghe.sys.pojo.SysDictionary;

import java.util.List;

/**
 * Created by lenovo on 2018/6/28.
 */
public interface ISysDictionaryService {
    /**
     * 添加一个新的分类
     *
     * @param sysDictionary
     * @return
     */
    String addSysDictionary(SysDictionary sysDictionary);


    int delSysDictionary(String id);

    int updateSysDictionary(SysDictionary sysDictionary);

    SysDictionary getSysDictionary(String id);

    /**
     * 查询子节点
     *
     * @param parentId
     * @return
     */
    List<SysDictionary> listByParent(String parentId);

    List<SysDictionary> listByParentAndApp(String parentId, int appId);

    /**
     * 查询所有节点
     *
     * @param parentId
     * @return
     */
    List<SysDictionary> listAll(String parentId);


    SysDictionary findSysDictionary(String parentId, int appId);

}
