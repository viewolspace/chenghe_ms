package com.chenghe.position.controller;

import com.chenghe.common.BaseResponse;
import com.chenghe.common.GridBaseResponse;
import com.chenghe.parttime.pojo.Category;
import com.chenghe.parttime.pojo.PartTime;
import com.chenghe.parttime.query.PartTimeQuery;
import com.chenghe.parttime.service.ICategoryService;
import com.chenghe.parttime.service.IPartTimeService;
import com.chenghe.sys.interceptor.Repeat;
import com.chenghe.sys.log.annotation.MethodLog;
import com.chenghe.sys.utils.Constants;
import com.youguu.core.logging.Log;
import com.youguu.core.logging.LogFactory;
import com.youguu.core.util.PageHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;

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
                                    Integer cycle, String lable, Integer contactType, String contact,
                                    String content, Integer num, String workTime, String workAddress,
                                    Integer status, String sTime, String eTime) {

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
            partTime.setContactType(contactType);
            partTime.setContact(contact);
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
                                       Integer cycle, String lable, Integer contactType, String contact,
                                       String content, Integer num, String workTime, String workAddress,
                                       Integer status, String sTime, String eTime, Integer browseNum,
                                       Integer copyNum, Integer joinNum) {
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
            partTime.setContactType(contactType);
            partTime.setContact(contact);
            partTime.setContent(content);
            partTime.setNum(num);
            partTime.setWorkTime(workTime);
            partTime.setWorkAddress(workAddress);
            partTime.setStatus(status);
            SimpleDateFormat dft = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            partTime.setsTime(dft.parse(sTime));
            partTime.seteTime(dft.parse(eTime));
            partTime.setmTime(new Date());

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
                                         @RequestParam(value = "page", defaultValue = "1") int page,
                                         @RequestParam(value = "limit", defaultValue = "10") int limit) {

        GridBaseResponse rs = new GridBaseResponse();
        rs.setCode(0);
        rs.setMsg("ok");

        PartTimeQuery query = new PartTimeQuery();
        query.setTitle(title);
        query.setPageIndex(page);
        query.setPageSize(limit);
        PageHolder<PartTime> pageHolder = partTimeService.queryPartTime(query);
        if (null != pageHolder.getList()) {
            rs.setData(pageHolder.getList());
            rs.setCount(pageHolder.getTotalCount());
        }

        return rs;
    }
}
