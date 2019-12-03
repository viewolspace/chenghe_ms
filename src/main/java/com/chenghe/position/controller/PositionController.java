package com.chenghe.position.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.chenghe.common.BaseResponse;
import com.chenghe.common.GridBaseResponse;
import com.chenghe.common.LayeditResponse;
import com.chenghe.parttime.pojo.Category;
import com.chenghe.parttime.pojo.Contact;
import com.chenghe.parttime.pojo.PartTime;
import com.chenghe.parttime.query.PartTimeQuery;
import com.chenghe.parttime.service.ICategoryService;
import com.chenghe.parttime.service.IPartTimeService;
import com.chenghe.shiro.token.TokenManager;
import com.chenghe.sys.interceptor.Repeat;
import com.chenghe.sys.log.annotation.MethodLog;
import com.chenghe.sys.utils.Constants;
import com.youguu.core.logging.Log;
import com.youguu.core.logging.LogFactory;
import com.youguu.core.util.PageHolder;
import com.youguu.core.util.PropertiesUtil;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Random;

/**
 * Created by leo on 2017/11/29.
 */
@Controller
@RequestMapping("position")
public class PositionController {

    private static Log log = LogFactory.getLog(PositionController.class);

    @Resource
    private IPartTimeService partTimeService;
    @Resource
    private ICategoryService categoryService;

    @RequestMapping(value = "/addPosition", method = RequestMethod.POST)
    @ResponseBody
    @MethodLog(module = Constants.SYS_USER, desc = "添加职位")
    @Repeat
    public BaseResponse addPosition(Integer companyId, Integer recommend, String categoryId,
                                    Integer topNum, String title, Integer salary,
                                    Integer cycle, String lable,
                                    String content, Integer num, String workTime, String workAddress,
                                    Integer status, String sTime, String eTime, String imageUrl, Integer verify,
                                    @RequestParam(value = "contactArray", defaultValue = "") String contactArray) {

        int result = 0;

        try {
            PartTime partTime = new PartTime();
            partTime.setCompanyId(companyId);
            partTime.setRecommend(recommend);
            partTime.setCategoryId(categoryId);

            Category category = categoryService.getCategory(categoryId);
            if (null != category) {
                partTime.setCategoryName(category.getName());

            }
            partTime.setTopNum(topNum);
            partTime.setTitle(title);
            partTime.setSalary(salary);
            partTime.setCycle(cycle);
            partTime.setLable(lable);
            partTime.setContactType(1);
            partTime.setContact("");
            partTime.setContent(content);
            partTime.setNum(num);
            partTime.setWorkTime(workTime);
            partTime.setWorkAddress(workAddress);
            partTime.setStatus(status);
            SimpleDateFormat dft = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            partTime.setsTime(dft.parse(sTime));
            partTime.seteTime(dft.parse(eTime));
            partTime.setcTime(new Date());
            partTime.setBrowseNum(0);
            partTime.setCopyNum(0);
            partTime.setJoinNum(0);
            partTime.setPic(imageUrl);
            partTime.setVerify(verify);

            List<Contact> list = JSONArray.parseArray(contactArray, Contact.class);   //联系方式

            if (!CollectionUtils.isEmpty(list)) {
                partTime.setExt(JSONObject.toJSONString(list));
            }

            result = partTimeService.addPartTime(partTime);
        } catch (Exception e) {
            log.error(e);
        }

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

    @RequestMapping(value = "/updatePosition", method = RequestMethod.POST)
    @ResponseBody
    @MethodLog(module = Constants.SYS_USER, desc = "修改职位")
    @Repeat
    public BaseResponse updatePosition(Integer id, Integer companyId, Integer recommend, String categoryId,
                                       String categoryName, Integer topNum, String title, Integer salary,
                                       Integer cycle, String lable,
                                       String content, Integer num, String workTime, String workAddress,
                                       Integer status, String sTime, String eTime, Integer browseNum,
                                       Integer copyNum, Integer joinNum, String imageUrl, Integer verify,
                                       @RequestParam(value = "contactArray", defaultValue = "") String contactArray) {
        BaseResponse rs = new BaseResponse();

        int result = 0;
        try {
            PartTime partTime = partTimeService.getPartTime(id);
            if (null == partTime) {
                rs.setStatus(false);
                rs.setMsg("职位不存在");
            }

            partTime.setCompanyId(companyId);
            partTime.setRecommend(recommend);
            partTime.setCategoryId(categoryId);

            Category category = categoryService.getCategory(categoryId);
            if (null != category) {
                partTime.setCategoryName(category.getName());

            }
            partTime.setTopNum(topNum);
            partTime.setTitle(title);
            partTime.setSalary(salary);
            partTime.setCycle(cycle);
            partTime.setLable(lable);
            partTime.setContactType(1);
            partTime.setContact("");
            partTime.setContent(content);
            partTime.setNum(num);
            partTime.setWorkTime(workTime);
            partTime.setWorkAddress(workAddress);
            partTime.setStatus(status);
            SimpleDateFormat dft = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            partTime.setsTime(dft.parse(sTime));
            partTime.seteTime(dft.parse(eTime));
            partTime.setmTime(new Date());
            partTime.setPic(imageUrl);
            partTime.setVerify(verify);
            List<Contact> list = JSONArray.parseArray(contactArray, Contact.class);   //联系方式
            if (!CollectionUtils.isEmpty(list)) {
                partTime.setExt(JSONObject.toJSONString(list));
            }
            result = partTimeService.updatePartTime(partTime);
        } catch (Exception e) {
            log.error(e);
        }
        if (result > 0) {
            rs.setStatus(true);
            rs.setMsg("修改成功");
        } else {
            rs.setStatus(false);
            rs.setMsg("修改失败");
        }

        return rs;
    }

    @RequestMapping(value = "/deletePosition")
    @ResponseBody
    @MethodLog(module = Constants.SYS_USER, desc = "删除职位")
    @Repeat
    public BaseResponse deletePosition(int id) {
        BaseResponse rs = new BaseResponse();

        int result = partTimeService.delete(id);
        if (result > 0) {
            rs.setStatus(true);
            rs.setMsg("删除成功");
        } else {
            rs.setStatus(false);
            rs.setMsg("删除失败");
        }

        return rs;
    }

    @RequestMapping(value = "/positionList", method = RequestMethod.POST)
    @ResponseBody
    public GridBaseResponse positionList(@RequestParam(value = "title", defaultValue = "") String title,
                                         @RequestParam(value = "recommend", defaultValue = "-1") int recommend,
                                         @RequestParam(value = "page", defaultValue = "1") int page,
                                         @RequestParam(value = "limit", defaultValue = "10") int limit) {

        GridBaseResponse rs = new GridBaseResponse();
        rs.setCode(0);
        rs.setMsg("ok");

        PartTimeQuery query = new PartTimeQuery();
        if (!StringUtils.isEmpty(title)) {
            query.setTitle(title);
        }

        if (recommend != -1) {
            query.setRecommend(recommend);
        }
        query.setPageIndex(page);
        query.setPageSize(limit);
        if (!StringUtils.isEmpty(TokenManager.getCompanyId())) {
            query.setCompanyId(TokenManager.getCompanyId());
        }
        PageHolder<PartTime> pageHolder = partTimeService.queryPartTime(query);

        if (null != pageHolder.getList()) {
            for (PartTime partTime : pageHolder.getList()) {
                partTime.setContent("");
            }
            rs.setData(pageHolder.getList());
            rs.setCount(pageHolder.getTotalCount());
        }

        return rs;
    }

    @RequestMapping(value = "/getPosition")
    @ResponseBody
    public PartTimeResponse getPosition(int id) {
        PartTimeResponse rs = new PartTimeResponse();

        PartTime partTime = partTimeService.getPartTime(id);
        if (null != partTime) {
            rs.setStatus(true);
            rs.setMsg("成功");
            rs.setData(partTime);
        } else {
            rs.setStatus(false);
            rs.setMsg("职位不存在");
        }

        return rs;
    }

    @RequestMapping(value = "/uploadContentImage", method = RequestMethod.POST)
    @ResponseBody
    public LayeditResponse uploadContentImage(@RequestParam(value = "file", required = false) MultipartFile file) throws IOException {

        LayeditResponse rs = new LayeditResponse();

        if (null != file) {
            String myFileName = file.getOriginalFilename();// 文件原名称
            SimpleDateFormat dft = new SimpleDateFormat("yyyyMMddHHmmss");
            String fileName = dft.format(new Date()) + Integer.toHexString(new Random().nextInt()) + "." + myFileName.substring(myFileName.lastIndexOf(".") + 1);

            Properties properties = PropertiesUtil.getProperties("properties/config.properties");
            String path = properties.getProperty("img.path");
            String imageUrl = properties.getProperty("imageUrl");

            SimpleDateFormat yyyyMMdd = new SimpleDateFormat("yyyyMMdd");
            String midPath = yyyyMMdd.format(new Date());
            File fileDir = new File(path + midPath);
            if (!fileDir.exists()) { //如果不存在 则创建
                fileDir.mkdirs();
            }
            path = path + midPath + File.separator + fileName;
            File localFile = new File(path);
            try {
                file.transferTo(localFile);

                rs.setCode(0);
                rs.setMsg("上传成功");
                String httpUrl = imageUrl + File.separator + midPath + File.separator + fileName;
                Map<String, String> map = new HashMap<>();
                map.put("src", httpUrl);
                rs.setData(map);
            } catch (IllegalStateException e) {
                rs.setCode(1);
                rs.setMsg("服务器异常");
            } catch (IOException e) {
                rs.setCode(1);
                rs.setMsg("服务器异常");
            }
        } else {
            rs.setCode(1);
            rs.setMsg("文件为空");
        }

        return rs;
    }

    @RequestMapping(value = "/updateContact", method = RequestMethod.POST)
    @ResponseBody
    public BaseResponse updateContact(@RequestParam(value = "contactArray", defaultValue = "") String contactArray,
                                      @RequestParam(value = "id", defaultValue = "") Integer id) {

        BaseResponse rs = new BaseResponse();

        try {
            List<Contact> list = JSONArray.parseArray(contactArray, Contact.class);   //联系方式
            PartTime partTime = partTimeService.getPartTime(id);
            if(!CollectionUtils.isEmpty(list)){
                partTime.setExt(JSONObject.toJSONString(list));
            }
            int result = partTimeService.updatePartTime(partTime);
            if (result > 0) {
                rs.setStatus(true);
                rs.setMsg("修改成功");
            } else {
                rs.setStatus(false);
                rs.setMsg("修改失败");
            }
        } catch (Exception e) {
            log.error(e);
            rs.setStatus(false);
            rs.setMsg("修改失败");
        }

        return rs;
    }
}
