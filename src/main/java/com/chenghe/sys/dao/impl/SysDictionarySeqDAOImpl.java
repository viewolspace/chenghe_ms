package com.chenghe.sys.dao.impl;

import com.chenghe.sys.base.ChengheMsDAO;
import com.chenghe.sys.dao.ISysDictionarySeqDAO;
import com.chenghe.sys.pojo.SysDictionarySeq;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by lenovo on 2018/6/28.
 */
@Repository("systemDictionarySeqDAO")
public class SysDictionarySeqDAOImpl extends ChengheMsDAO<SysDictionarySeq> implements ISysDictionarySeqDAO {
    @Override
    public int addSysDictionarySeq(SysDictionarySeq sysDictionarySeq) {
        sysDictionarySeq.setcTime(new Date());
        sysDictionarySeq.setmTime(new Date());
        return super.insert(sysDictionarySeq);
    }

    @Override
    public int updateSysDictionarySeq(String parentId) {
        Map<String, Object> map = new HashMap<>();
        map.put("parentId", parentId);
        map.put("mTime", new Date());
        return super.updateBy("updateSeq", map);
    }

    @Override
    public SysDictionarySeq getSysDictionarySeq(String parentId) {
        return super.get(parentId);
    }
}
