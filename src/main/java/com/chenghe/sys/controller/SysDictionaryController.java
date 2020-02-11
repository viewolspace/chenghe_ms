package com.chenghe.sys.controller;

import com.chenghe.common.BaseResponse;
import com.chenghe.common.Option;
import com.chenghe.common.SelectListResponse;
import com.chenghe.sys.interceptor.Repeat;
import com.chenghe.sys.log.annotation.MethodLog;
import com.chenghe.sys.pojo.SysDictionary;
import com.chenghe.sys.service.ISysDictionaryService;
import com.chenghe.sys.utils.Constants;
import com.chenghe.sys.vo.DictionaryTreeNode;
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
 * 数据字典维护
 */
@Controller
@RequestMapping("sysDictionary")
public class SysDictionaryController {

    @Resource
    private ISysDictionaryService sysDictionaryService;

    @RequestMapping(value = "/addSysDictionary", method = RequestMethod.POST)
    @ResponseBody
    @MethodLog(module = Constants.DICTIONARY, desc = "添加类别")
    @Repeat
    public BaseResponse addSysDictionary(@RequestParam(value = "pid", defaultValue = "") String pid,
                                         @RequestParam(value = "title", defaultValue = "") String title,
                                         @RequestParam(value = "num", defaultValue = "0") int num,
                                         @RequestParam(value = "value", defaultValue = "") String value) {

        BaseResponse rs = new BaseResponse();
        SysDictionary sysDictionary = new SysDictionary();
        sysDictionary.setName(title);
        sysDictionary.setParentId(pid);
        sysDictionary.setNum(num);
        sysDictionary.setValue(value);
        sysDictionary.setcTime(new Date());

        String result = sysDictionaryService.addSysDictionary(sysDictionary);
        if (null == result || "".equals(result)) {
            rs.setStatus(false);
            rs.setMsg("添加失败");
        } else {
            rs.setStatus(true);
            rs.setMsg("添加成功");
        }

        return rs;
    }

    @RequestMapping(value = "/deleteSysDictionary")
    @ResponseBody
    @MethodLog(module = Constants.DICTIONARY, desc = "删除类别")
    @Repeat
    public BaseResponse deleteSysDictionary(@RequestParam(value = "id", defaultValue = "") String id) {
        BaseResponse rs = new BaseResponse();

        int result = sysDictionaryService.delSysDictionary(id);
        if (result > 0) {
            rs.setStatus(true);
            rs.setMsg("删除成功");
        } else {
            rs.setStatus(false);
            rs.setMsg("删除失败");
        }

        return rs;
    }

    @RequestMapping(value = "/updateSysDictionary", method = RequestMethod.POST)
    @ResponseBody
    @MethodLog(module = Constants.DICTIONARY, desc = "修改类别")
    @Repeat
    public BaseResponse updateSysDictionary(@RequestParam(value = "id", defaultValue = "") String id,
                                            @RequestParam(value = "pid", defaultValue = "") String pid,
                                            @RequestParam(value = "title", defaultValue = "") String title,
                                            @RequestParam(value = "num", defaultValue = "0") int num,
                                            @RequestParam(value = "value", defaultValue = "") String value) {

        BaseResponse rs = new BaseResponse();
        SysDictionary sysDictionary = sysDictionaryService.getSysDictionary(id);
        sysDictionary.setName(title);
        sysDictionary.setParentId(pid);
        sysDictionary.setNum(num);
        sysDictionary.setValue(value);
        int result = sysDictionaryService.updateSysDictionary(sysDictionary);

        if (result > 0) {
            rs.setStatus(true);
            rs.setMsg("修改成功");
        } else {
            rs.setStatus(false);
            rs.setMsg("修改失败");
        }


        return rs;
    }

    @RequestMapping(value = "/sysDictionaryList", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public List<DictionaryTreeNode> sysDictionaryList() {
        List<DictionaryTreeNode> nodeList = new ArrayList<>();
        List<SysDictionary> list = sysDictionaryService.listAll("0000");
        for (SysDictionary sysDictionary : list) {
            DictionaryTreeNode treeNode = new DictionaryTreeNode();
            treeNode.setId(sysDictionary.getId());
            treeNode.setPid(sysDictionary.getParentId());
            treeNode.setTitle(sysDictionary.getName());
            treeNode.setValue(sysDictionary.getValue());
            treeNode.setNum(sysDictionary.getNum());
            nodeList.add(treeNode);
        }

        return nodeList;
    }

    /**
     * 加载分类下拉框数据
     *
     * @param parentId
     * @return
     */
    @RequestMapping(value = "/listDataDic")
    @ResponseBody
    public SelectListResponse listDataDic(@RequestParam(value = "parentId", defaultValue = "0") String parentId) {
        SelectListResponse rs = new SelectListResponse();
        rs.setStatus(true);
        rs.setMsg("ok");

        List<SysDictionary> list = sysDictionaryService.listByParent(parentId);
        if (list != null && list.size() > 0) {
            List<Option> optionList = new ArrayList<>();
            for (SysDictionary sysDictionary : list) {
                Option option = new Option();
                option.setKey(sysDictionary.getId());
                option.setValue(sysDictionary.getName());
                optionList.add(option);
            }
            rs.setData(optionList);
        }
        return rs;
    }
}
