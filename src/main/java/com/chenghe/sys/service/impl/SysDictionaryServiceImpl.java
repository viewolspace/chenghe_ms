package com.chenghe.sys.service.impl;

import com.chenghe.sys.dao.ISysDictionaryDAO;
import com.chenghe.sys.dao.ISysDictionarySeqDAO;
import com.chenghe.sys.pojo.SysDictionary;
import com.chenghe.sys.pojo.SysDictionarySeq;
import com.chenghe.sys.service.ISysDictionaryService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by lenovo on 2018/6/28.
 */
@Service("sysDictionaryService")
public class SysDictionaryServiceImpl implements ISysDictionaryService {

    @Resource
    private ISysDictionaryDAO systemDictionaryDAO;

    @Resource
    private ISysDictionarySeqDAO systemDictionarySeqDAO;


    private String getNextValue(String parentId) {
        int seq = 1;
        SysDictionarySeq sysDictionarySeq = systemDictionarySeqDAO.getSysDictionarySeq(parentId);
        if (sysDictionarySeq == null) {
            sysDictionarySeq = new SysDictionarySeq();
            sysDictionarySeq.setParentId(parentId);
            sysDictionarySeq.setSeq(seq);
            systemDictionarySeqDAO.addSysDictionarySeq(sysDictionarySeq);
        } else {
            seq = sysDictionarySeq.getSeq() + 1;
            systemDictionarySeqDAO.updateSysDictionarySeq(parentId);
        }
        String seq_str = String.valueOf(seq);
        if (seq_str.length() < 4) {
            return parentId + "0000".substring(0, 4 - seq_str.length()) + seq_str;
        } else {
            return null;
        }

    }

    @Transactional("chengheMsTx")
    @Override
    public String addSysDictionary(SysDictionary sysDictionary) {
        String parentId = sysDictionary.getParentId();
        String id = getNextValue(parentId);
        sysDictionary.setId(id);
        int result = systemDictionaryDAO.addSysDictionary(sysDictionary);
        if (result > 0) {
            return id;
        } else {
            return null;
        }

    }

    @Override
    public int delSysDictionary(String id) {
        return systemDictionaryDAO.delSysDictionary(id);
    }

    @Override
    public int updateSysDictionary(SysDictionary sysDictionary) {
        return systemDictionaryDAO.updateSysDictionary(sysDictionary);
    }

    @Override
    public SysDictionary getSysDictionary(String id) {
        return systemDictionaryDAO.getSysDictionary(id);
    }

    @Override
    public List<SysDictionary> listByParent(String parentId) {
        return systemDictionaryDAO.listByParent(parentId);
    }

    @Override
    public List<SysDictionary> listByParentAndApp(String parentId, int appId) {
        return systemDictionaryDAO.listByParentAndApp(parentId, appId);
    }

    @Override
    public List<SysDictionary> listAll(String parentId) {
        return systemDictionaryDAO.listAll(parentId);
    }

    @Override
    public SysDictionary findSysDictionary(String parentId, int appId) {
        return systemDictionaryDAO.findSysDictionary(parentId, appId);
    }
}
