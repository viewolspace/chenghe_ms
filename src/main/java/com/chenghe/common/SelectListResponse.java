package com.chenghe.common;

import java.util.List;

public class SelectListResponse extends BaseResponse {
    private List<Option> data;

    public List<Option> getData() {
        return data;
    }

    public void setData(List<Option> data) {
        this.data = data;
    }
}
