package com.chenghe.sys.dao;

import com.youguu.core.util.PageHolder;
import com.chenghe.sys.pojo.SysLog;

/**
 * Created by leo on 2017/12/18.
 */
public interface SysLogDAO {
	int saveSysLog(SysLog sysLog);

	PageHolder<SysLog> listSysLogByPage(String moduleName, String methodName, String userName, String ip, String startDate,
										String endDate, int pageIndex, int pageSize);

}
