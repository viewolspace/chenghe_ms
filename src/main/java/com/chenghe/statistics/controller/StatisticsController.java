package com.chenghe.statistics.controller;

import com.chenghe.common.GridBaseResponse;
import com.chenghe.shiro.token.TokenManager;
import com.chenghe.sys.pojo.SysUser;
import com.chenghe.sys.service.SysUserService;
import com.youguu.core.util.PageHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * Created by leo on 2017/11/29.
 */
@Controller
@RequestMapping("statistics")
public class StatisticsController {

    @Resource
    private SysUserService sysUserService;

    @RequestMapping(value = "/userlist", method = RequestMethod.POST)
    @ResponseBody
    public GridBaseResponse userList(@RequestParam(value = "userId", defaultValue = "0") int userId,
                                     String realName,
                                     @RequestParam(value = "page", defaultValue = "1") int page,
                                     @RequestParam(value = "limit", defaultValue = "10") int limit) {

        GridBaseResponse rs = new GridBaseResponse();
        rs.setCode(0);
        rs.setMsg("ok");

        PageHolder<SysUser> pageHolder = sysUserService.querySysUserByPage(TokenManager.getAppId(), userId, realName, page, limit);
        if (null != pageHolder.getList()) {
            rs.setData(pageHolder.getList());
            rs.setCount(pageHolder.getTotalCount());
        }

        return rs;
    }
}
