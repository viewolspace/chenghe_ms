package com.chenghe.statistics.controller;

import com.chenghe.common.GridBaseResponse;
import com.chenghe.parttime.pojo.UserStat;
import com.chenghe.parttime.query.UserStatQuery;
import com.chenghe.parttime.service.IUserStatService;
import com.youguu.core.util.PageHolder;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;

/**
 * Created by leo on 2017/11/29.
 */
@Controller
@RequestMapping("statistics")
public class StatisticsController {

    @Resource
    private IUserStatService userStatService;

    @RequestMapping(value = "/userStatList", method = RequestMethod.POST)
    @ResponseBody
    public GridBaseResponse userStatList(@RequestParam(value = "statDate", defaultValue = "") String statDate,
                                         @RequestParam(value = "page", defaultValue = "1") int page,
                                         @RequestParam(value = "limit", defaultValue = "10") int limit) {

        GridBaseResponse rs = new GridBaseResponse();
        rs.setCode(0);
        rs.setMsg("ok");

        try {
            UserStatQuery query = new UserStatQuery();
            if(!StringUtils.isEmpty(statDate)){
                SimpleDateFormat dft = new SimpleDateFormat("yyyy-MM-dd");
                query.setStatDate(dft.parse(statDate));
            }
            query.setPageIndex(page);
            query.setPageSize(limit);

            PageHolder<UserStat> pageHolder = userStatService.queryUserStat(query);
            if (null != pageHolder.getList()) {
                rs.setData(pageHolder.getList());
                rs.setCount(pageHolder.getTotalCount());
            }
        } catch (Exception e) {

        }

        return rs;
    }
}
