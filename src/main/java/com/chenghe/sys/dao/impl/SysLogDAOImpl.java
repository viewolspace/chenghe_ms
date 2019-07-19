package com.chenghe.sys.dao.impl;

import com.chenghe.sys.base.ChengheMsDAO;
import com.chenghe.sys.dao.SysLogDAO;
import com.chenghe.sys.pojo.SysLog;
import com.youguu.core.util.PageHolder;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by leo on 2017/12/18.
 */
@Repository
public class SysLogDAOImpl extends ChengheMsDAO<SysLog> implements SysLogDAO {
    @Override
    public int saveSysLog(SysLog sysLog) {
        return this.insert(sysLog);
    }

    @Override
    public PageHolder<SysLog> listSysLogByPage(String moduleName, String methodName, String userName, String ip,
                                               String startDate, String endDate, int appId, int pageIndex, int pageSize) {
        Map<String, Object> map = new HashMap<>();
        map.put("moduleName", moduleName);
        map.put("methodName", methodName);
        map.put("userName", userName);
        map.put("ip", ip);
        map.put("startDate", startDate);
        map.put("endDate", endDate);
        map.put("appId", appId);
        return this.pagedQuery("querySysLogByPage", map, pageIndex, pageSize);
    }
}
