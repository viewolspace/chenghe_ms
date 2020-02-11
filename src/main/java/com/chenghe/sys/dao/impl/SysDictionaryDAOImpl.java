package com.chenghe.sys.dao.impl;

import com.chenghe.sys.base.ChengheMsDAO;
import com.chenghe.sys.dao.ISysDictionaryDAO;
import com.chenghe.sys.pojo.SysDictionary;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lenovo on 2018/6/28.
 */
@Repository("sysDictionaryDAO")
public class SysDictionaryDAOImpl extends ChengheMsDAO<SysDictionary> implements ISysDictionaryDAO {
    @Override
    public int addSysDictionary(SysDictionary sysDictionary) {
        sysDictionary.setcTime(new Date());
        return super.insert(sysDictionary);
    }

    @Override
    public SysDictionary getSysDictionary(String id) {
        return super.get(id);
    }

    @Override
    public int delSysDictionary(String id) {
        return super.delete(id);
    }

    @Override
    public int updateSysDictionary(SysDictionary sysDictionary) {
        return super.update(sysDictionary);
    }

    @Override
    public List<SysDictionary> listByParent(String parentId) {
        Map<String, Object> map = new HashMap<>();
        map.put("parentId", parentId);
        return super.findBy("queryByParent", map);
    }

    @Override
    public List<SysDictionary> listAll(String parentId) {
        Map<String, Object> map = new HashMap<>();
        map.put("parentId", parentId);
        return super.findBy("queryAll", map);
    }

    @Override
    public List<SysDictionary> listSysDictionarys(List<String> ids) {
        Map<String, Object> map = new HashMap<>();
        map.put("list", ids);
        return super.findBy("listSysDictionarys", map);
    }
}
