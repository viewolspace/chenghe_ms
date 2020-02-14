package com.chenghe.advertise.controller;

import com.chenghe.common.BaseResponse;
import com.chenghe.common.GridBaseResponse;
import com.chenghe.parttime.pojo.Ad;
import com.chenghe.parttime.pojo.AdStat;
import com.chenghe.parttime.pojo.Category;
import com.chenghe.parttime.pojo.PartTimeStat;
import com.chenghe.parttime.query.AdQuery;
import com.chenghe.parttime.query.AdStatQuery;
import com.chenghe.parttime.service.IAdService;
import com.chenghe.parttime.service.IAdStatService;
import com.chenghe.parttime.service.ICategoryService;
import com.chenghe.shiro.token.TokenManager;
import com.chenghe.sys.interceptor.Repeat;
import com.chenghe.sys.log.annotation.MethodLog;
import com.chenghe.sys.pojo.SysDictionary;
import com.chenghe.sys.utils.Constants;
import com.youguu.core.logging.Log;
import com.youguu.core.logging.LogFactory;
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
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by leo on 2017/11/29.
 */
@Controller
@RequestMapping("advertise")
public class AdvertiseController {
    private static Log log = LogFactory.getLog(AdvertiseController.class);

    @Resource
    private IAdService adService;
    @Resource
    private IAdStatService adStatService;
    @Resource
    private ICategoryService categoryService;

    @RequestMapping(value = "/addAd", method = RequestMethod.POST)
    @ResponseBody
    @MethodLog(module = Constants.SYS_USER, desc = "添加广告")
    @Repeat
    public BaseResponse addAd(String categoryId, String title, String imageUrl, String url, Integer num, Integer status, Integer companyId) {
        Ad ad = new Ad();
        ad.setNum(num);
        ad.setStatus(status);
        ad.setTitle(title);
        ad.setUrl(url);
        ad.setImageUrl(imageUrl);
        ad.setCategoryId(categoryId);
        ad.setcTime(new Date());
        ad.setCompanyId(companyId);

        int result = adService.addAd(ad);

        BaseResponse rs = new BaseResponse();
        if (result > 0) {
            rs.setStatus(true);
            rs.setMsg("添加成功");
        } else {
            rs.setStatus(false);
            rs.setMsg("添加失败");
        }
        return rs;
    }

    @RequestMapping(value = "/updateAd", method = RequestMethod.POST)
    @ResponseBody
    @MethodLog(module = Constants.SYS_USER, desc = "修改广告")
    @Repeat
    public BaseResponse updateAd(Integer id, String title, String categoryId, String imageUrl, String url, Integer num, Integer status, Integer companyId) {
        BaseResponse rs = new BaseResponse();

        Ad ad = adService.getAd(id);
        if (null == ad) {
            rs.setStatus(false);
            rs.setMsg("广告不存在");
            return rs;
        }

        ad.setNum(num);
        ad.setTitle(title);
        ad.setStatus(status);
        ad.setUrl(url);
        ad.setImageUrl(imageUrl);
        ad.setCategoryId(categoryId);
        ad.setmTime(new Date());
        ad.setCompanyId(companyId);

        int result = adService.updateAd(ad);

        if (result > 0) {
            rs.setStatus(true);
            rs.setMsg("修改成功");
        } else {
            rs.setStatus(false);
            rs.setMsg("修改失败");
        }
        return rs;
    }

    @RequestMapping(value = "/deleteAd")
    @ResponseBody
    @MethodLog(module = Constants.SYS_USER, desc = "删除广告")
    @Repeat
    public BaseResponse deleteAd(int id) {
        BaseResponse rs = new BaseResponse();

        int result = adService.delete(id);
        if (result > 0) {
            rs.setStatus(true);
            rs.setMsg("删除成功");
        } else {
            rs.setStatus(false);
            rs.setMsg("删除失败");
        }

        return rs;
    }

    @RequestMapping(value = "/adList", method = RequestMethod.POST)
    @ResponseBody
    public GridBaseResponse adList(@RequestParam(value = "title", defaultValue = "") String title,
                                   @RequestParam(value = "status", defaultValue = "99") Integer status,
                                   @RequestParam(value = "categoryId", defaultValue = "") String categoryId,
                                   @RequestParam(value = "page", defaultValue = "1") int page,
                                   @RequestParam(value = "limit", defaultValue = "10") int limit) {

        GridBaseResponse rs = new GridBaseResponse();
        rs.setCode(0);
        rs.setMsg("ok");

        AdQuery query = new AdQuery();
        query.setTitle(title);

        if (!StringUtils.isEmpty(categoryId) && !"-1".equals(categoryId)) {
            query.setCategoryId(categoryId);
        } else {
            List<Category> categoryList = categoryService.listByParent("00000002", TokenManager.getAppId());
            if (!CollectionUtils.isEmpty(categoryList)) {
                StringBuffer sb = new StringBuffer();
                for (Category category : categoryList) {
                    sb.append(category.getId()).append(",");
                }
                query.setCategoryId(sb.toString().substring(0, sb.toString().length()-1));
            }
        }

        if (status != 99) {
            query.setStatus(status);
        }
        query.setPageIndex(page);
        query.setPageSize(limit);

        PageHolder<Ad> pageHolder = adService.queryAd(query);
        if (null != pageHolder.getList()) {
            rs.setData(pageHolder.getList());
            rs.setCount(pageHolder.getTotalCount());
        }

        return rs;
    }

    @RequestMapping(value = "/adStatList", method = RequestMethod.POST)
    @ResponseBody
    public GridBaseResponse adStatList(@RequestParam(value = "adId", defaultValue = "") Integer adId,
                                       @RequestParam(value = "statDate", defaultValue = "") String statDate,
                                       @RequestParam(value = "companyId", defaultValue = "") String companyId,
                                       @RequestParam(value = "page", defaultValue = "1") int page,
                                       @RequestParam(value = "limit", defaultValue = "10") int limit) {

        GridBaseResponse rs = new GridBaseResponse();
        rs.setCode(0);
        rs.setMsg("ok");

        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            AdStatQuery query = new AdStatQuery();
            query.setAdId(adId);
            query.setCompanyId(companyId);
            if (!StringUtils.isEmpty(statDate)) {
                query.setStatDate(dateFormat.parse(statDate));
            }
            query.setPageIndex(page);
            query.setPageSize(limit);

            /**
             * 查询appId对应的广告类别
             */
            List<Category> categoryList = categoryService.listByParent("00000002", TokenManager.getAppId());
            if (!CollectionUtils.isEmpty(categoryList)) {
                StringBuffer sb = new StringBuffer();
                for (Category category : categoryList) {
                    sb.append(category.getId()).append(",");
                }
                query.setCategoryId(sb.toString().substring(0, sb.toString().length()-1));
            }

            PageHolder<AdStat> pageHolder = adStatService.queryAdStatStat(query);
            if (null != pageHolder.getList()) {
                /**
                 * 根据类别ID查询类别名称，页面显示
                 */
                Map<String, String> categoryMap = new HashMap<>();
                if(!CollectionUtils.isEmpty(categoryList)){
                    for (Category category : categoryList) {
                        categoryMap.put(category.getId(), category.getName());
                    }
                }

                for(AdStat adStat : pageHolder.getList()){
                    adStat.setCategoryName(categoryMap.get(adStat.getCategoryId()));
                }

                rs.setData(pageHolder.getList());
                rs.setCount(pageHolder.getTotalCount());
            }

            return rs;
        } catch (Exception e) {
            log.error("adStatList", e);
        }
        rs.setCode(-1);
        rs.setMsg("系统异常");
        return rs;
    }
}
