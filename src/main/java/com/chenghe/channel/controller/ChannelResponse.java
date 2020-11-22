package com.chenghe.channel.controller;


import com.chenghe.common.BaseResponse;
import com.chenghe.parttime.pojo.ChannelView;

public class ChannelResponse extends BaseResponse {
    private ChannelView data;

    public ChannelView getData() {
        return data;
    }

    public void setData(ChannelView data) {
        this.data = data;
    }
}
