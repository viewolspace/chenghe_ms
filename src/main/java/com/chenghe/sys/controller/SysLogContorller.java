package com.chenghe.sys.controller;

import com.youguu.core.util.PageHolder;
import com.chenghe.shiro.token.TokenManager;
import com.chenghe.sys.pojo.SysLog;
import com.chenghe.sys.response.SysLogResponse;
import com.chenghe.sys.service.SysLogService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * Created by leo on 2017/12/18.
 */
@Controller
@RequestMapping("sysLog")
public class SysLogContorller {

	@Resource
	private SysLogService sysLogService;

	/**
	 * 查询操作日志列表
	 * @param moduleName
	 * @param methodName
	 * @param userName
	 * @param ip
	 * @param createTime
	 * @param page
	 * @param limit
	 * @return
	 */
	@RequestMapping(value = "/logList", method = RequestMethod.POST)
	@ResponseBody
	public SysLogResponse logList(@RequestParam(value = "moduleName", defaultValue = "") String moduleName,
									 @RequestParam(value = "methodName", defaultValue = "") String methodName,
									 @RequestParam(value = "userName", defaultValue = "") String userName,
									 @RequestParam(value = "ip", defaultValue = "") String ip,
								     @RequestParam(value = "createTime", defaultValue = "") String createTime,
									 @RequestParam(value = "page", defaultValue = "1") int page,
									 @RequestParam(value = "limit", defaultValue = "50") int limit) {

		SysLogResponse rs = new SysLogResponse();
		rs.setCode(0);
		rs.setMsg("ok");

		String endDate = "";
		String startDate = "";
		if (createTime != null && createTime.length() > 0) {
			String[] datearr = createTime.split(" ");
			startDate = datearr[0];
			endDate = datearr[2];
		}

		if("-1".equals(moduleName)){
			moduleName = "";
		}

		PageHolder<SysLog> pageHolder = sysLogService.listSysLogByPage(moduleName, methodName, userName, ip, startDate,
				endDate, page, limit);

		if(null != pageHolder && pageHolder.size()>0){
			rs.setData(pageHolder.getList());
			rs.setCount(pageHolder.getTotalCount());
		}

		return rs;
	}

}
