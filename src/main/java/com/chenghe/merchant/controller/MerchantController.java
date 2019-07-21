package com.chenghe.merchant.controller;

import com.chenghe.common.BaseResponse;
import com.chenghe.common.GridBaseResponse;
import com.chenghe.common.UploadResponse;
import com.chenghe.parttime.pojo.Company;
import com.chenghe.parttime.query.CompanyQuery;
import com.chenghe.parttime.service.ICompanyService;
import com.chenghe.sys.interceptor.Repeat;
import com.chenghe.sys.log.annotation.MethodLog;
import com.chenghe.sys.utils.Constants;
import com.youguu.core.util.PageHolder;
import com.youguu.core.util.PropertiesUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.Random;

/**
 * @version V1.0
 * @Title:
 * @ClassName: com.chenghe.merchant.controller.MerchantController.java
 * @Description: 商户管理
 * @date: 10:20
 */
@Controller
@RequestMapping("merchant")
public class MerchantController {

    @Resource
    private ICompanyService companyService;

    @RequestMapping(value = "/addMerchant", method = RequestMethod.POST)
    @ResponseBody
    @MethodLog(module = Constants.SYS_USER, desc = "添加用户")
    @Repeat
    public BaseResponse addMerchant(String name, String logo, String des, String phone,
                                    String qq, String wx, Integer star) {
        Company company = new Company();
        company.setName(name);
        company.setLogo(logo);
        company.setDes(des);
        company.setPhone(phone);
        company.setQq(qq);
        company.setWx(wx);
        company.setStar(star);

        int result = companyService.addCompany(company);

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

    @RequestMapping(value = "/updateMerchant", method = RequestMethod.POST)
    @ResponseBody
    @MethodLog(module = Constants.SYS_USER, desc = "修改商户")
    @Repeat
    public BaseResponse updateMerchant(Integer id, String name, String logo, String des, String phone,
                                       String qq, String wx, Integer star) {
        BaseResponse rs = new BaseResponse();

        Company company = companyService.getCompany(id);
        if (null == company) {
            rs.setStatus(false);
            rs.setMsg("商户不存在");
        }

        company.setName(name);
        company.setLogo(logo);
        company.setDes(des);
        company.setPhone(phone);
        company.setQq(qq);
        company.setWx(wx);
        company.setStar(star);

        int result = companyService.updateCompany(company);

        if (result > 0) {
            rs.setStatus(true);
            rs.setMsg("修改成功");
        } else {
            rs.setStatus(false);
            rs.setMsg("修改失败");
        }

        return rs;
    }

    @RequestMapping(value = "/deleteMerchant")
    @ResponseBody
    @MethodLog(module = Constants.SYS_USER, desc = "删除商户")
    @Repeat
    public BaseResponse deleteMerchant(int id) {
        BaseResponse rs = new BaseResponse();

        int result = companyService.delete(id);

        if (result > 0) {
            rs.setStatus(true);
            rs.setMsg("删除成功");
        } else {
            rs.setStatus(false);
            rs.setMsg("删除失败");
        }

        return rs;
    }

    @RequestMapping(value = "/merchantList", method = RequestMethod.POST)
    @ResponseBody
    public GridBaseResponse merchantList(@RequestParam(value = "name", defaultValue = "") String name,
                                         @RequestParam(value = "page", defaultValue = "1") int page,
                                         @RequestParam(value = "limit", defaultValue = "10") int limit) {

        GridBaseResponse rs = new GridBaseResponse();
        rs.setCode(0);
        rs.setMsg("ok");

        CompanyQuery query = new CompanyQuery();
        query.setName(name);
        query.setPageIndex(page);
        query.setPageSize(limit);

        PageHolder<Company> pageHolder = companyService.queryCompany(query);
        if (null != pageHolder.getList()) {
            rs.setData(pageHolder.getList());
            rs.setCount(pageHolder.getTotalCount());
        }

        return rs;
    }
}
