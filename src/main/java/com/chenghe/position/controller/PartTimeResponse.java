package com.chenghe.position.controller;

import com.chenghe.common.BaseResponse;
import com.chenghe.parttime.pojo.PartTime;

/**
 * describe:
 *
 * @date: 2019/07/31 21:18:21:18
 * @version: V1.0
 * @review:
 */
public class PartTimeResponse extends BaseResponse {
    private PartTime data;

    public PartTime getData() {
        return data;
    }

    public void setData(PartTime data) {
        this.data = data;
    }
}
