package com.chenghe.advertise.controller;

import com.chenghe.common.BaseResponse;
import com.chenghe.common.GridBaseResponse;
import com.chenghe.parttime.pojo.Ad;
import com.chenghe.parttime.query.AdQuery;
import com.chenghe.parttime.service.IAdService;
import com.chenghe.sys.interceptor.Repeat;
import com.chenghe.sys.log.annotation.MethodLog;
import com.chenghe.sys.utils.Constants;
import com.youguu.core.util.PageHolder;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Date;

/**
 * Created by leo on 2017/11/29.
 */
@Controller
@RequestMapping("advertise")
public class AdvertiseController {

    @Resource
    private IAdService adService;

    @RequestMapping(value = "/addAd", method = RequestMethod.POST)
    @ResponseBody
    @MethodLog(module = Constants.SYS_USER, desc = "添加广告")
    @Repeat
    public BaseResponse addAd(String categoryId, String title, String imageUrl, String url, Integer num, Integer status) {
        Ad ad = new Ad();
        ad.setNum(num);
        ad.setStatus(status);
        ad.setTitle(title);
        ad.setUrl(url);
        ad.setImageUrl(imageUrl);
        ad.setCategoryId(categoryId);
        ad.setcTime(new Date());

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
    public BaseResponse updateAd(Integer id, String title,String categoryId, String imageUrl, String url, Integer num, Integer status) {
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
                                   @RequestParam(value = "status", defaultValue = "") Integer status,
                                   @RequestParam(value = "categoryId", defaultValue = "") String categoryId,
                                   @RequestParam(value = "page", defaultValue = "1") int page,
                                   @RequestParam(value = "limit", defaultValue = "10") int limit) {

        GridBaseResponse rs = new GridBaseResponse();
        rs.setCode(0);
        rs.setMsg("ok");

        AdQuery query = new AdQuery();
        query.setTitle(title);

        if(!StringUtils.isEmpty(categoryId) && !"-1".equals(categoryId)){
            query.setCategoryId(categoryId);
        }

        if(status != 99){
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
}
