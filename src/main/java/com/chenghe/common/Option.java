package com.chenghe.common;

/**
 * Select Option
 * Created by leo on 2017/12/16.
 */
public class Option {
	private Object key;
	private String value;
	private boolean select = false;

	public Object getKey() {
		return key;
	}

	public void setKey(Object key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public boolean isSelect() {
		return select;
	}

	public void setSelect(boolean select) {
		this.select = select;
	}
}
