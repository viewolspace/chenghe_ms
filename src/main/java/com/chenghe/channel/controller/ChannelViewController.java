package com.chenghe.channel.controller;

import com.chenghe.common.BaseResponse;
import com.chenghe.common.GridBaseResponse;
import com.chenghe.parttime.pojo.ChannelView;
import com.chenghe.parttime.query.ChannelViewQuery;
import com.chenghe.parttime.service.impl.ChannelViewServiceImpl;
import com.chenghe.sys.interceptor.Repeat;
import com.chenghe.sys.log.annotation.MethodLog;
import com.chenghe.sys.pojo.SysDictionary;
import com.chenghe.sys.service.ISysDictionaryService;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("channel")
public class ChannelViewController {
    private static Log log = LogFactory.getLog(ChannelViewController.class);

    @Resource
    private ChannelViewServiceImpl channelViewService;
    @Resource
    private ISysDictionaryService sysDictionaryService;

    /**
     * @param version
     * @param channel
     * @param type
     * @return
     */
    @RequestMapping(value = "/addChannelView", method = RequestMethod.POST)
    @ResponseBody
    @MethodLog(module = Constants.SYS_USER, desc = "添加渠道配置")
    @Repeat
    public BaseResponse addChannelView(String appId, String version, String channel, Integer type) {
        ChannelView channelView = new ChannelView();
        channelView.setAppId(appId);
        channelView.setChannel(channel);
        channelView.setVersion(version);
        channelView.setType(type);

        int result = channelViewService.addChannelView(channelView);

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

    @RequestMapping(value = "/updateChannelView", method = RequestMethod.POST)
    @ResponseBody
    @MethodLog(module = Constants.SYS_USER, desc = "修改渠道配置")
    @Repeat
    public BaseResponse updateChannelView(Integer id, String appId, String version, String channel, Integer type) {
        BaseResponse rs = new BaseResponse();

        ChannelView channelView = channelViewService.getChannelView(id);
        if (null == channelView) {
            rs.setStatus(false);
            rs.setMsg("渠道配置不存在");
            return rs;
        }

        channelView.setAppId(appId);
        channelView.setChannel(channel);
        channelView.setVersion(version);
        channelView.setType(type);

        int result = channelViewService.updateChannelView(channelView);

        if (result > 0) {
            rs.setStatus(true);
            rs.setMsg("修改成功");
        } else {
            rs.setStatus(false);
            rs.setMsg("修改失败");
        }
        return rs;
    }


    @RequestMapping(value = "/delChannelView")
    @ResponseBody
    @MethodLog(module = Constants.SYS_USER, desc = "删除渠道配置")
    @Repeat
    public BaseResponse delChannelView(int id) {
        BaseResponse rs = new BaseResponse();

        int result = channelViewService.delChannelView(id);
        if (result > 0) {
            rs.setStatus(true);
            rs.setMsg("删除成功");
        } else {
            rs.setStatus(false);
            rs.setMsg("删除失败");
        }

        return rs;
    }

    @RequestMapping(value = "/channelViewList", method = RequestMethod.POST)
    @ResponseBody
    public GridBaseResponse channelViewList(@RequestParam(value = "version", defaultValue = "") String version,
                                            @RequestParam(value = "appId", defaultValue = "") String appId,
                                            @RequestParam(value = "channel", defaultValue = "") String channel,
                                            @RequestParam(value = "page", defaultValue = "1") int page,
                                            @RequestParam(value = "limit", defaultValue = "10") int limit) {

        GridBaseResponse rs = new GridBaseResponse();
        rs.setCode(0);
        rs.setMsg("ok");

        ChannelViewQuery query = new ChannelViewQuery();
        query.setPageIndex(page);
        query.setPageSize(limit);
        query.setVersion(version);
        query.setChannel(channel);
        if(!"-1".equals(appId)){
            query.setAppId(appId);
        }

        PageHolder<ChannelView> pageHolder = channelViewService.queryChannelView(query);

        List<SysDictionary> list = sysDictionaryService.listByParentAndApp("00000001", 0);
        Map<String, String> map = new HashMap<>();

        for (SysDictionary sysDictionary : list) {
            map.put(sysDictionary.getValue(), sysDictionary.getName());
        }
        if (null != pageHolder.getList()) {
            for (ChannelView channelView : pageHolder.getList()) {
                channelView.setAppName(map.get(channelView.getAppId()));
            }
            rs.setData(pageHolder.getList());
            rs.setCount((int) pageHolder.getTotalCount());
        }

        return rs;
    }


    @RequestMapping(value = "/getChannelView", method = RequestMethod.POST)
    @ResponseBody
    public ChannelResponse getChannelView(int id) {
        ChannelResponse rs = new ChannelResponse();

        try {
            ChannelView ad = channelViewService.getChannelView(id);

            if (null != ad) {
                rs.setStatus(true);
                rs.setMsg("成功");
                rs.setData(ad);
            } else {
                rs.setStatus(false);
                rs.setMsg("数据不存在");
            }
        } catch (Exception e) {
            log.error("getAd error", e);
            rs.setStatus(false);
            rs.setMsg("系统异常");
        }

        return rs;
    }


}
