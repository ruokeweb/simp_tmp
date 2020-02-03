package com.mpri.aio.app.utils.model;

/**
 * 小程序配置参数
 * @author Administrator
 *
 */
public class MiniProConfig {

	private String key;
	
	private Object val;

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public Object getVal() {
		return val;
	}

	public void setVal(Object val) {
		this.val = val;
	}

	public MiniProConfig(String key, Object val) {
		super();
		this.key = key;
		this.val = val;
	}
	
	
}
