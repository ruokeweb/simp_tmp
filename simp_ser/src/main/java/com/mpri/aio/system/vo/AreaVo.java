package com.mpri.aio.system.vo;

/**
 * 四级联动
 * @author Administrator
 *
 */
public class AreaVo {
	
	private static final long serialVersionUID = 1551417911221L;
	
	private String id;
	
	private String parentId;
	
	private String name;
	
	

	public AreaVo(String id, String parentId, String name) {
		super();
		this.id = id;
		this.parentId = parentId;
		this.name = name;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
}
