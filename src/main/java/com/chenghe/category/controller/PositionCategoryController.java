package com.chenghe.category.controller;

import com.chenghe.common.BaseResponse;
import com.chenghe.common.Option;
import com.chenghe.common.SelectListResponse;
import com.chenghe.parttime.pojo.Category;
import com.chenghe.parttime.service.ICategoryService;
import com.chenghe.sys.interceptor.Repeat;
import com.chenghe.sys.log.annotation.MethodLog;
import com.chenghe.sys.utils.Constants;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by leo on 2017/11/29.
 */
@Controller
@RequestMapping("positionCategory")
public class PositionCategoryController {

    @Resource
    private ICategoryService categoryService;


    @RequestMapping(value = "/addCategory", method = RequestMethod.POST)
    @ResponseBody
    @MethodLog(module = Constants.CATEGORY, desc = "添加类别")
    @Repeat
    public BaseResponse addCategory(@RequestParam(value = "pid", defaultValue = "") String pid,
                                    @RequestParam(value = "title", defaultValue = "") String title,
                                    @RequestParam(value = "num", defaultValue = "0") int num,
                                    @RequestParam(value = "type", defaultValue = "0") int type) {

        BaseResponse rs = new BaseResponse();
        Category category = new Category();
        category.setName(title);
        category.setParentId(pid);
        category.setNum(num);
        category.setType(type);
        category.setcTime(new Date());

        String result = categoryService.addCategory(category);
        if (null == result || "".equals(result)) {
            rs.setStatus(false);
            rs.setMsg("添加失败");
        } else {
            rs.setStatus(true);
            rs.setMsg("添加成功");
        }

        return rs;
    }

    @RequestMapping(value = "/deleteCategory")
    @ResponseBody
    @MethodLog(module = Constants.CATEGORY, desc = "删除类别")
    @Repeat
    public BaseResponse deleteCategory(@RequestParam(value = "id", defaultValue = "") String id) {
        BaseResponse rs = new BaseResponse();

        int result = categoryService.delCategory(id);
        if (result > 0) {
            rs.setStatus(true);
            rs.setMsg("删除成功");
        } else {
            rs.setStatus(false);
            rs.setMsg("删除失败");
        }

        return rs;
    }

    @RequestMapping(value = "/updateCategory", method = RequestMethod.POST)
    @ResponseBody
    @MethodLog(module = Constants.CATEGORY, desc = "修改类别")
    @Repeat
    public BaseResponse updateCategory(@RequestParam(value = "id", defaultValue = "") String id,
                                       @RequestParam(value = "pid", defaultValue = "") String pid,
                                       @RequestParam(value = "title", defaultValue = "") String title,
                                       @RequestParam(value = "num", defaultValue = "0") int num,
                                       @RequestParam(value = "type", defaultValue = "0") int type) {

        BaseResponse rs = new BaseResponse();
        Category category = categoryService.getCategory(id);
        category.setName(title);
        category.setParentId(pid);
        category.setNum(num);
        category.setType(type);
        int result = categoryService.updateCategory(category);

        if (result > 0) {
            rs.setStatus(true);
            rs.setMsg("修改成功");
        } else {
            rs.setStatus(false);
            rs.setMsg("修改失败");
        }


        return rs;
    }

    @RequestMapping(value = "/categoryList", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public List<TreeNode> categoryList() {
        List<TreeNode> nodeList = new ArrayList<>();
        List<Category> list = categoryService.listAll("0000");
        for (Category category : list) {
            TreeNode treeNode = new TreeNode();
            treeNode.setId(category.getId());
            treeNode.setPid(category.getParentId());
            treeNode.setTitle(category.getName());
//            treeNode.setStatus(category.getS);
            treeNode.setType(category.getType());
            treeNode.setNum(category.getNum());
            nodeList.add(treeNode);
        }

        return nodeList;
    }

    /**
     * 加载分类下拉框数据
     * @param parentId
     * @return
     */
    @RequestMapping(value = "/listDataDic")
    @ResponseBody
    public SelectListResponse listDataDic(@RequestParam(value = "parentId", defaultValue = "0") String parentId) {
        SelectListResponse rs = new SelectListResponse();
        rs.setStatus(true);
        rs.setMsg("ok");

        List<Category> list = categoryService.listByParent(parentId);
        if(list!=null && list.size()>0){
            List<Option> optionList = new ArrayList<>();
            for(Category category : list){
                Option option = new Option();
                option.setKey(category.getId());
                option.setValue(category.getName());
                optionList.add(option);
            }
            rs.setData(optionList);
        }
        return rs;
    }
}
