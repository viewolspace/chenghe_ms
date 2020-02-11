package com.chenghe.sys.dao;


import com.chenghe.sys.pojo.SysDictionary;

import java.util.List;

/**
 * Created by lenovo on 2018/6/28.
 */
public interface ISysDictionaryDAO {

    int addSysDictionary(SysDictionary sysDictionary);

    SysDictionary getSysDictionary(String id);

    int delSysDictionary(String id);

    int updateSysDictionary(SysDictionary sysDictionary);

    /**
     * 查询多个分类
     *
     * @param ids
     * @return
     */
    List<SysDictionary> listSysDictionarys(List<String> ids);

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
