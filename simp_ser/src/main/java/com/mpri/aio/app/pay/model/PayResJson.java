package com.mpri.aio.app.pay.model;

import java.util.concurrent.ConcurrentMap;

/**
 * 支付返回的实体
* <p>Title: PayResJson</p>  
* <p>Description: </p>  
* @author syp  
* @date 2018年9月20日
 */
public class PayResJson {

    private static final long serialVersionUID = 1L;

    private boolean success = true;// 是否成功

    private Object obj = null;// 其他信息

    private ConcurrentMap<String, Object> attributes;// 其他参数

    private String errorCode;// 错误码

    private Integer totalSize;// 错误码

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public Object getObj() {
		return obj;
	}

	public void setObj(Object obj) {
		this.obj = obj;
	}

	public ConcurrentMap<String, Object> getAttributes() {
		return attributes;
	}

	public void setAttributes(ConcurrentMap<String, Object> attributes) {
		this.attributes = attributes;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public Integer getTotalSize() {
		return totalSize;
	}

	public void setTotalSize(Integer totalSize) {
		this.totalSize = totalSize;
	}
    
}
