package com.chenghe.seekers.controller;

import com.chenghe.common.GridBaseResponse;
import com.chenghe.parttime.pojo.User;
import com.chenghe.parttime.query.UserQuery;
import com.chenghe.parttime.service.IUserService;
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
@RequestMapping("jobSeekers")
public class JobSeekersController {

    @Resource
    private IUserService userService;

    @RequestMapping(value = "/userList", method = RequestMethod.POST)
    @ResponseBody
    public GridBaseResponse userList(@RequestParam(value = "userId", defaultValue = "0") int userId,
                                     @RequestParam(value = "phone", defaultValue = "") String phone,
                                     @RequestParam(value = "realName", defaultValue = "") String realName,
                                     @RequestParam(value = "nickName", defaultValue = "") String nickName,
                                     @RequestParam(value = "page", defaultValue = "1") int page,
                                     @RequestParam(value = "limit", defaultValue = "10") int limit) {

        GridBaseResponse rs = new GridBaseResponse();
        rs.setCode(0);
        rs.setMsg("ok");
        UserQuery query = new UserQuery();
        query.setRealName(realName);
        query.setNickName(nickName);
        query.setPhone(phone);
        query.setUserId(userId);
        query.setPageIndex(page);
        query.setPageSize(limit);

        PageHolder<User> pageHolder = userService.queryUser(query);
        if (null != pageHolder.getList()) {
            rs.setData(pageHolder.getList());
            rs.setCount(pageHolder.getTotalCount());
        }

        return rs;
    }
}
