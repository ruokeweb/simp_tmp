package com.mpri.aio.mq;

import java.io.Serializable;
import java.util.Date;

public class User implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String id;
	
	private String Msg;
	
	private Date SendTime;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getMsg() {
		return Msg;
	}

	public void setMsg(String msg) {
		Msg = msg;
	}

	public Date getSendTime() {
		return SendTime;
	}

	public void setSendTime(Date sendTime) {
		SendTime = sendTime;
	}
	
	
	
}
