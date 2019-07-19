package com.chenghe.sys.response;

import com.chenghe.common.BaseResponse;
import com.chenghe.sys.pojo.SysRole;

import java.util.List;

/**
 * Created by leo on 2017/11/30.
 */
public class RoleResponse extends BaseResponse {

	private int recordsFiltered;
	private int recordsTotal;
	private List<SysRole> data;

	public int getRecordsFiltered() {
		return recordsFiltered;
	}

	public void setRecordsFiltered(int recordsFiltered) {
		this.recordsFiltered = recordsFiltered;
	}

	public int getRecordsTotal() {
		return recordsTotal;
	}

	public void setRecordsTotal(int recordsTotal) {
		this.recordsTotal = recordsTotal;
	}

	public List<SysRole> getData() {
		return data;
	}

	public void setData(List<SysRole> data) {
		this.data = data;
	}
}
