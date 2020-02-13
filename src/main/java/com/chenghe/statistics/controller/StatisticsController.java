package com.chenghe.statistics.controller;

import com.chenghe.common.GridBaseResponse;
import com.chenghe.parttime.pojo.PartTimeStat;
import com.chenghe.parttime.pojo.UserStat;
import com.chenghe.parttime.query.PartTimeStatQuery;
import com.chenghe.parttime.query.UserStatQuery;
import com.chenghe.parttime.service.IPartTimeStatService;
import com.chenghe.parttime.service.IUserStatService;
import com.chenghe.shiro.token.TokenManager;
import com.chenghe.sys.pojo.SysDictionary;
import com.chenghe.sys.service.ISysDictionaryService;
import com.youguu.core.util.PageHolder;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by leo on 2017/11/29.
 */
@Controller
@RequestMapping("statistics")
public class StatisticsController {

    @Resource
    private IUserStatService userStatService;
    @Resource
    private IPartTimeStatService partTimeStatService;
    @Resource
    private ISysDictionaryService sysDictionaryService;

    /**
     * 用户统计
     *
     * @param statDate
     * @param page
     * @param limit
     * @return
     */
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
            if (!StringUtils.isEmpty(statDate)) {
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
            e.printStackTrace();
        }

        return rs;
    }

    /**
     * 职位统计
     *
     * @param statDate
     * @param page
     * @param limit
     * @return
     */
    @RequestMapping(value = "/partTimeStatList", method = RequestMethod.POST)
    @ResponseBody
    public GridBaseResponse partTimeStatList(@RequestParam(value = "statDate", defaultValue = "") String statDate,
                                             @RequestParam(value = "page", defaultValue = "1") int page,
                                             @RequestParam(value = "limit", defaultValue = "10") int limit) {

        GridBaseResponse rs = new GridBaseResponse();
        rs.setCode(0);
        rs.setMsg("ok");

        try {
            PartTimeStatQuery query = new PartTimeStatQuery();
            if (!StringUtils.isEmpty(statDate)) {
                SimpleDateFormat dft = new SimpleDateFormat("yyyy-MM-dd");
                query.setStatDate(dft.parse(statDate));
            }
            query.setPageIndex(page);
            query.setPageSize(limit);

            List<SysDictionary> dictionaryList = sysDictionaryService.listByParentAndApp("00000002", TokenManager.getAppId());
            if (!CollectionUtils.isEmpty(dictionaryList)) {
                StringBuffer sb = new StringBuffer();
                for (SysDictionary sysDictionary : dictionaryList) {
                    sb.append(sysDictionary.getValue()).append(",");
                }
                query.setRecommend(sb.toString().substring(0, sb.toString().length() - 1));
            }

            if (!StringUtils.isEmpty(TokenManager.getCompanyId())) {
                query.setCompanyId(TokenManager.getCompanyId());
            }
            PageHolder<PartTimeStat> pageHolder = partTimeStatService.queryPartTimeStat(query);
            if (null != pageHolder.getList()) {
                /**
                 * 查询推荐位数据字典，页面显示推荐位置中文使用
                 */
                List<SysDictionary> recomList =  sysDictionaryService.listByParent("00000002");
                Map<String, String> recoMap = new HashMap<>();
                if(!CollectionUtils.isEmpty(recomList)){
                    for (SysDictionary sysDictionary : recomList) {
                        recoMap.put(sysDictionary.getValue(), sysDictionary.getName());
                    }
                }

                for(PartTimeStat partTimeStat : pageHolder.getList()){
                    partTimeStat.setRecommendName(recoMap.get(String.valueOf(partTimeStat.getRecommend())));
                }
                rs.setData(pageHolder.getList());
                rs.setCount(pageHolder.getTotalCount());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return rs;
    }
}
